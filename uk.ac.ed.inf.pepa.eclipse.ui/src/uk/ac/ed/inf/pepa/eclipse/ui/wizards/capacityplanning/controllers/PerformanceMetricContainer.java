package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;

public abstract class PerformanceMetricContainer {
	
	protected IValidationCallback cb;
	protected IParametricDerivationGraph fDerivationGraph;
	protected CapacityPlanningHandler capacityPlanningHandler;
	protected Text filterText;
	protected OptionMap fOptionMap;
	protected IPepaModel fPepaModel;
	
	public PerformanceMetricContainer(boolean supportsTransient, 
			IValidationCallback cb, 
			Composite container, 
			ODEConfig configODE,
			PEPAConfig configPEPA) {
		
		this.cb = cb;
		configODE.setOptionMap(configPEPA.getPepaModel().getOptionMap());
		this.fOptionMap = configODE.getOptionMap();
		this.fDerivationGraph = configPEPA.getGraph();
	
	}
	
	public final Control createDialogArea(Composite parent) {
		
		//ODE configuration options
		this.capacityPlanningHandler = new CapacityPlanningHandler(this.fOptionMap, cb);
		Composite composite = (Composite) capacityPlanningHandler.createDialogArea(parent);

		//Bottom box from here
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
					viewer.resetFilters();
				}
			}

		});
		filterText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				viewer.setFilters(filter);
			}

		});
		parent.pack();
		return composite;
	}
	
	
	protected abstract void addOptions(Composite composite);
	
	protected abstract String getViewerHeader();
	
	protected abstract StructuredViewer createViewer(Composite composite);
	
	protected abstract ViewerFilter getViewerFilter();
	
	public boolean validConfiguration() {
		return capacityPlanningHandler.isConfigurationValid();

	}
	
	public CapacityPlanningHandler getSolver(){
		return this.capacityPlanningHandler;
	}
	
	public abstract IPointEstimator[] getPerformanceMetrics();
	
	public abstract String[] getLabels();
	
}