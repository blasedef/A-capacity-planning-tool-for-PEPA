package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;


/**
* 
* Using the Model-View-Controller architecture. This is the model and map for the wizard pages
* @author Christopher Williams
*
*/

public abstract class Models {
	
	public static final EvaluatorType evaluator = new EvaluatorType(ModelType.THROUGHPUT_S);
	public static final MetaHeuristicType metaHeuristicType = new MetaHeuristicType(ModelType.HILLCLIMBING_S);
	public static MetaHeuristicNetworkType metaHeuristicNetworkType = new MetaHeuristicNetworkType(ModelType.METAHEURISTICSINGLE_S);
	public static AdditionalCosts additionalCosts = new AdditionalCosts(ModelType.ADDITIONALCOSTSNO_S);
	public static ODEAndPEPAModelConfig oDEConfig = new ODEAndPEPAModelConfig("ODE Config");
}