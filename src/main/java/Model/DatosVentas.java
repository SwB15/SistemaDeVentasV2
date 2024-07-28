
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosVentas {
    int idventas;
    String boleta;
    String fecha;
    String hora;
    int preciototal;
    int fk_clientes;

    public DatosVentas(int idventas, String boleta, String fecha, String hora, int preciototal, int fk_clientes) {
        this.idventas = idventas;
        this.boleta = boleta;
        this.fecha = fecha;
        this.hora = hora;
        this.preciototal = preciototal;
        this.fk_clientes = fk_clientes;
    }
    
    public DatosVentas(){
        
    }

    public int getIdventas() {
        return idventas;
    }

    public void setIdventas(int idventas) {
        this.idventas = idventas;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
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

    public int getFk_clientes() {
        return fk_clientes;
    }

    public void setFk_clientes(int fk_clientes) {
        this.fk_clientes = fk_clientes;
    }
}