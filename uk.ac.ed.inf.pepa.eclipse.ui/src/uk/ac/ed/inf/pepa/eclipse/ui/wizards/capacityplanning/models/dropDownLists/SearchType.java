package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class SearchType extends DropDownLists {
	
	public SearchType (){
		
		this.key = Config.SEARCHTYPE_S;
		this.value = Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S;
		this.options = new String[] {Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S,Config.SEARCHDRIVEN_S};
		this.description = "Choose the type of search:";
		
	}

}