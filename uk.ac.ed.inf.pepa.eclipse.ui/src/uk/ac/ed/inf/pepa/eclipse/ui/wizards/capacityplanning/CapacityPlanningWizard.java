/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import uk.ac.ed.inf.common.ui.wizards.SaveAsPage;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;


public class CapacityPlanningWizard extends Wizard {
	
	/** Extension for CSV files */
	private static final String EXTENSION = "csv";
	private WizardNewFileCreationPage newFilePage;
	
	protected SetUpOptimiserPage setUpOptimiserPage;
	private IPepaModel model;
	
	public CapacityPlanningWizard(IPepaModel model) {
		super();
	    this.model = model;
	    setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		addSaveAsPage();
		setUpOptimiserPage = new SetUpOptimiserPage();
		addPage(setUpOptimiserPage);
	}
  
	private void addSaveAsPage() {
		IFile handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						model.getUnderlyingResource(), EXTENSION));
	
		newFilePage = new SaveAsPage("newFilePage", new StructuredSelection(
				handle), EXTENSION);
		newFilePage.setTitle("Save to CSV");
		newFilePage.setDescription("Save model configurations to");
		newFilePage.setFileName(handle.getName());
		this.addPage(newFilePage);
	}

	@Override
	public boolean performFinish() {
		Job myJob;
		try {
			myJob = new MetaHeuristicJob(this.model.getAST(), this.setUpOptimiserPage, this.newFilePage.createNewFile());
			myJob.schedule();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
} 
