package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import vistaControlador.*;

public class BDConexiones {

	Metodos metodos = new Metodos();

	// Variables de conexión con la BBDD
	final String driverBBDD = "jdbc:mysql";
	final String servidorBBDD = "localhost";
	// final String servidorBBDD = "rhythmicity.duckdns.org";
	final String puertoBBDD = "3306";
	final String nombreBBDD = "reto4_grupo6";
	// final String usuarioBBDD = "grupo6";
	final String usuarioBBDD = "root";
	final String contrasenaBBDD = "elorrieta";
	final String DRIVER = "com.mysql.cj.jdbc.Driver";
	final String LinkBD = driverBBDD + "://" + servidorBBDD + ":" + puertoBBDD + "/" + nombreBBDD;

	public Connection conexionBD() {
		try {
			Class.forName(DRIVER);
			Connection conexion = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
			return conexion;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public void cerrarConexionBD(PreparedStatement pS, Connection conexion) {
		try {
			pS.close();
			conexion.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	public void conexionInsertYDelete(String sentenciaSQL) {
		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQL);
			pS.executeUpdate();
			JOptionPane.showMessageDialog(null, "Operación exitosa");
			cerrarConexionBD(pS, conexion);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
		}
	}

	public void conexionInsertYDeleteCancion(String sentenciaSQL) {
		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQL);
			pS.executeUpdate();
			cerrarConexionBD(pS, conexion);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
		}
	}

