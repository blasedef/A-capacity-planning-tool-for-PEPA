package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates.Experiment;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Reporter;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;

public class MetaHeuristicJob extends Job{
	
	private int totalWork;
	public static Reporter reporter;

	public MetaHeuristicJob(String name) {
		super(name);
		MetaHeuristicJob.reporter = new Reporter();
		this.postProcessing();
		
		/*
		 * SINGLE = Experiments * Candidates * Generation * Analysis
		 * PIPE = Experiments * Candidates * Generation + Experiments' * Candidates' * Generation' * Analysis
		 * DRIVEN = Experiments * (Experiments * Candidates * Generation) * Generation * Analysis
		 * 
		 * Analysis = a little extra added on so that the monitor can progress during the analysis phase
		 * 
		 */
		
		if(ExperimentConfiguration.networkType.getValue().equals(ExperimentConfiguration.NETWORKSINGLE_S)){
			int experiments = ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.EXPERIMENTS_S).intValue();
			int candidates = ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S).intValue();
			int generation = ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.GENERATION_S).intValue();
			this.totalWork = experiments * candidates * generation * 10;
		}
		
	}

	private void postProcessing() {
		
		//So we can treat Hill climbing as a Genetic Algorithm with one candidate and no crossover...
		if(!ExperimentConfiguration.metaHeuristic.getAttributeMap().containsKey(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S))
			ExperimentConfiguration.metaHeuristic.getAttributeMap().put(ExperimentConfiguration.INITIALCANDIDATEPOPULATION_S,1.0);
		
		if(!ExperimentConfiguration.metaHeuristic.getAttributeMap().containsKey(ExperimentConfiguration.CROSSOVERPROBABILITY_S))
			ExperimentConfiguration.metaHeuristic.getAttributeMap().put(ExperimentConfiguration.CROSSOVERPROBABILITY_S,0.0);
			
		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		IStatus status = (new Experiment(monitor, this.totalWork, false)).startExperiments();
		
		System.out.println(MetaHeuristicJob.reporter.reportRunTime());
		
		return status;
	}
	
}