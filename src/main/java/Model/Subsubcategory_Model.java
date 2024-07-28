package Model;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Model {

    private int idsubsubcategorias;
    private String codigo;
    private String subsubcategorias;
    private String descripcion;
    private int fk_subcategorias;
    private int fk_estados;

    public Subsubcategory_Model(int idsubsubcategorias, String codigo, String subsubcategorias, String descripcion, int fk_subcategorias, boolean estados) {
        this.idsubsubcategorias = idsubsubcategorias;
        this.codigo = codigo;
        this.subsubcategorias = subsubcategorias;
        this.descripcion = descripcion;
        this.fk_subcategorias = fk_subcategorias;
        this.fk_estados = estados ? 1 : 0;
    }

    public Subsubcategory_Model() {
    }

    public int getIdsubsubcategorias() {
        return idsubsubcategorias;
    }

    public void setIdsubsubcategorias(int idsubsubcategorias) {
        this.idsubsubcategorias = idsubsubcategorias;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSubsubcategorias() {
        return subsubcategorias;
    }

    public void setSubsubcategorias(String subsubcategorias) {
        this.subsubcategorias = subsubcategorias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFk_subcategorias() {
        return fk_subcategorias;
    }

    public void setFk_subcategorias(int fk_subcategorias) {
        this.fk_subcategorias = fk_subcategorias;
    }

    public boolean getFk_estados() {
        return fk_estados == 1;
    }

    public void setFk_estados(boolean estados) {
        this.fk_estados = estados ? 1 : 0;
    }
}
