package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

public abstract class Parser {
	
	String subject;
	
	public Parser(String subject){
		this.subject = subject;
	}
	
	public abstract boolean isCorrect();

}