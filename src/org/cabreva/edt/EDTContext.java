package org.cabreva.edt;


public class EDTContext {

	private EDTTransaction transaction;
	private String outputFolder;
	private EDTTestCase testCase;

	public EDTContext(EDTTransaction transaction, String outputFolder) {
		this(transaction, outputFolder, null);
	}
	
	public EDTContext(EDTTransaction transaction, String outputFolder, EDTTestCase testCase) {
		super();
		this.transaction = transaction;
		this.outputFolder = outputFolder;
		this.testCase = testCase;
	}	

	public EDTTransaction getTransaction() {
		return transaction;
	}

	public String getOutputFolder() {
		return outputFolder;
	}
	
	public EDTTestCase getTestCase() {
		return testCase;
	}


}
