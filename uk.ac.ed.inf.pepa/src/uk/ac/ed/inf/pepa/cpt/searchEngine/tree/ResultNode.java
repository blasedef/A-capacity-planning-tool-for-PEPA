package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ResultNode implements Comparator<ResultNode>, Comparable<ResultNode> {

	private CandidateNode labConfiguration;
	private MetaHeuristicNode psoConfiguration;
	private ModelConfigurationCandidateNode modelConfiguration;
	private Double fitness, hRunTime;
	
	public ResultNode(Double hRunTime,
			CandidateNode labConfiguration,
			MetaHeuristicNode psoConfiguration,
			ModelConfigurationCandidateNode modelConfiguration){
		
		this.hRunTime = hRunTime;
		this.labConfiguration = labConfiguration;
		this.psoConfiguration = psoConfiguration;
		this.modelConfiguration = modelConfiguration;
		this.fitness = this.modelConfiguration.getFitness();
		
	}
	
	public ModelConfigurationCandidateNode getMCN(){
		return this.modelConfiguration;
	}
	
	public int compare(ResultNode c1, ResultNode c2){
		
		if(c1.getFitness() > c2.getFitness()){
			return -1;
		} else if (c1.getFitness() < c2.getFitness()){
			return 1;
		} else {
			return 0;
		}
		
	}

	private Double getFitness() {
		return this.fitness;
	}

	@Override
	public int compareTo(ResultNode arg0) {
		
		if(this.fitness > arg0.getFitness()){
			return -1;
		} else if (this.fitness < arg0.getFitness()){
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof ResultNode){
			ResultNode candidate = (ResultNode) obj;
			if(this.modelConfiguration.getMyMap().toString().equals(candidate.getMCN().myMap.toString())){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public JSONArray getMapAsList(HashMap<String,Double> map){
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		for(Entry<String, Double> entry : map.entrySet()){
			obj.put(entry.getKey(),entry.getValue());
		}
		
		list.add(obj);
		
		return list;
		
	}
	
	public void print(JSONObject obj) {
		
		JSONArray list = new JSONArray();
		JSONObject obj2 = new JSONObject();
		
		list.add(getMapAsList(this.modelConfiguration.getMyMap()));
		list.add(getMapAsList(this.psoConfiguration.getMyMap()));
		
		obj2.put("modelconfigurationfitness",this.modelConfiguration.getFitness());
		obj2.put("performanceMap",this.modelConfiguration.getPerformanceMap());
		obj2.put("totalPopulation", this.modelConfiguration.getTotalComponents());
		obj2.put("labfitness",this.labConfiguration.getFitness());
		obj2.put("modelConfigurationRuntime",this.modelConfiguration.getRunTime());
		obj2.put("psoRunTime",this.psoConfiguration.getRunTime());
		obj2.put("labRunTime",this.hRunTime);
		
		list.add(obj2);
		
		obj.put(this.modelConfiguration.getName(), list);
		
	}

}
