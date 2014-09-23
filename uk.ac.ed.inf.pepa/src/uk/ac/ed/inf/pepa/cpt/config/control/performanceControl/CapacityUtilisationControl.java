package uk.ac.ed.inf.pepa.cpt.config.control.performanceControl;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.cpt.config.control.PerformanceControl;
import uk.ac.ed.inf.pepa.cpt.config.lists.IOptionList;
import uk.ac.ed.inf.pepa.cpt.config.lists.ProcessList;
import uk.ac.ed.inf.pepa.largescale.CapacityUtilisationCalculation;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.DefaultCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

public class CapacityUtilisationControl extends PerformanceControl {

	IParametricDerivationGraph graph;
	
	public CapacityUtilisationControl(IOptionList options, IParametricDerivationGraph graph) {
		super(options);
		this.graph = graph;
	}

	@Override
	public boolean validate() {
		
		return  ((ProcessList) this.myOptions).getSelectedProcessIds().length > 0;
	}

	@Override
	public IStatisticsCollector[] getCollectors(IPointEstimator[] estimators) {
		return DefaultCollector.create(estimators);
	}
	
	@Override
	public IPointEstimator[] getEstimators() {
		Integer[] ids = ((ProcessList) this.myOptions).getSelectedProcessIds();
		CapacityUtilisationCalculation[] calculators = new CapacityUtilisationCalculation[ids.length];
		for (int i = 0; i < ids.length; i++) {
			calculators[i] = new CapacityUtilisationCalculation(
					((ProcessList) this.myOptions).getSeqIndexFromProcessID(ids[i].shortValue()),
					this.graph);
		}
		return calculators;
	}

	@Override
	public ArrayList<HashMap<String, Short>> getOptions() {
		return ((ProcessList) this.myOptions).getProcessIDOfSequentialComponents();
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

	@Override
	public String[] getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setValue(String component, String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

}
