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
	private WizardPage saveAsPageCapacityPlanningWizardPage;
	
	public CapacityPlanningWizard(){
		
		wizardPageList = new ArrayList<WizardPage>();
		
		frontMetaheuristicCapacityPlanningWizardPage = 
			new FrontMetaheuristicCapacityPlanningWizardPage(CPTAPI.getSearchControls().getValue());
		
		wizardPageList.add(frontMetaheuristicCapacityPlanningWizardPage);
		addPage(frontMetaheuristicCapacityPlanningWizardPage);
		
		backMetaheuristicCapacityPlanningWizardPage = 
			new BackMetaheuristicCapacityPlanningWizardPage(CPTAPI.getSearchControls().getValue());
		
		wizardPageList.add(backMetaheuristicCapacityPlanningWizardPage);
		addPage(backMetaheuristicCapacityPlanningWizardPage);
		
	}
	
//	/**
//	 * page ordering
//	 */
//	public IWizardPage getNextPage(IWizardPage page){
//		
//		
//		if(page == frontMetaheuristicCapacityPlanningWizardPage){
//			return driven();
//		}
//		
//		return super.getNextPage(null);
//		
//	}
	
	
//	public IWizardPage driven(){
//		
//		if(CPTAPI.getSearchControls().getValue().equals(Config.SEARCHDRIVEN)){
//			
//			backMetaheuristicCapacityPlanningWizardPage = 
//				new FrontMetaheuristicCapacityPlanningWizardPage(CPTAPI.getSearchControls().getValue());
//			
//			wizardPageList.add(backMetaheuristicCapacityPlanningWizardPage);
//			addPage(backMetaheuristicCapacityPlanningWizardPage);
//		}
//		
//		return backMetaheuristicCapacityPlanningWizardPage;
//		
//	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canFinish(){
		
		return this.frontMetaheuristicCapacityPlanningWizardPage.isPageComplete() &&
		this.backMetaheuristicCapacityPlanningWizardPage.isPageComplete();
		
	}

}
