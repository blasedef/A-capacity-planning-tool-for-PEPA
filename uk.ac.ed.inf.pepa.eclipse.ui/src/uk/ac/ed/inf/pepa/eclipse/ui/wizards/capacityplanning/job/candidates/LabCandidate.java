package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions.LabFitnessFunction;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.CandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;

public class LabCandidate extends Candidate {
	
	protected CandidateParameters candidateParameters;
	protected String name;
	protected LabParameters labParameters;
	protected String bestName;
	protected Double bestSystemEquationFitness;
	protected Double bestFitness;
	protected HashMap<String,Double> bestPerformanceResultsMap;
	protected CandidateParameters bestSystemEquationCandidateParameters;
	
	public LabCandidate(int i,
			LabParameters labParameters,
			FitnessFunction fitnessFunction,
			CandidateParameters candidateParameters){
		super();
		
		this.candidateParameters = candidateParameters;
		this.labParameters = labParameters;
		this.fitnessFunction = (LabFitnessFunction) fitnessFunction;
		this.setCandidateMap(((LabFitnessFunction) fitnessFunction).getCandidateMap());
		this.name = "";
		this.bestPerformanceResultsMap = new HashMap<String,Double>();
	}

	@Override
	public int compare(Candidate arg0, Candidate arg1) {
		
		if(arg0.getFitness() > arg1.getFitness()){
			return -1;
		}
		if(arg0.getFitness() < arg1.getFitness()){
			return 1;
		}
		return 0;
	}

	@Override
	public int compareTo(Candidate arg0) {
		
		if(this.fitness > arg0.getFitness()){
			return -1;
		}
		if(this.fitness < arg0.getFitness()){
			return 1;
		}
		return 0;
	}

	@Override
	public Candidate copySelf() {
		Candidate temp = (Candidate) new LabCandidate(this.generation,
				this.labParameters,
				this.fitnessFunction.copySelf(),
				this.candidateParameters);
		temp.setCandidateMap(Tool.copyHashMap(this.candidateMap));
		temp.setPerformanceResultMap(this.bestPerformanceResultsMap);
		temp.setFitness(this.fitness);
		((LabCandidate) temp).setName(this.name);
		return temp;
	}

	@Override
	public void crossOver(Candidate candidate, double probability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Candidate){
			Candidate candidate = (Candidate) obj;
			if(super.getHashCode() == candidate.getHashCode()){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void mutate(double probability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scatter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCandidateMap(HashMap<String, Double> candidateMap) {
		this.candidateMap = Tool.copyHashMap(candidateMap);
		
	}

	@Override
	public void setVelocity(Candidate globalBestCandidate,
			double originalVelocityWeight, double personalBestVelocityWeight,
			double globalBestVelocityWeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCandidateMapFromAST() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHashCode() {
		this.hashCode = this.name.hashCode();
		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String getName() {
		
		return this.name;
		
	}
	
	@Override
	public void updateFitness(){
		this.fitness = fitnessFunction.getFitness(candidateMap);
		ArrayList<Candidate> temp = ((LabFitnessFunction) fitnessFunction).getTop();
		
		this.bestSystemEquationFitness = temp.get(temp.size() - 1).getFitness();
		this.bestPerformanceResultsMap = Tool.copyHashMap((temp.get(temp.size() - 1)).getPerformanceResultMap());
		this.bestName = temp.get(temp.size() - 1).getName();
		this.bestFitness = temp.get(temp.size() - 1).getFitness();
		
		
		this.setName(super.getName() + "@" + this.bestName + " @ \"fitness\":" + this.bestFitness);
	}
	
	public ArrayList<Candidate> getTop(){
		return ((LabFitnessFunction) fitnessFunction).getTop();
	}

	@Override
	public HashMap<String, Double> getPerformanceResultMap() {
		return this.bestPerformanceResultsMap;
	}
	
	public void setPerformanceResultMap(HashMap<String,Double> performanceResultsMap){
		this.bestPerformanceResultsMap = Tool.copyHashMap(performanceResultsMap);
	}
	
}
