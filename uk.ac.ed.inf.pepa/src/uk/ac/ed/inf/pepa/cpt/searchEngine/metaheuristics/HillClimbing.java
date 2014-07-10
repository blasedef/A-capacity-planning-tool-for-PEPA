package uk.ac.ed.inf.pepa.cpt.searchEngine.metaheuristics;

import java.util.HashMap;

import uk.ac.ed.inf.pepa.IProgressMonitor;
import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.Utils;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.candidates.ModelConfigurationLab;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.CandidateNode;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.MetaHeuristicNode;

public class HillClimbing implements MetaHeuristics {
	
	private MetaHeuristicNode myNode;
	private IProgressMonitor myMonitor;
	private ModelConfigurationLab candidate;
	private ModelConfigurationLab possibleCandidate;

	public HillClimbing(HashMap<String, Double> parameters,
			CandidateNode resultNode, 
			IProgressMonitor monitor) {
		
		this.myNode = new MetaHeuristicNode("HillClimbingAlgorithm",
				Utils.copyHashMap(parameters), 
				resultNode);
		resultNode.registerChild(this.myNode);
		
		this.myMonitor = monitor;
		
		startAlgorithm();
		
		this.myNode.updateFinishTime();
		
	}
	
	public void startAlgorithm(){
		
		HashMap<String,Double> parameters = getPSOParameters();
		HashMap<String,Double> possibleParameters = mutateModelConfigurationLabParameters(parameters);
		
		this.candidate = new ModelConfigurationLab(parameters,
				this.myMonitor, 
				this.myNode);
		
		this.possibleCandidate = new ModelConfigurationLab(this.candidate.getNode(), this.myMonitor);
		
		int generations = this.myNode.getMyMap().get(Config.LABGEN).intValue();
		
		for(int i = 1; i < generations; i++){
			
			this.possibleCandidate.setParameters(possibleParameters, this.myMonitor, this.myNode);
			
			if(possibleCandidate.getFitness() < candidate.getFitness()){
				parameters = Utils.copyHashMap(possibleParameters);
				candidate.setNode(possibleCandidate.getNode());
			} 
			
			possibleParameters = mutateModelConfigurationLabParameters(parameters);
			
		}
		
	}
	
	public HashMap<String,Double> mutateModelConfigurationLabParameters(HashMap<String,Double> parameters){
		
		HashMap<String,Double> mutatedParameters = Utils.copyHashMap(parameters);
		
		String[] keys = mutatedParameters.keySet().toArray(new String[mutatedParameters.keySet().size()]);
		
		Double min, max, value;
		
		String type;
		
		for(int i = 0; i < keys.length; i++){
			
			if(Utils.rollDice(this.myNode.getMyMap().get(Config.LABMUT))){
				
				min = Double.parseDouble(CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMIN));
				max = Double.parseDouble(CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMAX));
				
				type = CPTAPI.getPSORangeParameterControls().getType(keys[i],Config.LABMIN);
				
				value = Utils.returnRandomInRange(min, max, type);
				
				mutatedParameters.put(keys[i], value);
			}
			
		}
		
		return Utils.copyHashMap(mutatedParameters);
	}
	
	public HashMap<String,Double> getPSOParameters(){
		
		HashMap<String,Double> parameters = new HashMap<String,Double>();
		
		String[] keys = CPTAPI.getPSORangeParameterControls().getKeys();
		
		Double min, max, value;
		
		String type;
		
		for(int i = 0; i < keys.length; i++){
			
			min = Double.parseDouble(CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMIN));
			max = Double.parseDouble(CPTAPI.getPSORangeParameterControls().getValue(keys[i], Config.LABMAX));
			
			type = CPTAPI.getPSORangeParameterControls().getType(keys[i],Config.LABMIN);
			
			value = Utils.returnRandomInRange(min, max, type);
			
			parameters.put(keys[i], value);
		}

		return Utils.copyHashMap(parameters);
	}

}
