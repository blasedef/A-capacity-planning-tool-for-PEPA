/*******************************************************************************
 * Copyright (c) 2006, 2009 University of Edinburgh.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD Licence, which
 * accompanies this feature and can be downloaded from
 * http://groups.inf.ed.ac.uk/pepa/update/licence.txt
 *******************************************************************************/
package uk.ac.ed.inf.pepa.ctmc.modelchecking;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.ctmc.modelchecking.internal.ICSLVisitor;


public class CSLPathPlaceHolder extends CSLAbstractPathProperty {

	public CSLPathPlaceHolder() {
	}
	
	public CSLAbstractPathProperty copy() {
		return new CSLPathPlaceHolder();
	}

	public boolean isSimple() {
		return true;
	}

	public String toString() {
		return "<*>";
	}
	
	public boolean containsPlaceHolder() {
		return true;
	}
	
	public CSLAbstractPathProperty replace(CSLAbstractProperty object1, CSLAbstractProperty object2) {		
		if (this == object1 && object2 instanceof CSLAbstractPathProperty) {
			return (CSLAbstractPathProperty)object2;
		} else {
			return this;
		}
	}

	public boolean equals(Object o) {
		if (o instanceof CSLPathPlaceHolder) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		return 4;
	}
	
	@Override
	public void accept(ICSLVisitor visitor) throws ModelCheckingException {
		visitor.visit(this);
	}
	
	@Override
	public CSLAbstractPathProperty normalise() {
		return this;
	}
	
	@Override
	public ArrayList<CSLAtomicNode> getAtomicProperties() {
		return new ArrayList<CSLAtomicNode>();
	}
	
}
