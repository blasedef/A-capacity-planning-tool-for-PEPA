package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Map;

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
 * Class for setting the AST values
 * @author twig
 *
 */

class SetASTVisitor implements ASTVisitor {
	
	private String component;
	private boolean test;
	Map<String, Double> systemEquation; 
	
	public SetASTVisitor(Map<String, Double> mySystemEquation){
		this.systemEquation = mySystemEquation;
	}
	
	public Map<String, Double>  getSystemEquation(){
		return this.systemEquation;
	}
	
	public void setSystemEquation(Map<String, Double> systemEquation){
		this.systemEquation = systemEquation;
	}
	
	@Override
	public void visitConstantProcessNode(ConstantProcessNode constant) {
		component = constant.getName();
		test = systemEquation.get(component) != null;
		
	}
	
	@Override
	public void visitRateDoubleNode(RateDoubleNode doubleRate) {
		if(test){
			doubleRate.setValue((double) systemEquation.get(component));
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