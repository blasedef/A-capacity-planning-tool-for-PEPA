package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.WizardPage;

import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class MetaHeuristicJob extends Job {
	
	private ModelNode originalModel;
	private Double minimumPopulation, maximumPopulation;
	private IFile output;
	private MetaHeuristicPopulation population;

	public MetaHeuristicJob(ModelNode model, SetupOptimiserPage parameters, IODESolution ODESolution, IFile file) throws InvocationTargetException, InterruptedException {
		super("MetaHeuristic");
		this.originalModel = model;
		this.minimumPopulation = parameters.getMinimumPopulation();
		this.maximumPopulation = parameters.getMaximumPopulation();
		this.population = new MetaHeuristicPopulation(this.originalModel);
		this.output = file;
		Job thisJob = ODESolution.getAnalysisJob();
		thisJob.setUser(true);
		thisJob.schedule();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		String source = this.minimumPopulation + " , " + this.maximumPopulation + " \n " + this.population.getSystemEquationAsString();
		byte currentBytes[] = source.getBytes();
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
