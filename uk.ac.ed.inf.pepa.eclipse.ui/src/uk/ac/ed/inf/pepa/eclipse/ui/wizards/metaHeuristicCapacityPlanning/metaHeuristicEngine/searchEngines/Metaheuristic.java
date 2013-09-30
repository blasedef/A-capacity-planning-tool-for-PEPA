package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.searchEngines;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

public class Metaheuristic {
	
	private IProgressMonitor monitor;
	
	public Metaheuristic (IProgressMonitor monitor){
		this.monitor = monitor;
	}
	
	public SubProgressMonitor getSubProgressMonitor(int i){
		return new SubProgressMonitor(this.monitor,i);
	}
	
	public void search(int i, int j){
		
		IProgressMonitor m = getSubProgressMonitor(i);
		
		try{
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			m.beginTask("some meta", j);
			m.subTask("busy little bee");
			System.out.println("busy little bee");
			m.worked(1);
			
		} finally {
			m.done();
		}
	}
	
}