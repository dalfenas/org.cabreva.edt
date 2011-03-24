package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

/**
 * Semelhante ao SelestepClickAndWait, porém aceita parâmetros dinâmicos, definos em dataGroups.
 * Sintaxes possíveis:
 * </br><i>{click linkName='value' timeoutPage="3000"/}</i>
 * </br>ou
 * </br><i>{click timeoutPage="3000"}{linkName datagroup='groupname' field='fieldname' /}{/click}</i>
 * 
 * O timeoutPage é opcional.
 * @author cabreva
 * 
 */
public class SelestepClickLink extends EDTTestStep {

	private String linkName = null;
	private String timeoutPage = null;
	private String dataGroup = null;
	private String field = null;
	
	public static String NODE_NAME = "clickLink";

	public SelestepClickLink(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		timeoutPage = node.getAttributeValue("timeOutforPage");
		linkName = node.getAttributeValue("link");
		if (linkName == null) {
			Element origin = node.getChild("linkName");
			dataGroup = origin.getAttributeValue("datagroup");
			field=origin.getAttributeValue("field");
		}
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		try {
			if (linkName!=null) {
				selenese.click("link="+linkName);
			} else {
				   EDTFieldGroup group = context.getTestCase().getFieldsGroupByName(dataGroup);
				   linkName = group.getFieldValue(field);			
		           selenese.click("link="+linkName);
			}
			if (timeoutPage != null) {
				selenese.waitForPageToLoad(timeoutPage);
			}
		} catch (Exception e) {
			throw new EDTException("Selenium exception while clicking button (btn name: " + linkName + ")", e);
		}

	}

}
