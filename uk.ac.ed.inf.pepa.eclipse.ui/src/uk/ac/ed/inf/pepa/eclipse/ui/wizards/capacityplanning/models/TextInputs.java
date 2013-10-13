package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

import java.util.HashMap;

public class TextInputs {
	
	/*
	 * Maps for storing user input
	 */
	protected HashMap<String,Double> leftMap;
	protected HashMap<String,Double> rightMap;
	
	/*
	 * default attributes
	 */
	protected HashMap<String,Double> defaultMapping;
	
	protected String key;
	
	protected String leftHeading;
	
	protected String rightHeading;
	
	protected String description;
	
	
	/**
	 * Type map: maps parameter/attribute to type 
	 * 
	 */
	protected HashMap<String,String> typeMap = new HashMap<String, String>()
	{
		private static final long serialVersionUID = -1391658164702351480L;

	{	put(Config.EXPERIMENTS_S,Config.INTEGER);
		put(Config.GENERATION_S,Config.INTEGER);
		put(Config.GENERATIONHC_S,Config.INTEGER);
		put(Config.FITNESS_ALPHA_PERFORMANCE_S,Config.PERCENT);
		put(Config.FITNESS_BETA_POPULATION_S,Config.PERCENT);
		put(Config.FITNESS_DELTA_PERFORMANCE_S,Config.PERCENT);
		put(Config.FITNESS_EPSILON_TIME_S,Config.PERCENT);
		put(Config.MUTATIONPROBABILITY_S,Config.PERCENT);
		put(Config.CROSSOVERPROBABILITY_S,Config.PERCENT);
		put(Config.INITIALCANDIDATEPOPULATION_S,Config.EVEN);
		put(Config.PERSONALBEST,Config.PERCENT);
		put(Config.GLOBALBEST,Config.PERCENT);
		put(Config.ORIGINALVELO,Config.PERCENT);
	}};
	
	public TextInputs(String key){
		leftMap = new HashMap<String, Double>();
		rightMap = new HashMap<String, Double>();
		defaultMapping = new HashMap<String, Double>();
		this.key = key;
		
		this.leftHeading = "";
		this.rightHeading = "";
	}
	
	public HashMap<String,Double> getLeftMap(){
		return this.leftMap;
	}
	
	public HashMap<String,Double> getRightMap(){
		return this.rightMap;
	}
	
	public HashMap<String,Double> getMap(boolean side){
		HashMap<String,Double> map;
		map = leftMap;
		if(side){
			map = rightMap;
		}
		return map;
	}

	public String getDescription(){
		return this.description;
	}
	
	public String getKey(){
		return this.key;
	}
	
	/**
	 * 
	 * Using a string key, and a boolean to decide which side of the mapping (true = right)
	 * return a parsed string of the value in the map. Else return the default value.
	 * 
	 * @param key
	 * @param side
	 * @return
	 */
	public String getMapValueAsString(String key, boolean side){
		
		Double number;
		HashMap<String, Double> map = this.leftMap;
		if(side)
			map = this.rightMap;
		
		if(map.containsKey(key)){
			number = map.get(key);
		} else {
			number = defaultMapping.get(key);
		}
		
		if((this.typeMap.get(key).equals(Config.INTEGER)) ||(this.typeMap.get(key).equals(Config.EVEN)) ){
			return "" + number.intValue();
		} else { 
			return "" + number;
		}
	}
	
	/**
	 * return the keys of either the left map (side = false) or the right map (side = true)
	 * 
	 * @param side
	 * @return
	 */
	public String[] getMapKeys(boolean side) {
		
		HashMap<String, Double> map = this.leftMap;
		if(side)
			map = this.rightMap;
		
		return map.keySet().toArray(new String[0]);
	}
	
	public HashMap<String, Double> getDefaultMap(){
		return defaultMapping;
	}
	
	public String getType(String key){
		return typeMap.get(key);
	}
	
	public String getLeftHeading(){
		return this.leftHeading;
	}
	
	public String getRightHeading(){
		return this.rightHeading;
	}
	
	public boolean isCorrect(boolean single){
		return true;
	}

}
