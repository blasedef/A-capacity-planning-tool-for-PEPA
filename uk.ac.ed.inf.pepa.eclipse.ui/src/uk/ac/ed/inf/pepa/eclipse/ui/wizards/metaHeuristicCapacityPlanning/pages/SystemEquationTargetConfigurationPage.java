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

public class SystemEquationTargetConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public SystemEquationTargetConfigurationPage(String title) {
		
		//copy title upwards
		super(title,"Performance target and System Equation population range","Determine the performance targets and population ranges...");
		
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
		if(!ExperimentConfiguration.networkType.getValue().equals(ExperimentConfiguration.NETWORKSINGLE_S)){
			((MetaHeuristicCapacityPlanningWizard) getWizard()).updateExperimentTargetPage();
		}
		((MetaHeuristicCapacityPlanningWizard) getWizard()).updateSummaryPage();
		setPageComplete(bool);
		
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
		GetValue getFitness = new GetFitnessValue();
		String[] topKeys = new String[] {ExperimentConfiguration.ALPHABETA_S};
		Map<String,Number> topMap = ExperimentConfiguration.metaHeuristic.getFitnessMap();
		
		BorderedCompositeWithTextWidget topBorderedCompositeWithTextWidget = new BorderedCompositeWithTextWidget("System equation target balance:", 
				topKeys, 
				topMap, 
				cb, 
				container, 
				getFitness, 
				null);
		widgets.add(topBorderedCompositeWithTextWidget);
		
		GetValue getTarget = new GetTargetValue();
		GetValue getTargetWeight = new GetTargetWeightValue();
		TestValues test1 = new SumToUnderOneRight();
		String[] optionsTarget = ExperimentConfiguration.oDEConfig.getLabels();
		String[] optionsTargetWeight = ExperimentConfiguration.oDEConfig.getLabels();
		Map<String,Number> mapTarget = ExperimentConfiguration.metaHeuristic.getTargetMap();
		Map<String,Number> mapTargetWeight = ExperimentConfiguration.metaHeuristic.getTargetWeightMap();
		String typeTarget = ExperimentConfiguration.DOUBLE;
		String typeTargetWeight = ExperimentConfiguration.PERCENT;
		
		BorderedCompositeWithDoubleTextWidget topBorderedCompositeWithDoubleTextWidget = new BorderedCompositeWithDoubleTextWidget("Performance target(s):",
				"Performance target:",
				"Performance weight:", 
				optionsTarget,
				optionsTargetWeight,
				mapTarget,
				mapTargetWeight,
				cb, 
				container, 
				getTarget,
				getTargetWeight,
				typeTarget,
				typeTargetWeight,
				test1);
		
		widgets.add(topBorderedCompositeWithDoubleTextWidget);
		
//		GetValue getTarget = new GetTargetValue();
//		String[] middleKeys = ExperimentConfiguration.oDEConfig.getLabels();
//		Map<String,Number> middleMap = ExperimentConfiguration.metaHeuristic.getTargetMap();
//		
//		BorderedCompositeWithTextWidget middleBorderedCompositeWithTextWidget = new BorderedCompositeWithTextWidget("Performance target(s):", 
//				middleKeys, 
//				middleMap, 
//				cb, 
//				container, 
//				getTarget, 
//				ExperimentConfiguration.DOUBLE);
//		widgets.add(middleBorderedCompositeWithTextWidget);
		
		GetValue getMinPop = new GetMinPopValue();
		GetValue getMaxPop = new GetMaxPopValue();
		TestValues test2 = new LeftLessThanOrEqualToRight();
		String[] optionsMinPop = ExperimentConfiguration.pEPAConfig.getSystemEquation(); 
		String[] optionsMaxPop = ExperimentConfiguration.pEPAConfig.getSystemEquation();
		Map<String,Number> mapMinPop = ExperimentConfiguration.metaHeuristic.getMinPopMap();
		Map<String,Number> mapMaxPop = ExperimentConfiguration.metaHeuristic.getMaxPopMap();
		String typeMinPop = ExperimentConfiguration.INTEGER;
		String typeMaxPop = ExperimentConfiguration.INTEGER;
		
		BorderedCompositeWithDoubleTextWidget bottomBorderedCompositeWithDoubleTextWidget = new BorderedCompositeWithDoubleTextWidget("System Equation population ranges:",
				"Minimum population:",
				"Maximum population:", 
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
				test2);
		
		widgets.add(bottomBorderedCompositeWithDoubleTextWidget);
		
		//to update the summaryPage
		completePage();
		
	}	

}
