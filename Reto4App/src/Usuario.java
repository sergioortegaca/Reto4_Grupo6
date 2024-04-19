public abstract class Usuario {
	protected String clienteID;
	protected String nombre;
	protected String apellido;
	protected String usuario;
	protected String contrasena;
	protected String fechaNacimiento;
	protected String fechaRegistro;

	protected enum tipoUsuario {
		Free, Premium
	}
}