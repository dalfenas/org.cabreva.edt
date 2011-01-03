package org.cabreva.edt.selenium;

import org.cabreva.edt.EDTTransaction;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumTransaction implements EDTTransaction {

	private DefaultSelenium selenium = null;

	@Override
	public void start() {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.google.com/");
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
