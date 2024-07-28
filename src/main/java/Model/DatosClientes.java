package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosClientes {
    private int idclientes;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String telefono;
    private String email;
    private int fk_ciudades;

    public DatosClientes(int fk_ciudades) {
        this.fk_ciudades = fk_ciudades;
    }

    public DatosClientes(int idclientes, String nombre, String apellido, String cedula, String direccion, String telefono, String email, String fk_ofertas) {
        this.idclientes = idclientes;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public DatosClientes() {
        
    }

    public int getIdclientes() {
        return idclientes;
    }

    public void setIdclientes(int idclientes) {
        this.idclientes = idclientes;
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

    public int getFk_ciudades() {
        return fk_ciudades;
    }

    public void setFk_ciudades(int fk_ciudades) {
        this.fk_ciudades = fk_ciudades;
    }
}
