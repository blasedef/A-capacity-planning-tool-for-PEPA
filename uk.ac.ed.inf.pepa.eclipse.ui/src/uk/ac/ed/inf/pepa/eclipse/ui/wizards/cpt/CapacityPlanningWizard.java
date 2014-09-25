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
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages.*;

public class CapacityPlanningWizard extends Wizard {
	
	private static final String EXTENSION = "csv";
	
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
		
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
				model.getUnderlyingResource(), EXTENSION));
		
		saveAsCapacityPlanningWizardPage = new SaveAsCapacityPlanningWizardPage(new StructuredSelection(handle), EXTENSION);
		saveAsCapacityPlanningWizardPage.setTitle(CPTAPI.getSearchControls().getValue() + ": " + CPTAPI.getEvaluationControls().getValue());
		saveAsCapacityPlanningWizardPage.setDescription("Save model configurations to");
		saveAsCapacityPlanningWizardPage.setFileName(handle.getName());
		
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
	
	

	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
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
