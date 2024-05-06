package Objetos;

public class Cancion extends Multimedia {

	protected String Nombre;
	protected String ArtistasInvitados;

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}

	public String getArtistasInvitados() {
		return ArtistasInvitados;
	}

	public void setArtistasInvitados(String artistasInvitados) {
		ArtistasInvitados = artistasInvitados;
	}

}