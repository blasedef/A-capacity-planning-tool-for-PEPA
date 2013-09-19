package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.parameters;

/**
*
* PerformanceValueParameters:
* Used to provide values to the Wizard pages, and to also store variables for the Metaheuristic Job
* @author Christopher Williams
*
*/

public class PerformanceEvaluationParameters {
	
	//Array for wizard combo box
	public static final String[] performanceEvaluationTypes = new String[] {"Throughput","Average Response Time"};
	
	//Evaluation type, 0 = Throughput, 1 = Average Response Time
	public static int EVALUATORTYPE = 0;
	
	//Array for additional costs page
	public static final String[] additionalCosts = new String[] {"No","Yes"};
	
	//Additional Costs
	public static int ADDITIONALCOSTS = 0;
	
	public PerformanceEvaluationParameters(){	
	}
	
}