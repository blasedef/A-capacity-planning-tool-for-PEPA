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
	final Combo aCombo;
	
	public CapacityPlanningComboWidget(Composite container, Configuration model, final IValidationCallback cb) {
		super(model.getValue(),cb,model.getTitle());
		this.model = model;
		
		Label aLabel = new Label(container,SWT.LEFT);
		aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aLabel.setText(key + ":");
		aCombo = new Combo(container, SWT.READ_ONLY | SWT.NONE);
		aCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aCombo.setItems(model.getOptions());
		aCombo.setText(model.getOptions()[0]);

		aCombo.addSelectionListener(new SelectionAdapter() {
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
		this.model.setValue(aCombo.getText());
	}


}
