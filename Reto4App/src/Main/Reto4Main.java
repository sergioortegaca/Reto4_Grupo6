package Main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import Objetos.UsuarioFree;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import Objetos.*;
import java.awt.event.MouseAdapter;

public class Reto4Main extends JFrame {
	JDateChooser fechaNacimientoCalendar;
	SimpleDateFormat dateFormat;
	String user;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String driverBBDD = "jdbc:mysql";
	String servidorBBDD = "localhost";
	String puertoBBDD = "33060";
	String nombreBBDD = "reto4_grupo6";
	String usuarioBBDD = "mañana";
	String contrasenaBBDD = "elorrieta";

	String LinkBD = driverBBDD + "://" + servidorBBDD + ":" + puertoBBDD + "/" + nombreBBDD;
	String nombrePanel = "", timeStamp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reto4Main frame = new Reto4Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Reto4Main() {

		JTextField txtUsuario;
		JPasswordField pswContrasena;
		JTextField txtNombre, txtApellidos, txtUsuarioRegistro;
		JPasswordField pswCrearContrasena;

		// Variables de conexión con la BBDD

		Metodos metodos = new Metodos();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(230, 130, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		// COGER LA HORA ACTUAL PARA EL REGISTRO:
		timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "LayeredPane");
		layeredPane.setLayout(new CardLayout(0, 0));

		// ****************************************************************BIENVENIDA
		JPanel panelBienvenida = new JPanel();
		panelBienvenida.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelBienvenida, "Bienvenida");
		panelBienvenida.addMouseListener((MouseListener) new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				metodos.cambiarDePanel(layeredPane, "Login");
				System.out.println(LinkBD);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		panelBienvenida.setLayout(null);
		JLabel lblBienvenido = new JLabel("Bienvenido a");
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenido.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblBienvenido.setBounds(254, 130, 365, 131);
		panelBienvenida.add(lblBienvenido);

		JLabel lblRhythmicity = new JLabel("Rhythmicity");
		lblRhythmicity.setHorizontalAlignment(SwingConstants.CENTER);
		lblRhythmicity.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblRhythmicity.setBounds(236, 180, 402, 160);
		panelBienvenida.add(lblRhythmicity);

		// ****************************************************************INICIO DE
		// SESIÓN
		JPanel panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelLogin, "Login");
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioDeSesion.setBounds(0, 80, 874, 33);
		panelLogin.add(lblInicioDeSesion);

		JLabel lblUsuarioLogin = new JLabel("Usuario:");
		lblUsuarioLogin.setBounds(321, 172, 176, 14);
		panelLogin.add(lblUsuarioLogin);

		JLabel lblContrasenaLogin = new JLabel("Contraseña:");
		lblContrasenaLogin.setBounds(297, 227, 176, 14);
		panelLogin.add(lblContrasenaLogin);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(399, 170, 148, 20);
		panelLogin.add(txtUsuario);

		txtUsuario.setColumns(10);
		pswContrasena = new JPasswordField();
		pswContrasena.setBounds(399, 225, 148, 20);
		panelLogin.add(pswContrasena);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sentencia = "Select Usuario, Contrasena from Cliente where Usuario=? and Contrasena=?";
				String textOk = "Has iniciado sesión correctamente";
				String textNot = "Creedenciales incorrectas";
				user = txtUsuario.getText();
				String pass = new String(pswContrasena.getPassword());

