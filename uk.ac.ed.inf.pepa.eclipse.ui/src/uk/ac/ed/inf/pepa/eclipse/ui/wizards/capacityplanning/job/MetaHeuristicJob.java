package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.SystemEquationCandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.SystemEquationFitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Results;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;



public class MetaHeuristicJob extends Job implements CapacityPlanningSubject {
	
	private Lab lab;
	private ConfigurationModel configurationModel;
	private LabParameters primaryLabParameters;
	private RecordParameters recordParameters;
	private MetaHeuristicParameters primaryMetaheuristicParameters;
	private SystemEquationFitnessFunctionParameters systemEquationFitnessFunctionParameters;
	private SystemEquationCandidateParameters systemEquationCandidateParameters;
	
	//temp
	private JSONObject json;
	
	public MetaHeuristicJob(String name, ConfigurationModel configurationModel) {
		super(name);
		
		this.configurationModel = configurationModel;
		
		if(configurationModel.dropDownListList.get(1).getValue().equals(Config.SEARCHDRIVEN_S))
			postProcessHillClimbing(configurationModel.metaheuristicParametersRoot);
			configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap().remove(Config.MUTATIONPROBABILITY_S);
			//postProcessHillClimbing(configurationModel.metaheuristicParametersCandidateLeaf);
		
		primaryLabParameters = new LabParameters(configurationModel, true);
		
		primaryMetaheuristicParameters = new MetaHeuristicParameters(configurationModel, true);
		systemEquationCandidateParameters = new SystemEquationCandidateParameters(configurationModel);
		systemEquationFitnessFunctionParameters = new SystemEquationFitnessFunctionParameters(configurationModel);
		
		recordParameters = new RecordParameters(configurationModel);
		
		//select network type, then primary metaheuristic
		if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S)){
			
			
			
			if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
				
				this.lab = new SingleNetworkHillClimbingLab(primaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
				
			} else if (configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
				
				this.lab = new SingleNetworkGeneticAlgorithmLab(primaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
				
			} else {
				
				this.lab = new SingleNetworkParticleSwarmOptimisationLab(primaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
				
			}
			//driven
		} else if(configurationModel.dropDownListList.get(1).getValue().equals(Config.SEARCHDRIVEN_S)){
			
			//setup secondary lab
			LabParameters secondaryLabParameters = new LabParameters(configurationModel, false);
			//setup secondary metaheuristic
			MetaHeuristicParameters secondaryMetaheuristicParameters = new MetaHeuristicParameters(configurationModel, false);
			
			//hill climbing
			if(configurationModel.dropDownListList.get(1).getValue().equals(Config.SEARCHDRIVEN_S)){
				
				this.lab = new DrivenNetworkHillClimbingLab(primaryLabParameters,
						secondaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						secondaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
				
			} else if (configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEGENETICALGORITHM_S)) {
				
				this.lab = new SingleNetworkGeneticAlgorithmLab(primaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
				
			} else {
				
				this.lab = new SingleNetworkParticleSwarmOptimisationLab(primaryLabParameters,
						recordParameters,
						primaryMetaheuristicParameters,
						systemEquationFitnessFunctionParameters,
						systemEquationCandidateParameters);
			}
		} else {
			
			this.lab = new SingleNetworkHillClimbingLab(primaryLabParameters,
					recordParameters,
					primaryMetaheuristicParameters,
					systemEquationFitnessFunctionParameters,
					systemEquationCandidateParameters);
			
		}
		
		
		
	}

	private void postProcessHillClimbing(TextInputs metaheuristicParameters) {
		metaheuristicParameters.getLeftMap().put(Config.INITIALCANDIDATEPOPULATION_S, 1.0);
		//metaheuristicParameters.getLeftMap().put(Config.MUTATIONPROBABILITY_S, 1.0);
		metaheuristicParameters.getLeftMap().put(Config.GENERATION_S,
		metaheuristicParameters.getLeftMap().get(Config.GENERATIONHC_S));
		metaheuristicParameters.getLeftMap().remove(Config.GENERATIONHC_S);

		
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		Tool.setStartTime();
		
		IStatus status;
		
		status = this.lab.startExperiments(monitor, true);
		
		this.lab.complete();
		
		this.notifyObservers();
		
		return status;
	}

	@Override
	public void notifyObservers() {
		CapacityListenerManager.results = lab.results;
		CapacityListenerManager.listener.capacityPlanningJobCompleted();
		
		/**
		 * temp inclusion of json output
		 */
		//writeToDisk();
		
		
	}
	
	/**
	 * temp inclusion
	 */
	public void createJSON(){
		
		String networkType = configurationModel.dropDownListList.get(1).getValue();
		
		this.json = new JSONObject(networkType);
		
		for(Results r : CapacityListenerManager.results){
			this.json.put(r.key,r.value);
		}
		
	}
	
	/**
	 * temp inclusion
	 * @param generation
	 */
	public void writeToDisk(){
		
		this.createJSON();
		
		boolean success;
		
		success = (new File(this.setFileOutputPath().toOSString())).mkdirs();
		if (!success) {
		    // Directory creation failed
		}
		
		String networkType = configurationModel.dropDownListList.get(1).getValue();
		
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						configurationModel.configPEPA.getPepaModel().getUnderlyingResource(), ""));
		
		String filename = this.setFileOutputPath().addTrailingSeparator().toOSString() 
		+ Tool.getDateTime() 
		+ "_"
		+ handle.getName()
		+ "_"
		+ networkType
		+ ".json";
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename,"UTF-8");
			writer.println(json.output());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Path setFileOutputPath(){
		
		//regardless of how this is run, everyone saves to the same folder
		return new Path( ResourcesPlugin.getWorkspace().getRoot().getLocation().addTrailingSeparator().toOSString() 
				+ "results_summary");
	}

}