
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosProveedores {
    
    int idproveedores;
    String proveedores;
    String ruc;
    String direccion;
    String telefono;
    String email;
    int fk_ciudades;

    public DatosProveedores(int idproveedores, String proveedores, String ruc, String direccion, String telefono, String email, int fk_ciudades) {
        this.idproveedores = idproveedores;
        this.proveedores = proveedores;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fk_ciudades = fk_ciudades;
    }

    public DatosProveedores() {
        
    }

    public int getIdproveedores() {
        return idproveedores;
    }

    public void setIdproveedores(int idproveedores) {
        this.idproveedores = idproveedores;
    }

    public String getProveedores() {
        return proveedores;
    }

    public void setProveedores(String proveedores) {
        this.proveedores = proveedores;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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
