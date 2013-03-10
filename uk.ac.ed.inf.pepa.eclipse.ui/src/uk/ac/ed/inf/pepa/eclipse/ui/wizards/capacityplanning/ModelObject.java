package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class ModelObject {
	

	private Map<String, Double> mySystemEquation = new HashMap<String, Double>();
	private Map<String, Double> myIndividualFitness = new HashMap<String, Double>();
	private IParametricDerivationGraph fGraph;
	private AnalysisOfFluidSteadyState analyseThis;
	private IProgressMonitor monitor;
	private ModelNode node;
	private SetASTVisitor writeAST;
	private GetASTVisitor readAST;
	private double myFitness;
	private double totalAgentPopulation;
	private String pvString;
	private String pString;
	Random generator = new Random();


	public ModelObject(IProgressMonitor monitor) {
		this.monitor = monitor;
		this.node = (ModelNode) ASTSupport.copy(CapacityPlanningAnalysisParameters.model.getAST());
		this.analyseThis = new AnalysisOfFluidSteadyState();
		this.writeAST = new SetASTVisitor(this.mySystemEquation);
		this.readAST = new GetASTVisitor(this.mySystemEquation);
		this.readAST();
		this.fGraph = this.getFGraph();
		this.updateTotalAgentPopulation();
		this.myFitness = 1000000;
		this.pvString = "";
		this.pString = "";
		this.updateFitness();
	}
	

	/**
	 * toString
	 */
	public String toString(){
		String name = "";
		for(Map.Entry<String, Double> entry : this.mySystemEquation.entrySet()){
			name += entry.getKey() + "," + entry.getValue() + ",";
		}
		name += this.myFitness + "," + this.pvString + "," + this.pString + ",";
		for(Map.Entry<String, Double> entry : this.myIndividualFitness.entrySet()){
			name += entry.getKey() + "," + entry.getValue() + ",";
		}
		name += "\n";
		return name;
	}
		
	/**
	 * mutation occurs on the saved sys.equation
	 * therefore the AST will need to be 'set'
	 * and then the graph will need to be 'got' again
	 * finally the totalpopulation will have to be updated
	 */
	public void mutateMe(){
		
		int min = CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Minimum Population:").intValue();
		int max = CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Maximum Population:").intValue();
		
		/**
		 * mutation section
		 */
		for(Map.Entry<String, Double> entry : this.mySystemEquation.entrySet()){
			double p = generator.nextDouble();
			if(p < CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Mutation Probability:")){
				double newCount = generator.nextInt(max - min + 1) + min;
				this.mySystemEquation.put(entry.getKey(), newCount);
			}
		}
		
		this.writeASTUpdateGraph();
	}
	
	/**
	 * assumes graph has been updated with this models sys.equation
	 */
	public void updateFitness(){
		
		Map<String, Double> results = this.analyseThis.getResults(this.fGraph, this.monitor);
		double resultingSize = results.size();
		//for now we treat them all evenly...
		double pvWeighting = 1/resultingSize;
		double pvFitness = 0;
		Map<String, Double> scaledPerformance = new HashMap<String, Double>();
		for(Map.Entry<String, Double> entry : results.entrySet()){
			double target = CapacityPlanningAnalysisParameters.targetValues.get(entry.getKey());
			if(target == 0){
				scaledPerformance.put(entry.getKey(), 100.0);
				pvFitness += 0;
				this.myIndividualFitness.put(entry.getKey(), 0.0);
			} else {
				double pv = results.get(entry.getKey());
				this.myIndividualFitness.put(entry.getKey(), pv);
				double scaled = 0.0;
				//TODO make these work correctly...
				if(CapacityPlanningAnalysisParameters.performanceRequirementType == 0){
					if(CapacityPlanningAnalysisParameters.performanceRequirementTargetLimit){
						scaled = Math.abs(100-((pv/target)*100));
					}
				} else {
					//average response time...
				}
				
				scaledPerformance.put(entry.getKey(), scaled);
				pvFitness += scaled*pvWeighting;
			}
			
		}
		
		this.updateTotalAgentPopulation();
		double scaledAgentPopulation = ((this.totalAgentPopulation/CapacityPlanningAnalysisParameters.maximumPossibleAgentCount)*100);
		double alpha = CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Performance to Population:");
		double beta = 1.0 - alpha;
		this.pvString = pvFitness + "";
		this.pString = scaledAgentPopulation + "";
		this.myFitness = (alpha*pvFitness) + (beta*scaledAgentPopulation);
		
	}
	
	/**
	 * update and return the fitness of this ModelObject
	 * @return
	 */
	public double getFitness(){
		this.updateFitness();
		return this.myFitness;
		
	}
	
	/**
	 * Do this whenever the system equation has been updated
	 */
	private void updateTotalAgentPopulation(){
		this.totalAgentPopulation = 0;
		for(Map.Entry<String, Double> entry : this.mySystemEquation.entrySet()){
			this.totalAgentPopulation += entry.getValue();
		}
	}
	
	/**
	 * Read from the AST
	 */
	public void readAST(){
		this.writeAST.setSystemEquation(this.mySystemEquation);
		this.node.accept(this.readAST);
		this.mySystemEquation = this.writeAST.getSystemEquation();
	}
	
	/**
	 * update the AST; 
	 * update the Graph
	 */
	public void writeASTUpdateGraph(){
		this.writeAST();
		this.updateMyGraph();
	}
	
	/**
	 * Update my graph
	 */
	public void updateMyGraph(){
		this.fGraph = getFGraph();
	}
	
	/**
	 * Write to the AST
	 */
	public void writeAST(){
		this.writeAST.setSystemEquation(this.mySystemEquation);
		this.node.accept(this.writeAST);
	}
	
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public IParametricDerivationGraph getFGraph(){
	
		IParametricDerivationGraph fGraph = null;
		
		try{
			//so this is how to make the graph :)
			fGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(this.node, null);
			
		} catch (InterruptedException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Cancel Acknowledgement",
					"The ODE generation process has been cancelled");
			
		} catch (DifferentialAnalysisException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Differential error",
					e.getMessage());
			
		}
		
		return fGraph;
	}



	public Map<String, Double> getSystemEquation() {
		return this.mySystemEquation;
	}
	
	/**
	 * copy incoming system equation, update total population, update AST node, update Graph, and update fitness
	 * @param systemEquation
	 */
	public void setModelObject(Map<String, Double> systemEquation) {
		this.mySystemEquation = new HashMap<String, Double>();
		for(Map.Entry<String, Double> entry : systemEquation.entrySet()){
			this.mySystemEquation.put(entry.getKey(),entry.getValue());
		}
		this.updateTotalAgentPopulation();	
		this.writeASTUpdateGraph();
		this.updateFitness();
	}
	
}
