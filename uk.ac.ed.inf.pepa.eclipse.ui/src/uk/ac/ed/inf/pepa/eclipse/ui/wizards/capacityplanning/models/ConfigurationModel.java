package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists.*;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.textInputs.*;

public class ConfigurationModel {
	
	//configuration models
	public ArrayList<DropDownLists> dropDownListsList;
	
	public TextInputs systemEquationFitnessWeights; 
	public TextInputs metaheuristicFitnessWieghts; 
	
	public TextInputs metaheuristicParameters;
	public TextInputs metaheuristicParametersCandidate; 
	
	public TextInputs systemEquationCandidate;
	
	public TextInputs performanceTargetsAndWeights;
	public TextInputs metaheuristicTargetsAndWeights;
	
	public ODEConfig configODE;
	
	public PEPAConfig configPEPA;
	
	public ConfigurationModel(IPepaModel model){
		
		dropDownListsList = new ArrayList<DropDownLists>();
		
		dropDownListsList.add(new EvaluatorType());
		dropDownListsList.add(new MetaheuristicType());
		dropDownListsList.add(new NetworkType());
		dropDownListsList.add(new AdditionalCosts());
		
		systemEquationFitnessWeights = new Fitness();
		metaheuristicFitnessWieghts = new Fitness();
		
		metaheuristicParameters = new MetaheuristicParameters();
		
		metaheuristicParametersCandidate = new MetaheuristicParameters();
		
		systemEquationCandidate = new SystemEquation();
		
		performanceTargetsAndWeights = new Targets(10.0);
		metaheuristicTargetsAndWeights = new Targets(0.0);
		
		configODE = new ODEConfig();
		configPEPA = new PEPAConfig(model);
		
		
	}

}
