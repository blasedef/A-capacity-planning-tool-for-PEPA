package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;


import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public class CapacityPlanningConfigurationTextWidget extends CapacityPlanningWidget {
	
	private HashMap<String,Double> map;
	private String type;
	final Text aText;
	
	public CapacityPlanningConfigurationTextWidget(Composite container, 
			String key, 
			String value, 
			HashMap<String,Double> map, 
			String type, 
			final IValidationCallback cb) {
		
		super(key,value,cb);
		this.map = map;
		this.type = type;
		
		Label aLabel = new Label(container,SWT.LEFT);
		aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aLabel.setText(key + ":");
		aText = new Text(container, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		aText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aText.setText("" + value);
		
		aText.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				//call back...
				cb.validate();
			}
			
		});

	}

	@Override
	public boolean isValid() {
		if(type.equals(Config.NATURAL)){
			NaturalParser parser = new NaturalParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		if(type.equals(Config.INTEGER)){
			IntegerParser parser = new IntegerParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		else if(type.equals(Config.PERCENT)){
			PercentParser parser = new PercentParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		} 
		else if(type.equals(Config.DOUBLE)){
			DoubleParser parser = new DoubleParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		else if(type.equals(Config.EVEN)){
			EvenParser parser = new EvenParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		else {
			return false;
		}
	}

	public void setValue() {
		this.map.put(key, Double.parseDouble(aText.getText()));
		this.value = aText.getText();
	}

}
