package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.MetaHeuristicType;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.Models;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningConfigurationTextWidget;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets.CapacityPlanningWidget;

public class TargetConfigurationPage extends MetaHeuristicCapacityPlanningWizardPage {
	
	protected ArrayList<CapacityPlanningWidget> widgets = new ArrayList<CapacityPlanningWidget>();
	
	public TargetConfigurationPage(String title) {
		
		//copy title upwards
		super(title,"Performance target and Population range configuration","Determine the performance targets and population ranges...");
		
		Models.metaHeuristicType.updateTargetValues();
		Models.metaHeuristicType.updateMinPopulationRanges();
		Models.metaHeuristicType.updateMaxPopulationRanges();
		
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
		setPageComplete(bool);
	}

	@Override
	protected void constructPage(Composite container, IValidationCallback cb) {
		
	
		Composite compositeTar = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataTar = new GridData(GridData.FILL_HORIZONTAL);
		gridDataTar.horizontalSpan = 2;
		compositeTar.setLayoutData(gridDataTar);
		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 2;
		compositeTar.setLayout(layout1);
		
		Label heading1 = new Label(compositeTar,SWT.NONE);
		GridData gridDataHeading1 = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading1.horizontalSpan = 2;
		heading1.setLayoutData(gridDataHeading1);
		heading1.setText("MetaHeuristic performance target(s):");
		
		String[] optionsTarget = Models.oDEConfig.getLabels();
		Map<String,Number> mapTarget = MetaHeuristicType.targets.getMap();
		String typeTarget; 
		
		for(String option : optionsTarget){
			typeTarget = MetaHeuristicType.DOUBLE;
			widgets.add(new CapacityPlanningConfigurationTextWidget(compositeTar,option,MetaHeuristicType.targets.getTargetMapValue(option),mapTarget,typeTarget,cb));
		}
		
		Composite compositePop = new Composite(container,SWT.NONE | SWT.BORDER);
		GridData gridDataPop = new GridData(GridData.FILL_HORIZONTAL);
		gridDataPop.horizontalSpan = 2;
		compositePop.setLayoutData(gridDataPop);
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		compositePop.setLayout(layout2);
		
		Label heading2 = new Label(compositePop,SWT.NONE);
		GridData gridDataHeading2 = new GridData(GridData.FILL_HORIZONTAL);
		gridDataHeading2.horizontalSpan = 2;
		heading2.setLayoutData(gridDataHeading2);
		heading2.setText("System Equation population ranges:");
		
		Composite compositeMin = new Composite(compositePop, SWT.NONE);
		GridData gridDataMin = new GridData(GridData.FILL_HORIZONTAL);
		compositeMin.setLayoutData(gridDataMin);
		GridLayout layoutMin = new GridLayout();
		layoutMin.numColumns = 2;
		compositeMin.setLayout(layoutMin);
		
		String[] optionsMinPop = Models.oDEConfig.getSystemEquation();
		Map<String,Number> mapMinPop = MetaHeuristicType.minPopulationRanges.getMap();
		String typeMinPop; 
		
		for(String option : optionsMinPop){
			typeMinPop = MetaHeuristicType.INTEGER;
			widgets.add(new CapacityPlanningConfigurationTextWidget(compositeMin,"Min for " + option,MetaHeuristicType.minPopulationRanges.getTargetMapValue(option),mapMinPop,typeMinPop,cb));
		}
		
		Composite compositeMax = new Composite(compositePop, SWT.NONE);
		GridData gridDataMax = new GridData(GridData.FILL_HORIZONTAL);
		compositeMax.setLayoutData(gridDataMax);
		GridLayout layoutMax = new GridLayout();
		layoutMax.numColumns = 2;
		compositeMax.setLayout(layoutMax);
		
		String[] optionsMaxPop = Models.oDEConfig.getSystemEquation();
		Map<String,Number> mapMaxPop = MetaHeuristicType.minPopulationRanges.getMap();
		String typeMaxPop; 
		
		for(String option : optionsMaxPop){
			typeMaxPop = MetaHeuristicType.INTEGER;
			widgets.add(new CapacityPlanningConfigurationTextWidget(compositeMax,"Max for " + option,MetaHeuristicType.maxPopulationRanges.getTargetMapValue(option),mapMaxPop,typeMaxPop,cb));
		}
		
		
		
	}
	

}
