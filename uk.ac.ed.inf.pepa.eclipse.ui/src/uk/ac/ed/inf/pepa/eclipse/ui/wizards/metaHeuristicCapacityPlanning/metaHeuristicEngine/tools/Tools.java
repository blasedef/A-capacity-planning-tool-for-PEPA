package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.Random;


import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;

public class Tools {
	
	public static Double returnRandomInRange(Double min, Double max, String type){
		
		Random generator = new Random();
		
		if(type.equals(ExperimentConfiguration.INTEGER)){
			
			return generator.nextInt((int)(max - min) + 1) + min; 
			
		} else if (type.equals(ExperimentConfiguration.DOUBLE)) {
			
			return generator.nextDouble() * (max - min) + min; 
			
		} else if (type.equals(ExperimentConfiguration.PERCENT)) {
			
			return generator.nextDouble() * (max - min) + min;
			
		} else {
			//would imply Even, to which I don't need a value
			return 0.0;
			
		}
		
	}
	
	public static Double returnRandom(){
		Random generator = new Random();
		return generator.nextDouble();
	}
	
	public static boolean rollDice(Double p){
		return (returnRandom() < p);
	}

}
