package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;


import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;


public class MetaHeuristicJob extends Job{

	private Lab lab;
	private int totalWork;
	private ConfigurationModel configurationModel;
	private HashMap<String,Double> systemEquationPopulationRanges;
	public Path resultsFolder;
	
	static Logger log = Logger.getLogger(MetaHeuristicJob.class);
	
	
	public MetaHeuristicJob(String name, ConfigurationModel configurationModel) {
		super(name);
		
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						configurationModel.configPEPA.getPepaModel().getUnderlyingResource(), ""));
		
		//regardless of how this is run, everyone saves to the same folder
		resultsFolder = new Path( ResourcesPlugin.getWorkspace().getRoot().getLocation().addTrailingSeparator().toOSString() 
				+ "results_" 
				+ handle.getName()
				+ "_"
				+ configurationModel.dropDownListList.get(1).getValue()
				+ "_"
				+ Tool.getDateTime());
		
		BasicConfigurator.configure();
		
		
		this.configurationModel = configurationModel;
		
		if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S))
			postProcessHillClimbing();
		
		setTotalWorkUnits();
		
		if(configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
				
				this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue(),
						resultsFolder);
				
			} else if (configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
				
				this.lab = new SingleNetworkGeneticAlgorithmLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue(),
						resultsFolder);
				
			} else {
				
				this.lab = new SingleNetworkParticleSwarmOptimisationLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue(),
						resultsFolder);
				
			}
		} else if(configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINDRIVEN_S)){
			
			
		} else {
			
			this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
					totalWork,
					systemEquationPopulationRanges,
					this.configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue(),
					resultsFolder);
			
		}
		
		
		
	}

	private void postProcessHillClimbing() {
		this.configurationModel.metaheuristicParametersRoot.getLeftMap().put(Config.INITIALCANDIDATEPOPULATION_S, 1.0);
		this.configurationModel.metaheuristicParametersRoot.getLeftMap().put(Config.MUTATIONPROBABILITY_S, 1.0);
		this.configurationModel.metaheuristicParametersRoot.getLeftMap().put(Config.GENERATION_S,
				this.configurationModel.metaheuristicParametersRoot.getLeftMap().get(Config.GENERATIONHC_S));
		this.configurationModel.metaheuristicParametersRoot.getLeftMap().remove(Config.GENERATIONHC_S);

		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		Tool.setStartTime();
		
		IStatus status;
		
		status = this.lab.startExperiments(monitor);
		
		this.lab.complete();
		
		return status;
	}
	

	


	public void setPopulationRanges(){
		
		this.systemEquationPopulationRanges = new HashMap<String,Double>();
		
		HashMap<String,Double> minPopulationRange = Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap());
		HashMap<String,Double> maxPopulationRange = Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getRightMap());
		
		for(Entry<String, Double> entry : minPopulationRange.entrySet()){
			double range = (maxPopulationRange.get(entry.getKey()) - minPopulationRange.get(entry.getKey())) + 1;
			this.systemEquationPopulationRanges.put(entry.getKey(), range);
		}
	}
	
	public int getEstimatedSearchSpaceSize(){
		
		setPopulationRanges();
		
		int size = 1;
		
		for(Entry<String, Double> entry : this.systemEquationPopulationRanges.entrySet()){
			size *= entry.getValue();
		}
		
		return size;
		
	}
	
	public void setTotalWorkUnits(){
		
		this.totalWork = 1;
		
		if(!configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			totalWork = configurationModel.labParametersCandidateLeaf.getRightMap().get(Config.EXPERIMENTS_S).intValue();
			//worst case scenario, every generation created a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidateLeaf.getRightMap().get(Config.GENERATION_S).intValue();
			//worst case scenario, every candidate produces a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidateLeaf.getRightMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		}
		
		
		//times by estimated search space size
		totalWork *= 1; 
		
		this.getEstimatedSearchSpaceSize();
		
		
		//times by experiments
		totalWork *= configurationModel.labParametersRoot.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
		
		
		//worst case scenario, every generation created a new analysis job
		totalWork *= configurationModel.metaheuristicParametersRoot.getLeftMap().get(Config.GENERATION_S).intValue();
		
		
		//worst case scenario, every candidate produces a new analysis job
		totalWork *= configurationModel.metaheuristicParametersRoot.getLeftMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		
	}
	
}