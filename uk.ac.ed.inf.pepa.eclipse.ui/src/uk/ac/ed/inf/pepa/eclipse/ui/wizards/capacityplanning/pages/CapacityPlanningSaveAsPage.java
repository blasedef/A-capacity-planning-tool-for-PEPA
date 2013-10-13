package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.pages;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class CapacityPlanningSaveAsPage extends WizardNewFileCreationPage {

	protected String extension;

	public CapacityPlanningSaveAsPage(String pageName, 
			IStructuredSelection selection, 
			String extension) {

		super(pageName, selection);
		if (extension == null) {
			throw new NullPointerException("Extension cannot be null");
		}
		this.extension = extension;

	}

	public String getExtension() {
		return extension;
	}

	protected boolean validatePage() {
		if (!super.validatePage())
			return false;
		/* Check extension */
		Path path = new Path(getFileName());
		if (path.getFileExtension() == null || path.getFileExtension().compareToIgnoreCase(
				extension) != 0) {
			this.setErrorMessage("Wrong extension. It must be a ."
					+ extension + " file");
			return false;
		} else {
			this.setMessage(null);
			return true;
		}
	}

}
