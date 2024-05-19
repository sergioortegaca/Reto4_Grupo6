package pruebas;

import org.junit.Test;
import modelo.*;

public class JUnitObjetos {

	Cancion cancion = new Cancion();
	String filePath = "/audio/10.wav";

	@Test
	public void testLoadClip() {
		cancion.loadClip(filePath);
	}

	@Test
	public void testPlay() {
		cancion.loadClip(filePath);

		cancion.play();
	}

	@Test
	public void testPause() {
		cancion.loadClip(filePath);

		cancion.play();

		cancion.pause();
	}

	@Test
	public void testResume() {
		cancion.loadClip(filePath);

		cancion.play();

		cancion.pause();

		cancion.resume();
	}

	@Test
	public void testSetReproducciones() {
		Musico musico = new Musico();
		musico.setReproducciones(2);
	}

	@Test
	public void testPodcast() {
		Podcast podcast = new Podcast();
		podcast.setArtistasInvitados("a");
		podcast.getArtistasInvitados();
	}

	@Test
	public void testCancion() {
		Cancion cancion = new Cancion();
		cancion.setArtistasInvitados("a");
		cancion.getArtistasInvitados();
	}

	@Test
	public void testPlaylist() {
		Playlist playlist = new Playlist();
		playlist.setIDList(1);
		playlist.getIDList();
		playlist.setTitulo("a");
		playlist.getTitulo();
		playlist.setFechaCreacion(null);
		playlist.getFechaCreacion();
	}

	@Test
	public void testUsuarioPremium() {
		UsuarioPremium usuarioPremium = new UsuarioPremium();
		usuarioPremium.setPlaylistFavorita(1);
		usuarioPremium.getPlaylistFavorita();
		usuarioPremium.setClienteID(1);
		usuarioPremium.getClienteID();
		usuarioPremium.setNombre("a");
		usuarioPremium.setApellido("a");
		usuarioPremium.setUsuario("a");
		usuarioPremium.setContrasena("a");
		usuarioPremium.getContrasena();
		usuarioPremium.setFechaNacimiento("a");
		usuarioPremium.setFechaRegistro("a");
	}

}
