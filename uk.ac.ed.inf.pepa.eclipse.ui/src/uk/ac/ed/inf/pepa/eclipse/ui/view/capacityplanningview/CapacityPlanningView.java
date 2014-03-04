package uk.ac.ed.inf.pepa.eclipse.ui.view.capacityplanningview;



import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.CapacityListenerManager;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.CapacityPlanningListener;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Results;


public class CapacityPlanningView extends ViewPart{
       
	public CapacityPlanningViewMix listener;
	private TableViewer viewer; 
	
	private static class CapacityPlanningViewMix implements CapacityPlanningListener {
		
		private CapacityPlanningView cView;
		
		public CapacityPlanningViewMix(CapacityPlanningView view){
			this.cView = view;
		}
		
		/*
		 * Listen for changes to the underlying PEPA Model
		 */
		public void capacityPlanningJobCompleted() {
			cView.handleCapacityJobFinished();
		}
		
	}
	
	protected void updateView() {
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				viewer.setInput(CapacityListenerManager.results);
			}
		});
		
		
	}

	public void handleCapacityJobFinished(){
		this.updateView();
	}
     
	@Override
	public void createPartControl(Composite parent) {
		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
			      | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		String[] columnNames = new String[] {"Result Type","Result"};
		int[] columnWidths = new int[] {250,100};
//		int[] columnAlignments = new int[] {SWT.LEFT,SWT.LEFT};
//		
//		for(int i = 0; i < columnNames.length; i++){
//			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
//			tableColumn.setText(columnNames[i]);
//			tableColumn.setWidth(columnWidths[i]);
//		}
		
		TableViewerColumn resultKey = createTableViewerColumn(columnNames[0], columnWidths[0], 0);
		resultKey.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element){
				Results r = (Results) element;
				return r.key;
			}
		});
		
		TableViewerColumn resultValue = createTableViewerColumn(columnNames[1], columnWidths[1], 1);
		resultValue.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element){
				Results r = (Results) element;
				return r.value;
			}
		});
		
		
		viewer.setContentProvider(new ArrayContentProvider());
		
		viewer.setInput(CapacityListenerManager.results);
		
	}
	
	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
	        SWT.NONE);
	    final TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
	  }
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
		listener = new CapacityPlanningViewMix(this);
		CapacityListenerManager.listener = listener;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
	}

}
