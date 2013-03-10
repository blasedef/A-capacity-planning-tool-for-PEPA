package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;

/**
 * Used to pass the setup values on to the MetaHeuristic
 * @author twig
 *
 */
public class CapacityPlanningAnalysisParameters {
	
	
	//Initial
	public static IPepaModel model = null;
	public static IParametricDerivationGraph fGraph = null;
	
	//Choice
	//0 = throughput, 1 = response
	public static int performanceRequirementChoice;
	//0 = HC, 1 = GA
	public static int metaHeuristicChoice;
	
	//PVr
	public static IPointEstimator[] performanceMetrics = null;
	public static OptionMap fOptionMap = null;
	public static IStatisticsCollector[] collectors = null;
	public static String[] targetLabels = null;
	public static String[] allLabels = null;
	
	//fitness function
	public static String[] nonTargetRelatedPerformanceLabels = {"Minimum Population", "Maximum Population"};
	public static String[] targetRelatedPerformanceLabels = {"Target", "Weight"};
	public static String[] targetRelatedValidationTypes = {"doubleGT0", "percent"};
	public static String[] nonTargetRelatedValidationTypes = {"intGT0", "intGT0"}; 
	public static Double metaheuristicParametersMinimumPopulation;
	public static Double metaheuristicParametersMaximumPopulation;
	public static Map<String, Double> pvTargetValues = new HashMap<String, Double>();
	public static Map<String, Double> pvWeightingValues = new HashMap<String, Double>();
	public static double maximumPossibleAgentCount;
	
	//MH setup parameters
	public static String[] mlabels;
	public static String[] mTypes;
	public static Map<String, String> mLabelsAndTypes= new HashMap<String, String>();
	public static Map<String, Double> metaheuristicParameters = new HashMap<String, Double>();
	public static int candidatePopulationSize;
	
	//MHworking variables
	public static Map<String, Double> originalSystemEquation = new HashMap<String, Double>();
	public static ModelObject original;
	public static ModelObject best;
	public static boolean performanceRequirementTargetLimit;
	
	//output
	public static String source;
	
	
	
	
	/**
	 * Build one object for storing all information for this CP package
	 * @param model
	 */
	public CapacityPlanningAnalysisParameters(IPepaModel model) {
		
		//####---RESET
		//init
		CapacityPlanningAnalysisParameters.model = null;
		CapacityPlanningAnalysisParameters.fGraph = null;
		CapacityPlanningAnalysisParameters.fOptionMap = null;
		
		//Choice
		CapacityPlanningAnalysisParameters.performanceRequirementChoice = 0;
		CapacityPlanningAnalysisParameters.performanceRequirementTargetLimit = true;
		CapacityPlanningAnalysisParameters.metaHeuristicChoice = 0;
		
		//PVr
		
		//MHr
		CapacityPlanningAnalysisParameters.maximumPossibleAgentCount = 0;
		
		//output
		CapacityPlanningAnalysisParameters.source = "";
		
		//####---INIT
		//Store model
		CapacityPlanningAnalysisParameters.model = (IPepaModel) model;
		
		//update Graph
		CapacityPlanningAnalysisParameters.updateFGraph();
		
		//setup PVRelated 
		CapacityPlanningAnalysisParameters.fOptionMap = model.getOptionMap();
	    
	    //setup output
	    
	}
	
	//--------------------------------------------------------------Initialisation
	
	/**
	 * Get an fGraph
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static void updateFGraph(){
	
		try{
			//so this is how to make the graph :)
			CapacityPlanningAnalysisParameters.fGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(model.getAST(), null);
			
		} catch (InterruptedException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Cancel Acknowledgement",
					"The ODE generation process has been cancelled");
			
		} catch (DifferentialAnalysisException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Differential error",
					e.getMessage());
			
		}
	}
	
	
	//--------------------------------------------------------------Initialisation End
	
	
	//--------------------------------------------------------------Choice
	
	public static int getPerformanceChoice(){
		return CapacityPlanningAnalysisParameters.performanceRequirementChoice;
	}
	
	public static int getMetaHeuristicChoice() {
		return CapacityPlanningAnalysisParameters.metaHeuristicChoice;
	}
	
	
	//--------------------------------------------------------------Choice End
	
	
	//--------------------------------------------------------------Fitness Function Related
	
	/**
	 * set the Maximum Agent count
	 */
	public static void updateMaximumPossibleAgentCount() {
		CapacityPlanningAnalysisParameters.maximumPossibleAgentCount = (CapacityPlanningAnalysisParameters.metaheuristicParametersMaximumPopulation
				- (CapacityPlanningAnalysisParameters.metaheuristicParametersMinimumPopulation - 1)) * originalSystemEquation.size();
	}
	
	
	//--------------------------------------------------------------Fitness Function Related End
	
