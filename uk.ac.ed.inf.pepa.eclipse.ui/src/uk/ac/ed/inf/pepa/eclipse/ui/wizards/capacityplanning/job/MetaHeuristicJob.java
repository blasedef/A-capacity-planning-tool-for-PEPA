package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.LabParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.SystemEquationCandidateParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.SystemEquationFitnessFunctionParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.TextInputs;


public class MetaHeuristicJob extends Job{

	private Lab lab;
	@SuppressWarnings("unused")
	private ConfigurationModel configurationModel;
	private LabParameters primaryLabParameters;
	private RecordParameters recordParameters;
	private MetaHeuristicParameters primaryMetaheuristicParameters;
	private SystemEquationFitnessFunctionParameters systemEquationFitnessFunctionParameters;
	private SystemEquationCandidateParameters systemEquationCandidateParameters;
	
	public MetaHeuristicJob(String name, ConfigurationModel configurationModel) {
		super(name);
		
		this.configurationModel = configurationModel;
		
		if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S))
			postProcessHillClimbing(configurationModel.metaheuristicParametersRoot);
			postProcessHillClimbing(configurationModel.metaheuristicParametersCandidateLeaf);
		
		primaryLabParameters = new LabParameters(configurationModel, true);
		recordParameters = new RecordParameters(configurationModel);
		primaryMetaheuristicParameters = new MetaHeuristicParameters(configurationModel, true);
		systemEquationCandidateParameters = new SystemEquationCandidateParameters(configurationModel);
		systemEquationFitnessFunctionParameters = new SystemEquationFitnessFunctionParameters(configurationModel);
		
		//select network type, then primary metaheuristic
		if(configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINSINGLE_S)){
			
			
			
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
		} else if(configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINDRIVEN_S)){
			
			//setup secondary lab
			LabParameters secondaryLabParameters = new LabParameters(configurationModel, false);
			//setup secondary metaheuristic
			MetaHeuristicParameters secondaryMetaheuristicParameters = new MetaHeuristicParameters(configurationModel, false);
			
			//hill climbing
			if(configurationModel.dropDownListList.get(1).getValue().equals(Config.METAHEURISTICTYPEHILLCLIMBING_S)){
				
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
		metaheuristicParameters.getLeftMap().put(Config.MUTATIONPROBABILITY_S, 1.0);
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
		
		return status;
	}

}