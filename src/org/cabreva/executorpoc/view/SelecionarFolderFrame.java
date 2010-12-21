package org.cabreva.executorpoc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cabreva.executorpoc.business.TesteCommand;
import org.cabreva.executorpoc.model.ParametrosTestes;

public class SelecionarFolderFrame extends JFrame {

	private ParametrosTestes parametros;
	private JTextField txtFolderIn;
	private JTextField txtFolderOut;
	private JPanel contentPane;

	public SelecionarFolderFrame() {

		setSize(800, 400);
		setTitle("Interface de ajuda do executor");
		parametros = new ParametrosTestes();

		contentPane = new JPanel();

		JLabel jlblIn = new JLabel("Diretório que contém os cenários de teste em XML:");
		txtFolderIn = new JTextField(58);
		JLabel jlblOut = new JLabel("Diretório onde serão salvas as screenshots:");
		txtFolderOut = new JTextField(58);
		JButton button = new JButton("Iniciar teste");

		contentPane.add(jlblIn);
		contentPane.add(txtFolderIn);
		contentPane.add(jlblOut);
		contentPane.add(txtFolderOut);		
		contentPane.setBackground(Color.GREEN);
		contentPane.add(button);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String folderIn = txtFolderIn.getText();
				String folderOut = txtFolderOut.getText();
				if (folderIn.trim().isEmpty() || folderOut.trim().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Ambos diretórios de saída e entrada precisam ser fornecidos",
							"Executor", JOptionPane.ERROR_MESSAGE);
					return;
				}
				parametros.setFolderIn(folderIn.trim());
				parametros.setFolderOut(folderOut.trim());
				new TesteCommand(parametros).run();
			}
		});

		this.add(contentPane);
		setVisible(true);

	}

}
