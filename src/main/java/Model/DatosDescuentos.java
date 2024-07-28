
package Model;

import java.util.Date;

/**
 *
 * @author SwichBlade15
 */
public class DatosDescuentos {
    int iddescuentos;
    String nombredescuentos;
    int descuentos;
    String descripcion;
    Date fechainicio;
    Date fechacierre;
    int cantidad;
    String estado;

    public DatosDescuentos(int iddescuentos, String nombredescuentos, int descuentos, String descripcion, Date fechainicio, Date fechacierre, int cantidad, String estado) {
        this.iddescuentos = iddescuentos;
        this.nombredescuentos = nombredescuentos;
        this.descuentos = descuentos;
        this.descripcion = descripcion;
        this.fechainicio = fechainicio;
        this.fechacierre = fechacierre;
        this.cantidad = cantidad;
        this.estado = estado;
    }
    
    public DatosDescuentos(){
        
    }

    public int getIddescuentos() {
        return iddescuentos;
    }

    public void setIddescuentos(int iddescuentos) {
        this.iddescuentos = iddescuentos;
    }

    public String getNombredescuentos() {
        return nombredescuentos;
    }

    public void setNombredescuentos(String nombredescuentos) {
        this.nombredescuentos = nombredescuentos;
    }
    
    public int getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(int descuentos) {
        this.descuentos = descuentos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
}
