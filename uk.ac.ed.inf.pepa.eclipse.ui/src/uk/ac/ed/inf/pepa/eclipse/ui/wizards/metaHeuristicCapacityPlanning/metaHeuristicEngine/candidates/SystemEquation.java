package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.candidates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.AnalysisOfFluidSteadyState;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.NodeHandler;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Reporter;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools.Tools;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;


public class SystemEquation extends Candidate {
	
	private double created;
	private NodeHandler myNode;
	private HashMap<String, Double> mySystemEquation = new HashMap<String, Double>();
	private IProgressMonitor monitor;
	Reporter reporter;
	private HashMap<String, Double> performanceFitness;
	private HashMap<String, Double> populationFitness;
	private Double totalPopulationFitness;
	private Double totalPerformanceFitness;
	private Double myFitness;
	private Double maximumPopulationSize;
	
	public SystemEquation(IProgressMonitor monitor, Reporter reporter){
		this.created = reporter.createdTime();
		//def my own copy...
		this.myNode = new NodeHandler();
		this.mySystemEquation = this.myNode.getSystemEquation();
		this.monitor = monitor;
		this.reporter = reporter;
		this.totalPerformanceFitness = 100000.0;
		setMaximumpopulationSize();
		initialise();
	}
	
	public void setMaximumpopulationSize(){
		this.maximumPopulationSize = 1.0;
		for(Map.Entry<String, Double> entry : ExperimentConfiguration.metaHeuristic.getComponentRange().entrySet()){
			this.maximumPopulationSize *= entry.getValue(); 
		}
	}
	
	public Map<String, Double> getMap(){
		return this.mySystemEquation;
	}

	@Override
	public void Crossover(Candidate candidate) {
		int split = Tools.returnRandomInRange(0.0, ((Integer) mySystemEquation.size()).doubleValue(), ExperimentConfiguration.INTEGER).intValue();
		SystemEquation parentB = (SystemEquation) candidate;
		
		String[] options = mySystemEquation.keySet().toArray(new String[0]);
		
		for(int i = 0; i < split; i++){
			Double temp = mySystemEquation.get(options[i]);
			mySystemEquation.put(options[i],parentB.mySystemEquation.get(options[i]));
			parentB.mySystemEquation.put(options[i], temp);
		}
		
	}
	
	public HashMap<String, Double> getMutated(){
		
		boolean happened = false;
		HashMap<String, Double> newSystemEquation = getNewMap(this.mySystemEquation);
		
		for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
			if(Tools.rollDice(ExperimentConfiguration.metaHeuristic.getAttributeMap().get(ExperimentConfiguration.MUTATIONPROBABILITY_S).doubleValue())){
				happened = true;
				Double min = ExperimentConfiguration.metaHeuristic.getMinPopMap().get(entry.getKey()).doubleValue();
				Double max = ExperimentConfiguration.metaHeuristic.getMaxPopMap().get(entry.getKey()).doubleValue();
				Double d = Tools.returnRandomInRange(min, max, ExperimentConfiguration.INTEGER);
				newSystemEquation.put(entry.getKey(),d);
			} 
		}
		
