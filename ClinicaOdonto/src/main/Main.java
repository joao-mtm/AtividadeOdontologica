package main;

import javax.swing.SwingUtilities;

import view.CadastroPacientes;

public class Main {
	public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            CadastroPacientes tela = new CadastroPacientes();

            tela.setVisible(true);
        });
    }
}
