package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
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
	public static int minimumComponentPopulation = 1;
	public static int maximumComponentPopulation = 1;
	public static double mutationProbabilty = 0.5;
	public static Map<String, Double> originalSystemEquationDict = new HashMap<String, Double>();
	public static Map<String, Double> targetValues = new HashMap<String, Double>();
	
	
	/**
	 * Build one object for storing all information for the Metaheuristic
	 * @param model
	 */
	public CapacityPlanningAnalysisParameters(IPepaModel model) {
		CapacityPlanningAnalysisParameters.model = model;
	    fOptionMap = model.getOptionMap();
	    sweepASTForSystemEquationAsDictionary();
	}
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static IParametricDerivationGraph getFGraph(IPepaModel model){
	
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
	
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static IParametricDerivationGraph getInitialFGraph(){
	
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
	
	/**
	 * get the system equation...
	 */
	public void sweepASTForSystemEquationAsDictionary(){
		CapacityPlanningAnalysisParameters.model.getAST().accept(new ModelObjectVisitor());
	}
	
	
	/**
	 * I can not find any previous work that gives me access to the system equation
	 * so I wrote/borrowed this so I can add the system equation to a dictionary
	 * @author twig
	 *
	 */
	private class ModelObjectVisitor implements ASTVisitor {
		
		private String component;
		private Double population; 

		@Override
		public void visitConstantProcessNode(ConstantProcessNode constant) {
			component = constant.getName();
		}
		
		@Override
		public void visitRateDoubleNode(RateDoubleNode doubleRate) {
			population = doubleRate.getValue();
			CapacityPlanningAnalysisParameters.originalSystemEquationDict.put(component,population);
		}
		
		@Override
		public void visitAggregationNode(AggregationNode aggregation) {
			aggregation.getProcessNode().accept(this);
			aggregation.getCopies().accept(this);
			
		}

		@Override
		public void visitCooperationNode(CooperationNode cooperation) {
			cooperation.getLeft().accept(this);
			cooperation.getRight().accept(this);
			
		}

		@Override
		public void visitWildcardCooperationNode(
				WildcardCooperationNode cooperation) {
			cooperation.getLeft().accept(this);
			cooperation.getRight().accept(this);
			
		}		

		@Override
		public void visitModelNode(ModelNode model) {
			model.getSystemEquation().accept(this);
			
		}
		
		public void visitPassiveRateNode(PassiveRateNode passive) {}
		public void visitPrefixNode(PrefixNode prefix) {}
		public void visitProcessDefinitionNode(ProcessDefinitionNode processDefinition) {}
		public void visitUnknownActionTypeNode(UnknownActionTypeNode unknownActionTypeNode) {}
		public void visitVariableRateNode(VariableRateNode variableRate) {}
		public void visitRateDefinitionNode(RateDefinitionNode rateDefinition) {}
		public void visitActionTypeNode(ActionTypeNode actionType){}
		public void visitBinaryOperatorRateNode(BinaryOperatorRateNode rate) {}
		public void visitChoiceNode(ChoiceNode choice) {}
		public void visitHidingNode(HidingNode hiding) {}
		public void visitActivityNode(ActivityNode activity) {}
		
	}	

}
