
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosDetalleCompra {
    int iddetallecompra;
    int precio;
    int cantidad;
    int fk_productos;
    int fk_compras;

    public DatosDetalleCompra(int iddetallecompra, int precio, int cantidad, int fk_productos, int fk_compras) {
        this.iddetallecompra = iddetallecompra;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fk_productos = fk_productos;
        this.fk_compras = fk_compras;
    }

    public DatosDetalleCompra() {
        
    }

    public int getIddetallecompra() {
        return iddetallecompra;
    }

    public void setIddetallecompra(int iddetallecompra) {
        this.iddetallecompra = iddetallecompra;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFk_productos() {
        return fk_productos;
    }

    public void setFk_productos(int fk_productos) {
        this.fk_productos = fk_productos;
    }

    public int getFk_compras() {
        return fk_compras;
    }

    public void setFk_compras(int fk_compras) {
        this.fk_compras = fk_compras;
    }
    
}
