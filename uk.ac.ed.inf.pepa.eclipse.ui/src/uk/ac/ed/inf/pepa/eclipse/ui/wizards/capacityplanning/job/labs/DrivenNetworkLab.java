package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs;


import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.LabFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.FitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;

public abstract class DrivenNetworkLab extends Lab {
	
	protected LabParameters secondaryLabParameters;
	protected MetaHeuristicParameters secondaryMetaheuristicParameters;

	public DrivenNetworkLab(LabParameters primaryLabParameters,
			LabParameters secondarylabParameters,
			RecordParameters recordParameters,
			MetaHeuristicParameters primaryMetaheuristicParameters,
			MetaHeuristicParameters secondaryMetaheuristicParameters,
			FitnessFunctionParameters fitnessFunctionParameters,
			CandidateParameters candidateParameters) {
		
		super(primaryLabParameters, recordParameters, primaryMetaheuristicParameters,
				fitnessFunctionParameters, candidateParameters);
		
		this.secondaryLabParameters = secondarylabParameters;
		this.secondaryMetaheuristicParameters = secondaryMetaheuristicParameters;
		
		
		// TODO Auto-generated constructor stub
	}

	public FitnessFunction getLabFitnessFunction(Recorder recorder){
		
		FitnessFunction fitnessFunction = new LabFitnessFunction(recorder,
				secondaryLabParameters,
				recordParameters,
				secondaryMetaheuristicParameters,
				fitnessFunctionParameters,
				candidateParameters,
				monitor);
		
		return fitnessFunction;
	}
	
}
