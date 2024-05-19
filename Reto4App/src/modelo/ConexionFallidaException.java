package modelo;

import javax.swing.JOptionPane;

public class ConexionFallidaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5973774496480208145L;

	public ConexionFallidaException() {
		super();
		JOptionPane.showMessageDialog(null, "Ha ocurrido un error al cerra la conexión a la BD");
	}
}