package uk.ac.ed.inf.pepa.cpt.config.control;

import uk.ac.ed.inf.pepa.cpt.config.ConfigCallBack;
import uk.ac.ed.inf.pepa.cpt.config.parameters.Parameters;

public class PSOParameterControl extends ParameterControl {

	private ConfigCallBack cb;
	
	public PSOParameterControl(Parameters parameters, ConfigCallBack cb) {
		super(parameters);
		this.cb = cb;
	}
	
	public boolean setValue(String key, String value){
		return this.myParameters.setValue(key, value) && cb.setPSOvalues(); 
	}
	
	public boolean validate(){
		return this.myParameters.valid() && cb.setPSOvalues();
	}

}
