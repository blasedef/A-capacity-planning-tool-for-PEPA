package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public abstract class CapacityPlanningWidget {
	
	protected IValidationCallback cb;
	protected String value;
	protected String title;
	
	
	public CapacityPlanningWidget(String value, IValidationCallback cb, String title) {
		this.value = value;
		this.title = title;
		this.cb = cb;
	
	}
	
	public abstract boolean isValid();

	public abstract void setValue();
}
