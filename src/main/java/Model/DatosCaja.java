package Model;

/**
 *
 * @author SwichBlde15
 */
public class DatosCaja {

    int idcaja;
    String fechapertura;
    String horaapertura;
    int saldoapertura;
    int saldocierre;
    int ingresodia;
    int egresodia;
    String fechacierre;
    String horacierre;
    int fk_usuarios;

    public DatosCaja() {

    }

    public DatosCaja(int idcaja, String fechapertura, String horaapertura, int saldoapertura, int saldocierre, int ingresodia, int egresodia, String fechacierre, String horacierre, int fk_usuarios) {
        this.idcaja = idcaja;
        this.fechapertura = fechapertura;
        this.horaapertura = horaapertura;
        this.saldoapertura = saldoapertura;
        this.saldocierre = saldocierre;
        this.ingresodia = ingresodia;
        this.egresodia = egresodia;
        this.fechacierre = fechacierre;
        this.horacierre = horacierre;
        this.fk_usuarios = fk_usuarios;
    }

    public int getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(int idcaja) {
        this.idcaja = idcaja;
    }

    public String getFechapertura() {
        return fechapertura;
    }

    public void setFechapertura(String fechapertura) {
        this.fechapertura = fechapertura;
    }

    public String getHoraapertura() {
        return horaapertura;
    }

    public void setHoraapertura(String horaapertura) {
        this.horaapertura = horaapertura;
    }

    public int getSaldoapertura() {
        return saldoapertura;
    }

    public void setSaldoapertura(int saldoapertura) {
        this.saldoapertura = saldoapertura;
    }

    public int getSaldocierre() {
        return saldocierre;
    }

    public void setSaldocierre(int saldocierre) {
        this.saldocierre = saldocierre;
    }

    public int getIngresodia() {
        return ingresodia;
    }

    public void setIngresodia(int ingresodia) {
        this.ingresodia = ingresodia;
    }

    public int getEgresodia() {
        return egresodia;
    }

    public void setEgresodia(int egresodia) {
        this.egresodia = egresodia;
    }

    public String getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(String fechacierre) {
        this.fechacierre = fechacierre;
    }

    public String getHoracierre() {
        return horacierre;
    }

    public void setHoracierre(String horacierre) {
        this.horacierre = horacierre;
    }

    public int getFk_usuarios() {
        return fk_usuarios;
    }

    public void setFk_usuarios(int fk_usuarios) {
        this.fk_usuarios = fk_usuarios;
    }

    
}
