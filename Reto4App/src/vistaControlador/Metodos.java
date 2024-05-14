package vistaControlador;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import modelo.Album;
import modelo.Audio;
import modelo.Cancion;
import modelo.Musico;
import modelo.Podcaster;

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

	/**
	 * Descripción: metodo para crear el botón de volver al panel anterior. El botón
	 * se creara en el panel que pasamos como parametro.
	 *
	 * @param layeredPane   JLayeredPane. Es un panel padre dentro del que están
	 *                      otro paneles entre los que permitiré visualizar uno y
	 *                      otro.
	 *
	 * @param nombrePanel   String. Es el panel al que nos llevara el botón que se
	 *                      va a crear.
	 *
	 * @param variablePanel JPanel. Es el panel en el que se crea el botón.
	 *
	 * @return No devuelve ningún valor.
	 *
	 * @author in1dm3
	 */
	public void botonAtras(JLayeredPane layeredPane, String nombrePanel, JPanel variablePanel) {
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, nombrePanel);
			}
		});
		btnAtras.setBounds(55, 28, 89, 23);
		variablePanel.add(btnAtras);
	}

	/**
	 * Descripción: metodo para crear el botón de ir al perfil de usuario. El botón
	 * se creara en el panel que pasamos como parametro
	 *
	 * @param layeredPane   JLayeredPane. Es un panel padre dentro del que están
	 *                      otro paneles entre los que permitiré visualizar uno y
	 *                      otro.
	 *
	 * @param variablePanel JPanel. Es el panel en el que se crea el botón.
	 *
	 * @param user          String. Es el nombre de usuario con el que el usuario ha
	 *                      iniciado sesión.
	 *
	 * @return No devuelve ningún valor.
	 *
	 * @author in1dm3
	 */
	public void botonPerfil(JLayeredPane layeredPane, JPanel variablePanel, String user) {
		JButton btnPerfil = new JButton(user);

		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, "Perfil");
			}
		});
		btnPerfil.setBounds(735, 28, 89, 23);
		variablePanel.add(btnPerfil);
	}

	/**
	 * Descripción: Configura el mensaje de bienvenida del panel menú en función de
	 * la hora del ordenador.
	 *
	 * @return Devuelve un String que será el mensaje que se muestra en el panel
	 *         menú.
	 *
	 * @author in1dm3
	 */
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

	public JLabel crearLabel(String texto, int x, int y, int anchura, int altura, JPanel panel) {
		JLabel label = new JLabel(texto);
		label.setBounds(x, y, anchura, altura);
		panel.add(label);
		return label;
	}

	public void cancionSonando(JTextArea textAreaInfoCancion, Audio audioSeleccionado, Album albumSeleccionado,
			Musico musicoSeleccionado, JPanel panelReproduccion) {

		textAreaInfoCancion.setText("Está sonando " + audioSeleccionado.getNombreMultimedia() + "\nDel disco "
				+ albumSeleccionado.getTituloAlbum() + "\nDe " + musicoSeleccionado.getNombreArtistico());

		textAreaInfoCancion.setLineWrap(true);
		textAreaInfoCancion.setWrapStyleWord(true);
		textAreaInfoCancion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfoCancion.setFont(new Font("Tahoma", Font.BOLD, 11));
		textAreaInfoCancion.setBackground(SystemColor.menu);
		textAreaInfoCancion.setBounds(192, 348, 489, 92);
		textAreaInfoCancion.setEditable(false);
		panelReproduccion.add(textAreaInfoCancion);
		panelReproduccion.updateUI();

	}

	public void podcastSonando(JTextArea textAreaInfoCancion, Audio audioSeleccionado, Podcaster pocasterSeleccionado,
			JPanel panelReproduccion) {

		textAreaInfoCancion.setText("Estás reproduciendo " + audioSeleccionado.getNombreMultimedia()
				+ "\nDel podcaster " + pocasterSeleccionado.getNombreArtistico());
		textAreaInfoCancion.setLineWrap(true);
		textAreaInfoCancion.setWrapStyleWord(true);
		textAreaInfoCancion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfoCancion.setFont(new Font("Tahoma", Font.BOLD, 11));
		textAreaInfoCancion.setBackground(SystemColor.menu);
		textAreaInfoCancion.setBounds(192, 348, 489, 92);
		textAreaInfoCancion.setEditable(false);
		panelReproduccion.add(textAreaInfoCancion);
		panelReproduccion.updateUI();
	}
}