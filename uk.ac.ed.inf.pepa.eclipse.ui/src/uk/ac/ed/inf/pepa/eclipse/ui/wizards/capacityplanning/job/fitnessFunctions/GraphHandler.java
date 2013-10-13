package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class GraphHandler {
	
	private ODEConfig configODE;
	
	public GraphHandler(ODEConfig configODE){
		this.configODE = configODE;
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
	
	public HashMap<String, Double> getPerformanceResultsMap(ModelNode node, IProgressMonitor monitor) {
		AnalysisOfFluidSteadyState analysis = new AnalysisOfFluidSteadyState(this.getDevGraphFromAST(node),
				configODE.getOptionMap(),
				configODE.getEstimators(),
				configODE.getCollectors(),
				configODE.getLabels(),
				new SubProgressMonitor(monitor,1)
				);
		return analysis.getResults();
	}

}
