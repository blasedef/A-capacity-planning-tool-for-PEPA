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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.*;


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

	//The underlying PEPAModel
	IPepaModel model;
	
	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//Wizard pages
	MetaHeuristicCapacityPlanningWizardPage performanceEvaluatorAndMetaHeuristicSelectionPage;
	MetaHeuristicCapacityPlanningWizardPage metaHeuristicConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage additionalCostsPage;
	MetaHeuristicCapacityPlanningWizardPage ordinaryDifferentialEquationConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage targetConfigurationPage;
	MetaHeuristicCapacityPlanningWizardPage	fitnessFunctionConfigurationPage;
	
	//Page name
	String pageTitle = "Metaheuristic Capacity Planning";
	
	
	public MetaHeuristicCapacityPlanningWizard(IPepaModel model){
		//claim model
		this.model = model;
		
		//wizard pages
		this.performanceEvaluatorAndMetaHeuristicSelectionPage = new PerformanceEvaluatorAndMetaHeuristicSelectionPage(this.pageTitle);
		wizardPageList.add(this.performanceEvaluatorAndMetaHeuristicSelectionPage);
		
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		wizardPageList.add(this.metaHeuristicConfigurationPage);
		
		this.additionalCostsPage = new PlaceHolderWizardPage("This is for additional costs");
		wizardPageList.add(this.additionalCostsPage);
		
		this.ordinaryDifferentialEquationConfigurationPage = new PlaceHolderWizardPage("ODE config");
		wizardPageList.add(this.ordinaryDifferentialEquationConfigurationPage);
		
		this.targetConfigurationPage = new PlaceHolderWizardPage("Action / State selection");
		wizardPageList.add(this.targetConfigurationPage);
		
		this.fitnessFunctionConfigurationPage = new PlaceHolderWizardPage("Target and Population range");
		wizardPageList.add(this.fitnessFunctionConfigurationPage);
		
	}
	
	public void updateMetaHeuristicConfigurationPage(){
		this.metaHeuristicConfigurationPage = new MetaHeuristicConfigurationPage(this.pageTitle);
		addPage(this.metaHeuristicConfigurationPage);
	}
	
	public IWizardPage getNextPage(IWizardPage page){
		if(page == performanceEvaluatorAndMetaHeuristicSelectionPage)	{
			return this.metaHeuristicConfigurationPage;
		}
		else if(page == metaHeuristicConfigurationPage)	{
			return this.additionalCostsPage;
		}
		else if(page == additionalCostsPage)	{
			return this.ordinaryDifferentialEquationConfigurationPage;
		}
		else if(page == ordinaryDifferentialEquationConfigurationPage)	{
			return this.targetConfigurationPage;
		}
		else if(page == targetConfigurationPage)	{
			return this.fitnessFunctionConfigurationPage;
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
	

} 
