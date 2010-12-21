package org.cabreva;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;

public class SeleniumGoogleTest extends SeleneseTestBase {
	DefaultSelenium selenium = null;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox",
				"http://www.google.com.br/");
		selenium.start();
	}

	@Test
	public void testUntitled() throws Exception {
		selenium.open("/");
		selenium.type("q", "cabreva blogspot");
		selenium.click("btnG");
		if (selenium.isTextPresent("cabrevaL.blogspot.com")) {
			System.out.println("Código executado com sucesso");
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