				try {
					try {

						Class.forName(DRIVER);

					} catch (ClassNotFoundException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // Cargamos el Driver para mysql y Abrimos la conexión a BBDD
					Connection connection = (Connection) DriverManager.getConnection(LinkBD, usuarioBBDD,
							contrasenaBBDD);
					PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);
					st.setString(1, user);
					st.setString(2, pass);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, textOk);
						metodos.cambiarDePanel(layeredPane, "Menu");

					} else {

						JOptionPane.showMessageDialog(null, textNot);
					}
					txtUsuario.setText("");
					pswContrasena.setText("");
					rs.close();
					st.close();
					connection.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		});

		btnEntrar.setBounds(457, 283, 142, 23);
		panelLogin.add(btnEntrar);

		JButton btnCrear = new JButton("Crear nuevo usuario\r\n");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, "Registro");

			}
		});
		btnCrear.setBounds(255, 283, 159, 23);
		panelLogin.add(btnCrear);

		// ****************************************************************REGISTRO
		JPanel panelRegistro = new JPanel();
		panelRegistro.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelRegistro, "Registro");
		panelRegistro.setLayout(null);
		nombrePanel = "Login";
		metodos.botonAtras(layeredPane, nombrePanel, panelRegistro);

		JLabel lblTituloCrear = new JLabel("Crear Usuario");
		lblTituloCrear.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTituloCrear.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCrear.setBounds(0, 40, 874, 33);
		panelRegistro.add(lblTituloCrear);

		txtNombre = new JTextField();
		txtNombre.setBounds(412, 125, 175, 20);
		panelRegistro.add(txtNombre);

		txtNombre.setColumns(10);
		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(412, 156, 175, 20);
		panelRegistro.add(txtApellidos);

		txtUsuarioRegistro = new JTextField();
		txtUsuarioRegistro.setColumns(10);
		txtUsuarioRegistro.setBounds(412, 187, 175, 20);
		panelRegistro.add(txtUsuarioRegistro);
		pswCrearContrasena = new JPasswordField();
		pswCrearContrasena.setBounds(412, 218, 175, 20);
		panelRegistro.add(pswCrearContrasena);
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(274, 125, 148, 20);
		panelRegistro.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(274, 156, 148, 20);
		panelRegistro.add(lblApellidos);

		JLabel lblUsuarioRegistro = new JLabel("Nombre de usuario:");
		lblUsuarioRegistro.setBounds(274, 187, 148, 20);
		panelRegistro.add(lblUsuarioRegistro);

		JLabel lblContrasenaRegistro = new JLabel("Contraseña:");
		lblContrasenaRegistro.setBounds(274, 218, 148, 20);
		panelRegistro.add(lblContrasenaRegistro);

		JLabel lblNacimiento = new JLabel("Fecha de nacimiento:");
		lblNacimiento.setBounds(274, 250, 148, 20);
		panelRegistro.add(lblNacimiento);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// OBJETOS
				int clienteID = 0;
				String nombre = txtNombre.getText(), apellido = txtApellidos.getText(),
						usuario = txtUsuarioRegistro.getText(),
						contrasena = new String(pswCrearContrasena.getPassword()),
						fechaNacimiento = dateFormat.format(fechaNacimientoCalendar.getDate()),
						fechaRegistro = timeStamp;
				UsuarioFree UsuarioNuevo = new UsuarioFree(clienteID, nombre, apellido, usuario, contrasena,
						fechaNacimiento, fechaRegistro);

				// if (txtNombre.getText().length() > 0 && txtNombre.getText().length() < 13) {
				// if (txtApellidos.getText().length() > 0 && txtApellidos.getText().length() <
				// 30) {
				// if (pswCrearContrasena.getPassword().length > 7
				// && pswCrearContrasena.getPassword().length < 16) {
				// Código para añadir datos de los clientes a la base de datos

				try {
					Connection conexion = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
					String sql = "INSERT INTO cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStatement = conexion.prepareStatement(sql);

					preparedStatement.setString(2, UsuarioNuevo.getNombre());
					preparedStatement.setString(3, UsuarioNuevo.getApellido());
					preparedStatement.setString(4, UsuarioNuevo.getUsuario());
					preparedStatement.setString(5, UsuarioNuevo.getContrasena());
					preparedStatement.setString(6, UsuarioNuevo.getFechaNacimiento());
					preparedStatement.setString(7, UsuarioNuevo.getFechaRegistro());
					//preparedStatement.setString(8, UsuarioNuevo.Tipo);
					preparedStatement.executeUpdate();
					preparedStatement.close();
					conexion.close();
				} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
				JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente");
				txtNombre.setText("");
				txtApellidos.setText("");
				txtUsuarioRegistro.setText("");
				pswCrearContrasena.setText("");
				metodos.cambiarDePanel(layeredPane, "Login");
				// } else {
				// JOptionPane.showMessageDialog(null, "La Contraseña debe tener entre 8 y 16
				// caracteres");
				// }
				// } else {
				// JOptionPane.showMessageDialog(null,
				// "Los apellidos no deben superar los 30 caracteres y el campo no debe estar
				// vacío");
				// }
				// } else {
				// JOptionPane.showMessageDialog(null,
				// "El nombre no debe superar los 13 caracteres y el campo no debe estar
				// vacío");
				// }
			}
		});
		btnCrearUsuario.setBounds(376, 296, 122, 23);
		panelRegistro.add(btnCrearUsuario);
		JPanel panelPerfil = new JPanel();
		panelPerfil.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelPerfil, "Perfil");
		panelPerfil.setLayout(null);
		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(412, 248, 175, 20);
		panelRegistro.add(fechaNacimientoCalendar);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		year = year - 7;
		String dateString = "1905-01-01";
		String maxString = year + "-12-31";
		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(dateString));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// ****************************************************************MENÚ
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMenu, "Menu");
		panelMenu.setLayout(null);
		metodos.botonPerfil(layeredPane, panelMenu, user);
		nombrePanel = "Login";
		metodos.botonAtras(layeredPane, nombrePanel, panelMenu);
		// ****************************************************************DESCUBRIR
		// MÚSICA
		JButton btnDescubrirMusica = new JButton("Descubrir música");
		btnDescubrirMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, "DescubrirMusica");
			}
		});
		btnDescubrirMusica.setBounds(304, 171, 265, 23);
		panelMenu.add(btnDescubrirMusica);
		// ****************************************************************DESCUBRIR
		// PODCASTS
		JButton btnDescubrirPodcasts = new JButton("Descubrir podcasts");
		btnDescubrirPodcasts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, "DescubrirPodcasts");
			}
		});
		btnDescubrirPodcasts.setBounds(304, 219, 265, 23);
		panelMenu.add(btnDescubrirPodcasts);
		// ****************************************************************MIS PLAYLISTS
		JButton btnMisPlayLists = new JButton("Mis PlayLists");
		btnMisPlayLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, "MisPlaylists");
			}
		});
		btnMisPlayLists.setBounds(304, 265, 265, 23);
		panelMenu.add(btnMisPlayLists);
		JLabel lblMenu = new JLabel(metodos.bienvenidaMenu());
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMenu.setBounds(262, 59, 349, 83);
		panelMenu.add(lblMenu);

		JPanel panelDescubrirMusica = new JPanel();
		panelDescubrirMusica.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelDescubrirMusica, "DescubrirMusica");
		panelDescubrirMusica.setLayout(null);
		metodos.botonPerfil(layeredPane, panelDescubrirMusica, user);
		nombrePanel = "Menu";
		metodos.botonAtras(layeredPane, nombrePanel, panelDescubrirMusica);

		JLabel lblListaDeArtistas = new JLabel("Lista de artistas");
		lblListaDeArtistas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArtistas.setBounds(328, 61, 217, 14);
		panelDescubrirMusica.add(lblListaDeArtistas);

		JList list = new JList();
		DefaultListModel<String> modeloLista = new DefaultListModel<>();

		ArrayList<Musico> artistas = artistasBD();

		if (artistas != null) {
			/*
			 * for(int i =0; i<artistas.size();i++) {
			 * modeloLista.addElement(artistas.get(i).getNombreArtistico());
			 * 
			 * }
			 */
			for (Musico musico : artistas) {
				modeloLista.addElement(musico.getNombreArtistico());
			}

		} else {

			modeloLista.addElement("Error 1");
			modeloLista.addElement("Error 2");
			modeloLista.addElement("Error 3");
		}
		list.setModel(modeloLista);
		list.setBounds(246, 120, 382, 295);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = list.getSelectedIndex();
				System.out.println(modeloLista.getElementAt(index));
			}
		});

		panelDescubrirMusica.add(list);

		JPanel panelDescubirPodcasts = new JPanel();
		panelDescubirPodcasts.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelDescubirPodcasts, "DescubrirPodcasts");
		panelDescubirPodcasts.setLayout(null);
		metodos.botonPerfil(layeredPane, panelDescubirPodcasts, user);
		nombrePanel = "Menu";
		metodos.botonAtras(layeredPane, nombrePanel, panelDescubirPodcasts);

		JPanel panelMisPlaylists = new JPanel();
		panelMisPlaylists.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMisPlaylists, "MisPlaylists");
		panelMisPlaylists.setLayout(null);
		metodos.botonPerfil(layeredPane, panelMisPlaylists, user);
		nombrePanel = "Menu";
		metodos.botonAtras(layeredPane, nombrePanel, panelMisPlaylists);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.control);
		textArea.setBounds(10, 76, 550, 364);
		panelMisPlaylists.add(textArea);

		JButton btnNewButton = new JButton("Nueva PlayList");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnNewButton.setBounds(639, 76, 170, 35);
		panelMisPlaylists.add(btnNewButton);
		JButton btnNuevaPlaylist = new JButton("Eliminar");
		btnNuevaPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnNuevaPlaylist.setBounds(639, 146, 170, 35);
		panelMisPlaylists.add(btnNuevaPlaylist);

		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(639, 286, 170, 35);
		panelMisPlaylists.add(btnImportar);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.setBounds(639, 216, 170, 35);
		panelMisPlaylists.add(btnExportar);

		JPanel panelArtista = new JPanel();
		layeredPane.add(panelArtista, "Artista");
		metodos.botonPerfil(layeredPane, panelArtista, user);
		nombrePanel = "MisPlaylists";
		metodos.botonAtras(layeredPane, nombrePanel, panelArtista);
		panelArtista.setLayout(null);

		JPanel panelAlbum = new JPanel();
		layeredPane.add(panelAlbum, "Album");
		metodos.botonPerfil(layeredPane, panelAlbum, user);
		nombrePanel = "Artista";
		metodos.botonAtras(layeredPane, nombrePanel, panelAlbum);
		panelAlbum.setLayout(null);

		JPanel panelReproduccion = new JPanel();
		layeredPane.add(panelReproduccion, "Reproduccion");
		metodos.botonPerfil(layeredPane, panelReproduccion, user);
		nombrePanel = "Album";
		metodos.botonAtras(layeredPane, nombrePanel, panelReproduccion);
		panelReproduccion.setLayout(null);
	}

	public ArrayList<Musico> artistasBD() {
		String sentencia = "SELECT * FROM Musico;";

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
}
