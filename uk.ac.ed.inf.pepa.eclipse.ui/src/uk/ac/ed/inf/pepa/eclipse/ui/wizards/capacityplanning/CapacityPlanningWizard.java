/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import uk.ac.ed.inf.common.ui.wizards.SaveAsPage;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;


public class CapacityPlanningWizard extends Wizard {
	
	/** Extension for CSV files */
	private static final String EXTENSION = "csv";
	private WizardNewFileCreationPage newFilePage;
	
	protected PerformanceRequirementSelectionPage performanceRequirementSelectionPage;
	protected ThroughputSetupPage throughputSetupPage;
	protected AverageResponseTimeSetupPage averageResponseTimeSetupPage;
	protected SetupOptimiserPage setupOptimiserPage;
	protected TargetSetupPage targetSetupPage;
	protected IODESolution sendToMetaheuristic;
	private IPepaModel model;
	@SuppressWarnings("unused")
	private CapacityPlanningAnalysisParameters fParams;
	
	public CapacityPlanningWizard(IPepaModel model) {
		super();
	    this.model = (IPepaModel) model;
	    this.fParams = new CapacityPlanningAnalysisParameters(this.model);
	    setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		addSaveAsPage();
		performanceRequirementSelectionPage = new PerformanceRequirementSelectionPage();
		addPage(performanceRequirementSelectionPage);
		throughputSetupPage = new ThroughputSetupPage();
		averageResponseTimeSetupPage = new AverageResponseTimeSetupPage();
		addPage(throughputSetupPage);
		addPage(averageResponseTimeSetupPage);
	}
  
	private void addSaveAsPage() {
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						model.getUnderlyingResource(), EXTENSION));
	
		newFilePage = new SaveAsPage("newFilePage", new StructuredSelection(
				handle), EXTENSION);
		newFilePage.setTitle("Save to CSV");
		newFilePage.setDescription("Save model configurations to");
		newFilePage.setFileName(handle.getName());
		this.addPage(newFilePage);
	}

	@Override
	public boolean performFinish() {
		Job metaheuristicJob;
		try {
			metaheuristicJob = new MetaHeuristicJob(this.newFilePage);
			metaheuristicJob.schedule();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public IWizardPage getNextPage(IWizardPage page){
		if(page == performanceRequirementSelectionPage){
			if(performanceRequirementSelectionPage.getPerformanceRequirement()){
				this.sendToMetaheuristic = averageResponseTimeSetupPage;
				CapacityPlanningAnalysisParameters.performanceRequirementType = 1;
				return averageResponseTimeSetupPage;
			} else {
				this.sendToMetaheuristic = throughputSetupPage;
				CapacityPlanningAnalysisParameters.performanceRequirementType = 0;
				return throughputSetupPage;
			}
		} else if (page == averageResponseTimeSetupPage || page == throughputSetupPage){
			return targetSetupPage;
		} else {
			return super.getNextPage(page);
		}
	}
	
	public void addRemainingPages(){
		targetSetupPage = new TargetSetupPage();
		this.addPage(targetSetupPage);
		setupOptimiserPage = new SetupOptimiserPage();
		addPage(setupOptimiserPage);
	}
	
	public boolean canFinish (){
		if(setupOptimiserPage != null){
			if(setupOptimiserPage.isPageComplete() && sendToMetaheuristic.isPageComplete() && performanceRequirementSelectionPage.isPageComplete()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
} 
