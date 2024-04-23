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

public class Reto4Main extends JFrame {
	private JTextField txtUsuario;
	private JPasswordField pswContrasena;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtUsuarioRegistro;
	private JPasswordField pswCrearContrasena;
	JDateChooser fechaNacimiento;
	SimpleDateFormat dateFormat;
	String timeStamp;
	Metodos metodos = new Metodos();
	String linkBD = "jdbc:mysql://localhost:33060/reto4_grupo6", userBD = "mañana", passBD = "elorrieta";

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

		// COGER LA HORA ACTUAL PARA EL REGISTRO:
		timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "LayeredPane");
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel panelBienvenida = new JPanel();
		panelBienvenida.setBackground(new Color(255, 255, 255));
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

		JLabel lblNewLabel = new JLabel("Bienvenido");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblNewLabel.setBounds(287, 155, 299, 141);
		panelBienvenida.add(lblNewLabel);

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

				String user = txtUsuario.getText();
				String pass = new String(pswContrasena.getPassword());

				try {
					Connection connection = (Connection) DriverManager.getConnection(linkBD, userBD, passBD);

					PreparedStatement st = (PreparedStatement) connection.prepareStatement(sentencia);

					st.setString(1, user);
					st.setString(2, pass);

					ResultSet rs = st.executeQuery();

					if (rs.next()) {
						JOptionPane.showMessageDialog(null, textOk);
						metodos.cambiarDePanel(layeredPane, "Menu");
					} else {
						JOptionPane.showMessageDialog(null, textNot);
						txtUsuario.setText("");
						pswContrasena.setText("");
					}
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

		JPanel panelRegistro = new JPanel();
		panelRegistro.setBackground(new Color(255, 255, 255));
		layeredPane.add(panelRegistro, "Registro");
		panelRegistro.setLayout(null);

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

		JLabel lblNombre = new JLabel("Nombre:\r\n");
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
		btnCrearUsuario.setBackground(Color.LIGHT_GRAY);
		btnCrearUsuario.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// if (txtNombre.getText().length() > 0 && txtNombre.getText().length() < 13) {
				// if (txtApellidos.getText().length() > 0 && txtApellidos.getText().length() <
				// 30) {
				// if (pswCrearContrasena.getPassword().length > 7
				// && pswCrearContrasena.getPassword().length < 16) {

				// Código para añadir datos de los clientes a la base de datos

				try {
					Connection conexion = DriverManager.getConnection(linkBD, userBD, passBD);

					String sql = "INSERT INTO cliente (Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo ) VALUES (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStatement = conexion.prepareStatement(sql);

					preparedStatement.setString(2, txtNombre.getText());
					preparedStatement.setString(3, txtApellidos.getText());
					preparedStatement.setString(4, txtUsuarioRegistro.getText());
					preparedStatement.setString(5, new String(pswCrearContrasena.getPassword()));
					preparedStatement.setString(6, dateFormat.format(fechaNacimiento.getDate()));
					preparedStatement.setString(7, timeStamp);

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
		btnCrearUsuario.setBounds(287, 296, 122, 23);
		panelRegistro.add(btnCrearUsuario);

		JButton btnVolverLogin = new JButton("Volver");
		btnVolverLogin.setBackground(Color.LIGHT_GRAY);
		btnVolverLogin.setFont(new Font("Book Antiqua", Font.BOLD, 13));
		btnVolverLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombre.setText("");
				txtApellidos.setText("");
				txtUsuarioRegistro.setText("");
				pswCrearContrasena.setText("");
				metodos.cambiarDePanel(layeredPane, "Login");
			}
		});
		btnVolverLogin.setBounds(452, 296, 122, 23);
		panelRegistro.add(btnVolverLogin);

		fechaNacimiento = new JDateChooser();
		fechaNacimiento.setBounds(412, 248, 175, 20);
		panelRegistro.add(fechaNacimiento);

		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "2024-04-01";
		String maxString = "2024-04-25";
		try {
			fechaNacimiento.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimiento.setMinSelectableDate(dateFormat.parse(dateString));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JPanel panelMenu = new JPanel();
		layeredPane.add(panelMenu, "Menu");
		panelMenu.setLayout(null);
	}
}