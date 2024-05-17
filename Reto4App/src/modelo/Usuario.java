package modelo;

public abstract class Usuario extends Audio {
	protected int clienteID;
	protected String nombre;
	protected String apellido;
	protected String usuario;
	protected String contrasena;
	protected String fechaNacimiento;
	protected String fechaRegistro;

	protected enum tipoUsuario {
		Free, Premium
	}

	public int getPlaylistFavorita() {
		return playlistFavorita;
	}

	public void setPlaylistFavorita(int playlistFavorita) {
		this.playlistFavorita = playlistFavorita;
	}

	protected int playlistFavorita;

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}