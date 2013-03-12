package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.ThroughputCalculation;
import uk.ac.ed.inf.pepa.largescale.simulation.DefaultCollector;



public class ThroughputSetupPage extends WizardPage implements IODESolution {

	//model bits
	private IParametricDerivationGraph fGraph;
	private SolverOptionsHandler fSolverOptionsHandler;
	private boolean solverReturned = false;
	private boolean actionCallbackReturned = false;
	protected Text filterText;
	protected CheckboxTableViewer viewer;
	private ViewerFilter filter;
	
	/**
	 * Constructor
	 */
	protected ThroughputSetupPage() {
	    super("Stochastic Search Optimisation");
	    this.setErrorMessage(null);
		this.setPageComplete(false);
	    setTitle("Throughput setup");
	    setDescription("Setting up performance requirement...");
	    this.fGraph = CPAParameters.fGraph;
	    
	    //call back method
	    IValidationCallback callBackOnSolver = new IValidationCallback() {
	    	
	    	public void validate(){
	    		callbackOnActionReturned();
	    	}
	    };
	    
	    this.fSolverOptionsHandler = new ODESolverOptionsHandler(false, CPAParameters.fOptionMap, callBackOnSolver);
	}
	
	/**
	 * The callback came from the solver, is the configuration valid?
	 */
	private void callBackOnSolverUpdate(){
		this.solverReturned = fSolverOptionsHandler.isConfigurationValid();
		validateMe();
	}
	
	/**
	 * UI stuff
	 */
	@Override
	public void createControl(Composite parent) {
		//from PerformanceMetricDialog
		Composite composite = (Composite) fSolverOptionsHandler
				.createDialogArea(parent);
		
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
		
		//end of stuff from PerformanceDialog
		
		setControl(composite);
	}
	
	/**
	 * UI
	 * @return
	 */
	protected String getViewerHeader() {
		return "Select actions";
	}
	
	/**
	 * Some function that I have left in as it was in the XDialog, will delete later
	 * @param composite
	 */
	protected void addOptions(Composite composite) {
		// do nothing;
	}
	
	
	/**
	 * UI stuff
	 * @param parent
	 * @return
	 */
	protected StructuredViewer createViewer(Composite parent) {
		
		Composite composite = parent;
		GridData checkListData = new GridData(GridData.FILL_BOTH);
		checkListData.horizontalSpan = 2;
		viewer = CheckboxTableViewer.newCheckList(composite, SWT.NONE);
		viewer.getTable().setLayoutData(checkListData);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(getProvider());
		viewer.setInput(getViewerInput());
		viewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				callbackOnActionReturned();
			}

		});

		return viewer;
	}
	
	ITableLabelProvider getProvider() {
		return new ThroughputTableProvider();
	}
	
	private class ThroughputTableProvider extends LabelProvider implements
	ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0)
				if (element instanceof Short)
					return fGraph.getSymbolGenerator()
					.getActionLabel((Short) element);
				else
					throw new IllegalArgumentException();
			return null;
		}
	}
	
	protected Object getViewerInput() {
		return getAlphabet();
	}
	
	/**
	 * Return all of the actions from the fGraph
	 * @return
	 */
	private Short[] getAlphabet() {
		ArrayList<Short> alphabet = new ArrayList<Short>();
		for (ISequentialComponent c : fGraph
				.getSequentialComponents())
			for (short actionId : c.getActionAlphabet())
				if (!alphabet.contains(actionId))
					alphabet.add(actionId);
		Collections.sort(alphabet, new Comparator<Short>() {

			public int compare(Short arg0, Short arg1) {
				return fGraph.getSymbolGenerator().getActionLabel(
						arg0).compareTo(
						fGraph.getSymbolGenerator().getActionLabel(
								arg1));
			}

		});
		return alphabet.toArray(new Short[alphabet.size()]);
	}
	
	/**
	 * Checklist, taken from PerformanceMetricsDialog
	 * @return
	 */
	protected ViewerFilter getViewerFilter() {
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
		return filter;
	}
	
	/**
	 * Callback
	 */
	protected void callbackOnActionReturned(){
		this.actionCallbackReturned = viewer.getCheckedElements().length != 0;
		this.callBackOnSolverUpdate();
	}
	
	/**
	 * Validation and ODE parameter update
	 */
	protected void validateMe(){
		if(this.solverReturned && this.actionCallbackReturned){
			updateAnalysisParams();
			((CapacityPlanningWizard) getWizard())
			.addFitnessFunctionPage();
		}
		this.setPageComplete(this.solverReturned && this.actionCallbackReturned);
	}
	
	/**
	 * Update the CPAParameters (ODE)
	 */
	public void updateAnalysisParams() {
		CPAParameters.fOptionMap = fSolverOptionsHandler.updateOptionMap();
		CPAParameters.performanceMetrics = getPerformanceMetrics();
		CPAParameters.targetLabels = getTargetLabels();
		CPAParameters.allTargetLabels= getAllLabels();
		CPAParameters.collectors = DefaultCollector
				.create(CPAParameters.performanceMetrics);
	}
	
	/**
	 * Get the target labels, not used in AverageResponseTime. 
	 * @return
	 */
	protected String[] getTargetLabels() {
		Object[] checkedElements = viewer.getCheckedElements();
		String[] labels = new String[checkedElements.length];
		for (int i = 0; i < checkedElements.length; i++) {
			labels[i] = fGraph.getSymbolGenerator().getActionLabel(
					(Short) checkedElements[i]);
		}
		return labels;
	}
	
	/**
	 * Used later to assess if one or many performance targets have been selected
	 * @return
	 */
	protected String[] getAllLabels() {
		Short[] temp = getAlphabet();
		String[] labels = new String[temp.length];
		for(int i = 0; i < temp.length; i++){
			labels[i] = fGraph.getSymbolGenerator().getActionLabel(temp[i]);
		}
		return labels;
	}
	
	/**
	 * No idea. Performance Metrics here. Calculators else where. Evaluators some where else again.
	 * @return calculators (AKA performance metrics/evaluators)
	 */
	protected IPointEstimator[] getPerformanceMetrics() {
		Object[] checkedElements = viewer.getCheckedElements();
		ThroughputCalculation[] calculators = new ThroughputCalculation[checkedElements.length];
		for (int i = 0; i < checkedElements.length; i++) {
			calculators[i] = new ThroughputCalculation(
					(Short) checkedElements[i], fGraph);
		}
		return calculators;
	}

}
