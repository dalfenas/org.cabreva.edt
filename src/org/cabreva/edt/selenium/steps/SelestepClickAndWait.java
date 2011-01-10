package org.cabreva.edt.selenium.steps;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Element;

import com.thoughtworks.selenium.Selenium;

/**
 * Interpreta e roda nós "press" dos workflows
 * sintaxe: <press btn='btnName' timeOutforPage='xxx' input='true' inputType='submit'>
 * onde btn é o nome do botão, obrigatório
 * timeOutforPage é o timeout quando o botão abre uma nova página, na mesma janela/aba, em milisegundos
 * input, booleano, indica que o botão foi programado como "input". Se omitido, o valor de fault é false.
 * inputType é opcional e só pode ser preenchido quando input='true'. Determina o método de input. 
 * @author admin
 *
 */
public class SelestepClickAndWait extends EDTTestStep {

	private String buttonName;
	private String timeoutPage;
	private boolean input = false;
	private String inputType=null;

	public static String NODE_NAME = "press";

	public SelestepClickAndWait(Element node) {
		super();
		fill(node);
	}

	@Override
	public void fill(Element node) {
		buttonName = node.getAttributeValue("btn");
		timeoutPage = node.getAttributeValue("timeOutforPage");
		String input = node.getAttributeValue("input");
		if(input!=null){
			this.input=Boolean.parseBoolean("input");
			if(this.input=true) {
				inputType = node.getAttributeValue("inputType");
			}
		}
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		Selenium selenese = ((SeleniumTransaction) context.getTransaction()).getSelenium();
		try {
			if(!input) {
				selenese.click(buttonName);
			} else {
				StringBuffer sb = new StringBuffer("//input[@value='");
				sb.append(buttonName).append("'");
				if(inputType!=null) {
					sb.append(" and @type='").append(inputType).append("'");
				}
				sb.append("]");
				selenese.click(sb.toString());
			}
			if (timeoutPage != null) {
				selenese.waitForPageToLoad(timeoutPage);
			}
		} catch (Exception e) {
			throw new EDTException("Selenium exception while clicking button (btn name: " + buttonName + ")", e);
		}

	}

}
