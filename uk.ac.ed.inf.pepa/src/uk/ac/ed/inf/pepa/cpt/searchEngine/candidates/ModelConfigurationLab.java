package uk.ac.ed.inf.pepa.cpt.searchEngine.candidates;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.metaheuristics.ParticleSwarmOptimisation;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.CandidateNode;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.MetaHeuristicNode;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ModelConfigurationCandidateNode;

public class ModelConfigurationLab extends Lab {
	
	private CandidateNode myNode;
	private IProgressMonitor myMonitor;
	
	public ModelConfigurationLab(HashMap<String,Double> parameters, 
			IProgressMonitor monitor, 
			MetaHeuristicNode resultNode){
		
		this.myNode = new CandidateNode("ModelConfigurationLab",
				Utils.copyHashMap(parameters), 
				resultNode);
		resultNode.registerChild(this.myNode);
		
		this.myMonitor = monitor;
		
		startExperiment(parameters);
		
	}

	public ModelConfigurationLab(CandidateNode node, IProgressMonitor myMonitor) {
		this.myNode = node;
		this.myMonitor = myMonitor;
	}

	public void startExperiment(HashMap<String,Double> parameters){
		
		for(int i = 0; i < myNode.getMyMap().get(Config.LABEXP); i++){
			new ParticleSwarmOptimisation(Utils.copyHashMap(parameters), this.myNode, this.myMonitor);
		}
		
		this.myNode.updateFitness();
		
	}
	
	public Double getFitness(){
		return this.myNode.getFitness();
		
	}

	public ModelConfigurationCandidateNode getFitnessNode() {
		return myNode.getFittestNode();
	}
	
	public void setParameters(HashMap<String,Double> parameters,
			IProgressMonitor monitor,
			MetaHeuristicNode resultNode){
		
		CandidateNode newNode = new CandidateNode("ModelConfigurationLab",
				Utils.copyHashMap(parameters), 
				resultNode);
		
		newNode.setSister(this.myNode);
		
		resultNode.registerChild(this.myNode);
		
		this.myNode = newNode;
		
		startExperiment(parameters);
		
	}

	public CandidateNode getNode() {
		return myNode;
	}

	public void setNode(CandidateNode node) {
		this.myNode = node;
	}

}
