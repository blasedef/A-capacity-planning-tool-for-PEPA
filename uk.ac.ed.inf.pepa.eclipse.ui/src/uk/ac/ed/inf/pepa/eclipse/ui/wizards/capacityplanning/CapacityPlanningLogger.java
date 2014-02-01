package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class CapacityPlanningLogger {
	
	static Logger log = Logger.getLogger(CapacityPlanningLogger.class);
	
	CapacityPlanningLogger(){
		BasicConfigurator.configure();
	}

}
