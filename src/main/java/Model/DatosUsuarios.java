package Model;

/**
 *
 * @author SwichBlade15
 */

public class DatosUsuarios {
    private int idusuario;
    private String usuarios;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String telefono;
    private String email;
    private String permisos;
    private int fk_ciudades;

    public DatosUsuarios(int idusuario, String usuarios, String contrasena, String nombre, String apellido, String cedula, String direccion, String telefono, String email, String permisos, int fk_ciudades) {
        this.idusuario = idusuario;
        this.usuarios = usuarios;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.permisos = permisos;
        this.fk_ciudades = fk_ciudades;
    }

    public int getFk_ciudades() {
        return fk_ciudades;
    }

    public void setFk_ciudades(int fk_ciudades) {
        this.fk_ciudades = fk_ciudades;
    }

    public DatosUsuarios() {
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuario) {
        this.usuarios = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
}