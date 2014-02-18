package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job;

import java.util.HashMap;
import java.util.Map.Entry;

public class JSONObject {
	
	String output;
	
	public JSONObject(String string){
		output = "{\n\"Lab\":\""+string+"\",\n";
	}
	
	@SuppressWarnings("unchecked")
	public void put(String string, Object obj2){
		
		
		if(obj2 instanceof HashMap<?,?>){
			output += "\"" + string + "\":\n\t{\n";
			
			HashMap<String,Double> map;
			map = (HashMap<String,Double>) obj2;
			
			for(Entry<String, Double> entry : map.entrySet()){
				output += "\t\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",\n";
			}
			
			output += "\"junk\":0\t},\n";
			
		}
		
		if(obj2 == null){
			output += "\"" + string + "\":\""+ obj2 ;
		}
		
		if(obj2 instanceof String){
			output += string + obj2 ;
		}
		
	}

	public char[] output() {
		output += "\n}";
		return this.output.toCharArray();
	}

}
