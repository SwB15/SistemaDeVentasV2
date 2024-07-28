
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosCuentas {
    int idcuentas;
    String cuentas;
    String codigo;
    String descripcion;
    
    public DatosCuentas(){
        
    }

    public DatosCuentas(int idcuentas, String cuentas, String codigo, String descripcion) {
        this.idcuentas = idcuentas;
        this.cuentas = cuentas;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getIdcuentas() {
        return idcuentas;
    }

    public void setIdcuentas(int idcuentas) {
        this.idcuentas = idcuentas;
    }

    public String getCuentas() {
        return cuentas;
    }

    public void setCuentas(String cuentas) {
        this.cuentas = cuentas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String atxtDescripcion) {
        this.descripcion = atxtDescripcion;
    }
}
