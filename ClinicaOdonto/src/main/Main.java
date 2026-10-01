package main;

import javax.swing.SwingUtilities;

import view.CadastroPacientes;
import view.MenuPrincipal;

public class Main {
	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(
                () -> {

                	MenuPrincipal menu =
                            new MenuPrincipal();

                	menu.setVisible(true);
        });
    }
}
