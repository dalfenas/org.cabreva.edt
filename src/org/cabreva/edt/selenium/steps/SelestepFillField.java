package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTField.TypeField;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

/**
 * Digite um valor em um campo html. Nome: "typeField". Atributos:
 * <br/><b>name</b> nome do campo
 * <br/><b>component</b> "text" ou "select"
 * <br/><b>value</b> valor estatico a digitar
 * <br/><b>origin</b> Nome do datagroup de onde se deseja obter um valor dinamico.
 * @author admin
 *
 */
public class SelestepFillField extends EDTTestStep {

	public static String NODE_NAME = "typeField";

	private EDTField field;
	private String value;
	private String origin;
	private String name;

	public SelestepFillField(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		name = node.getAttributeValue("name");
		value = node.getAttributeValue("value");
		origin = node.getAttributeValue("origin");
		TypeField fieldType = TypeField.fromString(node.getAttributeValue("component"));
		field = new EDTField(name, fieldType);
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		
		if(origin==null) {
			field.setValue(value);
		} else {
			EDTFieldGroup group = context.getTestCase().getFieldsGroupByName(origin);
			field.setValue(group.getFieldValue(name));
		}
		
		new SeleniumAux(selenese).type(field);

	}

}
