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

	// Variables de conexión con la BD
	public final String driverBBDD = "jdbc:mysql";
	public final String servidorBBDD = "localhost";
	// public final String servidorBBDD = "rhythmicity.duckdns.org";
	public final String puertoBBDD = "3306";
	public final String nombreBBDD = "reto4_grupo6";
	// public final String usuarioBBDD = "grupo6";
	public final String usuarioBBDD = "root";
	public final String contrasenaBBDD = "julioespiaeritreo";
	public final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public final String LinkBD = driverBBDD + "://" + servidorBBDD + ":" + puertoBBDD + "/" + nombreBBDD;

	/**
	 * Descripción: prueba el driver y la conexión a la BD y si no salta ninguna
	 * excepción devuelve la conexión de tipo Connection.
	 * 
	 * @return Devuelve la conexión para que se use en el resto de metodos que
	 *         necesitan una conexión a la BD.
	 * @author grupo6
	 */
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

	/**
	 * Descripción: recibe como parámetros el PreparedStatement y la Connection y si
	 * no salta ninguna excepción los cierra.
	 * 
	 * @param pS       PreparedStatement. Es una plantilla para hacer consultas a la
	 *                 BD SQL que se rellena en cada consulta según las necesidades
	 *                 de dicha consulta.
	 * @param conexion Connection. Conexión a la BD.
	 * @author grupo6
	 */
	public void cerrarConexionBD(PreparedStatement pS, Connection conexion) {
		try {
			pS.close();
			conexion.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	/**
	 * Descripción: recibe como parámetro una sentencia SQL de tipo insert o delete
	 * para ejecutarla. Tanto si salta una excepción como si no, muestra un
	 * JOptionPane al usuario con el mensaje correspondiente.
	 * 
	 * @param sentenciaSQL String. Sentencia SQL que se va a ejecutar.
	 * @author grupo6
	 */
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

	/**
	 * Descripción: recibe como parámetro una sentencia SQL de tipo insert o delete
	 * para ejecutarla. Es exactamente igual que el metodo anterior, salvo por que
	 * no muestra un JOptionPane al usuario cuando la sentencia SQL se ha ejecutado
	 * correctamente.
	 * 
	 * @param sentenciaSQL String. Sentencia SQL que se va a ejecutar.
	 * @author grupo6
	 */
	public void conexionInsertYDeleteSinMensaje(String sentenciaSQL) {
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

	/**
	 * Descripción: comprueba en la BD si el usuario y la contraseña que ha
	 * introducido el usuario al intentar iniciar sesión existen. Tanto si salta una
	 * excepción como si no, muestra un JOptionPane al usuario con el mensaje
	 * correspondiente. Además, obtiene de la BD los datos del usuario que ha
	 * iniciado sesión para después mostrarlos en el perfil.
	 * 
	 * @param textOk      String. Texto que informa de que se ha iniciado sesión
	 *                    correctamente.
	 * @param user        String. Texto que ha introducido el usuario en el campo de
	 *                    usuario al intentar iniciar sesión.
	 * @param pass        String. Texto que ha introducido el usuario en el campo de
	 *                    contraseña al intentar iniciar sesión.
	 * @param menu        String. Es el nombre del panel de menú al que irá el
	 *                    usuario depués de iniciar sesión.
	 * @param layeredPane JLayeredPane. Es un panel padre dentro del que están otros
	 *                    paneles superpuestos.
	 * @param Usuario     UsuarioFree. Objeto de tipo UsuarioFree con la información
	 *                    del Usuario que ha iniciado sesión.
	 * @return Devuelve el objeto Usuario con la información del usuario que ha
	 *         iniciado sesión.
	 * @author grupo6
	 */
	public UsuarioFree conexionLogin(String textOk, String user, String pass, String menu, JLayeredPane layeredPane,
			UsuarioFree Usuario) {
		String sentenciaSQL = "Select Usuario, Contrasena, IDCliente, Nombre, Apellido, FechaNacimiento, FechaRegistro from Cliente where Usuario=? and Contrasena=?";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQL);
			pS.setString(1, user);
			pS.setString(2, pass);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				Usuario.setUsuario(user);
				Usuario.setClienteID(rS.getInt("IDCliente"));
				Usuario.setNombre(rS.getString("Nombre"));
				Usuario.setApellido(rS.getString("Apellido"));
				Usuario.setFechaNacimiento(rS.getString("FechaNacimiento"));
				Usuario.setFechaRegistro(rS.getString("FechaRegistro"));

				JOptionPane.showMessageDialog(null, textOk);
				metodos.cambiarDePanel(layeredPane, menu);
				rS.close();
				cerrarConexionBD(pS, conexion);
				return Usuario;
			} else {
				rS.close();
				cerrarConexionBD(pS, conexion);
				throw new LoginFallidoException();
			}
		} catch (LoginFallidoException e) {
			e.getMessage();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return Usuario;
	}

	public ArrayList<Musico> conexionMusico() {

		String sentenciaSQL = "SELECT * FROM Musico";

		/*
		 * "SELECT M.*, " + "COALESCE(R.TotalReproducciones, 0) AS TotalReproducciones "
		 * + "FROM Musico M " +
		 * "LEFT JOIN ( SELECT A.IDMusico, COUNT(*) AS TotalReproducciones " +
		 * "FROM Estadisticas R " + "INNER JOIN Cancion C ON R.IDAudio = C.IDAudio " +
		 * "INNER JOIN Album A ON C.IDAlbum = A.IDAlbum GROUP BY A.IDMusico ) R " +
		 * "ON M.IDMusico = R.IDMusico; ";
		 */
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
				album.setAnoAlbum(rS.getString("Ano"));
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

	public ArrayList<Cancion> conexionCancion(String albumSeleccionado) {

		String sentenciaSQL = "SELECT Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen " + "FROM Audio "
				+ "JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio " + "JOIN Album ON Cancion.IDAlbum = Album.IDAlbum "
				+ "WHERE Album.Titulo = '" + albumSeleccionado
				+ "' GROUP BY Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen;";

		/*
		 * "SELECT Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen," +
		 * "       COUNT(Reproducciones.IDAudio) AS total_reproducciones " +
		 * "FROM Audio " + "JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio " +
		 * "JOIN Album ON Cancion.IDAlbum = Album.IDAlbum " +
		 * "LEFT JOIN Reproducciones ON Audio.IDAudio = Reproducciones.IDAudio " +
		 * "WHERE Album.Titulo = '" + albumSeleccionado +
		 * "' GROUP BY Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen;";
		 */
		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Cancion> cancionesArrayList = new ArrayList<>();

			while (rS.next()) {
				Cancion multimedia = new Cancion();
				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				multimedia.setDuracion(rS.getString("Duracion"));
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

	/**
	 * Descripción: Selecciona todos los atributos de la tabla Podcaster. Cada
	 * resultado lo registra en un objeto artista de tipo Podcaster y añade dicho
	 * objeto a un ArrayList de objetos de tipo Podcaster.
	 * 
	 * @return Devuelve el ArrayList podcasterArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Podcaster> conexionPodcaster() {

		String sentenciaSQL = "SELECT * FROM Podcaster;";

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

	/**
	 * Descripción: Selecciona todos los atributos de la tabla Podcast en función
	 * del IDPodcaster. Cada resultado lo registra en un objeto multimedia de tipo
	 * Podcast y añade dicho objeto a un ArrayList de objetos de tipo Podcast.
	 * 
	 * @param IDPodcaster int. Es el número con el que se identififca a un
	 *                    podcaster.
	 * @return Devuelve el ArrayList podcastsArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
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
				multimedia.setDuracion(rS.getString("Duracion"));
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

	/**
	 * Descripción: Selecciona todos los atributos de la tabla Playlist en función
	 * del IDCliente. Cada resultado lo registra en un objeto playlist de tipo
	 * Playlist y añade dicho objeto a un ArrayList de objetos de tipo Playlist.
	 * 
	 * @param usuario int. Es el número con el que se identififca a un usuario.
	 * @return Devuelve el ArrayList playlistsArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
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

	/**
	 * Descripción: Selecciona todos los atributos de la tabla PlaylistCanciones en función
	 * del IDList. Cada resultado lo registra en un objeto playlistCanciones de tipo
	 * Playlist y añade dicho objeto a un ArrayList de objetos de tipo Playlist.
	 * 
	 * @param i int. Es el número con el que se identififca a una Playlist.
	 * @return Devuelve el ArrayList playlistsArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Playlist> conexionPlaylistCanciones(int i) {
		String sentenciaSQL = "SELECT PlaylistCanciones.*, Audio.Nombre " + "FROM PlaylistCanciones "
				+ "INNER JOIN Audio ON PlaylistCanciones.IDAudio = Audio.IDAudio " + "WHERE IDList=" + i + ";";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Playlist> playlistsArrayList = new ArrayList<>();
			while (rS.next()) {
				Playlist playlistCanciones = new Playlist();
				playlistCanciones.setAudioID(rS.getInt("IDAudio"));
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

	public Cancion nuevaCancion(int IDCliente, int IDAudio, String timeStamp) {

		String sentenciaSQL = "SELECT * FROM Audio WHERE IDAudio='" + IDAudio + "';";

		try {

			Cancion multimedia = new Cancion();

			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			while (rS.next()) {

				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				multimedia.setDuracion(rS.getString("Duracion"));
				multimedia.setImagenMultimedia(rS.getString("Imagen"));
				// cancionesArrayList.add(multimedia);

			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return multimedia;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;

	}

	/**
	 * Descripción: Selecciona el IDAlbum y el Titulo de la tabla Album. Cada
	 * resultado lo registra en un objeto album de tipo Album y añade dicho objeto a
	 * un ArrayList de objetos de tipo Album.
	 * 
	 * @return Devuelve el ArrayList albumesArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
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

	/**
	 * Descripción: Selecciona el IDAudio y el Nombre de la tabla Audio cuando el
	 * atributo Tipo sea Cancion. Cada resultado lo registra en un objeto multimedia
	 * de tipo Cancion y añade dicho objeto a un ArrayList de objetos de tipo
	 * Cancion.
	 * 
	 * @return Devuelve el ArrayList cancionesArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
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
	 * Descripción: Selecciona el IDAudio y el Nombre de la tabla Audio cuando el
	 * atributo Tipo sea Podcast. Cada resultado lo registra en un objeto multimedia
	 * de tipo Podcast y añade dicho objeto a un ArrayList de objetos de tipo
	 * Podcast.
	 * 
	 * @return Devuelve el ArrayList podcastArrayList, que contiene los registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Podcast> conexionPodcastAdmin() {

		String sentenciaSQL = "SELECT IDAudio, Nombre FROM Audio WHERE Tipo = 'Podcast';";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery();

			ArrayList<Podcast> podcastArrayList = new ArrayList<>();

			while (rS.next()) {
				Podcast multimedia = new Podcast();
				multimedia.setNombreMultimedia(rS.getString("Nombre"));
				multimedia.setAudioID(rS.getInt("IDAudio"));
				podcastArrayList.add(multimedia);
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return podcastArrayList;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Selecciona el NumeroReproducciones de la tabla Estadisticas, el
	 * NombreArtistico de la tabla Musico y el Nombre de la tabla Audio, para
	 * después ordenarlos en función del NumeroReproducciones en orden descendente
	 * hasta un máximo de 9 resultados. Cada resultado lo registra en un objeto
	 * cancionMasEscuchada de tipo Cancion y añade dicho objeto a un ArrayList de
	 * objetos de tipo Cancion.
	 * 
	 * @return Devuelve el ArrayList masEscuchados, que contiene los 9 registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Cancion> conexionMasEscuchadas() {
		String sentenciaSQL = "SELECT Estadisticas.NumeroReproducciones, Musico.NombreArtistico, Audio.Nombre FROM Estadisticas INNER JOIN Cancion ON Estadisticas.IDAudio = Cancion.IDAudio INNER JOIN Album ON Cancion.IDAlbum = Album.IDAlbum INNER JOIN Musico ON Album.IDMusico = Musico.IDMusico INNER JOIN Audio ON Cancion.IDAudio = Audio.IDAudio WHERE Audio.Tipo = 'Cancion' ORDER BY NumeroReproducciones DESC LIMIT 9;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery(sentenciaSQL);

			ArrayList<Cancion> masEscuchados = new ArrayList<Cancion>();

			while (rS.next()) {
				Cancion cancionMasEscuchada = new Cancion();
				cancionMasEscuchada.setNombreMultimedia(rS.getString("Audio.Nombre"));
				cancionMasEscuchada.setNombreArtistico(rS.getString("Musico.NombreArtistico"));
				cancionMasEscuchada.setReproducciones(rS.getInt("Estadisticas.NumeroReproducciones"));
				masEscuchados.add(cancionMasEscuchada);
			}
			rS.close();
			cerrarConexionBD(pS, conexion);
			return masEscuchados;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Selecciona el NumeroMeGustas de la tabla Estadisticas, el
	 * NombreArtistico de la tabla Musico y el Nombre de la tabla Audio, para
	 * después ordenarlos en función del NumeroMeGustas en orden descendente hasta
	 * un máximo de 9 resultados. Cada resultado lo registra en un objeto
	 * cancionMasGustada de tipo Cancion y añade dicho objeto a un ArrayList de
	 * objetos de tipo Cancion.
	 * 
	 * @return Devuelve el ArrayList masGustadas, que contiene los 9 registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Cancion> conexionMasGustadas() {
		String sentenciaSQL = "SELECT Estadisticas.NumeroMeGustas, Musico.NombreArtistico, Audio.Nombre FROM Estadisticas INNER JOIN Cancion ON Estadisticas.IDAudio = Cancion.IDAudio INNER JOIN Album ON Cancion.IDAlbum = Album.IDAlbum INNER JOIN Musico ON Album.IDMusico = Musico.IDMusico INNER JOIN Audio ON Cancion.IDAudio = Audio.IDAudio WHERE Audio.Tipo = 'Cancion' ORDER BY NumeroMeGustas DESC LIMIT 9;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery(sentenciaSQL);

			ArrayList<Cancion> masGustadas = new ArrayList<Cancion>();

			while (rS.next()) {
				Cancion cancionMasGustada = new Cancion();
				cancionMasGustada.setNombreMultimedia(rS.getString("Audio.Nombre"));
				cancionMasGustada.setNombreArtistico(rS.getString("Musico.NombreArtistico"));
				cancionMasGustada.setNumeroMeGustas(rS.getInt("Estadisticas.NumeroMeGustas"));
				masGustadas.add(cancionMasGustada);
			}
			rS.close();
			cerrarConexionBD(pS, conexion);
			return masGustadas;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Selecciona el NumeroMeGustas de la tabla Estadisticas, el
	 * NombreArtistico de la tabla Podcaster y el Nombre de la tabla Audio, para
	 * después ordenarlos en función del NumeroMeGustas en orden descendente hasta
	 * un máximo de 9 resultados. Cada resultado lo registra en un objeto
	 * podcastMasGustado de tipo Podcast y añade dicho objeto a un ArrayList de
	 * objetos de tipo Podcast.
	 * 
	 * @return Devuelve el ArrayList masGustados, que contiene los 9 registros
	 *         obtenidos de la BD.
	 * @author grupo6
	 */
	public ArrayList<Podcast> conexionMasGustados() {
		String sentenciaSQL = "SELECT Estadisticas.NumeroMeGustas, Podcaster.NombreArtistico, Audio.Nombre FROM Estadisticas INNER JOIN Podcast ON Estadisticas.IDAudio = Podcast.IDAudio INNER JOIN Podcaster ON Podcast.IDPodcaster = Podcaster.IDPodcaster INNER JOIN Audio ON Podcast.IDAudio = Audio.IDAudio WHERE Audio.Tipo = 'Podcast' ORDER BY NumeroMeGustas DESC LIMIT 9;";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery(sentenciaSQL);

			ArrayList<Podcast> masGustados = new ArrayList<Podcast>();

			while (rS.next()) {
				Podcast podcastMasGustado = new Podcast();
				podcastMasGustado.setNombreMultimedia(rS.getString("Audio.Nombre"));
				podcastMasGustado.setNombreArtistico(rS.getString("Podcaster.NombreArtistico"));
				podcastMasGustado.setNumeroMeGustas(rS.getInt("Estadisticas.NumeroMeGustas"));
				masGustados.add(podcastMasGustado);
			}
			rS.close();
			cerrarConexionBD(pS, conexion);
			return masGustados;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	/**
	 * Descripción: Selecciona el IDAudio que ha sido insertado el último de la
	 * tabla Audio de la BD.
	 * 
	 * @return Devuelve una variable de tipo int con el IDAudio.
	 * @author grupo6
	 * @throws ConexionFallidaException
	 */
	public int conexionSelectIDAudio() throws ConexionFallidaException {
		String sentenciaSQL = "SELECT IDAudio FROM Audio ORDER BY IDAudio DESC LIMIT 1;";
		int idAudio = 0;

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);
			ResultSet rS = pS.executeQuery(sentenciaSQL);

			while (rS.next()) {
				idAudio = rS.getInt("Audio.IDAudio");
			}

			rS.close();
			cerrarConexionBD(pS, conexion);
			return idAudio;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new ConexionFallidaException();
		}
	}
}
