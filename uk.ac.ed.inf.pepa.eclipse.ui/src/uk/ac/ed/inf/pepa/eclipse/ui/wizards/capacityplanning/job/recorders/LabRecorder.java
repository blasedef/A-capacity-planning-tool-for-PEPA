package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders;


import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.RecorderCandidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.RecordParameters;

public class LabRecorder extends Recorder {
	
	private ArrayList<RecorderCandidate> finals;

	public LabRecorder(RecordParameters recordParameters) {
		super(recordParameters);
		this.finals = new ArrayList<RecorderCandidate>();
	
	}

	@Override
	public void addNewCandidate(Candidate c, int generation) {
		
		Candidate d = (Candidate) new RecorderCandidate(c.getFitness(),
				c.getName(),
				c.getCreatedAt(),
				c.getPerformanceResultMap());
		d.setCandidateMap(c.getCandidateMap());
		d.setGeneration(generation);
	
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
		
	}

	@Override
	public void finalise(){
		
		int x = queue.size();
		
		for(int i = 0; i < x; i++){
			this.finals.add((RecorderCandidate) queue.poll());
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
