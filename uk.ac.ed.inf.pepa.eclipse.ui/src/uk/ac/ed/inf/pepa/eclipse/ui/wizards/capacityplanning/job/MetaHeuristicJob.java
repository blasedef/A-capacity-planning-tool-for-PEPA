package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;


import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class MetaHeuristicJob extends Job{

	private IFile iFile;
	private Lab lab;
	private int totalWork;
	private ConfigurationModel configurationModel;
	private HashMap<String,Double> systemEquationPopulationRanges;
	
	public MetaHeuristicJob(String name, ConfigurationModel configurationModel, IFile iFile) {
		super(name);
		this.iFile = iFile;
		this.configurationModel = configurationModel;
		
		if(configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S))
			postProcessHillClimbing();
		
		setTotalWorkUnits();
		
		if(configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)){
			if(configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
				
				this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			} else if (configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
				
				this.lab = new SingleNetworkGeneticAlgorithmLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			} else {
				
				this.lab = new SingleNetworkParticleSwarmOptimisationLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			}
			
		} else {
			
			this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
					totalWork,
					systemEquationPopulationRanges,
					this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
			
		}
		
		
		
	}

	private void postProcessHillClimbing() {
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.INITIALCANDIDATEPOPULATION_S, 1.0);
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.MUTATIONPROBABILITY_S, 1.0);
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.GENERATION_S,
				this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.GENERATIONHC_S));

		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		Tool.setStartTime();
		
		IStatus status;
		
		status = this.lab.startExperiments(monitor);
		
		writeRecorders(monitor);
		
		return status;
	}
	
	private void writeRecorders(IProgressMonitor monitor) {
		String output = "\n";
		
		output += configurationModel.dropDownListsList.get(1).getKey() 
		+ ";" 
		+ configurationModel.systemEquationCandidate.getLeftMap()
		+ ";"
		+ configurationModel.dropDownListsList.get(1).getValue()
		+ "\n";
		
		for(Entry<String, Double> entry : configurationModel.metaheuristicParameters.getLeftMap().entrySet()){
			output += entry.getKey() + ";" + entry.getValue() + "\n";
		}
		
		output += "\n";

		for(int i = 0; i < lab.recorders.size(); i++){
			output += "Experiment;" + i + ";\n";
			output += lab.recorders.get(i).getTopX(1);
			output += ";\n";
		}
		
		
		for(int i = 0; i < lab.recorders.size(); i++){
			output += "Experiment;" + i + ";\n";
			output += lab.recorders.get(i).getTopX(10);
			output += ";\n";
		}
		
		for(int i = 0; i < lab.recorders.size(); i++){
			output += "Experiment;" + i + ";\n";
			for(int j = 0; j < lab.recorders.get(i).getGeneration().size(); j++){
				output += lab.recorders.get(i).thisGenerationsMix(j);
			}
		}
		
		byte currentBytes[] = output.getBytes();
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				currentBytes);
		try {
			iFile.setContents(byteArrayInputStream, true, false,
					monitor);
		} catch (CoreException e) {
			try {
				throw new InvocationTargetException(e);
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public void setPopulationRanges(){
		
		this.systemEquationPopulationRanges = new HashMap<String,Double>();
		
		HashMap<String,Double> minPopulationRange = Tool.copyHashMap(configurationModel.systemEquationCandidate.getLeftMap());
		HashMap<String,Double> maxPopulationRange = Tool.copyHashMap(configurationModel.systemEquationCandidate.getRightMap());
		
		for(Entry<String, Double> entry : minPopulationRange.entrySet()){
			double range = maxPopulationRange.get(entry.getKey()) - minPopulationRange.get(entry.getKey());
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
		
		if(!configurationModel.dropDownListsList.get(2).getValue().equals(Config.NETWORKSINGLE_S)){
			totalWork = configurationModel.metaheuristicParametersCandidate.getRightMap().get(Config.EXPERIMENTS_S).intValue();
			//worst case scenario, every generation created a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidate.getRightMap().get(Config.GENERATION_S).intValue();
			//worst case scenario, every candidate produces a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidate.getRightMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		}
		
		
		//times by estimated search space size
		totalWork *= 1; 
		
		this.getEstimatedSearchSpaceSize();
		
		
		//times by experiments
		totalWork *= configurationModel.metaheuristicParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
		
		
		//worst case scenario, every generation created a new analysis job
		totalWork *= configurationModel.metaheuristicParameters.getLeftMap().get(Config.GENERATION_S).intValue();
		
		
		//worst case scenario, every candidate produces a new analysis job
		totalWork *= configurationModel.metaheuristicParameters.getLeftMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		
	}
	
}