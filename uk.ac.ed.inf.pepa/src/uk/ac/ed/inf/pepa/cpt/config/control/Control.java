package uk.ac.ed.inf.pepa.cpt.config.control;

public interface Control {

	public boolean validate();
	
	public boolean setValue(String key, String value);
	
	public boolean setValue(String component, String key, String value);
	
	public String getValue(String key);
	
	public String getType(String key);
	
	public String[] getKeys();
	
}
