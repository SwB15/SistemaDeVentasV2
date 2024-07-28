
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosCompras {
    int idcompras;
    String boletanumero;
    String fecha;
    String hora;
    int preciototal;
    int fk_proveedores;

    public DatosCompras(int idcompras, String boletanumero, String fecha, String hora, int preciototal, int fk_proveedores) {
        this.idcompras = idcompras;
        this.boletanumero = boletanumero;
        this.fecha = fecha;
        this.hora = hora;
        this.preciototal = preciototal;
        this.fk_proveedores = fk_proveedores;
    }

    public DatosCompras() { 
        
    }

    public int getIdcompras() {
        return idcompras;
    }

    public void setIdcompras(int idcompras) {
        this.idcompras = idcompras;
    }

    public String getBoletanumero() {
        return boletanumero;
    }

    public void setBoletanumero(String boletanumero) {
        this.boletanumero = boletanumero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(int preciototal) {
        this.preciototal = preciototal;
    }

    public int getFk_proveedores() {
        return fk_proveedores;
    }

    public void setFk_proveedores(int fk_proveedores) {
        this.fk_proveedores = fk_proveedores;
    }
    
    
}
