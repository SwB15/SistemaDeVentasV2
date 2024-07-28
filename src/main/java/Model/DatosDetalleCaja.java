
package Model;

/**
 *
 * @author SwichBlade15
 */

public class DatosDetalleCaja {
    int iddetallecaja;
    int fk_caja;
    int fk_ventas;
    int fk_comprasonline;
    int fk_servicios;
    int fk_compras;

    public DatosDetalleCaja(int iddetallecaja, int fk_caja, int fk_ventas, int fk_comprasonline, int fk_servicios, int fk_compras) {
        this.iddetallecaja = iddetallecaja;
        this.fk_caja = fk_caja;
        this.fk_ventas = fk_ventas;
        this.fk_comprasonline = fk_comprasonline;
        this.fk_servicios = fk_servicios;
        this.fk_compras = fk_compras;
    }
    
    public DatosDetalleCaja(){
        
    }

    public int getIddetallecaja() {
        return iddetallecaja;
    }

    public void setIddetallecaja(int iddetallecaja) {
        this.iddetallecaja = iddetallecaja;
    }
    
    public int getFk_caja() {
        return fk_caja;
    }

    public void setFk_caja(int fk_caja) {
        this.fk_caja = fk_caja;
    }

    public int getFk_ventas() {
        return fk_ventas;
    }

    public void setFk_ventas(int fk_ventas) {
        this.fk_ventas = fk_ventas;
    }

    public int getFk_comprasonline() {
        return fk_comprasonline;
    }

    public void setFk_comprasonline(int fk_comprasonline) {
        this.fk_comprasonline = fk_comprasonline;
    }

    public int getFk_servicios() {
        return fk_servicios;
    }

    public void setFk_servicios(int fk_servicios) {
        this.fk_servicios = fk_servicios;
    }

    public int getFk_compras() {
        return fk_compras;
    }

    public void setFk_compras(int fk_compras) {
        this.fk_compras = fk_compras;
    }
}
