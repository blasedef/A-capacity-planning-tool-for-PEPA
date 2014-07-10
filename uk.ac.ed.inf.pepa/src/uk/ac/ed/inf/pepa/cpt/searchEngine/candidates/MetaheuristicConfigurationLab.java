package uk.ac.ed.inf.pepa.cpt.searchEngine.candidates;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.json.simple.JSONObject;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.metaheuristics.HillClimbing;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.CandidateNode;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ResultNode;

public class MetaheuristicConfigurationLab extends Lab {

	private CPTAPI api;
	private CandidateNode myNode;
	private IProgressMonitor myMonitor;
	
	/**
	 * A lab runs many experiments (each meta heuristic run is an experiment)
	 * @param api
	 * @param monitor
	 */
	public MetaheuristicConfigurationLab(HashMap<String,Double> parameters, 
			IProgressMonitor monitor) {
		
		this.myNode = new CandidateNode("MetaheuristicConfigurationLab",
				Utils.copyHashMap(parameters),
				null);
		
		this.myMonitor = monitor;

		for(int i = 0; i < myNode.getMyMap().get(Config.LABEXP); i++){
			new HillClimbing(Utils.copyHashMap(parameters), myNode, monitor);
		}
		
	}
	
	public void jsonUp(JSONObject obj){
		
		this.myNode.json(obj);
	}

	public void fillQueue(PriorityQueue<ResultNode> resultsQueue) {
		
		this.myNode.fillQueue(resultsQueue);
		
	}
	


}
