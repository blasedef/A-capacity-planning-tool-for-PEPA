package uk.ac.ed.inf.pepa.cpt.searchEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import uk.ac.ed.inf.pepa.cpt.CPTAPI;
import uk.ac.ed.inf.pepa.cpt.searchEngine.candidates.HillClimbingLab;
import uk.ac.ed.inf.pepa.cpt.searchEngine.tree.ResultNode;


public class CPT {
	
	private HillClimbingLab root;
	private IProgressMonitor monitor;
	private PriorityQueue<ResultNode> resultsQueue;
	private IStatus myStatus;
	
	public CPT (IProgressMonitor monitor){
		
		this.monitor = monitor;
		
		CPTAPI.setTime(System.currentTimeMillis());
		
	}
	
	public IStatus start() {
		
		try{
		
			this.monitor.beginTask("Searching", CPTAPI.toPrint().length + 2*(CPTAPI.totalHCLabWork() *
					CPTAPI.totalHCWork() * CPTAPI.totalPSOLabWork() * CPTAPI.totalPSOWork()));
			
			this.root = new HillClimbingLab(new SubProgressMonitor(monitor, (CPTAPI.totalHCLabWork() *
					CPTAPI.totalHCWork() * CPTAPI.totalPSOLabWork() * CPTAPI.totalPSOWork())));
			
			createResultsQueue();
			
			generateCSVFile();
		} finally {
			this.monitor.done();
		}
		
		return myStatus;
		
	}
	
	public void generateCSVFile(){
		
		try{
			
			String[] labels = new String[this.resultsQueue.size()];
			
			FileWriter writer = new FileWriter(CPTAPI.getFileName());
			
			String outputString = "";
			
			int rqs = this.resultsQueue.size();
			
			this.monitor.subTask("scanning results: " + rqs + " left");
			rqs--;
			
			if(this.monitor.isCanceled()){
				this.myStatus = Status.CANCEL_STATUS;
				throw new OperationCanceledException();
			}
			
			ResultNode rn = this.resultsQueue.poll();
			outputString = rn.heading() + "\n";
			outputString = outputString + rn.toString() + "\n";
			CPTAPI.getResultList().add(rn);
			labels[0] = rn.populationMapAsNodeString();
			writer.append(outputString);
			
			this.monitor.worked(1);
			
			int i = 1;
			
			while(this.resultsQueue.size() > 0){
				
				rn = this.resultsQueue.poll();
				
				this.monitor.subTask("scanning results: " + rqs + " left");
				rqs--;
				
				if(this.monitor.isCanceled()){
					this.myStatus = Status.CANCEL_STATUS;
					throw new OperationCanceledException();
				}
				
				outputString = rn.toString() + "\n";
				CPTAPI.getResultList().add(rn);
				labels[i] = rn.populationMapAsNodeString();
				i++;
				writer.append(outputString);
				this.monitor.worked(1);
			}
			
			this.monitor.subTask("saving configuration...");
			
			writer.append("\n");
			writer.append("Configuration \n");
			
			String[] configuration = CPTAPI.toPrint();
			outputString = "";
			
			for(String s : configuration){
				
				if(this.monitor.isCanceled()){
					this.myStatus = Status.CANCEL_STATUS;
					throw new OperationCanceledException();
				}
				
				outputString = outputString + s + "\n";
				this.monitor.worked(1);
			}
			
			this.monitor.subTask("complete.");
			
			writer.append(outputString);
			
			writer.close();
			this.myStatus = Status.OK_STATUS;
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
			
	}
	
	public void createResultsQueue(){
		
		//remove duplicates
		this.resultsQueue = new PriorityQueue<ResultNode>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean offer(ResultNode e){
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
			@Override
			public boolean add(ResultNode e){
				if(this.contains(e)){
					return false;
				} else {
					return super.offer(e);
				}
			}
			
		};
		
		fillQueue();
		
		
	}
	
	private void fillQueue(){
		
		this.monitor.subTask("Compiling results...");
		
		this.root.fillQueue(this.resultsQueue,this.monitor);
		
		this.monitor.subTask("Compiled results");
		
	}
	

}
