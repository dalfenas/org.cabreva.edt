package org.cabreva.edt.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

/**
 * Este passo existe apenas para interromper a execução
 * do teste.
 * @author cabreva
 *
 */
public class AbortStep extends EDTTestStep {

	public static String NODE_NAME = "abort";
	
	@Override
	public void fill(Element node) {
		// nada a fazer

	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		throw new EDTAbortException();
	}

}
