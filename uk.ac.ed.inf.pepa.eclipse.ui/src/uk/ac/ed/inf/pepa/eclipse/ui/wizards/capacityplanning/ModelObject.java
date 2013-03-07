package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.largescale.CapacityPlanningAnalysisParameters;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
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

public class ModelObject {
	

	private Map<String, Double> mySystemEquation = new HashMap<String, Double>();
	private IParametricDerivationGraph fGraph;
	private AnalysisOfFluidSteadyState analyseThis;
	private IProgressMonitor monitor;


	public ModelObject(IProgressMonitor monitor) {
		this.monitor = monitor;
		this.analyseThis = new AnalysisOfFluidSteadyState();
		this.fGraph = CapacityPlanningAnalysisParameters.getInitialFGraph();
	}

	public Map<String, Double> getDictionary() {
		return this.mySystemEquation;
	}

	public void setAnItem(String s, Double d) {
		this.mySystemEquation.put(s, d);
		setASTvalues();
		this.fGraph = CapacityPlanningAnalysisParameters.getInitialFGraph();
	}

	public void setASTvalues(){
		CapacityPlanningAnalysisParameters.model.getAST().accept(new ModelObjectVisitor());
	}
	
	public void copyDictionary(Map<String, Double> someDict) {
		this.mySystemEquation = new HashMap<String, Double>();
		for(Map.Entry<String, Double> entry : someDict.entrySet()){
			this.mySystemEquation.put(entry.getKey(),entry.getValue());
		}
	}
	
	public String toString(){
		String name = "";
		for(Map.Entry<String, Double> entry : this.mySystemEquation.entrySet()){
			name += entry.getKey() + "," + entry.getValue() + ",";
		}
		name += this.analyseThis.getResults(this.fGraph, this.monitor)[0];
		return name;
	}
	
	
	private class ModelObjectVisitor implements ASTVisitor {
		
		private String component;
		private boolean test;
		
		
		@Override
		public void visitConstantProcessNode(ConstantProcessNode constant) {
			component = constant.getName();
			test = mySystemEquation.get(component) != null;
			
		}
		
		@Override
		public void visitRateDoubleNode(RateDoubleNode doubleRate) {
			if(test){
				doubleRate.setValue((double) mySystemEquation.get(component));
				test = false;
			}
				
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


	public void reset() {
		for(Map.Entry<String, Double> entry : this.mySystemEquation.entrySet()){
			this.mySystemEquation.put(entry.getKey(),CapacityPlanningAnalysisParameters.originalSystemEquationDict.get(entry.getKey()));
			setASTvalues();
		}
		
	}
	
}
