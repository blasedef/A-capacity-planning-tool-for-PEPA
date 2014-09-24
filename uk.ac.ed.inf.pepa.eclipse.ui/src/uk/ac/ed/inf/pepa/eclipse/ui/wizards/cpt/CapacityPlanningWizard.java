package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;


import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
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
	private WizardPage saveAsCapacityPlanningWizardPage;
	
	public CapacityPlanningWizard(){
		
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
		
		saveAsCapacityPlanningWizardPage =
			new SaveAsCapacityPlanningWizardPage("Save results as...");
		
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