		if(happened){		
			return newSystemEquation;
		} else {
			return null;
		}
		
	}
	
	@Override
	public void mutate(boolean isHillClimbing) {
		
		/**
		 * if you have not searched the entire search space...
		 */
		if(this.maximumPopulationSize > this.reporter.getTotalFitness().size()){
			
			HashMap<String, Double> newSystemEquation = getMutated();
			
			if(isHillClimbing){
				if(newSystemEquation == null){
					mutate(true);
				} else {
					String temp = getAttributeString(newSystemEquation);
					if(this.reporter.getTotalFitness().containsKey(temp)){
						mutate(true);
					} else {
						System.out.println("mutation happened! old: " + this.mySystemEquation + " new: " + newSystemEquation);
						this.mySystemEquation = getNewMap(newSystemEquation);
					}
				}
				
			} else {
				if(newSystemEquation != null){
					System.out.println("mutation happened! old: " + this.mySystemEquation + " new: " + newSystemEquation);
					this.mySystemEquation = getNewMap(newSystemEquation);
				}
			}
		}
		
	}

	public void setTotalFitness(){
		Double alpha = ExperimentConfiguration.metaHeuristic.getFitnessMap().get(ExperimentConfiguration.ALPHABETA_S).doubleValue();
		Double beta = 1 - alpha;
		this.myFitness = (alpha * this.totalPerformanceFitness) + (beta * this.totalPopulationFitness);
	}
	
	@Override
	public Double getTotalFitness() {
		return this.myFitness;
	}

	public void setPerformanceFitness() {
		
		AnalysisOfFluidSteadyState aOSS = new AnalysisOfFluidSteadyState(this.myNode.getGraph(this.mySystemEquation), 
				ExperimentConfiguration.oDEConfig.getOptionMap(), 
				ExperimentConfiguration.oDEConfig.getEstimators(), 
				ExperimentConfiguration.oDEConfig.getCollectors(), 
				ExperimentConfiguration.oDEConfig.getLabels(), 
				new SubProgressMonitor(monitor,10));
		
		this.performanceFitness = aOSS.getResults();
	}

	@Override
	public HashMap<String, Double> getPopulationFitness() {
		return this.populationFitness;
	}
	
	public void setPopulationFitness() {
		
		this.populationFitness = new HashMap<String, Double>();
		
		for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
			String component = entry.getKey();
			Double value = entry.getValue();
			Double range = ExperimentConfiguration.metaHeuristic.getComponentRange().get(component);
			Double weight = ((Integer) ExperimentConfiguration.metaHeuristic.getComponentRange().size()).doubleValue(); //so this needs to be in the GUI... bah!!!!
			this.populationFitness.put(component, ((value/range)*100)/weight);
		}
		
	}

	@Override
	public void initialise() {
		
		//'10%' of user system equations make it into the original population
		if(Tools.rollDice(0.9)){
			for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
				Double min = ExperimentConfiguration.metaHeuristic.getMinPopMap().get(entry.getKey()).doubleValue();
				Double max = ExperimentConfiguration.metaHeuristic.getMaxPopMap().get(entry.getKey()).doubleValue();
				Double d = Tools.returnRandomInRange(min, max, ExperimentConfiguration.INTEGER);
				this.mySystemEquation.put(entry.getKey(),d);
			}
			
			this.myNode.setSystemEquation(this.mySystemEquation);
		}
		
		this.mySystemEquation = myNode.getSystemEquation();
		
	}

	@Override
	public void newVector() {
		// TODO Auto-generated method stub
		
	}

	public Double getCreatedTime() {
		return this.created;
	}
	
	@Override
	public void updateFitness() {
		String temp = getAttributeString();
		if(!this.reporter.getTotalFitness().containsKey(temp)){
			setPerformanceFitness();
			setPopulationFitness();
			setTotalPerformanceFitness();
			setTotalPopulationFitness();
			setTotalFitness();
		} else {
			this.myFitness = this.reporter.getTotalFitness().get(temp);
		}
		
	}

	@Override
	public String getAttributeString() {
		String temp = "";
		
		for(Map.Entry<String, Double> entry : mySystemEquation.entrySet()){
			temp += " " + entry.getKey() + "[" + entry.getValue().longValue() + "] ";
		}
		
		return temp;
	}
	
	public String getAttributeString(HashMap<String, Double> map) {
		String temp = "";
		
		for(Map.Entry<String, Double> entry : map.entrySet()){
			temp += " " + entry.getKey() + "[" + entry.getValue().longValue() + "] ";
		}
		
		return temp;
	}

	@Override
	public HashMap<String, Double> getPerformanceFitness() {
		return this.performanceFitness;
	}
	
	@Override
	public void setTotalPerformanceFitness(){
		this.totalPerformanceFitness = 0.0; 
		for(Map.Entry<String, Double> entry : this.performanceFitness.entrySet()){
			Double ode = entry.getValue();
			Double target = ExperimentConfiguration.metaHeuristic.getTargetMap().get(entry.getKey()).doubleValue();
			Double targetWeight = ExperimentConfiguration.metaHeuristic.getTargetWeightMap().get(entry.getKey()).doubleValue();
			this.totalPerformanceFitness += (Math.abs(100 -((ode/target)*100))) * targetWeight;
		}
			
	}
	
	public void setTotalPopulationFitness(){
		this.totalPopulationFitness = 0.0; 
		for(Map.Entry<String, Double> entry : this.populationFitness.entrySet()){
			Double value = entry.getValue();
			this.totalPopulationFitness += value;
		}
			
	}
	
	@Override
	public Double getTotalPerformanceFitness(){
		return this.totalPerformanceFitness;
		
	}
	
	@Override
	public Double getTotalPopulationFitness(){
		return this.totalPopulationFitness;
		
	}
	
	@Override
	public int compare(Candidate arg0, Candidate arg1) {
		
		SystemEquation sarg0 = (SystemEquation) arg0;
		SystemEquation sarg1 = (SystemEquation) arg1;
		
		if(sarg0.myFitness.intValue() < sarg1.myFitness.intValue()){
			return -1;
		}
		if(sarg0.myFitness.intValue() > sarg1.myFitness.intValue()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int compareTo(Candidate arg0) {
		
		SystemEquation sarg0 = (SystemEquation) arg0;
		
		if(this.myFitness.intValue() < sarg0.myFitness.intValue()){
			return -1;
		}
		if(this.myFitness.intValue() > sarg0.myFitness.intValue()){
			return 1;
		}
		return 0;
	}
	
}