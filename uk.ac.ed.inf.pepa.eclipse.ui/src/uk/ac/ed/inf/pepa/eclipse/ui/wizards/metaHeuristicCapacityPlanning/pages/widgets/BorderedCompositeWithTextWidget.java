package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;


import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.MetaHeuristicCapacityPlanningWizardPage.GetValue;

public class BorderedCompositeWithTextWidget extends BorderedCompositeLabelWidget{
	
	String type;
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public BorderedCompositeWithTextWidget(String heading, String[] keys, Map<String,Number> map, IValidationCallback cb,
			  Composite container,
			  GetValue getTarget,
			  String type) {
		super(heading, null, null, cb, container, 2);
		
		
		for(String key : keys){
			if(type == null){
				widgets.add(new CapacityPlanningConfigurationTextWidget(this.composite,
						key,
						getTarget.getMapValue(key),
						map,
						ExperimentConfiguration.defaultOptionTypeMap.get(key),
						cb));
			} else {
				widgets.add(new CapacityPlanningConfigurationTextWidget(this.composite,
						key,
						getTarget.getMapValue(key),
						map,
						type,
						cb));
			}
		}
		
		
	}
	
	@Override
	public boolean isValid() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		return bool;
	}

	@Override
	public void setValue() {
		//do nothing
	}
	
}