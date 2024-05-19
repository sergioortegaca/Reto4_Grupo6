package modelo;

import javax.swing.JOptionPane;

public class LoginFallidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4187718690499253020L;

	public LoginFallidoException() {
		super();
		JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
	}
}