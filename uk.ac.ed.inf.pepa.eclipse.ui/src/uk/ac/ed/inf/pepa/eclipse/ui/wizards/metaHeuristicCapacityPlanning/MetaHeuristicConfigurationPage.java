package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters.MetaHeuristicTypeParameters;

public class MetaHeuristicConfigurationPage extends WizardPage {
	
	private Composite container;
	public MetaHeuristicParameters metaheuristicParameters;

	protected MetaHeuristicConfigurationPage(String s) {
		
		//copy title upwards
		super(s);
		
		//set page title
	    setTitle("Metaheuristic configuration page");
	    
	    //set description underneath
	    setDescription("Configure the metaheuristic...");
	}

	@Override
	public void createControl(Composite parent) {
		//minimum required for a wizard page 1/2
		this.container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		/**
		 * This is used to switch between only configuring the last metaheuristic or the first (which is used in the case of Driven and Pipeline searches)
		 */
		if(MetaHeuristicTypeParameters.METAHEURISTICNETWORK == 0){
			metaheuristicParameters = MetaHeuristicTypeParameters.metaheuristic2;
		} else {
			//for now the same result
			metaheuristicParameters = MetaHeuristicTypeParameters.metaheuristic2;
		}
		hashMapToCollumnsAttributesValues("Metaheuristic Configuration Attributes : Values",metaheuristicParameters.fitnessFunctionAttributesValuesHashMap,container);
		
		//minimum required for a wizard page 2/2
		setControl(container);
		
	}
	
	public void hashMapToCollumnsAttributesValues(String title, HashMap<String, Number> attributeValues, Composite parent){
		this.container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		Label thisSectionsTitleLabel = new Label(container,SWT.CENTER);
		thisSectionsTitleLabel.setText(title);
		
		final Label[] labelArray = new Label[attributeValues.size()];
		final Text[] textArray = new Text[attributeValues.size()];
		int i = 0;
		
		final Label testOutputLabel = new Label(container,SWT.CENTER);
		testOutputLabel.setText("was this");
		
		for(Map.Entry<String, Number> entry : attributeValues.entrySet()){
			String attribute = entry.getKey();
			Number value = entry.getValue();
			
			
			labelArray[i] = new Label(parent,SWT.CENTER);
			labelArray[i].setText(attribute);
			
			textArray[i] = new Text(parent,SWT.SINGLE | SWT.LEFT | SWT.BORDER);
			textArray[i].setText("" + value);
			
			textArray[i].addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					
					testOutputLabel.setText("" + MetaHeuristicTypeParameters.metaheuristic2.METAHEURISTICTYPE + MetaHeuristicTypeParameters.metaHeuristicTypes[MetaHeuristicTypeParameters.metaheuristic2.METAHEURISTICTYPE]);
					
				}
			});
			
			i++;
		}
		
		
	}

}