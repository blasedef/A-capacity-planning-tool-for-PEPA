package uk.ac.ed.inf.pepa.cpt.config.control;

import uk.ac.ed.inf.pepa.cpt.config.parameters.Parameters;

public class ParameterControl implements Control {
	
	Parameters myParameters;
	
	public ParameterControl(Parameters parameters){
		
		this.myParameters = parameters;
		
	}
	
	public boolean setValue(String key, String value){
		return this.myParameters.setValue(key, value);
	}
	
	public String getValue(String key){
		return this.myParameters.getValue(key);
	}
	
	public String getType(String key){
		return this.myParameters.getType(key);
	}
	
	public String[] getKeys(){
		return this.myParameters.getKeys();
	}
	
	public boolean validate(){
		return this.myParameters.valid();
	}

}
