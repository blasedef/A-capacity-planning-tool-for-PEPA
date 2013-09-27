package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;


/**
 * This are for the first page of the Wizard, key is the name of the 'thing' being chosen, options provide a list of different options the 'thing' can take, and value is the user selected choice.
 * @author twig
 *
 */
public abstract class Configuration {
	
	protected String value;
	protected String key;
	protected String[] options;
	
	public Configuration(String key, String defaultValue, String[] defaultOptions){
		this.value = defaultValue;
		this.options = defaultOptions;
		this.key = key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setValue(String type){
		this.value = type;
	}
	
	public String[] getOptions(){
		return this.options;
	}
	
	public String getTitle(){
		return key;
	}
	
	public abstract void setOptions(String option);
	
	public String summary(){
		return "" + this.key + " : " + this.value;
	}
	
}