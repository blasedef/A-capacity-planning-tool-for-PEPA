package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;


import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningConfigurationTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class MetaHeuristicConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public MetaHeuristicConfigurationPage(String s) {
		
		//copy title upwards
		super(s,"Primary Metaheuristic configuration page","Set up the " + ExperimentConfiguration.metaHeuristicNetworkType.getDescription() + " " + 
				ExperimentConfiguration.metaHeuristicPrimary.getValue() + " " +
				"Metaheuristic...");
		
		ExperimentConfiguration.metaHeuristicPrimary.updateAttributeValues();
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
		String[] options = ExperimentConfiguration.metaHeuristicPrimary.getAttributeMapKeys();
		Map<String,Number> map = ExperimentConfiguration.metaHeuristicPrimary.getAttributeMap();
		String type; 
		
		for(String option : options){
			type = ExperimentConfiguration.defaultOptionTypeMap.get(option);
			widgets.add(new CapacityPlanningConfigurationTextWidget(container,option,ExperimentConfiguration.metaHeuristicPrimary.getAttributeMapValue(option),map,type,cb));
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