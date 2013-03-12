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

	public MetaHeuristicJob() throws InvocationTargetException, InterruptedException {
		super("MetaHeuristic");
		this.population = new MetaHeuristicPopulation();
		//trying to create the file last :/
		this.output = CPAParameters.getNewFilePage().createNewFile();
		
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		
		monitor.beginTask("MetaHeuristic", CPAParameters.metaheuristicParameters.get("Generations:").intValue());
		
		CPAParameters.source += "Generation,";
		
		for(int i = 0; i < CPAParameters.originalSystemEquation.size(); i++){
			CPAParameters.source += "Agent,Population,";
		}
		
		CPAParameters.source += "total fitness,performance fitness,population fitness,";
		
		for(int i = 0; i < CPAParameters.targetLabels.length; i++){
			CPAParameters.source += "target,actual performance,";
		}
		
		CPAParameters.source += "\n";
		
		/**
		 * take original snap shot...
		 */
		CPAParameters.makeOriginal(monitor);
		
		//initialisation
		this.population.initialise(monitor);
		
		byte currentBytes[] = CPAParameters.source.getBytes();
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
