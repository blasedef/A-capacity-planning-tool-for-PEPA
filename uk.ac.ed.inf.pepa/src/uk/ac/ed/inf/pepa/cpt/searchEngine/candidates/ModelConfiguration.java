package uk.ac.ed.inf.pepa.cpt.searchEngine.candidates;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.nodeHandling.SetRatesASTVisitor;
import uk.ac.ed.inf.pepa.cpt.nodeHandling.SetSystemEquationASTVisitor;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.MetaHeuristicNode;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ModelConfigurationCandidateNode;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ASTVisitor;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class ModelConfiguration implements Candidate {
	
	private ModelConfigurationCandidateNode myNode;
	private ModelConfigurationCandidateNode myLocalbest;
	private ModelNode myModelNode;
	private ASTVisitor setter;
	
	public ModelConfiguration(HashMap<String,Double> parameters,
			HashMap<String,Double> velocity,
			IProgressMonitor monitor, 
			MetaHeuristicNode resultNode){
		
		this.myNode = new ModelConfigurationCandidateNode("ModelConfigurationCandidate",
				Utils.copyHashMap(parameters),
				Utils.copyHashMap(velocity),
				resultNode);
		
		resultNode.registerChild(this.myNode);
		
		this.myLocalbest = this.myNode;
		
		this.myModelNode = (ModelNode) ASTSupport.copy(CPTAPI.getModel());
		
		setNode();
		
	}
	
	public void setNode(){
		
		if(CPTAPI.getDomainControl().getValue().equals(Config.DOMCOM)){
			this.setter = new SetSystemEquationASTVisitor();
			((SetSystemEquationASTVisitor) this.setter).setSystemEquation(myNode.getMyMap());
		} else {
			this.setter = new SetRatesASTVisitor();
			((SetRatesASTVisitor) this.setter).setRatePopulation(myNode.getMyMap());
		}
		
		this.setter.visitModelNode(myModelNode);
		
	}
	
	public void setParameters(HashMap<String,Double> parameters, 
			HashMap<String,Double> velocity, 
			MetaHeuristicNode resultNode){
		
		if(this.myLocalbest.getFitness() > this.myNode.getFitness()){
			this.myLocalbest = this.myNode;
		}
		
		ModelConfigurationCandidateNode newNode = new ModelConfigurationCandidateNode("ModelConfigurationCandidate",
				Utils.copyHashMap(parameters), 
				Utils.copyHashMap(velocity),
				resultNode);
		
		newNode.setSister(this.myNode);
		
		this.myNode = newNode;
		
		resultNode.registerChild(this.myNode);
		
		setNode();
		
	}
	
	public ModelConfigurationCandidateNode getNode(){
		return this.myNode;
	}

	public IParametricDerivationGraph getGraph() {
		return Utils.getDevGraphFromAST(myModelNode);
	}

	public ModelConfigurationCandidateNode getLocalBest() {
		return this.myLocalbest;
	}


}
