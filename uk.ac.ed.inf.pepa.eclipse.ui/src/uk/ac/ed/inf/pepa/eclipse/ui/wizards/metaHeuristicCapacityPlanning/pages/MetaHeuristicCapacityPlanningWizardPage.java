package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages;

import org.eclipse.draw2d.GridData;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model.ExperimentConfiguration;

public abstract class MetaHeuristicCapacityPlanningWizardPage extends WizardPage {
	
	protected Composite container;
	
	protected final IValidationCallback parentCallBack = new IValidationCallback() {

		public void validate() {
			completePage();
		}
	};
	
	public interface Command {
		
		public String getMapValue(String key);
		
	}
	
	public abstract class GetValue implements Command {

		public abstract String getMapValue(String key);
		
	}
	
	public class GetTargetValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getTargetMapValue(key);
		}

	}
	
	public class GetTargetWeightValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getTargetWeightMapValue(key);
		}

	}
	
	public class GetFitnessValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getFitnessMapValue(key);
		}

	}
	
	public class GetMinPopValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getMinPopMapValue(key);
		}
	}
	
	public class GetMaxPopValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getMaxPopMapValue(key);
		}
	}
	
	public class GetEMinPopValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getSecondExperimentMinPopMapValue(key);
		}
	}
	
	public class GetEMaxPopValue extends GetValue implements Command {
		
		public String getMapValue(String key){
			return ExperimentConfiguration.metaHeuristic.getSecondExperimentMaxPopMapValue(key);
		}
	}
	
	public interface Validation {
		
		public boolean test(Double[] x, Double[] y);
		
	}
	
	public abstract class TestValues implements Validation {

		public abstract boolean test(Double[] x, Double[] y);
		
	}
	
	public class LeftLessThanOrEqualToRight extends TestValues implements Validation {

		@Override
		public boolean test(Double[] x, Double[] y) {
			boolean test = true;
			for(int i = 0; i < x.length; i++){
				test = test & (x[i] <= y[i]);
			}
			return test;
		}

	}
	
	public class SumToUnderOne extends TestValues implements Validation {

		@Override
		public boolean test(Double[] x, Double[] y) {
			Double sumX = 0.0;
			Double sumY = 0.0;
			boolean test = true;
			
			for(int i = 0; i < x.length; i++){
				sumX += x[i];
				if(y != null){
					sumY += y[i];
				}
			}
			
			if(y != null){
				test = test & (sumY <= 1.0);
			}
			
			test = test & (sumX <= 1.0);
			
			return test;
		}

	}
	
	public class SumToUnderOneRight extends TestValues implements Validation {

		@Override
		public boolean test(Double[] x, Double[] y) {
			Double sumY = 0.0;
			boolean test = true;
			
			for(int i = 0; i < y.length; i++){
				sumY += y[i];
			}
			
			test = test & (sumY <= 1.0);
			return test;
		}

	}

	protected MetaHeuristicCapacityPlanningWizardPage(String s, String title, String description) {
		
		//copy title upwards
		super(s);
		
		//set page title
	    setTitle(title);
	    
	    //set description underneath
	    setDescription(description);
	    
	}

	@Override
	public void createControl(Composite parent) {
		//minimum required for a wizard page 1/2
		this.container = new Composite(parent, SWT.NONE);
		this.container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 10;
		layout.numColumns = 2;
		container.setLayout(layout);
		
		constructPage(container, this.parentCallBack);
		
		//minimum required for a wizard page 2/2
		setControl(container);
		
	}
	
	protected abstract void constructPage(Composite container, IValidationCallback cb);
	
	public abstract void completePage();
	
}
