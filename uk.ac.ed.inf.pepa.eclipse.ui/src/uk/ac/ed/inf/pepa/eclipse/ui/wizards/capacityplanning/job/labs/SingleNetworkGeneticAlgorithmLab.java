package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;


import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.GeneticAlgorithmSystemEquationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;

public class SingleNetworkGeneticAlgorithmLab extends SingleNetworkLab {

	public SingleNetworkGeneticAlgorithmLab(LabParameters labParameters,
			RecordParameters recordParameters,
			MetaHeuristicParameters metaheuristicParameters,
			FitnessFunctionParameters fitnessFunctionParameters,
			CandidateParameters candidateParameters) {
		super(labParameters,
				recordParameters,
				metaheuristicParameters,
				fitnessFunctionParameters,
				candidateParameters);
	}
	
	@Override
	public Metaheuristic setupLab(IProgressMonitor monitor){
		
		Recorder temp = new SystemEquationRecorder(recordParameters, metaheuristicParameters.copySelf());
		
		return ((Metaheuristic) new GeneticAlgorithm(metaheuristicParameters, 
				getSystemEquationCandidate(temp), 
				monitor,
				temp));
		
	}
	
	public Candidate getSystemEquationCandidate(Recorder recorder){
		return (Candidate) new GeneticAlgorithmSystemEquationCandidate(0, 
				getSystemEquationFitnessFunction(recorder),
				candidateParameters);
	}

}
