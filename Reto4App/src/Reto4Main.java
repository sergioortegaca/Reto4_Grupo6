import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class Reto4Main extends JFrame {
	private JTextField txtUsuario;
	private JPasswordField pswContrasena;
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

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "LayeredPane");
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel panelBienvenida = new JPanel();
		layeredPane.add(panelBienvenida, "Bienvenida");
		panelBienvenida.addMouseListener((MouseListener) new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				metodos.moverseA(layeredPane, "Login");
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
		layeredPane.add(panelLogin, "Login");
		panelLogin.setLayout(null);

		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioDeSesion.setBounds(0, 49, 874, 33);
		panelLogin.add(lblInicioDeSesion);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(152, 109, 176, 14);
		panelLogin.add(lblUsuario);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(128, 164, 176, 14);
		panelLogin.add(lblContrasena);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(230, 107, 148, 20);
		panelLogin.add(txtUsuario);
		txtUsuario.setColumns(10);

		pswContrasena = new JPasswordField();
		pswContrasena.setBounds(230, 162, 148, 20);
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
				metodos.moverseA(layeredPane, "Menu");
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
		btnEntrar.setBounds(288, 220, 142, 23);
		panelLogin.add(btnEntrar);

		JButton btnCrear = new JButton("Crear nuevo usuario\r\n");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.moverseA(layeredPane, "Registro");
			}
		});
		btnCrear.setBounds(86, 220, 159, 23);
		panelLogin.add(btnCrear);

		JPanel panelRegistro = new JPanel();
		layeredPane.add(panelRegistro, "Registro");
		panelRegistro.setLayout(null);

		JPanel panelMenu = new JPanel();
		layeredPane.add(panelMenu, "Menu");
		panelMenu.setLayout(null);
	}
}