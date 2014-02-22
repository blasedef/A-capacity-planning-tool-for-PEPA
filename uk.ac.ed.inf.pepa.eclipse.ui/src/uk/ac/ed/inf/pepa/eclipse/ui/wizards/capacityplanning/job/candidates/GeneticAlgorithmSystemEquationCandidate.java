package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class GeneticAlgorithmSystemEquationCandidate extends SystemEquationCandidate {

	public GeneticAlgorithmSystemEquationCandidate(int i,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super(i, fitnessFunction, candidateParameters);
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new GeneticAlgorithmSystemEquationCandidate(this.generation,
				this.fitnessFunction.copySelf(),
				candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		((SystemEquationCandidate) temp).setPerformanceResultMap(performanceResultsMap);
		temp.updateCreatedTime();
		return temp;
	}
	
	@Override
	public void mutate(double probability) {
		
		for(Entry<String, Double> entry : candidateMap.entrySet()){
			if(Tool.rollDice(probability)){
				Double min = minimumPopulation.get(entry.getKey()).doubleValue();
				Double max = maximumPopulation.get(entry.getKey()).doubleValue();
				Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
				candidateMap.put(entry.getKey(), d);
			}
		}
			
	}
	
	@Override
	public void crossOver(Candidate candidate, double probability) {
		
		if(Tool.rollDice(probability)){
			int split = Tool.returnRandomInRange(0.0, ((Integer) candidateMap.size()).doubleValue(), Config.NATURAL).intValue();
			
			HashMap<String,Double> candidateMapA = Tool.copyHashMap(this.getCandidateMap());
			HashMap<String,Double> candidateMapB = Tool.copyHashMap(candidate.getCandidateMap());
			
			String[] options = candidateMapA.keySet().toArray(new String[0]);
			
			for(int i = 0; i < split; i++){
				Double temp = candidateMapA.get(options[i]);
				candidateMapA.put(options[i], candidateMapB.get(options[i]));
				candidateMapB.put(options[i],temp);
			}
			
			this.candidateMap = candidateMapA;
			candidate.setCandidateMap(candidateMapB);
			
		}
		
		
	}

}
