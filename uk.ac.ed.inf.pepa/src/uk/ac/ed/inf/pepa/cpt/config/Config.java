package uk.ac.ed.inf.pepa.cpt.config;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.control.EListControl;
import uk.ac.ed.inf.pepa.cpt.config.control.ListControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PSOParameterControl;
import uk.ac.ed.inf.pepa.cpt.config.control.ParameterControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PerformanceControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PopulationControl;
import uk.ac.ed.inf.pepa.cpt.config.control.RListControl;
import uk.ac.ed.inf.pepa.cpt.config.control.performanceControl.AverageResponseTimeControl;
import uk.ac.ed.inf.pepa.cpt.config.control.performanceControl.CapacityUtilisationControl;
import uk.ac.ed.inf.pepa.cpt.config.control.performanceControl.PopulationLevelControl;
import uk.ac.ed.inf.pepa.cpt.config.control.performanceControl.ThroughputControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.ComponentControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.PSOControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.RateControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.TargetControl;
import uk.ac.ed.inf.pepa.cpt.config.lists.ActionList;
import uk.ac.ed.inf.pepa.cpt.config.lists.ComponentList;
import uk.ac.ed.inf.pepa.cpt.config.lists.EvaluatorChoiceList;
import uk.ac.ed.inf.pepa.cpt.config.lists.PSOList;
import uk.ac.ed.inf.pepa.cpt.config.lists.ProcessList;
import uk.ac.ed.inf.pepa.cpt.config.lists.DomainChoiceList;
import uk.ac.ed.inf.pepa.cpt.config.lists.RateList;
import uk.ac.ed.inf.pepa.cpt.config.lists.SearchChoiceList;
import uk.ac.ed.inf.pepa.cpt.config.lists.TargetList;
import uk.ac.ed.inf.pepa.cpt.config.parameters.ExperimentParameters;
import uk.ac.ed.inf.pepa.cpt.config.parameters.FitnessFunctionWeightParameters;
import uk.ac.ed.inf.pepa.cpt.config.parameters.GenerationParameters;
import uk.ac.ed.inf.pepa.cpt.config.parameters.HillClimbingParameters;
import uk.ac.ed.inf.pepa.cpt.config.parameters.ParticleSwarmOptimisationParameters;
import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.parsing.ModelNode;



/**
 * Configuration for a capacity planning job. 
 * Also contains hard written strings used in Capacity Planning.
 * 
 * @author twig
 *
 */
public class Config implements ConfigCallBack{
	
	//List labels
	public static final String SEARCH = "Search type choice";
	public static final String EVALUATOR = "Evaluator choice";
	public static final String DOMAIN = "Search domain";
	
	//Input Types
	public static final String NATURAL = "NATURAL"; // >= 1
	public static final String INTEGER = "INTEGER"; // >= 0
	public static final String PERCENT = "PERCENT"; // 0.0 <= x <= 1.0
	public static final String DOUBLE  = "DOUBLE "; // 0.0 <= x 

	//Evaluator related hard coded strings
	public static String EVALARPT = "Average response time";
	public static String EVALTHRO = "Throughput";
	public static String EVALUTIL = "Capacity Utilisation";
	public static String EVALPOPU = "Population Level";
	
	//Domain related
	public static String DOMRAR = "Search on rates";
	public static String DOMCOM = "Search on components";
	public static String DOMBOT = "Search on both";
	
	//Search type related hard coded strings
	public static String SEARCHSINGLE = "Single Particle Swarm Optimisation";
	public static String SEARCHDRIVEN = "Driven Particle Swarm Optimisation";
	
	//lab parameter related hard coded strings
	public static String LABEXP ="Experiments";
	public static String LABGEN ="Generations";
	
	//lab parameter related hard coded strings - metaheuristic
	public static String LABMUT ="Mutation rate";
	public static String LABPOP ="Population count";
	public static String LABLOC ="Personal best";
	public static String LABGLO ="Global best";
	public static String LABORG ="Original velocity";
	
	//lab parameter (ranges) related hard coded strings - metaheuristic
	public static String LABMIN ="Minimum";
	public static String LABMAX ="Maximum";
	public static String LABRAN ="Range";
	public static String LABWEI ="Weight";
	public static String LABSTE ="Step";
	public static String LABTAR ="Target";
	
	//fitnessfunction population vs performance vs .... (maybe ODE fitness later?)
	public static String FITRES = "Resource count";
	public static String FITPER = "Performance distance";
	
