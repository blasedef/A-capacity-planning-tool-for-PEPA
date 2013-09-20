package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.CapacityPlanningOptionsMap;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.MetaHeuristicCapacityPlanningWizardPage;


public class CapacityPlanningComboWidget extends CapacityPlanningWidget {
	
	public CapacityPlanningComboWidget(Composite container, String labelText, String[] combos, WizardPage w) {
		
		final String targetText = labelText;
		final MetaHeuristicCapacityPlanningWizardPage listeningWizard = (MetaHeuristicCapacityPlanningWizardPage) w;
		
		Label aLabel = new Label(container,SWT.CENTER);
		aLabel.setText(targetText + ":");
		final Combo aCombo = new Combo(container, SWT.SIMPLE);
		aCombo.setItems(combos);
		aCombo.setText(combos[0]);

		aCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
					String key = targetText;
					Number value = CapacityPlanningOptionsMap.optionMap.get(aCombo.getText());
					CapacityPlanningOptionsMap.optionMap.put(key,value);
					CapacityPlanningOptionsMap.optionMap_S.put(key, aCombo.getText());
					listeningWizard.updateNextPage();
			}
		});
	
	}

}
