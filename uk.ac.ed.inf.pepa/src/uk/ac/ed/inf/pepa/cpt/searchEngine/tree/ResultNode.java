package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.Comparator;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.Utils;

public class ResultNode implements Comparator<ResultNode>, Comparable<ResultNode> {

//	private MetaHeuristicNode mhConfiguration;
//	private ModelConfigurationCandidateNode modelConfiguration;
	private Double cost, performanceCost, populationCost, totalPopulation;
	private HashMap<String,Double> componentPopulationMap;
	private HashMap<String,Double> performanceMap;
	private HashMap<String,Double> metaheuristicParameterMap;
	
	public String COMPONENT,MEASURED, PSO, TOTAL, POP, PER, TPOP;
	
	public ResultNode(MetaHeuristicNode mhConfiguration,
			ModelConfigurationCandidateNode modelConfiguration){
		
		this.cost = modelConfiguration.getFitness();
		this.performanceCost = modelConfiguration.getPerformanceResult();
		this.populationCost = modelConfiguration.getPopulationResult();
		this.totalPopulation = modelConfiguration.getTotalComponents();
		this.componentPopulationMap = Utils.copyHashMap(modelConfiguration.getMyMap());
		this.performanceMap = Utils.copyHashMap(modelConfiguration.getPerformanceMap());
		this.metaheuristicParameterMap = Utils.copyHashMap(mhConfiguration.getMyMap());
		
		//required strings
		this.COMPONENT = "Component population";
		this.MEASURED = "Measured Performance";
		this.PSO = "PSO parameters";
		this.TOTAL = "Total cost";
		this.POP = "Population cost";
		this.PER = "Performance cost";
		this.TPOP = "Total population";
		
	}
	
	private String mapAsCSVString(HashMap<String,Double> map){
		String output = "";
		
		for(String s : map.keySet()){
			output = output + s + ";" + map.get(s) + ";";
		}
		
		return output.substring(0,output.length() - 1);
	}
	
	private String mapAsNodeString(HashMap<String,Double> map){
		String output = "";
		
		for(String s : map.keySet()){
			output = output + s + "[" + map.get(s) + "]-";
		}
		
		return output.substring(0,output.length() - 1);
	}
	

	public String populationMapAsCSVString(){
		return mapAsCSVString(componentPopulationMap);
	}
	
	public String peformanceMapAsCSVString(){
		return  mapAsCSVString(performanceMap);
	}
	
	public String psoMapAsCSVString(){
		return  mapAsCSVString(metaheuristicParameterMap);
	}
	
	public String populationMapAsNodeString(){
		return mapAsNodeString(componentPopulationMap);
	}
	
	public String peformanceMapAsNodeString(){
		return mapAsNodeString(performanceMap);
	}
	
	public String psoMapAsNodeString(){
		return mapAsNodeString(metaheuristicParameterMap);
	}
	
	public String getTotalCostString(){
		return "" + this.cost;
	}
	
	public String getPopulationCostString(){
		return "" + this.populationCost;
	}
	
	public String getPerformanceCostString(){
		return "" + this.performanceCost;
	}
	
	public String getTotalPopulationString(){
		return "" + this.totalPopulation;
	}
	
	public String gapper(HashMap<String,Double> map){
		String output = "";
		for(int i = 0; i < map.size()*2; i++){
			output = output + ";";
		}
		return output;
	}
	
	public String componentPopulationGap(){
		return gapper(this.componentPopulationMap);	
	}
	
	public String performanceGap(){
		return gapper(performanceMap);
	}
	
	public String psoGap(){
		return gapper(metaheuristicParameterMap);
	}
	
	public String heading(){
		return COMPONENT + componentPopulationGap() + ";"
		+ TOTAL + ";"
		+ PER + ";"
		+ POP + ";"
		+ MEASURED + performanceGap()
		+ TPOP + ";;"
		+ PSO + psoGap();
	}
	
	public String toString(){
		return populationMapAsCSVString() + ";;" 
		+ getTotalCostString() + ";" 
		+ getPerformanceCostString() + ";"
		+ getPopulationCostString() + ";"
		+ peformanceMapAsCSVString() + ";"
		+ getTotalPopulationString() + ";;"
		+ psoMapAsCSVString() ;
	}
	
	public int compare(ResultNode c1, ResultNode c2){
		
		if(c1.getCost() > c2.getCost()){
			return -1;
		} else if (c1.getCost() < c2.getCost()){
			return 1;
		} else {
			return 0;
		}
		
	}

	private Double getCost() {
		return this.cost;
	}

	@Override
	public int compareTo(ResultNode arg0) {
		
		if(this.cost > arg0.getCost()){
			return -1;
		} else if (this.cost < arg0.getCost()){
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object obj){
		return false;
	}
	
	


}
