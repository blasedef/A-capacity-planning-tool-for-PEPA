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
	
	public void jsonNodes(){
		
		
		this.root.jsonUp(obj);
		
		try {
			 
			FileWriter file = new FileWriter("/home/twig/test.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		
	}
	
	/**
	 * Fill out all PSO parameters, use MetaheuristicSearchParameters for the PSO metaheuristic, and a
	 * hashmap for the HillClimbing algorithm.
	 */
	public HashMap<String,Double> getParameters(){
		
		HashMap<String,Double>  parameters = new HashMap<String,Double>();
		
		boolean isSingleSearch = (CPTAPI.getSearchControls().getValue().equals(Config.SEARCHSINGLE));
		
		parameters.put("MetaheuristicConfigurationLab", 1.0);
		
		if(isSingleSearch){
			
			CPTAPI.setPSOValues();
			
			parameters.put(Config.SEARCH, 1.0);
			parameters.put(Config.LABGEN, 1.0);
			parameters.put(Config.LABEXP, 1.0);
			parameters.put(Config.LABMUT, 0.0);
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, 
					Config.LABMIN, 
					"" + Integer.parseInt(CPTAPI.getExperimentControls().getValue(Config.LABEXP)));
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, 
					Config.LABMAX, 
					"" + Integer.parseInt(CPTAPI.getExperimentControls().getValue(Config.LABEXP)));
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, 
					Config.LABRAN, 
					"1");
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABGEN, 
					Config.LABMIN, 
					"" + Integer.parseInt(CPTAPI.getGenerationControls().getValue(Config.LABGEN)));
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABGEN, 
					Config.LABMAX, 
					"" + Integer.parseInt(CPTAPI.getGenerationControls().getValue(Config.LABGEN)));
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABGEN, 
					Config.LABRAN, 
					"1");
			
			
		} else {
			parameters.put(Config.SEARCH, 0.0);
			
			parameters.put(Config.LABEXP,
					Double.parseDouble(CPTAPI.getExperimentControls().getValue(Config.LABEXP)));
			
			parameters.put(Config.LABMUT,
					Double.parseDouble(CPTAPI.getHCParameterControls().getValue(Config.LABMUT)));
			
			parameters.put(Config.LABGEN,
					Double.parseDouble(CPTAPI.getGenerationControls().getValue(Config.LABGEN)));
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, Config.LABMIN, "2");
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, Config.LABMAX, "2");
		}
		
		parameters.put(Config.LABPOP, 1.0);
		
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
		
		//this.processQueue(10);
		
	}
	
	
	private void processQueue(int count){
		
		//remove duplicates
		PriorityQueue<ResultNode> tempQueue = new PriorityQueue<ResultNode>() {

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
		
		while(tempQueue.size() <= count){
			
			tempQueue.add(this.resultsQueue.poll());
			
		}
		
		this.resultsQueue = tempQueue;
		
	}

	public void printQueue() {
		
		createResultsQueue();
		
		fillQueue();
		
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
