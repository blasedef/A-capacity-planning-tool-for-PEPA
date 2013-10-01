package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.AnalysisOfFluidSteadyState;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.NodeHandler;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Reporting;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Tools;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;


public class SystemEquation extends Candidate {
	
	private double created;
	private NodeHandler myNode;
	private HashMap<String, Double> mySystemEquation = new HashMap<String, Double>();
	private IProgressMonitor monitor;
	
	public SystemEquation(IProgressMonitor monitor){
		this.created = Reporting.createdTime();
		//def my own copy...
		this.myNode = new NodeHandler();
		this.mySystemEquation = this.myNode.getSystemEquation();
		this.monitor = monitor;
	}
	
	public Map<String, Double> getMap(){
		return this.mySystemEquation;
	}

	@Override
	public Candidate Crossover(Candidate candidate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate() {
		
		if(Tools.rollDice(ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.MUTATIONPROBABILITY_S).doubleValue())){
			for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
				Double min = ExperimentConfiguration.metaHeuristic.getMinPopMap().get(entry.getKey()).doubleValue();
				Double max = ExperimentConfiguration.metaHeuristic.getMaxPopMap().get(entry.getKey()).doubleValue();
				Double d = Tools.returnRandomInRange(min, max, ExperimentConfiguration.INTEGER);
				this.mySystemEquation.put(entry.getKey(),d);
			}
			
			this.myNode.setSystemEquation(this.mySystemEquation);
		}
		
		this.mySystemEquation = myNode.getSystemEquation();
		Reporting.reportSystemEquation(this);
		
	}

	@Override
	public Double getTotalFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> getPerformanceFitness() {
		
		AnalysisOfFluidSteadyState aOSS = new AnalysisOfFluidSteadyState(this.myNode.getGraph(null), 
				ExperimentConfiguration.oDEConfig.getOptionMap(), 
				ExperimentConfiguration.oDEConfig.getEstimators(), 
				ExperimentConfiguration.oDEConfig.getCollectors(), 
				ExperimentConfiguration.oDEConfig.getLabels(), 
				new SubProgressMonitor(monitor,10));
		
		return aOSS.getResults();
	}

	@Override
	public Double[] getPopulationFitness() {
		
		return null;
	}

	@Override
	public void initialise() {
		
		//'10%' of user system equations make it into the original population
		if(Tools.rollDice(0.1)){
			for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
				Double min = ExperimentConfiguration.metaHeuristic.getMinPopMap().get(entry.getKey()).doubleValue();
				Double max = ExperimentConfiguration.metaHeuristic.getMaxPopMap().get(entry.getKey()).doubleValue();
				Double d = Tools.returnRandomInRange(min, max, ExperimentConfiguration.INTEGER);
				this.mySystemEquation.put(entry.getKey(),d);
			}
			
			this.myNode.setSystemEquation(this.mySystemEquation);
		}
		
		this.mySystemEquation = myNode.getSystemEquation();
		Reporting.reportSystemEquation(this);
		
	}

	@Override
	public void newVector() {
		// TODO Auto-generated method stub
		
	}

	public Double getCreated() {
		return this.created;
	}
	
	@Override
	public void updateFitness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		for(Map.Entry<String, Double> entry : myNode.getSystemEquation().entrySet()){
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
		Map<String, Double> results = getPerformanceFitness();
		
//		for(Map.Entry<String, Double> entry : results.entrySet()){
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
		
//		mutate();
//		
//		for(Map.Entry<String, Double> entry : myNode.getSystemEquation().entrySet()){
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
		
//		results = getPerformanceFitness();
		
//		for(Map.Entry<String, Double> entry : results.entrySet()){
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
		
	}
	
}