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
	public CapacityPlanningWizardPage systemEquationTargetConfigurationPage;
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
				null,
				false);
		wizardPageList.add(metaheuristicParameterConfigurationPageOne);
		
		additionalCostsPage = new AdditionalCostsPage(pageTitle);
		wizardPageList.add(additionalCostsPage);
		
		ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
				configurationModel.configPEPA, 
				configurationModel.configODE, 
				(EvaluatorType) configurationModel.dropDownListsList.get(0));
		wizardPageList.add(ordinaryDifferentialEquationConfigurationPage);
		
		systemEquationTargetConfigurationPage = new SystemEquationTargetConfigurationPage(pageTitle,
				configurationModel.systemEquationFitnessWeights,
				configurationModel.performanceTargetsAndWeights,
				configurationModel.systemEquationCandidate);
		wizardPageList.add(systemEquationTargetConfigurationPage);
		
		metaheuristicParameterConfigurationPageTwo = new MetaheuristicParameterConfigurationPage(pageTitle, 
				configurationModel.metaheuristicParametersCandidate, 
				configurationModel.metaheuristicFitnessWieghts,
				true);
		wizardPageList.add(metaheuristicParameterConfigurationPageTwo);
		
		addSaveAsPage();
		wizardPageList.add(newFilePage);
		
	}
	
	public IWizardPage getNextPage(IWizardPage page){
		if(page == evaluatorAndMetaHeuristicSelectionPage)	{
			
			addSaveAsPage();
			addPage(newFilePage);
			
			if(configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)){
				((MetaheuristicParameters) configurationModel.metaheuristicParameters).update(configurationModel.dropDownListsList.get(1).getValue());
			} else {
				((MetaheuristicParameters) configurationModel.metaheuristicParameters).update(Config.METAHEURISTICTYPEHILLCLIMBING_S);
			}
			metaheuristicParameterConfigurationPageOne = new MetaheuristicParameterConfigurationPage(pageTitle, 
					configurationModel.metaheuristicParameters,
					null,
					false);
			addPage(this.metaheuristicParameterConfigurationPageOne);
			
			return this.metaheuristicParameterConfigurationPageOne;
			
		}
		else if(page == metaheuristicParameterConfigurationPageOne && (configurationModel.dropDownListsList.get(3).getValue().equals(Config.ADDITIONALCOSTSYES_S)))	{
			additionalCostsPage = new AdditionalCostsPage(pageTitle);
			addPage(additionalCostsPage);
			return this.additionalCostsPage;
			
		}
		else if(page == metaheuristicParameterConfigurationPageOne && (configurationModel.dropDownListsList.get(3).getValue().equals(Config.ADDITIONALCOSTSNO_S)))	{
			ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
					configurationModel.configPEPA, 
					configurationModel.configODE, 
					(EvaluatorType) configurationModel.dropDownListsList.get(0));
			addPage(ordinaryDifferentialEquationConfigurationPage);
			return this.ordinaryDifferentialEquationConfigurationPage;
			
		}
		else if(page == additionalCostsPage)	{
			ordinaryDifferentialEquationConfigurationPage = new OrdinaryDifferentialEquationConfigurationPage(pageTitle, 
					configurationModel.configPEPA, 
					configurationModel.configODE, 
					(EvaluatorType) configurationModel.dropDownListsList.get(0));
			addPage(ordinaryDifferentialEquationConfigurationPage);
			return this.ordinaryDifferentialEquationConfigurationPage;
			
		}
		else if(page == ordinaryDifferentialEquationConfigurationPage){
			
			((Targets) configurationModel.performanceTargetsAndWeights).update(configurationModel.configODE.getLabels());
			
			((SystemEquation) configurationModel.systemEquationCandidate).update(configurationModel.configPEPA.getSystemEquation(), 
					configurationModel.configPEPA.getInitialPopulation());
			systemEquationTargetConfigurationPage = new SystemEquationTargetConfigurationPage(pageTitle,
					configurationModel.systemEquationFitnessWeights,
					configurationModel.performanceTargetsAndWeights,
					configurationModel.systemEquationCandidate);
			addPage(this.systemEquationTargetConfigurationPage);
			return this.systemEquationTargetConfigurationPage;
		}
		else if(page == systemEquationTargetConfigurationPage && (configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)))	{
			return newFilePage;
		}
		else if(page == systemEquationTargetConfigurationPage && (!configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)))	{
			
			((MetaheuristicParameters) configurationModel.metaheuristicParametersCandidate).update(configurationModel.dropDownListsList.get(1).getValue());
			metaheuristicParameterConfigurationPageTwo = new MetaheuristicParameterConfigurationPage(pageTitle, 
					configurationModel.metaheuristicParametersCandidate, 
					configurationModel.metaheuristicFitnessWieghts,
					true);
			addPage(this.metaheuristicParameterConfigurationPageTwo);
			
			return this.metaheuristicParameterConfigurationPageTwo;
		}
		else if(page == this.metaheuristicParameterConfigurationPageTwo){
			return this.newFilePage;
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
		configurationModel.dropDownListsList.get(2).getValue();
		
		this.newFilePage.setFileName(fileName + "_" + (System.currentTimeMillis()/1000) + "_" + handle.getName()  );

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
		
		if(!configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)){
			return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.systemEquationTargetConfigurationPage.isPageComplete() && 
					this.metaheuristicParameterConfigurationPageOne.isPageComplete() &&
					this.metaheuristicParameterConfigurationPageTwo.isPageComplete() &&
					this.newFilePage.isPageComplete());
		} else {
			return (this.ordinaryDifferentialEquationConfigurationPage.isPageComplete() && 
					this.systemEquationTargetConfigurationPage.isPageComplete() && 
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
