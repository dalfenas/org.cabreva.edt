package org.cabreva.edt.steps;

import java.util.ArrayList;
import java.util.List;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.steps.SelestepClickLink;
import org.cabreva.edt.selenium.steps.SelestepClickAndWait;
import org.cabreva.edt.selenium.steps.SelestepFillData;
import org.cabreva.edt.selenium.steps.SelestepFillField;
import org.cabreva.edt.selenium.steps.SelestepOpen;
import org.cabreva.edt.selenium.steps.SelestepScreenshot;
import org.jdom.Element;

public class StepsGroup extends EDTTestStep {

	private List<EDTTestStep> steps;
	private List<EDTFieldGroup> fieldsGroups = null;

	public StepsGroup(Element node, List<EDTFieldGroup> dataGroups) {
		super();
		this.fieldsGroups = dataGroups;
		fill(node);
	}

	@Override
	public void fill(Element node) {

		steps = new ArrayList<EDTTestStep>();

		@SuppressWarnings("unchecked")
		List<Element> children = node.getChildren();

		for (Element child : children) {
			String childNodeName = child.getName();
			if (childNodeName.equalsIgnoreCase(SelestepFillData.NODE_NAME)) {
				steps.add(new SelestepFillData(child, fieldsGroups));
			} else if (childNodeName.equalsIgnoreCase(SelestepClickLink.NODE_NAME)) {
				steps.add(new SelestepClickLink(child));				
			} else if (childNodeName.equalsIgnoreCase(CleanOutputStep.NODE_NAME)) {
				steps.add(new CleanOutputStep());
			} else if (childNodeName.equalsIgnoreCase(SelestepFillField.NODE_NAME)) {
				steps.add(new SelestepFillField(child));
			} else if (childNodeName.equalsIgnoreCase(SelestepClickAndWait.NODE_NAME)) {
				steps.add(new SelestepClickAndWait(child));
			} else if (childNodeName.equalsIgnoreCase(SelestepOpen.NODE_NAME)) {
				steps.add(new SelestepOpen(child));
			} else if (childNodeName.equalsIgnoreCase(CheckNegative.NODE_NAME)) {
				steps.add(new CheckNegative(child, fieldsGroups));
			} else if (childNodeName.equalsIgnoreCase(CheckPositive.NODE_NAME)) {
				steps.add(new CheckPositive(child, fieldsGroups));
			} else if (childNodeName.equalsIgnoreCase(SelestepScreenshot.NODE_NAME)) {
				steps.add(new SelestepScreenshot(child));
			} else if (childNodeName.equals(AbortStep.NODE_NAME)) {
				steps.add(new AbortStep());
			} else if (childNodeName.equals(ReportStatusStep.NODE_NAME)) {
				steps.add(new ReportStatusStep(child));
			}
		}

	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		for (EDTTestStep step : steps) {
			step.run(context);
		}
	}

}
