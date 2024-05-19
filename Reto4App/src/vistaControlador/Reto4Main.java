package vistaControlador;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import modelo.*;

public class Reto4Main extends JFrame {
	private static final long serialVersionUID = 1L;

	Metodos metodos = new Metodos();
	BDConexiones conexionesBD = new BDConexiones();

	// PANELES
	JLayeredPane layeredPane;
	JPanel contentPane, panelBienvenida, panelLogin, panelRegistro, panelPerfil, panelMenuAdmin, panelMenuGestionar,
			panelEstadisticas, panelEditar, panelDescubrir, panelArtista, panelAlbum, panelReproduccion,
			panelDescubrirPodcasts, panelMisPlaylists, panelPlaylist, panelMenuAnadirPlaylist, panelNuevaPlaylist,
			panelBorrarPlaylist, panelMenu, panelMenuEditar, panelFormularioAdmin, panelJlistAdmin;

	// FORMATO DE FECHA
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// COGER EL DIA ACTUAL PARA EL REGISTRO
	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

	// NOMBRES PANELES
	String bienvenida = "bienvenida", login = "login", registro = "registro", menu = "menu", perfil = "perfil",
			menuAdmin = "menuAdmin", descubrir = "descubrir", artistaPanel = "artista", albumPanel = "album",
			reproduccion = "reproduccion", misPlaylists = "misPlaylists", playlistPanel = "playlistPanel",
			menuAnadirPlaylist = "menuAnadirPlaylist", nuevaPlaylist = "nuevaPlaylist",
			borrarPlaylist = "borrarPlaylist", estadisticas = "estadisticas", editar = "editar",
			menuGestionar = "menugestionar", formularioAdmin = "formularioAdmin", jlistAdmin = "jlistAdmin";

	// VARIABLES PARA LOS SWITCH DE LOS PANELES DE MUSICA/PODCAST
	final String swVentanaMusico = "swVentanaMusico", swVentanaPodcaster = "swVentanaPodcaster",
			swVentanaAudios = "swVentanaAudios", swVentanaAlbumes = "swVentanaAlbumes",
			swVentanaPodcasts = "swVentanaPodcasts", swVentanaPodcast = "swVentanaPodcast",
			swVentanaCancion = "swVentanaCancion";

	// VARIABLES PARA LOS SWITCH DE LOS PANELES DE ADMIN
	final String swMusica = "swMusica", swPodcasts1 = "swPodcasts1", swEstadisticas = "swEstadisticas",
			swArtistas = "swArtistas", swAlbumes = "swAlbumes", swCanciones = "swCanciones",
			swPodcasters = "swPodcasters", swPodcasts2 = "swPodcasts2",
			swCancionesMasGustadas = "swCancionesMasGustadas", swPodcastMasGustados = "swPodcastMasGustados",
			swMasEscuchados = "swMasEscuchados";

	String user = "";
	String albumTit = "", albumAno = "", albumGen = "";

	int reproduccionCont, i;

	// VARIABLES BOTONES DE CAMBIAR DE CANCIÓN
	int atras = 0, numAudio = 0;
	Album albumSeleccionado;
	Audio audioSeleccionado;
	Podcaster podcasterSeleccionado;
	Podcast podcastSeleccionado;
	Musico musicoSeleccionado;
	Cancion cancionSeleccionada;
	Playlist playlist, playlistCanciones, playlistFavorita, playlistDeleteSeleccionada;
	UsuarioFree UsuarioNuevo, Usuario;
	JButton btnReproPlay;

	JProgressBar progressBar;
	JTextArea textAreaInfo = new JTextArea(""), textAreaInfoCancion = new JTextArea("");

	// LÍMITES DE FECHAS
	Calendar ahoraMismo = Calendar.getInstance();
	int ano = ahoraMismo.get(Calendar.YEAR);
	int mes = ahoraMismo.get(Calendar.MONTH) + 1;
	int dia = ahoraMismo.get(Calendar.DATE);
	String minString = (ano - 100) + "-" + mes + "-" + dia;
	String maxString = ano + "-" + mes + "-" + dia;

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

