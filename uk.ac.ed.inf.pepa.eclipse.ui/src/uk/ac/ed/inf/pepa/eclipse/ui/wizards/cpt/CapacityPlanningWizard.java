package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt;

import org.eclipse.jface.wizard.Wizard;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;

public class CapacityPlanningWizard extends Wizard {

	private IPepaModel model;
	
	public CapacityPlanningWizard(IPepaModel model){
		this.model = model;
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
