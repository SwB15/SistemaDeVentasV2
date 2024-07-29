package Repository;

import Config.DataSource;
import Model.Category_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Category_Repository {

    String sql = "";
    PreparedStatement pst;
    
//********************************Begin of Insert, Update, Delete, Disable********************************
    public boolean insert(Category_Model model, int idestado) {
        sql = "INSERT INTO categorias (codigo, categorias, descripcion, fk_estados) VALUES (?,?,?,?)";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getCategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, idestado);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Category_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Category_Model model, int idestado) {
        sql = "UPDATE categorias SET codigo = ?, categorias = ?, descripcion = ?, fk_estados = ? WHERE idcategorias = ?";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getCategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, idestado);
            pst.setInt(5, model.getIdcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Category_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Category_Model model) {
        sql = "DELETE FROM categorias WHERE idcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean disable(Category_Model model, int foreignKey) {
        sql = "UPDATE categorias SET fk_estados = ? WHERE idcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdcategorias());
            pst.setInt(2, foreignKey);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//******************************** End of Insert, Update, Delete, Disable ********************************

//********************************Begin of Display Methods********************************
    public DefaultTableModel showCategories(String search, String stateFilter) {
        DefaultTableModel model;
        String[] titles = {"Codigo", "Categoría", "Descripción", "Estado"};
        String[] records = new String[4];
        int totalRecords = 0;
        model = new DefaultTableModel(null, titles);

        String sSQL = "SELECT c.codigo, c.categorias, c.descripcion, e.estados "
                    + "FROM categorias c "
                    + "JOIN estados e ON c.fk_estados = e.idestados "
                    + "WHERE c.categorias LIKE ?";

        if (stateFilter.equals("activo")) {
            sSQL += " AND e.estados = 'activo'";
        } else if (stateFilter.equals("inactivo")) {
            sSQL += " AND e.estados = 'inactivo'";
        }

        sSQL += " ORDER BY c.codigo DESC";

        try (Connection cn = DataSource.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) { //Don't touch!
            pst.setString(1, "%" + search + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                records[0] = rs.getString("codigo");
                records[1] = rs.getString("categorias");
                records[2] = rs.getString("descripcion");
                records[3] = rs.getString("estados");
                
                totalRecords++;
                model.addRow(records);
            }
            return model;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
//********************************End of Display Methods********************************

}
