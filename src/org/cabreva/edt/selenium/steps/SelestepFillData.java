package org.cabreva.edt.selenium.steps;

import java.util.List;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

public class SelestepFillData extends EDTTestStep {

	// private List<String> fieldsNames;
	// private List<EDTField> fields;
	private EDTFieldGroup fieldGroup;
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
		for (EDTFieldGroup group : fieldsGroups) {
			if (fieldsGroupName.equalsIgnoreCase(group.getGroupName())) {
				fieldGroup = group;
				break;
			}
		}
		// we should throw an exception when failing at finding a group with the given name
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		SeleniumAux auxiliar = new SeleniumAux(((SeleniumTransaction) context.getTransaction()).getSelenium());
		
		// Localiza-se o grupo de dados do workflow a ser digitado.
		EDTFieldGroup group = context.getTestCase().getFieldsGroupByName(fieldsGroupName);

		// Digita-se cada campo dentro do grupo
		for (EDTField field : fieldGroup.getFields()) {
			String value = group.getFieldValue(field.getId());
			field.setValue(value);
			auxiliar.type(field);
		}

	}
}
