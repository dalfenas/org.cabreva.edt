package org.cabreva.edt.selenium.steps;

import java.util.ArrayList;
import java.util.List;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTTestCase;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepFillData extends EDTTestStep {

	private List<String> fieldsNames;
	private String fieldsGroupName;
	private List<EDTFieldGroup> fieldsGroups;
	
	public static String NODE_NAME = "typeDataGroup";

	public SelestepFillData(Element node, List<EDTFieldGroup> fieldsGroups) {
		super();
		this.fieldsGroups = fieldsGroups;
		fill(node);
	}

	@Override
	public void fill(Element node) {
		fieldsGroupName = node.getAttributeValue("name");
		for(EDTFieldGroup group:fieldsGroups){
			if( fieldsGroupName.equalsIgnoreCase(group.getGroupName() )) {
				fieldsNames = new ArrayList<String>();
				for(EDTField field: group.getFields()){
					fieldsNames.add(field.getId());
				}
				break;
			}
		}
		// we should throw an exception when failing at finding a group with the given name
	}

	@Override
	public void run(EDTTestCase context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		EDTFieldGroup group = context.getFieldsGroupByName(fieldsGroupName);
		for (String fieldName : fieldsNames) {
			String value = group.getFieldValue(fieldName);
			try {
				selenese.type(fieldName, value);
			} catch (Exception e) {
				throw new EDTException("Selenium exception while typing value (" + value + ") in this field: "
						+ fieldName, e);
			}
		}

	}

}
