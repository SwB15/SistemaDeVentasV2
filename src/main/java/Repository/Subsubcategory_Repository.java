
package Repository;

import Config.DataSource;
import Model.Subsubcategory_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Repository {
String sql = "";
    PreparedStatement pst = null;

    public boolean Insert(Subsubcategory_Model model, int foreignKey) {
        sql = "INSERT INTO subsubcategorias(codigo, subsubcategorias, descripcion, fk_subcategorias) VALUES(?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubsubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Subsubcategory_Model model, String foreignName) {
        sql = "UPDATE subsubcategorias SET codigo = ?, subsubcategorias = ?, descripcion = ?, fk_subcategorias(SELECT idsubcategorias FROM subcategorias WHERE subcategorias LIKE'%" + foreignName + "%' limit 1) WHERE idsubsubcategorias = ?";
        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubsubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(3, model.getIdsubsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Subsubcategory_Model model) {
        sql = "DELETE FROM subsubcategorias WHERE idsubsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdsubsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
