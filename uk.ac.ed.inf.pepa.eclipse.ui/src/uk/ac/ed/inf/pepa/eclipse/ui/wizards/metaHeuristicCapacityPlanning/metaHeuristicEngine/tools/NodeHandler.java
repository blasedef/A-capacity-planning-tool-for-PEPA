package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class NodeHandler {
	
	private ModelNode node;
	private SetASTVisitor writeAST;
	private GetASTVisitor readAST;

	public NodeHandler(){
		this.node = (ModelNode) ASTSupport.copy(ExperimentConfiguration.pEPAConfig.getPepaModel().getAST());
		this.readAST = new GetASTVisitor();
		this.writeAST = new SetASTVisitor();
	}
	
	private void readAST(){
		this.node.accept(this.readAST);
	}
	
	public HashMap<String, Double> getSystemEquation(){
		this.readAST();
		return this.readAST.systemEquation;
	}
	
	private void writeAST(){
		this.node.accept(this.writeAST);
	}
	
	public void setSystemEquation(HashMap<String, Double> map){
		this.writeAST.setSystemEquation(map);
		this.writeAST();
	}
	
	public IParametricDerivationGraph getGraph(HashMap<String, Double> map) {
		if(map == null){
			return Tools.getDevGraphFromAST(this.node);
		} else {
			setSystemEquation(map);
			return Tools.getDevGraphFromAST(this.node);
		}
	}

}
