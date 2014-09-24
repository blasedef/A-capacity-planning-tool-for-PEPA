package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.AverageResponseTreeWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.CapacityPlanningWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.UtilCapacityTreeWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.PopulationTreeWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets.ThroughputTreeWidget;

public class PerformanceSelectionCapacityPlanningWizardPage extends
		CapacityPlanningWizardPage {

	public PerformanceSelectionCapacityPlanningWizardPage(String pageName) {
		super();
		this.setDescription(pageName);	
	}

	@Override
	public void completePage() {
		
		String inputError = "";
		
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid().valid;
			String temp = w.isValid().complaint;
			if(temp.length() > 0)
				inputError = temp;
		}
		
		if(inputError.length() > 0){
			setErrorMessage(inputError);
		} else {
			setErrorMessage(null);
		}
		
		if(CPTAPI.getPerformanceControls().validate()){
			CPTAPI.updateTargetControl();
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
		
		
		
		
	}

	@Override
	protected void constructPage(IValidationCallback cb, Composite container) {
		
		setPageComplete(false);
		
		if(CPTAPI.getEvaluationControls().getValue().equals(Config.EVALARPT)){
			this.widgets.add(new AverageResponseTreeWidget(cb, container, CPTAPI.getPerformanceControls()));
		}
		
		if(CPTAPI.getEvaluationControls().getValue().equals(Config.EVALTHRO)){
			this.widgets.add(new ThroughputTreeWidget(cb, container, CPTAPI.getPerformanceControls()));
		}
		
		if(CPTAPI.getEvaluationControls().getValue().equals(Config.EVALUTIL)){
			this.widgets.add(new UtilCapacityTreeWidget(cb, container, CPTAPI.getPerformanceControls()));
		}
		
		if(CPTAPI.getEvaluationControls().getValue().equals(Config.EVALPOPU)){
			this.widgets.add(new PopulationTreeWidget(cb, container, CPTAPI.getPerformanceControls()));
		}
	}

}
