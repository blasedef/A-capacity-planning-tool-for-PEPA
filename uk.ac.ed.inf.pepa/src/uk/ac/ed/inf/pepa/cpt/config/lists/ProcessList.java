package uk.ac.ed.inf.pepa.cpt.config.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import uk.ac.ed.inf.pepa.cpt.config.options.ProcessOptions;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.expressions.Coordinate;


/**
 * For use with process/components in terms of performance target variables
 * @author twig
 *
 */
public class ProcessList implements IOptionList {
	
	private HashMap<String,Integer> myComponentIndexList;
	private HashMap<String,Short> mySequentialComponentProcessIDMap;
	private HashMap<String,ProcessOptions[]> myComponentToProcessOptionsMap;
	private HashMap<Short,String> myProcessIDToSequentialComponentMap;
	private HashMap<Short,Integer> myProcessIDToIndexMap;
	private HashMap<String,Short> myLabelToProcessIDMap;
	
	public ProcessList(IParametricDerivationGraph graph){
		
		this.myComponentIndexList = new HashMap<String,Integer>();
		this.mySequentialComponentProcessIDMap = new HashMap<String,Short>();
		this.myComponentToProcessOptionsMap = new HashMap<String,ProcessOptions[]>();
		this.myProcessIDToIndexMap = new HashMap<Short,Integer>();
		this.myProcessIDToSequentialComponentMap = new HashMap<Short,String>();
		this.myLabelToProcessIDMap = new HashMap<String,Short>();
		
		for(int i = 0; i < graph.getSequentialComponents().length;i++){
			ISequentialComponent c = graph.getSequentialComponents()[i];
			this.myComponentIndexList.put(c.getName(),i);
			this.myComponentToProcessOptionsMap.put(c.getName(), new ProcessOptions[c.getComponentMapping().size()]);
			int j = 0;
			for (Entry<Short, Coordinate> entry : c.getComponentMapping()) {
				int coordinate = entry.getValue().getCoordinate();
				short processId = entry.getKey();
				String label = graph.getSymbolGenerator().getProcessLabel(processId);
				if(c.getName().equals(label)){
					this.mySequentialComponentProcessIDMap.put(label, processId);
				}
				this.myComponentToProcessOptionsMap.get(c.getName())[j] = new ProcessOptions(label,processId,coordinate);
				this.myProcessIDToSequentialComponentMap.put(processId, c.getName());
				this.myProcessIDToIndexMap.put(processId, j);
				this.myLabelToProcessIDMap.put(label, processId);
				j++;
			}
		}
		
	}
	
	public int getComponentIndex(String label){
		return this.myComponentIndexList.get(label);
	}
	
	public int[] getSelectedCoordinates(String label){
		
		ArrayList<Integer> collector = new ArrayList<Integer>();
		
		for(int i = 0; i < this.myComponentToProcessOptionsMap.get(label).length;i++){
			if(this.myComponentToProcessOptionsMap.get(label)[i].isSelected()){
				collector.add(this.myComponentToProcessOptionsMap.get(label)[i].getCoordinate());
			}
		}
		
		Integer[] temp = collector.toArray(new Integer[collector.size()]);
		int[] toReturn = new int[temp.length];
		for(int i = 0; i < toReturn.length; i++){
			toReturn[i] = temp[i];
		}
		return toReturn;
	}
	
	public Integer[] getSelectedProcessIds(){
		
		ArrayList<Integer> collector = new ArrayList<Integer>();
		
		for(Entry<String, ProcessOptions[]> entry : this.myComponentToProcessOptionsMap.entrySet()){
			for(ProcessOptions p : entry.getValue()){
				if(p.isSelected()){
					collector.add(((Short) p.getProcessId()).intValue());
				}
			}
		}
		
		return collector.toArray(new Integer[collector.size()]);
		
	}
	
