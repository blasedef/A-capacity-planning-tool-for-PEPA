package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public class PopulationTreeWidget extends CapacityPlanningWidget {
	
	final Tree tree;

	public PopulationTreeWidget(final IValidationCallback cb, Composite container,
			Control control) {
		super(cb, container, control);
	
		String[] keys = control.getKeys();
		
		tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		for (int i = 0; i < keys.length; i++) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(keys[i]);
		}
		
		tree.addListener(SWT.Selection, new Listener() {
            
			public void handleEvent(Event e) {
				cb.validate();   
			}
		}); 
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 4);
		tree.setLayoutData(data);

		
	}
		
	@Override
	public Response isValid() {
		
		TreeItem[] items = tree.getItems();
		ArrayList<Response> responses = new ArrayList<Response>();
		Response response = new Response(true);
		
		for(TreeItem item : items){
			String key, value;
			key = item.getText();
			if(item.getChecked()){
				value = "True";
			} else {
				value = "False";
			}
			if(!control.getValue(key).equals(value)){
				responses.add(new Response(control.setValue(key, value)));
			}
		} 
		
		for(Response r : responses){
			if(!r.valid){
				r.setComplaint("Invalid configuration");
				response = r;
			}
		}
		
		return response;
	}

}
