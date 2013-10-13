package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class NetworkType extends DropDownLists {
	
	public NetworkType (){
		
		this.key = Config.NETWORKTYPE_S;
		this.value = Config.NETWORKSINGLE_S;
		this.options = new String[] {Config.NETWORKSINGLE_S, Config.NETWORKDRIVEN_S, Config.NETWORKPIPELINE_S};
		
	}

}