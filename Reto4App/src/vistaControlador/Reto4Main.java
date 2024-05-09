package vistaControlador;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.toedter.calendar.JTextFieldDateEditor;
import modelo.*;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;

public class Reto4Main extends JFrame {
	private static final long serialVersionUID = 1L;

	Metodos metodos = new Metodos();

	JPanel contentPane, panelBienvenida, panelLogin, panelRegistro, panelPerfil, panelMenuAdmin, panelMenuGestionar,
			panelEstadisticas, panelEditar, panelDescubrirMusica, panelArtista, panelAlbum, panelReproduccion,
			panelDescubrirPodcasts, panelMisPlaylists, panelMenu, panelMenuEditar, panelModificarYAnadir;

	JLayeredPane layeredPane;

	JTextField txtUsuario, txtNombre, txtApellidos, txtUsuarioRegistro;

	JPasswordField pswContrasena, pswCrearContrasena;

	JDateChooser fechaNacimientoCalendar;
	SimpleDateFormat dateFormat;

	JTextArea textAreaInfoCancion = new JTextArea("");

	String user = "", timeStamp;
	String albumTit = "", albumAno = "", albumGen = "";

	// Variables de conexión con la BBDD
	String driverBBDD = "jdbc:mysql";
	String servidorBBDD = "rhythmicity.duckdns.org";
	String puertoBBDD = "3306";
	String nombreBBDD = "reto4_grupo6";
	String usuarioBBDD = "grupo6";
	String contrasenaBBDD = "julioespiaeritreo";
	String DRIVER = "com.mysql.cj.jdbc.Driver";

	// Conexión final BBDD
	String LinkBD = driverBBDD + "://" + servidorBBDD + ":" + puertoBBDD + "/" + nombreBBDD;

	// NOMBRES PANELES
	String bienvenida = "bienvenida", login = "login", registro = "registro", menu = "menu", perfil = "perfil",
			menuAdmin = "menuAdmin", descubrirMusica = "descubrirMusica", artistaPanel = "artista",
			albumPanel = "album", reproduccion = "reproduccion", descubrirPodcasts = "descubrirPodcasts",
			misPlayLists = "misPlayLists", estadisticas = "estadisticas", editar = "editar",
			menuGestionar = "menugestionar", modificarYAnadir = "modificarYAnadir";

	// VARIABLES PARA LOS SWITCH DE LOS PANELES DE ADMIN
	final String swMusica = "swMusica", swPodcasts1 = "swPodcasts1", swEstadisticas = "swEstadisticas",
			swArtistas = "swArtistas", swAlbumes = "swAlbumes", swCanciones = "swCanciones",
			swPodcasters = "swPodcasters", swPodcasts2 = "swPodcasts2",
			swCancionesMasGustadas = "swCancionesMasGustadas", swPodcastMasGustados = "swPodcastMasGustados",
			swMasEscuchados = "swMasEscuchados", swTopPlaylist = "swTopPlaylist";

	// VARIABLES BOTONES DE CAMBIAR DE CANCIÓN
	int atras = 0, numCancion = 0;
	Album albumSeleccionado;
	String podcasterSeleccionado = "";
	Cancion cancionSeleccionada;
	Musico artistaSeleccionado;

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

	public Reto4Main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(230, 130, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "LayeredPane");
		layeredPane.setLayout(new CardLayout(0, 0));

		// BIENVENIDA
		crearPanelBienvenida();

		// INICIO DE SESIÓN
		/**
		 * Dentro del metodo creador del incio de sesión se encuentra otro metodo el
		 * cual crea los demás paneles de la aplicación para que así los botones como el
		 * botón del perfil muestre el nombre de usuario de la persona que esté usando
		 * el programa.
		 */
		crearPanelLogin();

		// REGISTRO
		crearPanelRegistro();

