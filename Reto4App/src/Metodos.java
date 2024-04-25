import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Metodos {

	/**
	 * Descripción: metodo para pasar de un panel a otro. Ocultará el actual y
	 * mostrará el que pasamos como parámetro.
	 * 
	 * @param nombreLayeredPane JLayeredPane Es un panel padre dentro del que están
	 *                          otro paneles entre los que permitiré visualizar uno
	 *                          y otro.
	 * @param identificadorCapa String Es el panel que quiero mostrar.
	 * 
	 *                          No devuelve ningún valor
	 * @author in1dm3
	 */
	public void cambiarDePanel(JLayeredPane layeredPane, String nombrePanel) {
		// CardLayout es un manejador de contenido
		CardLayout cardLayout = (CardLayout) layeredPane.getLayout();

		// Cambia la visibilidad de las capas en el JLayeredPane

		cardLayout.show(layeredPane, nombrePanel);
	}

	public void botonAtras(JLayeredPane layeredPane, String nombrePanel, JPanel variablePanel) {
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, nombrePanel);
			}
		});
		btnAtras.setBounds(55, 39, 89, 23);
		variablePanel.add(btnAtras);
	}

	public String bienvenidaMenu() {
		String msgBienvenida = "";

		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);

		if (hour < 6 || hour == 21 || hour == 22 || hour == 23)
			msgBienvenida = "¡Buenas noches!";
		else if (hour >= 6 && hour < 12)
			msgBienvenida = "¡Buenos días!";
		else
			msgBienvenida = "¡Buenas tardes!";

		return msgBienvenida;

	}

	public void botonPerfil(JLayeredPane layeredPane, JPanel variablePanel, String user) {
		JButton btnPerfil = new JButton(user);
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, "Perfil");
			}
		});
		btnPerfil.setBounds(735, 39, 89, 23);
		variablePanel.add(btnPerfil);

	}
}