package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class MetaheuristicType extends DropDownLists {
	
	public MetaheuristicType (){
		
		this.key = Config.METAHEURISTICTYPE_S;
		this.value = Config.METAHEURISTICTYPEHILLCLIMBING_S;
		this.options = new String[] {Config.METAHEURISTICTYPEHILLCLIMBING_S,Config.METAHEURISTICTYPEGENETICALGORITHM_S,Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S};
		
	}

}