		// Está la creación de paneles main ahí para poder acceder desde el designer.
		// crearPanelesMain();
	}

	private void crearPanelesMain() {

		// PERFIL
		crearPanelPerfil();

		// MENÚ
		crearPanelMenu();

		// DESCUBRIR MUSICA
		crearPanelDescubrirMusica();

		// DESCUBRIR PODCASTS
		crearPanelDescubrirPodcasts();

		// MIS PLAYLISTS
		crearPanelMisPlaylists();

	}

	private void crearPanelBienvenida() {

		panelBienvenida = new JPanel();
		panelBienvenida.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelBienvenida, bienvenida);
		panelBienvenida.addMouseListener((MouseListener) new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				metodos.cambiarDePanel(layeredPane, login);
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

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void crearPanelLogin() {
		panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelLogin, login);
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioDeSesion.setBounds(0, 80, 874, 33);
		panelLogin.add(lblInicioDeSesion);

		JLabel lblUsuarioLogin = new JLabel("Usuario:");
		lblUsuarioLogin.setBounds(292, 195, 109, 14);
		panelLogin.add(lblUsuarioLogin);

		JLabel lblContrasenaLogin = new JLabel("Contraseña:");
		lblContrasenaLogin.setBounds(292, 235, 109, 14);
		panelLogin.add(lblContrasenaLogin);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Cliente", "Administrador" }));
		comboBox.setBounds(399, 146, 148, 22);
		panelLogin.add(comboBox);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(399, 192, 148, 20);
		panelLogin.add(txtUsuario);

		pswContrasena = new JPasswordField();
		pswContrasena.setBounds(399, 232, 148, 20);
		panelLogin.add(pswContrasena);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] contrasenaAdmin = { '1', '2', '3' };
				String textOk = "Has iniciado sesión correctamente";
				String textNot = "Creedenciales incorrectas";
				user = txtUsuario.getText();

				if (comboBox.getSelectedItem().toString().equals("Administrador")) {
					if (txtUsuario.getText().equals("admin")
							&& Arrays.equals(pswContrasena.getPassword(), contrasenaAdmin)) {
						JOptionPane.showMessageDialog(null, textOk);
						crearPanelMenuAdmin();
						metodos.cambiarDePanel(layeredPane, menuAdmin);
					} else {
						JOptionPane.showMessageDialog(null, textNot);
					}
					txtUsuario.setText("");
					pswContrasena.setText("");
				} else if (comboBox.getSelectedItem().toString().equals("Cliente")) {
					String sentencia = "Select Usuario, Contrasena from Cliente where Usuario=? and Contrasena=?";
					String pass = new String(pswContrasena.getPassword());

					try {
						try {
							Class.forName(DRIVER);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						Connection connection = (Connection) DriverManager.getConnection(LinkBD, usuarioBBDD,
								contrasenaBBDD);
						PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);
						st.setString(1, user);
						st.setString(2, pass);
						ResultSet rs = st.executeQuery();
						if (rs.next()) {
							JOptionPane.showMessageDialog(null, textOk);
							crearPanelesMain();
							metodos.cambiarDePanel(layeredPane, menu);
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
			}
		});

		btnEntrar.setBounds(457, 283, 142, 23);
		panelLogin.add(btnEntrar);

		JButton btnCrear = new JButton("Crear nuevo usuario");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, registro);

			}
		});
		btnCrear.setBounds(255, 283, 159, 23);
		panelLogin.add(btnCrear);

		JLabel lblTipoInicio = new JLabel("Tipo de usuario:");
		lblTipoInicio.setBounds(292, 150, 109, 14);
		panelLogin.add(lblTipoInicio);
	}

	private void crearPanelRegistro() {
		panelRegistro = new JPanel();
		panelRegistro.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelRegistro, registro);
		panelRegistro.setLayout(null);
		metodos.botonAtras(layeredPane, login, panelRegistro);

		JLabel lblTituloCrear = new JLabel("Crear Usuario");
		lblTituloCrear.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTituloCrear.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCrear.setBounds(0, 40, 874, 33);
		panelRegistro.add(lblTituloCrear);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(412, 125, 175, 20);
		panelRegistro.add(txtNombre);

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

		metodos.createLabel("Nombre:", 274, 125, 148, 20, panelRegistro);
		metodos.createLabel("Apellidos:", 274, 156, 148, 20, panelRegistro);
		metodos.createLabel("Nombre de usuario:", 274, 187, 148, 20, panelRegistro);
		metodos.createLabel("Contraseña:", 274, 218, 148, 20, panelRegistro);
		metodos.createLabel("Fecha de nacimiento:", 274, 250, 148, 20, panelRegistro);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(412, 248, 175, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);
		panelRegistro.add(fechaNacimientoCalendar);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR) - 12;
		String dateString = "1905-01-01";
		String maxString = year + "-12-31";

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(dateString));

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// COGER LA HORA ACTUAL PARA EL REGISTRO:
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

				// System.out.println(dateFormat.format(fechaNacimientoCalendar.getDate()));

				UsuarioFree UsuarioNuevo = new UsuarioFree();
				UsuarioNuevo.setNombre(txtNombre.getText());
				UsuarioNuevo.setApellido(txtApellidos.getText());
				UsuarioNuevo.setUsuario(txtUsuarioRegistro.getText());
				UsuarioNuevo.setContrasena(new String(pswCrearContrasena.getPassword()));
				UsuarioNuevo.setFechaNacimiento(dateFormat.format(fechaNacimientoCalendar.getDate()));
				UsuarioNuevo.setFechaRegistro(timeStamp);

				if (UsuarioNuevo.getNombre().length() <= 30 && UsuarioNuevo.getNombre().length() >= 1
						&& UsuarioNuevo.getApellido().length() <= 30 && UsuarioNuevo.getApellido().length() >= 1
						&& UsuarioNuevo.getUsuario().length() <= 30 && UsuarioNuevo.getUsuario().length() >= 1
						&& UsuarioNuevo.getContrasena().length() <= 30 && UsuarioNuevo.getContrasena().length() >= 1) {
					try {
						Connection conexion = DriverManager.getConnection(LinkBD, usuarioBBDD, contrasenaBBDD);
						String sql = "INSERT IGNORE INTO Cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement preparedStatement = conexion.prepareStatement(sql);

						preparedStatement.setString(1, UsuarioNuevo.getNombre());
						preparedStatement.setString(2, UsuarioNuevo.getApellido());
						preparedStatement.setString(3, UsuarioNuevo.getUsuario());
						preparedStatement.setString(4, UsuarioNuevo.getContrasena());
						preparedStatement.setString(5, UsuarioNuevo.getFechaNacimiento());
						preparedStatement.setString(6, UsuarioNuevo.getFechaRegistro());
						preparedStatement.setString(7, "Free");
						preparedStatement.executeUpdate();
						preparedStatement.close();
						conexion.close();

						JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente");
						metodos.cambiarDePanel(layeredPane, login);
					} catch (SQLException ex) {
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
						JOptionPane.showMessageDialog(null,
								"Ha ocurrido un  error al registrar el ususario en la base de datos");
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres");
				}
				txtNombre.setText("");
				txtApellidos.setText("");
				txtUsuarioRegistro.setText("");
				pswCrearContrasena.setText("");
			}
		});
		btnCrearUsuario.setBounds(376, 296, 122, 23);
		panelRegistro.add(btnCrearUsuario);

	}

	private void crearPanelPerfil() {
		panelPerfil = new JPanel();
		panelPerfil.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelPerfil, perfil);
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

	}

	private void crearPanelMenu() {

		panelMenu = new JPanel();
		panelMenu.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMenu, menu);
		panelMenu.setLayout(null);

		metodos.botonPerfil(layeredPane, panelMenu, user);
		metodos.botonAtras(layeredPane, login, panelMenu);

		JLabel lblMenu = new JLabel(metodos.bienvenidaMenu());
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMenu.setBounds(262, 59, 349, 83);
		panelMenu.add(lblMenu);

		// DESCUBRIR MÚSICA
		JButton btnDescubrirMusica = new JButton("Descubrir música");
		btnDescubrirMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, descubrirMusica);
			}
		});
		btnDescubrirMusica.setBounds(304, 171, 265, 23);
		panelMenu.add(btnDescubrirMusica);

		// DESCUBRIR PODCASTS
		JButton btnDescubrirPodcasts = new JButton("Descubrir podcasts");
		btnDescubrirPodcasts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, descubrirPodcasts);
			}
		});
		btnDescubrirPodcasts.setBounds(304, 219, 265, 23);
		panelMenu.add(btnDescubrirPodcasts);

		// MIS PLAYLISTS
		JButton btnMisPlayLists = new JButton("Mis PlayLists");
		btnMisPlayLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, misPlayLists);
			}
		});
		btnMisPlayLists.setBounds(304, 265, 265, 23);
		panelMenu.add(btnMisPlayLists);

	}

	private void crearPanelDescubrirMusica() {

		panelDescubrirMusica = new JPanel();
		panelDescubrirMusica.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelDescubrirMusica, descubrirMusica);
		panelDescubrirMusica.setLayout(null);

		// Botones
		metodos.botonPerfil(layeredPane, panelDescubrirMusica, user);
		metodos.botonAtras(layeredPane, menu, panelDescubrirMusica);

		JLabel lblListaDeArtistas = new JLabel("Lista de Artistas");
		lblListaDeArtistas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArtistas.setBounds(328, 61, 217, 14);
		panelDescubrirMusica.add(lblListaDeArtistas);

		JList<String> list = new JList<String>();
		list.setBackground(SystemColor.menu);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		DefaultListModel<String> modeloLista = new DefaultListModel<>();

		ArrayList<Musico> artistas = metodos.artistasBD(DRIVER, LinkBD, usuarioBBDD, contrasenaBBDD);

		if (artistas != null) {
			for (Musico musico : artistas) {
				modeloLista.addElement(musico.getNombreArtistico());
			}
		} else {
			modeloLista.addElement("No se han encontrado artistas disponibles");
		}
		list.setModel(modeloLista);
		list.setBounds(246, 120, 382, 295);
		list.addMouseListener(new MouseAdapter() {
			@Override

			// AL HACER CLICK EN EL ARTISTA SE EJECUTA
			public void mouseClicked(MouseEvent e) {
				int index = list.getSelectedIndex();
				artistaSeleccionado = artistas.get(index);
				artistaSeleccionado.getNombreArtistico();
				crearPanelArtista();
				metodos.cambiarDePanel(layeredPane, artistaPanel);

			}
		});

		panelDescubrirMusica.add(list);
	}

	private void crearPanelArtista() {

		panelArtista = new JPanel();
		layeredPane.add(panelArtista, artistaPanel);
		panelArtista.setBackground(Color.WHITE);
		panelArtista.setLayout(null);

		// Botones
		metodos.botonPerfil(layeredPane, panelArtista, user);
		metodos.botonAtras(layeredPane, descubrirMusica, panelArtista);

		// Imagen
		Image imagenArtista = new ImageIcon(
				Paths.get("").toAbsolutePath().toString() + artistaSeleccionado.getImagenArtista()).getImage();
		ImageIcon imagenArtistaEscalada = new ImageIcon(imagenArtista.getScaledInstance(255, 235, Image.SCALE_SMOOTH));

		JLabel lblImagen = new JLabel(imagenArtistaEscalada);
		lblImagen.setBounds(569, 229, 217, 186);
		lblImagen.setIcon(imagenArtistaEscalada);
		panelArtista.add(lblImagen);

		// Lista de álbumes
		JList<String> listaAlbumes = new JList<>();
		listaAlbumes.setBackground(SystemColor.menu);
		listaAlbumes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaAlbumes.setBounds(55, 68, 382, 347);

		DefaultListModel<String> albumesModelList = new DefaultListModel<>();

		ArrayList<Album> albumes = metodos.albumesBD(DRIVER, LinkBD, usuarioBBDD, contrasenaBBDD,
				artistaSeleccionado.getNombreArtistico());

		if (albumes != null) {
			for (Album album : albumes) {
				albumesModelList.addElement(album.getTituloAlbum());
			}
		} else {
			albumesModelList.addElement("No se han encontrado artistas disponibles");
		}
		listaAlbumes.setModel(albumesModelList);
		panelArtista.add(listaAlbumes);

		listaAlbumes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAlbum = listaAlbumes.getSelectedIndex();
				if (indexAlbum != -1) {
					albumSeleccionado = albumes.get(indexAlbum);
					crearPanelAlbum();
					metodos.cambiarDePanel(layeredPane, albumPanel);

				}
			}
		});

		// TextArea
		JTextArea textAreaInfoArtista = new JTextArea("Tipo: " + artistaSeleccionado.getCaracteristica()
				+ "\nEn activo desde: " + artistaSeleccionado.getAnoActivo() + "\nDescripción: "
				+ artistaSeleccionado.getDescripcionArtista());
		textAreaInfoArtista.setLineWrap(true);
		textAreaInfoArtista.setWrapStyleWord(true);
		textAreaInfoArtista.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfoArtista.setBackground(SystemColor.menu);
		textAreaInfoArtista.setBounds(529, 68, 298, 126);
		textAreaInfoArtista.setEditable(false);
		panelArtista.add(textAreaInfoArtista);

	}

	private void crearPanelAlbum() {
		panelAlbum = new JPanel();
		layeredPane.add(panelAlbum, albumPanel);
		panelAlbum.setBackground(new Color(255, 255, 255));
		panelAlbum.setLayout(null);

		metodos.botonPerfil(layeredPane, panelAlbum, user);
		metodos.botonAtras(layeredPane, artistaPanel, panelAlbum);

		// Imagen
		Image imagen = new ImageIcon(Paths.get("").toAbsolutePath().toString() + albumSeleccionado.getImagenAlbum())
				.getImage();
		ImageIcon imagenEscalada = new ImageIcon(imagen.getScaledInstance(200, 190, Image.SCALE_SMOOTH));

		JLabel lblImagen = new JLabel(imagenEscalada);
		lblImagen.setBounds(569, 229, 217, 186);
		lblImagen.setIcon(imagenEscalada);
		panelAlbum.add(lblImagen);

		// Lista de álbumes
		JList<String> listaCanciones = new JList<>();
		listaCanciones.setBackground(SystemColor.menu);
		listaCanciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaCanciones.setBounds(55, 68, 382, 347);

		DefaultListModel<String> cancionesModelList = new DefaultListModel<>();

		ArrayList<Cancion> canciones = metodos.cancionesBD(DRIVER, LinkBD, usuarioBBDD, contrasenaBBDD,
				albumSeleccionado.getTituloAlbum());

		if (canciones != null) {
			for (Cancion multimedia : canciones) {

				cancionesModelList.addElement(multimedia.getNombreMultimedia());
			}
		} else {
			cancionesModelList.addElement("No se han encontrado artistas disponibles");
		}
		listaCanciones.setModel(cancionesModelList);
		panelAlbum.add(listaCanciones);

		listaCanciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexCancion = listaCanciones.getSelectedIndex();
				if (indexCancion != -1) {
					cancionSeleccionada = canciones.get(indexCancion);
					crearPanelReproduccion();
					estaSonando();
					metodos.cambiarDePanel(layeredPane, reproduccion);

				}
			}
		});
		// int DuracionTotal;

		// TextArea
		JTextArea textAreaInfoAlbum = new JTextArea("Título: " + albumSeleccionado.getTituloAlbum() + "\nGenero: "
				+ albumSeleccionado.getGeneroAlbum() + "\nFecha de publicación: " + albumSeleccionado.getAnoAlbum());
		textAreaInfoAlbum.setLineWrap(true);
		textAreaInfoAlbum.setWrapStyleWord(true);
		textAreaInfoAlbum.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfoAlbum.setBackground(SystemColor.menu);
		textAreaInfoAlbum.setBounds(529, 68, 298, 126);
		textAreaInfoAlbum.setEditable(false);
		panelAlbum.add(textAreaInfoAlbum);

	}

	private void crearPanelReproduccion() {
		panelReproduccion = new JPanel();
		layeredPane.add(panelReproduccion, reproduccion);
		panelReproduccion.setBackground(new Color(255, 255, 255));

		metodos.botonPerfil(layeredPane, panelReproduccion, user);
		// metodos.botonAtras(layeredPane, "Album", panelReproduccion);
		panelReproduccion.setLayout(null);

		Image imagen = new ImageIcon(
				Paths.get("").toAbsolutePath().toString() + cancionSeleccionada.getImagenMultimedia()).getImage();
		ImageIcon imagenEscalada = new ImageIcon(imagen.getScaledInstance(255, 235, Image.SCALE_SMOOTH));

		JLabel lblImagen = new JLabel(imagenEscalada);
		lblImagen.setBounds(292, 30, 287, 250);
		lblImagen.setIcon(imagenEscalada);
		panelReproduccion.add(lblImagen);

		numCancion = cancionSeleccionada.getAudioID();

		cancionSeleccionada.loadClip("/audio/" + numCancion + ".wav");

		JButton btnReproMenu = new JButton("Menu");
		btnReproMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancionSeleccionada.pause();
				metodos.cambiarDePanel(layeredPane, menu);
			}
		});
		btnReproMenu.setBounds(192, 299, 89, 28);
		panelReproduccion.add(btnReproMenu);

		JButton btnReproAnterior = new JButton("<");
		btnReproAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (atras == 0) {
					cancionSeleccionada.resume();
					atras++;
				} else if (atras == 1) {
					estaSonando();
					cancionSeleccionada.pause();
					numCancion--;
					cancionSeleccionada.loadClip("/audio/" + numCancion + ".wav");
					atras = 0;
					cancionSeleccionada.play();
				}

			}
		});
		btnReproAnterior.setBounds(292, 299, 89, 28);
		panelReproduccion.add(btnReproAnterior);

		JButton btnReproPlay = new JButton("Play");
		btnReproPlay.setBounds(392, 299, 89, 28);
		btnReproPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancionSeleccionada.play();
				// btnReproPlay.setEnabled(false);
				btnReproPlay.setVisible(false);

				JButton btnReproPause = new JButton("Pause");
				btnReproPause.setBounds(392, 299, 89, 28);
				btnReproPause.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancionSeleccionada.pause();
						// btnReproPause.setEnabled(false);
						btnReproPause.setVisible(false);
						// btnReproPlay.setEnabled(true);
						btnReproPlay.setVisible(true);
					}
				});
				panelReproduccion.add(btnReproPause);
			}
		});
		panelReproduccion.add(btnReproPlay);

		JButton btnReproAdelante = new JButton(">");
		btnReproAdelante.setBounds(492, 299, 89, 28);
		btnReproAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancionSeleccionada.pause();
				numCancion++;
				cancionSeleccionada.loadClip("/audio/" + numCancion + ".wav");
				cancionSeleccionada.play();
				estaSonando();
			}
		});

		panelReproduccion.add(btnReproAdelante);

		JButton btnReproFavorito = new JButton("Favoritos");
		btnReproFavorito.setBounds(592, 299, 89, 28);
		panelReproduccion.add(btnReproFavorito);

		JButton btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.cambiarDePanel(layeredPane, albumPanel);
				cancionSeleccionada.pause();
			}
		});
		btnAtras.setBounds(55, 28, 89, 23);
		panelReproduccion.add(btnAtras);

	}

	private void estaSonando() {

		textAreaInfoCancion.setText("Está sonando " + cancionSeleccionada.getNombreMultimedia() + "\nDel disco "
				+ albumSeleccionado.getTituloAlbum() + "\nDe " + artistaSeleccionado.getNombreArtistico());
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

	private void crearPanelDescubrirPodcasts() {
		panelDescubrirPodcasts = new JPanel();
		panelDescubrirPodcasts.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelDescubrirPodcasts, descubrirPodcasts);
		panelDescubrirPodcasts.setLayout(null);
		metodos.botonPerfil(layeredPane, panelDescubrirPodcasts, user);
		metodos.botonAtras(layeredPane, menu, panelDescubrirPodcasts);

		JLabel lblListaDePodcasters = new JLabel("Lista de podcasters");
		lblListaDePodcasters.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDePodcasters.setBounds(328, 61, 217, 14);
		panelDescubrirPodcasts.add(lblListaDePodcasters);

		JList<String> list2 = new JList<String>();
		list2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list2.setBackground(SystemColor.menu);
		DefaultListModel<String> modeloLista2 = new DefaultListModel<>();

		ArrayList<Podcaster> artistas2 = metodos.artistas2BD(DRIVER, LinkBD, usuarioBBDD, contrasenaBBDD);

		if (artistas2 != null) {
			for (Podcaster podcaster : artistas2) {
				modeloLista2.addElement(podcaster.getNombreArtistico());
			}
		} else {
			modeloLista2.addElement("No se han encontrado artistas disponibles");
		}
		list2.setModel(modeloLista2);
		list2.setBounds(246, 120, 382, 295);
		list2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index2 = list2.getSelectedIndex();
				podcasterSeleccionado = modeloLista2.getElementAt(index2);

			}
		});

		panelDescubrirPodcasts.add(list2);

	}

	private void crearPanelMisPlaylists() {
		panelMisPlaylists = new JPanel();
		panelMisPlaylists.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMisPlaylists, misPlayLists);
		panelMisPlaylists.setLayout(null);
		metodos.botonPerfil(layeredPane, panelMisPlaylists, user);
		metodos.botonAtras(layeredPane, menu, panelMisPlaylists);

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
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnImportar.setBounds(639, 286, 170, 35);
		panelMisPlaylists.add(btnImportar);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExportar.setBounds(639, 216, 170, 35);
		panelMisPlaylists.add(btnExportar);

		JList<?> list_1 = new JList<Object>();
		list_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list_1.setBackground(SystemColor.menu);
		list_1.setBounds(10, 76, 600, 364);
		panelMisPlaylists.add(list_1);

	}

	private void crearPanelMenuAdmin() {
		panelMenuAdmin = new JPanel();
		panelMenuAdmin.setLayout(null);
		panelMenuAdmin.setBackground(Color.WHITE);
		layeredPane.add(panelMenuAdmin, menuAdmin);
		metodos.botonAtras(layeredPane, login, panelMenuAdmin);

		JLabel lblMenuAdmin = new JLabel("Menú de gestión");
		lblMenuAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuAdmin.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMenuAdmin.setBounds(262, 59, 349, 83);
		panelMenuAdmin.add(lblMenuAdmin);

		JButton btnGestionarMusica = new JButton("Gestionar música");
		btnGestionarMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelMenuGestionar(swMusica);
				metodos.cambiarDePanel(layeredPane, menuGestionar);
			}
		});
		btnGestionarMusica.setBounds(304, 171, 265, 23);
		panelMenuAdmin.add(btnGestionarMusica);
		JButton btnGestionarPodcasts = new JButton("Gestionar podcasts");
		btnGestionarPodcasts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelMenuGestionar(swPodcasts1);
				metodos.cambiarDePanel(layeredPane, menuGestionar);
			}
		});
		btnGestionarPodcasts.setBounds(304, 219, 265, 23);
		panelMenuAdmin.add(btnGestionarPodcasts);
		JButton btnEstadisticas = new JButton("Estadísticas");
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelMenuGestionar(swEstadisticas);
				metodos.cambiarDePanel(layeredPane, menuGestionar);
			}
		});
		btnEstadisticas.setBounds(304, 267, 265, 23);
		panelMenuAdmin.add(btnEstadisticas);
	}

	private void crearPanelMenuGestionar(String opcionMenu) {
		panelMenuGestionar = new JPanel();
		panelMenuGestionar.setLayout(null);
		panelMenuGestionar.setBackground(Color.WHITE);
		layeredPane.add(panelMenuGestionar, menuGestionar);
		metodos.botonAtras(layeredPane, menuAdmin, panelMenuGestionar);
		JLabel lblGestionarMusica = new JLabel("¿Qué desea gestionar?");
		lblGestionarMusica.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionarMusica.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblGestionarMusica.setBounds(153, 59, 567, 83);
		panelMenuGestionar.add(lblGestionarMusica);
		JButton btnGestionar1 = new JButton();
		btnGestionar1.setBounds(304, 171, 265, 23);
		panelMenuGestionar.add(btnGestionar1);
		JButton btnGestionar2 = new JButton();
		btnGestionar2.setBounds(304, 219, 265, 23);
		panelMenuGestionar.add(btnGestionar2);
		JButton btnGestionar3 = new JButton();
		btnGestionar3.setBounds(304, 267, 265, 23);
		panelMenuGestionar.add(btnGestionar3);
		JButton btnGestionar4 = new JButton();
		btnGestionar4.setBounds(304, 315, 265, 23);
		panelMenuGestionar.add(btnGestionar4);
		switch (opcionMenu) {
		case swMusica:
			btnGestionar1.setText("Gestionar Artistas");
			btnGestionar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swArtistas);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			btnGestionar2.setText("Gestionar Albumes");
			btnGestionar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swAlbumes);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			btnGestionar3.setText("Gestionar Canciones");
			btnGestionar3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swCanciones);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			btnGestionar4.setVisible(false);
			break;
		case swPodcasts1:
			btnGestionar1.setText("Gestionar Podcasters");
			btnGestionar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swPodcasters);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			btnGestionar2.setText("Gestionar Podcasts");
			btnGestionar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swPodcasts2);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			btnGestionar3.setVisible(false);
			btnGestionar4.setVisible(false);
			break;
		case swEstadisticas:
			lblGestionarMusica.setText("Estadísticas");
			btnGestionar1.setText("Canciones Más Gustadas");
			btnGestionar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelEstadisticas(swCancionesMasGustadas);
					metodos.cambiarDePanel(layeredPane, estadisticas);
				}
			});
			btnGestionar2.setText("Podcast Más Gustados");
			btnGestionar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelEstadisticas(swPodcastMasGustados);
					metodos.cambiarDePanel(layeredPane, estadisticas);
				}
			});
			btnGestionar3.setText("Más Escuchados");
			btnGestionar3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelEstadisticas(swMasEscuchados);
					metodos.cambiarDePanel(layeredPane, estadisticas);
				}
			});
			btnGestionar4.setText("Top PlayLists");
			btnGestionar4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelEstadisticas(swTopPlaylist);
					metodos.cambiarDePanel(layeredPane, estadisticas);
				}
			});
			break;
		}
	}

	private void crearPanelMenuEditar(String opcionGestionar) {
		panelMenuEditar = new JPanel();
		panelMenuEditar.setLayout(null);
		panelMenuEditar.setBackground(Color.WHITE);
		layeredPane.add(panelMenuEditar, editar);
		metodos.botonAtras(layeredPane, menuGestionar, panelMenuEditar);
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(304, 171, 265, 23);
		panelMenuEditar.add(btnModificar);
		JButton btnAnadir = new JButton("Añadir");
		btnAnadir.setBounds(304, 219, 265, 23);
		panelMenuEditar.add(btnAnadir);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(304, 267, 265, 23);
		panelMenuEditar.add(btnEliminar);
		switch (opcionGestionar) {
		case swArtistas:
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelModificarYAnadir();
					metodos.cambiarDePanel(layeredPane, modificarYAnadir);
				}
			});
			btnAnadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelModificarYAnadir();
					metodos.cambiarDePanel(layeredPane, modificarYAnadir);
				}
			});
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			break;
		case swAlbumes:
			break;
		case swCanciones:
			break;
		case swPodcasters:
			break;
		case swPodcasts2:
			break;
		}
	}

	private void crearPanelModificarYAnadir() {
		panelModificarYAnadir = new JPanel();
		panelModificarYAnadir.setLayout(null);
		panelModificarYAnadir.setBackground(Color.WHITE);
		layeredPane.add(panelModificarYAnadir, modificarYAnadir);

		metodos.botonAtras(layeredPane, editar, panelModificarYAnadir);

		metodos.createLabel("Nombre Artistico:", 274, 125, 148, 20, panelModificarYAnadir);
		metodos.createLabel("Imagen:", 274, 156, 148, 20, panelModificarYAnadir);
		metodos.createLabel("Caracteristica:", 274, 187, 148, 20, panelModificarYAnadir);

	}

	private void crearPanelEstadisticas(String opcionEstadisticas) {
		panelEstadisticas = new JPanel();
		panelEstadisticas.setLayout(null);
		panelEstadisticas.setBackground(Color.WHITE);
		layeredPane.add(panelEstadisticas, estadisticas);

		metodos.botonAtras(layeredPane, menuGestionar, panelEstadisticas);
	}
}