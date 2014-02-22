package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders;


import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.LabCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;

public class LabRecorder extends Recorder {
	
	private ArrayList<LabCandidate> finals;

	public LabRecorder(RecordParameters recordParameters) {
		super(recordParameters);
		this.finals = new ArrayList<LabCandidate>();
	
	}

	@Override
	public void addNewCandidate(Candidate c, int generation) {
		
		Candidate d = (Candidate) c.copySelf();
		d.setCandidateMap(Tool.copyHashMap(c.getCandidateMap()));
		d.nullOut();
		d.setFitness(c.getFitness());
		d.setGeneration(generation);
		((LabCandidate) d).setName(c.getName());
	
		this.queue.add(d);
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
		
	}

	@Override
	public void finalise(){
		
		int x = queue.size();
		
		for(int i = 0; i < x; i++){
			this.finals.add((LabCandidate) queue.poll());
		} 
		
	}

	@Override
	public double getLastFinished() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Candidate getTop() {
		return this.finals.get(this.finals.size() - 1);
	}

	@Override
	public String getTopAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToDisk(int generation) {
		// TODO Auto-generated method stub
		
	}


}
