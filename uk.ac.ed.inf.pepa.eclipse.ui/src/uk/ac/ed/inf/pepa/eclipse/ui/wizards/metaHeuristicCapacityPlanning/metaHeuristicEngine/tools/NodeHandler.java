package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class NodeHandler {
	
	private ModelNode node;
	private SetASTVisitor writeAST;
	private GetASTVisitor readAST;
	private Grapher grapher;

	public NodeHandler(){
		this.node = (ModelNode) ASTSupport.copy(ExperimentConfiguration.pEPAConfig.getPepaModel().getAST());
		this.readAST = new GetASTVisitor();
		this.writeAST = new SetASTVisitor();
		this.grapher = new Grapher();
	}
	
	private void readAST(){
		this.node.accept(this.readAST);
	}
	
	public HashMap<String, Double> getSystemEquation(){
		this.readAST();
		
		HashMap<String, Double> map = new HashMap<String, Double>();
			
		for(Map.Entry<String, Double> entry : readAST.systemEquation.entrySet()){
			map.put(entry.getKey(), entry.getValue());
		}
		
		return map;
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
			return grapher.getDevGraphFromAST(this.node);
		} else {
			setSystemEquation(map);
			return grapher.getDevGraphFromAST(this.node);
		}
	}

}