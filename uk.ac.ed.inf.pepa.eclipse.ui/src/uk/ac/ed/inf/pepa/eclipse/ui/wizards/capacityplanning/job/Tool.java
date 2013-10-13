package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class Tool {
	
	public static double startTime;
	
	public static void setStartTime(){
		Tool.startTime = System.currentTimeMillis();
	}
	
	public static HashMap<String, Double> copyHashMap(HashMap<String, Double> map){
		
		HashMap<String, Double> newMap = new HashMap<String,Double>();
		
		for(Entry<String, Double> entry : map.entrySet()){
			newMap.put(entry.getKey(), entry.getValue());
		}
		
		return newMap;
		
	}
	
	public static Double returnRandomInRange(Double min, Double max, String type){
		
		Random generator = new Random();
		
		if(type.equals(Config.INTEGER)){
			
			return generator.nextInt((int)(max - min) + 1) + min; 
			
		} else if (type.equals(Config.DOUBLE)) {
			
			return generator.nextDouble() * (max - min) + min; 
			
		} else if (type.equals(Config.PERCENT)) {
			
			return generator.nextDouble() * (max - min) + min;
			
		} else {
			//would imply Even, to which I don't need a value
			return 0.0;
			
		}
		
	}
	
	public static Double returnRandom(){
		Random generator = new Random();
		Double next = generator.nextDouble();
		return next; 
	}
	
	public static boolean rollDice(Double p){
		return (returnRandom() < p);
	}

}
