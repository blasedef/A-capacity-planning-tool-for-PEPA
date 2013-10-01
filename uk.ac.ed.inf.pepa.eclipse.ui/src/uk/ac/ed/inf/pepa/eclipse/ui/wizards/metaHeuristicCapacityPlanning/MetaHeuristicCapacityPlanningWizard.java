/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.job.MetaHeuristicJob;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Tools;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.*;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ModelNode;


/**
 * 
 * MPP2 Minf project, A capacity planning tool for PEPA Eclipse
 * 
 * @author Christoper Williams
 * @version 1.1 (in terms of this wizard)
 * 
 *
 */
public class MetaHeuristicCapacityPlanningWizard extends Wizard {

	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//Wizard pages
	MetaHeuristicCapacityPlanningWizardPage performanceEvaluatorAndMetaHeuristicSelectionPage;
	MetaHeuristicCapacityPlanningWizardPage metaHeuristicConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage pipelineMetaHeuristicConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage additionalCostsPage;
	MetaHeuristicCapacityPlanningWizardPage ordinaryDifferentialEquationConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage systemEquationTargetConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage experimentTargetConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage summaryPage;
	
	//Page name
	String pageTitle = "Metaheuristic Capacity Planning";
	
	
	//The underlying PEPAModel
	private IPepaModel model;
	
	//An AST ModelNode
	private ModelNode node;
	
	//A derivation of the model for the wizard
	private IParametricDerivationGraph dGraph;
	
	
	public MetaHeuristicCapacityPlanningWizard(IPepaModel model){
		
		//claim model
		this.model = (IPepaModel) model;
		
		this.node = model.getAST();
		
		this.dGraph = Tools.getDevGraphFromAST(node);
		
		ExperimentConfiguration.resetToDefaults();
		
		//wizard pages
		this.performanceEvaluatorAndMetaHeuristicSelectionPage = new PerformanceEvaluatorAndMetaHeuristicSelectionPage(this.pageTitle);
		wizardPageList.add(this.performanceEvaluatorAndMetaHeuristicSelectionPage);
		
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		wizardPageList.add(this.metaHeuristicConfigurationPage);
		
		this.additionalCostsPage = new PlaceHolderWizardPage("This is for additional costs (Place holder page for later work)");
		wizardPageList.add(this.additionalCostsPage);
		
		this.ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(this.pageTitle, dGraph, model, node);
		wizardPageList.add(this.ordinaryDifferentialEquationConfigurationPage);
		
		this.systemEquationTargetConfigurationPage = new PlaceHolderWizardPage("Target and Population range (System Equation)");
		wizardPageList.add(this.systemEquationTargetConfigurationPage);
		
		this.experimentTargetConfigurationPage = new PlaceHolderWizardPage("Target and Population range (Metaheuristic/Experiment)");
		wizardPageList.add(this.experimentTargetConfigurationPage);
		
		this.summaryPage = new PlaceHolderWizardPage("Summary");
		wizardPageList.add(this.summaryPage);
		
	}
	
	public void updateMHAndODEPages(){
		ExperimentConfiguration.metaHeuristic.updateNetwork(ExperimentConfiguration.networkType.getValue());
		
		/*
		 * These pages need updating with the user choices
		 */
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		addPage(this.metaHeuristicConfigurationPage);
		this.ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(this.pageTitle, dGraph, model, node);
		addPage(this.ordinaryDifferentialEquationConfigurationPage);
	}
	
	public void updateSystemEquationTargetPage(){
		
		/*
		 * Important to update the values for the re-created SystemEquationPage
		 */
		ExperimentConfiguration.metaHeuristic.updateTargetValues();
		ExperimentConfiguration.metaHeuristic.updateToDefaultMinPopulationRanges();
		ExperimentConfiguration.metaHeuristic.updateToDefaultMaxPopulationRanges();
		
		/*
		 * Now the page is recreated
		 */
		this.systemEquationTargetConfigurationPage = new SystemEquationTargetConfigurationPage(this.pageTitle);
		addPage(this.systemEquationTargetConfigurationPage);
	}
	
	public void updateExperimentTargetPage(){
		//ExperimentConfiguration.metaHeuristic.updateNetwork(ExperimentConfiguration.networkType.getValue());
		this.experimentTargetConfigurationPage = new ExperimentTargetConfigurationPage(this.pageTitle);
		addPage(this.experimentTargetConfigurationPage);
	}
	
	public void updateSummaryPage(){
		this.summaryPage = new SummaryPage(this.pageTitle);
		addPage(this.summaryPage);
	}
	
	public IWizardPage getNextPage(IWizardPage page){
		if(page == performanceEvaluatorAndMetaHeuristicSelectionPage)	{
			return this.metaHeuristicConfigurationPage;
		}
		else if(page == metaHeuristicConfigurationPage && (ExperimentConfiguration.additionalCosts.getValue().equals(ExperimentConfiguration.ADDITIONALCOSTSYES_S)))	{
			return this.additionalCostsPage;
		}
		else if(page == metaHeuristicConfigurationPage && (ExperimentConfiguration.additionalCosts.getValue().equals(ExperimentConfiguration.ADDITIONALCOSTSNO_S)))	{
			return this.ordinaryDifferentialEquationConfigurationPage;
		}
		else if(page == additionalCostsPage)	{
			return this.ordinaryDifferentialEquationConfigurationPage;
		}
		else if(page == ordinaryDifferentialEquationConfigurationPage){
			return this.systemEquationTargetConfigurationPage;
		}
		else if(page == pipelineMetaHeuristicConfigurationPage){
			return this.systemEquationTargetConfigurationPage;
		}
		else if(page == systemEquationTargetConfigurationPage && (ExperimentConfiguration.networkType.getValue().equals(ExperimentConfiguration.NETWORKSINGLE_S))){
			return this.summaryPage;
		}
		else if(page == systemEquationTargetConfigurationPage && (!ExperimentConfiguration.networkType.getValue().equals(ExperimentConfiguration.NETWORKSINGLE_S))){
			return this.experimentTargetConfigurationPage;
		}
		else if(page == experimentTargetConfigurationPage){
			return this.summaryPage;
		}
		else {
			return super.getNextPage(null);
		}
		
	}
	
	@Override
	public void addPages() {
		for (WizardPage wizardPage : this.wizardPageList){
			addPage(wizardPage);
		}
	}
	
	@Override
	public boolean performFinish() {
		MetaHeuristicJob job = new MetaHeuristicJob("Running the search");
		job.setUser(true);
		job.schedule();
		return true;
	}
	
	public boolean canFinish(){
		return (this.systemEquationTargetConfigurationPage.isPageComplete() 
				&& this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() 
				&& this.experimentTargetConfigurationPage.isPageComplete());
	}

} 