	public boolean setSelectedHandler(short processId, boolean selected){
		if(this.myProcessIDToSequentialComponentMap.containsKey(processId)){
			this.setSelected(processId, selected);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * given a processId (unique) set the underlying option
	 * @param processId
	 */
	private void setSelected(short processId, boolean selected){
		String key = this.myProcessIDToSequentialComponentMap.get(processId);
		int index = this.myProcessIDToIndexMap.get(processId);
		this.myComponentToProcessOptionsMap.get(key)[index].setSelected(selected);
	}
	
	public Integer getSeqIndexFromProcessID(Short processId){
		return this.myProcessIDToIndexMap.get(processId);
	}
	
	public ArrayList<HashMap<String,Short>> getProcessesUnderSequentialComponents(){
		
		ArrayList<HashMap<String,Short>> processesUnderSequential = new ArrayList<HashMap<String,Short>>();
		
		for(Entry<String, ProcessOptions[]> entry : this.myComponentToProcessOptionsMap.entrySet()){
			
			for(int i = 0; i < entry.getValue().length;i++){
				processesUnderSequential.add(new HashMap<String,Short>());
				processesUnderSequential.get(i).put(entry.getValue()[i].getLabel(), entry.getValue()[i].getProcessId());
			}
		}
		
		return processesUnderSequential;
		
	}
	
	public ArrayList<HashMap<String,Short>> getProcessIDOfSequentialComponents(){
		
		ArrayList<HashMap<String,Short>> processesIDsOfSequential = new ArrayList<HashMap<String,Short>>();
		
		processesIDsOfSequential.add(new HashMap<String,Short>());
		
		for(Entry<String, Short> entry : this.mySequentialComponentProcessIDMap.entrySet()){
			processesIDsOfSequential.get(0).put(entry.getKey(), entry.getValue());
		}
		
		return processesIDsOfSequential;
		
	}
	
	public String getRootComponent(short processId){
		return this.myProcessIDToSequentialComponentMap.get(processId);
	}
	
	
	public void toPrint(){
		System.out.println("//");
		for(Entry<String, Integer> entry : myComponentIndexList.entrySet()){
			
			System.out.println("Component: " 
					+ entry.getKey() 
					+ ", index: " 
					+ entry.getValue());
			
			for(int i = 0; i < myComponentToProcessOptionsMap.get(entry.getKey()).length;i++){
				myComponentToProcessOptionsMap.get(entry.getKey())[i].toPrint();
			}
		}
	}
	
	public void clearSelection(){
		
		for(Entry<String, ProcessOptions[]> entry : myComponentToProcessOptionsMap.entrySet()){
			for(int i = 0; i < entry.getValue().length;i++){
				entry.getValue()[i].setSelected(false);
			}
		}
		
	}

	/**
	 * given a processId (unique) set the underlying option, but only if it shares the same root
	 * as all other set processes
	 * @param processId
	 */
	public boolean setSelectedART(short processId, boolean selected) {
		Integer[] setProcessIds = getSelectedProcessIds();		
		Set<String> setOfRoots= new HashSet<String>();
		
		for(int i = 0; i < setProcessIds.length;i++){
			setOfRoots.add(this.myProcessIDToSequentialComponentMap.get(setProcessIds[i].shortValue()));
		}
		
		setOfRoots.add(this.myProcessIDToSequentialComponentMap.get(processId));
		
		if(setOfRoots.size() == 1){
			this.setSelected(processId, selected);
			return true;
		} else {
			return false;
		}
		
	}

	public ArrayList<HashMap<String, Short>> getAllProcessIds() {
		ArrayList<HashMap<String,Short>> allProcessIds = new ArrayList<HashMap<String,Short>>();
		
		allProcessIds.add(new HashMap<String,Short>());
		
		for(Entry<String, Short> entry : this.myLabelToProcessIDMap.entrySet()){
			allProcessIds.get(0).put(entry.getKey(), entry.getValue());
		}
		
		return allProcessIds;
	}

	public String getLabel(Short processId) {
		String seq = myProcessIDToSequentialComponentMap.get(processId);
		Integer i = myProcessIDToIndexMap.get(processId);
		return this.myComponentToProcessOptionsMap.get(seq)[i].getLabel();
	}
	
}



