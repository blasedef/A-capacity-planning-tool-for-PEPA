package uk.ac.ed.inf.pepa.cpt.config.parameters;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.Config;

public class GenerationParameters extends Parameters {

	public GenerationParameters() {
		this.keyValueMap = new HashMap<String,Double>();
		this.keyTypeMap = new HashMap<String,String>();
		
		//default generation count
		this.keyValueMap.put(Config.LABGEN, 10.0);
		
		//type map
		this.keyTypeMap.put(Config.LABGEN, Config.NATURAL);
		
	}

	@Override
	public boolean valid() {
		return this.keyValueMap.get(Config.LABGEN) > 0.0;
	}

}
