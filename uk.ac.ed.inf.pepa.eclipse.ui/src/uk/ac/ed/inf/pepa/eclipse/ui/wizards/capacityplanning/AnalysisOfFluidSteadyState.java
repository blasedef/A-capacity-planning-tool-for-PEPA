package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.PepatoProgressMonitorAdapter;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.ode.SteadyStateRoutine;

public class AnalysisOfFluidSteadyState {

	private double[] results = null;
	private Map<String, Double> resultsMap = new HashMap<String, Double>();
	
	private SteadyStateRoutine routine;
	
	protected String[] labels;
	protected IPointEstimator[] estimators;
	protected IStatisticsCollector[] collectors;
	protected OptionMap optionMap;
	protected String name;

	public AnalysisOfFluidSteadyState() {
		
		this.name = "ODE";
		this.labels = CPAParameters.targetLabels;
		this.estimators = CPAParameters.performanceMetrics;
		this.collectors = CPAParameters.collectors;
		this.optionMap = CPAParameters.fOptionMap;
	}
	
	/*
	 * Return results from ODE computation
	 */
	public Map<String, Double> getResults(IParametricDerivationGraph derivationGraph, final IProgressMonitor monitor){
		
		routine = new SteadyStateRoutine(optionMap,
				derivationGraph);
		
		try {
			routine.obtainSteadyState(new PepatoProgressMonitorAdapter(monitor,
					"ODE integration") {

				private long tic;

				private int units;

				private boolean measuring;

				private int maxUnits;

				public void beginTask(int amount) {
					super.beginTask(amount);
					tic = System.currentTimeMillis();
					units = 0;
					measuring = true;
				}

				public void done() {
					notifyProgress();
					super.done();
				}

				public void worked(int amount) {
					super.worked(amount);
					if (measuring) {
						units++;
						if (System.currentTimeMillis() - tic > 800) {
							maxUnits = units;
							measuring = false;
						}
					} else {
						units--;
						if (units == 0) {
							notifyProgress();
							units = maxUnits;
						}
					}
				}

				private void notifyProgress() {
					monitor.subTask(""
							+ new Formatter().format(
									"Time: %6.3f  Convergence: %e", routine
											.getTimePoint(), routine
											.getConvergenceNorm()));
				}
			});
		} catch (DifferentialAnalysisException e) {
			//need to sort this out...

		} catch (InterruptedException e) {
			//need to sort this out...
		}
		
		//here the results are actually computed
		try {
			computeResults(routine.getTimePoint(), routine.getSolution());
		} catch (DifferentialAnalysisException e) {

		}
		
		for(int i = 0; i < results.length; i++){
			this.resultsMap.put(this.labels[i], this.results[i]);
		}
		
		return this.resultsMap;
	}

	private void computeResults(double timePoint, double[] solution)
			throws DifferentialAnalysisException {
		// calculate results anyway
		double[] estimates = new double[estimators.length];
		results = new double[collectors.length];
		
		for (int i = 0; i < estimates.length; i++) {
			estimates[i] = estimators[i].computeEstimate(timePoint, solution);
		}
		
		for (int j = 0; j < collectors.length; j++)
			results[j] = collectors[j].computeObservation(estimates);
	}
	
}