	public void conexionLogin(String textOk, String user, String pass, String menu, JLayeredPane layeredPane,
			UsuarioFree Usuario) {
		String sentenciaSQL = "Select Usuario, Contrasena, IDCliente from Cliente where Usuario=? and Contrasena=?";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQL);
			pS.setString(1, user);
			pS.setString(2, pass);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				Usuario.setUsuario(user);
				Usuario.setClienteID(rS.getInt("IDCliente"));
				JOptionPane.showMessageDialog(null, textOk);
				metodos.cambiarDePanel(layeredPane, menu);
			} else {
				JOptionPane.showMessageDialog(null, "Creedenciales incorrectas");
			}
			rS.close();
			cerrarConexionBD(pS, conexion);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar iniciar sesión");
		}
	}

	/**
	 * Descripción: Este método se conecta a la base de datos, ejecuta una consulta
	 * SQL para obtener todos los registros de la tabla Musico y los convierte en un
	 * objetos Musico que se almacenan en un ArrayList.
	 *
	 * @return Una lista de objetos Musico que representa a los músicos almacenados
	 *         en la base de datos. Devuelve null si ocurre un error durante la
	 *         operación.
	 * 
	 * @author grupo6
	 */
	public ArrayList<Musico> conexionMusico() {

		String sentenciaSQL = "SELECT * FROM Musico";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Musico> musicoArrayList = new ArrayList<Musico>();
			while (rS.next()) {
				Musico artista = new Musico();
				artista.setArtistaID(rS.getInt("IDMusico"));
				artista.setNombreArtistico(rS.getString("NombreArtistico"));
				artista.setImagenArtista(rS.getString("Imagen"));
				artista.setDescripcionArtista(rS.getString("Descripcion"));
				artista.setCaracteristica(rS.getString("Caracteristica").toString());
				artista.setAnoActivo(rS.getInt("AnoActivo"));
				// artista.setReproducciones(rS.getInt("TotalReproducciones"));
				musicoArrayList.add(artista);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return musicoArrayList;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Obtiene una lista de álbumes de un artista específico desde la
	 * base de datos. Mediante el nombre artístico extrae una lista de albumes y los
	 * convierte en objetos que se almacenan en un ArrayList.
	 * 
	 * 
	 * @param artistaSeleccionado String.El nombre artístico del músico del cual se
	 *                            obtienen los álbumes.
	 * 
	 * @return Una lista de objetos Album con los albumes del artista seleccionado.
	 *         Devuelve null si ocurre un error durante la operación.
	 */
	public ArrayList<Album> conexionAlbum(String artistaSeleccionado) {

		String sentenciaSQL = "SELECT DISTINCT Album.IDAlbum, Album.Titulo, Album.Ano, Album.Genero, Album.Genero, Album.Imagen FROM Album "
				+ "JOIN Musico ON Album.IDMusico = Musico.IDMusico " + "WHERE Musico.NombreArtistico = '"
				+ artistaSeleccionado + "';";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Album> albumesArrayList = new ArrayList<>();
			while (rS.next()) {
				Album album = new Album();
				album.setAlbumID(rS.getInt("IDAlbum"));
				album.setTituloAlbum(rS.getString("Titulo"));
				album.setAnoAlbum(rS.getDate("Ano"));
				album.setGeneroAlbum(rS.getString("Genero"));
				album.setImagenAlbum(rS.getString("Imagen"));
				albumesArrayList.add(album);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return albumesArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Este método Obtiene una lista de canciones de un álbum
	 * específico desde la base de datos. Mediante una conexión a la base de datos,
	 * ejecuta una consulta SQL para obtener todas las canciones asociadas con un
	 * álbum cuyo título coincide con el valor proporcionado y las convierte en
	 * objetos Cancion que se almacena en un ArrayList.
	 * 
	 * @param albumSeleccionado String.El título del álbum cuyas canciones se desean
	 *                          obtener.
	 * 
	 * @return Una lista de objetos Cancion con las canciones del album
	 *         seleccionado. Devuelve null si ocurre un error durante la operación.
	 * 
	 * @author grupo6
	 */
	public ArrayList<Cancion> conexionCancion(String albumSeleccionado) {

		String sentenciaSQL = "SELECT Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen " + "FROM Audio "
				+ "JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio " + "JOIN Album ON Cancion.IDAlbum = Album.IDAlbum "
				+ "WHERE Album.Titulo = '" + albumSeleccionado
				+ "' GROUP BY Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Cancion> cancionesArrayList = new ArrayList<>();

			while (rS.next()) {
				Cancion multimedia = new Cancion();
				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				multimedia.setDuracion(rS.getTime("Duracion"));
				multimedia.setImagenMultimedia(rS.getString("Imagen"));
				cancionesArrayList.add(multimedia);

			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return cancionesArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Album> conexionAlbumAdmin() {

		String sentenciaSQL = "SELECT IDAlbum, Titulo FROM Album;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Album> albumesArrayList = new ArrayList<>();
			while (rS.next()) {
				Album album = new Album();
				album.setAlbumID(rS.getInt("IDAlbum"));
				album.setTituloAlbum(rS.getString("Titulo"));
				albumesArrayList.add(album);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return albumesArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Cancion> conexionCancionAdmin() {

		String sentenciaSQL = "SELECT IDAudio, Nombre FROM Audio WHERE Tipo = 'Cancion';";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Cancion> cancionesArrayList = new ArrayList<>();

			while (rS.next()) {
				Cancion multimedia = new Cancion();
				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				cancionesArrayList.add(multimedia);

			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return cancionesArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripcioón: Este método se conecta a la base de datos, ejecuta una consulta
	 * SQL para obtener los detalles de una canción cuyo ID coincide con el valor
	 * proporcionado y los convierte en un objeto Cancion. Mediante este método se
	 * actualiza la información para obtener los valores de la nueva cancion
	 * directamente desde la base de datos y almacenarlos en un objeto.
	 * 
	 * 
	 * @param IDAudio Int.El ID del audio de la canción que se desea obtener.
	 * 
	 * @return Devuelve un objeto con la información de la nueva canción
	 *         actualizada. Devuelve null si ocurre un error durante la operación.
	 * 
	 * @author grupo6
	 */
	public Cancion nuevaCancion(int IDAudio) {

		String sentenciaSQL = "SELECT * FROM Audio WHERE IDAudio = '" + IDAudio + "' ;";

		try {

			Cancion multimedia = new Cancion();

			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			while (rS.next()) {

				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				multimedia.setDuracion(rS.getTime("Duracion"));
				multimedia.setImagenMultimedia(rS.getString("Imagen"));

			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return multimedia;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;

	}

	public ArrayList<Podcaster> conexionPodcaster() {

		String sentenciaSQL = "SELECT DISTINCT * FROM Podcaster;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Podcaster> podcasterArrayList = new ArrayList<Podcaster>();
			while (rS.next()) {
				Podcaster artista = new Podcaster();
				artista.setArtistaID(rS.getInt("IDPodcaster"));
				artista.setNombreArtistico(rS.getString("NombreArtistico"));
				artista.setImagenArtista(rS.getString("Imagen"));
				artista.setDescripcionArtista(rS.getString("Descripcion"));
				artista.setAnoActivo(rS.getInt("AnoActivo"));
				podcasterArrayList.add(artista);
			}
			rS.close();
			cerrarConexionBD(pS, conexion);
			return podcasterArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Podcast> conexionPodcast(int IDPodcaster) {

		String sentenciaSQL = "SELECT A.* " + "FROM Podcast P " + "INNER JOIN Audio A ON P.IDAudio = A.IDAudio "
				+ "WHERE P.IDPodcaster = (SELECT IDPodcaster " + "FROM Podcaster " + "WHERE IDPodcaster = "
				+ IDPodcaster + ");";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Podcast> podcastsArrayList = new ArrayList<>();

			while (rS.next()) {
				Podcast multimedia = new Podcast();
				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				multimedia.setDuracion(rS.getTime("Duracion"));
				multimedia.setTipoMultimedia(rS.getString("Tipo"));
				multimedia.setImagenMultimedia(rS.getString("Imagen"));
				podcastsArrayList.add(multimedia);

			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return podcastsArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Playlist> conexionPlaylist(int usuario) {

		String sentenciaSQL = "SELECT * FROM Playlist WHERE IDCliente='" + usuario + "';";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Playlist> playlistsArrayList = new ArrayList<>();
			while (rS.next()) {
				Playlist playlist = new Playlist();
				playlist.setIDList(rS.getInt("IDList"));
				playlist.setTitulo(rS.getString("Titulo"));
				playlistsArrayList.add(playlist);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return playlistsArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public ArrayList<Playlist> conexionPlaylistCanciones(int i) {
		String sentenciaSQL = "SELECT PlaylistCanciones.*, Audio.*, Musico.*, Album.*, Cancion.* "
				+ "FROM PlaylistCanciones " + "INNER JOIN Audio ON PlaylistCanciones.IDAudio = Audio.IDAudio "
				+ "INNER JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio  "
				+ "INNER JOIN Album ON Cancion.IDAlbum = Album.IDAlbum "
				+ "INNER JOIN Musico ON Album.IDMusico = Musico.IDMusico " + "WHERE IDList=" + i + ";";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Playlist> playlistsArrayList = new ArrayList<>();
			while (rS.next()) {
				Playlist playlistCanciones = new Playlist();
				playlistCanciones.setAudioID(rS.getInt("IDAudio"));
				playlistCanciones.setNombreArtistico(rS.getString("NombreArtistico"));
				playlistCanciones.setDuracion(rS.getTime("Duracion"));
				playlistCanciones.setNombreMultimedia(rS.getString("Nombre"));
				playlistsArrayList.add(playlistCanciones);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return playlistsArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;

	}

	/**
	 * Descripción: Método que selecciona la playlist favorita del cliente que está
	 * usando el programa.
	 * 
	 * @param Usuario UsuarioFree. Es el objeto al cual se asigna la lista favorita.
	 * 
	 * 
	 * @param id      Int. Es el id cliente el cual se usa para extraer la playlist
	 *                favorita asignada a su cuenta.
	 * 
	 * 
	 *                No devuelve ningún valor
	 * @author grupo6
	 */
	public void playlistFavorita(UsuarioFree Usuario, int id) {
		String sentenciaSQL = "SELECT * FROM Playlist WHERE Titulo='Favoritos' && IDCliente = " + id + ";";

		try {

			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();
			while (rS.next()) {
				Usuario.setPlaylistFavorita(rS.getInt("IDList"));

			}

			rS.close();
			cerrarConexionBD(pS, conexion);

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	/**
	 * Descripción: Método que crea en la base de datos la playlist que todos los
	 * usuarios tienen de base mediante el ID del cliente.
	 * 
	 * @param Usuario   UsuarioFree. Es el objeto del cual se obtiene el IDCliente
	 *                  mediante el nombre de usuario.
	 * 
	 * 
	 * @param usuario   String. Es el nombre de usuario que se obtiene del text
	 *                  field (txtUsuarioRegistro) en el panel de registro para
	 *                  poder extraer el IDCliente en la base de datos.
	 * 
	 * 
	 * @param timeStamp String. Es la fecha de cuando se realizó el registro para
	 *                  asignarle la fecha de creación a la playlist default.
	 * 
	 *                  No devuelve ningún valor
	 * @author grupo6
	 */
	public void asignacionPlaylistDefault(UsuarioFree Usuario, String usuario, String timeStamp) {
		String sentenciaSQL = "SELECT * FROM Cliente WHERE Usuario='" + usuario + "';";

		try {

			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();
			while (rS.next()) {
				Usuario.setClienteID(rS.getInt("IDCliente"));
			}
			rS.close();
			cerrarConexionBD(pS, conexion);

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		conexionInsertYDeleteCancion(
				"INSERT IGNORE INTO Playlist (Titulo, FechaCreacion, IDCliente) VALUES ('Favoritos', '" + timeStamp
						+ "', '" + Usuario.getClienteID() + "')");

	}

}
