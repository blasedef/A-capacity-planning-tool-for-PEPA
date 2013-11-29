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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.MetaHeuristicJob;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists.EvaluatorType;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.MetaheuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.PopulationWeights;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.SystemEquation;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.Targets;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages.*;


public class CapacityPlanningWizard extends Wizard {
	
	//Page name
	String pageTitle = "Metaheuristic Capacity Planning";
	
	public ConfigurationModel configurationModel;
	
	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//wizard pages
	public CapacityPlanningWizardPage evaluatorAndMetaHeuristicSelectionPage;
	public CapacityPlanningWizardPage metaheuristicParameterConfigurationPageOne;
	public CapacityPlanningWizardPage additionalCostsPage;
	public CapacityPlanningWizardPage ordinaryDifferentialEquationConfigurationPage;
	public CapacityPlanningWizardPage fitnessFunctionConfigurationPage;
	public CapacityPlanningWizardPage performanceConfigurationPage;
	public CapacityPlanningWizardPage populationConfigurationPage;
	public CapacityPlanningWizardPage metaheuristicParameterConfigurationPageTwo;
	public CapacityPlanningWizardPage fileSaveAsPage;
	public CapacityPlanningWizardPage summaryPage;
	
	/** Extension for CSV files */
	private static final String EXTENSION = "csv";
	private WizardNewFileCreationPage newFilePage;
	
	public CapacityPlanningWizard(IPepaModel model){
		
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
		
		additionalCostsPage = new AdditionalCostsPage(pageTitle);
		wizardPageList.add(additionalCostsPage);
		
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
				configurationModel.systemEquationCandidate,
				configurationModel.populationWeights);
		wizardPageList.add(populationConfigurationPage);
		
		metaheuristicParameterConfigurationPageTwo = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersCandidate,
				configurationModel.labParametersCandidate,
				configurationModel.metaheuristicFitnessWeights,
				true,
				true);
		wizardPageList.add(metaheuristicParameterConfigurationPageTwo);
		
		addSaveAsPage();
		wizardPageList.add(newFilePage);
		
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
	
	private IWizardPage updateAndGetAdditionalCostPage(){
		additionalCostsPage = new AdditionalCostsPage(pageTitle);
		addPage(additionalCostsPage);
		return this.additionalCostsPage;
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
		((Targets) configurationModel.performanceTargetsAndWeights).update(configurationModel.configODE.getLabels());
		performanceConfigurationPage = new PerformanceConfigurationPage(pageTitle,
				configurationModel.performanceTargetsAndWeights);
		addPage(this.performanceConfigurationPage);
		return this.performanceConfigurationPage;
	}
	
	private IWizardPage updateAndGetPopulationConfigurationPage(){
		((SystemEquation) configurationModel.systemEquationCandidate).update(configurationModel.configPEPA.getSystemEquation(), 
				configurationModel.configPEPA.getInitialPopulation());
		
		((PopulationWeights) configurationModel.populationWeights).update(configurationModel.configPEPA.getSystemEquation(), false);
		
		populationConfigurationPage = new PopulationConfigurationPage(pageTitle,
				configurationModel.systemEquationCandidate,
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
	
	private IWizardPage updateAndGetNewFilePage(){
		addSaveAsPage();
		addPage(newFilePage);
		return newFilePage;
	}
	
	/**
	 * page ordering
	 */
	public IWizardPage getNextPage(IWizardPage page){
		
		boolean additionalCosts = configurationModel.dropDownListsList.get(3).getValue().equals(Config.ADDITIONALCOSTSYES_S);
		boolean chained = !configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S);
		
		if(page == evaluatorAndMetaHeuristicSelectionPage)	{
			return updateAndGetEvaluatorAndMetaHeristicPage();
		}
		else if(page == metaheuristicParameterConfigurationPageOne && additionalCosts)	{
			return updateAndGetAdditionalCostPage();
		}
		else if(page == metaheuristicParameterConfigurationPageOne && !additionalCosts)	{
			if(!chained){
				return updateAndGetODEPage();
			} else {
				return updateAndGetSecondMetaheuristicPage();
			}
		}
		else if(page == additionalCostsPage && !chained){
			return updateAndGetODEPage();
		}
		else if(page == additionalCostsPage && chained){
			return updateAndGetSecondMetaheuristicPage();
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
		else if(page == populationConfigurationPage)	{
			return updateAndGetNewFilePage();
		}
		else {
			return super.getNextPage(null);
		}
		
	}
	
	/**
	 * save page setup
	 */
	private void addSaveAsPage() {
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						configurationModel.configPEPA.getPepaModel().getUnderlyingResource(), EXTENSION));

		this.newFilePage = new CapacityPlanningSaveAsPage("newFilePage", new StructuredSelection(
				handle), EXTENSION);
		this.newFilePage.setTitle("Save to CSV");
		this.newFilePage.setDescription("Save model configurations to");
		
		String fileName = configurationModel.dropDownListsList.get(0).getValue() + 
		"_" + 
		configurationModel.dropDownListsList.get(1).getValue() +
		"_" + 
		configurationModel.dropDownListsList.get(2).getValue() + 
		"_" + 
		configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S) +
		"_" +
		this.getDateTime();
		
		this.newFilePage.setFileName(fileName + "_" + handle.getName()  );

	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

	@Override
	public boolean performFinish() {
		
		MetaHeuristicJob job = new MetaHeuristicJob("Running the search", 
				configurationModel, 
				this.newFilePage.createNewFile());
		
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
					this.metaheuristicParameterConfigurationPageTwo.isPageComplete() &&
					this.newFilePage.isPageComplete());
		} else {
			return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.fitnessFunctionConfigurationPage.isPageComplete() && 
					this.metaheuristicParameterConfigurationPageOne.isPageComplete() &&
					this.newFilePage.isPageComplete());
		}
		
		
	}
	
	@Override
	public void addPages() {
		for (WizardPage wizardPage : this.wizardPageList){
			addPage(wizardPage);
		}
	}
	
} 
