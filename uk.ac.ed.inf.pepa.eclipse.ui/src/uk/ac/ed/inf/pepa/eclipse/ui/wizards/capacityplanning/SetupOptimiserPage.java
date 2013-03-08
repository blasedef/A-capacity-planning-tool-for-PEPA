package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Need to sort out this thing!! Needs to be more like TargetSetupPage and use arrays to make the options...
 * @author twig
 *
 */
public class SetupOptimiserPage extends WizardPage {
  //parameters
  private Text minimumPop;
  private Text maximumPop;
  private Text mProbv;
  
  public SetupOptimiserPage() {
    super("Stochastic Search Optimisation");
    setTitle("Stochastic Search Optimisation");
    setDescription("Setting up Search Optimisation");
  }

  @Override
  public void createControl(Composite parent) {
	  
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		

		validate();
		
		/* Minimum Agent Population */
	    Label miniPop = new Label(composite, SWT.NONE);
	    miniPop.setText("Minimum population");
		minimumPop = new Text(composite, textStyle);
		minimumPop.setLayoutData(createDefaultGridData());
		minimumPop.setText(Integer.toString(CapacityPlanningAnalysisParameters.minimumComponentPopulation));
		validate();
		minimumPop.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				validate();
			}
		});
		
		/* Maximum Agent Population */
	    Label maxPop = new Label(composite, SWT.NONE);
	    maxPop.setText("Maximum population");
		maximumPop = new Text(composite, textStyle);
		maximumPop.setLayoutData(createDefaultGridData());
		maximumPop.setText(Integer.toString(CapacityPlanningAnalysisParameters.minimumComponentPopulation));
		validate();
		maximumPop.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				validate();
			}
		});
		setControl(composite);
		
		/* Mutation Probability rate */
	    Label mProb = new Label(composite, SWT.NONE);
	    mProb.setText("Mutation probability");
		mProbv = new Text(composite, textStyle);
		mProbv.setLayoutData(createDefaultGridData());
		mProbv.setText(Double.toString(CapacityPlanningAnalysisParameters.mutationProbabilty));
		validate();
		mProbv.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				validate();
			}
		});
		setControl(composite);
		
  }

  public void validate() {
	this.setErrorMessage(null);
	this.setPageComplete(false);
	boolean minimumPopOk = false;
	boolean maximumPopOk = false;
	boolean mutateProbOk = false;
	
	//check minimum population
	try{
		double miniPop = Integer.valueOf(minimumPop.getText());
		if(miniPop < 1.0){
			minimumPopOk = false;
		} else {
			minimumPopOk = true;
		}
	} catch (NumberFormatException e) {
		maximumPopOk = false;
	} finally {
		if (!minimumPopOk) {
			setErrorMessage("Value not allowed, Minimum Population must be at least 1 and an Integer");
			return;
		}
	}
	
	//check maximum population
	try{
		double maxiPop = Integer.valueOf(maximumPop.getText());
		if(maxiPop < 1.0){
			maximumPopOk = false;
		} else {
			maximumPopOk = true;
		}
	} catch (NumberFormatException e) {
		maximumPopOk = false;
	} finally {
		if (!maximumPopOk) {
			setErrorMessage("Value not allowed, Maximum Population must be at least 1 and an Intege");
			return;
		}
	}
	
	//check mProb
	try{
		double mProbvd = Double.valueOf(mProbv.getText());
		if(mProbvd < 1.0 && mProbvd > 0.0){
			mutateProbOk = true;
		} else {
			mutateProbOk = false;
		}
	} catch (NumberFormatException e) {
		mutateProbOk = false;
	} finally {
		if (!mutateProbOk) {
			setErrorMessage("Mutation Probability must be between 0.0 and 1.0");
			return;
		}
	}
	
	if(minimumPopOk && maximumPopOk && mutateProbOk)
		this.setPageComplete(true);
	CapacityPlanningAnalysisParameters.minimumComponentPopulation = getMinimumPopulation();
	CapacityPlanningAnalysisParameters.maximumComponentPopulation = getMaximumPopulation();
	CapacityPlanningAnalysisParameters.mutationProbabilty = getMutationProbability();
  }
  
  public double getMutationProbability(){
	  return Double.valueOf(mProbv.getText());
  }
  
  public int getMinimumPopulation(){
	  return Integer.valueOf(minimumPop.getText());
  }
  
  public int getMaximumPopulation(){
	  return Integer.valueOf(maximumPop.getText());
  }
  
  private GridData createDefaultGridData() {
	/* ...with grabbing horizontal space */
	return new GridData(SWT.FILL, SWT.CENTER, true, false);
  }
  
} 