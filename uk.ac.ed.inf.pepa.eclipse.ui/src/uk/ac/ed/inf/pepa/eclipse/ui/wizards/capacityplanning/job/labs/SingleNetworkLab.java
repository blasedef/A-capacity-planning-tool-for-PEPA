package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;



import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;

public abstract class SingleNetworkLab extends Lab {

	public SingleNetworkLab(LabParameters labParameters,
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
	
	public FitnessFunction getSystemEquationFitnessFunction(Recorder recorder){
		
		FitnessFunction fitnessFunction = new SystemEquationFitnessFunction(fitnessFunctionParameters,
				monitor,
				recorder);
		
		return fitnessFunction;
	}
	
}
