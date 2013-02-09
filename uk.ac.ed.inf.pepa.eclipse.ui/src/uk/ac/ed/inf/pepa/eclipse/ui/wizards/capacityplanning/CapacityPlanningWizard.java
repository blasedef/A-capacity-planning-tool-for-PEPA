/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;
import org.eclipse.jface.wizard.Wizard;
import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;


public class CapacityPlanningWizard extends Wizard {

  protected SetUpOptimiserPage setUpOptimiserPage;
  protected ListOfConfigurationsPage listOfConfigurationsPage;
  private IPepaModel model;

  public CapacityPlanningWizard(IPepaModel model) {
    super();
    this.model = model;
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    setUpOptimiserPage = new SetUpOptimiserPage();
    listOfConfigurationsPage = new ListOfConfigurationsPage();
    addPage(setUpOptimiserPage);
    addPage(listOfConfigurationsPage);
  }

  @Override
  public boolean performFinish() {
    // Print the result to the console
    System.out.println(setUpOptimiserPage.getText1());
    System.out.println(listOfConfigurationsPage.getText1());

    return true;
  }
} 
