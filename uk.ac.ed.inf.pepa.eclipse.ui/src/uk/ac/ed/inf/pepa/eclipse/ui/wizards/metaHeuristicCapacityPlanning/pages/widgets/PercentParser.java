package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.pages.widgets;

public class PercentParser extends Parser{

	public PercentParser(String text) {
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
			if(i <= 1.0){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
}