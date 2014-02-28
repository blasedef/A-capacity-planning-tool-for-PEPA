package uk.ac.ed.inf.pepa.eclipse.ui.view.capacityplanningview;


import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.CapacityListenerManager;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.CapacityPlanningListener;


public class CapacityPlanningView extends ViewPart{
        
	private Label label;
	private CapacityPlanningViewMix listener;
	
	private static class CapacityPlanningViewMix implements CapacityPlanningListener {
		
		private CapacityPlanningView cView;
		
		public CapacityPlanningViewMix(CapacityPlanningView view){
			this.cView = view;
		}
		
		/*
		 * Listen for changes to the underlying PEPA Model
		 */
		public void capacityPlanningJobCompleted() {
			cView.handleCapacityJobFinished();
		}
		
	}
	
	protected void updateView() {
		
		label.getDisplay().syncExec(new Runnable() {

			public void run() {
				label.setText(CapacityListenerManager.results.get(0));
				System.out.println("inside sync");
			}
			
		});
		
	}

	public void handleCapacityJobFinished(){
		this.updateView();
	}
	
	public void setFocus() {
		label.setFocus();
	}
     
	@Override
	public void createPartControl(Composite parent) {
		label = new Label(parent, 0);
        label.setText("Run the capacity planning tool");
        CapacityListenerManager.listener = listener;
		
	}
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
		listener = new CapacityPlanningViewMix(this);
	}

}
