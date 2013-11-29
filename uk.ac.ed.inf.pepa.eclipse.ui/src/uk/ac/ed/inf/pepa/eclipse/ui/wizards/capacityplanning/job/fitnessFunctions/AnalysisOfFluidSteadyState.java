package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.PepatoProgressMonitorAdapter;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.ode.SteadyStateRoutine;

public class AnalysisOfFluidSteadyState {

	private double[] results = null;
	private SteadyStateRoutine routine;
	protected String[] labels;
	protected IParametricDerivationGraph derivationGraph;
	protected IPointEstimator[] estimators;
	protected IStatisticsCollector[] collectors;
	protected OptionMap optionMap;

	public AnalysisOfFluidSteadyState(IParametricDerivationGraph derivationGraph, 
			OptionMap map,
			IPointEstimator[] estimators, 
			IStatisticsCollector[] collectors,
			String[] labels, 
			IProgressMonitor monitor) {
		
		this.labels = labels;
		this.derivationGraph = derivationGraph;
		this.estimators = estimators;
		this.collectors = collectors;
		this.optionMap = map;
		
		doRun(monitor);
		
		
	}

	protected IStatus doRun(final IProgressMonitor monitor) {
		routine = new SteadyStateRoutine(optionMap,
				this.derivationGraph);
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
//					notifyProgress();
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
//							notifyProgress();
							units = maxUnits;
						}
					}
				}

//				private void notifyProgress() {
//					monitor.subTask(""
//							+ new Formatter().format(
//									"Time: %6.3f  Convergence: %e", routine
//											.getTimePoint(), routine
//											.getConvergenceNorm()));
//				}
			});
		} catch (DifferentialAnalysisException e) {

			if (e.getKind() != DifferentialAnalysisException.NOT_CONVERGED) {
				return new Status(IStatus.ERROR,
						uk.ac.ed.inf.pepa.eclipse.ui.Activator.ID,
						"An error occurred during steady-state analysis", e);
			}

		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
		try {
			computeResults(routine.getTimePoint(), routine.getSolution());
		} catch (DifferentialAnalysisException e) {
			return new Status(IStatus.ERROR,
					uk.ac.ed.inf.pepa.eclipse.ui.Activator.ID,
					"An error occurred during steady-state analysis", e);
		}
		return Status.OK_STATUS;
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
	
	public HashMap<String, Double> getResults(){
		
		HashMap<String, Double> resultsMap = new HashMap<String, Double>();
		
		if(results == null){
			for(int i = 0; i < labels.length; i++){
				resultsMap.put(labels[i], 10000.0);
			}
			
		} else {
			for(int i = 0; i < labels.length; i++){
				resultsMap.put(labels[i], results[i]);
			}
		}
		
		return resultsMap;
	}

}
