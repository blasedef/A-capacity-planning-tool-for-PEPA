package uk.ac.ed.inf.pepa.cpt.config.parameters;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.Config;

public class HillClimbingParameters extends Parameters {

	public HillClimbingParameters() {
		this.keyValueMap = new HashMap<String,Double>();
		this.keyTypeMap = new HashMap<String,String>();
		
		//default mutation rate
		this.keyValueMap.put(Config.LABMUT, 0.5);
		
		//type map
		this.keyTypeMap.put(Config.LABMUT, Config.PERCENT);
	}

	@Override
	public boolean valid() {
		return (this.keyValueMap.get(Config.LABMUT) >= 0.0) && 
			(this.keyValueMap.get(Config.LABMUT) <= 1.0);
	}

}
