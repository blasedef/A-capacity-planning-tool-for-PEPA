package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.metaHeuristicEngine.tools;

import java.util.Random;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class Tools {
	
	public static IParametricDerivationGraph getDevGraphFromAST(ModelNode node){
		
		IParametricDerivationGraph dGraph;
		
		try{
			//so this is how to make the graph :)
			dGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(node, null);
			
		} catch (InterruptedException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Cancel Acknowledgement",
					"The ODE generation process has been cancelled");
			dGraph = null;
			
		} catch (DifferentialAnalysisException e) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					"Differential error",
					e.getMessage());
			dGraph = null;
			
		}
		
		return dGraph;
		
	}
	
	public static Double returnRandomInRange(Double min, Double max, String type){
		
		Random generator = new Random();
		
		if(type.equals(ExperimentConfiguration.INTEGER)){
			
			return generator.nextInt((int)(max - min) + 1) + min; 
			
		} else if (type.equals(ExperimentConfiguration.DOUBLE)) {
			
			return generator.nextDouble() * (max - min) + min; 
			
		} else if (type.equals(ExperimentConfiguration.PERCENT)) {
			
			return generator.nextDouble() * (max - min) + min;
			
		} else {
			//would imply Even, to which I don't need a value
			return 0.0;
			
		}
		
	}
	
	public static Double returnRandom(){
		Random generator = new Random();
		return generator.nextDouble();
	}
	
	public static boolean rollDice(Double p){
		return (returnRandom() < p);
	}

}
