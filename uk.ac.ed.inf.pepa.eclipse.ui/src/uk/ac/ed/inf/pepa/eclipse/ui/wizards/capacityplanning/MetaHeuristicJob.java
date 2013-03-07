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

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.eclipse.ui.largescale.CapacityPlanningAnalysisParameters;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class MetaHeuristicJob extends Job {
	
	private int minimumPopulation, maximumPopulation;
	private IFile output;
	private MetaHeuristicPopulation population;
	private AnalysisOfFluidSteadyState analyseThis;
	private IParametricDerivationGraph fGraph;

	public MetaHeuristicJob(IFile file) throws InvocationTargetException, InterruptedException {
		super("MetaHeuristic");
		this.fGraph = CapacityPlanningAnalysisParameters.getInitialFGraph();
		this.minimumPopulation = CapacityPlanningAnalysisParameters.minimumComponentPopulation;
		this.maximumPopulation = CapacityPlanningAnalysisParameters.maximumComponentPopulation;
		this.population = new MetaHeuristicPopulation(CapacityPlanningAnalysisParameters.model);
		this.output = file;
		this.analyseThis = new AnalysisOfFluidSteadyState();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		String source = this.population.getSystemEquationAsString();
		double[] test;
		test = this.analyseThis.getResults(CapacityPlanningAnalysisParameters.getInitialFGraph(), monitor);
		
		for(int i = 0; i < test.length; i++){
			source += test[i] + "\n";
		}
		
		this.population.changeAValueTest();
		
		source += this.population.getSystemEquationAsString();
		
		test = this.analyseThis.getResults(CapacityPlanningAnalysisParameters.getFGraph(CapacityPlanningAnalysisParameters.model), monitor);
		
		for(int i = 0; i < test.length; i++){
			source += test[i] + "\n";
		}
		
		this.population.putValuesBackTest();
		
		source += this.population.getSystemEquationAsString();
		
		test = this.analyseThis.getResults(CapacityPlanningAnalysisParameters.getFGraph(CapacityPlanningAnalysisParameters.model), monitor);
		
		for(int i = 0; i < test.length; i++){
			source += test[i] + "\n";
		}
		
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