	//component parameters
	//public static String LABMIN = "Minimum count";
	//public static String LABMAX = "Maximum count";
	//public static String LABRAN = "Range";
	//public static String LABWEI = "Component weighting";
	
	//rate parameters
	//public static String LABMIN = "Minimum rate";
	//public static String LABMAX = "Maximum count";
	//public static String LABRAN = "Range";
	//public static String LABWEI = "Component weighting";
	
	//models
	private ModelNode node = null;
	private IParametricDerivationGraph graph = null;
	
	//configuration objects
	/**
	 * These are the configuration objects required in order to
	 * run a capacity search.
	 */
	//choose type of performance evaluation
	private EvaluatorChoiceList evaluatorChoiceList;
	//choose type of search
	private SearchChoiceList searchChoiceList;
	//choose search domain
	private DomainChoiceList domainChoiceList;
	//choose number of experiments
	private ExperimentParameters experimentParameters;
	//choose number of generations
	private GenerationParameters generationParameters;
	//set up HillClimbing parameters
	private HillClimbingParameters hillClimbingParameters;
	//set up PSO parameters
	private ParticleSwarmOptimisationParameters particleSwarmOptimisationParameters;
	//set up fitness balance
	private FitnessFunctionWeightParameters fitnessFunctionWeightParameters;
	//set up pso range list
	private PSOList psoList;
	//set up component population ranges
	private ComponentList componentList;
	//set up rate population ranges
	private RateList rateList;
	//choose actions for performance evaluation
	private ActionList actionList;
	//choose processes for performance evaluation
	private ProcessList processList;
	//choose targets for action/process
	private TargetList targetList;
	
	//for ODE solutions
	private IPointEstimator[] estimators;
	private IStatisticsCollector[] collectors;
	private OptionMap map;
	
	/*
	 * Controllers
	 */
	
	//lists
	public ListControl evaluationController;
	public ListControl searchController;
	public ListControl domainController;
	
	//parameters
	public ParameterControl experimentsController;
	public ParameterControl generationController;
	public ParameterControl hillController;
	public ParameterControl psoController;
	public ParameterControl fitnessFunctionWeightController;
	
	//pso range related
	public PSOControl psoRangeController;
	
	//population related
	public PopulationControl rateAndComponentRangeAndWeightController;
	
	//target related 
	public TargetControl targetControl;
	
	//performance related
	public PerformanceControl actionAndProcessSelectionController;
	
	/**
	 * Configuration object, required for a cpt run.
	 * @param node
	 */
	public Config(ModelNode node){
		this.node = node;
		this.graph = Utils.getDevGraphFromAST(node);
		this.map = new OptionMap();
		OptionMap.getDefaultValue(OptionMap.ODE_START_TIME);
		OptionMap.getDefaultValue(OptionMap.ODE_STOP_TIME);
		map.put(OptionMap.ODE_STOP_TIME,500.0);
		OptionMap.getDefaultValue(OptionMap.ODE_STEP);
		OptionMap.getDefaultValue(OptionMap.ODE_ATOL);
		OptionMap.getDefaultValue(OptionMap.ODE_RTOL);
		OptionMap.getDefaultValue(OptionMap.ODE_STEADY_STATE_NORM);
		map.put(OptionMap.ODE_INTERPOLATION,OptionMap.ODE_INTERPOLATION_OFF);
		this.evaluatorChoiceList = new EvaluatorChoiceList();
		this.searchChoiceList = new SearchChoiceList();
		this.domainChoiceList = new DomainChoiceList();
		this.experimentParameters = new ExperimentParameters();
		this.generationParameters = new GenerationParameters();
		this.hillClimbingParameters = new HillClimbingParameters();
		this.particleSwarmOptimisationParameters = new ParticleSwarmOptimisationParameters();
		this.fitnessFunctionWeightParameters = new FitnessFunctionWeightParameters();
		this.psoList = new PSOList();
		this.componentList = new ComponentList(this.graph);
		this.rateList = new RateList(this.node);
		this.actionList = new ActionList(this.graph);
		this.processList = new ProcessList(this.graph);
		this.targetList = new TargetList();
		
		/*
		 * set up controllers - so only use controllers to change underlying data
		 */
		
		this.evaluationController = new EListControl(this.evaluatorChoiceList, this);
		
		this.searchController = new ListControl(this.searchChoiceList);
		
		this.domainController = new RListControl(this.domainChoiceList, this);
		
		this.experimentsController = new ParameterControl(this.experimentParameters);
		this.generationController = new ParameterControl(this.generationParameters);
		
		this.hillController = new ParameterControl(this.hillClimbingParameters);
		this.psoController = new PSOParameterControl(this.particleSwarmOptimisationParameters,this);
		
		this.fitnessFunctionWeightController = new ParameterControl(this.fitnessFunctionWeightParameters);
		
		this.psoRangeController = new PSOControl(psoList);
		
		this.actionAndProcessSelectionController = new ThroughputControl(actionList, this.graph);
		this.rateAndComponentRangeAndWeightController = new ComponentControl(this.componentList);
		
		this.targetControl = new TargetControl(targetList);
		
	}
	
