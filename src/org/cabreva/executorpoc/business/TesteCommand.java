package org.cabreva.executorpoc.business;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.cabreva.edt.EDTContext;
import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTTestCase;
import org.cabreva.edt.EDTTransaction;
import org.cabreva.edt.EDTWorkflow;
import org.cabreva.edt.selenium.SeleniumTransaction;
import org.cabreva.executorpoc.model.ParametrosTestes;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class TesteCommand {

	ParametrosTestes parametros;
	// Build the document with SAX and Xerces, no validation
	SAXBuilder builder;

	public TesteCommand(ParametrosTestes parametros) {
		super();
		this.parametros = parametros;
	}

	private EDTWorkflow parseFile(File file) {
		try {
			Document doc = builder.build(file.getAbsoluteFile());
			EDTWorkflow workflow = new EDTWorkflow();
			workflow.parseRoot(doc.getRootElement());
			return workflow;
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null, "Error while parsing XML file '" + file.getName()
					+ "'. Original exception message:/n" + e.getMessage(), "Erro na leitura do workflow",
					JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error (IOException) while reading file '" + file.getName()
					+ "'. Original exception message:/n" + e.getMessage(), "Erro na leitura do workflow",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private EDTContext createContextForTestCase(EDTTransaction transaction, File file) {
		try {
			EDTTestCase testCase = new EDTTestCase(file);
			return new EDTContext(transaction, parametros.getFolderOut(), testCase);
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null, "Error while parsing XML file '" + file.getName()
					+ "'. Original exception message:/n" + e.getMessage(), "Erro na leitura do test case",
					JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error (IOException) while reading file '" + file.getName()
					+ "'. Original exception message:/n" + e.getMessage(), "Erro na leitura do test case",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public void run() {
		System.out.println(parametros.getFolderIn());
		File folder = new File(parametros.getFolderIn());

		// Filtramos primeiro somente os arquivos de workflow (WML)
		File[] listOfWorkflows = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return !arg0.isDirectory() && arg0.getName().toLowerCase().endsWith(".wml");
			}
		});
				
		builder = new SAXBuilder();

		List<EDTWorkflow> wflist = new ArrayList<EDTWorkflow>();
		for (int i = 0; i < listOfWorkflows.length; i++) {
			if (listOfWorkflows[i].isFile()) {
				EDTWorkflow wf = parseFile(listOfWorkflows[i]);
				if(wf==null) return;
				wflist.add(wf);
			}
		}
		
		// Filtramos os casos de teste (XML)
		File[] listOfTestCases = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return !arg0.isDirectory() && arg0.getName().toLowerCase().endsWith(".xml");
			}
		});
		
		// initicamos a transação
		EDTTransaction transaction = new SeleniumTransaction();
		transaction.start();
		
		// iniciamos os "workflows"
		EDTContext workflowContext = new EDTContext(transaction, parametros.getFolderOut());
		for(EDTWorkflow wf: wflist){
			try {
				wf.init(workflowContext);
			} catch (EDTException e) {
				JOptionPane.showMessageDialog(null, "Error while starting workflow '" + wf.getName()
						+ "'. Original exception message:/n" + e.getMessage(), "Erro no processamento do workflow",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		// rodamos os test cases
		for (int i = 0; i < listOfTestCases.length; i++) {
			if (listOfTestCases[i].isFile()) {
				EDTContext context = createContextForTestCase(transaction, listOfTestCases[i]);
				if(context==null) return;
				String wfname = context.getTestCase().getWorkflowName();
				for(EDTWorkflow wf:wflist) {
					if(wf.getName().equalsIgnoreCase(wfname)) {
						try {
							wf.run(context);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Error while writing file '" + listOfTestCases[i].getName()
									+ "'. Original exception message:/n" + e.getMessage(), "Erro no processamento do test case",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
			}
		}
		
		
		transaction.stop();
	
	}

}
