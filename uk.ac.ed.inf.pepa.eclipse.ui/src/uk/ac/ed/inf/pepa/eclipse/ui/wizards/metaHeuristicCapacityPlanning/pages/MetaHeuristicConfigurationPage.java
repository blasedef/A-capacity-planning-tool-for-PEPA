package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;


import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningConfigurationTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class MetaHeuristicConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public MetaHeuristicConfigurationPage(String s) {
		
		//copy title upwards
		super(s,Models.metaHeuristicType.getValue() + " Metaheuristic configuration page","Set up the " + Models.metaHeuristicNetworkType.getValue() + " " + 
				Models.metaHeuristicType.getValue() + " " +
				"Metaheuristic...");
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
		String[] options = Models.metaHeuristicType.getFitnessFunction().getFitnessMapKeys();
		Map<String,Number> map = Models.metaHeuristicType.getFitnessFunction().getMap();
		String type; 
		
		for(String option : options){
			type = Models.metaHeuristicType.getFitnessFunction().getTypeMap().get(option);
			widgets.add(new CapacityPlanningConfigurationTextWidget(container,option,Models.metaHeuristicType.getFitnessFunction().getFitnessMapValue(option),map,type,cb));
		}
		
		
	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		if(!bool){
			setErrorMessage("Invalid Configuration");
		} else {
			setErrorMessage(null);
		}
		setPageComplete(bool);
	}


}