package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

/**
 * for saving and storing all ODE configuration
 * @author twig
 *
 */
public class ODEConfig {
	
	private IPointEstimator[] estimators;
	private IStatisticsCollector[] collectors;
	private String[] labels;
	protected OptionMap map;

	public ODEConfig() {
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
	
	public OptionMap getOptionMap(){
		return this.map;
	}
	
	public void setOptionMap(OptionMap map){
		this.map = map;
	}

}