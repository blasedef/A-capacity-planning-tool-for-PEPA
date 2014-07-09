package uk.ac.ed.inf.pepa.cpt.searchEngine;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.candidates.MetaheuristicConfigurationLab;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CPT {
	
	private MetaheuristicConfigurationLab root;
	private IProgressMonitor monitor;
	private JSONObject obj = new JSONObject();
	
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
	 
		//System.out.print(obj);
		
		
		
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
			
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, Config.LABMIN, "1");
			CPTAPI.getPSORangeParameterControls().setValue(Config.LABEXP, Config.LABMAX, "1");
		}
		
		parameters.put(Config.LABPOP, 1.0);
		
		return parameters;
		
	}

}
