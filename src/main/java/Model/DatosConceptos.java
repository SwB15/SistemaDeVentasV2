
package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosConceptos {
    int idconceptos;
    String conceptos;

    public DatosConceptos(int idconceptos, String conceptos) {
        this.idconceptos = idconceptos;
        this.conceptos = conceptos;
    }

    public DatosConceptos() {
    
    }
    
    public int getIdconceptos() {
        return idconceptos;
    }

    public void setIdconceptos(int idconceptos) {
        this.idconceptos = idconceptos;
    }

    public String getConceptos() {
        return conceptos;
    }

    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
    }

}
