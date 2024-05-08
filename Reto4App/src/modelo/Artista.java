package modelo;

public abstract class Artista {
	protected int artistaID;
	protected String nombreArtistico;
	protected String imagenArtista;

	public int getArtistaID() {
		return artistaID;
	}

	public void setArtistaID(int artistaID) {
		this.artistaID = artistaID;
	}

	public String getNombreArtistico() {
		return nombreArtistico;
	}

	public void setNombreArtistico(String nombreArtistico) {
		this.nombreArtistico = nombreArtistico;
	}

	public String getImagenArtista() {
		return imagenArtista;
	}

	public void setImagenArtista(String imagenArtista) {
		this.imagenArtista = imagenArtista;
	}

}