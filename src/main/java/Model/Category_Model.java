package Model;

/**
 *
 * @author SwichBlade15
 */
public class Category_Model {

    private int idcategorias;
    private String codigo;
    private String categorias;
    private String descripcion;
    private int fk_estados;

    public Category_Model(int idcategorias, String codigo, String categorias, String descripcion, boolean estado) {
        this.idcategorias = idcategorias;
        this.codigo = codigo;
        this.categorias = categorias;
        this.descripcion = descripcion;
        this.fk_estados = estado ? 1:0;
    }
    
    public Category_Model() {
    }

    public int getIdcategorias() {
        return idcategorias;
    }

    public void setIdcategorias(int idcategorias) {
        this.idcategorias = idcategorias;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getFk_estados() {
        return fk_estados == 1;
    }

    public void setFk_estados(boolean estado) {
        this.fk_estados = estado ? 1 : 0;
    }
}
