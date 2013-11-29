package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class PopulationWeights extends TextInputs {
	
	public PopulationWeights() {
		super("Population weighting");
		
		this.defaultMapping.put("x", 1.0);
		
		this.leftMap.put("x", 1.0);
		
		//really Chris you should make more models, and not have this left and right thing...!
		this.rightMap.put("x", 1.0);
		
		this.leftHeading = "Candidate";
		this.leftHeading = "NaN";
	}
	
	/**
	 * Always returns as a percent
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
			number = defaultMapping.get("x");
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
			boolean greaterThanOrEqualToZero = true;
			
			String[] keysLeft = this.getMapKeys(false);
			
			for(String key : keysLeft){
				greaterThanOrEqualToZero = greaterThanOrEqualToZero && (this.leftMap.get(key) >= 0.0);
			}
			
			return greaterThanOrEqualToZero;
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
				map.put(t, this.defaultMapping.get("x"));
			}
		}
		
		if(side){
			this.rightMap = map;
		} else {
			this.leftMap = map;
		}
		
	}
	
	public void update(String[] ss, boolean side){
		this.setParameterList(ss, side);
	}

}
