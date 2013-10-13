package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models;

public class DropDownLists {
	
	/*
	 * label for list
	 */
	protected String key;
	
	/*
	 * user set value
	 */
	protected String value;
	
	/*
	 * list of options for the user
	 */
	protected String[] options;
	
	/*
	 * description of this drop down list
	 */
	protected String description;
	
	public DropDownLists(){
		this.description = "placeholder";
		
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setValue(String s){
		this.value = s;
	}
	
	public String[] getOptions(){
		return this.options;
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setKey(String key) {
		this.key = key;
		
	}
	

}
