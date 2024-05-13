package modelo;

public abstract class Artista {
	protected int artistaID;
	protected String nombreArtistico;
	protected String imagenArtista;
	protected String descripcionArtista;
	protected String caracteristica;
	protected int anoActivo;
	protected int reproducciones;

	public int getReproducciones() {
		return reproducciones;
	}

	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}

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

	public String getDescripcionArtista() {
		return descripcionArtista;
	}

	public void setDescripcionArtista(String descripcionArtista) {
		this.descripcionArtista = descripcionArtista;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public int getAnoActivo() {
		return anoActivo;
	}

	public void setAnoActivo(int anoActivo) {
		this.anoActivo = anoActivo;
	}
}