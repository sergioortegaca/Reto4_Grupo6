import java.awt.CardLayout;
import javax.swing.JLayeredPane;

public class Metodos {

	/**
	 * Descripción: metodo para pasar de un panel a otro. Ocultará el actual y mostrará el que pasamos como parámetro.
	 * @param nombreLayeredPane		JLayeredPane	Es un panel padre dentro del que están otro paneles entre los que permitiré visualizar uno y otro.
	 * @param identificadorCapa		String			Es el panel que quiero mostrar.
	 * 
	 * No devuelve ningún valor
	 * @author in1dm3
	 */
	public void cambiarDePanel(JLayeredPane nombreLayeredPane, String identificadorCapa) {
		// CardLayout es un manejador de contenido
		CardLayout cardLayout = (CardLayout) nombreLayeredPane.getLayout();

		// Cambia la visibilidad de las capas en el JLayeredPane

		cardLayout.show(nombreLayeredPane, identificadorCapa);
	}
}