package Repository;

import Config.DataSource;
import Model.Subsubcategory_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Repository {

    String sql = "";
    PreparedStatement pst = null;

//******************************** Begin of Insert, Update, Delete, Disable ********************************
    public boolean insert(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        sql = "INSERT INTO subsubcategorias(codigo, subsubcategorias, descripcion, fk_subcategorias, fk_estados) VALUES(?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubsubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);
            pst.setInt(5, foreignKey2);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        sql = "UPDATE subsubcategorias SET codigo = ?, subsubcategorias = ?, descripcion = ?, fk_subcategorias, fk_estados WHERE idsubsubcategorias = ?";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubsubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);
            pst.setInt(5, foreignKey2);
            pst.setInt(6, model.getIdsubsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Subsubcategory_Model model) {
        sql = "DELETE FROM subsubcategorias WHERE idsubsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdsubsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subsubcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean disable(Subsubcategory_Model model, int foreignKey) {
        sql = "UPDATE subsubcategorias SET fk_estados = ? WHERE idsubsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdsubsubcategorias());
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
    public DefaultTableModel showSubsubcategories(String search, String stateFilter) {
        DefaultTableModel model;
        String[] titles = {"Id", "Codigo", "Sub-subcategoría", "Descripción", "Subcategoría", "Categoría", "Estado"};
        String[] records = new String[7];
        int totalRecords = 0;
        model = new DefaultTableModel(null, titles);

        String sSQL = "SELECT ss.idsubsubcategorias, ss.codigo, ss.subsubcategorias, ss.descripcion, s.subcategorias, c.categorias, e.estados "
                + "FROM subsubcategorias ss "
                + "JOIN subcategorias s ON ss.fk_subcategorias = s.idsubcategorias "
                + "JOIN categorias c ON s.fk_categorias = c.idcategorias "
                + "JOIN estados e ON ss.fk_estados = e.idestados "
                + "WHERE ss.subsubcategorias LIKE ?";

        if (stateFilter.equals("activo")) {
            sSQL += " AND e.estados = 'activo'";
        } else if (stateFilter.equals("inactivo")) {
            sSQL += " AND e.estados = 'inactivo'";
        }

        sSQL += " ORDER BY ss.codigo DESC";

        try (Connection cn = DataSource.getConnection(); PreparedStatement pst = cn.prepareStatement(sSQL)) {
            pst.setString(1, "%" + search + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                records[0] = rs.getString("idsubsubcategorias");
                records[1] = rs.getString("codigo");
                records[2] = rs.getString("subsubcategorias");
                records[3] = rs.getString("descripcion");
                records[4] = rs.getString("subcategorias");
                records[5] = rs.getString("categorias");
                records[6] = rs.getString("estados");

                totalRecords++;
                model.addRow(records);
            }
            return model;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public HashMap<String, List<String>> fillSubcategoryCombobox(int foreignKey) {
        HashMap<String, List<String>> subcategoryMap = new HashMap<>();
        String sSQL = "SELECT idsubcategorias, codigo, subcategorias FROM subcategorias WHERE fk_categorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, foreignKey);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString("idsubcategorias");
                String code = rs.getString("codigo");
                String name = rs.getString("subcategorias");

                List<String> values = new ArrayList<>();
                values.add(code);
                values.add(id);

                subcategoryMap.put(name, values);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return subcategoryMap;
    }
//********************************End of Display Methods********************************
}
