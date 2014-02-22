package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;

import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.HillClimbingLabCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.HillClimbing;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.Metaheuristic;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.LabRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;

public class DrivenNetworkHillClimbingLab extends DrivenNetworkLab {

	public DrivenNetworkHillClimbingLab(LabParameters primaryLabParameters,
			LabParameters secondarylabParameters,
			RecordParameters recordParameters,
			MetaHeuristicParameters primaryMetaheuristicParameters,
			MetaHeuristicParameters secondaryMetaheuristicParameters,
			FitnessFunctionParameters fitnessFunctionParameters,
			CandidateParameters candidateParameters) {
		super(primaryLabParameters, 
				secondarylabParameters, 
				recordParameters, 
				primaryMetaheuristicParameters, 
				secondaryMetaheuristicParameters,
				fitnessFunctionParameters, 
				candidateParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Metaheuristic setupLab(IProgressMonitor monitor){
		
		Recorder temp = new LabRecorder(recordParameters);
		
		return ((Metaheuristic) new HillClimbing(metaheuristicParameters, 
				getLabCandidate(temp), 
				monitor,
				temp));
		
	}

	public Candidate getLabCandidate(Recorder recorder){
		return (Candidate) new HillClimbingLabCandidate(0, 
				getLabFitnessFunction(recorder),
				candidateParameters);
	}



}
