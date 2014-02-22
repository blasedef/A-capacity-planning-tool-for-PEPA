package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.ParticleSwarmOptimsationSystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class ParticleSwarmOptimisationSystemEquationCandidate extends SystemEquationCandidate {

	protected Candidate personalBest;
	protected HashMap<String,Double> velocityVector;
	
	public ParticleSwarmOptimisationSystemEquationCandidate(int i,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super(i, fitnessFunction, candidateParameters);
		
		this.velocityVector = createVelocityVector();
		this.personalBest = new ParticleSwarmOptimisationSystemEquationCandidate();
		
	}
	
	public ParticleSwarmOptimisationSystemEquationCandidate(){
		super();
	}
	
	public  HashMap<String,Double> createVelocityVector() {
		
		HashMap<String,Double> newMap = Tool.copyHashMap(candidateMap);
		
		//'10%' of user system equations make it into the original population
		if(Tool.rollDice(0.9)){
			for(Entry<String, Double> entry : newMap.entrySet()){
				Double min = minimumPopulation.get(entry.getKey()).doubleValue();
				Double max = maximumPopulation.get(entry.getKey()).doubleValue();
				Double d = Tool.returnRandomInRange(min, max, Config.NATURAL);
				newMap.put(entry.getKey(),d);
			}
		}
		
		for(Entry<String, Double> entry : newMap.entrySet()){
			Double positionElement1 = entry.getValue();
			Double positionElement2 = candidateMap.get(entry.getKey());
			newMap.put(entry.getKey(),(positionElement2 - positionElement1));
		}
		
		return newMap;
		
	}
	
	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new ParticleSwarmOptimisationSystemEquationCandidate(this.generation,
				this.fitnessFunction.copySelf(),
				candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		((SystemEquationCandidate) temp).setPerformanceResultMap(performanceResultsMap);
		temp.updateCreatedTime();
		return temp;
	}
	
	@Override
	public void updateFitness(){
		this.fitness = ((ParticleSwarmOptimsationSystemEquationFitnessFunction) fitnessFunction).getFitness(candidateMap,this.maximumPopulation);
		this.performanceResultsMap = ((SystemEquationFitnessFunction) this.fitnessFunction).getPerformanceResultsMap();
		if(this.fitness < this.personalBest.getFitness())
			this.personalBest.setFitness(this.fitness);
			this.personalBest.candidateMap = Tool.copyHashMap(this.candidateMap);
	}
	
	@Override
	public void setVelocity(Candidate globalBest,
			double originalVelocityWeight, 
			double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		
		HashMap<String,Double> personalBestCandidateMap = Tool.copyHashMap(this.personalBest.getCandidateMap());
		HashMap<String,Double> globalBestCandidateMap = Tool.copyHashMap(globalBest.getCandidateMap());
		HashMap<String,Double> originalVelocityMap = Tool.copyHashMap(this.velocityVector);
		
		this.velocityVector = this.getVelocity(originalVelocityWeight, 
				personalBestVelocityWeight, 
				globalBestVelocityWeight,
				originalVelocityMap,
				personalBestCandidateMap, 
				globalBestCandidateMap,
				Tool.copyHashMap(candidateMap));
		
		
		updateVelocity(this.velocityVector);
		
	}
	
	public HashMap<String,Double> getVelocity(Double o, Double p, Double g, 
			HashMap<String,Double> original, 
			HashMap<String,Double> personal,
			HashMap<String,Double> global,
			HashMap<String,Double> candidate){
		
		for(Entry<String, Double> entry : original.entrySet()){
			Double temp = entry.getValue() * o;
			original.put(entry.getKey(), temp);
		}
		
		for(Entry<String, Double> entry : candidate.entrySet()){
			Double temp = p * (personal.get(entry.getKey()) - entry.getValue());
			personal.put(entry.getKey(), temp);
		}
		
		for(Entry<String, Double> entry : candidate.entrySet()){
			Double temp = g * (global.get(entry.getKey()) - entry.getValue());
			global.put(entry.getKey(), temp);
		}
		
		for(Entry<String, Double> entry : original.entrySet()){
			Double temp = Math.rint(entry.getValue() + personal.get(entry.getKey()) + global.get(entry.getKey()));
			original.put(entry.getKey(), temp);
		}
		
		return Tool.copyHashMap(original);
		
	}
	
	public void updateVelocity(HashMap<String,Double> velocityVector){
		
		for(Entry<String, Double> entry : candidateMap.entrySet()){
			Double step = Tool.returnRandomInRange(1.0, 1.0, Config.NATURAL)*velocityVector.get(entry.getKey());
			Double temp = (candidateMap.get(entry.getKey()) + step);
			
			while(temp > this.maximumPopulation.get(entry.getKey()) || temp < this.minimumPopulation.get(entry.getKey())){
				temp = this.wrapping(temp, this.minimumPopulation.get(entry.getKey()), this.maximumPopulation.get(entry.getKey()) );
			}
			
			if(temp == 0){
				candidateMap.put(entry.getKey(), 1.0);
			} else if (temp < this.minimumPopulation.get(entry.getKey())){
				
				temp = this.minimumPopulation.get(entry.getKey()) + Math.abs(temp);
				
				candidateMap.put(entry.getKey(),temp);
			} else if (temp > this.maximumPopulation.get(entry.getKey())){
				
				temp = this.maximumPopulation.get(entry.getKey()) - temp;
				
				candidateMap.put(entry.getKey(),temp);
			} else {
				
			}
			candidateMap.put(entry.getKey(),temp);
		}
	}
	
	private double wrapping(double x, double bottom, double top){
			if(x == 0.0){
				return 1.0;
			} else if ( x <= bottom){
				return bottom + Math.abs(x) + 1;
			} else if ( x >= top){
				return top - x + 1;
			} else {
				return x;
			}
	}

}
