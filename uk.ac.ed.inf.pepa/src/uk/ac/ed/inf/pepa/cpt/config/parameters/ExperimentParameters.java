package uk.ac.ed.inf.pepa.cpt.config.parameters;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.Config;

public class ExperimentParameters extends Parameters {
	
	public ExperimentParameters (){
		this.keyValueMap = new HashMap<String,Double>();
		this.keyTypeMap = new HashMap<String,String>();
		
		//default experiment number
		this.keyValueMap.put(Config.LABEXP, 1.0);
		
		//type map
		this.keyTypeMap.put(Config.LABEXP, Config.NATURAL);
		
	}

	@Override
	public boolean valid() {
		return this.keyValueMap.get(Config.LABEXP) > 0.0;
	}

}
