package org.cabreva.edt;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class EDTManager {

	private String workflowFileName;
	private String inputFolderName;
	private String outputFolderName;
	private SAXBuilder builder;

	public EDTManager(String workflowFile, String inputFolder, String outputFolder) {
		super();
		this.workflowFileName = workflowFile;
		this.inputFolderName = inputFolder;
		this.outputFolderName = outputFolder;
	}

	public void run() throws JDOMException, IOException, EDTException {
		// Inicialização do JDOM
		builder = new SAXBuilder();

		// Parsing do workflow
		EDTWorkflow wf = parseFile(new File(workflowFileName));

		// Parsing dos arquivos de caso de testes
		File inputFolder = new File(inputFolderName);

		// Filtramos primeiro somente os arquivos de workflow (WML)
		File[] listOfTestCases = inputFolder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return !arg0.isDirectory() && arg0.getName().toLowerCase().endsWith(".xml");
			}
		});

		// initicamos a transação
		EDTTransaction transaction = new SeleniumTransaction();
		transaction.start(wf.getDomain());
		try {
			// iniciamos o workflow
			wf.init(new EDTContext(transaction, outputFolderName));
			
			// para cada caso de testes, fazemos o parse do arquivo e rodamos no contexto
			for (int i = 0; i < listOfTestCases.length; i++) {
				EDTContext caseContext = new EDTContext(transaction, outputFolderName, new EDTTestCase(
						listOfTestCases[i]));
				wf.run(caseContext);
			}
		} finally {
			// paramos a transação antes de sair por erro
			transaction.stop();
		}

	}

	private EDTWorkflow parseFile(File file) throws JDOMException, IOException {
		Document doc = builder.build(file.getAbsoluteFile());
		EDTWorkflow workflow = new EDTWorkflow();
		workflow.parseRoot(doc.getRootElement());
		doc.detachRootElement();
		return workflow;
	}

}
