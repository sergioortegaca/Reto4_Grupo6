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
	final String contrasenaBBDD = "julioespiaeritreo";
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

	public int conexionSelectIDAudio() {
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
		}
		return idAudio;
	}
}
