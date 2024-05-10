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
   	 try {
   		 String sentenciaSQL = "Select Usuario, Contrasena, IDCliente from Cliente where Usuario=? and Contrasena=?";
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
   		 System.out.println("SQLException: " + sqlException.getMessage());
   		 System.out.println("SQLState: " + sqlException.getSQLState());
   		 System.out.println("VendorError: " + sqlException.getErrorCode());
   		 JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar iniciar sesión");
   	 }
    }

    public void conexionRegistro(UsuarioFree UsuarioNuevo, String login, JLayeredPane layeredPane) {
   	 try {
   		 String sentenciaSQL = "INSERT IGNORE INTO Cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
   		 Connection conexion = conexionBD();
   		 PreparedStatement pS = conexion.prepareStatement(sentenciaSQL);
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
   	 } catch (SQLException ex) {
   		 System.out.println("SQLException: " + ex.getMessage());
   		 System.out.println("SQLState: " + ex.getSQLState());
   		 System.out.println("VendorError: " + ex.getErrorCode());
   		 JOptionPane.showMessageDialog(null, "Ha ocurrido un  error al registrar el ususario en la base de datos");
   	 }
    }

    public ArrayList<Musico> conexionMusico() {

   	 String sentencia = "SELECT DISTINCT * FROM Musico;";

   	 try {
   		 try {
   			 Class.forName(DRIVER);
   		 } catch (ClassNotFoundException e1) {
   			 e1.printStackTrace();
   		 }
   		 Connection connection = (Connection) DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
   		 PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);
   		 ResultSet rs = st.executeQuery();

   		 ArrayList<Musico> musicoArrayList = new ArrayList<Musico>();
   		 while (rs.next()) {
   			 Musico artista = new Musico();
   			 artista.setArtistaID(rs.getInt("IDMusico"));
   			 artista.setNombreArtistico(rs.getString("NombreArtistico"));
   			 artista.setImagenArtista(rs.getString("Imagen"));
   			 musicoArrayList.add(artista);
   		 }

   		 rs.close();
   		 st.close();
   		 connection.close();
   		 return musicoArrayList;

   	 } catch (SQLException sqlException) {
   		 sqlException.printStackTrace();
   	 }
   	 return null;
    }

    public ArrayList<Album> conexionAlbum(String artistaSeleccionado) {

   	 String sentencia = "SELECT DISTINCT Album.IDAlbum, Album.Titulo, Album.Ano, Album.Genero, Album.Genero, Album.Imagen FROM Album "
   			 + "JOIN Musico ON Album.IDMusico = Musico.IDMusico " + "WHERE Musico.NombreArtistico = '"
   			 + artistaSeleccionado + "';";

   	 try {
   		 Class.forName(DRIVER);
   		 Connection connection = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
   		 PreparedStatement st = connection.prepareStatement(sentencia);
   		 ResultSet rs = st.executeQuery();

   		 ArrayList<Album> albumesArrayList = new ArrayList<>();
   		 while (rs.next()) {
   			 Album album = new Album();
   			 album.setAlbumID(rs.getInt("IDAlbum"));
   			 album.setTituloAlbum(rs.getString("Titulo"));
   			 album.setAnoAlbum(rs.getDate("Ano"));
   			 album.setGeneroAlbum(rs.getString("Genero"));
   			 album.setImagenAlbum(rs.getString("Imagen"));
   			 albumesArrayList.add(album);
   		 }

   		 rs.close();
   		 st.close();
   		 connection.close();
   		 return albumesArrayList;

   	 } catch (SQLException | ClassNotFoundException sqlException) {
   		 sqlException.printStackTrace();
   	 }
   	 return null;
    }

    public ArrayList<Podcaster> conexionPodcaster() {

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

   		 ArrayList<Podcaster> podcasterArrayList = new ArrayList<Podcaster>();
   		 while (rs.next()) {
   			 Podcaster artista = new Podcaster();
   			 artista.setArtistaID(rs.getInt("IDPodcaster"));
   			 artista.setNombreArtistico(rs.getString("NombreArtistico"));
   			 podcasterArrayList.add(artista);

   			 rs.close();
   			 st.close();
   			 connection.close();
   			 return podcasterArrayList;
   		 }

   	 } catch (SQLException sqlException) {
   		 sqlException.printStackTrace();
   	 }
   	 return null;
    }

    public ArrayList<Cancion> conexionCancion(String albumSeleccionado) {

   	 String sentencia = " SELECT Audio.Nombre, Audio.IDAudio, Audio.Duracion, Audio.Imagen " + " FROM Audio "
   			 + " JOIN Cancion ON Audio.IDAudio = Cancion.IDAudio "
   			 + " JOIN Album ON Cancion.IDAlbum = Album.IDAlbum " + " WHERE Album.Titulo = '" + albumSeleccionado
   			 + "';";

   	 try {
   		 Class.forName(DRIVER);
   		 Connection connection = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
   		 PreparedStatement st = connection.prepareStatement(sentencia);
   		 ResultSet rs = st.executeQuery();

   		 ArrayList<Cancion> cancionesArrayList = new ArrayList<>();

   		 while (rs.next()) {
   			 Cancion multimedia = new Cancion();
   			 multimedia.setNombreMultimedia(rs.getString("Nombre"));
   			 multimedia.setAudioID(rs.getInt("IDAudio"));
   			 multimedia.setDuracion(rs.getTime("Duracion"));
   			 // multimedia.settipoMultimedia(rs.getString("Tipo"));
   			 multimedia.setImagenMultimedia(rs.getString("Imagen"));

   			 cancionesArrayList.add(multimedia);

   		 }

   		 rs.close();
   		 st.close();
   		 connection.close();
   		 return cancionesArrayList;

   	 } catch (SQLException | ClassNotFoundException sqlException) {
   		 sqlException.printStackTrace();
   	 }
   	 return null;
    }

    /*
     * public ArrayList<Musico> getBDaRTISTAS(String SQL) {
     *
     * String sentencia = SQL; try { try { Class.forName(DRIVER);
     *
     * } catch (ClassNotFoundException e1) {
     *
     * e1.printStackTrace(); } // Cargamos el Driver para mysql y Abrimos la
     * conexión a BBDD Connection connection = (Connection)
     * DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
     * PreparedStatement st = (PreparedStatement)
     * connection.prepareStatement(sentencia); ResultSet rs = st.executeQuery();
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


