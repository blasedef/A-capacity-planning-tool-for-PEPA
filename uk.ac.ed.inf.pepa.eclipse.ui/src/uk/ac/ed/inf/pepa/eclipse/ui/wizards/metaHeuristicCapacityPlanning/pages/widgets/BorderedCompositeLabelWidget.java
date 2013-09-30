package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public class BorderedCompositeLabelWidget extends CapacityPlanningWidget{
	
	Composite composite;

	public BorderedCompositeLabelWidget(String heading, String key, String value, IValidationCallback cb,
			  Composite container, int span) {
		super(key, value, cb);
		
		composite = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		composite.setLayoutData(gridData);
		GridLayout layout = new GridLayout();
		layout.numColumns = span;
		composite.setLayout(layout);
		
		Label label = new Label(composite,SWT.NONE | SWT.BOLD);
		GridData gridDataHeading = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading.horizontalSpan = span;
		label.setLayoutData(gridDataHeading);
		label.setText(heading);
		
	}
	
	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void setValue() {
		//do nothing
	}
	
}