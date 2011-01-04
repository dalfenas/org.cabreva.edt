package org.cabreva.edt.steps;

import java.io.File;

import org.cabreva.edt.EDTAbortException;
import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestStep;
import org.jdom.Element;

public class CleanOutputStep  extends EDTTestStep {

	public static String NODE_NAME = "cleanOutput";
	
	@Override
	public void fill(Element node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(EDTContext context) throws EDTException, EDTAbortException {
		File outputFolder = new File(context.getOutputFolder());
		for(File outputFile:outputFolder.listFiles()) {
			if(!outputFile.isDirectory()) {
				if(!outputFile.delete() ) {
					throw new EDTException("Não foi possível apagar o arquivo " + outputFile.getName());
				}
			}
		}
	}

}
