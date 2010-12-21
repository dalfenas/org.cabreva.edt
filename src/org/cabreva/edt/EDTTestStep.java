package org.cabreva.edt;

import org.jdom.Element;

public abstract class EDTTestStep {

	public abstract void fill(Element node);

	public abstract void run(EDTTestCase context) throws EDTException, EDTAbortException;;

}
