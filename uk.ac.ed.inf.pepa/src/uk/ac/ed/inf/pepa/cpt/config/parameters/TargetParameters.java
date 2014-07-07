package uk.ac.ed.inf.pepa.cpt.config.parameters;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.Config;

public class TargetParameters extends Parameters {

	public TargetParameters() {
		
		this.keyValueMap = new HashMap<String,Double>();
		this.keyTypeMap = new HashMap<String,String>();
		
		//default component parameters
		this.keyValueMap.put(Config.LABTAR, 1.0);
		this.keyValueMap.put(Config.LABWEI, 1.0);
		
		//type map
		this.keyTypeMap.put(Config.LABTAR, Config.DOUBLE);
		this.keyTypeMap.put(Config.LABWEI, Config.INTEGER);
	}

	@Override
	public boolean valid() {
		Double tar, wei;
		
		tar = this.keyValueMap.get(Config.LABTAR);
		wei = this.keyValueMap.get(Config.LABWEI);
		
		//no negatives..
		return (tar >= 0) && (wei >= 0);
	}

}
