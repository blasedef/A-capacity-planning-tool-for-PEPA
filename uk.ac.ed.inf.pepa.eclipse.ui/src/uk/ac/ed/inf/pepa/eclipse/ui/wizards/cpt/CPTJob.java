package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;

public class CPTJob extends Job {

	public CPTJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		CPTAPI.createCPT(monitor);
		
		IStatus status = CPTAPI.startCPT();
		
		return status;
	}
	
	

}
