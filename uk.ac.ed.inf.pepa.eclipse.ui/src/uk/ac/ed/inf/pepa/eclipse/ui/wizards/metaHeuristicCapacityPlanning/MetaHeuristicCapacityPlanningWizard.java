/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;


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
	
	/**
	 * WizardParameters - Used to store the variables used to create the 'dynamic' wizard page path 
	 * MetaHeuristicParameters - Used to store the variables required for the metaheuristics
	 * ODEParameters - Used to store the variables required for the ODE graph and functions
	 */
	
	/** Extension for CSV files */
	private static final String EXTENSION = "csv";
	private WizardNewFileCreationPage newFilePage;
	
	protected ChoiceSelectionPage performanceRequirementSelectionPage;
	protected ThroughputSetupPage throughputSetupPage;
	protected AverageResponseTimeSetupPage averageResponseTimeSetupPage;
	protected SetupOptimiserPage setupOptimiserPage;
	protected HillClimbingSetupPage setupHillClimbingPage;
	protected GeneticAlgorithmSetupPage setupGeneticAlgorithmPage;
	protected FitnessFunctionSetupPage targetSetupPage;
	protected IODESolution performanceRequirementChoice;
	@SuppressWarnings("unused")
	private CPAParameters fParams;
	
	/**
	 * Initialise CPAP, CPAP requires a model (for files?) and to initialise an fGraph for the setup pages 
	 * @param model
	 */
	public CapacityPlanningWizard(IPepaModel model) {
		super();
	    this.fParams = new CPAParameters(model);
	    setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		performanceRequirementSelectionPage = new ChoiceSelectionPage();
		addPage(performanceRequirementSelectionPage);
		throughputSetupPage = new ThroughputSetupPage();
		averageResponseTimeSetupPage = new AverageResponseTimeSetupPage();
		addPage(throughputSetupPage);
		addPage(averageResponseTimeSetupPage);
		addSaveAsPage();
		addPage(newFilePage);
	}
  
	/**
	 * save page setup
	 */
	private void addSaveAsPage() {
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						CPAParameters.model.getUnderlyingResource(), EXTENSION));
	
		newFilePage = new CapacityPlanningSaveAsPage("newFilePage", new StructuredSelection(
				handle), EXTENSION);
		newFilePage.setTitle("Save to CSV");
		newFilePage.setDescription("Save model configurations to");
		newFilePage.setFileName(handle.getName());
		
	}

	@Override
	public boolean performFinish() {
		Job metaheuristicJob;
		CPAParameters.setNewFilePage(newFilePage);
		try {
			metaheuristicJob = new MetaHeuristicJob();
			metaheuristicJob.schedule();
		} catch (InvocationTargetException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"error",
					e.toString());
			
		} catch (InterruptedException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Cancel Acknowledgement",
					"The ODE generation process has been cancelled");
		}
		return true;
	}
	
	/**
	 * I guess this is not the correct way of doing things, but it works, this forces a 'route' through the pages
	 * so that the choices made, and the population of the CPAP effect the pages created later. There is dependency between
	 * the page creation, and the page choice...
	 */
	public IWizardPage getNextPage(IWizardPage page){
		if(page == performanceRequirementSelectionPage){
			if(CPAParameters.getPerformanceChoice() == 0){
				
				this.performanceRequirementChoice = throughputSetupPage;
			} else {
				
				this.performanceRequirementChoice = averageResponseTimeSetupPage;
			}
			return (IWizardPage) this.performanceRequirementChoice;
			
		} else if (page == performanceRequirementChoice){
			return this.targetSetupPage;
			
		} else if (page == targetSetupPage){
			if(CPAParameters.getMetaHeuristicChoice() == 0){
				this.setupOptimiserPage = this.setupHillClimbingPage;
			} else {
				this.setupOptimiserPage = this.setupGeneticAlgorithmPage;
			}
			return this.setupOptimiserPage;
			
		} else if (page == setupOptimiserPage){
			return this.newFilePage;
		} else {
			return super.getNextPage(null);
		}
	}
	
	public void addFitnessFunctionPage(){
		this.targetSetupPage = new FitnessFunctionSetupPage();
		this.addPage(targetSetupPage);
		this.addPage(newFilePage);
	}
	
	public void addSetupOptimiserPage(){
		this.setupHillClimbingPage = new HillClimbingSetupPage();
		this.setupGeneticAlgorithmPage = new GeneticAlgorithmSetupPage();
		this.addPage(this.setupHillClimbingPage);
		this.addPage(this.setupGeneticAlgorithmPage);
	}
	
	public boolean canFinish (){
		if(setupOptimiserPage != null){
			if(setupOptimiserPage.isPageComplete() 
					&& performanceRequirementChoice.isPageComplete() 
					&& performanceRequirementSelectionPage.isPageComplete()
					&& newFilePage.isPageComplete()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
} 
