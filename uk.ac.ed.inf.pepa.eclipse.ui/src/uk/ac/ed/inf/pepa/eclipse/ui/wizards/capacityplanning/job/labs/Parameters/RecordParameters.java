package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import java.util.HashMap;

import uk.ac.ed.inf.pepa.eclipse.core.ResourceUtilities;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ConfigurationModel;

public class RecordParameters extends Parameters{
	
	private Path resultsFolder;
	private String primaryMetaheuristicType;
	private String secondaryMetaheuristicType;
	private HashMap<String,Double> minPop;
	private HashMap<String,Double> maxPop;
	private IFile handle;
	private HashMap<String,Double> mhParamsRoot;
	private HashMap<String,Double> mhParamsCan;
	private HashMap<String,Double> pmTargets;
	private HashMap<String,Double> pmWeights;
	private HashMap<String,Double> popWeights;
	private HashMap<String,Double> sysEqWeights;
	private boolean hasSecondary;
	
	public RecordParameters(ConfigurationModel configurationModel){
		this.setMetaheuristicType(configurationModel.dropDownListList.get(1).getValue());
		setHasSecondary(!configurationModel.dropDownListList.get(2).getValue().equals(Config.CHAINSINGLE_S));
			//this.setSecondaryMetaheuristicType(configurationModel.secondDropDownListList.get(0).getValue());
		this.setMinPop(Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getLeftMap()));
		this.setMaxPop(Tool.copyHashMap(configurationModel.systemEquationPopulationRanges.getRightMap()));
		this.handle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				ResourceUtilities.changeExtension(
						configurationModel.configPEPA.getPepaModel().getUnderlyingResource(), ""));
		setFileOutputPath(configurationModel);
		this.setMhParamsRoot(Tool.copyHashMap(configurationModel.metaheuristicParametersRoot.getLeftMap()));
		this.setMhParamsCan(Tool.copyHashMap(configurationModel.metaheuristicParametersCandidateLeaf.getLeftMap()));
		this.setPmTargets(Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getLeftMap()));
		this.setPmWeights(Tool.copyHashMap(configurationModel.performanceTargetsAndWeights.getRightMap()));
		this.setPopWeights(Tool.copyHashMap(configurationModel.populationWeights.getLeftMap()));
		this.setSysEqWeights(Tool.copyHashMap(configurationModel.systemEquationFitnessWeights.getLeftMap()));
	}
	
	public void setFileOutputPath(ConfigurationModel configurationModel){
		
		//regardless of how this is run, everyone saves to the same folder
		setResultsFolder(new Path( ResourcesPlugin.getWorkspace().getRoot().getLocation().addTrailingSeparator().toOSString()
				+ "/run/"
				+ handle.getName()
				+ "_"
				+ configurationModel.dropDownListList.get(1).getValue()
				+ "_"
				+ Tool.getDateTime()));
	}

	public void setResultsFolder(Path resultsFolder) {
		this.resultsFolder = resultsFolder;
	}

	public Path getResultsFolder() {
		return resultsFolder;
	}

	public void setMetaheuristicType(String metaheuristicType) {
		this.primaryMetaheuristicType = metaheuristicType;
	}

	public String getMetaheuristicType() {
		return primaryMetaheuristicType;
	}

	public void setMinPop(HashMap<String,Double> minPop) {
		this.minPop = minPop;
	}

	public HashMap<String,Double> getMinPop() {
		return minPop;
	}

	public void setMaxPop(HashMap<String,Double> maxPop) {
		this.maxPop = maxPop;
	}

	public HashMap<String,Double> getMaxPop() {
		return maxPop;
	}
	
	public IFile getIFile(){
		return this.handle;
	}

	public void setMhParamsRoot(HashMap<String,Double> mhParams) {
		this.mhParamsRoot = mhParams;
	}
	
	public void setMhParamsCan(HashMap<String,Double> mhParams) {
		this.mhParamsCan = mhParams;
	}

	public HashMap<String,Double> getMhParamsRoot() {
		return mhParamsRoot;
	}
	
	public HashMap<String,Double> getMhParamsCan() {
		return mhParamsCan;
	}

	public void setPmTargets(HashMap<String,Double> pmTargets) {
		this.pmTargets = pmTargets;
	}

	public HashMap<String,Double> getPmTargets() {
		return pmTargets;
	}

	public void setPmWeights(HashMap<String,Double> pmWeights) {
		this.pmWeights = pmWeights;
	}

	public HashMap<String,Double> getPmWeights() {
		return pmWeights;
	}

	public void setPopWeights(HashMap<String,Double> popWeights) {
		this.popWeights = popWeights;
	}

	public HashMap<String,Double> getPopWeights() {
		return popWeights;
	}

	public void setSysEqWeights(HashMap<String,Double> sysEqWeights) {
		this.sysEqWeights = sysEqWeights;
	}

	public HashMap<String,Double> getSysEqWeights() {
		return sysEqWeights;
	}

	public void setSecondaryMetaheuristicType(String secondaryMetaheuristicType) {
		this.secondaryMetaheuristicType = secondaryMetaheuristicType;
	}

	public String getSecondaryMetaheuristicType() {
		return secondaryMetaheuristicType;
	}

	public void setHasSecondary(boolean hasSecondary) {
		this.hasSecondary = hasSecondary;
	}

	public boolean isHasSecondary() {
		return hasSecondary;
	}

}
