package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class AdditionalCosts extends DropDownLists {
	
	public AdditionalCosts (){
		
		this.key = Config.ADDITIONALCOSTS_S;
		this.value = Config.ADDITIONALCOSTSNO_S;
		this.options = new String[] {Config.ADDITIONALCOSTSNO_S, Config.ADDITIONALCOSTSYES_S};
		
	}

}
