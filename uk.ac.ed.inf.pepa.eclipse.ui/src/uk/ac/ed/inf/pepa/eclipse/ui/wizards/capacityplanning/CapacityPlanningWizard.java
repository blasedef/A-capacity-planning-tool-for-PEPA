/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.core.IProcessAlgebraModel;

public class CapacityPlanningWizard extends Wizard {

	private IPepaModel model;
	
	public CapacityPlanningWizard(IPepaModel model) {
			if (model == null)
				throw new NullPointerException();
			if (!model.isDerivable())
				throw new IllegalArgumentException("The model is not derivable");
			this.model = model;
			this.setForcePreviousAndNextButtons(true);
			this.setNeedsProgressMonitor(true);

		}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
