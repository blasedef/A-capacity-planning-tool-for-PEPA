package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.threadAndSubprogressExample;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Configuration;

public class SmallJobTest implements Runnable {
	
	public Configuration c;
	public IProgressMonitor m;
	
	
	public SmallJobTest (Configuration c, IProgressMonitor monitor){
		this.c = c;
		this.m = monitor;
	}

	@Override
	public void run() {
		try {
			m.beginTask("sub thread", 1);
			m.subTask(c.getValue());
			System.out.println(c.getValue());
			m.worked(1);
		} finally {
			m.done();
		}
	}
	
	
}