package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ModelType;

public class CapacityPlanningConfigurationTextWidget extends CapacityPlanningWidget {
	
	private Map<String,Number> map;
	private String type;
	final Text aText;
	
	public CapacityPlanningConfigurationTextWidget(Composite container, String key, String value, Map<String,Number> map, String type, final IValidationCallback cb) {
		super(value,cb,key);
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
		if(type.equals(ModelType.INTEGER)){
			IntegerParser parser = new IntegerParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		else if(type.equals(ModelType.PERCENT)){
			PercentParser parser = new PercentParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		} 
		else if(type.equals(ModelType.DOUBLE)){
			DoubleParser parser = new DoubleParser(aText.getText());
			if(parser.isCorrect()){
				setValue();
			} 
			return parser.isCorrect();
		}
		else {
			return false;
		}
	}

	/**
	 * assumes only called after isValid()
	 */
	@Override
	public void setValue() {
		this.map.put(key, Double.parseDouble(aText.getText()));	
	}

}
