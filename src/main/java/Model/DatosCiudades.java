package Model;

/**
 *
 * @author SwichBlade15
 */
public class DatosCiudades {
    
    private int idciudades;
    private String ciudades;
    private int fk_departamentos;

    public DatosCiudades(int idciudades, String ciudades) {
        this.idciudades = idciudades;
        this.ciudades = ciudades;
    }

    public DatosCiudades() {
        
    }

    public int getIdciudades() {
        return idciudades;
    }

    public void setIdciudades(int idciudades) {
        this.idciudades = idciudades;
    }

    public String getCiudades() {
        return ciudades;
    }

    public void setCiudades(String ciudades) {
        this.ciudades = ciudades;
    }

    public int getFk_departamentos() {
        return fk_departamentos;
    }

    public void setFk_departamentos(int fk_departamentos) {
        this.fk_departamentos = fk_departamentos;
    }

    public String toString(){
        return this.ciudades;
    }
}
