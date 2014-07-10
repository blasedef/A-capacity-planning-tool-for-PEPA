package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.searchEngine.fitnessFunctions.FitnessFunction;
import uk.ac.ed.inf.pepa.cpt.searchEngine.fitnessFunctions.ModelConfigurationFitnessFunction;


public class ModelConfigurationCandidateNode extends CandidateNode {
	
	private ModelConfigurationCandidateNode sister;
	private int generation;
	private HashMap<String,Double> performanceMap;
	private HashMap<String,Double> velocity;
	private FitnessFunction fitnessFunction;
	private Double performanceResult;
	private Double populationResult;
	private String familyUID;
	private boolean evaluatedSuccessFully;

	public ModelConfigurationCandidateNode(String name,
			HashMap<String, Double> parameters, 
			HashMap<String,Double> velocity, 
			MetaHeuristicNode parent) {
		super(name, parameters, parent);
		
		this.performanceMap = new HashMap<String, Double>();
		
		this.performanceResult = 100000.0;
		this.populationResult = 100000.0;
		
		this.sister = null;
		this.generation = 0;
		this.velocity = velocity;
		this.fitnessFunction = new ModelConfigurationFitnessFunction();
		this.familyUID = this.getName().split("-")[1];
		this.evaluatedSuccessFully = true;
	}
	
	public Double getTotalComponents(){
		
		String[] keys = this.myMap.keySet().toArray(new String[this.myMap.keySet().size()]);
		Double count = 0.0;
		
		for(int i = 0; i < keys.length; i++){
			count += this.myMap.get(keys[i]);
		}
		
		return count;
	}
	
	public ModelConfigurationCandidateNode(){
		super("temp",null,null);
	}
	
	public void setUpUID(){
		this.uid = Utils.getModelCandidateNodeUID();
	}
	
	public void setMyMap(HashMap<String,Double> myMap) {
		this.myMap = myMap;
	}
	
	public void setSister(ModelConfigurationCandidateNode node){
		this.sister = node;
		this.setFamilyUID(node.familyUID);
		this.setGeneration(this.sister.generation + 1);
	}
	
	private void setFamilyUID(String familyUID) {
		this.familyUID = familyUID;
		
	}

	public int getGeneration(){
		return this.generation;
	}
	
	public void setGeneration(int generation){
		this.generation = generation;
	}

	public void setODEResults(String[] labels, double[] results) {
		
		for(int i = 0; i < labels.length;i++){
			this.performanceMap.put(labels[i], results[i]);
		}
		
	}
	
	public HashMap<String,Double> getMyMap() {
		return myMap;
	}
	
	@Override
	public void jsonMyMap(JSONArray list){
		
		JSONObject obj = new JSONObject();
		
		obj.put("Domain", jsonDomain());
		obj.put("Velocity", jsonVelocity());
		obj.put("Performance", jsonPerformance());
		obj.put("Generation", this.generation);
		obj.put("Runtime", this.getRunTime());
		obj.put("TC", this.getTotalComponents());
		obj.put("perr", this.performanceResult);
		obj.put("popr", this.populationResult);
		obj.put("FUID", this.familyUID);
		if(this.sister != null)
			obj.put("Sister", this.sister.getName());
		
		list.add(obj);
		
	}
	
	public JSONArray jsonDomain(){
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		for(Entry<String, Double> entry : myMap.entrySet()){
			obj.put(entry.getKey(),entry.getValue());
		}
		
		list.add(obj);
		
		return list;
	}
	
	
	public JSONArray jsonVelocity(){
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		for(Entry<String, Double> entry : velocity.entrySet()){
			obj.put(entry.getKey(),entry.getValue());
		}
		
		list.add(obj);
		
		return list;
	}
	
	public JSONArray jsonPerformance(){
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		for(Entry<String, Double> entry : performanceMap.entrySet()){
			obj.put(entry.getKey(),entry.getValue());
		}
		
		list.add(obj);
		
		return list;
	}
	
	/**
	 * reads from local maps, uses fitness function object, returns results... 
	 */
	public void updateFitness(){
		this.fitness = this.fitnessFunction.assessFitness(this.myMap, this.performanceMap);
		this.timeFinished = System.currentTimeMillis();
		this.performanceResult = ((ModelConfigurationFitnessFunction)this.fitnessFunction).performance;
		this.populationResult = ((ModelConfigurationFitnessFunction)this.fitnessFunction).population;
	}
	
	public Double getFitness() {
		return this.fitness;
	}
	
	public HashMap<String, Double> getVelocity() {
		return velocity;
	}

	public void setVelocity(HashMap<String, Double> velocity) {
		this.velocity = velocity;
	}
	
	public boolean hasSister(){
		
		if(this.sister != null){
			return true;
		} else {
			return false;
		}
	}
	
	public ModelConfigurationCandidateNode getSister(){
		return this.sister;
	}
	
	
	/**
//	 * has the particle moved in the last two turns?
	 * @return
	 */
	public boolean hasStopped(){
		
		HashMap<String,Double> third,second,first;
		Double t;
		
		if(this.hasSister()){
			first = getMyMap();
		} else {
			return false;
		}
		
		if(this.sister.hasSister()){
			second = this.sister.getMyMap();
		} else {
			return false;
		}
		
		if(this.sister.getSister().hasSister()){
			third = this.sister.getSister().getMyMap();
			
		} else {
			return false;
		}
		
		String[] keys = first.keySet().toArray(new String[first.keySet().size()]);
		
		t = 0.0; 
		
		for(int i = 0; i < keys.length; i++){
			t += Math.abs(first.get(keys[i]) - second.get(keys[i]));
			t += Math.abs(first.get(keys[i]) - third.get(keys[i]));

		}
		
		return (t < 3);
		
	}

	public void switchFlag() {
		this.evaluatedSuccessFully = !this.evaluatedSuccessFully;
		
	}
	
	public HashMap<String,Double> getPerformanceMap(){
		return this.performanceMap;
	}
	
	public void fillQueue(MetaHeuristicNode metaHeuristicNode,
			CandidateNode candidateNode, double runTime,
			PriorityQueue<ResultNode> resultsQueue) {
		
		resultsQueue.add(new ResultNode(runTime,candidateNode,metaHeuristicNode,this));
		
	}

}
