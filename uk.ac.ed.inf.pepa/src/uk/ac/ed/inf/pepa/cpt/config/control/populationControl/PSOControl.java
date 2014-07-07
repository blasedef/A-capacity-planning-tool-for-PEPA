package uk.ac.ed.inf.pepa.cpt.config.control.populationControl;

import uk.ac.ed.inf.pepa.cpt.config.control.PopulationControl;
import uk.ac.ed.inf.pepa.cpt.config.lists.PSOList;

public class PSOControl extends PopulationControl {
	
	private PSOList myPSOList;

	public PSOControl(PSOList componentList) {
		this.myPSOList = componentList;
	}
	
	public String[] getKeys(){
		return this.myPSOList.getYKeys();
	}
	
	public String[] getXKeys(String component){
		return this.myPSOList.getXKeys(component);
	}
	
	public boolean setValue(String component, String key, String value){
		return this.myPSOList.setValue(component, key, value);
	}
	
	public String getValue(String component, String key){
		return this.myPSOList.getValue(component, key);
	}
	
	public String getType(String component, String key){
		return this.myPSOList.getType(component, key);
	}
	
	public boolean validate(){
		return this.myPSOList.valid();
	}

}
