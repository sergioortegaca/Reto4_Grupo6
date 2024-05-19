package pruebas;

import static org.junit.Assert.*;
import org.junit.Test;

import modelo.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class JUnitBDConexiones {
	BDConexiones conexionesBD = new BDConexiones();

	@Test
	public void testVariablesConexion() {
		assertEquals("jdbc:mysql", conexionesBD.driverBBDD);
		assertEquals("localhost", conexionesBD.servidorBBDD);
		assertEquals("3306", conexionesBD.puertoBBDD);
		assertEquals("reto4_grupo6", conexionesBD.nombreBBDD);
		assertEquals("root", conexionesBD.usuarioBBDD);
		assertEquals("julioespiaeritreo", conexionesBD.contrasenaBBDD);
		assertEquals("com.mysql.cj.jdbc.Driver", conexionesBD.DRIVER);
		assertEquals("jdbc:mysql://localhost:3306/reto4_grupo6", conexionesBD.LinkBD);
	}

	@Test
	public void testConexionBD() throws SQLException {
		Connection conexion = conexionesBD.conexionBD();
		assertNotNull("La conexión no debería ser nula", conexion);
		assertTrue("La conexión debería estar activa", conexion.isValid(5));
		try {
			conexion.close();
		} catch (Exception e) {
			fail("Error al cerrar la conexión: " + e.getMessage());
		}
	}

	@Test
	public void testConexionMusico() {
		Connection conexion = conexionesBD.conexionBD();
		assertNotNull("No se pudo obtener la conexión en memoria", conexion);

		try {
			ArrayList<Musico> musicoArrayList = conexionesBD.conexionMusico();

			assertNotNull("La lista de músicos no debería ser nula", musicoArrayList);
			assertFalse("La lista de músicos debería contener al menos un elemento", musicoArrayList.isEmpty());

			for (Musico musico : musicoArrayList) {
				assertNotNull("IDMusico no debería ser nulo", musico.getArtistaID());
				assertNotNull("NombreArtista no debería ser nulo", musico.getNombreArtistico());
				assertNotNull("ImagenArtista no debería ser nulo", musico.getImagenArtista());
				assertNotNull("DescripcionArtista no debería ser nulo", musico.getDescripcionArtista());
				assertNotNull("Caracteristica no debería ser nulo", musico.getCaracteristica());
				assertNotNull("AnoActivo no debería ser nulo", musico.getAnoActivo());
			}
		} finally {
			try {
				if (conexion != null && !conexion.isClosed()) {
					conexion.close();
				}
			} catch (SQLException e) {
				fail("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	@Test
	public void testConexionAlbum() {
		String artistaSeleccionado = "Cruz Cafuné";

		Connection conexion = conexionesBD.conexionBD();
		assertNotNull("No se pudo obtener la conexión en memoria", conexion);

		try {
			ArrayList<Album> albumesArrayList = conexionesBD.conexionAlbum(artistaSeleccionado);

			assertNotNull("La lista de álbumes no debería ser nula", albumesArrayList);
			assertFalse("La lista de álbumes debería contener al menos un elemento", albumesArrayList.isEmpty());

			for (Album album : albumesArrayList) {
				assertNotNull("IDAlbum no debería ser nulo", album.getAlbumID());
				assertNotNull("TituloAlbum no debería ser nulo", album.getTituloAlbum());
				assertNotNull("AnoAlbum no debería ser nulo", album.getAnoAlbum());
				assertNotNull("GeneroAlbum no debería ser nulo", album.getGeneroAlbum());
				assertNotNull("ImagenAlbum no debería ser nulo", album.getImagenAlbum());
			}

		} finally {
			try {
				if (conexion != null && !conexion.isClosed()) {
					conexion.close();
				}
			} catch (SQLException e) {
				fail("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	@Test
	public void testConexionCancion() {
		String albumSeleccionado = "Maracucho Bueno Muere Chiquito";

		Connection conexion = conexionesBD.conexionBD();
		assertNotNull("No se pudo obtener la conexión en memoria", conexion);

		try {
			ArrayList<Cancion> cancionesArrayList = conexionesBD.conexionCancion(albumSeleccionado);

			assertNotNull("La lista de canciones no debería ser nula", cancionesArrayList);
			assertFalse("La lista de canciones debería contener al menos un elemento", cancionesArrayList.isEmpty());

			for (Cancion cancion : cancionesArrayList) {
				assertNotNull("NombreMultimedia no debería ser nulo", cancion.getNombreMultimedia());
				assertNotNull("AudioID no debería ser nulo", cancion.getAudioID());
				assertNotNull("Duracion no debería ser nulo", cancion.getDuracion());
				assertNotNull("ImagenMultimedia no debería ser nulo", cancion.getImagenMultimedia());
			}

		} finally {
			try {
				if (conexion != null && !conexion.isClosed()) {
					conexion.close();
				}
			} catch (SQLException e) {
				fail("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	@Test
	public void testConexionPodcaster() {
		Connection conexion = conexionesBD.conexionBD();
		assertNotNull("No se pudo obtener la conexión en memoria", conexion);

		try {
			ArrayList<Podcaster> podcasterArrayList = conexionesBD.conexionPodcaster();

			assertNotNull("La lista de podcaster no debería ser nula", podcasterArrayList);
			assertFalse("La lista de podcaster debería contener al menos un elemento", podcasterArrayList.isEmpty());

			for (Podcaster podcaster : podcasterArrayList) {
				assertNotNull("IDPodcaster no debería ser nulo", podcaster.getArtistaID());
				assertNotNull("NombreArtistico no debería ser nulo", podcaster.getNombreArtistico());
				assertNotNull("ImagenArtista no debería ser nulo", podcaster.getImagenArtista());
				assertNotNull("DescripcionArtista no debería ser nulo", podcaster.getDescripcionArtista());
				assertNotNull("AnoActivo no debería ser nulo", podcaster.getAnoActivo());
			}

		} finally {
			try {
				if (conexion != null && !conexion.isClosed()) {
					conexion.close();
				}
			} catch (SQLException e) {
				fail("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	@Test
	public void testConexionPodcast() {
		int idPodcaster = 1;

		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull("No se pudo obtener la conexión", conexion);

			ArrayList<Podcast> podcastsArrayList = conexionesBD.conexionPodcast(idPodcaster);

			assertNotNull("La lista de podcasts no debería ser nula", podcastsArrayList);
			assertFalse("La lista de podcasts debería contener al menos un elemento", podcastsArrayList.isEmpty());

			for (Podcast podcast : podcastsArrayList) {
				assertNotNull("NombreMultimedia no debería ser nulo", podcast.getNombreMultimedia());
				assertNotNull("AudioID no debería ser nulo", podcast.getAudioID());
				assertNotNull("Duracion no debería ser nulo", podcast.getDuracion());
				assertNotNull("TipoMultimedia no debería ser nulo", podcast.getTipoMultimedia());
				assertNotNull("ImagenMultimedia no debería ser nulo", podcast.getImagenMultimedia());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionAlbumAdmin() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull("No se pudo obtener la conexión", conexion);

			ArrayList<Album> albumesArrayList = conexionesBD.conexionAlbumAdmin();

			assertNotNull("La lista de álbumes no debería ser nula", albumesArrayList);
			assertFalse("La lista de álbumes debería contener al menos un elemento", albumesArrayList.isEmpty());

			for (Album album : albumesArrayList) {
				assertNotNull("IDAlbum no debería ser nulo", album.getAlbumID());
				assertNotNull("TituloAlbum no debería ser nulo", album.getTituloAlbum());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionCancionAdmin() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull("No se pudo obtener la conexión", conexion);

			ArrayList<Cancion> cancionesArrayList = conexionesBD.conexionCancionAdmin();

			assertNotNull("La lista de canciones no debería ser nula", cancionesArrayList);
			assertFalse("La lista de canciones debería contener al menos un elemento", cancionesArrayList.isEmpty());

			for (Cancion cancion : cancionesArrayList) {
				assertNotNull("IDAudio no debería ser nulo", cancion.getAudioID());
				assertNotNull("NombreMultimedia no debería ser nulo", cancion.getNombreMultimedia());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionPodcastAdmin() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull("No se pudo obtener la conexión", conexion);

			ArrayList<Podcast> podcastArrayList = conexionesBD.conexionPodcastAdmin();

			assertNotNull("La lista de podcasts no debería ser nula", podcastArrayList);
			assertFalse("La lista de podcasts debería contener al menos un elemento", podcastArrayList.isEmpty());

			for (Podcast podcast : podcastArrayList) {
				assertNotNull("IDAudio no debería ser nulo", podcast.getAudioID());
				assertNotNull("NombreMultimedia no debería ser nulo", podcast.getNombreMultimedia());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionMasEscuchadas() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull("No se pudo obtener la conexión", conexion);

			ArrayList<Cancion> masEscuchadas = conexionesBD.conexionMasEscuchadas();

			assertNotNull("La lista de canciones más escuchadas no debería ser nula", masEscuchadas);
			assertFalse("La lista de canciones más escuchadas debería contener al menos un elemento",
					masEscuchadas.isEmpty());

			for (Cancion cancion : masEscuchadas) {
				assertNotNull("NombreMultimedia no debería ser nulo", cancion.getNombreMultimedia());
				assertNotNull("NombreArtistico no debería ser nulo", cancion.getNombreArtistico());
				assertNotNull("Reproducciones no debería ser nulo", cancion.getReproducciones());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionMasGustadas() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull(conexion);

			ArrayList<Cancion> masGustadas = conexionesBD.conexionMasGustadas();

			assertNotNull(masGustadas);
			assertFalse(masGustadas.isEmpty());

			for (Cancion cancion : masGustadas) {
				assertNotNull(cancion.getNombreMultimedia());
				assertNotNull(cancion.getNombreArtistico());
				assertNotNull(cancion.getNumeroMeGustas());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionMasGustados() {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull(conexion);

			ArrayList<Podcast> masGustados = conexionesBD.conexionMasGustados();

			assertNotNull(masGustados);
			assertFalse(masGustados.isEmpty());

			for (Podcast podcast : masGustados) {
				assertNotNull(podcast.getNombreMultimedia());
				assertNotNull(podcast.getNombreArtistico());
				assertNotNull(podcast.getNumeroMeGustas());
			}

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}

	@Test
	public void testConexionSelectIDAudio() throws ConexionFallidaException {
		try {
			Connection conexion = conexionesBD.conexionBD();
			assertNotNull(conexion);

			int idAudio = conexionesBD.conexionSelectIDAudio();

			assertTrue(idAudio > 0);

			conexion.close();

		} catch (SQLException e) {
			fail("Se produjo una excepción al probar la conexión con la BD: " + e.getMessage());
		}
	}
}