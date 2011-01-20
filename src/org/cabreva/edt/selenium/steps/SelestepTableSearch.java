package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.cabreva.edt.steps.BooleanStep;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class SelestepTableSearch extends BooleanStep {

	private boolean result = false;
	private String dataGroup = null;
	private String field= null;
	private Integer column= null;
	private Integer line= null;
	private String tableName= null;

	public static String NODE_NAME = "tableSearch";

	public SelestepTableSearch(Element node) {
		super();
		fill(node);
	}

	@Override
	public boolean getResult() {
		return result;
	}

	@Override
	public void fill(Element node) {
		tableName = node.getAttributeValue("tableName");
		Element target = node.getChild("target");
		Element origin = node.getChild("origin");
		if (target != null) {
			line = Integer.valueOf(target.getAttributeValue("line"));
			column = Integer.valueOf(target.getAttributeValue("column"));
		}
		if (origin != null) {
			dataGroup = origin.getAttributeValue("datagroup");
			field = origin.getAttributeValue("field");
		}
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		String value = null;
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		if (dataGroup != null) {
			EDTFieldGroup group = context.getTestCase().getFieldsGroupByName(dataGroup);
			value = group.getFieldValue(field);
		}
		if (line != null && column != null) {
			StringBuffer sb = new StringBuffer(tableName);
			sb.append(".").append(line).append(".").append(column);
			try {
				String celValue = selenese.getTable(sb.toString());
				result = value.equals(celValue);
			} catch (SeleniumException e) {
				result = false;
			}
		}
	}

}
