package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;


import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.AverageResponseTimeContainer;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.PerformanceMetricContainer;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers.ThroughputContainer;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.dropDownLists.EvaluatorType;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.AverageResponseTimeCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.DefaultCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;

public class OrdinaryDifferentialEquationConfigurationPage extends CapacityPlanningWizardPage {
	
	private PerformanceMetricContainer performanceMetricContainer;
	private PEPAConfig configPEPA;
	private ODEConfig configODE;
	private EvaluatorType evaluator;
	
	public OrdinaryDifferentialEquationConfigurationPage(String s, 
			PEPAConfig configPEPA,
			ODEConfig configODE,
			EvaluatorType evaluator) {
		
		//copy title upwards
		super(s,"Ordinary Differential Equation page",
				"Configure Ordinary Differential Equation...");
		
		
		this.configPEPA = configPEPA;
		this.configODE = configODE;
		this.evaluator = evaluator;
		
		setPageComplete(false);
		
	}
	
	@Override
	protected void constructPage(IValidationCallback cb) {
		if(evaluator.getValue().equals(Config.EVALUATORTHROUGHPUT_S)){
			this.performanceMetricContainer = new ThroughputContainer(cb, container, this.configODE, this.configPEPA);
		} else {
			this.performanceMetricContainer = new AverageResponseTimeContainer(cb, container, this.configODE, this.configPEPA);
		}
		
		@SuppressWarnings("unused")
		Composite composite = (Composite) this.performanceMetricContainer.createDialogArea(container);
		
	}

	@Override
	public void completePage() {
		if(this.performanceMetricContainer.validConfiguration()){
			setErrorMessage(null);
			
			//Used for calculation later
			IPointEstimator[] estimators = this.performanceMetricContainer.getPerformanceMetrics();
			this.configODE.setEstimators(estimators);
			
			//User for calculation later
			IStatisticsCollector[] collectors;
			if(evaluator.getValue().equals(Config.EVALUATORTHROUGHPUT_S)){
				collectors = DefaultCollector.create(estimators);
			} else {
				collectors = new IStatisticsCollector[] { new AverageResponseTimeCollector(
						0, 1) };
			}
			this.configODE.setCollectors(collectors);
			
			//The labels of actions/states
			String[] labels = this.performanceMetricContainer.getLabels();
			this.configODE.setLabels(labels);
			setPageComplete(true);
			
		} else {
			setPageComplete(false);
			setErrorMessage("Invalid Configuration");
		}
		
	}



}
