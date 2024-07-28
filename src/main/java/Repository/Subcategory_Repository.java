package Repository;

import Config.DataSource;
import Model.Subcategory_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SwichBlade15
 */
public class Subcategory_Repository {

    String sql = "";
    PreparedStatement pst = null;

    public boolean insert(Subcategory_Model model, int foreignKey) {
        sql = "INSERT INTO subcategorias(codigo, subcategorias, descripcion, fk_categorias) VALUES(?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Subcategory_Model model, String foreignName) {
        sql = "UPDATE subcategorias SET codigo = ?, subcategorias = ?, descripcion = ?, fk_categorias(SELECT idcategorias FROM categorias WHERE categorias LIKE'%" + foreignName + "%' limit 1) WHERE idsubcategorias = ?";
        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, model.getIdsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Subcategory_Model model) {
        sql = "DELETE FROM subcategorias WHERE idsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
