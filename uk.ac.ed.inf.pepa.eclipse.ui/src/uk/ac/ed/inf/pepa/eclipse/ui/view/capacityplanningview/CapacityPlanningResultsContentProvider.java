package uk.ac.ed.inf.pepa.eclipse.ui.view.capacityplanningview;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Results;


public class CapacityPlanningResultsContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		Results result = (Results) inputElement;
		return new String[] {result.key, result.value}; 
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

}
