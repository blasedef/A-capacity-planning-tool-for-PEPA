package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class MetaHeuristicJob extends Job {
	
	private IFile output;
	private MetaHeuristicPopulation population;

	public MetaHeuristicJob(IFile file) throws InvocationTargetException, InterruptedException {
		super("MetaHeuristic");
		this.population = new MetaHeuristicPopulation();
		this.output = file;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		String source = "start here... \n";
		
		//initialisation
		//best - test fitness
		//mutate
		//selection/crossover
		//etc etc...
		
		this.population.initialise(monitor);
		this.population.initialise(monitor);
		this.population.initialise(monitor);
		
		
		this.population.setAModel(0,"Farm",6.0);
		this.population.setAModel(1,"Farm",6.0);
		this.population.setAModel(0,"Farm",1.0);
		
		source += this.population.giveMeAModelsName(0) + "\n";
		source += this.population.giveMeAModelsName(1) + "\n";
		
		//here to reset the AST to the original values
		this.population.initialise(monitor);
		this.population.reset();
		
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
