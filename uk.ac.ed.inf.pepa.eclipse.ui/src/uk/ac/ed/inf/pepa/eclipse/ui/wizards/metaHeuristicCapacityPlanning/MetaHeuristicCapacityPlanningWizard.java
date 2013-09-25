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
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ModelType;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;
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
 * @version 1.1 (in terms of this wizard
 * 
 *
 */
public class MetaHeuristicCapacityPlanningWizard extends Wizard {

	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//Wizard pages
	MetaHeuristicCapacityPlanningWizardPage performanceEvaluatorAndMetaHeuristicSelectionPage;
	MetaHeuristicCapacityPlanningWizardPage metaHeuristicConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage additionalCostsPage;
	MetaHeuristicCapacityPlanningWizardPage ordinaryDifferentialEquationConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage targetConfigurationPage;
	
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
		this.model = model;
		
		this.node = model.getAST();
		
		try{
			//so this is how to make the graph :)
			this.dGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(model.getAST(), null);
			
		} catch (InterruptedException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Cancel Acknowledgement",
					"The ODE generation process has been cancelled");
			
		} catch (DifferentialAnalysisException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Differential error",
					e.getMessage());
			
		}
		
		//wizard pages
		this.performanceEvaluatorAndMetaHeuristicSelectionPage = new PerformanceEvaluatorAndMetaHeuristicSelectionPage(this.pageTitle);
		wizardPageList.add(this.performanceEvaluatorAndMetaHeuristicSelectionPage);
		
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		wizardPageList.add(this.metaHeuristicConfigurationPage);
		
		this.additionalCostsPage = new PlaceHolderWizardPage("This is for additional costs (Place holder page for later work)");
		wizardPageList.add(this.additionalCostsPage);
		
		this.ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(this.pageTitle, dGraph, model, node);
		wizardPageList.add(this.ordinaryDifferentialEquationConfigurationPage);
		
		this.targetConfigurationPage = new PlaceHolderWizardPage("Target and Population range");
		wizardPageList.add(this.targetConfigurationPage);
		
	}
	
	public void updateMHAndODEPages(){
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		addPage(this.metaHeuristicConfigurationPage);
		this.ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(this.pageTitle, dGraph, model, node);
		addPage(this.ordinaryDifferentialEquationConfigurationPage);
	}
	
	public void updateTargetPage(){
		this.targetConfigurationPage = new TargetConfigurationPage(this.pageTitle);
		addPage(this.targetConfigurationPage);
	}
	
	public IWizardPage getNextPage(IWizardPage page){
		if(page == performanceEvaluatorAndMetaHeuristicSelectionPage)	{
			return this.metaHeuristicConfigurationPage;
		}
		else if(page == metaHeuristicConfigurationPage && (Models.additionalCosts.getValue().equals(ModelType.ADDITIONALCOSTSYES_S)))	{
			return this.additionalCostsPage;
		}
		else if(page == metaHeuristicConfigurationPage && (Models.additionalCosts.getValue().equals(ModelType.ADDITIONALCOSTSNO_S)))	{
			return this.ordinaryDifferentialEquationConfigurationPage;
		}
		else if(page == additionalCostsPage )	{
			return this.ordinaryDifferentialEquationConfigurationPage;
		}
		else if(page == ordinaryDifferentialEquationConfigurationPage)	{
			return this.targetConfigurationPage;
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
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canFinish(){
		return (this.targetConfigurationPage.isPageComplete() && this.ordinaryDifferentialEquationConfigurationPage.isPageComplete());
	}

} 
