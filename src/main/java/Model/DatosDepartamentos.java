package Model;

/**
 *
 * @author SwichBlade15
 */

public class DatosDepartamentos {
    
    private int iddepartamentos;
    private String departamentos;
    
    public DatosDepartamentos(){
        
    }

    public DatosDepartamentos(int iddepartamentos, String departamentos) {
        this.iddepartamentos = iddepartamentos;
        this.departamentos = departamentos;
    }

    public int getIddepartamentos() {
        return iddepartamentos;
    }

    public void setIddepartamentos(int iddepartamentos) {
        this.iddepartamentos = iddepartamentos;
    }

    public String getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(String departamentos) {
        this.departamentos = departamentos;
    }
    
    @Override
    public String toString(){
        return this.departamentos;
    }
}
