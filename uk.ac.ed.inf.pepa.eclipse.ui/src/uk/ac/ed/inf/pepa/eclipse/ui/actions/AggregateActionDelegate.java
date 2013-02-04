/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.eclipse.ui.actions;

import org.eclipse.jface.action.IAction;

import uk.ac.ed.inf.pepa.ctmc.solution.OptionMap;

public class AggregateActionDelegate extends BasicProcessAlgebraModelActionDelegate {

	@Override
	public void run(IAction action) {
		OptionMap map = model.getOptionMap();
		map.put(OptionMap.AGGREGATE_ARRAYS, action.isChecked());
		model.setOptionMap(map);
	}

	@Override
	protected void checkStatus() {
		if (this.action != null) {

			boolean aggregate = (Boolean) model
					.getOption(OptionMap.AGGREGATE_ARRAYS);
			action.setChecked(aggregate);
		}
	}
}
