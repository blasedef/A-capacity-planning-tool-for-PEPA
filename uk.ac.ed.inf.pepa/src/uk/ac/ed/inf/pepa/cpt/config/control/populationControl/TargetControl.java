package uk.ac.ed.inf.pepa.cpt.config.control.populationControl;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.config.control.PopulationControl;
import uk.ac.ed.inf.pepa.cpt.config.lists.TargetList;

public class TargetControl extends PopulationControl {
	
	private TargetList myTargetList;

	public TargetControl(TargetList targetList) {
		this.myTargetList = targetList;
	}
	
	public String[] getKeys(){
		return this.myTargetList.getYKeys();
	}
	
	public String[] getXKeys(String component){
		return this.myTargetList.getXKeys(component);
	}
	
	public boolean setValue(String component, String key, String value){
		return this.myTargetList.setValue(component, key, value);
	}
	
	public boolean setValue(String component){
		return this.myTargetList.setValue(component);
	}
	
	public void clearMap(){
		this.myTargetList.clear();
	}
	
	public String getValue(String component, String key){
		return this.myTargetList.getValue(component, key);
	}
	
	public boolean validate(){
		return this.myTargetList.valid();
	}

	@Override
	public String getType(String key, String attribute) {
		return this.myTargetList.getType(key, attribute);
	}

	public void update() {
		String[] keys = CPTAPI.getPerformanceControls().getLabels();
		
		for(int i = 0; i < keys.length; i++){
			this.setValue(keys[i]);
		}
		
	}


}
