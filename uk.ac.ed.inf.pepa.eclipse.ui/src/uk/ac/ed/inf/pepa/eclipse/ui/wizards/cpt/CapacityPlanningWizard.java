package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;


import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.listen.CapacityPlanningObserver;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages.*;

public class CapacityPlanningWizard extends Wizard {
	
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	private WizardPage frontMetaheuristicCapacityPlanningWizardPage;
	private WizardPage backMetaheuristicCapacityPlanningWizardPage;
	private WizardPage costFunctionCapacityPlanningWizardPage;
	private WizardPage performanceSelectionCapacityPlanningWizardPage;
	private WizardPage performanceTargetCapacityPlanningWizardPage;
	private WizardPage odeOptionCapacityPlanningWizardPage;
	private WizardPage populationCapacityPlanningWizardPage;
	private WizardNewFileCreationPage saveAsCapacityPlanningWizardPage;
	
	public CapacityPlanningWizard(IPepaModel model){
		
		wizardPageList = new ArrayList<WizardPage>();
		
		if(CPTAPI.getSearchControls().getValue().equals(Config.SEARCHSINGLE))
			CPTAPI.getPSORangeParameterControls().defaultUnits();
		
		frontMetaheuristicCapacityPlanningWizardPage = 
			new FrontMetaheuristicCapacityPlanningWizardPage("Meta heuristic configuration");
		
		backMetaheuristicCapacityPlanningWizardPage = 
			new BackMetaheuristicCapacityPlanningWizardPage("Driven Meta heuristic configuration");
		
		costFunctionCapacityPlanningWizardPage =
			new CostFunctionCapacityPlanningWizardPage("Cost function configuration...");
		
		performanceSelectionCapacityPlanningWizardPage =
			new PerformanceSelectionCapacityPlanningWizardPage("Performance cost: State/Action selection...");
		
		performanceTargetCapacityPlanningWizardPage =
			new PerformanceTargetCapacityPlanningWizardPage("Performance cost: Target/Threshold values...");
		
		odeOptionCapacityPlanningWizardPage = 
			new ODEOptionCapacityPlanningWizardPage("Performance cost: ODE configuration...");
		
		populationCapacityPlanningWizardPage =
			new PopulationCapacityPlanningWizardPage("Population cost: range and weight configuration...");
		
		saveAsCapacityPlanningWizardPage = new SaveAsCapacityPlanningWizardPage("Save as...",new StructuredSelection(
				getHandle(model)));
		
		wizardPageList.add(frontMetaheuristicCapacityPlanningWizardPage);
		wizardPageList.add(backMetaheuristicCapacityPlanningWizardPage);
		wizardPageList.add(costFunctionCapacityPlanningWizardPage);
		wizardPageList.add(performanceSelectionCapacityPlanningWizardPage);
		wizardPageList.add(performanceTargetCapacityPlanningWizardPage);
		wizardPageList.add(odeOptionCapacityPlanningWizardPage);
		wizardPageList.add(populationCapacityPlanningWizardPage);
		wizardPageList.add(saveAsCapacityPlanningWizardPage);
		
		addPage(frontMetaheuristicCapacityPlanningWizardPage);
		addPage(backMetaheuristicCapacityPlanningWizardPage);
		addPage(costFunctionCapacityPlanningWizardPage);
		addPage(performanceSelectionCapacityPlanningWizardPage);
		addPage(performanceTargetCapacityPlanningWizardPage);
		addPage(odeOptionCapacityPlanningWizardPage);
		addPage(populationCapacityPlanningWizardPage);
		addPage(saveAsCapacityPlanningWizardPage);
		
		setWindowTitle(CPTAPI.getSearchControls().getValue());
	}
	
	/**
	 * page ordering
	 */
	public IWizardPage getNextPage(IWizardPage page){
		
		
		if(page == frontMetaheuristicCapacityPlanningWizardPage){
			if(CPTAPI.getSearchControls().getValue().equals(Config.SEARCHDRIVEN)){
				return backMetaheuristicCapacityPlanningWizardPage;
			} else {
				return costFunctionCapacityPlanningWizardPage;
			}
		}
		
		if(page == backMetaheuristicCapacityPlanningWizardPage){
			return costFunctionCapacityPlanningWizardPage;
		}
		
		if(page == costFunctionCapacityPlanningWizardPage){
			return performanceSelectionCapacityPlanningWizardPage;
		}
		
		if(page == performanceSelectionCapacityPlanningWizardPage){
			
			performanceTargetCapacityPlanningWizardPage =
				new PerformanceTargetCapacityPlanningWizardPage("Performance cost: Target/Threshold values...");
			
			addPage(performanceTargetCapacityPlanningWizardPage);
			
			return performanceTargetCapacityPlanningWizardPage;
		}
		
		if(page == performanceTargetCapacityPlanningWizardPage){
			return odeOptionCapacityPlanningWizardPage;
		}
		
		if(page == odeOptionCapacityPlanningWizardPage){
			return populationCapacityPlanningWizardPage;
		}
		
		if(page == populationCapacityPlanningWizardPage){
			return saveAsCapacityPlanningWizardPage;
		}
		
		return super.getNextPage(null);
		
	}
	
	

	public IFile getHandle(IPepaModel model){
		
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
				model.getUnderlyingResource(), Config.EXTENSION));
		
		CPTAPI.setFileName(handle.getName());
		
		CPTAPI.setFolderName(ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						model.getUnderlyingResource(), Config.EXTENSION)).getRawLocation().removeLastSegments(1).toOSString());
		
		return handle;
		
	}
	
	@Override
	public boolean performFinish() {
		
		final CapacityPlanningObserver observer = new CapacityPlanningObserver();
		
		final CPTJob job = new CPTJob("Searching the model configuration space...",observer);
		
		job.setUser(true);
		job.schedule();
		
		return true;
	}
	
	@Override
	public boolean canFinish(){
		
		boolean finished = true;
		
		for(WizardPage w : this.wizardPageList){
			finished = finished && w.isPageComplete();
		}
		
		return finished;
		
	}

}
