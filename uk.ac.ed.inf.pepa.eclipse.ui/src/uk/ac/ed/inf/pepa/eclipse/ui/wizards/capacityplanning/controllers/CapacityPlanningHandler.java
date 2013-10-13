package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.ConfigurationText;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.ConfigurationWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.largescale.NonNegativeDoubleConfigurationText;
import uk.ac.ed.inf.pepa.eclipse.ui.largescale.PositiveIntegerConfigurationText;

public class CapacityPlanningHandler{
	
	protected OptionMap map;
	protected IValidationCallback cb;
	protected ArrayList<ConfigurationWidget> widgets = new ArrayList<ConfigurationWidget>();
	
	public CapacityPlanningHandler(OptionMap map,
			IValidationCallback cb) {
		this.map = map;
		this.cb = cb;
	}
	
	public final Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layoutTP = new GridLayout();
		layoutTP.marginWidth = 5;
		layoutTP.numColumns = 2;
		composite.setLayout(layoutTP);
		fillDialogArea(composite);
		return composite;

	}
	
	protected void fillDialogArea(Composite composite) {

		configure(composite, "Start time", OptionMap.ODE_START_TIME, true);
		configure(composite, "Stop time", OptionMap.ODE_STOP_TIME, true);
		configure(composite, "Number of time points", OptionMap.ODE_STEP, false);
		configure(composite, "Absolute tolerance", OptionMap.ODE_ATOL, true);
		configure(composite, "Relative tolerance", OptionMap.ODE_RTOL, true);
		configure(composite, "Steady-state convergercence norm", OptionMap.ODE_STEADY_STATE_NORM, true);

	}
	
	protected ConfigurationText configure(Composite composite,
			String labelText, 
			String key, 
			boolean isDouble) {
		
		return (ConfigurationText) configureComplete(composite, labelText, key,
				isDouble)[0];
	}
	
	protected Object[] configureComplete(Composite composite, 
			String labelText,
			String key, 
			boolean isDouble) {
		
		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ConfigurationText configurationText;
		if (isDouble)
			configurationText = new NonNegativeDoubleConfigurationText(map, key, cb);
		else
			configurationText = new PositiveIntegerConfigurationText(map, key, cb);
		configurationText.createControl(composite);
		configurationText.control.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		widgets.add(configurationText);
		return new Object[] { configurationText, label };
	}
	
	public boolean isConfigurationValid() {
		for (ConfigurationWidget w : widgets)
			if (!w.isValid())
				return false;
		this.updateOptionMap();
		return true;
	}
	
	public OptionMap updateOptionMap() {
		for (ConfigurationWidget w : widgets) {
			map.put(w.getProperty(), w.getValue());
		}
		map.put(OptionMap.ODE_INTERPOLATION, false);
		return map;
	}
}