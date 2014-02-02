package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class FitnessFunctionWeighting extends TextInputs {
	
	public FitnessFunctionWeighting(){
		super("Fitness function weighting");
		
		this.defaultMapping.put(Config.FITNESS_ALPHA_PERFORMANCE_S,0.5);
		this.defaultMapping.put(Config.FITNESS_BETA_POPULATION_S,0.5);
		this.defaultMapping.put(Config.FITNESS_DELTA_PERFORMANCE_S,0.5);
		this.defaultMapping.put(Config.FITNESS_EPSILON_TIME_S,0.5);
		
		this.leftMap.put(Config.FITNESS_ALPHA_PERFORMANCE_S,0.5);
		this.leftMap.put(Config.FITNESS_BETA_POPULATION_S,0.5);
		this.rightMap.put(Config.FITNESS_DELTA_PERFORMANCE_S,0.5);
		this.rightMap.put(Config.FITNESS_EPSILON_TIME_S,0.5);
	}
	
	@Override
	public String getType(String key){
		return Config.PERCENT;
	}

	@Override
	public boolean isCorrect(boolean single){
		
		if(!single){
			double sumLeft = 0;
			double sumRight = 0;
			
			String[] keysLeft = this.getMapKeys(false);
			String[] keysRight = this.getMapKeys(true);
			
			for(String key : keysLeft){
				sumLeft += this.leftMap.get(key);
			}
			
			for(String key : keysRight){
				sumRight += this.rightMap.get(key);
			}
			
			return ((sumLeft == 1.0) && (sumRight == 1.0));
		} else {
			return true;
		}
	}

}
