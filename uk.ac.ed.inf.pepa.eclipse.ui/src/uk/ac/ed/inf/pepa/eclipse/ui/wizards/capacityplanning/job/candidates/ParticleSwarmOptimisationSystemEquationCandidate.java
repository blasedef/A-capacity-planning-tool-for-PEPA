package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.ParticleSwarmOptimsationSystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.SystemEquationFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class ParticleSwarmOptimisationSystemEquationCandidate extends SystemEquationCandidate {

	protected PriorityQueue<Candidate> personalBestQueue;
	protected HashMap<String,Double> velocityVector;
	
	public ParticleSwarmOptimisationSystemEquationCandidate(int i,
			FitnessFunction fitnessFunction,
			HashMap<String,Double> minimumPopulation, 
			HashMap<String,Double> maximumPopulation){
		super(i, fitnessFunction, minimumPopulation, maximumPopulation);
		
		this.personalBestQueue = new PriorityQueue<Candidate>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean offer(Candidate e) {
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}

			@Override
			public boolean add(Candidate e) {
				
				
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
		};
		
		this.velocityVector = createVelocityVector();
		
	}
	
	public  HashMap<String,Double> createVelocityVector() {
		
		HashMap<String,Double> newMap = Tool.copyHashMap(candidateMap);
		
		//'10%' of user system equations make it into the original population
		if(Tool.rollDice(0.9)){
			for(Entry<String, Double> entry : newMap.entrySet()){
				Double min = minimumPopulation.get(entry.getKey()).doubleValue();
				Double max = maximumPopulation.get(entry.getKey()).doubleValue();
				Double d = Tool.returnRandomInRange(min, max, Config.INTEGER);
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
				Tool.copyHashMap(minimumPopulation),
				Tool.copyHashMap(maximumPopulation));
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setFitness(this.fitness);
		((SystemEquationCandidate) temp).setPerformanceResultMap(performanceResultsMap);
		temp.updateCreatedTime();
		temp.updateName();
		return temp;
	}
	
	@Override
	public void updateFitness(){
		this.fitness = ((ParticleSwarmOptimsationSystemEquationFitnessFunction) fitnessFunction).getFitness(candidateMap,this.maximumPopulation);
		this.performanceResultsMap = ((SystemEquationFitnessFunction) this.fitnessFunction).getPerformanceResultsMap();
		this.personalBestQueue.add(this.copySelf());
	}
	
	@Override
	public void setVelocity(Candidate globalBest,
			double originalVelocityWeight, 
			double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		
		Candidate personalBestCandidate = this.personalBestQueue.peek();
		
		HashMap<String,Double> personalBestCandidateMap = Tool.copyHashMap(personalBestCandidate.getCandidateMap());
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
			Double temp = (candidateMap.get(entry.getKey()) + velocityVector.get(entry.getKey()));
			if(temp > 0){
				candidateMap.put(entry.getKey(), temp);
			} else {
				candidateMap.put(entry.getKey(), 1.0);
			}
		}
	}

}
