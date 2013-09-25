package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.largescale.*;

public abstract class PerformanceMetricContainer {
	
	protected IValidationCallback cb;
	protected IParametricDerivationGraph fDerivationGraph;
	protected SolverOptionsHandler fSolverOptionsHandler;
	protected Text filterText;

	/*
	 * It is not strictly necessary to have this field because it can be
	 * obtained by fPepaModel
	 */
	protected OptionMap fOptionMap;

	protected IPepaModel fPepaModel;
	
	public PerformanceMetricContainer(boolean supportsTransient, IValidationCallback cb, IParametricDerivationGraph derivationGraph,
			IPepaModel model, Composite container) {
		
		this.cb = cb;
		this.fOptionMap = model.getOptionMap();
		this.fDerivationGraph = derivationGraph;
		this.fSolverOptionsHandler = new ODESolverOptionsHandler(
				supportsTransient, fOptionMap, cb);
		
		Composite composite = (Composite) fSolverOptionsHandler
		.createDialogArea(container);
		
		addOptions(composite);
		Label actions = new Label(composite, SWT.NONE);
		actions.setText(getViewerHeader());
		GridData actionsData = new GridData(GridData.FILL_HORIZONTAL);
		actionsData.horizontalSpan = 2;
		actions.setLayoutData(actionsData);

		filterText = new Text(composite, SWT.SEARCH | SWT.CANCEL);
		filterText.setMessage("Search");
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		textData.horizontalSpan = 2;
		filterText.setLayoutData(textData);

		final StructuredViewer viewer = createViewer(composite);
		final ViewerFilter[] filter = new ViewerFilter[] { getViewerFilter() };

		filterText.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					//viewer.resetFilters();
				}
			}

		});
		filterText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				//viewer.setFilters(filter);
			}

		});
		container.pack();
	
	}
	
	protected abstract void addOptions(Composite composite);
	
	protected abstract String getViewerHeader();
	
	protected abstract StructuredViewer createViewer(Composite composite);
	
	protected abstract ViewerFilter getViewerFilter();
	
	public boolean validConfiguration() {
		return fSolverOptionsHandler.isConfigurationValid();

	}
	
	public abstract IPointEstimator[] getPerformanceMetrics();
	
	public abstract String[] getLabels();
	
}