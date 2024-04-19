import java.awt.CardLayout;
import javax.swing.JLayeredPane;

public class Metodos {
	// metodo para pasar de un panel a otro
	public void cambiarDePanel(JLayeredPane nombreLayeredPane, String identificadorCapa) {
		// CardLayout es un manejador de contenido
		CardLayout cardLayout = (CardLayout) nombreLayeredPane.getLayout();

		// Cambia la visibilidad de las capas en el JLayeredPane

		cardLayout.show(nombreLayeredPane, identificadorCapa);
	}
}