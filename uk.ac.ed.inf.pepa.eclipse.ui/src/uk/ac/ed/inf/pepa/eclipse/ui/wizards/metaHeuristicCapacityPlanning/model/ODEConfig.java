package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

public class ODEConfig extends Configuration{
	
	private IPointEstimator[] estimators;
	private IStatisticsCollector[] collectors;
	private String[] labels;

	public ODEConfig(String key, String value, String[] options) {
		super(key, value, options);
	}

	@Override
	public void setOptions(String option) {
		super.options = null;
		
	}
	
	public void setEstimators(IPointEstimator[] estimators){
		this.estimators = estimators;
	}
	
	public IPointEstimator[] getEstimators(){
		return this.estimators;
	}
	
	public void setCollectors(IStatisticsCollector[] collectors){
		this.collectors = collectors;
	}
	
	public IStatisticsCollector[] getCollectors(){
		return this.collectors;
	}
	
	public void setLabels(String[] labels){
		this.labels = labels;
	}
	
	public String[] getLabels(){
		return this.labels;
	}

}