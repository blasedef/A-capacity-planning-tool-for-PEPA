package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;


import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class LabParameters extends TextInputs {
	
	
	/**
	 * Lab parameters - number of experiments
	 */
	public LabParameters (){
		super("Lab parameters setup");
		
		this.defaultMapping.put(Config.EXPERIMENTS_S,10.0);
		
		this.leftMap.put(Config.EXPERIMENTS_S, this.defaultMapping.get(Config.EXPERIMENTS_S));
		
		this.leftHeading = "Lab parameters; number of experiments";
		this.rightHeading = "not used";
	}

	
	@Override
	public boolean isCorrect(boolean single){
		
		if(!single){
			boolean aboveZero = true;
			
			String[] keysLeft = this.getMapKeys(false);
			
			for(String key : keysLeft){
				if((this.typeMap.get(key) == Config.NATURAL) || (this.typeMap.get(key) == Config.EVEN) ){
					aboveZero = aboveZero && (this.leftMap.get(key) > 0.0);
				} else {
					aboveZero = aboveZero && (this.leftMap.get(key) >= 0.0);
				}
				
			}
			
			return aboveZero;
		}else {
			return true;
		}
	}

}