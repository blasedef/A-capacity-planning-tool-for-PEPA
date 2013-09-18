/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;


/**
 * 
 * MPP2 Minf project, A capacity planning tool for PEPA Eclipse
 * 
 * @author Christoper Williams
 * @version 1.1 (in terms of this wizard
 * 
 *
 */
public class MetaHeuristicCapacityPlanningWizard extends Wizard {

	//The underlying PEPAModel
	IPepaModel model;
	
	//Wizard page iterator
	List<WizardPage> wizardPageList = new ArrayList<WizardPage>();
	
	//Wizard pages
	PerformanceValueTypeAndMetaHeuristicSelectionPage performanceValueTypeAndMetaHeuristicSelectionPage;
	
	
	public MetaHeuristicCapacityPlanningWizard(IPepaModel model){
		this.model = model;
		
		//wizard pages
		this.performanceValueTypeAndMetaHeuristicSelectionPage = new PerformanceValueTypeAndMetaHeuristicSelectionPage();
		wizardPageList.add(this.performanceValueTypeAndMetaHeuristicSelectionPage);
		
	}
	
	@Override
	public void addPages() {
		for (WizardPage wizardPage : this.wizardPageList){
			addPage(wizardPage);
		}
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	

} 
