package org.cabreva.edt.selenium.steps;

import java.util.ArrayList;
import java.util.List;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

public class SelestepFillData extends EDTTestStep {

    //private List<String> fieldsNames;
    //private List<EDTField> fields;
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
        Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
        EDTFieldGroup group = context.getTestCase().getFieldsGroupByName(fieldsGroupName);
        
        for (EDTField field : fieldGroup.getFields()) {
            String value = group.getFieldValue(field.getId());
            if (field.getType().equals(EDTField.TypeField.TEXT)) {
                try {
                    selenese.type(field.getId(), value);
                } catch (Exception e) {
                    throw new EDTException("Selenium exception while typing value (" + value + ") in this field: "
                            + field, e);
                }
            } else {
                if (field.getType().equals(EDTField.TypeField.SELECT)) {
                    try {
                        selenese.select(field.getValue(), value);
                    } catch (Exception e) {
                        throw new EDTException("Selenium exception while select value (" + value + ") in this field: "
                                + field, e);
                    }
                }
            }
        }

    }
}
