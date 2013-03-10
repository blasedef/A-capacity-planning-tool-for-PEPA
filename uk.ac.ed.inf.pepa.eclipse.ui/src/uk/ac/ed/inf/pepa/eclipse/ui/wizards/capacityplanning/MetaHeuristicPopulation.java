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
	
	public IStatus initialise(final IProgressMonitor monitor){
		
		IStatus test = null;
		monitor.beginTask(null, IProgressMonitor.UNKNOWN);
		
		//setup best
		CapacityPlanningAnalysisParameters.best = new ModelObject(monitor);
		CapacityPlanningAnalysisParameters.source += CapacityPlanningAnalysisParameters.best.toString();
		
		//setup working ModelObject(s)
		for(int i = 0; i < CapacityPlanningAnalysisParameters.candidatePopulationSize; i++){
			ModelObject temp = new ModelObject(monitor);
			this.mPopulation.add(temp);
		}
		
		//Go into repeating Queue
		test = this.generator(CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Generations:").intValue(), monitor);
		
		monitor.done();
		return test;
		
	}
	
	public IStatus generator(int generations, IProgressMonitor monitor){
		
		while(generations > 0){
			
			if (monitor.isCanceled() == true) {
				this.isCanceled = true;
				break;
			}
			
			for(int i = 0; i < CapacityPlanningAnalysisParameters.candidatePopulationSize; i++){
				ModelObject temp = this.mPopulation.get(i);
				temp.mutateMe();
				if(temp.getFitness() < CapacityPlanningAnalysisParameters.best.getFitness()){
					CapacityPlanningAnalysisParameters.best.setModelObject(temp.getSystemEquation());
					CapacityPlanningAnalysisParameters.source += CapacityPlanningAnalysisParameters.best.toString();
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
