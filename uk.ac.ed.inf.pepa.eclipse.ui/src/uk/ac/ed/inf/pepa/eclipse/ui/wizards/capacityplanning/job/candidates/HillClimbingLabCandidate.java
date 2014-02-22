package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class HillClimbingLabCandidate extends LabCandidate {
	
	public HillClimbingLabCandidate(int i,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super(i, fitnessFunction, candidateParameters);
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new HillClimbingLabCandidate(this.generation,
				this.fitnessFunction.copySelf(),
				candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		return temp;
	}
	
	@Override
	public void mutate(double probability) {
		
		Double min = 1.0;
		Double max = 10.0;
		Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
		this.getCandidateMap().put(Config.GENERATION_S, d);
		
	}

}
