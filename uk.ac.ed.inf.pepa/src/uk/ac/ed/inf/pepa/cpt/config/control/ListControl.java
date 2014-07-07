package uk.ac.ed.inf.pepa.cpt.config.control;

import java.util.Arrays;

import uk.ac.ed.inf.pepa.cpt.config.lists.SingleChoiceList;

public class ListControl implements Control {
	
	protected SingleChoiceList myList;
	
	public ListControl(SingleChoiceList list){
		
		this.myList = list;
		
	}
	
	public String getLabel(){
		return this.myList.getLabel();
	}
	
	public String[] getChoices(){
		return this.myList.getChoices();
	}
	
	public void setValue(String s){
		this.myList.setValue(s);
	}
	
	public String getValue(){
		return this.myList.getValue();
	}
	
	public boolean validate(){
		return Arrays.asList(this.myList.getChoices()).contains(this.myList.getValue());
	}

}
