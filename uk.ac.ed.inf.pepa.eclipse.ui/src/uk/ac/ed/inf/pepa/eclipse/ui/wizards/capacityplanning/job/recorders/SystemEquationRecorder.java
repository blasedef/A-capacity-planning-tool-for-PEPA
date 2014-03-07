package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.JSONObject;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.RecorderCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.SystemEquationCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;

public class SystemEquationRecorder extends Recorder {
	
	protected HashMap<String,Double> candidateNameToFitnessHash;
	private HashMap<String,HashMap<String,Double>> nameToPerformanceResultsMapHash;
	private JSONObject json;
	private ArrayList<RecorderCandidate> finals;
	private double lastFinished;
	private MetaHeuristicParameters metaheuristicParameters;
	
	public SystemEquationRecorder(RecordParameters recordParameters, MetaHeuristicParameters metaheuristicParameters){
		super(recordParameters);
		nameToPerformanceResultsMapHash = new HashMap<String,HashMap<String,Double>>();
		this.candidateNameToFitnessHash = new HashMap<String, Double>();
		this.json = new JSONObject("temp");
		this.finals = new ArrayList<RecorderCandidate>();
		this.lastFinished = -1.0;
		this.metaheuristicParameters = metaheuristicParameters;
	}
	
	@Override
	public void addNewCandidate(Candidate c, int generation){
		
		Candidate d = (Candidate) new RecorderCandidate(c.getFitness(),
				c.getName(),
				c.getCreatedAt(),
				((SystemEquationCandidate) c).getPerformanceResultMap());
		d.setCandidateMap(c.getCandidateMap());
		d.setGeneration(generation);
		if(d.getCreatedAt() > this.lastFinished)
			this.lastFinished= d.getCreatedAt();
	
		this.queue.add((Candidate) d);
		if(this.queue.size() > this.queueSize){
			this.queue.poll();
		}
		
		
		if(this.generation.size() <= generation){
			ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
			candidateList.add(d);
			this.generation.add(candidateList);
		} else {
			this.generation.get(((Integer) generation)).add(d);
		}
		
		String s = c.getName();
		Double e = c.getFitness();
		HashMap<String,Double> pm = ((SystemEquationCandidate) c).getPerformanceResultMap();
		
		this.candidateNameToFitnessHash.put(s, e);
		this.nameToPerformanceResultsMapHash.put(s, pm);
		
	}
	
	public void finalise(){
		
		int x = queue.size();
		
		for(int i = 0; i < x; i++){
			this.finals.add((RecorderCandidate) queue.poll());
		} 
		
	}
	

	@Override
	public String getTopAsString(){
		
		String output;
		
		output = "";
		
		int x = finals.size();
		
		for(int i = 0; i < x; i++){
			output += "\"rank_" + (x - i) + "\":{" + this.finals.get(i).toStringWithPerformance() + "},\n";
		}
		
		output = output.substring(0,output.length() - 2);
		
		return output;
	}
	
	public HashMap<String,Double> getCandidateMapToFitnessHash(){
		return candidateNameToFitnessHash;
	}
	
	public HashMap<String, HashMap<String, Double>> getNameToPerformanceResultsMapHash() {
		return nameToPerformanceResultsMapHash;
	}
	
	public String thisGenerationsMix(int i){
		String output;
		
		HashMap<String,Double> names = new HashMap<String,Double>();
		HashMap<String,Double> fitnesses =  new HashMap<String,Double>();
		HashMap<String,Double> times =  new HashMap<String,Double>();
		
		ArrayList<Candidate> thisGeneration = this.generation.get(i);
		
		for(Candidate c : thisGeneration){
			if(names.containsKey(c.getName())){
				names.put(c.getName(), names.get(c.getName()) + 1);
				fitnesses.put(c.getName(), c.getFitness());
				times.put(c.getName(), c.getCreatedAt());
			} else {
				names.put(c.getName(), 1.0);
				fitnesses.put(c.getName(), c.getFitness());
				times.put(c.getName(), c.getCreatedAt());
			}
		}
		
		int generationSize = thisGeneration.size();

		output = "{\n";
		
		int y = 0;
		
		String temp = "";
		
		for(String s : names.keySet().toArray(new String[0])){
			temp += "\"SystemEquation_" + y 
			+ "\":{" + s + ",\"percentage of current population\":" 
			+ ((names.get(s)/generationSize) * 100) 
			+ ",\"fitness\":" + fitnesses.get(s)
			+ ",\"created\":" + times.get(s)
			+ "}" + ",\n";
			y++;
		}
		
		temp = temp.substring(0,temp.length() - 2);
		
		output += temp + "},\n";
		
		return output;
	}
	
	public void createJSON(){
		
		this.json = new JSONObject(recordParameters.getMetaheuristicType());
		
		this.json.put("\"Filename\":", "\"" + recordParameters.getIFile().getName() + "\",\n");
		
		if(!recordParameters.isHasSecondary()){
			this.json.put("MHParams",recordParameters.getMhParamsRoot());
		} else {
			this.json.put("MHParams Root",recordParameters.getMhParamsRoot());
			this.json.put("MHParams Candidate",metaheuristicParameters.getParameters());
		}
		
		this.json.put("MinPop",recordParameters.getMinPop());
				
		this.json.put("MaxPop",recordParameters.getMaxPop());
					
		this.json.put("PMTargets",recordParameters.getPmTargets());
				
		this.json.put("PMWeights",recordParameters.getPmWeights());
				
		this.json.put("PopWeights",recordParameters.getPopWeights());

		this.json.put("SysEqWeights",recordParameters.getSysEqWeights());
		
		this.json.put("\"T100\":","\n\t{\n" + getTopAsString() + "},\n");
		

		for(int j = 0; j < getGeneration().size(); j++){
			this.json.put("\"Gen_" + j + "\":\n",thisGenerationsMix(j));
		}
		
	}
	
	public void writeToDisk(int generation){
		
		this.createJSON();
		
		boolean success;
		
		success = (new File(recordParameters.getResultsFolder().addTrailingSeparator().toOSString())).mkdirs();
		if (!success) {
		    // Directory creation failed
		}
		
		String filename = recordParameters.getResultsFolder().addTrailingSeparator().toOSString() 
		+ Tool.getDateTime() 
		+ "_" 
		+ generation 
		+ "_" 
		+ getPopulations() 
		+ ".json";
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename,"UTF-8");
			writer.println(json.output());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//for writing the file label to disk
	private String getPopulations(){
		
		String output;
		
		output = "";
		
		HashMap<String,Double> rightMap = recordParameters.getMaxPop();
		HashMap<String,Double> leftMap = recordParameters.getMinPop();
		
		for(Map.Entry<String, Double> entry : rightMap.entrySet()){
			output = output + entry.getKey() + "[" + leftMap.get(entry.getKey()) + "_" + entry.getValue() + "]"; 
		}
		
		return output;
		
	}

	@Override
	public Candidate getTop() {
		return this.finals.get(this.finals.size() - 1);
	}

	@Override
	public double getLastFinished() {
		return this.lastFinished;
	}

}
