package org.cabreva.executorpoc.business;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.cabreva.edt.EDTException;
import org.cabreva.edt.EDTManager;
import org.cabreva.executorpoc.model.ParametrosTestes;
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

	public void run() {
		EDTManager manager = new EDTManager(parametros.getFolderIn() + "\\workflow1.wml", parametros.getFolderIn(),
				parametros.getFolderOut());
		try {
			manager.run();
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null,
					"Erro na interpretação dos XML's de entrada. Erro original:\n" + e.getMessage(), "Executor",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Erro na leitura dos arquivos de entrada. Erro original:\n" + e.getMessage(), "Executor",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (EDTException e) {
			JOptionPane.showMessageDialog(null,
					"Erro EDT: " + e.getMessage() + ". Erro original:\n" + e.getCause().getMessage(), "Executor",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
