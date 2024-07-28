package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosCategorias {

    int idcategorias;
    String categorias;

    public DatosCategorias(int idcategorias, String categorias) {
        this.idcategorias = idcategorias;
        this.categorias = categorias;
    }

    public DatosCategorias() {
        
    }

    public int getIdcategorias() {
        return idcategorias;
    }

    public void setIdcategorias(int idcategorias) {
        this.idcategorias = idcategorias;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }
}
