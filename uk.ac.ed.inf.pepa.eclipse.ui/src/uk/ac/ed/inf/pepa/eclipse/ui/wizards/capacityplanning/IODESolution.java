package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;


public interface IODESolution {
	
	/**
	 * set metrics, labels, collectors, and optionplan for the ODE analysis
	 */
	public void updateAnalysisParams();
	
	/**
	 * 
	 */
	public boolean isPageComplete();
}
