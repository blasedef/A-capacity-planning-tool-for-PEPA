package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class PopulationMinAndMax extends TextInputs {
	
	private Integer[] initialPopulation;
	
	/**
	 * System equation population ranges
	 */
	public PopulationMinAndMax (){
		super("System equation population ranges");
		this.defaultMapping.put("min", 1.0);
		this.defaultMapping.put("max", 100.0);
		
		this.leftMap.put("min", 1.0);
		this.rightMap.put("max", 100.0);
		
		this.leftHeading = "Minimum population";
		this.rightHeading = "Maximum population";
	}
	
	/**
	 * Always returns as an int
	 */
	@Override
	public String getMapValueAsString(String key, boolean side){
		
		Double number;
		HashMap<String, Double> map = this.leftMap;
		if(side)
			map = this.rightMap;
		
		if(map.containsKey(key)){
			number = map.get(key);
		} else {
			if(side) {
				number = defaultMapping.get("max");
			} else {
				number = defaultMapping.get("min");
			}
		}
		
		return "" + number.intValue();
	}
	
	@Override
	public String getType(String key){
		return Config.INTEGER;
	}
	
	@Override
	public boolean isCorrect(boolean single){
		
		if(!single){
			boolean aboveZero = true;
			boolean maxGreaterThanOrEqualToMin = true;
			
			String[] keysLeft = this.getMapKeys(false);
			String[] keysRight = this.getMapKeys(true);
			
			for(String key : keysLeft){
				aboveZero = aboveZero && (this.leftMap.get(key) > 0.0);
			}
			
			for(String key : keysRight){
				maxGreaterThanOrEqualToMin = maxGreaterThanOrEqualToMin && (this.rightMap.get(key) >=  this.leftMap.get(key));
			}
			
			return (maxGreaterThanOrEqualToMin && aboveZero);
		} else {
			return true;
		}
	}
	
	public void setParameterList(String[] ss, boolean side){
		
		String[] options = ss;
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		HashMap<String, Double> oldMap;
		
		if(side){
			 oldMap = this.rightMap;
		} else {
			 oldMap = this.leftMap;
		}
		
		for(String t : options){
			if(oldMap.containsKey(t)){
				map.put(t, oldMap.get(t));
			} else {
				if(side){
					map.put(t, this.defaultMapping.get("max"));
				} else {
					map.put(t, this.defaultMapping.get("min"));
				}
			}
		}
		
		if(side){
			this.rightMap = map;
		} else {
			this.leftMap = map;
		}
		
	}
	
	public void update(String[] ss, Integer[] is){
		
		this.initialPopulation = new Integer[is.length];
		
		for(int i = 0; i < is.length; i++){
			this.initialPopulation[i] = is[i];
		}
		
		setParameterList(ss,false);
		setParameterList(ss,true);
		
	}

}
