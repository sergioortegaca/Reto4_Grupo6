package vistaControlador;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modelo.Album;
import modelo.Audio;
import modelo.Musico;
import modelo.Podcaster;

public class Metodos {

	/**
	 * Descripción: pasa de un panel a otro. Ocultará el actual y mostrará el que
	 * pasamos como parámetro.
	 * 
	 * @param layeredPane JLayeredPane. Es un panel padre dentro del que están otros
	 *                    paneles superpuestos.
	 * @param nombrePanel String. Es el panel que se mostrará.
	 * @author grupo6
	 */
	public void cambiarDePanel(JLayeredPane layeredPane, String nombrePanel) {
		CardLayout cardLayout = (CardLayout) layeredPane.getLayout();
		cardLayout.show(layeredPane, nombrePanel);
	}

	/**
	 * Descripción: crea el botón de volver al panel anterior. El botón se creara en
	 * el panel que pasamos como parámetro.
	 * 
	 * @param layeredPane   JLayeredPane. Es un panel padre dentro del que están
	 *                      otros paneles superpuestos.
	 * @param nombrePanel   String. Es el panel al que nos llevara el botón que se
	 *                      va a crear.
	 * @param variablePanel JPanel. Es el panel en el que se crea el botón.
	 * @author grupo6
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
	 * Descripción: crea el botón de ir al perfil de usuario. El botón se creara en
	 * el panel que pasamos como parámetro.
	 * 
	 * @param layeredPane   JLayeredPane. Es un panel padre dentro del que están
	 *                      otros paneles superpuestos.
	 * @param variablePanel JPanel. Es el panel en el que se crea el JButton.
	 * @param user          String. Es el nombre de usuario con el que el usuario ha
	 *                      iniciado sesión.
	 * @param perfil        String. Es el nombre del panel del perfil de usuario al
	 *                      que nos llevara el JButton.
	 * @author grupo6
	 */
	public void botonPerfil(JLayeredPane layeredPane, JPanel variablePanel, String user, String perfil) {
		JButton btnPerfil = new JButton(user);
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarDePanel(layeredPane, perfil);
			}
		});
		btnPerfil.setBounds(735, 28, 89, 23);
		variablePanel.add(btnPerfil);
	}

	/**
	 * Descripción: configura el mensaje de bienvenida del panel menú en función de
	 * la hora del ordenador.
	 * 
	 * @return Devuelve un String que será el mensaje que se muestra en el panel
	 *         menú.
	 * @author grupo6
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

	/**
	 * Descripción: crea un JLabel con el texto, dimensiones y posición que pasamos
	 * por parámetro. Se creará en el panel que pasamos como parámetro.
	 * 
	 * @param texto   String. Texto que va a tener el JLabel.
	 * @param x       int. Coordenada en el eje x en el que va a estar ubicado el
	 *                JLabel.
	 * @param y       int. Coordenada en el eje y en el que va a estar ubicado el
	 *                JLabel.
	 * @param anchura int. Anchura del JLabel.
	 * @param altura  int. Altura del JLabel.
	 * @param panel   JPanel. Panel en el que va a estar el JLabel.
	 * @author grupo6
	 */
	public void crearLabel(String texto, int x, int y, int anchura, int altura, JPanel panel) {
		JLabel label = new JLabel(texto);
		label.setBounds(x, y, anchura, altura);
		panel.add(label);
	}

	/**
	 * Descripción: rellena el JTextArea que hay en el panel de reproducción con la
	 * información de la canción que está seleccionada. Después actualiza el JPanel
	 * para que se muestre el nuevo texto.
	 * 
	 * @param textAreaInfoReproduciendo JTextArea. JTextArea que se va a rellenar.
	 * @param audioSeleccionado         Audio. Objeto de tipo Audio con la
	 *                                  información de la canción.
	 * @param albumSeleccionado         Album. Objeto de tipo Album con la
	 *                                  información del album al que pertenece la
	 *                                  canción.
	 * @param musicoSeleccionado        Musico. Objeto de tipo Musico con la
	 *                                  información del musico autor de la canción.
	 * @param panelReproduccion         JPanel. JPanel donde está el JTextArea.
	 * @author grupo6
	 */
	public void cancionSonando(JTextArea textAreaInfoReproduciendo, Audio audioSeleccionado, Album albumSeleccionado,
			Musico musicoSeleccionado, JPanel panelReproduccion) {

		textAreaInfoReproduciendo.setText("Está sonando " + audioSeleccionado.getNombreMultimedia() + "\nDel disco "
				+ albumSeleccionado.getTituloAlbum() + "\nDe " + musicoSeleccionado.getNombreArtistico());
		panelReproduccion.updateUI();

	}

	/**
	 * Descripción: rellena el JTextArea que hay en el panel de reproducción con la
	 * información del podcast que está seleccionado. Después actualiza el JPanel
	 * para que se muestre el nuevo texto.
	 * 
	 * @param textAreaInfoReproduciendo JTextArea. JTextArea que se va a rellenar.
	 * @param audioSeleccionado         Audio. Objeto de tipo Audio con la
	 *                                  información del podcast.
	 * @param pocasterSeleccionado      Podcaster. Objeto de tipo Podcaster con la
	 *                                  información del podcaster autor del podcast.
	 * @param panelReproduccion         JPanel. JPanel donde está el JTextArea.
	 * @author grupo6
	 */
	public void podcastSonando(JTextArea textAreaInfoReproduciendo, Audio audioSeleccionado,
			Podcaster pocasterSeleccionado, JPanel panelReproduccion) {

		textAreaInfoReproduciendo.setText("Estás reproduciendo " + audioSeleccionado.getNombreMultimedia()
				+ "\nDel podcaster " + pocasterSeleccionado.getNombreArtistico());
		panelReproduccion.updateUI();
	}
}