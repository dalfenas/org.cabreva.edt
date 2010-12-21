package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepClickAndWait extends EDTTestStep {

	private String buttonName;
	
	public static String NODE_NAME = "press";

	public SelestepClickAndWait(Element node) {
		super();
		fill(node);
	}
	
	@Override
	public void fill(Element node) {
		buttonName = node.getAttributeValue("btn");
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		try {
			selenese.click(buttonName);
		} catch (Exception e) {
			throw new EDTException("Selenium exception while clicking button (btn name: " + buttonName + ")", e);
		}

	}

}
