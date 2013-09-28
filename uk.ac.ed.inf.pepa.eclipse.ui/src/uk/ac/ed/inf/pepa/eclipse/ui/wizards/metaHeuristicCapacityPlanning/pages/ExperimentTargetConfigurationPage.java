package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.BorderedCompositeWithDoubleTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.BorderedCompositeWithTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class ExperimentTargetConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public ExperimentTargetConfigurationPage(String title) {
		
		//copy title upwards
		super(title,"Candidate metaheuristic performance target and population range","Determine the performance targets and population ranges...");
		
		ExperimentConfiguration.metaHeuristic.updateTargetValues();
		ExperimentConfiguration.metaHeuristic.updateMinPopulationRanges();
		ExperimentConfiguration.metaHeuristic.updateMaxPopulationRanges();
		
	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		if(!bool){
			setErrorMessage("Invalid Configuration");
		} else {
			setErrorMessage(null);
		}
		((MetaHeuristicCapacityPlanningWizard) getWizard()).updateSummaryPage();
		setPageComplete(bool);
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
		GetValue getFitness = new GetFitnessValue();
		String[] topKeys = new String[] {ExperimentConfiguration.DELTASIGMA_S};
		Map<String,Number> topMap = ExperimentConfiguration.metaHeuristic.getFitnessMap();
		
		BorderedCompositeWithTextWidget topBorderedCompositeWithTextWidget = new BorderedCompositeWithTextWidget("Experiment target balance", 
				topKeys, 
				topMap, 
				cb, 
				container, 
				getFitness, 
				null);
		widgets.add(topBorderedCompositeWithTextWidget);
		
		if(ExperimentConfiguration.metaHeuristicNetworkType.getValue().equals(ExperimentConfiguration.METAHEURISTICDRIVEN_S)){
			createDrivenTargets(cb);
		} else {
			createPipeLineTargets(cb);
		}
		
		//to update the summaryPage
		completePage();
		
	}	
	
	public void createDrivenTargets(IValidationCallback cb){
		
		GetValue getMinPop = new GetEMinPopValue();
		GetValue getMaxPop = new GetEMaxPopValue();
		TestValues test = new LeftLessThanOrEqualToRight();
		String[] optionsMinPop = ExperimentConfiguration.metaHeuristic.getExperimentMinPopulationOptions(); 
		String[] optionsMaxPop = ExperimentConfiguration.metaHeuristic.getExperimentMaxPopulationOptions();
		Map<String,Number> mapMinPop = ExperimentConfiguration.metaHeuristic.getExperimentMinPopulationMap();
		Map<String,Number> mapMaxPop = ExperimentConfiguration.metaHeuristic.getExperimentMaxPopulationMap();
		String typeMinPop = null;
		String typeMaxPop = null;
		
		BorderedCompositeWithDoubleTextWidget bottomBorderedCompositeWithDoubleTextWidget = new BorderedCompositeWithDoubleTextWidget("Second metaheuristic setting ranges","Minimum population","Maximum population", 
				optionsMinPop,
				optionsMaxPop,
				mapMinPop,
				mapMaxPop,
				cb, 
				container, 
				getMinPop,
				getMaxPop,
				typeMinPop,
				typeMaxPop,
				test);
		
		widgets.add(bottomBorderedCompositeWithDoubleTextWidget);
		
	}
	
	public void createPipeLineTargets(IValidationCallback cb){
		
		GetValue getMinPop = new GetEMinPopValue();
		GetValue getMaxPop = new GetEMaxPopValue();
		TestValues test = new LeftLessThanOrEqualToRight();
		String[] optionsMinPop = ExperimentConfiguration.metaHeuristic.getExperimentMinPopulationOptions(); 
		String[] optionsMaxPop = ExperimentConfiguration.metaHeuristic.getExperimentMaxPopulationOptions();
		Map<String,Number> mapMinPop = ExperimentConfiguration.metaHeuristic.getExperimentMinPopulationMap();
		Map<String,Number> mapMaxPop = ExperimentConfiguration.metaHeuristic.getExperimentMaxPopulationMap();
		String typeMinPop = ExperimentConfiguration.INTEGER;
		String typeMaxPop = ExperimentConfiguration.INTEGER;
		
		BorderedCompositeWithDoubleTextWidget bottomBorderedCompositeWithDoubleTextWidget = new BorderedCompositeWithDoubleTextWidget("Second metaheuristic population ranges","Minimum population","Maximum population", 
				optionsMinPop,
				optionsMaxPop,
				mapMinPop,
				mapMaxPop,
				cb, 
				container, 
				getMinPop,
				getMaxPop,
				typeMinPop,
				typeMaxPop,
				test);
		
		widgets.add(bottomBorderedCompositeWithDoubleTextWidget);
		
	}

}
