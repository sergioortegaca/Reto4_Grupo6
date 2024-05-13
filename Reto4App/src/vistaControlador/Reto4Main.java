package vistaControlador;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import modelo.*;

public class Reto4Main extends JFrame {
	private static final long serialVersionUID = 1L;
//CAMBIAR BOTON PERFIL
	Metodos metodos = new Metodos();
	BDConexiones conexionesBD = new BDConexiones();

	JPanel contentPane, panelBienvenida, panelLogin, panelRegistro, panelPerfil, panelMenuAdmin, panelMenuGestionar,
			panelEstadisticas, panelEditar, panelDescubrirMusica, panelArtista, panelAlbum, panelReproduccion,
			panelDescubrirPodcasts, panelMisPlaylists, panelMenu, panelMenuEditar, panelFormularioAdmin,
			panelJlistAdmin;

	JLayeredPane layeredPane;

	JTextField txtUsuario, txtNombre, txtApellidos, txtUsuarioRegistro;

	JPasswordField pswContrasena, pswCrearContrasena;

	JDateChooser fechaNacimientoCalendar;
	SimpleDateFormat dateFormat;

	String user = "";
	String albumTit = "", albumAno = "", albumGen = "";

	// COGER EL DIA ACTUAL PARA EL REGISTRO:
	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

	// NOMBRES PANELES
	String bienvenida = "bienvenida", login = "login", registro = "registro", menu = "menu", perfil = "perfil",
			menuAdmin = "menuAdmin", descubrirMusica = "descubrirMusica", artistaPanel = "artista",
			albumPanel = "album", reproduccion = "reproduccion", descubrirPodcasts = "descubrirPodcasts",
			misPlayLists = "misPlayLists", estadisticas = "estadisticas", editar = "editar",
			menuGestionar = "menugestionar", formularioAdmin = "formularioAdmin", jlistAdmin = "jlistAdmin";

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
	Cancion cancionSeleccionada2;
	Musico artistaSeleccionado;
	UsuarioFree UsuarioNuevo, Usuario;

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

