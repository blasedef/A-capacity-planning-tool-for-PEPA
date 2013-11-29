package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class NetworkType extends DropDownLists {
	
	public NetworkType (){
		
		this.key = Config.CHAINTYPE_S;
		this.value = Config.CHAINSINGLE_S;
		this.options = new String[] {Config.CHAINSINGLE_S, Config.CHAINDRIVEN_S, Config.CHAINPIPELINE_S};
		
	}

}