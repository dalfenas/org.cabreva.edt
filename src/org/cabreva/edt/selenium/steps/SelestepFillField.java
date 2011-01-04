package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

public class SelestepFillField extends EDTTestStep {

	public static String NODE_NAME = "typeField";

	public SelestepFillField(Element node) {
		super();
		fill(node);
	}
	
	@Override
	public void fill(Element node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		// TODO Auto-generated method stub

	}

}
