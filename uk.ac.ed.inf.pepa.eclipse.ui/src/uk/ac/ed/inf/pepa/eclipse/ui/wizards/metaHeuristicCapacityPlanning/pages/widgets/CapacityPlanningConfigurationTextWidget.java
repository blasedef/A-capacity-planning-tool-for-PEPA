package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.CapacityPlanningOptionsMap;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.MetaHeuristicCapacityPlanningWizardPage;


public class CapacityPlanningConfigurationTextWidget extends CapacityPlanningWidget {
	
	public CapacityPlanningConfigurationTextWidget(Composite container, String labelText, Integer type, WizardPage w) {
		
		final String target = labelText;
		final Integer test = type;
		final MetaHeuristicCapacityPlanningWizardPage listeningWizard = (MetaHeuristicCapacityPlanningWizardPage) w;
		
		Label aLabel = new Label(container,SWT.CENTER);
		aLabel.setText(target + ":");
		final Text aText = new Text(container, SWT.SIMPLE);
		aText.setText("" + CapacityPlanningOptionsMap.optionMap.get(target));

		aText.addListener(SWT.Modify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String text = aText.getText();
				
				if(CapacityPlanningOptionsMap.testType(text, CapacityPlanningOptionsMap.INTEGER)){ 
						if (CapacityPlanningOptionsMap.INTEGER == test){
							if(CapacityPlanningOptionsMap.testXLessThanY("0", text, test)){
								System.out.println("Int");
								System.out.println(text);
								listeningWizard.setErrorMessage(null);
								CapacityPlanningOptionsMap.optionMap.put(target, Double.parseDouble(text));
							} else {
								listeningWizard.setErrorMessage(target + " needs to be above 0");
							}
						} else {
							listeningWizard.setErrorMessage(target + " requires Whole number");
						}
					
				} else if (CapacityPlanningOptionsMap.testType(text, CapacityPlanningOptionsMap.DOUBLE)){
						if (CapacityPlanningOptionsMap.DOUBLE == test){
							if(CapacityPlanningOptionsMap.testXLessThanOrEqualToY("0.0", text, test)){
								if(CapacityPlanningOptionsMap.testXLessThanOrEqualToY(text, "1.0", test)){
									System.out.println("Dub");
									System.out.println(text);
									listeningWizard.setErrorMessage(null);
									CapacityPlanningOptionsMap.optionMap.put(target, Double.parseDouble(text));
								} else {
									listeningWizard.setErrorMessage(target + " needs to less than or equal to 1.0");
								}
							} else {
								listeningWizard.setErrorMessage(target + " needs to be greater than or equal to 0.0");
							}
						} else {
							listeningWizard.setErrorMessage(target + " requires Real number");
						}
					
				} else {
					listeningWizard.setErrorMessage(target + " requires values");
				}
				
			}
		});
		
	
	}

}
