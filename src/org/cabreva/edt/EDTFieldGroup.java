package org.cabreva.edt;

import java.util.List;

public class EDTFieldGroup {

	private String groupName;
	private List<EDTField> fields;

	public EDTFieldGroup(String groupName, List<EDTField> contextData) {
		super();
		this.groupName = groupName;
		this.fields = contextData;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<EDTField> getFields() {
		return fields;
	}

	public void setFields(List<EDTField> contextData) {
		this.fields = contextData;
	}

	public String getFieldValue(String dataid) {
		for (EDTField ecd : fields) {
			if (ecd.getId().equalsIgnoreCase(dataid)) {
				return ecd.getValue();
			}
		}
		return null;
	}
}
