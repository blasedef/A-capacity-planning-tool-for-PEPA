package uk.ac.ed.inf.pepa.cpt.searchEngine.tree;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;

public class PSONode extends MetaHeuristicNode {

	HashMap<String,Double> parameters;
	private PriorityQueue<CandidateNode> modelConfigurationQueue;
	
	public PSONode(String name,
			HashMap<String,Double> parameters,
			CandidateNode particleSwarmOptimisationLabCandidateNode){
		super(name, particleSwarmOptimisationLabCandidateNode, true);
		
		this.modelConfigurationQueue = new PriorityQueue<CandidateNode>();
		this.parameters = parameters;
		setMyMap();
	}
	
	@Override
	public void registerChild(CandidateNode child){
		modelConfigurationQueue.add(child);
		children.add(child);
		childUIDToIndex.put(child.getUID(), children.size() - 1);
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
	
		while(this.modelConfigurationQueue.size() > 0){
			this.modelConfigurationQueue.poll().fillQueue(resultsQueue, monitor);
		}
		
//		for(int i = 0; i < this.children.size(); i++){
//			this.children.get(i).fillQueue(resultsQueue, monitor);
//		}
	
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
