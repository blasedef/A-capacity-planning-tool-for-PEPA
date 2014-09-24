package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public class KeyDoubleValueWidgetForMinAndMax extends CapacityPlanningWidget {
	
	private String key, value1, value2;
	private final Text text1, text2;

	public KeyDoubleValueWidgetForMinAndMax(final IValidationCallback cb, 
			Composite container, String key, String value1, String value2, Control control) {
		super(cb, container, control);
	
		
		this.key = key;
		this.value1 = value1;
		this.value2 = value2;
			
		//pad
		Label label = new Label(container, 0);
		label.setText("");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		label.setLayoutData(data);
		
		label = new Label(container, SWT.SINGLE | SWT.LEFT);
		label.setText(this.key);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		label.setLayoutData(data);
		
		text1 = new Text(container, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		text1.setText("" + this.value1);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		text1.setLayoutData(data);
		
		text1.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
		text2 = new Text(container, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		text2.setText("" + this.value2);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		text2.setLayoutData(data);
		
		text2.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
	}

	@Override
	public Response isValid() {
		
		Response response = new Response(control.setValue(this.key, Config.LABMIN, text1.getText()) || 
				control.setValue(this.key, Config.LABMAX, text2.getText()));
		
		if(!response.valid){
			response.setComplaint("Invalid entry: " + this.key + " " + text1.getText() + " " + text2.getText());
		}
		
		return response;
		
	}

}
