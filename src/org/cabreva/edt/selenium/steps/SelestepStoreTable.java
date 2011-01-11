package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

public class SelestepStoreTable extends EDTTestStep {

	//private String[] columns;
	//private lines
	
	public static String NODE_NAME = "storeTable";
	
	public SelestepStoreTable(Element node) {
		fill(node);
	}
	
	@Override
	public void fill(Element node) {
		
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		// TODO Auto-generated method stub

	}

}
