package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

	private Lab lab;
	private int totalWork;
	private ConfigurationModel configurationModel;
	private HashMap<String,Double> systemEquationPopulationRanges;
	
	public MetaHeuristicJob(String name, ConfigurationModel configurationModel) {
		super(name);
		this.configurationModel = configurationModel;
		
		if(configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S))
			postProcessHillClimbing();
		
		setTotalWorkUnits();
		
		if(configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			if(configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
				
				this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			} else if (configurationModel.dropDownListsList.get(1).getValue().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
				
				this.lab = new SingleNetworkGeneticAlgorithmLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			} else {
				
				this.lab = new SingleNetworkParticleSwarmOptimisationLab(this.configurationModel,
						totalWork,
						systemEquationPopulationRanges,
						this.configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
				
			}
			
		} else {
			
			this.lab = new SingleNetworkHillClimbingLab(this.configurationModel,
					totalWork,
					systemEquationPopulationRanges,
					this.configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue());
			
		}
		
		
		
	}

	private void postProcessHillClimbing() {
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.INITIALCANDIDATEPOPULATION_S, 1.0);
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.MUTATIONPROBABILITY_S, 1.0);
		this.configurationModel.metaheuristicParameters.getLeftMap().put(Config.GENERATION_S,
				this.configurationModel.metaheuristicParameters.getLeftMap().get(Config.GENERATIONHC_S));
		this.configurationModel.metaheuristicParameters.getLeftMap().remove(Config.GENERATIONHC_S);

		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		Tool.setStartTime();
		
		IStatus status;
		
		status = this.lab.startExperiments(monitor);
		
		writeMHRecorders(monitor);
		
		return status;
	}
	
	private String getPopulations(){
		
		String output;
		
		output = "";
		
		HashMap<String,Double> rightMap = configurationModel.systemEquationPopulationRanges.getRightMap();
		HashMap<String,Double> leftMap = configurationModel.systemEquationPopulationRanges.getLeftMap();
		
		for(Map.Entry<String, Double> entry : rightMap.entrySet()){
			output = output + entry.getKey() + "[" + leftMap.get(entry.getKey()) + "_" + entry.getValue() + "]"; 
		}
		
		return output;
		
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	private void writeMHRecorders(IProgressMonitor monitor) {
		
		for(int i = 0; i < lab.recorders.size(); i++){
			
			// {"LAB":
				// {"MH":
				//		{"MHParams":
				//			{<K:V>},
				//		},
				//		{"SearchParams":
				//			{<K:V>},
				//		},
				//		{"TopSystemEquations":
				//			{"SystemEquation":
				//				{Name:String},
				//				{Fitness:Float},
				//				{PerformanceMeasures
				//					{Name:Float},
				//				},
				//				{Population:
				//					{Name:Int},
				//				}
				//		},
				//		{"GenerationMix":
				//			{"SystemEquation":
				//				{Generation:Int},
				//				{Name:String},
				//				{Fitness:Float},
				//				{PerformanceMeasures
				//					{Name:Float},
				//				},
				//				{Population:
				//					{Name:Int},
				//				}			
				//		}
				// }
			// }
			
			String output = "\n";
			
			output += "{\"LAB\":{ \n";
			
			output += "{\"METAHEURISTICDETAILS\":{ \n";
			
			output += "{" + configurationModel.dropDownListsList.get(1).getKey() 
			+ ":" 
			+ configurationModel.dropDownListsList.get(1).getValue()
			+ "}\n";
			
			for(Entry<String, Double> entry : configurationModel.metaheuristicParameters.getLeftMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			
			for(Entry<String, Double> entry : configurationModel.systemEquationPopulationRanges.getLeftMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			
			for(Entry<String, Double> entry : configurationModel.systemEquationPopulationRanges.getRightMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			
			for(Entry<String, Double> entry : configurationModel.performanceTargetsAndWeights.getLeftMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			
			for(Entry<String, Double> entry : configurationModel.populationWeights.getLeftMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			
			for(Entry<String, Double> entry : configurationModel.systemEquationFitnessWeights.getLeftMap().entrySet()){
				output += entry.getKey() + ";" + entry.getValue() + "\n";
			}
			output += lab.recorders.get(i).getTopX(100);
			
			output += "Experiment;" + i + ";\n";
			for(int j = 0; j < lab.recorders.get(i).getGeneration().size(); j++){
				output += lab.recorders.get(i).thisGenerationsMix(j);
			}
			
			output += "};\n";
			
			String filename = "/tmp/" + getDateTime() + "_" + getPopulations() + ".json";
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(filename,"UTF-8");
				writer.println(output);
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
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
		
		if(!configurationModel.dropDownListsList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			totalWork = configurationModel.labParametersCandidate.getRightMap().get(Config.EXPERIMENTS_S).intValue();
			//worst case scenario, every generation created a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidate.getRightMap().get(Config.GENERATION_S).intValue();
			//worst case scenario, every candidate produces a new analysis job
			totalWork *= configurationModel.metaheuristicParametersCandidate.getRightMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		}
		
		
		//times by estimated search space size
		totalWork *= 1; 
		
		this.getEstimatedSearchSpaceSize();
		
		
		//times by experiments
		totalWork *= configurationModel.labParameters.getLeftMap().get(Config.EXPERIMENTS_S).intValue();
		
		
		//worst case scenario, every generation created a new analysis job
		totalWork *= configurationModel.metaheuristicParameters.getLeftMap().get(Config.GENERATION_S).intValue();
		
		
		//worst case scenario, every candidate produces a new analysis job
		totalWork *= configurationModel.metaheuristicParameters.getLeftMap().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		
	}
	
}