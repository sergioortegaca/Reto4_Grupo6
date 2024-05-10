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
	final String servidorBBDD = "rhythmicity.duckdns.org";
	final String puertoBBDD = "3306";
	final String nombreBBDD = "reto4_grupo6";
	final String usuarioBBDD = "grupo6";
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

	public void conexionLogin(String textOk, String user, String pass, String menu, JLayeredPane layeredPane,
			UsuarioFree Usuario) {
		String sentenciaSQLSQL = "Select Usuario, Contrasena, IDCliente from Cliente where Usuario=? and Contrasena=?";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQLSQL);
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

	public void conexionRegistro(UsuarioFree UsuarioNuevo, String login, JLayeredPane layeredPane) {
		String sentenciaSQLSQL = "INSERT IGNORE INTO Cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = conexion.prepareStatement(sentenciaSQLSQL);
			pS.setString(1, UsuarioNuevo.getNombre());
			pS.setString(2, UsuarioNuevo.getApellido());
			pS.setString(3, UsuarioNuevo.getUsuario());
			pS.setString(4, UsuarioNuevo.getContrasena());
			pS.setString(5, UsuarioNuevo.getFechaNacimiento());
			pS.setString(6, UsuarioNuevo.getFechaRegistro());
			pS.setString(7, "Free");
			pS.executeUpdate();
			JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente");
			cerrarConexionBD(pS, conexion);
			metodos.cambiarDePanel(layeredPane, login);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un  error al registrar el ususario en la base de datos");
		}
	}

	public ArrayList<Musico> conexionMusico() {

		String sentenciaSQL = "SELECT DISTINCT * FROM Musico;";

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

	public ArrayList<Cancion> conexionCancion(String albumSeleccionado) {

		String sentenciaSQL = "SELECT Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen,"
				+ "       COUNT(Reproducciones.IDAudio) AS total_reproducciones " + "FROM Audio "
				+ "JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio " + "JOIN Album ON Cancion.IDAlbum = Album.IDAlbum "
				+ "LEFT JOIN Reproducciones ON Audio.IDAudio = Reproducciones.IDAudio " + "WHERE Album.Titulo = '"
				+ albumSeleccionado + "' GROUP BY Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen;";

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
				multimedia.setReproducciones(rS.getInt("total_reproducciones"));
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

	public void reproduccionesBBDD() {

		String sentenciaSQL = "INSERT IGNORE INTO Reproducciones (IDCliente, IDAudio, FechaReproduccion) VALUES (?, ?, ?)";

		try {
			Connection conexion = conexionBD();
			PreparedStatement pS = (PreparedStatement) conexion.prepareStatement(sentenciaSQL);

			/*
			 * preparedStatement.setString(1, UsuarioNuevo.getNombre());
			 * preparedStatement.setString(2, UsuarioNuevo.getApellido());
			 * preparedStatement.setString(3, UsuarioNuevo.getUsuario());
			 */
			pS.executeUpdate();
			cerrarConexionBD(pS, conexion);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un  error al registrar las estadísticas.");
		}

	}

	/*
	 * public ArrayList<Musico> getBDaRTISTAS(String SQL) {
	 * 
	 * String sentenciaSQL = SQL; try { try { Class.forName(DRIVER);
	 * 
	 * } catch (ClassNotFoundException e1) {
	 * 
	 * e1.printStackTrace(); } // Cargamos el Driver para mysql y Abrimos la
	 * conexión a BBDD Connection connection = (Connection)
	 * DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
	 * PreparedStatement st = (PreparedStatement)
	 * connection.prepareStatement(sentenciaSQL); ResultSet rs = st.executeQuery();
	 * 
	 * artistas = new ArrayList<Musico>(); while (rs.next()) { Musico artista = new
	 * Musico(); artista.setArtistaID(rs.getInt("IDMusico"));
	 * artista.setNombreArtistico(rs.getString("NombreArtistico"));
	 * artista.setImagenArtista(rs.getString("Imagen")); artistas.add(artista); }
	 * 
	 * rs.close(); st.close(); connection.close(); return artistas;
	 * 
	 * } catch (SQLException sqlException) { sqlException.printStackTrace(); }
	 * return null; }
	 */
}
