package uk.ac.ed.inf.pepa.cpt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.CandidateNode;
import uk.ac.ed.inf.pepa.parsing.ASTSupport;
import uk.ac.ed.inf.pepa.parsing.ModelNode;
import uk.ac.ed.inf.pepa.tools.PepaTools;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;


public class Cpt {
	
	public static ModelNode node; 
	
	public static final int THREADS =  Runtime.getRuntime().availableProcessors();
	
	public static void main(String[] args) throws IOException {
		
		if(args.length > 0){
			node = (ModelNode) PepaTools.parse(readText(args[0]));
		} else {
			node = (ModelNode) PepaTools.parse(readText("/home/twig/c.pepa"));
		}
		
		CPTAPI.setModel(node);
		
		CPTAPI.printConfiguration();
		
		setupForSingleART();
		
		CPTAPI.createCPT();
		
		CPTAPI.startCPT();
		
		//CPTAPI.printConfiguration();
		
		CPTAPI.printQueue();
		
		//CPTAPI.toJSON();
		
	}
	
	/**
	 * How to read in a pepa model
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String readText(String fileName) throws IOException {
		String result = null;

		if (fileName != null) {
			File file = new File(fileName);
			StringBuffer sb = new StringBuffer();
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = null;
				String lineSearator = System.getProperty("line.separator");
				while ((line = br.readLine()) != null) { // while not
					// at the
					// end of the file
					// stream do
					sb.append(line);
					sb.append(lineSearator);
				}// next line
				result = sb.toString();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException ioe) {
						ioe.printStackTrace(System.err);
					}
				}
			}
		}// else: input unavailable

		return result;
	}// readText()
	
	
	/**
	 * Just a test method, some way to make sure the right model is being used
	 * 
	 * @param modelName (fullpath to pepa file)
	 * @throws IOException
	 */
	private static void printModel(ModelNode node) throws IOException {
		System.out.println(ASTSupport.toString(node));
	}
	
	
	public static void setupForSingleART(){
		
		CPTAPI.getEvaluationControls().setValue(Config.EVALARPT);
		
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABLOC, "1"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABORG, "10"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABPOP, "10"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABGLO, "4"));
		
		System.out.println(CPTAPI.getHCParameterControls().setValue(Config.LABMUT, "1.0"));
		System.out.println(CPTAPI.getExperimentControls().setValue(Config.LABEXP, "1"));
		
		System.out.println(CPTAPI.getGenerationControls().setValue(Config.LABGEN, "10"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMIN, "600"));
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMAX, "600"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMAX, "80"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMAX, "80"));
		
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdBrowseTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 18,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdSelectTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 20,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdConfirmTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 22,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdRegisterTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdRegisterWaitTag",true));
		
		
		CPTAPI.updateTargetControl();
		
		System.out.println(CPTAPI.getTargetControl().setValue("Average response time", Config.LABTAR, "15.0"));
		
	}
	
	public static void setupForSingleTHR(){
		
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABLOC, "1"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABORG, "10"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABPOP, "10"));
		System.out.println(CPTAPI.getPSOParameterControls().setValue(Config.LABGLO, "4"));
		
		System.out.println(CPTAPI.getHCParameterControls().setValue(Config.LABMUT, "1.0"));
		System.out.println(CPTAPI.getExperimentControls().setValue(Config.LABEXP, "1"));
		
		System.out.println(CPTAPI.getGenerationControls().setValue(Config.LABGEN, "10"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMIN, "600"));
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMAX, "600"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPerformanceControls().setSelected("replystudentregister",true));
		
		CPTAPI.updateTargetControl();
		
		System.out.println(CPTAPI.getTargetControl().setValue("replystudentregister", Config.LABTAR, "15.0"));
		
	}
	
	public static void setupForDrivenART(){
		
		CPTAPI.getEvaluationControls().setValue(Config.EVALARPT);
		CPTAPI.getSearchControls().setValue(Config.SEARCHDRIVEN);
		
		System.out.println(CPTAPI.getHCParameterControls().setValue(Config.LABMUT, "1.0"));
		System.out.println(CPTAPI.getExperimentControls().setValue(Config.LABEXP, "2"));
		
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABGEN, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABORG, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABLOC, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABGLO, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABPOP, Config.LABMAX, "10"));
		
		System.out.println(CPTAPI.getGenerationControls().setValue(Config.LABGEN, "10"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMIN, "600"));
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMAX, "600"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMAX, "80"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMAX, "80"));
		
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdBrowseTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 18,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdSelectTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 20,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdConfirmTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected((short) 22,true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdRegisterTag",true));
		System.out.println(CPTAPI.getPerformanceControls().setSelected("StdRegisterWaitTag",true));
		
		
		CPTAPI.updateTargetControl();
		
		System.out.println(CPTAPI.getTargetControl().setValue("Average response time", Config.LABTAR, "15.0"));
	}
	
	public static void setupForDrivenTHR(){
		
		//CPTAPI.getEvaluationControls().setValue(Config.EVALARPT);
		CPTAPI.getSearchControls().setValue(Config.SEARCHDRIVEN);
		
		System.out.println(CPTAPI.getHCParameterControls().setValue(Config.LABMUT, "1.0"));
		System.out.println(CPTAPI.getExperimentControls().setValue(Config.LABEXP, "1"));
		
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABGEN, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABORG, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABLOC, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABGLO, Config.LABMAX, "100"));
		System.out.println(CPTAPI.getPSORangeParameterControls().setValue(Config.LABPOP, Config.LABMAX, "10"));
		
		System.out.println(CPTAPI.getGenerationControls().setValue(Config.LABGEN, "10"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMIN, "600"));
		System.out.println(CPTAPI.getPopulationControls().setValue("StdThinkTag", Config.LABMAX, "600"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Portal", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValUni", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("ValCur", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Database", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("Logger", Config.LABMAX, "320"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("PS_1", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMIN, "1"));
		System.out.println(CPTAPI.getPopulationControls().setValue("TTPD_1", Config.LABMAX, "160"));
		
		System.out.println(CPTAPI.getPerformanceControls().setSelected("replystudentregister",true));
		
		CPTAPI.updateTargetControl();
		
		System.out.println(CPTAPI.getTargetControl().setValue("replystudentregister", Config.LABTAR, "15.0"));
	}

}
