package uk.ac.ed.inf.pepa.cpt;

import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.ListControl;
import uk.ac.ed.inf.pepa.cpt.config.control.ParameterControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PerformanceControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PopulationControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.PSOControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.TargetControl;
import uk.ac.ed.inf.pepa.cpt.searchEngine.CPT;
import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class CPTAPI {
	
	private static Config configuration = null;
	private static CPT cpt = null; 
	
	public static void setPSOValues(){
		CPTAPI.configuration.setPSOvalues();
	}
	
	public static void setModel(ModelNode node){
		CPTAPI.configuration = new Config(node);
	}
	
	public static ListControl getEvaluationControls(){
		return CPTAPI.configuration.evaluationController;
	}
	
	public static ListControl getSearchControls(){
		return CPTAPI.configuration.searchController;
	}
	
	public static ParameterControl getExperimentControls(){
		return CPTAPI.configuration.experimentsController;
	}
	
	public static ParameterControl getGenerationControls(){
		return CPTAPI.configuration.generationController;
	}
	
	public static ParameterControl getMHParameterControls(){
		if(CPTAPI.getSearchControls().getValue().equals(Config.SEARCHSINGLE)){
			return CPTAPI.configuration.psoController;
		} else {
			return CPTAPI.configuration.hillController;
		}
	}
	
//	public static ParameterControl getHCParameterControls(){
//		return CPTAPI.configuration.hillController;
//	}
//	
//	public static ParameterControl getPSOParameterControls(){
//		return CPTAPI.configuration.psoController;
//	}
	
	public static PSOControl getPSORangeParameterControls(){
		return CPTAPI.configuration.psoRangeController;
	}
	
	public static ParameterControl getFitnessWeightControls(){
		return CPTAPI.configuration.fitnessFunctionWeightController;
	}
	
	public static PerformanceControl getPerformanceControls(){
		return CPTAPI.configuration.actionAndProcessSelectionController;
	}
	
	public static PopulationControl getPopulationControls(){
		return CPTAPI.configuration.rateAndComponentRangeAndWeightController;
	}
	
	public static void printConfiguration(){
		System.out.println("##############");
		CPTAPI.configuration.toPrint();
		System.out.println("##############");
	}
	
	public static IParametricDerivationGraph getGraph(){
		return CPTAPI.configuration.getGraph();
	}
	
	public static ModelNode getModel(){
		return CPTAPI.configuration.getNode();
	}
	
	public static OptionMap getOptionMap(){
		return CPTAPI.configuration.getOptionMap();
	}
	
	public static IPointEstimator[] getEstimators(){
		return CPTAPI.configuration.getEstimators();
	}
	
	public static IStatisticsCollector[] getCollectors(){
		return CPTAPI.configuration.getCollectors();
	}
	
	public static String[] getLabels(){
		return CPTAPI.configuration.getLabels();
	}
	
	public static ListControl getDomainControl(){
		return CPTAPI.configuration.domainController;
	}
	
	public static void updateTargetControl(){
		CPTAPI.configuration.targetControl.clearMap();
		CPTAPI.configuration.targetControl.update();
	}
	
	public static TargetControl getTargetControl(){
		return CPTAPI.configuration.targetControl;
	}
	
	public static void createCPT(){
		if(configuration != null){
			cpt = new CPT(null);
		};
	}
	
	public static void startCPT(){
		if(cpt != null){
			cpt.start();
		};
	}

	public static void toJSON() {
		if(cpt != null){
			cpt.jsonNodes();
		};
		
	}

	public static void printQueue() {
		if(cpt != null){
			cpt.printQueue();
		};
		
	}
	
}
