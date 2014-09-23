package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public class KeySingleValueWidget extends CapacityPlanningWidget {
	
	private String key, value;
	private Control control;
	private final Text text;

	public KeySingleValueWidget(final IValidationCallback cb, Composite container, String key, String value, Control control) {
		super(cb, container);
	
		
		this.key = key;
		this.value = value;
		this.control = control;
			
		//pad
		Label label = new Label(container, 0);
		label.setText("");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		label.setLayoutData(data);
		
		label = new Label(container, SWT.SINGLE | SWT.LEFT);
		label.setText(this.key);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		label.setLayoutData(data);
		
		text = new Text(container, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		text.setText("" + this.value);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		text.setLayoutData(data);
		
		text.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
		//pad
		label = new Label(container, 0);
		label.setText("");
		data = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		label.setLayoutData(data);
		
	}

	@Override
	public boolean isValid() {
			
		return control.setValue(this.key, text.getText());
		
	}

}
