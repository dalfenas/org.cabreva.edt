package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepScreenshot extends EDTTestStep {

	public static String NODE_NAME = "screenshot";

	public SelestepScreenshot(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		// nada a fazer.

	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		String inputFileName = context.getTestCase().getTestCaseFile().getName();
		StringBuffer outputFullFileName = new StringBuffer(context.getOutputFolder());
		outputFullFileName.append("/").append(inputFileName.substring(0, inputFileName.length() - 4)).append(".png");

		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		selenese.captureEntirePageScreenshot(outputFullFileName.toString(), "background=#CCFFDD");

	}

}
