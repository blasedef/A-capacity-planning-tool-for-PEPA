package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Configuration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.BorderedCompositeLabelWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningComboWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class PerformanceEvaluatorAndMetaHeuristicSelectionPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public PerformanceEvaluatorAndMetaHeuristicSelectionPage(String s) {
		
		//copy title upwards
		super(s,"Performance evaluator and Metaheuristic configuration page",
				"Choose performance evaluation and Metaheuristic configuration...");
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback parentCallBack) {
		
		for(Configuration c : ExperimentConfiguration.performanceEvaluatorAndMetaheuristic){
			new BorderedCompositeLabelWidget(c.getDescription(), null, null, parentCallBack, container, 2);
			widgets.add(new CapacityPlanningComboWidget(container, c, parentCallBack));
		}
		

	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		((MetaHeuristicCapacityPlanningWizard) getWizard()).updateMHAndODEPages();
	}

}
