package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Need to sort out this thing!! Needs to be more like TargetSetupPage and use arrays to make the options...
 * @author twig
 *
 */
public class HillClimbingSetupPage extends SetupOptimiserPage {
  
	public HillClimbingSetupPage() {
		super();
		this.setErrorMessage(null);
		this.setPageComplete(false);
		setTitle("Stochastic Search Optimisation");
		setDescription("Setting up Hill Climbing Optimisation");
	}
	  
	public void createInputs(Composite parent){
		
		CPAParameters.updateHCMetaheuristicParameters();
		inputs = new ArrayList<Text>(CPAParameters.mlabels.length);
		validation = new boolean[CPAParameters.mlabels.length];
		
		Composite composite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		composite.setLayout(layout);
		
		hillClimbingSettings(composite);
			
		
	}
	
	protected void hillClimbingSettings(Composite parent){
		Composite composite = new Composite(parent,SWT.NONE | SWT.BORDER);
		GridLayout layout = new GridLayout(2,false);
		composite.setLayout(layout);
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		
		for(int i = 0; i < CPAParameters.mlabels.length; i++){
			
			final int j = i;
			
			Label tempLabel = new Label(composite, SWT.NONE);
			tempLabel.setText(CPAParameters.mlabels[i]);
			tempLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    
		    Text tempText = new Text(composite, textStyle);
		    tempText.setLayoutData(createDefaultGridData());
		    tempText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					
					validate(j);
					
				}
			});
		    inputs.add(tempText);
			
		}
	}
	
	protected void metaHeuristicSettings(Composite composite){
		
	}
	
	@Override
	protected void updateMetaHeuristicParameters() {
		
		for(int i = 0; i < CPAParameters.mlabels.length; i++){
			CPAParameters.metaheuristicParameters.put(CPAParameters.mlabels[i],
					Double.valueOf(this.inputs.get(i).getText()));
		}
		
	}
	  
} 