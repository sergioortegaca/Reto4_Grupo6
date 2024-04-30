package Main;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;
import java.awt.CardLayout;
import static org.junit.Assert.assertEquals;

public class JUnit {
	private JLayeredPane layeredPane;
	private CardLayout cardLayout;
	private JPanel panel1;
	private JPanel panel2;

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
	}

	@Test
	public void testCambiarDePanel() {
		// Verificar que el panel visible inicialmente es el panel1
		assertEquals(panel1, layeredPane.getComponent(0));
		// Cambiar de panel
		cambiarDePanel(layeredPane, "panel2");
		// Verificar que el panel visible ahora es el panel2
		assertEquals(panel2, layeredPane.getComponent(1));
	}

	public void cambiarDePanel(JLayeredPane layeredPane, String nombrePanel) {
		CardLayout cardLayout = (CardLayout) layeredPane.getLayout();
		cardLayout.show(layeredPane, nombrePanel);
	}
}
