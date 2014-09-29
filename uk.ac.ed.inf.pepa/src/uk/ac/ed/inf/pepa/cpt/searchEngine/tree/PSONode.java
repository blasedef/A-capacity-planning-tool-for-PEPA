package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;

public class PSONode extends MetaHeuristicNode {

	HashMap<String,Double> parameters;
	
	public PSONode(String name,
			HashMap<String,Double> parameters,
			CandidateNode particleSwarmOptimisationLabCandidateNode){
		super(name, particleSwarmOptimisationLabCandidateNode, true);
		
		this.parameters = parameters;
		setMyMap();
	}

	public void setMyMap() {
		this.myMap.put(Config.LABPOP, parameters.get(Config.LABPOP));
		this.myMap.put(Config.LABGEN, parameters.get(Config.LABGEN));
		this.myMap.put(Config.LABORG, parameters.get(Config.LABORG));
		this.myMap.put(Config.LABLOC, parameters.get(Config.LABLOC));
		this.myMap.put(Config.LABGLO, parameters.get(Config.LABGLO));
	}

	@Override
	public void fillQueue(PriorityQueue<ResultNode> resultsQueue, IProgressMonitor monitor) {
	
		for(int i = 0; i < this.children.size(); i++){
			this.children.get(i).fillQueue(resultsQueue, monitor);
		}
	
	}
	
	@Override
	public void setUpUID() {
		this.uid = Utils.getPSONodeUID();
		
	}
	
	public void setResultsSize() {
		
		for(int i = 0; i < children.size() ; i ++){
			children.get(i).setResultsSize();
		}
	}

}
