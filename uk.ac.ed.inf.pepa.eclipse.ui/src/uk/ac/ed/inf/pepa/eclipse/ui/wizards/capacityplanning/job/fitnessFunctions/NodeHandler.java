package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;
import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class NodeHandler {
	
	private ModelNode node;
	private SetASTVisitor writeAST;
	private GetASTVisitor readAST;
	private GraphHandler grapher;

	public NodeHandler(PEPAConfig configPEPA, ODEConfig configODE){
		this.node = configPEPA.getNewModelNode();
		this.readAST = new GetASTVisitor();
		this.writeAST = new SetASTVisitor();
		this.grapher = new GraphHandler(configODE);
	}
	
	private void readAST(){
		this.node.accept(this.readAST);
	}
	
	public HashMap<String, Double> getSystemEquation(){
		this.readAST();
		return Tool.copyHashMap(readAST.systemEquation);
	}
	
	private void writeAST(){
		this.node.accept(this.writeAST);
	}
	
	public void setSystemEquation(HashMap<String, Double> candidate){
		this.writeAST.setSystemEquation(candidate);
		this.writeAST();
	}

	public HashMap<String, Double> getPerformanceResultsMap(HashMap<String, Double> candidate,
			IProgressMonitor monitor) {
		this.setSystemEquation(candidate);
		return this.grapher.getPerformanceResultsMap(this.node, monitor);
	}

}
