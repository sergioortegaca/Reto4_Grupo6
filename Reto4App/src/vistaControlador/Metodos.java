package vistaControlador;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import modelo.Album;
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

	public ArrayList<Musico> artistasBD(String DRIVER, String LinkBD, String usuarioBBDD, String contrasenaBBDD) {

		String sentencia = "SELECT DISTINCT * FROM Musico;";

		try {
			try {
				Class.forName(DRIVER);

			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
			} // Cargamos el Driver para mysql y Abrimos la conexión a BBDD
			Connection connection = (Connection) DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
			PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);
			ResultSet rs = st.executeQuery();

			ArrayList<Musico> artistas = new ArrayList<Musico>();
			while (rs.next()) {
				Musico artista = new Musico();
				artista.setArtistaID(rs.getInt("IDMusico"));
				artista.setNombreArtistico(rs.getString("NombreArtistico"));
				artistas.add(artista);
			}

			rs.close();
			st.close();
			connection.close();
			return artistas;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Album> albumesBD(String DRIVER, String LinkBD, String usuarioBBDD, String contrasenaBBDD,
			String artistaSeleccionado) {

		String sentencia = "SELECT DISTINCT Album.IDAlbum, Album.Titulo, Album.Ano, Album.Genero, Album.Genero, Album.Imagen FROM Album "
				+ "JOIN Musico ON Album.IDMusico = Musico.IDMusico " + "WHERE Musico.NombreArtistico = '"
				+ artistaSeleccionado + "';";

		try {
			Class.forName(DRIVER);
			Connection connection = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
			PreparedStatement st = connection.prepareStatement(sentencia);
			ResultSet rs = st.executeQuery();

			ArrayList<Album> albumes = new ArrayList<>();
			while (rs.next()) {
				Album album = new Album();
				album.setAlbumID(rs.getInt("IDAlbum"));
				album.setTituloAlbum(rs.getString("Titulo"));
				album.setAnoAlbum(rs.getDate("Ano"));
				album.setGeneroAlbum(rs.getString("Genero"));
				album.setImagenAlbum(rs.getString("Imagen"));
				albumes.add(album);
			}

			rs.close();
			st.close();
			connection.close();
			return albumes;

		} catch (SQLException | ClassNotFoundException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Podcaster> artistas2BD(String DRIVER, String LinkBD, String usuarioBBDD, String contrasenaBBDD) {

		String sentencia = "SELECT DISTINCT * FROM Podcaster;";

		try {
			try {
				Class.forName(DRIVER);

			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
			} // Cargamos el Driver para mysql y Abrimos la conexión a BBDD
			Connection connection = (Connection) DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
			PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);
			ResultSet rs = st.executeQuery();

			ArrayList<Podcaster> artistas = new ArrayList<Podcaster>();
			while (rs.next()) {
				Podcaster artista = new Podcaster();
				artista.setArtistaID(rs.getInt("IDPodcaster"));
				artista.setNombreArtistico(rs.getString("NombreArtistico"));
				artistas.add(artista);

				rs.close();
				st.close();
				connection.close();
				return artistas;
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Cancion> cancionesBD(String DRIVER, String LinkBD, String usuarioBBDD, String contrasenaBBDD,
			String albumSeleccionado) {

		
		String sentencia = " SELECT Audio.Nombre " + " FROM Audio "
				+ " JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio "
				+ " JOIN Album ON Cancion.IDAlbum = Album.IDAlbum " + " WHERE Album.Titulo = '" + albumSeleccionado
				+ "';";

		try {
			Class.forName(DRIVER);
			Connection connection = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
			PreparedStatement st = connection.prepareStatement(sentencia);
			ResultSet rs = st.executeQuery();

			ArrayList<Cancion> canciones = new ArrayList<>();

			while (rs.next()) {
				Cancion cancion = new Cancion();
				cancion.setNombre(rs.getString("Nombre"));
				canciones.add(cancion);

			}

			rs.close();
			st.close();
			connection.close();
			return canciones;

		} catch (SQLException | ClassNotFoundException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public JLabel createLabel(String text, int x, int y, int width, int height, JPanel panel) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		panel.add(label);
		return label;
	}

}