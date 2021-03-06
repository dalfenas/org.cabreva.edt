package org.cabreva.edt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cabreva.edt.steps.ReportStatusStep;
import org.cabreva.edt.steps.StepsGroup;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class EDTWorkflow {

	protected String name;
	protected String domain;
	private List<EDTFieldGroup> fieldsGroups;
	private EDTTestStep initFlow;
	private EDTTestStep processFlow;

	public void parseRoot(Element root) {
		name = root.getAttributeValue("name");
		domain = root.getAttributeValue("testDomain");
		@SuppressWarnings("unchecked")
		List<Element> dataGroupsElements = root.getChildren("dataGroup");
		fieldsGroups = new ArrayList<EDTFieldGroup>();
		if (dataGroupsElements != null) {
			for (Element e : dataGroupsElements) {
				fieldsGroups.add(parseDataFieldGroup(e));
			}
		}
		initFlow = parse(root.getChild("init"));
		processFlow = parse(root.getChild("process"));
	}

	public void init(EDTContext context) throws EDTException {
		try {
			initFlow.run(context);
		} catch (EDTAbortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(EDTContext context) throws IOException {
		EDTTestCase testCase = context.getTestCase();
		try {
			processFlow.run(context);
		} catch (EDTException e) {
			Element outputRoot = testCase.getOutputRoot();
			Element system = new Element("system");
			system.setAttribute("status", ReportStatusStep.SYSTEM_STATUS_UNEXPECTED);
			Element msg = new Element("message");
			msg.setText(e.getMessage());
			outputRoot.addContent(system);
			outputRoot.addContent(msg);
			outputRoot.setAttribute("result", ReportStatusStep.TEST_RESULT_NOK);
		} catch (EDTAbortException e) {
			// nothing to do.
		}
		save(new Document(testCase.getOutputRoot()), context.getOutputFolder() + "/"
				+ testCase.getTestCaseFile().getName());
	}

	public String getName() {
		return name;
	}

	private EDTTestStep parse(Element node) {
		return new StepsGroup(node, fieldsGroups);
	}

	private EDTFieldGroup parseDataFieldGroup(Element dataGroupElement) {
		String name = dataGroupElement.getAttributeValue("name");

		@SuppressWarnings("unchecked")
		List<Element> fieldElements = dataGroupElement.getChildren("field");
		List<EDTField> fields = new ArrayList<EDTField>();

		for (Element e : fieldElements) {
			EDTField.TypeField t = EDTField.TypeField.fromString(e.getAttributeValue("type"));
			EDTField field = new EDTField(e.getAttributeValue("name"), t);

			fields.add(field);
		}

		return new EDTFieldGroup(name, fields);
	}

	private void save(Document document, String fileName) throws IOException {
		XMLOutputter outputter = new XMLOutputter();
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		outputter.output(document, writer);
		writer.close();
	}

	public String getDomain() {
		return domain;
	}

}
