package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.ThroughputCalculation;

public class ThroughputContainer extends ChecklistPerformanceMetricContainer {
	
	private class ThroughputTableProvider extends LabelProvider implements
		ITableLabelProvider {
		
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0)
				if (element instanceof Short)
					return fDerivationGraph.getSymbolGenerator()
							.getActionLabel((Short) element);
				else
					throw new IllegalArgumentException();
			return null;
		}
	}

	public ThroughputContainer(IValidationCallback cb, 
			Composite container, 
			ODEConfig configODE, 
			PEPAConfig configPEPA) {
		super(true, cb, container, configODE, configPEPA);
	}
	
	protected String getViewerHeader() {
		return "Select actions";
	}
	
	ITableLabelProvider getProvider() {
		return new ThroughputTableProvider();
	}

	protected Object getViewerInput() {
		return getAlphabet();
	}

	private Short[] getAlphabet() {
		ArrayList<Short> alphabet = new ArrayList<Short>();
		for (ISequentialComponent c : fDerivationGraph
				.getSequentialComponents())
			for (short actionId : c.getActionAlphabet())
				if (!alphabet.contains(actionId))
					alphabet.add(actionId);
		Collections.sort(alphabet, new Comparator<Short>() {

			public int compare(Short arg0, Short arg1) {
				return fDerivationGraph.getSymbolGenerator().getActionLabel(
						arg0).compareTo(
						fDerivationGraph.getSymbolGenerator().getActionLabel(
								arg1));
			}

		});
		return alphabet.toArray(new Short[alphabet.size()]);
	}
	
	@Override
	public String[] getLabels() {
		Object[] checkedElements = viewer.getCheckedElements();
		String[] labels = new String[checkedElements.length];
		for (int i = 0; i < checkedElements.length; i++) {
			labels[i] = fDerivationGraph.getSymbolGenerator().getActionLabel(
					(Short) checkedElements[i]);
		}
		return labels;
	}

	@Override
	public IPointEstimator[] getPerformanceMetrics() {
		Object[] checkedElements = viewer.getCheckedElements();
		ThroughputCalculation[] calculators = new ThroughputCalculation[checkedElements.length];
		for (int i = 0; i < checkedElements.length; i++) {
			calculators[i] = new ThroughputCalculation(
					(Short) checkedElements[i], fDerivationGraph);
		}
		return calculators;
	}

}