	public void crearPanelesMain() {

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

	private void crearPanelLogin() {
		panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelLogin, login);
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioDeSesion.setBounds(0, 80, 874, 33);
		panelLogin.add(lblInicioDeSesion);

		metodos.crearLabel("Usuario:", 310, 180, 109, 14, panelLogin);

		metodos.crearLabel("Contraseña:", 310, 220, 109, 14, panelLogin);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(410, 180, 148, 20);
		panelLogin.add(txtUsuario);

		pswContrasena = new JPasswordField();
		pswContrasena.setBounds(410, 220, 148, 20);
		panelLogin.add(pswContrasena);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					crearPanelesMain();
					Usuario = new UsuarioFree();
					conexionesBD.conexionLogin(textOk, user, pass, menu, layeredPane, Usuario);
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
				metodos.cambiarDePanel(layeredPane, registro);
			}
		});
		btnCrear.setBounds(255, 283, 159, 23);
		panelLogin.add(btnCrear);
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

		metodos.crearLabel("Nombre:", 274, 125, 148, 20, panelRegistro);
		metodos.crearLabel("Apellidos:", 274, 156, 148, 20, panelRegistro);
		metodos.crearLabel("Nombre de usuario:", 274, 187, 148, 20, panelRegistro);
		metodos.crearLabel("Contraseña:", 274, 218, 148, 20, panelRegistro);
		metodos.crearLabel("Fecha de nacimiento:", 274, 250, 148, 20, panelRegistro);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(412, 248, 175, 20);
		panelRegistro.add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR) - 12;
		String dateString = "1905-01-01";
		String maxString = year + "-12-31";

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(dateString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
					conexionesBD.conexionRegistro(UsuarioNuevo, login, layeredPane);
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

		metodos.botonAtras(layeredPane, menu, panelPerfil);

		JLabel lblTituloCrear = new JLabel("Crear Usuario");
		lblTituloCrear.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTituloCrear.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCrear.setBounds(0, 40, 874, 33);
		panelPerfil.add(lblTituloCrear);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(412, 125, 175, 20);
		panelPerfil.add(txtNombre);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(412, 156, 175, 20);
		panelPerfil.add(txtApellidos);

		txtUsuarioRegistro = new JTextField();
		txtUsuarioRegistro.setColumns(10);
		txtUsuarioRegistro.setBounds(412, 187, 175, 20);
		panelPerfil.add(txtUsuarioRegistro);

		pswCrearContrasena = new JPasswordField();
		pswCrearContrasena.setBounds(412, 218, 175, 20);
		panelPerfil.add(pswCrearContrasena);

		metodos.crearLabel("Nombre:", 274, 125, 148, 20, panelPerfil);
		metodos.crearLabel("Apellidos:", 274, 156, 148, 20, panelPerfil);
		metodos.crearLabel("Nombre de usuario:", 274, 187, 148, 20, panelPerfil);
		metodos.crearLabel("Contraseña:", 274, 218, 148, 20, panelPerfil);
		metodos.crearLabel("Fecha de nacimiento:", 274, 250, 148, 20, panelPerfil);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(412, 248, 175, 20);
		panelPerfil.add(fechaNacimientoCalendar);

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

		ArrayList<Musico> musicoArrayList = conexionesBD.conexionMusico();

		if (musicoArrayList != null) {
			for (Musico musico : musicoArrayList) {
				modeloLista.addElement(
						musico.getNombreArtistico() + " (" + musico.getReproducciones() + " reproducciones)");
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
				artistaSeleccionado = musicoArrayList.get(index);
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

		ArrayList<Album> albumesArrayList = conexionesBD.conexionAlbum(artistaSeleccionado.getNombreArtistico());

		if (albumesArrayList != null) {
			for (Album album : albumesArrayList) {
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
					albumSeleccionado = albumesArrayList.get(indexAlbum);
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

		ArrayList<Cancion> cancionesArrayList = conexionesBD.conexionCancion(albumSeleccionado.getTituloAlbum());

		if (cancionesArrayList != null) {
			for (Cancion multimedia : cancionesArrayList) {

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
					cancionSeleccionada = cancionesArrayList.get(indexCancion);
					//System.out.println(indexCancion);
					crearPanelReproduccion();
					metodos.cambiarDePanel(layeredPane, reproduccion);	

				}
			}
		});

		// TextArea
		JTextArea textAreaInfoAlbum = new JTextArea("Título: " + albumSeleccionado.getTituloAlbum() + "\nGénero: "
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
		metodos.estaSonando(cancionSeleccionada, albumSeleccionado, artistaSeleccionado, panelReproduccion);
		metodos.botonPerfil(layeredPane, panelReproduccion, user);
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
					cancionSeleccionada.pause();
					numCancion--;
					cancionSeleccionada.loadClip("/audio/" + numCancion + ".wav");
					cancionSeleccionada.setAudioID(numCancion);
					atras = 0;
					cancionSeleccionada.play();
				//	cancionSeleccionada = BDConexiones.nuevaCancion(cancionSeleccionada);
					metodos.estaSonando(cancionSeleccionada, albumSeleccionado, artistaSeleccionado, panelReproduccion);
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
				btnReproPlay.setVisible(false);

				JButton btnReproPause = new JButton("Pause");
				btnReproPause.setBounds(392, 299, 89, 28);
				btnReproPause.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancionSeleccionada.pause();
						btnReproPause.setVisible(false);
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
				cancionSeleccionada.setAudioID(numCancion);
				cancionSeleccionada.play();
				metodos.estaSonando(cancionSeleccionada, albumSeleccionado, artistaSeleccionado, panelReproduccion);

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

		JList<String> listaPodcaster = new JList<String>();
		listaPodcaster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaPodcaster.setBackground(SystemColor.menu);
		DefaultListModel<String> modeloLista2 = new DefaultListModel<>();

		ArrayList<Podcaster> podcasterArrayList = conexionesBD.conexionPodcaster();

		if (podcasterArrayList != null) {
			for (Podcaster podcaster : podcasterArrayList) {
				modeloLista2.addElement(podcaster.getNombreArtistico());
			}
		} else {
			modeloLista2.addElement("No se han encontrado artistas disponibles");
		}
		listaPodcaster.setModel(modeloLista2);
		listaPodcaster.setBounds(246, 120, 382, 295);
		listaPodcaster.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index2 = listaPodcaster.getSelectedIndex();
				podcasterSeleccionado = modeloLista2.getElementAt(index2);

			}
		});

		panelDescubrirPodcasts.add(listaPodcaster);

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

		JLabel lblMenuEditar = new JLabel("¿Qué desea hacer?");
		lblMenuEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuEditar.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMenuEditar.setBounds(153, 59, 567, 83);
		panelMenuEditar.add(lblMenuEditar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(304, 171, 265, 23);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelFormularioAdmin(opcionGestionar);
				metodos.cambiarDePanel(layeredPane, jlistAdmin);
			}
		});
		panelMenuEditar.add(btnModificar);

		JButton btnAnadir = new JButton("Añadir");
		btnAnadir.setBounds(304, 219, 265, 23);
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelFormularioAdmin(opcionGestionar);
				metodos.cambiarDePanel(layeredPane, formularioAdmin);
			}
		});
		panelMenuEditar.add(btnAnadir);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(304, 267, 265, 23);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPanelJlistAdmin(opcionGestionar);
				metodos.cambiarDePanel(layeredPane, jlistAdmin);
			}
		});
		panelMenuEditar.add(btnEliminar);
	}

	private void crearPanelFormularioAdmin(String opcionGestionar) {
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
		txtFormulario1.setBounds(412, 150, 175, 20);
		panelFormularioAdmin.add(txtFormulario1);

		JTextField txtFormulario2 = new JTextField();
		txtFormulario2.setColumns(10);
		txtFormulario2.setBounds(412, 181, 175, 20);
		panelFormularioAdmin.add(txtFormulario2);

		JTextField txtFormulario3 = new JTextField();
		txtFormulario3.setColumns(10);
		txtFormulario3.setBounds(412, 212, 175, 20);
		panelFormularioAdmin.add(txtFormulario3);

		JTextField txtFormulario4 = new JTextField();
		txtFormulario4.setColumns(10);
		txtFormulario4.setBounds(412, 243, 175, 20);
		panelFormularioAdmin.add(txtFormulario4);

		JTextField txtFormulario5 = new JTextField();
		txtFormulario5.setColumns(10);
		txtFormulario5.setBounds(412, 274, 175, 20);
		panelFormularioAdmin.add(txtFormulario5);

		JButton btnAnadirFormulario = new JButton("Aceptar");
		btnAnadirFormulario.setBounds(387, 350, 100, 23);
		panelFormularioAdmin.add(btnAnadirFormulario);

		switch (opcionGestionar) {
		case swArtistas:
			metodos.crearLabel("Nombre Artistico:", 274, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 274, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Caracteristica:", 274, 212, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Descripción:", 274, 243, 148, 20, panelFormularioAdmin);
			txtFormulario5.setVisible(false);

			btnAnadirFormulario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});

			break;
		case swAlbumes:
			metodos.crearLabel("Título:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Año:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Genero:", 260, 212, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 243, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Músico al que pertenece:", 260, 274, 148, 20, panelFormularioAdmin);

			break;
		case swCanciones:
			metodos.crearLabel("Nombre:", 260, 150, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Duración:", 260, 181, 148, 20, panelFormularioAdmin);
			metodos.crearLabel("Imagen:", 260, 212, 148, 20, panelFormularioAdmin);
			txtFormulario4.setVisible(false);
			txtFormulario5.setVisible(false);

			break;
		case swPodcasters:

			break;
		case swPodcasts2:

			break;
		}

	}

	private void crearPanelJlistAdmin(String opcionGestionar) {
		panelJlistAdmin = new JPanel();
		panelJlistAdmin.setLayout(null);
		panelJlistAdmin.setBackground(Color.WHITE);
		layeredPane.add(panelJlistAdmin, jlistAdmin);

		metodos.botonAtras(layeredPane, editar, panelJlistAdmin);

		switch (opcionGestionar) {
		case swArtistas:

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

	private void crearPanelEstadisticas(String opcionEstadisticas) {
		panelEstadisticas = new JPanel();
		panelEstadisticas.setLayout(null);
		panelEstadisticas.setBackground(Color.WHITE);
		layeredPane.add(panelEstadisticas, estadisticas);

		metodos.botonAtras(layeredPane, menuGestionar, panelEstadisticas);
	}
}
