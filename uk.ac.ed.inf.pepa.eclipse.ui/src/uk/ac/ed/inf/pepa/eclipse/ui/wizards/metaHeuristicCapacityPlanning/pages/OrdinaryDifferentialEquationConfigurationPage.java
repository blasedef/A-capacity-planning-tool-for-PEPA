package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;


import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ModelType;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.AverageResponseTimeContainer;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.PerformanceMetricContainer;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.ThroughputContainer;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.simulation.AverageResponseTimeCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.DefaultCollector;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;

public class OrdinaryDifferentialEquationConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	private IParametricDerivationGraph dGraph;
	private IPepaModel model;
	private PerformanceMetricContainer performanceMetricContainer;
	
	public OrdinaryDifferentialEquationConfigurationPage(String s, IParametricDerivationGraph dGraph, IPepaModel model) {
		
		//copy title upwards
		super(s,"Ordinary Differential Equation page",
				"Configure Ordinary Differential Equation...");
		
		this.dGraph = dGraph;
		this.model = model;
		
		setPageComplete(false);
		
	}
	
	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		if(Models.evaluator.getValue().equals(ModelType.THROUGHPUT_S)){
			this.performanceMetricContainer = new ThroughputContainer(cb, dGraph, model, container);
		} else {
			this.performanceMetricContainer = new AverageResponseTimeContainer(cb, dGraph, model, container);
		}
		
	}

	@Override
	public void completePage() {
		if(this.performanceMetricContainer.validConfiguration()){
			setPageComplete(true);
			setErrorMessage(null);
			
			IPointEstimator[] estimators = this.performanceMetricContainer.getPerformanceMetrics();
			Models.oDEConfig.setEstimators(estimators);
			
			IStatisticsCollector[] collectors;
			if(Models.evaluator.getValue().equals(ModelType.THROUGHPUT_S)){
				collectors = DefaultCollector.create(estimators);
			} else {
				collectors = new IStatisticsCollector[] { new AverageResponseTimeCollector(
						0, 1) };
			}
			Models.oDEConfig.setCollectors(collectors);
			
			String[] labels = this.performanceMetricContainer.getLabels();
			Models.oDEConfig.setLabels(labels);
			
			((MetaHeuristicCapacityPlanningWizard) getWizard()).updateTargetPage();
			
		} else {
			setPageComplete(false);
			setErrorMessage("Invalid Configuration");
		}
		
	}



}
