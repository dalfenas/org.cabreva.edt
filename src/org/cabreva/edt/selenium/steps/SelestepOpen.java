package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepOpen extends EDTTestStep {

	private String addressToOpen;

	public static String NODE_NAME = "navigate2";
	
	public SelestepOpen(Element node) {
		super();
		fill(node);
	}
	
	@Override
	public void fill(Element node) {
		addressToOpen = node.getAttributeValue("url");
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		try {
			selenese.open(addressToOpen);
			selenese.waitForPageToLoad("2000");
		} catch (Exception e) {
			throw new EDTException("Selenium exception while opening addres: " + addressToOpen, e);
		}
	}
}