	public void toPrint(){
		
		this.evaluatorChoiceList.printList();
		this.searchChoiceList.printList();
		this.domainChoiceList.printList();
		
		this.fitnessFunctionWeightParameters.toPrint();
		
		this.experimentParameters.toPrint();
		this.generationParameters.toPrint();
		this.hillClimbingParameters.toPrint();
		this.particleSwarmOptimisationParameters.toPrint();
		
		this.psoList.toPrint();
		
		this.componentList.toPrint();
		this.rateList.toPrint();
		
		this.actionList.toPrint();
		
		this.processList.toPrint();
		
		this.targetList.toPrint();
	}

	public void setOptionMap(OptionMap optionMap) {
		this.map = optionMap;
	}

	public OptionMap getOptionMap() {
		return new OptionMap(map);
	}

	public ModelNode getNode() {
		return node;
	}
	
	public IParametricDerivationGraph getGraph(){
		return Utils.getDevGraphFromAST(node);
	}

	@Override
	public void initialiseAndUpdateActionsAndProcesses() {
		
		this.actionList.clearSelection();
		this.processList.clearSelection();
		
		if(this.evaluatorChoiceList.getValue().equals(Config.EVALARPT)){
			
			this.actionAndProcessSelectionController = new AverageResponseTimeControl(processList, this.graph);
			
		} else if (this.evaluatorChoiceList.getValue().equals(Config.EVALTHRO)){
			
			this.actionAndProcessSelectionController = new ThroughputControl(actionList, this.graph);
			
		} else if (this.evaluatorChoiceList.getValue().equals(Config.EVALUTIL)){
			
			this.actionAndProcessSelectionController = new CapacityUtilisationControl(processList, this.graph);
			
		} else {
			
			this.actionAndProcessSelectionController = new PopulationLevelControl(processList);
		}
	}
	
	public boolean setPSOvalues(){
		
		boolean isValid = true;
		
		String[] components = this.psoController.getKeys();
		
		for(int i = 0; i < components.length; i++){
			
			isValid = isValid && this.psoRangeController.setValue(components[i], 
					Config.LABMIN, 
					this.psoController.getValue(components[i]));
			
			isValid = isValid && this.psoRangeController.setValue(components[i], 
					Config.LABMAX, 
					this.psoController.getValue(components[i]));
			
			isValid = isValid && this.psoRangeController.setValue(components[i], 
					Config.LABRAN, 
					"1");
		}
		
		return isValid;
		
	}

	@Override
	public void initialiseAndUpdateComponentsAndRates() {
		
		
		if(domainChoiceList.getValue().equals(Config.DOMCOM)){
			this.rateAndComponentRangeAndWeightController = new ComponentControl(this.componentList);
		}
		if(domainChoiceList.getValue().equals(Config.DOMRAR)){
			this.rateAndComponentRangeAndWeightController = new RateControl(this.rateList);
		}
		if(domainChoiceList.getValue().equals(Config.DOMBOT)){
			this.rateAndComponentRangeAndWeightController = new ComponentControl(this.componentList);
			//and then I guess make a call to change this over to DOMRAR
		}
		
	}

	public IPointEstimator[] getEstimators() {
		this.estimators = this.actionAndProcessSelectionController.getEstimators();
		return this.estimators;
	}

	public IStatisticsCollector[] getCollectors() {
		if(this.estimators != null){
			this.collectors = this.actionAndProcessSelectionController.getCollectors(this.estimators);
			return this.collectors;
		} else {
			return null;
		}
	}
	
	public String[] getLabels() {
		return this.actionAndProcessSelectionController.getLabels();
	}

}
