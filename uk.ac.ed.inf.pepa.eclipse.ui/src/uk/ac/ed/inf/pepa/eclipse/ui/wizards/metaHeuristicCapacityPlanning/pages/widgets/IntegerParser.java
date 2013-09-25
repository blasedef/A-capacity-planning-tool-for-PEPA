package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

public class IntegerParser extends Parser{

	public IntegerParser(String text) {
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