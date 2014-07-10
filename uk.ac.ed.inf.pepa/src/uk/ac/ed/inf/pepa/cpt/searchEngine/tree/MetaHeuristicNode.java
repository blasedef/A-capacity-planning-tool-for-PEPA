package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;

public class MetaHeuristicNode implements Node {
	
	private int uid;
	private HashMap<String,Double> myMap;
	private String name;
	private long timeCreated;
	private long timeFinished;
	private CandidateNode parent;
	private ArrayList<CandidateNode> children;
	private HashMap<Integer,Integer> childUIDToIndex;
	private ModelConfigurationCandidateNode fittestNode;
	
	public MetaHeuristicNode(String name, 
			HashMap<String,Double> parameters,
			CandidateNode parent){
		
		this.myMap = new HashMap<String,Double>();
		this.children = new ArrayList<CandidateNode>();
		this.childUIDToIndex = new HashMap<Integer, Integer>();
		
		this.uid = Utils.getMetaheuristicNodeUID();
		
		if(parameters == null){
			this.myMap.put("n/a", 0.0);
		} else {
			parameters.remove(Config.LABEXP);
			this.myMap = parameters;
		}
		this.name = name + "-" + uid;
		this.parent = parent;
		this.timeCreated = System.currentTimeMillis();
		this.fittestNode = new ModelConfigurationCandidateNode();
		
	}
	
	public String getName() {
		return this.name;
	}

	public Integer getUID() {
		return this.uid;
	}
	
	public void updateFinishTime(){
		this.timeFinished = System.currentTimeMillis();
	}
	
	public long getRunTime(){
		
		return this.timeFinished - this.timeCreated;
		
	}

	public CandidateNode getParent() {
		return parent;
	}

	public void registerChild(CandidateNode child){
		children.add(child);
		childUIDToIndex.put(child.getUID(), children.size() - 1);
	}

	public void json(JSONObject obj) {
		
		JSONArray list = new JSONArray();
		
		jsonMyMap(list);
		
		obj.put(this.name,list);
		
		for(int i = 0; i < children.size();i++){
			children.get(i).json(obj);
		}
		
		
		
	}
	
	public void jsonMyMap(JSONArray list){
		
		JSONObject obj = new JSONObject();
		
		for(Entry<String, Double> entry : myMap.entrySet()){
			obj.put(entry.getKey(),entry.getValue());
		}
		
		list.add(obj);
		
	}

	@Override
	public HashMap<String, Double> getMyMap() {
		return this.myMap;
	}

	public ModelConfigurationCandidateNode getFittestNode() {
		return this.fittestNode;
	}

	public void setFittestNode(ModelConfigurationCandidateNode node) {
		this.fittestNode = node;
		
	}

	public void fillQueue(PriorityQueue<ResultNode> resultsQueue) {
		
		for(int i = 0; i < this.children.size(); i++){
			this.children.get(i).fillQueue((double) this.getRunTime(),resultsQueue);
		}
		
	}

	public void fillQueue(CandidateNode candidateNode, double runTime,
			PriorityQueue<ResultNode> resultsQueue) {
		
		for(int i = 0; i < this.children.size(); i++){
			((ModelConfigurationCandidateNode) this.children.get(i)).fillQueue(this, candidateNode, runTime,resultsQueue);
		}
		
	}

}
