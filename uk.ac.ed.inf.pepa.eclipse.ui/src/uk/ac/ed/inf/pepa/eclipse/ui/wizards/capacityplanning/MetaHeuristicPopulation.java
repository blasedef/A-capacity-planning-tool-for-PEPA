package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Dictionary;
import java.util.Hashtable;

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


public class MetaHeuristicPopulation {
	
	private ModelNode originalModel;
	private Dictionary originalSystemEquationDict = new Hashtable();
	private Dictionary tempSystemEquationDict = new Hashtable();
	private ModelObject originalModelObject;
	private String systemEquationAsString;
	private String component;
	private Double population;
	private boolean test = false;

	public MetaHeuristicPopulation(ModelNode originalModel) {
		this.originalModel = originalModel;
		this.systemEquationAsString = "";
		sweepASTForSystemEquationString();
		sweepASTForSystemEquationAsDictionary();
		this.originalModelObject = new ModelObject();
		this.originalModelObject.copyDictionary(this.originalSystemEquationDict);
		ModelObject secondModelObject = new ModelObject();
		secondModelObject.copyDictionary(this.originalSystemEquationDict);
		secondModelObject.setAnItem("Farm", 6.0);
		setASTvalues(secondModelObject.getDictionary());
		sweepASTForSystemEquationString();
		setASTvalues(originalModelObject.getDictionary());
		sweepASTForSystemEquationString();
	}
	
	public void sweepASTForSystemEquationString(){
		originalModel.accept(new ModelObjectVisitor());
	}
	
	public String getSystemEquationAsString(){
		return systemEquationAsString;
	}
	
	public void sweepASTForSystemEquationAsDictionary(){
		originalModel.accept(new ModelObjectVisitorDict());
	}
	
	public void setASTvalues(Dictionary dict){
		tempSystemEquationDict = dict;
		originalModel.accept(new ModelObjectVisitorSetValues());
	}
	
	private class ModelObjectVisitor implements ASTVisitor {

		@Override
		public void visitConstantProcessNode(ConstantProcessNode constant) {
			component = constant.getName();
			systemEquationAsString = systemEquationAsString + component + ",";
		}
		
		@Override
		public void visitRateDoubleNode(RateDoubleNode doubleRate) {
			population = doubleRate.getValue();
			systemEquationAsString = systemEquationAsString + population + "\n";
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
	
	private class ModelObjectVisitorDict implements ASTVisitor {

		@Override
		public void visitConstantProcessNode(ConstantProcessNode constant) {
			component = constant.getName();
		}
		
		@Override
		public void visitRateDoubleNode(RateDoubleNode doubleRate) {
			population = doubleRate.getValue();
			originalSystemEquationDict.put(component,population);
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

	private class ModelObjectVisitorSetValues implements ASTVisitor {

		@Override
		public void visitConstantProcessNode(ConstantProcessNode constant) {
			component = constant.getName();
			test = tempSystemEquationDict.get(component) != null;
			
		}
		
		@Override
		public void visitRateDoubleNode(RateDoubleNode doubleRate) {
			if(test){
				doubleRate.setValue((double) tempSystemEquationDict.get(component));
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
}
