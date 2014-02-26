package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class HillClimbingLabCandidate extends LabCandidate {
	
	public HillClimbingLabCandidate(int i,
			LabParameters labParameters,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super(i, labParameters, fitnessFunction, candidateParameters);
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new HillClimbingLabCandidate(this.generation,
				this.labParameters,
				this.fitnessFunction.copySelf(),
				candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		return temp;
	}
	
	@Override
	public void mutate(double probability) {
		
		Double minP = 0.0;
		Double maxP = 1.0;
		Double minI = 1.0;
		Double maxI = 100.0;
		
		if(labParameters.getSecondaryMetaheuristicType().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)){
			
			if(Tool.rollDice(probability)){
				Double d = Tool.returnRandomInRange(minP, maxP, Config.PERCENT);
				this.getCandidateMap().put(Config.MUTATIONPROBABILITY_S, d);
			}
			
			if(Tool.rollDice(probability)){
				Double d = Tool.returnRandomInRange(minP, maxP, Config.PERCENT);
				this.getCandidateMap().put(Config.CROSSOVERPROBABILITY_S, d);
			}
			
			if(Tool.rollDice(probability)){
				Double d = Tool.returnRandomInRange(minI, maxI, Config.NATURAL);
				this.getCandidateMap().put(Config.INITIALCANDIDATEPOPULATION_S, d);
			}
		} 
		
		if(labParameters.getSecondaryMetaheuristicType().equals(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S)){
			
			Double d = Tool.returnRandomInRange(minP, maxP, Config.PERCENT);
			Double e = Tool.returnRandomInRange(0.0, maxP-d, Config.PERCENT);
			Double f = 1.0 - (e+d);
			Double g = Tool.returnRandomInRange(minI, maxI, Config.NATURAL);
			
			if(Tool.rollDice(probability)){
				this.getCandidateMap().put(Config.PERSONALBEST, d);
			}
			
			if(Tool.rollDice(probability)){
				this.getCandidateMap().put(Config.GLOBALBEST, e);
			}
			
			if(Tool.rollDice(probability)){
				this.getCandidateMap().put(Config.ORIGINALVELO, f);
			}
			
			if(Tool.rollDice(probability)){
				this.getCandidateMap().put(Config.INITIALCANDIDATEPOPULATION_S, g);
			}			
		}
		

		Double d = Tool.returnRandomInRange(minI, maxI, Config.NATURAL);
		this.getCandidateMap().put(Config.GENERATION_S, d);
		
	}
	
	@Override
	public void scatter() {
		this.mutate(1.0);
		
	}

}
