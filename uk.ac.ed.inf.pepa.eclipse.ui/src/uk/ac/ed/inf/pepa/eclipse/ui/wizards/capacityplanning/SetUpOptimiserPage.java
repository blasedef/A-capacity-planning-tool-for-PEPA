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

public class SetUpOptimiserPage extends WizardPage {
  //parameters
  private Text minimumPop;
  private Text maximumPop;
  
  public SetUpOptimiserPage() {
    super("Stochastic Search Optimisation");
    setTitle("Stochastic Search Optimisation");
    setDescription("Setting up Search Optimisation");
  }

  @Override
  public void createControl(Composite parent) {
	  
		int textStyle = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		setControl(composite);

		validate();
		
		/* Minimum Agent Population */
	    Label miniPop = new Label(composite, textStyle);
	    miniPop.setText("Minimum population");
		minimumPop = new Text(composite, textStyle);
		minimumPop.setLayoutData(createDefaultGridData());
		minimumPop.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				validate();
			}
		});
		
		/* Maximum Agent Population */
	    Label maxPop = new Label(composite, textStyle);
	    maxPop.setText("Maximum population");
		maximumPop = new Text(composite, textStyle);
		maximumPop.setLayoutData(createDefaultGridData());
		maximumPop.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				validate();
			}
		});
		
  }

  public void validate() {
	this.setErrorMessage(null);
	this.setPageComplete(false);
	boolean minimumPopOk = false;
	boolean maximumPopOk = false;
	
	
	//check minimum population
	try{
		double miniPop = Double.valueOf(minimumPop.getText());
		if(miniPop < 1.0){
			minimumPopOk = false;
		} else {
			minimumPopOk = true;
		}
	} catch (NumberFormatException e) {
		maximumPopOk = false;
	} finally {
		if (!minimumPopOk) {
			setErrorMessage("Value not allowed, Minimum Population must be at least 1");
			return;
		}
	}
	
	//check maximum population
	try{
		double maxiPop = Double.valueOf(maximumPop.getText());
		if(maxiPop < 1.0){
			maximumPopOk = false;
		} else {
			maximumPopOk = true;
		}
	} catch (NumberFormatException e) {
		maximumPopOk = false;
	} finally {
		if (!maximumPopOk) {
			setErrorMessage("Value not allowed, Maximum Population must be at least 1");
			return;
		}
	}
	
	if(minimumPopOk && maximumPopOk)
		this.setPageComplete(true);
  }
  
  public double getMinimumPopulation(){
	  return Double.valueOf(minimumPop.getText());
  }
  
  public double getMaximumPopulation(){
	  return Double.valueOf(maximumPop.getText());
  }
  
  private GridData createDefaultGridData() {
	/* ...with grabbing horizontal space */
	return new GridData(SWT.FILL, SWT.CENTER, true, false);
  }
  
} 