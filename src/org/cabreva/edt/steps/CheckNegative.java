package org.cabreva.edt.steps;

import java.util.List;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTFieldGroup;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.steps.SelestepSearchText;
import org.jdom.Element;

/**
 * Runs subFlow only if no BooleanStep in conditions succeeds.
 * @author cabreva
 *
 */
public class CheckNegative extends EDTTestStep {

	private List<BooleanStep> conditions;
	private StepsGroup subFlow;
	private List<EDTFieldGroup> fieldsGroups;

	public static String NODE_NAME = "checkNegative";
	
	public CheckNegative(Element node, List<EDTFieldGroup> fieldsGroups) {
		super();
		this.fieldsGroups = fieldsGroups;
		fill(node);
	}

	@Override
	public void fill(Element node) {
		
		@SuppressWarnings("unchecked")
		List<Element> elements = node.getChild("condition").getChildren();
		
		for(Element e:elements) {
			if(e.getName().equalsIgnoreCase( SelestepSearchText.NODE_NAME )) {
				conditions.add(new SelestepSearchText(e));
			}
		}
		
		subFlow = new StepsGroup(node.getChild("script"), fieldsGroups);

	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		for(BooleanStep cond: conditions) {
			cond.run(context);
			if(cond.getResult()) {
				return; // exits if any condition runs successfully
			}
		}
		subFlow.run(context);

	}

}