		crearPanelBienvenida();
	}

	private void crearPanelBienvenida() {
		panelBienvenida = new JPanel();
		panelBienvenida.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelBienvenida, bienvenida);
		panelBienvenida.addMouseListener((MouseListener) new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				crearPanelLogin();
				metodos.cambiarDePanel(layeredPane, login);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

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

	public void crearPanelLogin() {
		panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelLogin, login);
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioDeSesion.setBounds(0, 80, 874, 33);
		panelLogin.add(lblInicioDeSesion);

		/**
		 * Creación de labels mediante un metodo al cual se le manda el texto, las
		 * coordenadas y el panel donde se va a encontrar el label
		 */
		metodos.crearLabel("Usuario:", 310, 180, 109, 14, panelLogin);
		metodos.crearLabel("Contraseña:", 310, 220, 109, 14, panelLogin);

		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(410, 180, 148, 20);
		panelLogin.add(txtUsuario);

		JPasswordField pswContrasena = new JPasswordField();
		pswContrasena.setBounds(410, 220, 148, 20);
		panelLogin.add(pswContrasena);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/**
				 * Al presionar el botón de entrar existirán dos casos diferentes, cuando el
				 * nombre de usuario coincida con el de un administrador la ejecución del
				 * programa será completamente diferente ya que se encargará de la gestión del
				 * mismo.
				 * 
				 * Cuando sea un cliente el que acceda, se comprobarán sus credenciales en la
				 * base de datos y se le asignarán los datos correspondientes al objeto cliente.
				 */

				char[] contrasenaAdmin = { '1', '2', '3' };
				String textOk = "Has iniciado sesión correctamente";
				user = txtUsuario.getText();

				if (user.equals("admin") && Arrays.equals(pswContrasena.getPassword(), contrasenaAdmin)) {
					JOptionPane.showMessageDialog(null, textOk);
					crearPanelMenuAdmin();
					metodos.cambiarDePanel(layeredPane, menuAdmin);
					txtUsuario.setText("");
					pswContrasena.setText("");

				} else {
					String pass = new String(pswContrasena.getPassword());
					crearPanelMenu();
					Usuario = new UsuarioFree();
					Usuario = conexionesBD.conexionLogin(textOk, user, pass, menu, layeredPane, Usuario);
					conexionesBD.playlistFavorita(Usuario, Usuario.getClienteID());
					crearPanelPerfil(Usuario);
					crearPanelMenu();
					txtUsuario.setText("");
					pswContrasena.setText("");
				}
			}
		});
		btnEntrar.setBounds(457, 283, 142, 23);
		panelLogin.add(btnEntrar);

		JButton btnCrear = new JButton("Crear nuevo usuario");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelRegistro();
				metodos.cambiarDePanel(layeredPane, registro);
			}
		});
		btnCrear.setBounds(255, 283, 159, 23);
		panelLogin.add(btnCrear);
	}

	public void crearPanelRegistro() {
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

		JTextField txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(412, 125, 175, 20);
		panelRegistro.add(txtNombre);

		JTextField txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(412, 156, 175, 20);
		panelRegistro.add(txtApellidos);

		JTextField txtUsuarioRegistro = new JTextField();
		txtUsuarioRegistro.setColumns(10);
		txtUsuarioRegistro.setBounds(412, 187, 175, 20);
		panelRegistro.add(txtUsuarioRegistro);

		JPasswordField pswCrearContrasena = new JPasswordField();
		pswCrearContrasena.setBounds(412, 218, 175, 20);
		panelRegistro.add(pswCrearContrasena);

		metodos.crearLabel("Nombre:", 274, 125, 148, 20, panelRegistro);
		metodos.crearLabel("Apellidos:", 274, 156, 148, 20, panelRegistro);
		metodos.crearLabel("Nombre de usuario:", 274, 187, 148, 20, panelRegistro);
		metodos.crearLabel("Contraseña:", 274, 218, 148, 20, panelRegistro);
		metodos.crearLabel("Fecha de nacimiento:", 274, 250, 148, 20, panelRegistro);

		JDateChooser fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(412, 248, 175, 20);
		panelRegistro.add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(minString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/**
				 * Al crear usuarios se asignarán los datos a un objeto. Para que el usuario
				 * nuevo se cree pasará por unas validaciones previas antes de insertar los
				 * datos en la base de datos. Una vez creado el usuario, este mismo tendrá que
				 * acceder al programa mediante el LogIn.
				 */

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

					String sentenciaSQL = "INSERT IGNORE INTO Cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo) VALUES ("
							+ "'" + UsuarioNuevo.getNombre() + "', '" + UsuarioNuevo.getApellido() + "'" + ", " + "'"
							+ UsuarioNuevo.getUsuario() + "', '" + UsuarioNuevo.getContrasena() + "', '"
							+ UsuarioNuevo.getFechaNacimiento() + "', '" + UsuarioNuevo.getFechaRegistro() + "'"
							+ ", 'Free')";
					conexionesBD.conexionInsertYDelete(sentenciaSQL);

					conexionesBD.asignacionPlaylistDefault(UsuarioNuevo, UsuarioNuevo.getUsuario(), timeStamp);

					metodos.cambiarDePanel(layeredPane, login);
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

	public void crearPanelPerfil(UsuarioFree Usuario) {
		panelPerfil = new JPanel();
		panelPerfil.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelPerfil, perfil);
		panelPerfil.setLayout(null);

		metodos.botonAtras(layeredPane, menu, panelPerfil);

		JLabel lblInformacionPerfil = new JLabel("Datos del usuario:");
		lblInformacionPerfil.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblInformacionPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacionPerfil.setBounds(0, 40, 874, 33);
		panelPerfil.add(lblInformacionPerfil);

		metodos.crearLabel("Nombre:", 274, 130, 148, 20, panelPerfil);
		metodos.crearLabel("Apellidos:", 274, 170, 148, 20, panelPerfil);
		metodos.crearLabel("Nombre de usuario:", 274, 210, 148, 20, panelPerfil);
		metodos.crearLabel("Contraseña:", 274, 250, 148, 20, panelPerfil);
		metodos.crearLabel("Fecha de nacimiento:", 274, 290, 148, 20, panelPerfil);
		metodos.crearLabel("Fecha de registro:", 274, 330, 148, 20, panelPerfil);
		metodos.crearLabel(Usuario.getNombre(), 450, 130, 148, 20, panelPerfil);
		metodos.crearLabel(Usuario.getApellido(), 450, 170, 148, 20, panelPerfil);
		metodos.crearLabel(Usuario.getUsuario(), 450, 210, 148, 20, panelPerfil);
		metodos.crearLabel("********", 450, 250, 148, 20, panelPerfil);
		metodos.crearLabel(Usuario.getFechaNacimiento(), 450, 290, 148, 20, panelPerfil);
		metodos.crearLabel(Usuario.getFechaRegistro(), 450, 330, 148, 20, panelPerfil);
	}

	public void crearPanelMenu() {
		panelMenu = new JPanel();
		panelMenu.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMenu, menu);
		panelMenu.setLayout(null);

		metodos.botonPerfil(layeredPane, panelMenu, user, perfil);
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
				crearPanelDescubrir(swVentanaMusico);
				metodos.cambiarDePanel(layeredPane, descubrir);
			}
		});
		btnDescubrirMusica.setBounds(304, 171, 265, 23);
		panelMenu.add(btnDescubrirMusica);

		// DESCUBRIR PODCASTS
		JButton btnDescubrirPodcasts = new JButton("Descubrir podcasts");
		btnDescubrirPodcasts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelDescubrir(swVentanaPodcaster);
				metodos.cambiarDePanel(layeredPane, descubrir);
			}
		});
		btnDescubrirPodcasts.setBounds(304, 219, 265, 23);
		panelMenu.add(btnDescubrirPodcasts);

		// MIS PLAYLISTS
		JButton btnMisPlayLists = new JButton("Mis PlayLists");
		btnMisPlayLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelMisPlaylists();
				metodos.cambiarDePanel(layeredPane, misPlaylists);
			}
		});
		btnMisPlayLists.setBounds(304, 265, 265, 23);
		panelMenu.add(btnMisPlayLists);

	}

	public void crearPanelDescubrir(String swVentana) {

		panelDescubrir = new JPanel();
		panelDescubrir.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelDescubrir, descubrir);
		panelDescubrir.setLayout(null);

		metodos.botonPerfil(layeredPane, panelDescubrir, user, perfil);
		metodos.botonAtras(layeredPane, menu, panelDescubrir);

		JLabel lblListaDeArtistas = new JLabel();
		lblListaDeArtistas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArtistas.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblListaDeArtistas.setBounds(262, 50, 349, 83);
		panelDescubrir.add(lblListaDeArtistas);

		switch (swVentana) {

		case swVentanaMusico:
			lblListaDeArtistas.setText("Lista de Músicos");

			JList<String> listaMusico = new JList<String>();
			listaMusico.setBackground(SystemColor.menu);
			listaMusico.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			DefaultListModel<String> modeloLista = new DefaultListModel<>();

			ArrayList<Musico> musicoArrayList = conexionesBD.conexionMusico();

			if (musicoArrayList != null) {
				int numero;
				for (Musico musico : musicoArrayList) {
					numero = (int) (Math.random() * (150 - 120) + 120);
					modeloLista.addElement(musico.getNombreArtistico() + " (" + numero + " reproducciones)");
				}
			} else {
				modeloLista.addElement("No se han encontrado artistas disponibles");
			}
			listaMusico.setModel(modeloLista);
			listaMusico.setBounds(246, 120, 382, 295);
			listaMusico.addMouseListener(new MouseAdapter() {
				// AL HACER CLICK EN EL ARTISTA SE EJECUTA
				public void mouseClicked(MouseEvent e) {
					int index = listaMusico.getSelectedIndex();
					musicoSeleccionado = musicoArrayList.get(index);
					crearPanelArtista(swVentanaAlbumes, musicoSeleccionado);
					metodos.cambiarDePanel(layeredPane, artistaPanel);
				}
			});

			panelDescubrir.add(listaMusico);
			break;

		case swVentanaPodcaster:
			lblListaDeArtistas.setText("Lista de Podcasters");

			JList<String> listaPodcaster = new JList<String>();
			listaPodcaster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaPodcaster.setBackground(SystemColor.menu);
			DefaultListModel<String> modeloListaPodcaster = new DefaultListModel<>();

			ArrayList<Podcaster> podcasterArrayList = conexionesBD.conexionPodcaster();

			if (podcasterArrayList != null) {
				for (Podcaster podcaster : podcasterArrayList) {

					modeloListaPodcaster.addElement(podcaster.getNombreArtistico());
				}
			} else {
				modeloListaPodcaster.addElement("No se han encontrado podcasters disponibles");
			}
			listaPodcaster.setModel(modeloListaPodcaster);
			listaPodcaster.setBounds(246, 120, 382, 295);
			listaPodcaster.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int indexPodcast = listaPodcaster.getSelectedIndex();
					podcasterSeleccionado = podcasterArrayList.get(indexPodcast);
					crearPanelArtista(swVentanaPodcasts, podcasterSeleccionado);
					metodos.cambiarDePanel(layeredPane, artistaPanel);
				}
			});
			panelDescubrir.add(listaPodcaster);
			break;
		}

	}

	public void crearPanelArtista(String swVentanaAudios, Artista seleccion) {
		String tipo = "";
		panelArtista = new JPanel();
		layeredPane.add(panelArtista, artistaPanel);
		panelArtista.setBackground(Color.WHITE);
		panelArtista.setLayout(null);

		metodos.botonPerfil(layeredPane, panelArtista, user, perfil);
		metodos.botonAtras(layeredPane, descubrir, panelArtista);

		// Imagen
		Image imagenArtista = new ImageIcon(Paths.get("").toAbsolutePath().toString() + seleccion.getImagenArtista())
				.getImage();
		ImageIcon imagenArtistaEscalada = new ImageIcon(imagenArtista.getScaledInstance(255, 235, Image.SCALE_SMOOTH));

		JLabel lblImagen = new JLabel(imagenArtistaEscalada);
		lblImagen.setBounds(569, 229, 217, 186);
		lblImagen.setIcon(imagenArtistaEscalada);
		panelArtista.add(lblImagen);

		switch (swVentanaAudios) {
		case swVentanaAlbumes:

			tipo = "Tipo: " + seleccion.getCaracteristica() + "\n";
			// Lista de álbumes
			JList<String> listaAlbumes = new JList<>();
			listaAlbumes.setBackground(SystemColor.menu);
			listaAlbumes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaAlbumes.setBounds(55, 68, 382, 347);

			DefaultListModel<String> albumesModelList = new DefaultListModel<>();

			ArrayList<Album> albumesArrayList = conexionesBD.conexionAlbum(musicoSeleccionado.getNombreArtistico());

			if (albumesArrayList != null) {
				for (Album album : albumesArrayList) {
					albumesModelList.addElement(album.getTituloAlbum());
				}
			} else {
				albumesModelList.addElement("No se han encontrado albumes disponibles");
			}
			listaAlbumes.setModel(albumesModelList);
			panelArtista.add(listaAlbumes);

			listaAlbumes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int indexAlbum = listaAlbumes.getSelectedIndex();
					if (indexAlbum != -1) {
						albumSeleccionado = albumesArrayList.get(indexAlbum);
						crearPanelAlbum();
						metodos.cambiarDePanel(layeredPane, albumPanel);

					}
				}
			});

			textAreaInfo.setText(tipo + "En activo desde: " + seleccion.getAnoActivo() + "\nDescripción: "
					+ seleccion.getDescripcionArtista());
			break;

		case swVentanaPodcasts:

			JList<String> listaPodcasts = new JList<>();
			listaPodcasts.setBackground(SystemColor.menu);
			listaPodcasts.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaPodcasts.setBounds(55, 68, 382, 347);

			DefaultListModel<String> podcastsModelList = new DefaultListModel<>();

			ArrayList<Podcast> podcastsArrayList = conexionesBD.conexionPodcast(podcasterSeleccionado.getArtistaID());

			if (podcastsArrayList != null) {
				for (Podcast multimedia : podcastsArrayList) {
					podcastsModelList.addElement(multimedia.getNombreMultimedia());
				}
			} else {
				podcastsModelList.addElement("No se han encontrado podcasts disponibles");
			}
			listaPodcasts.setModel(podcastsModelList);
			panelArtista.add(listaPodcasts);

			listaPodcasts.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int indexPodcast = listaPodcasts.getSelectedIndex();
					if (indexPodcast != -1) {
						podcastSeleccionado = podcastsArrayList.get(indexPodcast);
						crearPanelReproduccion(swVentanaPodcast, artistaPanel);
						metodos.cambiarDePanel(layeredPane, reproduccion);
					}
				}
			});

			textAreaInfo.setText(tipo + "En activo desde: " + seleccion.getAnoActivo() + "\nDescripción: "
					+ seleccion.getDescripcionArtista());
			break;

		}
		textAreaInfo.setLineWrap(true);
		textAreaInfo.setWrapStyleWord(true);
		textAreaInfo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfo.setBackground(SystemColor.menu);
		textAreaInfo.setBounds(529, 68, 298, 126);
		textAreaInfo.setEditable(false);
		panelArtista.add(textAreaInfo);

	}

	public void crearPanelAlbum() {
		panelAlbum = new JPanel();
		layeredPane.add(panelAlbum, albumPanel);
		panelAlbum.setBackground(new Color(255, 255, 255));
		panelAlbum.setLayout(null);

		metodos.botonPerfil(layeredPane, panelAlbum, user, perfil);
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

		ArrayList<Cancion> cancionesArrayList = conexionesBD.conexionCancion(albumSeleccionado.getTituloAlbum());

		if (cancionesArrayList != null) {
			for (Cancion multimedia : cancionesArrayList) {
				cancionesModelList.addElement(multimedia.getNombreMultimedia());
			}
		} else {
			cancionesModelList.addElement("No se han encontrado canciones disponibles");
		}
		listaCanciones.setModel(cancionesModelList);
		panelAlbum.add(listaCanciones);

		listaCanciones.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				int indexCancion = listaCanciones.getSelectedIndex();
				if (indexCancion != -1) {
					cancionSeleccionada = cancionesArrayList.get(indexCancion);
					crearPanelReproduccion(swVentanaCancion, albumPanel);
					metodos.cambiarDePanel(layeredPane, reproduccion);

				}
			}
		});

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

	public void crearPanelReproduccion(String ventanaReproduccion, String anterior) {
		panelReproduccion = new JPanel();
		layeredPane.add(panelReproduccion, reproduccion);
		panelReproduccion.setBackground(new Color(255, 255, 255));
		panelReproduccion.setLayout(null);

		metodos.botonPerfil(layeredPane, panelReproduccion, user, perfil);

		progressBar = new JProgressBar();
		progressBar.setBounds(292, 280, 287, 8);
		panelReproduccion.add(progressBar);
		progressBar.setValue(0);

		textAreaInfoCancion.setLineWrap(true);
		textAreaInfoCancion.setWrapStyleWord(true);
		textAreaInfoCancion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaInfoCancion.setFont(new Font("Tahoma", Font.BOLD, 11));
		textAreaInfoCancion.setBackground(SystemColor.menu);
		textAreaInfoCancion.setBounds(192, 348, 489, 92);
		textAreaInfoCancion.setEditable(false);
		panelReproduccion.add(textAreaInfoCancion);

		switch (ventanaReproduccion) {

		case swVentanaCancion:

			Image imagen = new ImageIcon(
					Paths.get("").toAbsolutePath().toString() + cancionSeleccionada.getImagenMultimedia()).getImage();
			ImageIcon imagenEscalada = new ImageIcon(imagen.getScaledInstance(255, 235, Image.SCALE_SMOOTH));

			JLabel lblImagen = new JLabel(imagenEscalada);
			lblImagen.setBounds(292, 25, 287, 250);
			lblImagen.setIcon(imagenEscalada);
			panelReproduccion.add(lblImagen);

			numAudio = cancionSeleccionada.getAudioID();
			cancionSeleccionada.loadClip("/audio/" + numAudio + ".wav");

			JButton btnReproAnterior = new JButton("<");
			btnReproAnterior.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (atras == 0) {
						cancionSeleccionada.resume();
						atras++;
					} else if (atras == 1) {
						progressBar.setValue(0);

						cancionSeleccionada.pause();
						numAudio--;
						cancionSeleccionada.setAudioID(numAudio);

						cancionSeleccionada = conexionesBD.nuevaCancion(cancionSeleccionada.getAudioID());

						cancionSeleccionada.loadClip("/audio/" + numAudio + ".wav");

						metodos.cancionSonando(textAreaInfoCancion, cancionSeleccionada, albumSeleccionado,
								musicoSeleccionado, panelReproduccion);

						atras = 0;
						cancionSeleccionada.play();
						btnReproPlay.setText("Pause");

					}

				}
			});
			btnReproAnterior.setBounds(292, 299, 89, 28);
			panelReproduccion.add(btnReproAnterior);

			btnReproPlay = new JButton("Play");
			btnReproPlay.setBounds(392, 299, 89, 28);
			btnReproPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (btnReproPlay.getText() == "Play") {
						cancionSeleccionada.play();
						btnReproPlay.setText("Pause");

					} else {
						cancionSeleccionada.pause();
						btnReproPlay.setText("Play");

					}
				}
			});
			panelReproduccion.add(btnReproPlay);

			JButton btnReproAdelante = new JButton(">");
			btnReproAdelante.setBounds(492, 299, 89, 28);
			btnReproAdelante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancionSeleccionada.pause();
					numAudio++;
					cancionSeleccionada.setAudioID(numAudio);

					cancionSeleccionada = conexionesBD.nuevaCancion(cancionSeleccionada.getAudioID());

					cancionSeleccionada.loadClip("/audio/" + numAudio + ".wav");

					metodos.cancionSonando(textAreaInfoCancion, cancionSeleccionada, albumSeleccionado,
							musicoSeleccionado, panelReproduccion);

					cancionSeleccionada.play();
					btnReproPlay.setText("Pause");

				}
			});
			panelReproduccion.add(btnReproAdelante);

			JButton btnReproFavorito = new JButton("Favoritos");
			btnReproFavorito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					conexionesBD.conexionInsertYDeleteSinMensaje(
							"INSERT IGNORE INTO PlaylistCanciones (IDAudio, IDList, FechaPlaylistCancion) VALUES ('"
									+ cancionSeleccionada.getAudioID() + "', '" + Usuario.getPlaylistFavorita() + "', '"
									+ timeStamp + "')");
					JOptionPane.showMessageDialog(null, "Añadida a favoritos con éxito");
				}
			});
			btnReproFavorito.setBounds(592, 299, 89, 28);
			panelReproduccion.add(btnReproFavorito);

			JButton btnMenuAnadirPlaylist = new JButton("Menu");
			btnMenuAnadirPlaylist.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					panelMenuAnadirPlaylist = new JPanel();
					panelMenuAnadirPlaylist.setBackground(new Color(255, 255, 255));
					layeredPane.add(panelMenuAnadirPlaylist, menuAnadirPlaylist);
					panelMenuAnadirPlaylist.setLayout(null);

					metodos.cambiarDePanel(layeredPane, menuAnadirPlaylist);

					metodos.botonPerfil(layeredPane, panelMenuAnadirPlaylist, user, perfil);
					metodos.botonAtras(layeredPane, reproduccion, panelMenuAnadirPlaylist);

					JList<String> playlistLista = new JList<>();
					playlistLista.setBackground(SystemColor.menu);
					playlistLista.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					playlistLista.setBounds(10, 76, 600, 364);

					DefaultListModel<String> playlistModelList = new DefaultListModel<>();

					ArrayList<Playlist> playlistsArrayList = conexionesBD.conexionPlaylist(Usuario.getClienteID());

					if (playlistsArrayList != null) {
						for (Playlist playlist : playlistsArrayList) {
							playlistModelList.addElement(playlist.getTitulo());
						}
					} else {
						playlistModelList.addElement("No se han encontrado playlists disponibles");
					}
					playlistLista.setModel(playlistModelList);
					panelMenuAnadirPlaylist.add(playlistLista);

					playlistLista.addMouseListener(new MouseAdapter() {

						public void mouseClicked(MouseEvent e) {
							int indexPlaylist = playlistLista.getSelectedIndex();
							if (indexPlaylist != -1) {
								playlist = playlistsArrayList.get(indexPlaylist);
								conexionesBD.conexionInsertYDeleteSinMensaje(
										"INSERT IGNORE INTO PlaylistCanciones (IDAudio, IDList, FechaPlaylistCancion) VALUES ('"
												+ cancionSeleccionada.getAudioID() + "', '" + playlist.getIDList()
												+ "', '" + timeStamp + "')");

								metodos.cambiarDePanel(layeredPane, reproduccion);
								JOptionPane.showMessageDialog(null, "Canción añadida con éxito.");
							}
						}
					});
				}
			});
			btnMenuAnadirPlaylist.setBounds(192, 299, 89, 28);
			panelReproduccion.add(btnMenuAnadirPlaylist);

			metodos.cancionSonando(textAreaInfoCancion, cancionSeleccionada, albumSeleccionado, musicoSeleccionado,
					panelReproduccion);

			JButton btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					metodos.cambiarDePanel(layeredPane, anterior);
					cancionSeleccionada.pause();
				}
			});
			btnAtras.setBounds(55, 28, 89, 23);
			panelReproduccion.add(btnAtras);

			break;

		case swVentanaPodcast:
			audioSeleccionado = podcastSeleccionado;

			Image imagenPodcast = new ImageIcon(
					Paths.get("").toAbsolutePath().toString() + audioSeleccionado.getImagenMultimedia()).getImage();
			ImageIcon imagenEscaladaPodcast = new ImageIcon(
					imagenPodcast.getScaledInstance(255, 235, Image.SCALE_SMOOTH));

			JLabel lblImagenPodcast = new JLabel(imagenEscaladaPodcast);
			lblImagenPodcast.setBounds(292, 25, 287, 250);
			lblImagenPodcast.setIcon(imagenEscaladaPodcast);
			panelReproduccion.add(lblImagenPodcast);

			JButton btnReproPlayPodcast = new JButton("Play");
			btnReproPlayPodcast.setBounds(392, 299, 89, 28);
			btnReproPlayPodcast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (btnReproPlayPodcast.getText() == "Play") {
						podcastSeleccionado.play();
						btnReproPlayPodcast.setText("Pause");

					} else {
						podcastSeleccionado.pause();
						btnReproPlayPodcast.setText("Play");

					}
				}
			});
			panelReproduccion.add(btnReproPlayPodcast);

			numAudio = audioSeleccionado.getAudioID();
			audioSeleccionado.loadClip("/audio/" + numAudio + ".wav");

			JButton btn05 = new JButton("x0.5");
			btn05.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					audioSeleccionado.pause();
					audioSeleccionado.loadClip("/audio/" + numAudio + "x05.wav");
					audioSeleccionado.play();
					btnReproPlay.setText("Pause");
				}
			});
			btn05.setBounds(292, 299, 89, 28);
			panelReproduccion.add(btn05);

			JButton btn15 = new JButton("x1.5");
			btn15.setBounds(492, 299, 89, 28);
			btn15.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					audioSeleccionado.pause();
					audioSeleccionado.loadClip("/audio/" + numAudio + "x15.wav");
					audioSeleccionado.play();
					btnReproPlay.setText("Pause");
				}
			});
			btn15.setBounds(492, 299, 89, 28);
			panelReproduccion.add(btn15);

			metodos.podcastSonando(textAreaInfoCancion, audioSeleccionado, podcasterSeleccionado, panelReproduccion);

			btnReproPlay = new JButton("Play");
			btnReproPlay.setBounds(392, 299, 89, 28);
			btnReproPlay.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (btnReproPlay.getText() == "Play") {
						audioSeleccionado.play();
						btnReproPlay.setText("Pause");

					} else {
						audioSeleccionado.pause();
						btnReproPlay.setText("Play");
					}
				}
			});

			JButton btnAtrasPodcast = new JButton("Atrás");
			btnAtrasPodcast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					metodos.cambiarDePanel(layeredPane, anterior);
					audioSeleccionado.pause();
				}
			});
			btnAtrasPodcast.setBounds(55, 28, 89, 23);
			panelReproduccion.add(btnAtrasPodcast);
			break;
		}
	}

	public void crearPanelMisPlaylists() {
		panelMisPlaylists = new JPanel();
		panelMisPlaylists.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelMisPlaylists, misPlaylists);
		panelMisPlaylists.setLayout(null);

		metodos.botonPerfil(layeredPane, panelMisPlaylists, user, perfil);
		metodos.botonAtras(layeredPane, menu, panelMisPlaylists);

		JList<String> playlistLista = new JList<>();
		playlistLista.setBackground(SystemColor.menu);
		playlistLista.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		playlistLista.setBounds(10, 76, 600, 364);

		DefaultListModel<String> playlistModelList = new DefaultListModel<>();

		ArrayList<Playlist> playlistsArrayList = conexionesBD.conexionPlaylist(Usuario.getClienteID());

		if (playlistsArrayList != null) {
			for (Playlist playlist : playlistsArrayList) {
				playlistModelList.addElement(playlist.getTitulo());
			}
		} else {
			playlistModelList.addElement("No se han encontrado playlists disponibles");
		}
		playlistLista.setModel(playlistModelList);
		panelMisPlaylists.add(playlistLista);

		playlistLista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indexPlaylist = playlistLista.getSelectedIndex();
				if (indexPlaylist != -1) {
					playlist = playlistsArrayList.get(indexPlaylist);
					crearPanelPlaylist();
					metodos.cambiarDePanel(layeredPane, playlistPanel);
				}
			}
		});

		JButton btnNuevaPlayList = new JButton("Nueva PlayList");
		btnNuevaPlayList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelNuevaPlaylist = new JPanel();
				panelNuevaPlaylist.setBackground(new Color(255, 255, 255));
				layeredPane.add(panelNuevaPlaylist, nuevaPlaylist);
				panelNuevaPlaylist.setLayout(null);

				metodos.cambiarDePanel(layeredPane, nuevaPlaylist);

				metodos.botonPerfil(layeredPane, panelNuevaPlaylist, user, perfil);
				metodos.botonAtras(layeredPane, misPlaylists, panelNuevaPlaylist);

				JTextField txtNombrePlaylist = new JTextField();
				txtNombrePlaylist.setColumns(10);
				txtNombrePlaylist.setBounds(412, 125, 175, 20);
				panelNuevaPlaylist.add(txtNombrePlaylist);

				metodos.crearLabel("Titulo Playlist:", 274, 125, 148, 20, panelNuevaPlaylist);

				JButton btnCrearPL = new JButton("Crear Playlist");
				btnCrearPL.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (txtNombrePlaylist.getText().equalsIgnoreCase("Favoritos")) {
							JOptionPane.showMessageDialog(null,
									"El nombre 'favoritos' solo es asignable por el administrador.");
						} else {
							conexionesBD.conexionInsertYDeleteSinMensaje(
									"INSERT IGNORE INTO Playlist (Titulo, FechaCreacion, IDCliente) VALUES ('"
											+ txtNombrePlaylist.getText() + "', '" + timeStamp + "', '"
											+ Usuario.getClienteID() + "')");
							JOptionPane.showMessageDialog(null, "Playlist creada con éxito.");
							crearPanelMisPlaylists();
							metodos.cambiarDePanel(layeredPane, misPlaylists);
						}
					}
				});
				btnCrearPL.setBounds(366, 394, 142, 29);
				panelNuevaPlaylist.add(btnCrearPL);

			}
		});

		btnNuevaPlayList.setBounds(639, 76, 170, 35);
		panelMisPlaylists.add(btnNuevaPlayList);

		JButton btnNuevaPlaylist = new JButton("Eliminar");
		btnNuevaPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelBorrarPlaylist = new JPanel();
				panelBorrarPlaylist.setBackground(new Color(255, 255, 255));
				layeredPane.add(panelBorrarPlaylist, borrarPlaylist);
				panelBorrarPlaylist.setLayout(null);

				metodos.cambiarDePanel(layeredPane, borrarPlaylist);

				metodos.botonPerfil(layeredPane, panelBorrarPlaylist, user, perfil);
				metodos.botonAtras(layeredPane, misPlaylists, panelBorrarPlaylist);

				JList<String> playlistLista = new JList<>();
				playlistLista.setBackground(SystemColor.menu);
				playlistLista.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				playlistLista.setBounds(10, 76, 600, 364);

				DefaultListModel<String> playlistModelList = new DefaultListModel<>();

				ArrayList<Playlist> playlistsArrayList = conexionesBD.conexionPlaylist(Usuario.getClienteID());

				if (playlistsArrayList != null) {
					for (Playlist playlist : playlistsArrayList) {
						playlistModelList.addElement(playlist.getTitulo());
					}
				} else {
					playlistModelList.addElement("No se han encontrado playlists disponibles");
				}
				playlistLista.setModel(playlistModelList);
				panelBorrarPlaylist.add(playlistLista);

				playlistLista.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int indexPlaylist = playlistLista.getSelectedIndex();
						if (indexPlaylist != -1) {
							playlistDeleteSeleccionada = playlistsArrayList.get(indexPlaylist);
							if (playlistDeleteSeleccionada.getTitulo().equalsIgnoreCase("Favoritos")) {
								JOptionPane.showMessageDialog(null,
										"No se pueden eliminar playlists generadas automáticamente.");
							} else {
								conexionesBD
										.conexionInsertYDeleteSinMensaje("DELETE FROM Playlist WHERE Playlist.IDList="
												+ playlistDeleteSeleccionada.getIDList() + ";");
								JOptionPane.showMessageDialog(null, "Playlist eliminada con éxito.");
							}
							crearPanelMisPlaylists();
							metodos.cambiarDePanel(layeredPane, misPlaylists);
						}
					}
				});
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

				try {
					LocalDateTime horaActual = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
					String horaActualFormateada = horaActual.format(formatter);

					File fichero = new File(Paths.get("").toAbsolutePath().toString() + "/src/playlists/"
							+ horaActualFormateada + ".txt");
					if (!fichero.exists()) {
						fichero.createNewFile();
					}
					FileWriter fic = new FileWriter(fichero);
					fic.write("**************  " + Usuario.getUsuario() + "'s Playlists  **************\n\n");
					for (Playlist playlist : playlistsArrayList) {
						fic.write(playlist.getTitulo() + "\n");

						ArrayList<Playlist> playlistCancionessArrayList = conexionesBD
								.conexionPlaylistCanciones(playlist.getIDList());
						for (Playlist playlistCancion : playlistCancionessArrayList) {
							fic.write("	-" + playlistCancion.getNombreMultimedia() + "\n");
						}

					}
					fic.write("\n\n**************  Rhythmicity  **************");
					fic.close();
					JOptionPane.showMessageDialog(null, "Playlists exportadas correctamente.");
					metodos.cambiarDePanel(layeredPane, menu);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExportar.setBounds(639, 216, 170, 35);
		panelMisPlaylists.add(btnExportar);

	}

	public void crearPanelPlaylist() {
		panelPlaylist = new JPanel();
		panelPlaylist.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelPlaylist, playlistPanel);
		panelPlaylist.setLayout(null);

		metodos.botonPerfil(layeredPane, panelPlaylist, user, perfil);
		metodos.botonAtras(layeredPane, misPlaylists, panelPlaylist);

		JList<String> playlistCancionesLista = new JList<>();
		playlistCancionesLista.setBackground(SystemColor.menu);
		playlistCancionesLista.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		playlistCancionesLista.setBounds(10, 76, 600, 364);

		DefaultListModel<String> playlistCancionesModelList = new DefaultListModel<>();
		ArrayList<Playlist> playlistCancionessArrayList = conexionesBD.conexionPlaylistCanciones(playlist.getIDList());

		if (playlistCancionessArrayList != null) {
			int numero;
			for (Playlist playlistCancion : playlistCancionessArrayList) {
				numero = (int) (Math.random() * (100 - 0) + 0);
				playlistCancionesModelList
						.addElement(playlistCancion.getNombreMultimedia() + " - " + playlistCancion.getNombreArtistico()
								+ " - " + numero + " reproducciones - " + playlistCancion.getDuracion());
			}
		} else {
			playlistCancionesModelList.addElement("No se han encontrado playlists disponibles");
		}
		playlistCancionesLista.setModel(playlistCancionesModelList);
		panelPlaylist.add(playlistCancionesLista);

		playlistCancionesLista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indexPlaylistCanciones = playlistCancionesLista.getSelectedIndex();
				if (indexPlaylistCanciones != -1) {
					playlistCanciones = playlistCancionessArrayList.get(indexPlaylistCanciones);
					metodos.cambiarDePanel(layeredPane, playlistPanel);
				}
			}
		});
	}

	public void crearPanelMenuAdmin() {
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

	public void crearPanelMenuGestionar(String opcionMenu) {
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

		switch (opcionMenu) {
		case swMusica:
			btnGestionar1.setText("Gestionar musicos");
			btnGestionar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swArtistas);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});

			btnGestionar2.setText("Gestionar albumes");
			btnGestionar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swAlbumes);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});

			btnGestionar3.setText("Gestionar canciones");
			btnGestionar3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelMenuEditar(swCanciones);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
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

			btnGestionar3.setText("Más Escuchadas");
			btnGestionar3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearPanelEstadisticas(swMasEscuchados);
					metodos.cambiarDePanel(layeredPane, estadisticas);
				}
			});
		}
	}

	public void crearPanelMenuEditar(String opcionGestionar) {
		panelMenuEditar = new JPanel();
		panelMenuEditar.setLayout(null);
		panelMenuEditar.setBackground(Color.WHITE);
		layeredPane.add(panelMenuEditar, editar);

		metodos.botonAtras(layeredPane, menuGestionar, panelMenuEditar);

		JLabel lblMenuEditar = new JLabel("¿Qué desea hacer?");
		lblMenuEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuEditar.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMenuEditar.setBounds(153, 59, 567, 83);
		panelMenuEditar.add(lblMenuEditar);

		JButton btnAnadir = new JButton("Añadir");
		btnAnadir.setBounds(304, 200, 265, 23);
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelFormularioAdmin(opcionGestionar);
				metodos.cambiarDePanel(layeredPane, formularioAdmin);
			}
		});
		panelMenuEditar.add(btnAnadir);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(304, 250, 265, 23);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelJlistAdmin(opcionGestionar);
				metodos.cambiarDePanel(layeredPane, jlistAdmin);
			}
		});
		panelMenuEditar.add(btnEliminar);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void crearPanelFormularioAdmin(String opcionGestionar) {
		panelFormularioAdmin = new JPanel();
		panelFormularioAdmin.setLayout(null);
		panelFormularioAdmin.setBackground(Color.WHITE);
		layeredPane.add(panelFormularioAdmin, formularioAdmin);

		metodos.botonAtras(layeredPane, editar, panelFormularioAdmin);

		JLabel lblFormularioAdmin = new JLabel("Introduzca los datos nuevos");
		lblFormularioAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormularioAdmin.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblFormularioAdmin.setBounds(153, 59, 600, 83);
		panelFormularioAdmin.add(lblFormularioAdmin);

		JTextField txtFormulario1 = new JTextField();
		txtFormulario1.setColumns(10);
		txtFormulario1.setBounds(450, 150, 175, 20);
		panelFormularioAdmin.add(txtFormulario1);

		JTextField txtFormulario2 = new JTextField();
		txtFormulario2.setColumns(10);
		txtFormulario2.setBounds(450, 181, 175, 20);
		panelFormularioAdmin.add(txtFormulario2);

		JTextField txtFormulario3 = new JTextField();
		txtFormulario3.setColumns(10);
		txtFormulario3.setBounds(450, 212, 175, 20);
		panelFormularioAdmin.add(txtFormulario3);

		JTextField txtFormulario4 = new JTextField();
		txtFormulario4.setColumns(10);
		txtFormulario4.setBounds(450, 243, 175, 20);
		panelFormularioAdmin.add(txtFormulario4);

		MaskFormatter duracionFormato = null;
		try {
			duracionFormato = new MaskFormatter("##:##:##");
			duracionFormato.setPlaceholderCharacter('#');
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JFormattedTextField ftxtFormulario = new JFormattedTextField(duracionFormato);
		ftxtFormulario.setBounds(450, 212, 175, 20);
		panelFormularioAdmin.add(ftxtFormulario);

		JComboBox comboBoxFormulario = new JComboBox();
		comboBoxFormulario.setBounds(450, 274, 175, 20);
		panelFormularioAdmin.add(comboBoxFormulario);

		JButton btnAnadirFormulario = new JButton("Aceptar");
		btnAnadirFormulario.setBounds(387, 350, 100, 23);
		panelFormularioAdmin.add(btnAnadirFormulario);

		switch (opcionGestionar) {
		case swArtistas:
			metodos.crearLabel("Nombre artistico:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Año desde el que lleva activo:", 260, 212, 200, 20, panelFormularioAdmin);
			metodos.crearLabel("Descripción:", 260, 243, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Característica:", 260, 274, 148, 20, panelFormularioAdmin);

			ftxtFormulario.setVisible(false);

			comboBoxFormulario.setModel(new DefaultComboBoxModel(new String[] { "Solista", "Grupo" }));

			btnAnadirFormulario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int anoActivo = Integer.parseInt(txtFormulario3.getText());

						Musico musicoNuevo = new Musico();
						musicoNuevo.setNombreArtistico(txtFormulario1.getText());
						musicoNuevo.setImagenArtista(txtFormulario2.getText());
						musicoNuevo.setCaracteristica(comboBoxFormulario.getSelectedItem().toString());
						musicoNuevo.setDescripcionArtista(txtFormulario4.getText());
						musicoNuevo.setAnoActivo(anoActivo);

						if (musicoNuevo.getNombreArtistico().length() <= 30
								&& musicoNuevo.getNombreArtistico().length() >= 1
								&& musicoNuevo.getImagenArtista().length() <= 30
								&& musicoNuevo.getImagenArtista().length() >= 1
								&& musicoNuevo.getDescripcionArtista().length() <= 30
								&& musicoNuevo.getDescripcionArtista().length() >= 1 && anoActivo <= ano) {

							String sentenciaSQL = "INSERT IGNORE INTO Musico (NombreArtistico, Imagen, Caracteristica, Descripcion, AnoActivo) VALUES ("
									+ "'" + musicoNuevo.getNombreArtistico() + "', '" + musicoNuevo.getImagenArtista()
									+ "', '" + musicoNuevo.getCaracteristica() + "', '"
									+ musicoNuevo.getDescripcionArtista() + "', '" + musicoNuevo.getAnoActivo() + "')";
							conexionesBD.conexionInsertYDelete(sentenciaSQL);
							metodos.cambiarDePanel(layeredPane, menuAdmin);
						} else {
							JOptionPane.showMessageDialog(null,
									"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres, y el año no puede superar el actual ("
											+ ano + ")");
							throw new PasarStringAintException();
						}
					} catch (PasarStringAintException e1) {
						e1.getMessage();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			break;

		case swAlbumes:
			metodos.crearLabel("Título:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Genero:", 260, 212, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Músico al que pertenece:", 260, 243, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Año:", 260, 274, 148, 20, panelFormularioAdmin);

			txtFormulario4.setVisible(false);
			ftxtFormulario.setVisible(false);

			DefaultComboBoxModel<String> modeloListaArtistas = new DefaultComboBoxModel<>();

			ArrayList<Musico> musicoArrayList = conexionesBD.conexionMusico();

			Album albumNuevo = new Album();

			int musicoArray[] = new int[musicoArrayList.size()];

			if (musicoArrayList != null) {
				int cont = 0;
				for (Musico musico : musicoArrayList) {
					modeloListaArtistas.addElement(musico.getNombreArtistico());

					musicoArray[cont] = musico.getArtistaID();
					cont++;
				}
			} else {
				modeloListaArtistas.addElement("No se han encontrado artistas disponibles");
			}
			comboBoxFormulario.setModel(modeloListaArtistas);
			comboBoxFormulario.setBounds(450, 243, 175, 20);

			JDateChooser fechaPubliAlbum = new JDateChooser();
			fechaPubliAlbum.setBounds(450, 274, 175, 20);
			panelFormularioAdmin.add(fechaPubliAlbum);

			JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaPubliAlbum.getDateEditor();
			editor.setEditable(false);

			try {
				fechaPubliAlbum.setMaxSelectableDate(dateFormat.parse(maxString));
				fechaPubliAlbum.setMinSelectableDate(dateFormat.parse(minString));
				fechaPubliAlbum.setDate(dateFormat.parse(maxString));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			btnAnadirFormulario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					albumNuevo.setTituloAlbum(txtFormulario1.getText());
					albumNuevo.setImagenAlbum(txtFormulario2.getText());
					albumNuevo.setGeneroAlbum(txtFormulario3.getText());
					albumNuevo.setArtistaID(musicoArray[comboBoxFormulario.getSelectedIndex()]);
					albumNuevo.setAnoAlbum(dateFormat.format(fechaPubliAlbum.getDate()));

					if (albumNuevo.getTituloAlbum().length() <= 30 && albumNuevo.getTituloAlbum().length() >= 1
							&& albumNuevo.getImagenAlbum().length() <= 30 && albumNuevo.getImagenAlbum().length() >= 1
							&& albumNuevo.getGeneroAlbum().length() <= 30
							&& albumNuevo.getGeneroAlbum().length() >= 1) {

						String sentenciaSQL = "INSERT IGNORE INTO Album (Titulo, Ano, Genero, Imagen, IDMusico) VALUES ('"
								+ albumNuevo.getTituloAlbum() + "', '" + albumNuevo.getAnoAlbum() + "'" + ", " + "'"
								+ albumNuevo.getGeneroAlbum() + "', '" + albumNuevo.getImagenAlbum() + "', '"
								+ albumNuevo.getArtistaID() + "');";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);
						metodos.cambiarDePanel(layeredPane, menuAdmin);
					} else
						JOptionPane.showMessageDialog(null,
								"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres, y el año no puede superar el actual ("
										+ ano + ")");
				}
			});
			break;

		case swCanciones:
			metodos.crearLabel("Nombre:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Duración:", 260, 212, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Album al que pertenece:", 260, 243, 148, 20, panelFormularioAdmin);

			txtFormulario3.setVisible(false);
			txtFormulario4.setVisible(false);

			DefaultComboBoxModel<String> modeloListaAlbum = new DefaultComboBoxModel<>();

			ArrayList<Album> albumArrayList = conexionesBD.conexionAlbumAdmin();

			Cancion cancionNueva = new Cancion();

			int albumesArray[] = new int[albumArrayList.size()];

			if (albumArrayList != null) {
				int cont = 0;
				for (Album album : albumArrayList) {
					modeloListaAlbum.addElement(album.getTituloAlbum());

					albumesArray[cont] = album.getAlbumID();
					cont++;
				}
			} else {
				modeloListaAlbum.addElement("No se han encontrado albumes disponibles");
			}
			comboBoxFormulario.setModel(modeloListaAlbum);
			comboBoxFormulario.setBounds(450, 243, 175, 20);

			btnAnadirFormulario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancionNueva.setNombreMultimedia(txtFormulario1.getText());
					cancionNueva.setImagenMultimedia(txtFormulario2.getText());
					cancionNueva.setDuracion(ftxtFormulario.getText());
					cancionNueva.setAlbumID(albumesArray[comboBoxFormulario.getSelectedIndex()]);

					if (cancionNueva.getNombreMultimedia().length() <= 30
							&& cancionNueva.getNombreMultimedia().length() >= 1
							&& cancionNueva.getImagenMultimedia().length() <= 30
							&& cancionNueva.getImagenMultimedia().length() >= 1) {

						String sentenciaSQL = "INSERT IGNORE INTO Audio (Nombre, Duracion, Imagen, Tipo) VALUES ('"
								+ cancionNueva.getNombreMultimedia() + "', '" + cancionNueva.getDuracion() + "', '"
								+ cancionNueva.getImagenMultimedia() + "'" + ", " + "'Cancion');";
						conexionesBD.conexionInsertYDeleteSinMensaje(sentenciaSQL);

						int idAudio = 0;

						try {
							idAudio = conexionesBD.conexionSelectIDAudio();
						} catch (ConexionFallidaException e1) {
							e1.getMessage();
						}

						sentenciaSQL = "INSERT IGNORE INTO Cancion (IDAudio, IDAlbum) VALUES ('" + idAudio + "', '"
								+ cancionNueva.getAlbumID() + "');";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);

						metodos.cambiarDePanel(layeredPane, menuAdmin);
					} else
						JOptionPane.showMessageDialog(null,
								"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres");
				}
			});
			break;

		case swPodcasters:
			metodos.crearLabel("Nombre Artistico:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Año desde el que lleva activo:", 260, 212, 200, 20, panelFormularioAdmin);
			metodos.crearLabel("Descripción:", 260, 243, 148, 20, panelFormularioAdmin);

			comboBoxFormulario.setVisible(false);
			ftxtFormulario.setVisible(false);

			btnAnadirFormulario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int anoActivo = Integer.parseInt(txtFormulario3.getText());

						Podcaster podcasterNuevo = new Podcaster();
						podcasterNuevo.setNombreArtistico(txtFormulario1.getText());
						podcasterNuevo.setImagenArtista(txtFormulario2.getText());
						podcasterNuevo.setDescripcionArtista(txtFormulario4.getText());
						podcasterNuevo.setAnoActivo(anoActivo);

						if (podcasterNuevo.getNombreArtistico().length() <= 30
								&& podcasterNuevo.getNombreArtistico().length() >= 1
								&& podcasterNuevo.getImagenArtista().length() <= 30
								&& podcasterNuevo.getImagenArtista().length() >= 1
								&& podcasterNuevo.getDescripcionArtista().length() <= 30
								&& podcasterNuevo.getDescripcionArtista().length() >= 1 && anoActivo <= ano) {

							String sentenciaSQL = "INSERT IGNORE INTO Podcaster (NombreArtistico, Imagen, Descripcion, AnoActivo) VALUES ("
									+ "'" + podcasterNuevo.getNombreArtistico() + "', '"
									+ podcasterNuevo.getImagenArtista() + "', '"
									+ podcasterNuevo.getDescripcionArtista() + "', '" + podcasterNuevo.getAnoActivo()
									+ "')";
							conexionesBD.conexionInsertYDelete(sentenciaSQL);
							metodos.cambiarDePanel(layeredPane, menuAdmin);
						} else {
							JOptionPane.showMessageDialog(null,
									"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres, y el año no puede superar el actual ("
											+ ano + ")");
							throw new PasarStringAintException();
						}
					} catch (PasarStringAintException e1) {
						e1.getMessage();
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "El año debe ser un número");
					}
				}
			});
			break;

		case swPodcasts2:
			metodos.crearLabel("Nombre:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Duración:", 260, 212, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Podcaster al que pertenece:", 260, 243, 148, 20, panelFormularioAdmin);

			txtFormulario3.setVisible(false);
			txtFormulario4.setVisible(false);

			DefaultComboBoxModel<String> modeloListaPodcaster = new DefaultComboBoxModel<>();

			ArrayList<Podcaster> podcasterArrayList = conexionesBD.conexionPodcaster();

			Podcast podcastNuevo = new Podcast();

			int podcastersArray[] = new int[podcasterArrayList.size()];

			if (podcasterArrayList != null) {
				int cont = 0;
				for (Podcaster podcaster : podcasterArrayList) {
					modeloListaPodcaster.addElement(podcaster.getNombreArtistico());

					podcastersArray[cont] = podcaster.getArtistaID();
					cont++;
				}
			} else {
				modeloListaPodcaster.addElement("No se han encontrado albumes disponibles");
			}
			comboBoxFormulario.setModel(modeloListaPodcaster);
			comboBoxFormulario.setBounds(450, 243, 175, 20);

			btnAnadirFormulario.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					podcastNuevo.setNombreMultimedia(txtFormulario1.getText());
					podcastNuevo.setImagenMultimedia(txtFormulario2.getText());
					podcastNuevo.setDuracion(ftxtFormulario.getText());
					podcastNuevo.setArtistaID(podcastersArray[comboBoxFormulario.getSelectedIndex()]);

					if (podcastNuevo.getNombreMultimedia().length() <= 30
							&& podcastNuevo.getNombreMultimedia().length() >= 1
							&& podcastNuevo.getImagenMultimedia().length() <= 30
							&& podcastNuevo.getImagenMultimedia().length() >= 1) {

						String sentenciaSQL = "INSERT IGNORE INTO Audio (Nombre, Duracion, Imagen, Tipo) VALUES ('"
								+ podcastNuevo.getNombreMultimedia() + "', '" + podcastNuevo.getDuracion() + "', '"
								+ podcastNuevo.getImagenMultimedia() + "'" + ", " + "'Podcast');";
						conexionesBD.conexionInsertYDeleteSinMensaje(sentenciaSQL);

						int idAudio = 0;

						try {
							idAudio = conexionesBD.conexionSelectIDAudio();
						} catch (ConexionFallidaException e1) {
							e1.getMessage();
						}

						sentenciaSQL = "INSERT IGNORE INTO Podcast (IDAudio, IDPodcaster) VALUES ('" + idAudio + "', '"
								+ podcastNuevo.getArtistaID() + "');";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);

						metodos.cambiarDePanel(layeredPane, menuAdmin);
					} else
						JOptionPane.showMessageDialog(null,
								"Los campos no pueden estar vacíos y deben tener menos de 30 caracteres");
				}

			});

			break;
		}

	}

	public void crearPanelJlistAdmin(String opcionGestionar) {
		panelJlistAdmin = new JPanel();
		panelJlistAdmin.setLayout(null);
		panelJlistAdmin.setBackground(Color.WHITE);
		layeredPane.add(panelJlistAdmin, jlistAdmin);

		metodos.botonAtras(layeredPane, editar, panelJlistAdmin);

		JLabel lblJlistAdmin = new JLabel("¿Qué desea eliminar?");
		lblJlistAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblJlistAdmin.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblJlistAdmin.setBounds(140, 40, 600, 83);
		panelJlistAdmin.add(lblJlistAdmin);

		switch (opcionGestionar) {
		case swArtistas:
			JList<String> listaMusicoAdmin = new JList<String>();
			listaMusicoAdmin.setBackground(SystemColor.menu);
			listaMusicoAdmin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaMusicoAdmin.setBounds(246, 120, 382, 295);

			DefaultListModel<String> modeloLista = new DefaultListModel<>();

			ArrayList<Musico> musicoArrayList = conexionesBD.conexionMusico();

			if (musicoArrayList != null) {
				for (Musico musico : musicoArrayList) {
					modeloLista.addElement(musico.getNombreArtistico());
				}
			} else {
				modeloLista.addElement("No se han encontrado artistas disponibles");
			}
			listaMusicoAdmin.setModel(modeloLista);

			listaMusicoAdmin.addMouseListener(new MouseAdapter() {
				// AL HACER CLICK EN EL ARTISTA SE EJECUTA
				public void mouseClicked(MouseEvent e) {
					int index = listaMusicoAdmin.getSelectedIndex();
					musicoSeleccionado = musicoArrayList.get(index);
					musicoSeleccionado.getNombreArtistico();
					String sentenciaSQL = "DELETE FROM Musico WHERE Musico.IDMusico = '"
							+ musicoSeleccionado.getArtistaID() + "';";
					conexionesBD.conexionInsertYDelete(sentenciaSQL);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			panelJlistAdmin.add(listaMusicoAdmin);
			break;

		case swAlbumes:
			JList<String> listaAlbumesAdmin = new JList<>();
			listaAlbumesAdmin.setBackground(SystemColor.menu);
			listaAlbumesAdmin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaAlbumesAdmin.setBounds(246, 120, 382, 295);

			DefaultListModel<String> albumesModelList = new DefaultListModel<>();
			ArrayList<Album> albumesArrayList = conexionesBD.conexionAlbumAdmin();

			if (albumesArrayList != null) {
				for (Album album : albumesArrayList) {
					albumesModelList.addElement(album.getTituloAlbum());
				}
			} else {
				albumesModelList.addElement("No se han encontrado albumes disponibles");
			}
			listaAlbumesAdmin.setModel(albumesModelList);

			listaAlbumesAdmin.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int indexAlbum = listaAlbumesAdmin.getSelectedIndex();
					if (indexAlbum != -1) {
						albumSeleccionado = albumesArrayList.get(indexAlbum);
						albumSeleccionado.getAlbumID();
						String sentenciaSQL = "DELETE FROM Album WHERE Album.IDAlbum = '"
								+ albumSeleccionado.getAlbumID() + "';";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);
						metodos.cambiarDePanel(layeredPane, editar);
					}
				}
			});
			panelJlistAdmin.add(listaAlbumesAdmin);
			break;

		case swCanciones:
			JList<String> listaCancionesAdmin = new JList<>();
			listaCancionesAdmin.setBackground(SystemColor.menu);
			listaCancionesAdmin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaCancionesAdmin.setBounds(246, 120, 382, 295);

			DefaultListModel<String> cancionesModelList = new DefaultListModel<>();
			ArrayList<Cancion> cancionesArrayList = conexionesBD.conexionCancionAdmin();

			if (cancionesArrayList != null) {
				for (Cancion multimedia : cancionesArrayList) {
					cancionesModelList.addElement(multimedia.getNombreMultimedia());
				}
			} else {
				cancionesModelList.addElement("No se han encontrado canciones disponibles");
			}
			listaCancionesAdmin.setModel(cancionesModelList);

			listaCancionesAdmin.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int indexCancion = listaCancionesAdmin.getSelectedIndex();
					if (indexCancion != -1) {
						cancionSeleccionada = cancionesArrayList.get(indexCancion);
						cancionSeleccionada.getAudioID();
						String sentenciaSQL = "DELETE FROM Audio WHERE Audio.IDAudio = '"
								+ cancionSeleccionada.getAudioID() + "';";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);
						metodos.cambiarDePanel(layeredPane, editar);
					}
				}
			});
			panelJlistAdmin.add(listaCancionesAdmin);

			JScrollPane scrollPaneCanciones = new JScrollPane(listaCancionesAdmin);
			scrollPaneCanciones.setSize(382, 295);
			scrollPaneCanciones.setLocation(246, 120);
			panelJlistAdmin.add(scrollPaneCanciones);
			break;

		case swPodcasters:
			JList<String> listaPodcaster = new JList<String>();
			listaPodcaster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaPodcaster.setBackground(SystemColor.menu);
			listaPodcaster.setBounds(246, 120, 382, 295);

			DefaultListModel<String> modeloListaPodcaster = new DefaultListModel<>();

			ArrayList<Podcaster> podcasterArrayList = conexionesBD.conexionPodcaster();

			if (podcasterArrayList != null) {
				for (Podcaster podcaster : podcasterArrayList) {
					modeloListaPodcaster.addElement(podcaster.getNombreArtistico());
				}
			} else {
				modeloListaPodcaster.addElement("No se han encontrado podcasters disponibles");
			}
			listaPodcaster.setModel(modeloListaPodcaster);

			listaPodcaster.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int index = listaPodcaster.getSelectedIndex();
					podcasterSeleccionado = podcasterArrayList.get(index);
					podcasterSeleccionado.getNombreArtistico();
					String sentenciaSQL = "DELETE FROM Podcaster WHERE Podcaster.IDPodcaster = '"
							+ podcasterSeleccionado.getArtistaID() + "';";
					conexionesBD.conexionInsertYDelete(sentenciaSQL);
					metodos.cambiarDePanel(layeredPane, editar);
				}
			});
			panelJlistAdmin.add(listaPodcaster);
			break;

		case swPodcasts2:
			JList<String> listaPodcasts = new JList<>();
			listaPodcasts.setBackground(SystemColor.menu);
			listaPodcasts.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			listaPodcasts.setBounds(246, 120, 382, 295);

			DefaultListModel<String> podcastsModelList = new DefaultListModel<>();

			ArrayList<Podcast> podcastsArrayList = conexionesBD.conexionPodcastAdmin();

			if (podcastsArrayList != null) {
				for (Podcast multimedia : podcastsArrayList) {
					podcastsModelList.addElement(multimedia.getNombreMultimedia());
				}
			} else {
				podcastsModelList.addElement("No se han encontrado podcasts disponibles");
			}
			listaPodcasts.setModel(podcastsModelList);
			panelJlistAdmin.add(listaPodcasts);

			listaPodcasts.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					int indexCancion = listaPodcasts.getSelectedIndex();
					if (indexCancion != -1) {
						podcastSeleccionado = podcastsArrayList.get(indexCancion);
						podcastSeleccionado.getAudioID();
						String sentenciaSQL = "DELETE FROM Audio WHERE Audio.IDAudio = '"
								+ podcastSeleccionado.getAudioID() + "';";
						conexionesBD.conexionInsertYDelete(sentenciaSQL);
						metodos.cambiarDePanel(layeredPane, editar);
					}
				}
			});
			break;
		}
	}

	public void crearPanelEstadisticas(String opcionEstadisticas) {
		panelEstadisticas = new JPanel();
		panelEstadisticas.setLayout(null);
		panelEstadisticas.setBackground(Color.WHITE);
		layeredPane.add(panelEstadisticas, estadisticas);

		metodos.botonAtras(layeredPane, menuGestionar, panelEstadisticas);

		JLabel lblGestionarMusica = new JLabel();
		lblGestionarMusica.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionarMusica.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblGestionarMusica.setBounds(150, 40, 567, 83);
		panelEstadisticas.add(lblGestionarMusica);

		switch (opcionEstadisticas) {
		case swCancionesMasGustadas:
			ArrayList<Cancion> masGustadas = conexionesBD.conexionMasGustadas();

			Object[][] celdasMasGustadas = new Object[9][4];
			String[] titulosMasGustadas = { "Posición", "Músico", "Canción", "Número de me gustas" };

			JTable tablaMasGustadas = new JTable();
			tablaMasGustadas.setDefaultEditor(Object.class, null);
			tablaMasGustadas.setRowSelectionAllowed(false);

			DefaultTableModel datosMasGustadas = new DefaultTableModel(celdasMasGustadas, titulosMasGustadas);

			for (int i = 0; i < 9; i++) {
				Cancion cancionMasEscuchada = masGustadas.get(i);
				datosMasGustadas.setValueAt(i + 1, i, 0);
				datosMasGustadas.setValueAt(cancionMasEscuchada.getNombreArtistico(), i, 1);
				datosMasGustadas.setValueAt(cancionMasEscuchada.getNombreMultimedia(), i, 2);
				datosMasGustadas.setValueAt(cancionMasEscuchada.getNumeroMeGustas(), i, 3);
			}
			tablaMasGustadas.setModel(datosMasGustadas);
			tablaMasGustadas.setBounds(87, 153, 699, 144);
			panelEstadisticas.add(tablaMasGustadas);

			JScrollPane scrollPaneMasGustadas = new JScrollPane(tablaMasGustadas);
			scrollPaneMasGustadas.setBounds(87, 160, 699, 167);
			panelEstadisticas.add(scrollPaneMasGustadas);

			lblGestionarMusica.setText("Canciones más gustadas");
			break;

		case swPodcastMasGustados:
			ArrayList<Podcast> masGustados = conexionesBD.conexionMasGustados();

			Object[][] celdasMasGustados = new Object[9][4];
			String[] titulosMasGustados = { "Posición", "Podcaster", "Podcast", "Número de me gustas" };

			JTable tablaMasGustados = new JTable();
			tablaMasGustados.setDefaultEditor(Object.class, null);
			tablaMasGustados.setRowSelectionAllowed(false);

			DefaultTableModel datosMasGustados = new DefaultTableModel(celdasMasGustados, titulosMasGustados);

			for (int i = 0; i < 9; i++) {
				if (i < masGustados.size()) {
					Podcast cancionMasEscuchada = masGustados.get(i);
					datosMasGustados.setValueAt(i + 1, i, 0);
					datosMasGustados.setValueAt(cancionMasEscuchada.getNombreArtistico(), i, 1);
					datosMasGustados.setValueAt(cancionMasEscuchada.getNombreMultimedia(), i, 2);
					datosMasGustados.setValueAt(cancionMasEscuchada.getNumeroMeGustas(), i, 3);
				} else {
					datosMasGustados.setValueAt(i + 1, i, 0);
					datosMasGustados.setValueAt(null, i, 1);
					datosMasGustados.setValueAt(null, i, 2);
					datosMasGustados.setValueAt(null, i, 3);
				}
			}
			tablaMasGustados.setModel(datosMasGustados);
			tablaMasGustados.setBounds(87, 153, 699, 144);
			panelEstadisticas.add(tablaMasGustados);

			JScrollPane scrollPaneMasGustados = new JScrollPane(tablaMasGustados);
			scrollPaneMasGustados.setBounds(87, 160, 699, 167);
			panelEstadisticas.add(scrollPaneMasGustados);

			lblGestionarMusica.setText("Podcast más gustados");
			break;

		case swMasEscuchados:
			ArrayList<Cancion> masEscuchados = conexionesBD.conexionMasEscuchadas();

			Object[][] celdasMasEscuchados = new Object[9][4];
			String[] titulosMasEscuchados = { "Posición", "Músico", "Canción", "Reproducciones" };

			JTable tablaMasEscuchados = new JTable();
			tablaMasEscuchados.setDefaultEditor(Object.class, null);
			tablaMasEscuchados.setRowSelectionAllowed(false);

			DefaultTableModel datosMasEscuchados = new DefaultTableModel(celdasMasEscuchados, titulosMasEscuchados);

			for (int i = 0; i < 9; i++) {
				Cancion cancionMasEscuchada = masEscuchados.get(i);
				datosMasEscuchados.setValueAt(i + 1, i, 0);
				datosMasEscuchados.setValueAt(cancionMasEscuchada.getNombreArtistico(), i, 1);
				datosMasEscuchados.setValueAt(cancionMasEscuchada.getNombreMultimedia(), i, 2);
				datosMasEscuchados.setValueAt(cancionMasEscuchada.getReproducciones(), i, 3);
			}
			tablaMasEscuchados.setModel(datosMasEscuchados);
			tablaMasEscuchados.setBounds(87, 153, 699, 144);
			panelEstadisticas.add(tablaMasEscuchados);

			JScrollPane scrollPaneMasEscuchados = new JScrollPane(tablaMasEscuchados);
			scrollPaneMasEscuchados.setBounds(87, 160, 699, 167);
			panelEstadisticas.add(scrollPaneMasEscuchados);

			lblGestionarMusica.setText("Más escuchadas");
			break;
		}
	}
}