package uk.ac.ed.inf.pepa.cpt.searchEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.json.simple.JSONObject;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.candidates.MetaheuristicConfigurationLab;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ResultNode;

public class CPT {
	
	private MetaheuristicConfigurationLab root;
	private IProgressMonitor monitor;
	private JSONObject obj = new JSONObject();
	private PriorityQueue<ResultNode> resultsQueue;
	
	public CPT (IProgressMonitor monitor){
		
		this.monitor = monitor;
		
	}
	
	public void start() {
		
		this.root = new MetaheuristicConfigurationLab(Utils.copyHashMap(getParameters()), 
				this.monitor);
		
	}
	
	public HashMap<String,Double> getParameters(){
		
		HashMap<String,Double>  parameters = new HashMap<String,Double>();
		parameters.put("MetaheuristicConfigurationLab", 1.0);
		boolean isSingleSearch = (CPTAPI.getSearchControls().getValue().equals(Config.SEARCHSINGLE));
		
		String[] keys = CPTAPI.getHCControls().getKeys();
		
		if(isSingleSearch){
			parameters.put(Config.SEARCH, 1.0);
		} else {
			parameters.put(Config.SEARCH, 0.0);
		}
		
		for(int i = 0; i < keys.length; i++){
			parameters.put(keys[i], Double.parseDouble(CPTAPI.getHCControls().getValue(keys[i])));
			
		}
		
		return parameters;
		
	}
	
	public void createResultsQueue(){
		
		//remove duplicates
		this.resultsQueue = new PriorityQueue<ResultNode>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean offer(ResultNode e){
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
			@Override
			public boolean add(ResultNode e){
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
		};
		
		fillQueue();
		
		
	}
	
	private void fillQueue(){
		
		this.root.fillQueue(this.resultsQueue);
		
	}
	
	public void printQueue() {
		//TODO remove this?
		createResultsQueue();
		
		JSONObject obj2 = new JSONObject();
		
		while(this.resultsQueue.size() > 1){
			resultsQueue.poll().print(obj2);
		}
		
		try {
			 
			FileWriter file = new FileWriter("/home/twig/Workspace/python/jsonToCSV/ordered.json");
			file.write(obj2.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
