package pruebas;

import static org.junit.Assert.*;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.junit.Test;
import vistaControlador.*;
import modelo.*;

public class JUnitMain {

	Reto4Main reto4Main = new Reto4Main();
	Metodos metodos = new Metodos();

	@Test
	public void testConstructor() {
		Reto4Main frame = new Reto4Main();

		assertNotNull(frame);

		assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());

		assertEquals(900, frame.getWidth());
		assertEquals(500, frame.getHeight());

		JPanel contentPane = (JPanel) frame.getContentPane();
		assertNotNull(contentPane);

		Component[] components = contentPane.getComponents();
		assertTrue(components.length > 0);
		assertTrue(components[0] instanceof JLayeredPane);
	}

	@Test
	public void testCrearPanelLogin() {
		reto4Main.crearPanelLogin();
	}

	@Test
	public void testCrearPanelRegistro() {
		reto4Main.crearPanelRegistro();
	}

	@Test
	public void testCrearPanelPerfil() {
		UsuarioFree Usuario = new UsuarioFree();
		reto4Main.crearPanelPerfil(Usuario);
	}

	@Test
	public void testCrearPanelMenu() {
		reto4Main.crearPanelMenu();
	}

	@Test
	public void testCrearPanelDescubrir1() {
		String swVentanaMusico = "swVentanaMusico";
		reto4Main.crearPanelDescubrir(swVentanaMusico);
	}

	@Test
	public void testCrearPanelDescubrir2() {
		String swVentanaPodcaster = "swVentanaPodcaster";
		reto4Main.crearPanelDescubrir(swVentanaPodcaster);
	}

	@Test
	public void testCrearPanelMenuAdmin() {
		reto4Main.crearPanelMenuAdmin();
	}

	@Test
	public void testCrearPanelMenuGestionar1() {
		String swMusica = "swMusica";
		reto4Main.crearPanelMenuGestionar(swMusica);
	}

	@Test
	public void testCrearPanelMenuGestionar2() {
		String swPodcasts1 = "swPodcasts1";
		reto4Main.crearPanelMenuGestionar(swPodcasts1);
	}

	@Test
	public void testCrearPanelMenuGestionar() {
		String swEstadisticas = "swEstadisticas";
		reto4Main.crearPanelMenuGestionar(swEstadisticas);
	}

	@Test
	public void testCrearPanelMenuEditar() {
		String opcionGestionar = "";
		reto4Main.crearPanelMenuEditar(opcionGestionar);
	}

	@Test
	public void testCrearPanelFormularioAdmin1() {
		String swArtistas = "swArtistas";
		reto4Main.crearPanelFormularioAdmin(swArtistas);
	}

	@Test
	public void testCrearPanelFormularioAdmin2() {
		String swAlbumes = "swAlbumes";
		reto4Main.crearPanelFormularioAdmin(swAlbumes);
	}

	@Test
	public void testCrearPanelFormularioAdmin3() {
		String swCanciones = "swCanciones";
		reto4Main.crearPanelFormularioAdmin(swCanciones);
	}

	@Test
	public void testCrearPanelFormularioAdmin4() {
		String swPodcasters = "swPodcasters";
		reto4Main.crearPanelFormularioAdmin(swPodcasters);
	}

	@Test
	public void testCrearPanelFormularioAdmin5() {
		String swPodcasts2 = "swPodcasts2";
		reto4Main.crearPanelFormularioAdmin(swPodcasts2);
	}

	@Test
	public void testCrearPanelJlistAdmin1() {
		String swArtistas = "swArtistas";
		reto4Main.crearPanelJlistAdmin(swArtistas);
	}

	@Test
	public void testCrearPanelJlistAdmin2() {
		String swAlbumes = "swAlbumes";
		reto4Main.crearPanelJlistAdmin(swAlbumes);
	}

	@Test
	public void testCrearPanelJlistAdmin3() {
		String swCanciones = "swCanciones";
		reto4Main.crearPanelJlistAdmin(swCanciones);
	}

	@Test
	public void testCrearPanelJlistAdmin4() {
		String swPodcasters = "swPodcasters";
		reto4Main.crearPanelJlistAdmin(swPodcasters);
	}

	@Test
	public void testCrearPanelJlistAdmin5() {
		String swPodcasts2 = "swPodcasts2";
		reto4Main.crearPanelJlistAdmin(swPodcasts2);
	}

	@Test
	public void testCrearPanelEstadisticas1() {
		String swCancionesMasGustadas = "swCancionesMasGustadas";
		reto4Main.crearPanelEstadisticas(swCancionesMasGustadas);
	}

	@Test
	public void testCrearPanelEstadisticas2() {
		String swPodcastMasGustados = "swPodcastMasGustados";
		reto4Main.crearPanelEstadisticas(swPodcastMasGustados);
	}

	@Test
	public void testCrearPanelEstadisticas3() {
		String swMasEscuchados = "swMasEscuchados";
		reto4Main.crearPanelEstadisticas(swMasEscuchados);
	}
}
