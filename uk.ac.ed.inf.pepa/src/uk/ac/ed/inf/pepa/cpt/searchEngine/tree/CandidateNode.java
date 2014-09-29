package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;


public abstract class CandidateNode implements Node {
	
	protected int uid;
	private int generation;
	protected HashMap<String,Double> myMap;
	private String name;
	private long timeCreated;
	protected long timeFinished;
	protected Double fitness;
	private MetaHeuristicNode parent;
	protected ArrayList<MetaHeuristicNode> children;
	private HashMap<Integer,Integer> childUIDToIndex;
	private ModelConfigurationCandidateNode fittestNode;
	private CandidateNode sister;
	private String familyUID;
	
	public CandidateNode(String name, 
			MetaHeuristicNode parent){
		
		this.myMap = new HashMap<String,Double>();
		this.children = new ArrayList<MetaHeuristicNode>();
		this.childUIDToIndex = new HashMap<Integer, Integer>();
		
		setUpUID();
		
		this.setName(name + "-" + uid);
		this.setGeneration(0);
		this.setParent(parent);
		this.timeCreated = System.currentTimeMillis();
		this.fitness = 1000000.0;
		this.sister = null;
		this.familyUID = this.getName().split("-")[1];
		
	}
	
	public abstract void setUpUID();

	
	public void registerChild(MetaHeuristicNode child){
		children.add(child);
		childUIDToIndex.put(child.getUID(), children.size() - 1);
	}

	public abstract void setMyMap();

	public HashMap<String,Double> getMyMap() {
		return myMap;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public int getGeneration() {
		return generation;
	}
	

	public long getRunTime(){
		
		return this.timeFinished - this.timeCreated;
		
	}

	public void setParent(MetaHeuristicNode parent) {
		this.parent = parent;
	}

	public MetaHeuristicNode getParent() {
		return parent;
	}

	public Integer getUID() {
		return this.uid;
	}

	public Double getFitness() {
		return this.fitness;
	}
	
	public void updateFitness(){
		
		Double top, mean, std, art, fitness;
		
		top = 10000.0;
		mean = 0.0;
		art = 0.0;
		std = 0.0;
		
		for(int i = 0; i < this.children.size(); i++){
			fitness = this.children.get(i).getFittestNode().getFitness();
			if(fitness < top){
				top = fitness;
				this.fittestNode = this.children.get(i).getFittestNode();
			}
			mean += fitness;
			art += ((Long) this.children.get(i).getRunTime()).doubleValue();
		}
		
		mean = mean/this.children.size();
		art = mean/this.children.size();
		
		for(int i = 0; i < this.children.size(); i++){
			fitness = this.children.get(i).getFittestNode().getFitness();
			std += Math.pow(mean - fitness,2);
		}
		
		std = std/this.children.size();
		
		this.fitness = (0.25 * top) + (0.25 * mean) + (0.25 * std) + (0.25 * art); 
	}

	public ModelConfigurationCandidateNode getFittestNode() {
		return this.fittestNode;
	}

	public void setSister(CandidateNode node) {
		this.sister = node;
		this.setFamilyUID(node.familyUID);
		this.setGeneration(this.sister.generation + 1);
	}
	
	private void setFamilyUID(String familyUID) {
		this.familyUID = familyUID;
	}

	public boolean hasSister(){
		
		if(this.sister != null){
			return true;
		} else {
			return false;
		}
	}
	
	public abstract void fillQueue(PriorityQueue<ResultNode> resultsQueue, IProgressMonitor monitor);

//	public void fillQueue(PriorityQueue<ResultNode> resultsQueue) {
//		
//		for(int i = 0; i < this.children.size(); i++){
//			this.children.get(i).fillQueue(resultsQueue);
//		}
//		
//	}

//	public void fillQueue(double runTime, PriorityQueue<ResultNode> resultsQueue) {
//		
//		for(int i = 0; i < this.children.size(); i++){
//			this.children.get(i).fillQueue(this, runTime, resultsQueue);
//		}
//		
//	}
	
	public void updateFinishTime(){
		this.timeFinished = System.currentTimeMillis();
	}


	public abstract void setResultsSize(); 

	
}
