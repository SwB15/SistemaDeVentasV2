package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosDetalleVentas {
    int iddetalleventas;
    int precio;
    int descuento;
    int cantidad;
    int fk_productos;
    int fk_ventas;

    public DatosDetalleVentas(int iddetalleventas, int precio, int descuento, int cantidad, int fk_productos, int fk_ventas) {
        this.iddetalleventas = iddetalleventas;
        this.precio = precio;
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.fk_productos = fk_productos;
        this.fk_ventas = fk_ventas;
    }
    
    public DatosDetalleVentas(){
        
    }

    public int getIddetalleventas() {
        return iddetalleventas;
    }

    public void setIddetalleventas(int iddetalleventas) {
        this.iddetalleventas = iddetalleventas;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
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

    public int getFk_ventas() {
        return fk_ventas;
    }

    public void setFk_ventas(int fk_ventas) {
        this.fk_ventas = fk_ventas;
    }
    
    
}