	//--------------------------------------------------------------MH (Meta Heuristic) Related
	
	/**
	 * All metaheuristic required inputs and labels for Hill Climbing
	 * 
	 */
	public static void updateHCMetaheuristicParameters(){
		String[] mlabels = {"Mutation Probability:","Performance to Population:","Generations:"};
		String[] mTypes = {"percent","percent","intGT0"};
		
		//the parameter labels
		CapacityPlanningAnalysisParameters.mlabels = mlabels;
		//the type of validation required on the above
		//intGT0 means an integer greater than 0
		//percent means a double between 0.0 and 1.0
		CapacityPlanningAnalysisParameters.mTypes = mTypes;
		for(int i = 0; i < mlabels.length; i++){
			CapacityPlanningAnalysisParameters.mLabelsAndTypes.put(mlabels[i],mTypes[i]);
				if(mTypes[i].equals("intGT0")){
					CapacityPlanningAnalysisParameters.metaheuristicParameters.put(mlabels[i], 1.0);
				} else {
					CapacityPlanningAnalysisParameters.metaheuristicParameters.put(mlabels[i], 0.5);
				}
		}
		
		CapacityPlanningAnalysisParameters.candidatePopulationSize = 1;
		
	}
	
	/**
	 * All metaheuristic required inputs and labels for Genetic Algorithms
	 * 
	 */
	public static void updateGAMetaheuristicParameters(){
		String[] mlabels = {"Mutation Probability:","Performance to Population:","Generations:","Candidate Population Size:"};
		String[] mTypes = {"percent","percent","intGT0","intGT0"};
		
		//the parameter labels
		CapacityPlanningAnalysisParameters.mlabels = mlabels;
		//the type of validation required on the above
		//intGT0 means an integer greater than 0
		//percent means a double between 0.0 and 1.0
		CapacityPlanningAnalysisParameters.mTypes = mTypes;
		for(int i = 0; i < mlabels.length; i++){
			CapacityPlanningAnalysisParameters.mLabelsAndTypes.put(mlabels[i],mTypes[i]);
				if(mTypes[i].equals("intGT0")){
					CapacityPlanningAnalysisParameters.metaheuristicParameters.put(mlabels[i], 1.0);
				} else {
					CapacityPlanningAnalysisParameters.metaheuristicParameters.put(mlabels[i], 0.5);
				}
		}
		
	}
	
	/**
	 * Validation for page inputs
	 * @param value
	 * @param type
	 * @return
	 * @throws NumberFormatException
	 */
	public static boolean testValidation(String value, String type) throws NumberFormatException{
		boolean test = false;
		if(type.equals("intGT0")){
			int v = Integer.valueOf(value);
			if(v > 0){
				test = true;
			} else {
				test = false;
			}
			return test;
		} else if (type.equals("doubleGT0")){
			double v = Double.valueOf(value);
			if(v > 0.0){
				test = true;
			} else {
				test = false;
			}
			return test;
		} else {
			double v = Double.valueOf(value);
			if(v >= 0.0 && v <= 1.0){
				test = true;
			} else {
				test = false;
			}
			return test;
		}
	}
	
	//--------------------------------------------------------------MH Value Related End

	
	//--------------------------------------------------------------MH Running methods

	public static void makeOriginal(IProgressMonitor monitor) {
		
		CapacityPlanningAnalysisParameters.original = new ModelObject(monitor);
		CapacityPlanningAnalysisParameters.originalSystemEquation = CapacityPlanningAnalysisParameters.original.getSystemEquation();
		CapacityPlanningAnalysisParameters.updateMaximumPossibleAgentCount();
		CapacityPlanningAnalysisParameters.source += CapacityPlanningAnalysisParameters.original.toString() + "\n";
	}
	
	//--------------------------------------------------------------MH Running methods End


}
