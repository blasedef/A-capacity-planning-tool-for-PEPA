package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.threadAndSubprogressExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Configuration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;

public class MetaHeuristicJob extends Job{
	
	int processors;

	public MetaHeuristicJob(String name) {
		super(name);
		
		this.processors = Runtime.getRuntime().availableProcessors();
		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		ExecutorService executor = Executors.newFixedThreadPool(this.processors - 1);
		
		try{
			monitor.beginTask("test", 4);
		
			for(Configuration c : ExperimentConfiguration.performanceEvaluatorAndMetaheuristic){
				if(monitor.isCanceled())
						throw new OperationCanceledException();
				try {
					Thread.sleep(1);
					SmallJobTest smallJob = new SmallJobTest(c, new SubProgressMonitor(monitor,1));
					executor.execute(smallJob);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			monitor.done();
		}
		
		executor.shutdown();
		
		return Status.OK_STATUS;
	}
	
}