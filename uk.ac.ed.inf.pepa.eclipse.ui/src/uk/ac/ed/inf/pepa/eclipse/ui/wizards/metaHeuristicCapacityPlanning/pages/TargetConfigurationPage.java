package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.MetaHeuristicCapacityPlanningWizard;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningConfigurationTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class TargetConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	protected ArrayList<CapacityPlanningWidget> minWidgets = new ArrayList<CapacityPlanningWidget>();
	protected ArrayList<CapacityPlanningWidget> maxWidgets = new ArrayList<CapacityPlanningWidget>();
	
	public TargetConfigurationPage(String title) {
		
		//copy title upwards
		super(title,"Performance target and System Equation population range","Determine the performance targets and population ranges...");
		
		ExperimentConfiguration.metaHeuristicPrimary.updateTargetValues();
		ExperimentConfiguration.metaHeuristicPrimary.updateMinPopulationRanges();
		ExperimentConfiguration.metaHeuristicPrimary.updateMaxPopulationRanges();
		
	}

	@Override
	public void completePage() {
		boolean bool = true;
		for (CapacityPlanningWidget w : widgets){
			bool = bool & w.isValid();
		}
		for (CapacityPlanningWidget w : minWidgets){
			bool = bool & w.isValid();
		}
		for (CapacityPlanningWidget w : maxWidgets){
			bool = bool & w.isValid();
		}
		if(bool){
			for(int i = 0; i < minWidgets.size();i++){
				bool = bool & (((CapacityPlanningConfigurationTextWidget) minWidgets.get(i)).getValue().doubleValue() <= ((CapacityPlanningConfigurationTextWidget) maxWidgets.get(i)).getValue().doubleValue());
			}
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
		
		//Create sub composite for the fitness attributes
		Composite compositeFit = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataFit = new GridData(GridData.FILL_HORIZONTAL);
		gridDataFit.horizontalSpan = 2;
		compositeFit.setLayoutData(gridDataFit);
		GridLayout layoutFit = new GridLayout();
		layoutFit.numColumns = 2;
		compositeFit.setLayout(layoutFit);
		
		String[] options = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMapKeys();
		Map<String,Number> map = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMap();
		String type; 
		
		for(String option : options){
			type = ExperimentConfiguration.defaultOptionTypeMap.get(option);
			widgets.add(new CapacityPlanningConfigurationTextWidget(compositeFit,option,ExperimentConfiguration.metaHeuristicPrimary.getFitnessMapValue(option),map,type,cb));
		}
		
		//Create sub composite for the performance targets
		Composite compositeTar = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataTar = new GridData(GridData.FILL_HORIZONTAL);
		gridDataTar.horizontalSpan = 2;
		compositeTar.setLayoutData(gridDataTar);
		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 2;
		compositeTar.setLayout(layout1);
		
		//Heading 
		Label heading1 = new Label(compositeTar,SWT.NONE | SWT.BOLD);
		GridData gridDataHeading1 = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading1.horizontalSpan = 2;
		heading1.setLayoutData(gridDataHeading1);
		heading1.setText("Performance target(s):");
		
		//Details for getting target list
		String[] optionsTarget = ExperimentConfiguration.oDEConfig.getLabels();
		Map<String,Number> mapTarget = ExperimentConfiguration.metaHeuristicPrimary.getTargetMap();
		String typeTarget; 
		
		for(String option : optionsTarget){
			typeTarget = ExperimentConfiguration.DOUBLE;
			widgets.add(new CapacityPlanningConfigurationTextWidget(compositeTar,option,ExperimentConfiguration.metaHeuristicPrimary.getTargetMapValue(option),mapTarget,typeTarget,cb));
		}
		
		//Now create sub composite for population ranges
		Composite compositePop = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataPop = new GridData(GridData.FILL_HORIZONTAL);
		gridDataPop.horizontalSpan = 2;
		compositePop.setLayoutData(gridDataPop);
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		compositePop.setLayout(layout2);
		
		Label heading2 = new Label(compositePop,SWT.NONE | SWT.BOLD);
		GridData gridDataHeading2 = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading2.horizontalSpan = 2;
		heading2.setLayoutData(gridDataHeading2);
		heading2.setText("System Equation population ranges:");
		
		//Sub sub composite for left hand side
		Composite compositeMin = new Composite(compositePop, SWT.NONE);
		GridData gridDataMin = new GridData(GridData.FILL_HORIZONTAL);
		compositeMin.setLayoutData(gridDataMin);
		GridLayout layoutMin = new GridLayout();
		layoutMin.numColumns = 2;
		compositeMin.setLayout(layoutMin);
		
		Label headingMin = new Label(compositeMin,SWT.NONE);
		GridData gridDataHeadingMin = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingMin.horizontalSpan = 2;
		headingMin.setLayoutData(gridDataHeadingMin);
		headingMin.setText("Min population:");
		
		String[] optionsMinPop = ExperimentConfiguration.pEPAConfig.getSystemEquation();
		Map<String,Number> mapMinPop = ExperimentConfiguration.metaHeuristicPrimary.getMinPopMap();
		String typeMinPop; 
		
		for(String option : optionsMinPop){
			typeMinPop = ExperimentConfiguration.INTEGER;
			minWidgets.add(new CapacityPlanningConfigurationTextWidget(compositeMin,option,ExperimentConfiguration.metaHeuristicPrimary.getMinPopMapValue(option),mapMinPop,typeMinPop,cb));
		}
		
		//Sub sub composite for right hand side
		Composite compositeMax = new Composite(compositePop, SWT.NONE);
		GridData gridDataMax = new GridData(GridData.FILL_HORIZONTAL);
		compositeMax.setLayoutData(gridDataMax);
		GridLayout layoutMax = new GridLayout();
		layoutMax.numColumns = 2;
		compositeMax.setLayout(layoutMax);
		
		Label headingMax = new Label(compositeMax,SWT.NONE);
		GridData gridDataHeadingMax = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingMax.horizontalSpan = 2;
		headingMax.setLayoutData(gridDataHeadingMax);
		headingMax.setText("Max population:");
		
		String[] optionsMaxPop = ExperimentConfiguration.pEPAConfig.getSystemEquation();
		Map<String,Number> mapMaxPop = ExperimentConfiguration.metaHeuristicPrimary.getMaxPopMap();
		String typeMaxPop; 
		
		for(String option : optionsMaxPop){
			typeMaxPop = ExperimentConfiguration.INTEGER;
			maxWidgets.add(new CapacityPlanningConfigurationTextWidget(compositeMax,option,ExperimentConfiguration.metaHeuristicPrimary.getMaxPopMapValue(option),mapMaxPop,typeMaxPop,cb));
		}
		
		if(!ExperimentConfiguration.metaHeuristicNetworkType.getValue().equals(ExperimentConfiguration.METAHEURISTICSINGLE_S)){
			notSingle(container,cb);
		}
		
		//to update the summaryPage
		completePage();
		
	}
	
	public void notSingle(Composite container, IValidationCallback cb){
		
		Composite compositePop = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataPop = new GridData(GridData.FILL_HORIZONTAL);
		gridDataPop.horizontalSpan = 2;
		compositePop.setLayoutData(gridDataPop);
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		compositePop.setLayout(layout2);
		
		Label heading3 = new Label(compositePop,SWT.NONE | SWT.BOLD);
		GridData gridDataHeading3 = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading3.horizontalSpan = 2;
		heading3.setLayoutData(gridDataHeading3);
		heading3.setText("Secondary metaheuristic ranges:");
		
		//Sub sub composite for left hand side
		Composite compositeMin = new Composite(compositePop, SWT.NONE);
		GridData gridDataMin = new GridData(GridData.FILL_HORIZONTAL);
		compositeMin.setLayoutData(gridDataMin);
		GridLayout layoutMin = new GridLayout();
		layoutMin.numColumns = 2;
		compositeMin.setLayout(layoutMin);
		
		Label headingMin = new Label(compositeMin,SWT.NONE);
		GridData gridDataHeadingMin = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingMin.horizontalSpan = 2;
		headingMin.setLayoutData(gridDataHeadingMin);
		headingMin.setText("Min value:");
		
		String[] optionsFMinPop = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMinPopulationOptions();
		Map<String,Number> mapFMinPop = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMinPopulationMap();
		String typeFMinPop; 
		
		for(String option : optionsFMinPop){
			if(!option.equals(ExperimentConfiguration.EXPERIMENTS_S)){
				typeFMinPop = ExperimentConfiguration.defaultOptionTypeMap.get(option);
				minWidgets.add(new CapacityPlanningConfigurationTextWidget(compositeMin,option,ExperimentConfiguration.metaHeuristicPrimary.getFitnessMinPopMapValue(option),mapFMinPop,typeFMinPop,cb));
			}
		}
		
		//Sub sub composite for right hand side
		Composite compositeMax = new Composite(compositePop, SWT.NONE);
		GridData gridDataMax = new GridData(GridData.FILL_HORIZONTAL);
		compositeMax.setLayoutData(gridDataMax);
		GridLayout layoutMax = new GridLayout();
		layoutMax.numColumns = 2;
		compositeMax.setLayout(layoutMax);
		
		Label headingMax = new Label(compositeMax,SWT.NONE);
		GridData gridDataHeadingMax = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeadingMax.horizontalSpan = 2;
		headingMax.setLayoutData(gridDataHeadingMax);
		headingMax.setText("Max value:");
		
		String[] optionsFMaxPop = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMaxPopulationOptions();
		Map<String,Number> mapFMaxPop = ExperimentConfiguration.metaHeuristicPrimary.getFitnessMaxPopulationMap();
		String typeFMaxPop; 
		
		for(String option : optionsFMaxPop){
			if(!option.equals(ExperimentConfiguration.EXPERIMENTS_S)){
				typeFMaxPop = ExperimentConfiguration.defaultOptionTypeMap.get(option);
				maxWidgets.add(new CapacityPlanningConfigurationTextWidget(compositeMax,option,ExperimentConfiguration.metaHeuristicPrimary.getFitnessMaxPopMapValue(option),mapFMaxPop,typeFMaxPop,cb));
			}
		}
		
	}
	

}
