package modelo;

public class Album extends Musico {

	protected int albumID;
	protected String tituloAlbum;
	protected String anoAlbum;
	protected String generoAlbum;
	protected String imagenAlbum;

	public int getAlbumID() {
		return albumID;
	}

	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

	public String getTituloAlbum() {
		return tituloAlbum;
	}

	public void setTituloAlbum(String tituloAlbum) {
		this.tituloAlbum = tituloAlbum;
	}

	public String getAnoAlbum() {
		return anoAlbum;
	}

	public void setAnoAlbum(String string) {
		this.anoAlbum = string;
	}

	public String getGeneroAlbum() {
		return generoAlbum;
	}

	public void setGeneroAlbum(String generoAlbum) {
		this.generoAlbum = generoAlbum;
	}

	public String getImagenAlbum() {
		return imagenAlbum;
	}

	public void setImagenAlbum(String imagenAlbum) {
		this.imagenAlbum = imagenAlbum;
	}

}