package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;
import org.cabreva.edt.EDTField.TypeField;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepFillField extends EDTTestStep {

	public static String NODE_NAME = "typeField";

	private EDTField field;

	public SelestepFillField(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		String name = node.getAttributeValue("name");
		String value = node.getAttributeValue("value");
		TypeField fieldType = TypeField.fromString(node.getAttributeValue("component"));
		field = new EDTField(name, fieldType);
		field.setValue(value);
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		new SeleniumAux(selenese).type(field);

	}

}
