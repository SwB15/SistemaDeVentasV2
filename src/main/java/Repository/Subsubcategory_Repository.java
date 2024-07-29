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
    public boolean insert(Subsubcategory_Model model, int foreignKey) {
        sql = "INSERT INTO subsubcategorias(codigo, subsubcategorias, descripcion, fk_subcategorias) VALUES(?,?,?,?)";
        try (Connection cn = DataSource.getConnection()) {
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

    public boolean update(Subsubcategory_Model model, int foreignKey) {
        sql = "UPDATE subsubcategorias SET codigo = ?, subsubcategorias = ?, descripcion = ?, fk_subcategorias WHERE idsubsubcategorias = ?";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubsubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);
            pst.setInt(5, model.getIdsubsubcategorias());

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
        String[] titles = {"Codigo", "Subsubcategoría", "Subcategoría", "Categoría", "Descripción", "Estado"};
        String[] records = new String[6];
        int totalRecords = 0;
        model = new DefaultTableModel(null, titles);

        String sSQL = "SELECT ss.codigo, ss.subsubcategorias, s.subcategorias, c.categorias, ss.descripcion, e.estados "
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
                records[0] = rs.getString("codigo");
                records[1] = rs.getString("subsubcategorias");
                records[2] = rs.getString("subcategorias");
                records[3] = rs.getString("categorias");
                records[4] = rs.getString("descripcion");
                records[5] = rs.getString("estados");

                totalRecords++;
                model.addRow(records);
            }
            return model;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    public HashMap<String, List<String>> fillSubcategoryCombos() {
        HashMap<String, List<String>> subcategoryMap = new HashMap<>();
        String sSQL = "SELECT idsubcategorias, codigo, subcategorias FROM subcategorias";

        try (Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

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
