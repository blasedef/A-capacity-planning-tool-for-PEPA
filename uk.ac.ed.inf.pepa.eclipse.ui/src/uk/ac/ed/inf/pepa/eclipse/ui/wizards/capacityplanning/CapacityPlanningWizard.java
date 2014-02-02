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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
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


public class CapacityPlanningWizard extends Wizard {
	
	static Logger log = Logger.getLogger(CapacityPlanningWizard.class);
	
	
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
		
		BasicConfigurator.configure();
		
		this.configurationModel = new ConfigurationModel(model);
		
		evaluatorAndMetaHeuristicSelectionPage = new EvaluatorAndMetaheuristicSelectionPage(pageTitle, 
				configurationModel.dropDownListsList);
		wizardPageList.add(evaluatorAndMetaHeuristicSelectionPage);
		
		metaheuristicParameterConfigurationPageOne = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParameters,
				configurationModel.labParameters,
				null,
				false,
				false);
		wizardPageList.add(metaheuristicParameterConfigurationPageOne);
		
		ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
				configurationModel.configPEPA, 
				configurationModel.configODE, 
				(EvaluatorType) configurationModel.dropDownListsList.get(0));
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
				configurationModel.metaheuristicParametersCandidate,
				configurationModel.labParametersCandidate,
				configurationModel.metaheuristicFitnessWeights,
				true,
				true);
		wizardPageList.add(metaheuristicParameterConfigurationPageTwo);
		
	}
	
	private IWizardPage updateAndGetEvaluatorAndMetaHeristicPage(){
		if(configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			((MetaheuristicParameters) configurationModel.metaheuristicParameters).update(configurationModel.dropDownListsList.get(1).getValue());
		} else {
			((MetaheuristicParameters) configurationModel.metaheuristicParameters).update(Config.METAHEURISTICTYPEHILLCLIMBING_S);
		}
		metaheuristicParameterConfigurationPageOne = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParameters,
				configurationModel.labParameters,
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
				(EvaluatorType) configurationModel.dropDownListsList.get(0));
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
	
	private IWizardPage updateAndGetSecondMetaheuristicPage(){
		((MetaheuristicParameters) configurationModel.metaheuristicParametersCandidate).update(configurationModel.dropDownListsList.get(1).getValue());
		metaheuristicParameterConfigurationPageTwo = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersCandidate, 
				configurationModel.labParametersCandidate,
				configurationModel.metaheuristicFitnessWeights,
				(configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINPIPELINE_S)),
				true);
		addPage(this.metaheuristicParameterConfigurationPageTwo);
		return this.metaheuristicParameterConfigurationPageTwo;
	}
	
	/*
	 * In the case the wizard is completed early, populate everything else with default values.
	 */
	private void updateAllModels(){
		((TargetsAndWeights) configurationModel.performanceTargetsAndWeights).update(configurationModel.configODE.getLabels());
		((PopulationMinAndMax) configurationModel.systemEquationPopulationRanges).update(configurationModel.configPEPA.getSystemEquation(), 
				configurationModel.configPEPA.getInitialPopulation());
		((PopulationWeights) configurationModel.populationWeights).update(configurationModel.configPEPA.getSystemEquation(), false);
		((MetaheuristicParameters) configurationModel.metaheuristicParametersCandidate).update(configurationModel.dropDownListsList.get(1).getValue());

	}
	
	
	/**
	 * page ordering
	 */
	public IWizardPage getNextPage(IWizardPage page){
		
		boolean chained = !configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S);
		
		if(page == evaluatorAndMetaHeuristicSelectionPage)	{
			return updateAndGetEvaluatorAndMetaHeristicPage();
		}
		else if(page == metaheuristicParameterConfigurationPageOne) { // && !additionalCosts)	{
			if(!chained){
				return updateAndGetODEPage();
			} else {
				return updateAndGetSecondMetaheuristicPage();
			}
		}
		else if(page == metaheuristicParameterConfigurationPageTwo){
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
		
		if(!configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			
			
			return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.fitnessFunctionConfigurationPage.isPageComplete() && 
					this.metaheuristicParameterConfigurationPageOne.isPageComplete() &&
					this.metaheuristicParameterConfigurationPageTwo.isPageComplete());
		} else {
			
			
			return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.fitnessFunctionConfigurationPage.isPageComplete() && 
					this.metaheuristicParameterConfigurationPageOne.isPageComplete());
		}
		
		
	}
	
	@Override
	public void addPages() {
		for (WizardPage wizardPage : this.wizardPageList){
			addPage(wizardPage);
		}
	}
	
} 
