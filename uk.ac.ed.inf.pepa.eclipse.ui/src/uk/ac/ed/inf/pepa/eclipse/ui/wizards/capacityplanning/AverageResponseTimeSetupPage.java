package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.largescale.AverageResponseTimeCalculation;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.expressions.Coordinate;
import uk.ac.ed.inf.pepa.largescale.simulation.AverageResponseTimeCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

public class AverageResponseTimeSetupPage extends WizardPage implements IODESolution {

	//model bits
	private IParametricDerivationGraph fGraph;
	private SolverOptionsHandler fSolverOptionsHandler;
	private boolean solverReturned = false;
	private boolean actionCallbackReturned = false;
	protected Text filterText;
	private CheckboxTreeViewer viewer;
	private ViewerFilter filter;
	
	/**
	 * This is used to get the component names from the system equation for a tree/table
	 * @author twig
	 *
	 */
	private class ResponseTimeContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ISequentialComponent) {
				Set<Entry<Short, Coordinate>> mapping = ((ISequentialComponent) parentElement)
						.getComponentMapping();
				Integer[] children = new Integer[mapping.size()];
				int i = 0;
				for (Entry<Short, Coordinate> entry : mapping) {
					children[i++] = entry.getValue().getCoordinate();
				}
				return children;
			} else
				return null;
		}

		public Object getParent(Object element) {
			if (element instanceof ISequentialComponent[])
				return null;
			if (element instanceof ISequentialComponent) {
				return fGraph.getSequentialComponents();
			}
			if (element instanceof Integer) {
				for (ISequentialComponent c : fGraph
						.getSequentialComponents())
					for (Entry<Short, Coordinate> entry : c
							.getComponentMapping())
						if (entry.getValue().getCoordinate() == ((int) (Integer) element))
							return c;
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof Integer)
				return false;
			else
				return true;
		}

		public Object[] getElements(Object inputElement) {
			return fGraph.getSequentialComponents();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}
	
	/**
	 * Constructor
	 */
	protected AverageResponseTimeSetupPage() {
		super("Stochastic Search Optimisation");
	    this.setErrorMessage(null);
		this.setPageComplete(false);
	    setTitle("Average Response time setup");
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
	 * The callback came from the solver, is the config valid?
	 */
	private void callBackOnSolverUpdate(){
		this.solverReturned = fSolverOptionsHandler.isConfigurationValid();
		validateMe();
	}
	
	/**
	 * Validates the whole page, sets up new pages, sets parameters for performance value targets
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
	 * Check if items have been checked, then pass on to further validation tests
	 */
	protected void callbackOnActionReturned(){
		this.actionCallbackReturned = viewer.getCheckedElements().length != 0;
		for(int i = 0; i < viewer.getCheckedElements().length; i++){
		}
		this.callBackOnSolverUpdate();
	}

	/**
	 * UI control
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
	 * Taken from other UI stuff, no idea why it is here, will get rid of it
	 * @param composite
	 */
	protected void addOptions(Composite composite) {
		// do nothing;
	}
	
	/**
	 * UI stuff
	 * @return
	 */
	protected String getViewerHeader() {
		return "Average response time";
	}
	
	/**
	 * Viewer for the checklist table
	 * @param composite
	 * @return
	 */
	protected StructuredViewer createViewer(Composite composite) {
		Label actions = new Label(composite, SWT.NONE);
		actions.setText("Select local state in the passage");
		
		GridData actionsData = new GridData(GridData.FILL_HORIZONTAL);
		actionsData.horizontalSpan = 2;
		actions.setLayoutData(actionsData);

		GridData checkListData = new GridData(GridData.FILL_BOTH);
		checkListData.horizontalSpan = 2;
		
		viewer = new CheckboxTreeViewer(composite, SWT.NONE);
		viewer.getTree().setLayoutData(checkListData);
		viewer.setContentProvider(new ResponseTimeContentProvider());
		viewer.setLabelProvider(new LabelProvider() {

			public String getText(Object element) {
				if (element instanceof ISequentialComponent)
					return ((ISequentialComponent) element).getName();
				if (element instanceof Integer)
					return fGraph
							.getSymbolGenerator()
							.getProcessLabel(
									fGraph.getProcessMappings()[(Integer) element]);
				return super.getText(element);
			}
		});
		viewer.setInput(fGraph.getSequentialComponents());
		viewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getElement() instanceof ISequentialComponent
						&& event.getChecked() == true){
					viewer.setChecked(event.getElement(), false);
					
				}
				callbackOnActionReturned();
			}

		});

		return viewer;
	}
	
	//Checklist Performance Dialog
	protected ViewerFilter getViewerFilter() {
		filter = new ViewerFilter() {

			@Override
			public boolean select(Viewer filteredViewer, Object parentElement,
					Object element) {
				if (element instanceof ISequentialComponent
						|| viewer.getChecked(element))
					return true;
				String label = null;
				if (element instanceof Integer)
					label = fGraph
							.getSymbolGenerator()
							.getProcessLabel(
									fGraph.getProcessMappings()[(Integer) element]);
				return label.contains(filterText.getText());
			}
		};
		return filter;
	}

	
	@Override
	public void updateAnalysisParams() {
		CPAParameters.fOptionMap = fSolverOptionsHandler.updateOptionMap();
		CPAParameters.performanceMetrics = getPerformanceMetrics();
		//great so in AverageResponseDialog we use this:
		//CPAParameters.targetLabels = new String[] { "Average response time" };
		//but in Throughput we use this...
		CPAParameters.targetLabels = getTargetLabels();
		//thats fucking annoying considering I have implemented everything else in the style of the later
		//so now I will have to hack it in later.
		CPAParameters.allTargetLabels= getAllLabels();
		CPAParameters.collectors = new IStatisticsCollector[] { new AverageResponseTimeCollector(0, 1) };
	}
	
	/**
	 * No idea, I just know it needs to be used... 
	 * @return
	 */
	protected IPointEstimator[] getPerformanceMetrics() {
		final int[] insystem = new int[viewer.getCheckedElements().length];
		for (int i = 0; i < insystem.length; i++){
			insystem[i] = (Integer) viewer.getCheckedElements()[i];
		}
		
		ISequentialComponent c = (ISequentialComponent) ((ResponseTimeContentProvider) viewer
				.getContentProvider())
				.getParent(viewer.getCheckedElements()[0]);
		
		int j = 0;
		for (; j < fGraph.getSequentialComponents().length; j++)
			if (fGraph.getSequentialComponents()[j] == c)
				break;
		AverageResponseTimeCalculation art = new AverageResponseTimeCalculation(j, insystem, fGraph);
		return new IPointEstimator[] { art.getUsersInSystemEstimator(),art.getIncomingThroughputEstimator() };
	}
	
	/**
	 * So in the throughput case this would return all of the actions that we are trying to asses
	 * in this case apparently we just set the label.... 
	 * @return
	 */
	protected String[] getTargetLabels() {
		Object[] checkedElements = viewer.getCheckedElements();
		String[] labels = new String[checkedElements.length];
		for (int i = 0; i < checkedElements.length; i++) {
			labels[i] = fGraph
					.getSymbolGenerator()
					.getProcessLabel(
							fGraph.getProcessMappings()[i]);
		}
		return labels;
	}
	
	/**
	 * Used later to assess if one or many performance targets have been selected
	 * @return
	 */
	protected String[] getAllLabels() {
		ArrayList<String> labels = new ArrayList<String>();
		for(ISequentialComponent c : fGraph.getSequentialComponents()){
			Set<Entry<Short, Coordinate>> d = c.getComponentMapping();
			for(Entry<Short, Coordinate> entry : d){
				labels.add(fGraph.getSymbolGenerator().getProcessLabel(
						fGraph.getProcessMappings()[entry.getValue().getCoordinate()]));
			}
		}
		String[] output = new String[labels.size()];
		return labels.toArray(output);
	}
}
