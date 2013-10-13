package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.controllers;

public class EvenParser extends Parser{

	public EvenParser(String text) {
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
		if(i >= 2) {
			if(i % 2 == 0){
				return true;
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
}