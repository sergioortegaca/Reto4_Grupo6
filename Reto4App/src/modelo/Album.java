package modelo;

import java.sql.Date;

public class Album extends Musico {

	protected int albumID;
	protected String tituloAlbum;
	protected Date anoAlbum;
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

	public Date getAnoAlbum() {
		return anoAlbum;
	}

	public void setAnoAlbum(Date anoAlbum) {
		this.anoAlbum = anoAlbum;
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