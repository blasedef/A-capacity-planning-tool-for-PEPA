package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;

public class MetaheuristicParameters extends TextInputs {
	
	//metaheuristic attribute lists
	//Hill Climbing has a different default generation from the other algorithms
	private String[] hillClimbingParameterList = new String[] {Config.GENERATIONHC_S};
	
	private String[] particleSwarmOptimisationParameterList = new String[] {
			Config.GENERATION_S,
			Config.PERSONALBEST,
			Config.GLOBALBEST,
			Config.ORIGINALVELO,
			Config.INITIALCANDIDATEPOPULATION_S};
	
	private String[] geneticAlgorithmParameterList = new String[] { 
			Config.GENERATION_S,
			Config.MUTATIONPROBABILITY_S,
			Config.CROSSOVERPROBABILITY_S,
			Config.INITIALCANDIDATEPOPULATION_S};
	
	//option mapping for attribute options
	private final Map<String,String[]> metaheuristicParameterListMap = new HashMap<String, String[]>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 9024689169600758025L;
	{
		put(Config.METAHEURISTICTYPEHILLCLIMBING_S,hillClimbingParameterList);
		put(Config.METAHEURISTICTYPEGENETICALGORITHM_S,geneticAlgorithmParameterList);
		put(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S,particleSwarmOptimisationParameterList);
	}};
	
	/**
	 * Metaheuristic parameters
	 */
	public MetaheuristicParameters (){
		super("Metaheuristic parameters setup");
		
		this.defaultMapping.put(Config.GENERATION_S,10.0);
		this.defaultMapping.put(Config.GENERATIONHC_S,100.0);
		this.defaultMapping.put(Config.MUTATIONPROBABILITY_S,0.02);
		this.defaultMapping.put(Config.CROSSOVERPROBABILITY_S,0.2);
		this.defaultMapping.put(Config.INITIALCANDIDATEPOPULATION_S,10.0);
		this.defaultMapping.put(Config.PERSONALBEST,0.33);
		this.defaultMapping.put(Config.GLOBALBEST,0.33);
		this.defaultMapping.put(Config.ORIGINALVELO,0.33);
		
		for(String s : hillClimbingParameterList){
			this.leftMap.put(s, this.defaultMapping.get(s));
			this.rightMap.put(s, this.defaultMapping.get(s));
		}
		
		this.leftHeading = "Minimum value";
		this.rightHeading = "Maximum value";
	}
	
	public void setParameterList(String s, boolean side){
		
		String[] options = this.metaheuristicParameterListMap.get(s);
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		HashMap<String, Double> oldMap;
		
		if(side){
			 oldMap = this.rightMap;
		} else {
			 oldMap = this.leftMap;
		}
		
		for(String t : options){
			if(oldMap.containsKey(t)){
				map.put(t, oldMap.get(t));
			} else {
				map.put(t, this.defaultMapping.get(t));
			}
		}
		
		if(side){
			this.rightMap = map;
		} else {
			this.leftMap = map;
		}
		
	}
	
	@Override
	public boolean isCorrect(boolean single){
		
		if(!single){
			boolean aboveZero = true;
			boolean maxGreaterThanOrEqualToMin = true;
			
			String[] keysLeft = this.getMapKeys(false);
			String[] keysRight = this.getMapKeys(true);
			
			for(String key : keysLeft){
				if((this.typeMap.get(key) == Config.NATURAL) || (this.typeMap.get(key) == Config.EVEN) ){
					aboveZero = aboveZero && (this.leftMap.get(key) > 0.0);
				} else {
					aboveZero = aboveZero && (this.leftMap.get(key) >= 0.0);
				}
				
			}
			
			for(String key : keysRight){
				maxGreaterThanOrEqualToMin = maxGreaterThanOrEqualToMin && (this.rightMap.get(key) >=  this.leftMap.get(key));
			}
			
			return (maxGreaterThanOrEqualToMin && aboveZero);
		}else {
			return true;
		}
	}
	
	public void update(String s){
		
		this.setParameterList(s, false);
		this.setParameterList(s, true);
	}

}