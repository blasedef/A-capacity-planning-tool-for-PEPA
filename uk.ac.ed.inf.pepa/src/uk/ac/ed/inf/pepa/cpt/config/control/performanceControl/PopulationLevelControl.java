package uk.ac.ed.inf.pepa.cpt.config.control.performanceControl;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.control.PerformanceControl;
import uk.ac.ed.inf.pepa.cpt.config.lists.IOptionList;
import uk.ac.ed.inf.pepa.cpt.config.lists.ProcessList;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.DefaultCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;

public class PopulationLevelControl extends PerformanceControl {

	public PopulationLevelControl(IOptionList options) {
		super(options);
	}

	@Override
	public boolean validate() {
		
		return  ((ProcessList) this.myOptions).getSelectedProcessIds().length > 0;
	}

	@Override
	public IStatisticsCollector[] getCollectors(IPointEstimator[] estimators) {
		return DefaultCollector.create(estimators);
	}
	
	class PopulationEstimator implements IPointEstimator {

		private int index;

		public PopulationEstimator(int index) {
			this.index = index;

		}

		public double computeEstimate(double timePoint, double[] solution)
				throws DifferentialAnalysisException {
			return solution[index];
		}
	}

	@Override
	public IPointEstimator[] getEstimators() {
		Integer[] ids = ((ProcessList) this.myOptions).getSelectedProcessIds();
		IPointEstimator[] estimators = new IPointEstimator[ids.length];
		for (int i = 0; i < ids.length; i++) {
			estimators[i] = new PopulationEstimator(ids[i]);
		}
		return estimators;
	}

	@Override
	public ArrayList<HashMap<String, Short>> getOptions() {
		return ((ProcessList) this.myOptions).getAllProcessIds();
	}

	@Override
	public boolean setSelected(short processId, boolean selected) {
		return ((ProcessList) this.myOptions).setSelectedHandler(processId, selected);
	}

	@Override
	public String[] getLabels() {
		
		Integer[] processIds = ((ProcessList) this.myOptions).getSelectedProcessIds();
		String[] collector = new String[processIds.length];
		
		for(int i = 0; i < processIds.length; i++){
			collector[i] = ((ProcessList) this.myOptions).getLabel(processIds[i].shortValue());
		}
		
		return collector;
	}

	@Override
	public boolean setSelected(String name, boolean selected) {
		return ((ProcessList) this.myOptions).setSelectedHandler(name, selected);
	}

}
