package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.parsing.ASTVisitor;
import uk.ac.ed.inf.pepa.parsing.ActionTypeNode;
import uk.ac.ed.inf.pepa.parsing.ActivityNode;
import uk.ac.ed.inf.pepa.parsing.AggregationNode;
import uk.ac.ed.inf.pepa.parsing.BinaryOperatorRateNode;
import uk.ac.ed.inf.pepa.parsing.ChoiceNode;
import uk.ac.ed.inf.pepa.parsing.ConstantProcessNode;
import uk.ac.ed.inf.pepa.parsing.CooperationNode;
import uk.ac.ed.inf.pepa.parsing.HidingNode;
import uk.ac.ed.inf.pepa.parsing.ModelNode;
import uk.ac.ed.inf.pepa.parsing.PassiveRateNode;
import uk.ac.ed.inf.pepa.parsing.PrefixNode;
import uk.ac.ed.inf.pepa.parsing.ProcessDefinitionNode;
import uk.ac.ed.inf.pepa.parsing.ProcessNode;
import uk.ac.ed.inf.pepa.parsing.RateDefinitionNode;
import uk.ac.ed.inf.pepa.parsing.RateDoubleNode;
import uk.ac.ed.inf.pepa.parsing.UnknownActionTypeNode;
import uk.ac.ed.inf.pepa.parsing.VariableRateNode;
import uk.ac.ed.inf.pepa.parsing.WildcardCooperationNode;


public class MetaHeuristicPopulation {
	
	private ArrayList<ModelObject> mPopulation;

	public MetaHeuristicPopulation() {
		this.mPopulation = new ArrayList<ModelObject>();
	}
	
	public void initialise(IProgressMonitor monitor){
		ModelObject temp = new ModelObject(monitor);
		temp.copyDictionary(CapacityPlanningAnalysisParameters.originalSystemEquationDict);
		this.mPopulation.add(temp);
	}

	public String giveMeAModelsName(int i) {
		return this.mPopulation.get(i).toString();
	}

	public void setAModel(int i, String x, Double y) {
		this.mPopulation.get(i).setAnItem(x, y);		
	}

	/**
	 * put the core AST back to how it was originally
	 * or the values are off...
	 */
	public void reset() {
		this.mPopulation.get(0).reset();
	}
	
}
