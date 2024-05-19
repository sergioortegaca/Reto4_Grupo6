package pruebas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;
import vistaControlador.Metodos;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JUnitMetodos {
	public Metodos metodos = new Metodos();
	private JLayeredPane layeredPane;
	private CardLayout cardLayout;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panelPerfil;

	@Before
	public void setUp() {
		layeredPane = new JLayeredPane();
		cardLayout = new CardLayout();
		layeredPane.setLayout(cardLayout);

		panel1 = new JPanel();
		panel1.setName("panel1");
		layeredPane.add(panel1, "panel1");

		panel2 = new JPanel();
		panel2.setName("panel2");
		layeredPane.add(panel2, "panel2");

		panelPerfil = new JPanel();
		panelPerfil.setName("perfil");
		layeredPane.add(panelPerfil, "panel2");
	}

	@Test
	public void testCambiarDePanel() {

		assertEquals(panel1, layeredPane.getComponent(0));

		metodos.cambiarDePanel(layeredPane, "panel2");

		assertEquals(panel2, layeredPane.getComponent(1));
	}

	@Test
	public void testBotonAtras() {
		metodos.botonAtras(layeredPane, "panel1", panel2);

		Component[] components = panel2.getComponents();
		JButton btnAtras = null;
		for (Component comp : components) {
			if (comp instanceof JButton && ((JButton) comp).getText().equals("Atrás")) {
				btnAtras = (JButton) comp;
			}
		}

		metodos.cambiarDePanel(layeredPane, "panel1");
		for (Component comp : layeredPane.getComponents()) {
			if (comp.isVisible()) {
				assertEquals("panel1", comp.getName());
			}
		}

		btnAtras.doClick();

		for (Component comp : layeredPane.getComponents()) {
			if (comp.isVisible()) {
				assertEquals("panel1", comp.getName());
			}
		}
	}

	@Test
	public void testBotonPerfil() {
		String userName = "User1";
		metodos.botonPerfil(layeredPane, panel1, userName, "perfil");

		Component[] components = panel1.getComponents();
		JButton btnPerfil = null;
		for (Component comp : components) {
			if (comp instanceof JButton && ((JButton) comp).getText().equals(userName)) {
				btnPerfil = (JButton) comp;
			}
		}
		assertNotNull("Button with user's name should be added to the panel", btnPerfil);

		CardLayout cardLayout = (CardLayout) layeredPane.getLayout();
		cardLayout.show(layeredPane, "panel1");

		assertEquals(panel1, layeredPane.getComponent(0));
		btnPerfil.doClick();
		assertEquals(panelPerfil, layeredPane.getComponent(2));
	}

	@Test
	public void testBienvenidaMenu() {
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);

		String mensajeBienvenida = metodos.bienvenidaMenu();

		if (hour < 6 || hour >= 21) {
			assertEquals("¡Buenas noches!", mensajeBienvenida);
		} else if (hour >= 6 && hour < 12) {
			assertEquals("¡Buenos días!", mensajeBienvenida);
		} else if (hour >= 12 && hour < 21) {
			assertEquals("¡Buenas tardes!", mensajeBienvenida);
		}
	}

	@Test
	public void testCrearLabel() {
		JPanel panel = new JPanel();

		String texto = "Ejemplo";
		int x = 10;
		int y = 20;
		int anchura = 100;
		int altura = 30;
		new Metodos().crearLabel(texto, x, y, anchura, altura, panel);

		Component[] components = panel.getComponents();
		JLabel label = (JLabel) components[0];

		assertEquals(texto, label.getText());
		assertEquals(x, label.getBounds().x);
		assertEquals(y, label.getBounds().y);
		assertEquals(anchura, label.getBounds().width);
		assertEquals(altura, label.getBounds().height);
	}
}