package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class MetaHeuristicPopulation {
	
	private ArrayList<ModelObject> mPopulation;
	private boolean isCanceled = false;

	public MetaHeuristicPopulation() {
		this.mPopulation = new ArrayList<ModelObject>();
	}
	
	/**
	 * initialise the population, and start the evolution
	 * @param monitor
	 * @return
	 */
	public IStatus initialise(final IProgressMonitor monitor){
		
		IStatus test = null;
		monitor.beginTask(null, IProgressMonitor.UNKNOWN);
		
		//setup best
		CPAParameters.best = new ModelObject(monitor);
		//CPAParameters.source += CPAParameters.best.toString();
		
		//Make the candidate population even :)
		//bit cheeky, but saves having to write more UI!
		if(!((CPAParameters.candidatePopulationSize%2)==0)){
			CPAParameters.candidatePopulationSize++;
		}
		
		//setup working ModelObject(s)
		for(int i = 0; i < CPAParameters.candidatePopulationSize; i++){
			ModelObject temp = new ModelObject(monitor);
			//randomize the population seed, but keep one of them as original
			if(i < (CPAParameters.candidatePopulationSize -1)){
				temp.mutateMe();
			}
			this.mPopulation.add(temp);
		}
		
		
		//Go into repeating Queue
		test = this.generator(CPAParameters.metaheuristicParameters.get("Generations:").intValue(), monitor);
		
		monitor.done();
		return test;
		
	}
	
	/**
	 * Evolution generator
	 * @param generations
	 * @param monitor
	 * @return
	 */
	public IStatus generator(int generations, IProgressMonitor monitor){
		
		while(generations > 0){
			
			//cancel options
			if (monitor.isCanceled() == true) {
				this.isCanceled = true;
				break;
			}
			
			//Do hill climbing
			if(CPAParameters.performanceRequirementChoice == 0){
				for(int i = 0; i < CPAParameters.candidatePopulationSize; i++){
					ModelObject temp = this.mPopulation.get(i);
					if(temp.getFitness() < CPAParameters.best.getFitness()){
						CPAParameters.best.setModelObject(temp.getSystemEquation());
						CPAParameters.source += (CPAParameters.metaheuristicParameters.get("Generations:").intValue() - generations) + "," + CPAParameters.best.toString();
					}
					temp.mutateMe();
				}
				
			//Do Genetic Algorithm
			} else {
				for(int i = 0; i < CPAParameters.candidatePopulationSize; i++){
					
					ModelObject temp = this.mPopulation.get(i);
					if(temp.getFitness() < CPAParameters.best.getFitness()){
						CPAParameters.best.setModelObject(temp.getSystemEquation());
						CPAParameters.source += (CPAParameters.metaheuristicParameters.get("Generations:").intValue() - generations) + "," + CPAParameters.best.toString();
					}
					temp.mutateMe();
					
				}
				ArrayList<ModelObject> q = new ArrayList<ModelObject>();
				for(int i = 0; i < CPAParameters.candidatePopulationSize; i++){
					
					ModelObject parentA;
					ModelObject parentB;
					
					//select A from pile
					//select B from pile
					//cross over parents to produce two children
					//mutate both children
					//put children into 'q'
					//replace population with 'q'
				}
			}
				
				
			generations--;
		}
		
		if (!isCanceled) {
			
			return Status.OK_STATUS;

		} else
			return Status.CANCEL_STATUS;
	}
	
	public void mutateAll(){
		for(ModelObject m : this.mPopulation){
			m.mutateMe();
		}
	}
	

	public String toString() {
		String temp = "";
		for(ModelObject m : this.mPopulation){
			temp += m.toString();
		}
		return temp;
	}
	
}
