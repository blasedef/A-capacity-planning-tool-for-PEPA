package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;

public abstract class ChecklistPerformanceMetricContainer extends PerformanceMetricContainer {

	protected CheckboxTableViewer viewer;
	private ViewerFilter filter;

	
	
	public ChecklistPerformanceMetricContainer(boolean supportsTransient, IValidationCallback cb, Composite container) {
		super(supportsTransient, cb, container);
		
		filter = new ViewerFilter() {

			@Override
			public boolean select(Viewer filteredViewer, Object parentElement,
					Object element) {
				if (viewer.getChecked(element))
					return true;
				String label = getProvider().getColumnText(element, 0);
				return label.contains(filterText.getText());
			}
		};
	}

	abstract ITableLabelProvider getProvider();

	abstract Object getViewerInput();
	
	protected void addOptions(Composite composite) {
		// do nothing;
	}

	public abstract String[] getLabels();

	public abstract IPointEstimator[] getPerformanceMetrics();

	protected StructuredViewer createViewer(Composite composite) {

		GridData checkListData = new GridData(GridData.FILL_BOTH);
		checkListData.horizontalSpan = 2;
		viewer = CheckboxTableViewer.newCheckList(composite, SWT.NONE);
		viewer.getTable().setLayoutData(checkListData);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(getProvider());
		viewer.setInput(getViewerInput());
		viewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				cb.validate();
			}

		});

		return viewer;
	}

	@Override
	protected ViewerFilter getViewerFilter() {
		return filter;
	}

	public boolean validConfiguration() {
		return super.validConfiguration()
				&& viewer.getCheckedElements().length != 0;
	}
	
}