package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class ModelObject {
	

	private Dictionary mySystemEquation = new Hashtable();


	public ModelObject() {
	}
	
	public void copyDictionary(Dictionary dict) {
		Enumeration keys = dict.keys();
		while(keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			Double value = (Double) dict.get(key);
			this.mySystemEquation.put(key, value);
		}
	}

	public Dictionary getDictionary() {
		return this.mySystemEquation;
	}

	public void setAnItem(String s, Double d) {
		mySystemEquation.put(s,d);
	}

}
