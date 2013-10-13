package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

public class DoubleParser extends Parser{

	public DoubleParser(String text) {
		super(text);
	}

	@Override
	public boolean isCorrect() {
		
		Double i;
		
		try{
			
			i = Double.parseDouble(subject);
			
		} catch (NumberFormatException e) {
			return false;
		}
		if(i >= 0.0) {
			return true;
		}
		else {
			return false;
		}
	}
	
}