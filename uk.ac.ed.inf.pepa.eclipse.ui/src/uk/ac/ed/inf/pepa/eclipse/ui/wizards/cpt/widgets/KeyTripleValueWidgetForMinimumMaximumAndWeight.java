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

public class KeyTripleValueWidgetForMinimumMaximumAndWeight extends CapacityPlanningWidget {
	
	private String key, value1, value2, value3;
	private final Text text1, text2, text3;

	public KeyTripleValueWidgetForMinimumMaximumAndWeight(final IValidationCallback cb, 
			Composite container, 
			String key, 
			String value1, 
			String value2, 
			String value3, 
			Control control) {
		
		super(cb, container, control);
	
		this.key = key;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
			
		Label label = new Label(container, SWT.SINGLE | SWT.LEFT);
		label.setText(this.key);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		label.setLayoutData(data);
		
		text1 = new Text(container, SWT.SINGLE | SWT.RIGHT | SWT.BORDER);
		text1.setText("" + this.value1);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		text1.setLayoutData(data);
		
		text1.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
		text2 = new Text(container, SWT.SINGLE | SWT.RIGHT | SWT.BORDER);
		text2.setText("" + this.value2);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		text2.setLayoutData(data);
		
		text2.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
		text3 = new Text(container, SWT.SINGLE | SWT.RIGHT | SWT.BORDER);
		text3.setText("" + this.value3);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		text3.setLayoutData(data);
		
		text3.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				cb.validate();
				
			}
			
		});
		
	}

	@Override
	public Response isValid() {
		
		Response response = new Response((control.setValue(this.key, Config.LABMIN, text1.getText()) || 
				control.setValue(this.key, Config.LABMAX, text2.getText())) &&
				control.setValue(this.key, Config.LABWEI, text3.getText()) );
		
		if(!response.valid){
			response.setComplaint("Invalid entry: " + this.key 
					+ " " + text1.getText() 
					+ " " + text2.getText()
					+ " " + text3.getText());
		}
		
		return response;
		
	}

}
