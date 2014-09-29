package uk.ac.ed.inf.pepa.cpt;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.cpt.config.control.ListControl;
import uk.ac.ed.inf.pepa.cpt.config.control.ParameterControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PerformanceControl;
import uk.ac.ed.inf.pepa.cpt.config.control.PopulationControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.PSOControl;
import uk.ac.ed.inf.pepa.cpt.config.control.populationControl.TargetControl;
import uk.ac.ed.inf.pepa.cpt.searchEngine.CPT;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ResultNode;
import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.parsing.ModelNode;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class CPTAPI {
	
	private static Config configuration = null;
	private static CPT cpt = null; 
	
	public static void setModel(ModelNode node){
		CPTAPI.configuration = new Config(node);
	}
	
	public static ListControl getEvaluationControls(){
		return CPTAPI.configuration.evaluationController;
	}
	
	public static ListControl getSearchControls(){
		return CPTAPI.configuration.searchController;
	}
	
	public static Control getMHParameterControls(){
		if(CPTAPI.getSearchControls().getValue().equals(Config.SEARCHSINGLE)){
			return CPTAPI.configuration.psoRangeController;
		} else {
			return CPTAPI.configuration.hillController;
		}
	}
	
	public static ParameterControl getOptionMapControls(){
		return CPTAPI.configuration.optionMapController;
	}
	
	public static ParameterControl getHCControls(){
		return CPTAPI.configuration.hillController;
	}
	
	public static PSOControl getPSORangeParameterControls(){
		return CPTAPI.configuration.psoRangeController;
	}
	
	public static ParameterControl getCostFunctionControls(){
		return CPTAPI.configuration.costFunctionController;
	}
	
	public static PerformanceControl getPerformanceControls(){
		return CPTAPI.configuration.actionAndProcessSelectionController;
	}
	
	public static PopulationControl getPopulationControls(){
		return CPTAPI.configuration.rateAndComponentRangeAndWeightController;
	}
	
	public static IParametricDerivationGraph getGraph(){
		return CPTAPI.configuration.getGraph();
	}
	
	public static ModelNode getModel(){
		return CPTAPI.configuration.getNode();
	}
	
	public static OptionMap getOptionMap(){
		return CPTAPI.configuration.getOptionMap();
	}
	
	public static IPointEstimator[] getEstimators(){
		return CPTAPI.configuration.getEstimators();
	}
	
	public static IStatisticsCollector[] getCollectors(){
		return CPTAPI.configuration.getCollectors();
	}
	
	public static String[] getLabels(){
		return CPTAPI.configuration.getLabels();
	}
	
	public static ListControl getDomainControl(){
		return CPTAPI.configuration.domainController;
	}
	
	public static void updateTargetControl(){
		CPTAPI.configuration.targetControl.clearMap();
		CPTAPI.configuration.targetControl.update();
	}
	
	public static TargetControl getTargetControl(){
		return CPTAPI.configuration.targetControl;
	}
	
	public static IStatus startCPT(){
		if(cpt != null){
			return cpt.start();
		};
		return Status.CANCEL_STATUS;
	}

	public static void setFileName(String fileName) {
		CPTAPI.configuration.setFileName(fileName);
	}
	
	public static void setFolderName(String osString) {
		CPTAPI.configuration.setFolderName(osString);
	}
	
	public static String getFileName() {
		return CPTAPI.configuration.getFileName();
	}
	
	public static String getFolderName() {
		return CPTAPI.configuration.getFolderName();
	}

	public static void createCPT(IProgressMonitor monitor) {
		if(configuration != null){
			cpt = new CPT(monitor);
		};
		
	}
	
	public static int totalHCLabWork(){
		
		return Integer.parseInt(CPTAPI.getMHParameterControls().getValue(Config.LABEXP));
	}
	
	public static int totalHCWork(){
		
		return Integer.parseInt(CPTAPI.getMHParameterControls().getValue(Config.LABGEN));

	}
	
	public static int totalPSOLabWork(){
		
		return Integer.parseInt(CPTAPI.getPSORangeParameterControls().getValue(Config.LABEXP, Config.LABMAX));
	}
	
	public static int totalPSOWork(){
		
		int gen, pop;
		
		gen = Integer.parseInt(CPTAPI.getPSORangeParameterControls().getValue(Config.LABGEN, Config.LABMAX));
		pop = Integer.parseInt(CPTAPI.getPSORangeParameterControls().getValue(Config.LABPOP, Config.LABMAX));
		
		return gen*pop;
	}
	
	public static void setTime(long time){
		CPTAPI.configuration.startTime = time;
	}
	
	public static long getTime(){
		return CPTAPI.configuration.startTime;
	}

	public static String[] toPrint(){
		return CPTAPI.configuration.toPrint();
	}

	public static void setTotalResults() {
		CPTAPI.configuration.resultSize++;
		
	}

	public static int getResultSize() {
		return CPTAPI.configuration.resultSize;
	}
	
	public static ArrayList<ResultNode> getResultList(){
		return CPTAPI.configuration.resultQ;
	}
	
	public static void setResultLabels(String[] strings){
		CPTAPI.configuration.resultLabels = strings;
	}
	
	public static String[] getResultLabels(){
		return CPTAPI.configuration.resultLabels;
	}
	
}
