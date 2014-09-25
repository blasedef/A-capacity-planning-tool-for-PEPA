package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.pages;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;

public class SaveAsCapacityPlanningWizardPage extends WizardNewFileCreationPage {
	
	protected String extension;

	public SaveAsCapacityPlanningWizardPage(StructuredSelection structuredSelection, 
			String extension) {
		
		super("", structuredSelection);
		
		if (extension == null) {
			throw new NullPointerException("Extension cannot be null");
		}
		this.extension = extension;
		
	}
	
	public String getExtension() {
		return extension;
	}

	public void completePage() {
		
		boolean complete = false;
		
		if (!super.validatePage())
			complete = false;
		
		/* Check extension */
		Path path = new Path(getFileName());
		CPTAPI.setFileName(path.toOSString());
		if (path.getFileExtension() == null || path.getFileExtension().compareToIgnoreCase(extension) != 0) {
			this.setErrorMessage("Wrong extension. It must be a ."
					+ extension + " file");
			complete = false;
		} else {
			this.setMessage(null);
			complete = true;
		}
		setPageComplete(complete);
	}

}
