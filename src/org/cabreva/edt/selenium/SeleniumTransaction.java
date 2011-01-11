package org.cabreva.edt.selenium;

import org.cabreva.edt.EDTTransaction;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumTransaction implements EDTTransaction {

	private DefaultSelenium selenium = null;
	protected String domain;

	@Override
	public void start(String domain) {
		this.domain = domain;
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", domain);
		selenium.start();
	}

	@Override
	public void stop() {
		selenium.stop();
	}

	public DefaultSelenium getSelenium() {
		return selenium;
	}
}
