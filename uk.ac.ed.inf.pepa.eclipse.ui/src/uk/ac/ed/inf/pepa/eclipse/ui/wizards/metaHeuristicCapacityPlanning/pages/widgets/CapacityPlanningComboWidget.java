package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Configuration;


public class CapacityPlanningComboWidget extends CapacityPlanningWidget {
	
	Configuration model;
	final Combo combo;
	
	public CapacityPlanningComboWidget(Composite container, Configuration model, final IValidationCallback cb) {
		super(model.getTitle(),model.getValue(),cb);
		this.model = model;
		
		Label label = new Label(container,SWT.LEFT);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(key + ":");
		combo = new Combo(container, SWT.READ_ONLY | SWT.NONE);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setItems(model.getOptions());
		combo.setText(model.getOptions()[0]);

		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//ready the value
				setValue();	
				//call back...
				cb.validate();
			}
		});
	
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void setValue() {
		this.model.setValue(combo.getText());
	}


}
