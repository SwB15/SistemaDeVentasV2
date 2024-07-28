
package Model;

import java.util.Date;

/**
 *
 * @author SwichBlade15
 */
public class DatosProductos {
    int idproductos;
    String productos;
    String tipocodigo;
    String codigo;
    int cantidad;
    int cantidadminima;
    int cantidadxmayor;
    int preciominorista;
    int preciomayorista;
    int iva;
    Date fechavencimiento;
    String unidadmedida;
    int fk_categorias;
    int fk_proveedores;

    public DatosProductos(int idproductos, String productos, String tipocodigo, String codigo, int cantidad, int cantidadminima, int cantidadxmayor, int preciominorista, int preciomayorista, int iva, Date fechavencimiento, String unidadmedida, int fk_categorias, int fk_proveedores) {
        this.idproductos = idproductos;
        this.productos = productos;
        this.tipocodigo = tipocodigo;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.cantidadminima = cantidadminima;
        this.cantidadxmayor = cantidadxmayor;
        this.preciominorista = preciominorista;
        this.preciomayorista = preciomayorista;
        this.iva = iva;
        this.fechavencimiento = fechavencimiento;
        this.unidadmedida = unidadmedida;
        this.fk_categorias = fk_categorias;
        this.fk_proveedores = fk_proveedores;
    }

    public DatosProductos() {
        
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getTipocodigo() {
        return tipocodigo;
    }

    public void setTipocodigo(String tipocodigo) {
        this.tipocodigo = tipocodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadminima() {
        return cantidadminima;
    }

    public void setCantidadminima(int cantidadminima) {
        this.cantidadminima = cantidadminima;
    }

    public int getCantidadxmayor() {
        return cantidadxmayor;
    }

    public void setCantidadxmayor(int cantidadxmayor) {
        this.cantidadxmayor = cantidadxmayor;
    }

    public int getPreciominorista() {
        return preciominorista;
    }

    public void setPreciominorista(int preciominorista) {
        this.preciominorista = preciominorista;
    }

    public int getPreciomayorista() {
        return preciomayorista;
    }

    public void setPreciomayorista(int preciomayorista) {
        this.preciomayorista = preciomayorista;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public int getFk_categorias() {
        return fk_categorias;
    }

    public void setFk_categorias(int fk_categorias) {
        this.fk_categorias = fk_categorias;
    }

    public int getFk_proveedores() {
        return fk_proveedores;
    }

    public void setFk_proveedores(int fk_proveedores) {
        this.fk_proveedores = fk_proveedores;
    }

    
}
