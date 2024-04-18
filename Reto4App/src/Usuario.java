public abstract class Usuario {
	protected String clienteID;
	protected String nombre;
	protected String apellido;
	protected String usuario;
	protected String contraseña;
	protected String fechaNacimiento;
	protected String fechaRegistro;

	protected enum tipoUsuario {
		Free, Premium
	}
}