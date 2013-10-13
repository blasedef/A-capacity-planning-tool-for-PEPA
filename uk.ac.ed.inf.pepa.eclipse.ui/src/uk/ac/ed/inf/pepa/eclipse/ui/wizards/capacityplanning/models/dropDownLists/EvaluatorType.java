package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.DropDownLists;

public class EvaluatorType extends DropDownLists {
	
	public EvaluatorType (){
		
		this.key = Config.EVALUATORTYPE_S;
		this.value = Config.EVALUATORTHROUGHPUT_S;
		this.options = new String[] {Config.EVALUATORTHROUGHPUT_S, Config.EVALUATORAVERAGERESPONSETIME_S};
		
	}

}
