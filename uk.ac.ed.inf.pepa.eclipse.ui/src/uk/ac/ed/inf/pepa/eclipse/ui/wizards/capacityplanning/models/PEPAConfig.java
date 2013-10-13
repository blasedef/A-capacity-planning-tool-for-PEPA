package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

/**
 * A place for storing all PEPAModel information, and for getting values for the Pages
 * @author twig
 *
 */
public class PEPAConfig {
	
	//The underlying PEPAModel
	private IPepaModel model;
	
	//An AST ModelNode
	private ModelNode node;
	
	//A derivation of the model for the wizard
	private IParametricDerivationGraph dGraph;

	public PEPAConfig(IPepaModel model){
		this.model = model;
		this.node = this.model.getAST();
		this.dGraph = this.getDevGraphFromAST(this.node);
	}
	
	public String[] getSystemEquation() {
		
		ArrayList<String> components = new ArrayList<String>();
		
		for (ISequentialComponent c : dGraph.getSequentialComponents()){
			components.add(c.getName());
		}
		
		return components.toArray(new String[0]);
	}
	
	public Integer[] getInitialPopulation() {
		
		ArrayList<Integer> components = new ArrayList<Integer>();
		
		for (ISequentialComponent c : dGraph.getSequentialComponents()){
			components.add(c.getInitialPopulationLevel());
		}
		
		return components.toArray(new Integer[0]);
	}
	
	public IPepaModel getPepaModel(){
		return this.model;
	}
	
	public ModelNode getModelNode(){
		return this.node;
	}
	
	public ModelNode getNewModelNode(){
		ModelNode newNode = (ModelNode) ASTSupport.copy(model.getAST());
		return newNode;
	}
	
	public IParametricDerivationGraph getGraph(){
		return this.dGraph;
	}
	
	public IParametricDerivationGraph getDevGraphFromAST(ModelNode node){
		
		IParametricDerivationGraph dGraph;
		
		try{
			
			//so this is how to make the graph :)
			dGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(node, null);
			
		} catch (InterruptedException e) {
			System.out.println(e);
			dGraph = null;
			
		} catch (DifferentialAnalysisException e) {
			System.out.println(e);
			dGraph = null;
			
		}
		
		return dGraph;
		
	}
	
}