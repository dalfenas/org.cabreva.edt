package org.cabreva.edt;

public interface EDTTransaction {

	/**
	 * For security questions, only one domain can be tested
	 * by transaction.
	 * @param domain site domain (ex: google.com, localhost)
	 */
	public void start(String domain);
	public void stop();
}
