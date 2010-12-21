package org.cabreva.edt;

public class EDTField {

	private String id;
	private String value;
	
	public EDTField() {
		
	}
	
	public EDTField(String id) {
		super();
		this.id=id;
	}
	
	public EDTField(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
