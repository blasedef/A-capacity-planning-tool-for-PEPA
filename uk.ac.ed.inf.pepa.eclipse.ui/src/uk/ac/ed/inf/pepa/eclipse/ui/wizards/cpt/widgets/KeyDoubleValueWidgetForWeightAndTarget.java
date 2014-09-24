package uk.ac.ed.inf.pepa.eclipse.ui.wizards.cpt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.cpt.config.Config;
import uk.ac.ed.inf.pepa.cpt.config.control.Control;
import uk.ac.ed.inf.pepa.eclipse.ui.dialogs.IValidationCallback;

public class KeyDoubleValueWidgetForWeightAndTarget extends CapacityPlanningWidget {
	
	final Table table;

	public KeyDoubleValueWidgetForWeightAndTarget(final IValidationCallback cb, 
			Composite container, Control control) {
		super(cb, container, control);
	
		
		
		table = new Table(container, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
        table.setHeaderVisible(true);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        table.setLayoutData(data);
        
        String[] titles = {"Action/State", Config.LABWEI, Config.LABTAR};
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
            table.getColumn(i).pack();
        }
        
        
        String[] components = control.getKeys();
        for (int i = 0 ; i < components.length ; i++){
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText (0, components[i]);
            String[] attributes = control.getKeys(components[i]);
            for(int j = 0; j < attributes.length; j++){
            	item.setText (1, control.getValue(components[i], attributes[j]));
            }
        }
        
        final TableEditor editor = new TableEditor(table);
        editor.horizontalAlignment = SWT.LEFT;
    	editor.grabHorizontal = true;
    	editor.minimumWidth = 50;
    	// editing the second column
    	final int EDITABLECOLUMN = 1;
    	
    	table.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			// Clean up any previous editor control
    			org.eclipse.swt.widgets.Control oldEditor = editor.getEditor();
    			if (oldEditor != null) oldEditor.dispose();
    	
    			// Identify the selected row
    			TableItem item = (TableItem)e.item;
    			if (item == null) return;
    	
    			// The control that will be the editor must be a child of the Table
    			Text newEditor = new Text(table, SWT.NONE);
    			newEditor.setText(item.getText(EDITABLECOLUMN));
    			newEditor.addModifyListener(new ModifyListener() {
    				@Override
    				public void modifyText(ModifyEvent me) {
    					Text text = (Text)editor.getEditor();
    					editor.getItem().setText(EDITABLECOLUMN, text.getText());
    				}

    			});
    			newEditor.selectAll();
    			newEditor.setFocus();
    			editor.setEditor(newEditor, item, EDITABLECOLUMN);
    		}
    	});
        
        for (int i=0; i<titles.length; i++) {
            table.getColumn (i).pack ();
        }    

		
	}

	@Override
	public Response isValid() {
		
		return null;
		
	}

}
