package Model;

/**
 *
 * @author SwichBlade15
 */
public class Subcategory_Model {

    private int idsubcategorias;
    private String codigo;
    private String subcategorias;
    private String descripcion;
    private int fk_categorias;
    private int fk_estados;

    public Subcategory_Model(int idsubcategorias, String codigo, String subcategorias, String descripcion, int fk_categorias, boolean estados) {
        this.idsubcategorias = idsubcategorias;
        this.codigo = codigo;
        this.subcategorias = subcategorias;
        this.descripcion = descripcion;
        this.fk_categorias = fk_categorias;
        this.fk_estados = estados ? 1 : 0;
    }

    public Subcategory_Model() {
    }

    public int getIdsubcategorias() {
        return idsubcategorias;
    }

    public void setIdsubcategorias(int idsubcategorias) {
        this.idsubcategorias = idsubcategorias;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategoria(String subcategorias) {
        this.subcategorias = subcategorias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFk_categorias() {
        return fk_categorias;
    }

    public void setFk_categorias(int fk_categorias) {
        this.fk_categorias = fk_categorias;
    }

    public boolean getFk_estados() {
        return fk_estados == 1;
    }

    public void setFk_estados(boolean estados) {
        this.fk_estados = estados ? 1 : 0;
    }
}
