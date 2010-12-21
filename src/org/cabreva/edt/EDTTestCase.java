package org.cabreva.edt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class EDTTestCase {

	public final static String TEST_TYPE_POSITIVE = "P";
	public final static String TEST_TYPE_NEGATIVE = "N";
	
	private EDTTransaction transaction;
	private List<EDTFieldGroup> fieldsGroup;
	private String workflowName;
	private Boolean positive;
	private File testCaseFile;
	private String outputFolder;
	private Element outputRoot;

	public EDTTestCase(EDTTransaction transaction, File file, String outputFolder) throws JDOMException, IOException {
		super();
		this.transaction = transaction;
		this.testCaseFile = file;
		this.outputFolder = outputFolder;

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(file.getAbsoluteFile());
		Element root = doc.getRootElement();
		workflowName = root.getChild("workflow").getAttributeValue("name");
		positive = TEST_TYPE_POSITIVE.equalsIgnoreCase(root.getAttributeValue("type")); 
		
		@SuppressWarnings("unchecked")
		List<Element> fieldsGroupsElements = root.getChild("parameters").getChildren("dataGroup");
		fieldsGroup = new ArrayList<EDTFieldGroup>();

		for (Element groupElement : fieldsGroupsElements) {
			@SuppressWarnings("unchecked")
			List<Element> fieldsElements = groupElement.getChildren("field");

			List<EDTField> fields = new ArrayList<EDTField>();
			for (Element fieldElement : fieldsElements) {
				fields.add(new EDTField(fieldElement.getAttributeValue("name"), fieldElement.getAttributeValue("value")));
			}
			fieldsGroup.add(new EDTFieldGroup(groupElement.getAttributeValue("name"), fields));
		}
		
		outputRoot = new Element("testCase");
	}

	public Boolean isPositive() {
		return positive;
	}

	public File getTestCaseFile() {
		return testCaseFile;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public EDTTransaction getTransaction() {
		return transaction;
	}

	public String getOutputFolder() {
		return outputFolder;
	}
	
	public Element getOutputRoot() {
		return outputRoot;
	}

	public EDTFieldGroup getFieldsGroupByName(String name) {
		for (EDTFieldGroup ecd : fieldsGroup) {
			if (ecd.getGroupName().equalsIgnoreCase(name)) {
				return ecd;
			}
		}
		return null;
	}

}
