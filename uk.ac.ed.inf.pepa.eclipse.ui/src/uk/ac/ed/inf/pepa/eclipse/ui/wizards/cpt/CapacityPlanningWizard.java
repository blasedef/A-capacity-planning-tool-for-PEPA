package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;


import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages.*;

public class CapacityPlanningWizard extends Wizard {
	
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	public CapacityPlanningWizard(){
		
		wizardPageList.add(new CapacityPlanningWizardPageOne(CPTAPI.getSearchControls().getValue()));
		addPage(wizardPageList.get(0));
		
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
