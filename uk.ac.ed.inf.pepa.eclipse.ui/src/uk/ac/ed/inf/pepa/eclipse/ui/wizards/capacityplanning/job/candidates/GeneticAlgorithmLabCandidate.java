package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class GeneticAlgorithmLabCandidate extends LabCandidate {
	
	public GeneticAlgorithmLabCandidate(int i,
			LabParameters labParameters,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super(i, labParameters, fitnessFunction, candidateParameters);
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new GeneticAlgorithmLabCandidate(this.generation,
				this.labParameters,
				this.fitnessFunction.copySelf(),
				candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		return temp;
	}
	
	@Override
	public void mutate(double probability) {
		
		Double min = 1.0;
		Double max = 100.0;
		Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
		this.getCandidateMap().put(Config.GENERATION_S, d);
		
	}
	
	@Override
	public void scatter() {
		this.mutate(1.0);
		
	}

}
