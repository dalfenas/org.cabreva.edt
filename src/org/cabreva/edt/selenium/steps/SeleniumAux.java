package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTField;

import com.thoughtworks.selenium.Selenium;

/**
 * Essa classe deve encapsular comportamentos similares de execu��o de comando, 
 * atrav�s dos variados Selestep. Deve ser visivel apenas dentro do pacote.
 * @author cabreva
 *
 */
class SeleniumAux {

	private Selenium selenese;

	public SeleniumAux(Selenium selenese) {
		this.selenese = selenese;
	}

	public void type(EDTField field) throws EDTException {
		if (field.getType().equals(EDTField.TypeField.TEXT)) {
			try {
				selenese.type(field.getId(), field.getValue());
			} catch (Exception e) {
				throw new EDTException("Selenium exception while typing value (" + field.getValue()
						+ ") in this field: " + field.getId(), e);
			}
		} else if (field.getType().equals(EDTField.TypeField.SELECT)) {
			try {
				selenese.select(field.getId(), field.getValue());
			} catch (Exception e) {
				throw new EDTException("Selenium exception while select value (" + field.getValue()
						+ ") in this field: " + field, e);
			}
		}
	}

}
