package uk.ac.ed.inf.pepa.cpt.config.lists;

import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.parameters.Parameters;
import uk.ac.ed.inf.pepa.cpt.config.parameters.MetaheuristicSearchParameters;

public class PSOList extends RangeList {

	public PSOList() {
		super();
		
		this.myHashMap.put(Config.LABEXP,new MetaheuristicSearchParameters());
		this.myHashMap.put(Config.LABGEN,new MetaheuristicSearchParameters());
		this.myHashMap.put(Config.LABPOP,new MetaheuristicSearchParameters());
		this.myHashMap.put(Config.LABLOC,new MetaheuristicSearchParameters());
		this.myHashMap.put(Config.LABGLO,new MetaheuristicSearchParameters());
		this.myHashMap.put(Config.LABORG,new MetaheuristicSearchParameters());
		
	}

	@Override
	public boolean valid() {
		
		boolean valid = true;
		
		for(Entry<String, Parameters> entry : this.myHashMap.entrySet()){
			valid = valid && entry.getValue().valid();
		}
		
		return valid;
	}

}
