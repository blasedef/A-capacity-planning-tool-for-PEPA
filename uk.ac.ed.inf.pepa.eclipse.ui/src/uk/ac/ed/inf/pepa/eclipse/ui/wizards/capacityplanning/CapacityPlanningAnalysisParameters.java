package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.HashMap;
import java.util.Map;

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
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ASTVisitor;
import uk.ac.ed.inf.pepa.parsing.ActionTypeNode;
import uk.ac.ed.inf.pepa.parsing.ActivityNode;
import uk.ac.ed.inf.pepa.parsing.AggregationNode;
import uk.ac.ed.inf.pepa.parsing.BinaryOperatorRateNode;
import uk.ac.ed.inf.pepa.parsing.ChoiceNode;
import uk.ac.ed.inf.pepa.parsing.ConstantProcessNode;
import uk.ac.ed.inf.pepa.parsing.CooperationNode;
import uk.ac.ed.inf.pepa.parsing.HidingNode;
import uk.ac.ed.inf.pepa.parsing.ModelNode;
import uk.ac.ed.inf.pepa.parsing.PassiveRateNode;
import uk.ac.ed.inf.pepa.parsing.PrefixNode;
import uk.ac.ed.inf.pepa.parsing.ProcessDefinitionNode;
import uk.ac.ed.inf.pepa.parsing.RateDefinitionNode;
import uk.ac.ed.inf.pepa.parsing.RateDoubleNode;
import uk.ac.ed.inf.pepa.parsing.UnknownActionTypeNode;
import uk.ac.ed.inf.pepa.parsing.VariableRateNode;
import uk.ac.ed.inf.pepa.parsing.WildcardCooperationNode;

/**
 * Used to pass the setup values on to the MetaHeuristic
 * @author twig
 *
 */
public class CapacityPlanningAnalysisParameters {
	
	public static IPointEstimator[] performanceMetrics = null;
	public static OptionMap fOptionMap = null;
	public static IStatisticsCollector[] collectors = null;
	public static String[] labels = null;
	public static IPepaModel model = null;
	public static String[] mlabels;
	public static String[] mTypes;
	public static Map<String, String> mLabelsAndTypes= new HashMap<String, String>();
	public static Map<String, Double> metaheuristicParameters = new HashMap<String, Double>();
	public static Map<String, Double> originalSystemEquation = new HashMap<String, Double>();
	public static Map<String, Double> incomingSystemEquation = new HashMap<String, Double>();
	public static Map<String, Double> targetValues = new HashMap<String, Double>();
	public static double maximumPossibleAgentCount;
	public static ModelObject original;
	public static ModelObject best;
	public static int performanceRequirementType;
	public static boolean performanceRequirementTargetLimit = true;
	public static String source = "";
	
	
	/**
	 * Build one object for storing all information for the Metaheuristic
	 * @param model
	 */
	public CapacityPlanningAnalysisParameters(IPepaModel model) {
		CapacityPlanningAnalysisParameters.model = (IPepaModel) model;
	    fOptionMap = model.getOptionMap();
	    setupMetaheuristicParameters();
	    CapacityPlanningAnalysisParameters.source = "";
	    maximumPossibleAgentCount = 0;
	}
	
	/**
	 * set the Maximum Agent count
	 */
	public static void setMaximumPossibleAgentCount() {
		CapacityPlanningAnalysisParameters.maximumPossibleAgentCount = (CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Maximum Population:")
				- (CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Minimum Population:") - 1)) * originalSystemEquation.size();
	}
	
	/**
	 * One place to set all the metaheuristic values,
	 * this is quite messy...
	 */
	private void setupMetaheuristicParameters(){
		String[] mlabels = {"Minimum Population:","Maximum Population:","Mutation Probability:","Performance to Population:","Generations:"};
		String[] mTypes = {"intGT0","intGT0","percent","percent","intGT0"};
		
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
	 * Validation for inputs
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

	public static void makeOriginal(IProgressMonitor monitor) {
		
		CapacityPlanningAnalysisParameters.original = new ModelObject(monitor);
		CapacityPlanningAnalysisParameters.originalSystemEquation = CapacityPlanningAnalysisParameters.original.getSystemEquation();
		CapacityPlanningAnalysisParameters.setMaximumPossibleAgentCount();
		CapacityPlanningAnalysisParameters.source += CapacityPlanningAnalysisParameters.original.toString() + "\n";
	}
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static IParametricDerivationGraph getFGraph(){
	
		IParametricDerivationGraph fGraph = null;
		
		try{
			//so this is how to make the graph :)
			fGraph = ParametricDerivationGraphBuilder
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
		
		return fGraph;
	}


}
