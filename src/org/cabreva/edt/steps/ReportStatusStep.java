package org.cabreva.edt.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestCase;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

public class ReportStatusStep extends EDTTestStep {

	public static String NODE_NAME = "report";

	public static String SYSTEM_STATUS_FAILURE = "fail";
	public static String SYSTEM_STATUS_SUCCESS = "success";
	public static String SYSTEM_STATUS_UNEXPECTED = "unexpected";

	public static String TEST_RESULT_OK = "OK";
	public static String TEST_RESULT_NOK = "NOK";

	private String systemStatus;
	private String message;

	public ReportStatusStep(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		systemStatus = node.getAttributeValue("systemStatus");
		message = node.getValue();
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		EDTTestCase testCase = context.getTestCase(); 
		Element outputRoot = testCase.getOutputRoot();
		Element system = new Element("system");
		system.setAttribute("status",systemStatus);
		Element msg = new Element("message");
		if(message!=null || !message.isEmpty()) {
			msg.setText(message);
		}
		outputRoot.addContent(system);
		outputRoot.addContent(msg);
		
		if(SYSTEM_STATUS_UNEXPECTED.equalsIgnoreCase(systemStatus)) {
			outputRoot.setAttribute("result",TEST_RESULT_NOK);
		} else if (SYSTEM_STATUS_FAILURE.equalsIgnoreCase(systemStatus)) {
			if(testCase.isPositive()) {
				outputRoot.setAttribute("result",TEST_RESULT_NOK);
			} else {
				outputRoot.setAttribute("result",TEST_RESULT_OK);
			}
		} else if (SYSTEM_STATUS_SUCCESS.equalsIgnoreCase(systemStatus)) {
			if(testCase.isPositive()) {
				outputRoot.setAttribute("result",TEST_RESULT_OK);
			} else {
				outputRoot.setAttribute("result",TEST_RESULT_NOK);
			}
		}
	}
}
