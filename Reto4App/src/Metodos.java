import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public void botonPerfil(JLayeredPane layeredPane, JPanel variablePanel, String user) {
		JButton btnPerfil = new JButton(user);
		System.out.println(user);

		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, "Perfil");
			}
		});
		btnPerfil.setBounds(735, 39, 89, 23);
		variablePanel.add(btnPerfil);
	}
}