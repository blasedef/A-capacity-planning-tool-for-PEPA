package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;


import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.MetaHeuristicType;
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
		
		Models.metaHeuristicType.updateFitnessFunctionValues();
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
		String[] options = MetaHeuristicType.fitnessFunction.getFitnessMapKeys();
		Map<String,Number> map = MetaHeuristicType.fitnessFunction.getMap();
		String type; 
		
		for(String option : options){
			type = MetaHeuristicType.fitnessFunction.getTypeMap().get(option);
			widgets.add(new CapacityPlanningConfigurationTextWidget(container,option,MetaHeuristicType.fitnessFunction.getFitnessMapValue(option),map,type,cb));
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