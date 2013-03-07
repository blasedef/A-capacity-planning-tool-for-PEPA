package uk.ac.ed.inf.pepa.eclipse.ui.largescale;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ParametricDerivationGraphBuilder;
import uk.ac.ed.inf.pepa.largescale.ThroughputCalculation;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.ode.DifferentialAnalysisException;

/**
 * Used to pass the setup values on to the MetaHeuristic
 * @author twig
 *
 */
public class CapacityPlanningAnalysisParameters {
	
	public static IPointEstimator[] performanceMetrics = null;
	public static OptionMap fOptionMap = null;
	public static IStatisticsCollector[] collectors = null;
	public static String[] labels = null;
	public static IPepaModel model = null;
	public static int minimumComponentPopulation = 1;
	public static int maximumComponentPopulation = 1;
	
	/**
	 * Build one object for storing all information for the Metaheuristic
	 * @param model
	 */
	public CapacityPlanningAnalysisParameters(IPepaModel model) {
		CapacityPlanningAnalysisParameters.model = model;
	    fOptionMap = model.getOptionMap();
	}
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static IParametricDerivationGraph getFGraph(IPepaModel model){
	
		IParametricDerivationGraph fGraph = null;
		
		try{
			//so this is how to make the graph :)
			fGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(model.getAST(), null);
			
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
	
	
	/**
	 * return an fGraph from a model, this function probably does not make sense being here
	 * @param model
	 * @return IParametricDerivationGraph
	 */
	public static IParametricDerivationGraph getInitialFGraph(){
	
		IParametricDerivationGraph fGraph = null;
		
		try{
			//so this is how to make the graph :)
			fGraph = ParametricDerivationGraphBuilder
					.createDerivationGraph(model.getAST(), null);
			
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
	
}
