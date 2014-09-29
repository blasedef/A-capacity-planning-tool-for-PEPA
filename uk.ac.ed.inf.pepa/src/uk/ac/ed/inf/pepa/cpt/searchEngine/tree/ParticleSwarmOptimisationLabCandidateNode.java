package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;

public class ParticleSwarmOptimisationLabCandidateNode extends CandidateNode {
	
	HashMap<String,Double> parameters;
	
	public ParticleSwarmOptimisationLabCandidateNode(String name,
			HashMap<String,Double> parameters,
			MetaHeuristicNode parent){
		super(name, parent);
		
		this.parameters = parameters;
		setMyMap();
	}
	
	public void setMyMap() {
		this.myMap.put(Config.LABEXP, parameters.get(Config.LABEXP));
	}

	@Override
	public void fillQueue(PriorityQueue<ResultNode> resultsQueue, IProgressMonitor monitor) {
		for(int i = 0; i < this.children.size(); i++){
			this.children.get(i).fillQueue(resultsQueue, monitor);
		}
	}
	
	@Override
	public void setUpUID() {
		this.uid = Utils.getParticleSwarmOptimisationLabCandidateNodeUID();
	}

	@Override
	public void setResultsSize() {
		
		for(int i = 0; i < children.size() ; i ++){
			children.get(i).setResultsSize();
		}
		
	}
	
}
