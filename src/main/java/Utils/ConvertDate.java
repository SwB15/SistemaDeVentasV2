package Utils;

/**
 *
 * @author SwichBlade15
 */
public class ConvertDate {

    public ConvertDate() {
    }

    // Convertir de java.sql.Date a java.util.Date
    public java.util.Date sqlToUtil(java.sql.Date sqlDate) {
        if (sqlDate != null) {
            return new java.util.Date(sqlDate.getTime());
        } else {
            return null; // Manejar el caso cuando sqlDate es null
        }
    }

    // Convertir de java.util.Date a java.sql.Date
    public java.sql.Date utilToSql(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        } else {
            return null; // Manejar el caso cuando utilDate es null
        }
    }
}
