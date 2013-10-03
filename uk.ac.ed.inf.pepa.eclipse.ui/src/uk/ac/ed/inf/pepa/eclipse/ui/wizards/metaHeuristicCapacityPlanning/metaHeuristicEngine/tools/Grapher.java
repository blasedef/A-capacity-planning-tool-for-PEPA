package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class Grapher {
	
	public Grapher(){
		
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
