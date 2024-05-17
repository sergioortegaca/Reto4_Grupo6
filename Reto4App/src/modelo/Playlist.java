package modelo;

import java.sql.Date;

public class Playlist extends Usuario {

	protected int IDList;
	protected String Titulo;
	protected Date FechaCreacion;

	public int getIDList() {
		return IDList;
	}

	public void setIDList(int iDList) {
		IDList = iDList;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

}
