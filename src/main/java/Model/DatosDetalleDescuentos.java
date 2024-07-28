
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosDetalleDescuentos {
    int iddetalledescuentos;
    int fk_productos;
    int fk_descuentos;
    int fk_categorias;
    int fk_clientes;

    public DatosDetalleDescuentos(int iddetalledescuentos, int fk_productos, int fk_descuentos, int fk_categorias, int fk_clientes) {
        this.iddetalledescuentos = iddetalledescuentos;
        this.fk_productos = fk_productos;
        this.fk_descuentos = fk_descuentos;
        this.fk_categorias = fk_categorias;
        this.fk_clientes = fk_clientes;
    }

    public DatosDetalleDescuentos(){
        
    }
    
    public int getIddetalledescuentos() {
        return iddetalledescuentos;
    }

    public void setIddetalledescuentos(int iddetalledescuentos) {
        this.iddetalledescuentos = iddetalledescuentos;
    }

    public int getFk_productos() {
        return fk_productos;
    }

    public void setFk_productos(int fk_productos) {
        this.fk_productos = fk_productos;
    }

    public int getFk_descuentos() {
        return fk_descuentos;
    }

    public void setFk_descuentos(int fk_descuentos) {
        this.fk_descuentos = fk_descuentos;
    }

    public int getFk_categorias() {
        return fk_categorias;
    }

    public void setFk_categorias(int fk_categorias) {
        this.fk_categorias = fk_categorias;
    }

    public int getFk_clientes() {
        return fk_clientes;
    }

    public void setFk_clientes(int fk_clientes) {
        this.fk_clientes = fk_clientes;
    }
    
    
}
