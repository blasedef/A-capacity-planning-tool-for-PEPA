package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

public class ODEConfig extends Configuration{
	
	private IPointEstimator[] estimators;
	private IStatisticsCollector[] collectors;
	private String[] labels;
	protected OptionMap map;

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
	
	public OptionMap getOptionMap(){
		return this.map;
	}
	
	public void setOptionMap(OptionMap map){
		this.map = map;
	}

	@Override
	public String getDescription() {
		OptionMap optionMap = map;
		
		String output = "ODE Configuration: \n"; 
		output += "Start time " + optionMap.get(OptionMap.ODE_STOP_TIME) + "\n";
		output += "Stop time " + optionMap.get(OptionMap.ODE_STOP_TIME) + "\n";
		output += "Number of time points " + optionMap.get(OptionMap.ODE_STEP)  + "\n";
		output += "Absolute tolerance " + optionMap.get(OptionMap.ODE_ATOL) + "\n";
		output += "Relative tolerance " + optionMap.get(OptionMap.ODE_RTOL) + "\n";
		output += "Steady-state convergercence norm " + optionMap.get(OptionMap.ODE_STEADY_STATE_NORM) + "\n";
		
		return output;
	}

}