package uk.ac.ed.inf.pepa.cpt.config.parameters;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.Config;

public class ParticleSwarmOptimisationParameters extends Parameters {

	public ParticleSwarmOptimisationParameters() {
		this.keyValueMap = new HashMap<String,Double>();
		this.keyTypeMap = new HashMap<String,String>();
		
		//default pso parameters
		this.keyValueMap.put(Config.LABPOP, 10.0);
		this.keyValueMap.put(Config.LABLOC, 1.0);
		this.keyValueMap.put(Config.LABGLO, 1.0);
		this.keyValueMap.put(Config.LABORG, 1.0);
		
		//type map
		this.keyTypeMap.put(Config.LABPOP, Config.NATURAL);
		this.keyTypeMap.put(Config.LABLOC, Config.INTEGER);
		this.keyTypeMap.put(Config.LABGLO, Config.INTEGER);
		this.keyTypeMap.put(Config.LABORG, Config.INTEGER);
	}

	@Override
	public boolean valid() {
		Double d,e,f,g;
		d = this.keyValueMap.get(Config.LABPOP);
		e = this.keyValueMap.get(Config.LABLOC);
		f = this.keyValueMap.get(Config.LABGLO);
		g = this.keyValueMap.get(Config.LABORG);
		
		return (d > 0) && (e + f + g > 0);
	}

}
