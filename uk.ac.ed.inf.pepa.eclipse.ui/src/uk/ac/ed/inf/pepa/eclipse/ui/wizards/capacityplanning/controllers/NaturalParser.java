package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

public class NaturalParser extends Parser{

	public NaturalParser(String text) {
		super(text);
	}

	@Override
	public boolean isCorrect() {
		
		Integer i;
		
		try{
			
			i = Integer.parseInt(subject);
			
		} catch (NumberFormatException e) {
			return false;
		}
		if(i > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
}