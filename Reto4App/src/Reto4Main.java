import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class Reto4Main extends JFrame {
	private JTextField txtUsuario;
	private JPasswordField pswContrasena;
	private JButton btnVolverPelis;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDni;
	private JPasswordField pswCrearContrasena;
	Metodos metodos = new Metodos();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(230, 130, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		// COGER LA HORA ACTUAL PARA EL REGISTRO: String timeStamp = new
		// SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "LayeredPane");
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel panelBienvenida = new JPanel();
		layeredPane.add(panelBienvenida, "Bienvenida");
		panelBienvenida.addMouseListener((MouseListener) new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				metodos.cambiarDePanel(layeredPane, "Login");
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

		JLabel lblNewLabel = new JLabel("ergrthdrtrth");
		lblNewLabel.setBounds(398, 218, 174, 14);
		panelBienvenida.add(lblNewLabel);

		JPanel panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelLogin, "Login");
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
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
				/*
				 * try { // Conexión con la base de datos Connection conexion =
				 * DriverManager.getConnection("jdbc:mysql://localhost:33060/reto3grupo6_m",
				 * "mañana", "elorrieta");
				 * 
				 * // Consulta PreparedStatement sentencia = (PreparedStatement) conexion
				 * .prepareStatement("SELECT dni, contraseña from clientes where dni=? and contraseña=?"
				 * );
				 * 
				 * sentencia.setString(1, txtUsuario.getText()); sentencia.setString(2, new
				 * String(pswContrasena.getPassword())); ResultSet rs =
				 * sentencia.executeQuery();
				 * 
				 * // Condición para comprobar si el usuario y la contraseña son correctos if
				 * (rs.next()) { JOptionPane.showMessageDialog(null,
				 * "Sesión iniciada correctamente");
				 */
				metodos.cambiarDePanel(layeredPane, "Menu");
				/*
				 * } else { JOptionPane.showMessageDialog(null,
				 * "No se ha podido iniciar sesión. Usuario o contraseña incorrectos");
				 * txtUsuario.setText(""); pswContrasena.setText(""); } sentencia.close(); }
				 * catch (SQLException ex) { System.out.println("SQLException: " +
				 * ex.getMessage()); System.out.println("SQLState: " + ex.getSQLState());
				 * System.out.println("VendorError: " + ex.getErrorCode()); }
				 */
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

		JPanel panelRegistro = new JPanel();
		layeredPane.add(panelRegistro, "Registro");
		panelRegistro.setLayout(null);

		JLabel lblTituloCrear = new JLabel("Crear Usuario");
		lblTituloCrear.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCrear.setFont(new Font("Book Antiqua", Font.BOLD, 21));
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

		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(412, 187, 175, 20);
		panelRegistro.add(txtDni);

		pswCrearContrasena = new JPasswordField();
		pswCrearContrasena.setBounds(412, 218, 175, 20);
		panelRegistro.add(pswCrearContrasena);

		JLabel lblNombre = new JLabel("Nombre:\r\n");
		lblNombre.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		lblNombre.setBounds(274, 125, 148, 20);
		panelRegistro.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		lblApellidos.setBounds(274, 156, 148, 20);
		panelRegistro.add(lblApellidos);

		JLabel lblUsuarioRegistro = new JLabel("Nombre de usuario:");
		lblUsuarioRegistro.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		lblUsuarioRegistro.setBounds(274, 187, 148, 20);
		panelRegistro.add(lblUsuarioRegistro);

		JLabel lblContrasenaRegistro = new JLabel("Contraseña:");
		lblContrasenaRegistro.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		lblContrasenaRegistro.setBounds(274, 218, 148, 20);
		panelRegistro.add(lblContrasenaRegistro);

		JLabel lblNacimiento = new JLabel("Fecha de nacimiento:");
		lblNacimiento.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		lblNacimiento.setBounds(274, 250, 148, 20);
		panelRegistro.add(lblNacimiento);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBackground(Color.LIGHT_GRAY);
		btnCrearUsuario.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * String DNI = txtDni.getText().toUpperCase(); if (1 == 1) { if
				 * (txtNombre.getText().length() > 0 && txtNombre.getText().length() < 13) { if
				 * (txtApellidos.getText().length() > 0 && txtApellidos.getText().length() < 30)
				 * { if (pswCrearContrasena.getPassword().length > 7 &&
				 * pswCrearContrasena.getPassword().length < 16) {
				 * 
				 * // Código para añadir datos de los clientes a la base de datos String url =
				 * "jdbc:mysql://localhost:33060/reto3grupo6_m"; try { Connection conexion =
				 * DriverManager.getConnection(url, "mañana", "elorrieta");
				 * 
				 * String sql =
				 * "INSERT INTO cliente (dni, contraseña, nombre, apellidos, sexo) VALUES (?, ?, ?, ?, ?)"
				 * ; PreparedStatement preparedStatement = conexion.prepareStatement(sql);
				 * 
				 * preparedStatement.setString(1, txtDni.getText());
				 * preparedStatement.setString(2, new String(pswCrearContrasena.getPassword()));
				 * preparedStatement.setString(3, txtNombre.getText());
				 * preparedStatement.setString(4, txtApellidos.getText());
				 * 
				 * preparedStatement.executeUpdate(); preparedStatement.close();
				 * 
				 * conexion.close(); } catch (SQLException ex) {
				 * System.out.println("SQLException: " + ex.getMessage());
				 * System.out.println("SQLState: " + ex.getSQLState());
				 * System.out.println("VendorError: " + ex.getErrorCode()); }
				 * JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente");
				 * txtNombre.setText(""); txtApellidos.setText(""); txtDni.setText("");
				 * pswCrearContrasena.setText(""); metodos.cambiarDePanel(layeredPane, "Login");
				 * 
				 * } else { JOptionPane.showMessageDialog(null,
				 * "La Contraseña debe tener entre 8 y 16 caracteres"); } } else {
				 * JOptionPane.showMessageDialog(null,
				 * "Los apellidos no deben superar los 30 caracteres y el campo no debe estar vacío"
				 * ); } } else { JOptionPane.showMessageDialog(null,
				 * "El nombre no debe superar los 13 caracteres y el campo no debe estar vacío"
				 * ); } } else { JOptionPane.showMessageDialog(null, "DNI Incorrecto"); }
				 */
			}
		});
		btnCrearUsuario.setBounds(287, 296, 122, 23);
		panelRegistro.add(btnCrearUsuario);

		JButton btnVolverLogin = new JButton("Volver");
		btnVolverLogin.setBackground(Color.LIGHT_GRAY);
		btnVolverLogin.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		btnVolverLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombre.setText("");
				txtApellidos.setText("");
				txtDni.setText("");
				pswCrearContrasena.setText("");
				metodos.cambiarDePanel(layeredPane, "Login");
			}
		});
		btnVolverLogin.setBounds(452, 296, 122, 23);
		panelRegistro.add(btnVolverLogin);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(412, 248, 175, 20);
		panelRegistro.add(dateChooser);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "2024-04-01";
		String maxString = "2024-04-25";
		try {
			dateChooser.setMaxSelectableDate(dateFormat.parse(maxString));
			dateChooser.setMinSelectableDate(dateFormat.parse(dateString));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JPanel panelMenu = new JPanel();
		layeredPane.add(panelMenu, "Menu");
		panelMenu.setLayout(null);
	}
}