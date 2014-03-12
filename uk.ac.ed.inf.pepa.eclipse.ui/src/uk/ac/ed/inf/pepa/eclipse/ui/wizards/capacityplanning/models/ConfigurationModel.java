package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.*;

public class ConfigurationModel {
	
	//configuration models
	public ArrayList<DropDownLists> dropDownListList;
	public ArrayList<DropDownLists> secondDropDownListList;
	
	public TextInputs systemEquationFitnessWeights; 
	public TextInputs metaheuristicFitnessWeights; 
	
	public TextInputs labParametersRoot;
	public TextInputs labParametersCandidateLeaf;
	
	public TextInputs metaheuristicParametersRoot;
	public TextInputs metaheuristicParametersCandidateLeaf; 
	
	public TextInputs systemEquationPopulationRanges;
	public TextInputs populationWeights;
	
	public TextInputs performanceTargetsAndWeights;
	public TextInputs metaheuristicTargetsAndWeights;
	
	public ODEConfig configODE;
	
	public PEPAConfig configPEPA;
	
	public ConfigurationModel(IPepaModel model){
		
		dropDownListList = new ArrayList<DropDownLists>();
		
		dropDownListList.add(new EvaluatorType());
		dropDownListList.add(new SearchType());
		
		systemEquationFitnessWeights = new FitnessFunctionWeighting();
		metaheuristicFitnessWeights = new FitnessFunctionWeighting();
		
		//root settings
		labParametersRoot = new LabParameters();
		metaheuristicParametersRoot = new MetaheuristicParameters();
		
		//chained settings
		labParametersCandidateLeaf = new LabParameters();
		metaheuristicParametersCandidateLeaf = new MetaheuristicParameters();
		((MetaheuristicParameters) metaheuristicParametersCandidateLeaf).update(Config.METAHEURISTICTYPEPARTICLESWARMOPTIMISATION_S);
		
		secondDropDownListList = new ArrayList<DropDownLists>();
		secondDropDownListList.add(new MetaheuristicType());
		
		systemEquationPopulationRanges = new PopulationMinAndMax();
		populationWeights = new PopulationWeights();
		
		performanceTargetsAndWeights = new TargetsAndWeights(10.0);
		metaheuristicTargetsAndWeights = new TargetsAndWeights(0.0);
		
		configPEPA = new PEPAConfig(model);
		configODE = new ODEConfig(model.getOptionMap());
		
		
	}

}
