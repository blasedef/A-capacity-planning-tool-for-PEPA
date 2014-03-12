/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 * 
 * Author: Christopher Williams
 * 
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.MetaHeuristicJob;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists.EvaluatorType;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.MetaheuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.PopulationWeights;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.PopulationMinAndMax;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.TargetsAndWeights;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages.*;


public class CapacityPlanningWizard extends Wizard  {
	
	//Page name
	String pageTitle = "Metaheuristic Capacity Planning";
	
	public ConfigurationModel configurationModel;
	
	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//wizard pages
	public CapacityPlanningWizardPage evaluatorAndMetaHeuristicSelectionPage;
	public CapacityPlanningWizardPage metaheuristicParameterConfigurationPageOne;
	public CapacityPlanningWizardPage ordinaryDifferentialEquationConfigurationPage;
	public CapacityPlanningWizardPage fitnessFunctionConfigurationPage;
	public CapacityPlanningWizardPage performanceConfigurationPage;
	public CapacityPlanningWizardPage populationConfigurationPage;
	public CapacityPlanningWizardPage metaheuristicParameterConfigurationPageTwo;
	public CapacityPlanningWizardPage summaryPage;
	
	public CapacityPlanningWizard(IPepaModel model){
		
		this.configurationModel = new ConfigurationModel(model);
		
		evaluatorAndMetaHeuristicSelectionPage = new EvaluatorAndMetaheuristicSelectionPage(pageTitle, 
				configurationModel.dropDownListList);
		wizardPageList.add(evaluatorAndMetaHeuristicSelectionPage);
		
		metaheuristicParameterConfigurationPageOne = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersRoot,
				configurationModel.labParametersRoot,
				null,
				false,
				false);
		wizardPageList.add(metaheuristicParameterConfigurationPageOne);
		
		ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
				configurationModel.configPEPA, 
				configurationModel.configODE, 
				(EvaluatorType) configurationModel.dropDownListList.get(0));
		wizardPageList.add(ordinaryDifferentialEquationConfigurationPage);
		
		fitnessFunctionConfigurationPage = new FitnessFunctionConfigurationPage(pageTitle,
				configurationModel.systemEquationFitnessWeights);
		wizardPageList.add(fitnessFunctionConfigurationPage);
		
		performanceConfigurationPage = new PerformanceConfigurationPage(pageTitle,
				configurationModel.performanceTargetsAndWeights);
		wizardPageList.add(performanceConfigurationPage);
		
		populationConfigurationPage = new PopulationConfigurationPage(pageTitle,
				configurationModel.systemEquationPopulationRanges,
				configurationModel.populationWeights);
		wizardPageList.add(populationConfigurationPage);
		
		metaheuristicParameterConfigurationPageTwo = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersCandidateLeaf,
				configurationModel.labParametersCandidateLeaf,
				configurationModel.secondDropDownListList,
				true,
				true);
		wizardPageList.add(metaheuristicParameterConfigurationPageTwo);
		
	}
	
	private IWizardPage updateAndGetMetaHeristicConfigurationPage(){
		
		if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S)){
			((MetaheuristicParameters) configurationModel.metaheuristicParametersRoot).update(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S);
		} else {
			((MetaheuristicParameters) configurationModel.metaheuristicParametersRoot).update(Config.METAHEURISTICTYPEHILLCLIMBING_S);
		}

		metaheuristicParameterConfigurationPageOne = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersRoot,
				configurationModel.labParametersRoot,
				null,
				false,
				false);
		addPage(this.metaheuristicParameterConfigurationPageOne);
		
		return this.metaheuristicParameterConfigurationPageOne;
		
	}
	
	
	private IWizardPage updateAndGetODEPage(){
		
		ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
				configurationModel.configPEPA, 
				configurationModel.configODE, 
				(EvaluatorType) configurationModel.dropDownListList.get(0));
		addPage(ordinaryDifferentialEquationConfigurationPage);
		return this.ordinaryDifferentialEquationConfigurationPage;
	}
	
	private IWizardPage updateAndGetFitnessFunctionPage(){
		fitnessFunctionConfigurationPage = new FitnessFunctionConfigurationPage(pageTitle,
				configurationModel.systemEquationFitnessWeights);
		addPage(this.fitnessFunctionConfigurationPage);
		return this.fitnessFunctionConfigurationPage;
	}
	
	private IWizardPage updateAndGetPerformanceConfigurationPage(){
		((TargetsAndWeights) configurationModel.performanceTargetsAndWeights).update(configurationModel.configODE.getLabels());
		performanceConfigurationPage = new PerformanceConfigurationPage(pageTitle,
				configurationModel.performanceTargetsAndWeights);
		addPage(this.performanceConfigurationPage);
		return this.performanceConfigurationPage;
	}
	
	private IWizardPage updateAndGetPopulationConfigurationPage(){
		((PopulationMinAndMax) configurationModel.systemEquationPopulationRanges).update(configurationModel.configPEPA.getSystemEquation(), 
				configurationModel.configPEPA.getInitialPopulation());
		
		((PopulationWeights) configurationModel.populationWeights).update(configurationModel.configPEPA.getSystemEquation(), false);
		
		populationConfigurationPage = new PopulationConfigurationPage(pageTitle,
				configurationModel.systemEquationPopulationRanges,
				configurationModel.populationWeights);
		addPage(this.populationConfigurationPage);
		return this.populationConfigurationPage;
	}
	
	/*
	 * In the case the wizard is completed early, populate everything else with default values.
	 */
	private void updateAllModels(){
		((TargetsAndWeights) configurationModel.performanceTargetsAndWeights).update(configurationModel.configODE.getLabels());
		((PopulationMinAndMax) configurationModel.systemEquationPopulationRanges).update(configurationModel.configPEPA.getSystemEquation(), 
				configurationModel.configPEPA.getInitialPopulation());
		((PopulationWeights) configurationModel.populationWeights).update(configurationModel.configPEPA.getSystemEquation(), false);
		((MetaheuristicParameters) configurationModel.metaheuristicParametersCandidateLeaf).getLeftMap().remove(Config.MUTATIONPROBABILITY_S); 
	}
	
	
	/**
	 * page ordering
	 */
	public IWizardPage getNextPage(IWizardPage page){
		
		if(page == evaluatorAndMetaHeuristicSelectionPage)	{
			return updateAndGetMetaHeristicConfigurationPage();
		}
		else if(page == metaheuristicParameterConfigurationPageOne)	{
			return updateAndGetODEPage();
		}
		else if(page == ordinaryDifferentialEquationConfigurationPage){
			return updateAndGetFitnessFunctionPage();
		}
		else if(page == fitnessFunctionConfigurationPage){
			return updateAndGetPerformanceConfigurationPage();
		}
		else if(page == performanceConfigurationPage){
			return updateAndGetPopulationConfigurationPage();
		}
		else {
			return super.getNextPage(null);
		}
		
	}


	@Override
	public boolean performFinish() {
		
		
		//set all defaults if required...
		this.updateAllModels();
		
		MetaHeuristicJob job = new MetaHeuristicJob("Running the search", 
				configurationModel);
		
		job.setUser(true);
		job.schedule();
		return true;
	}
	
	@Override
	public boolean canFinish(){
		
		return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.fitnessFunctionConfigurationPage.isPageComplete() && 
					this.metaheuristicParameterConfigurationPageOne.isPageComplete());
		
		
	}
	
	@Override
	public void addPages() {
		for (WizardPage wizardPage : this.wizardPageList){
			addPage(wizardPage);
		}
	}
	
} 
