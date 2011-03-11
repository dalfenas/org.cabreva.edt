package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.cabreva.edt.steps.BooleanStep;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepSearchText extends BooleanStep {

	private boolean result = false;
	private String text;

	public static String NODE_NAME = "textSearch";
	
	public SelestepSearchText(Element node) {
		super();
		fill(node);
	}
	
	@Override
	public void fill(Element node) {
		text = node.getAttributeValue("text");
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		try {
			if (selenese.isTextPresent(text)) {
				result = true;
			}
                        else {
                                result = false;
                        }
		} catch (Exception e) {
			throw new EDTException("Selenium exception while clicking button (btn name: " + text + ")", e);
		}
	}

	@Override
	public boolean getResult() {
		return result;
	}

}
