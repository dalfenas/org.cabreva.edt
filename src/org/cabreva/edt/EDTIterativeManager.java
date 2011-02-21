package org.cabreva.edt;

import java.io.File;
import java.io.IOException;

import org.cabreva.edt.selenium.SeleniumTransaction;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class EDTIterativeManager {

    private String workflowFileName;
    private String outputFolderName;
    EDTWorkflow wf;
    EDTTransaction transaction = null;
    private SAXBuilder builder;

    public EDTIterativeManager(String workflowFile, String outputFolder) {
        super();
        this.workflowFileName = workflowFile;
        this.outputFolderName = outputFolder;
    }

    public void init() throws JDOMException, IOException, EDTException {
        // Inicializando do JDOM
        builder = new SAXBuilder();

        // Parsing do workflow
        wf = parseFile(new File(workflowFileName));
        // initicamos a transacao
        transaction = new SeleniumTransaction();
        transaction.start(wf.getDomain());
        try {
            // iniciamos o workflow
            wf.init(new EDTContext(transaction, outputFolderName));
        } catch (EDTException ex) {
            stop();
            throw ex;         
        } 


    }

    public void run(String fullFileName) throws JDOMException, IOException {
        try {
            EDTContext caseContext = new EDTContext(transaction, outputFolderName, new EDTTestCase(new File(
                    fullFileName)));
            wf.run(caseContext);
        } //paramos a transacao caso ocorra uma excecao
        catch (Exception ex) {
            stop();
        }       

    }

    public void stop() {
        transaction.stop();
        transaction = null;
        wf = null;
    }

    private EDTWorkflow parseFile(File file) throws JDOMException, IOException {
        Document doc = builder.build(file.getAbsoluteFile());
        EDTWorkflow workflow = new EDTWorkflow();
        workflow.parseRoot(doc.getRootElement());
        doc.detachRootElement();
        return workflow;
    }
}
