package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class Targets extends TextInputs {
	
	public Targets(double i){
		super("Performance target(s)");
		this.defaultMapping.put("target", i);
		this.defaultMapping.put("weight", 1.0);
		
		this.leftMap.put("target", i);
		this.rightMap.put("weight", 1.0);
		
		this.leftHeading = "Performance target";
		this.rightHeading = "Performance weight";
	}

	/**
	 * Always returns as a double
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
				number = defaultMapping.get("target");
			} else {
				number = defaultMapping.get("weight");
			}
		}
		
		return "" + number;
	}
	
	@Override
	public String getType(String key){
		return Config.DOUBLE;
	}
	
	@Override
	public boolean isCorrect(boolean single){
		
		if(!single){
			double sumRight = 0;
			
			String[] keysRight = this.getMapKeys(true);
			
			for(String key : keysRight){
				sumRight += this.rightMap.get(key);
			}
			
			return (sumRight == 1.0);
		} else {
			return true;
		}
	}

	public void update(String[] ss){
		
		setParameterList(ss,false);
		setParameterList(ss,true);
		
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
					map.put(t, this.defaultMapping.get("weight"));
				} else {
					map.put(t, this.defaultMapping.get("target"));
				}
			}
		}
		
		if(side){
			this.rightMap = map;
		} else {
			this.leftMap = map;
		}
		
	}
	
}