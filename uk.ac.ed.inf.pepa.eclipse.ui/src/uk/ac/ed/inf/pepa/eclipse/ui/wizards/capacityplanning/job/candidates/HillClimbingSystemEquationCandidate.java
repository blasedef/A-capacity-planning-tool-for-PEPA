package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.SystemEquationRecorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class HillClimbingSystemEquationCandidate extends SystemEquationCandidate {

	public HillClimbingSystemEquationCandidate(int i,
			FitnessFunction fitnessFunction,
			HashMap<String,Double> minimumPopulation, 
			HashMap<String,Double> maximumPopulation){
		super(i, fitnessFunction, minimumPopulation, maximumPopulation);
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new HillClimbingSystemEquationCandidate(this.generation,
				this.fitnessFunction.copySelf(),
				Tool.copyHashMap(minimumPopulation),
				Tool.copyHashMap(maximumPopulation));
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		((SystemEquationCandidate) temp).setPerformanceResultMap(performanceResultsMap);
		temp.updateCreatedTime();
		return temp;
	}
	

	@Override
	public void mutate(double probability) {
		
		int maximumSearchSpaceSize = ((SystemEquationFitnessFunction) fitnessFunction).getMaxSearchSpace();

		if(((SystemEquationRecorder) recorder).getCandidateMapToFitnessHash().size() < maximumSearchSpaceSize){
			
			for(Entry<String, Double> entry : candidateMap.entrySet()){
				
				if(Tool.rollDice(probability)){
					Double min = minimumPopulation.get(entry.getKey()).doubleValue();
					Double max = maximumPopulation.get(entry.getKey()).doubleValue();
					Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
					candidateMap.put(entry.getKey(), d);
				}
			
			}
		
		}	
	}

}
