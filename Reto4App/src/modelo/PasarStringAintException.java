package modelo;

import javax.swing.JOptionPane;

public class PasarStringAintException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4815608728858799294L;

	public PasarStringAintException() {
		super();
		JOptionPane.showMessageDialog(null, "El año debe ser un número");
	}


}
