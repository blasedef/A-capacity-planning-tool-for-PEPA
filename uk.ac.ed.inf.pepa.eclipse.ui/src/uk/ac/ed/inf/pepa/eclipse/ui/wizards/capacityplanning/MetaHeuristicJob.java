package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class MetaHeuristicJob extends Job {
	
	private IFile output;
	private MetaHeuristicPopulation population;

	public MetaHeuristicJob(WizardNewFileCreationPage newFilePage) throws InvocationTargetException, InterruptedException {
		super("MetaHeuristic");
		this.population = new MetaHeuristicPopulation();
		//trying to create the file last :/
		this.output = newFilePage.createNewFile();
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		
		monitor.beginTask("MetaHeuristic", CapacityPlanningAnalysisParameters.metaheuristicParameters.get("Generations:").intValue());
		
		/**
		 * take original snap shot...
		 */
		CapacityPlanningAnalysisParameters.makeOriginal(monitor);
		
		//initialisation
		this.population.initialise(monitor);
		
		
		byte currentBytes[] = CapacityPlanningAnalysisParameters.source.getBytes();
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				currentBytes);
		try {
			output.setContents(byteArrayInputStream, true, false,
					monitor);
		} catch (CoreException e) {
			try {
				throw new InvocationTargetException(e);
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		monitor.done();

		return Status.OK_STATUS;

	}
}
