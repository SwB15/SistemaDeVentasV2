package Repository;

import Config.DataSource;
import Model.Subcategory_Model;
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
public class Subcategory_Repository {

    String sql = "";
    PreparedStatement pst = null;

//********************************Begin of Insert, Update, Delete, Disable********************************
    public boolean insert(Subcategory_Model model, int foreignKey, int foreignKey2) {
        sql = "INSERT INTO subcategorias(codigo, subcategorias, descripcion, fk_categorias, fk_estados) VALUES(?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);
            pst.setInt(5, foreignKey2);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Subcategory_Model model, int foreignKey, int foreignKey2) {
        sql = "UPDATE subcategorias SET codigo = ?, subcategorias = ?, descripcion = ?, fk_categorias = ?, fk_estados = ? WHERE idsubcategorias = ?";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getSubcategorias());
            pst.setString(3, model.getDescripcion());
            pst.setInt(4, foreignKey);
            pst.setInt(5, foreignKey2);
            pst.setInt(6, model.getIdsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Subcategory_Model model) {
        sql = "DELETE FROM subcategorias WHERE idsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdsubcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean disable(Subcategory_Model model, int foreignKey) {
        sql = "UPDATE subcategorias SET fk_estados = ? WHERE idsubcategorias = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, foreignKey);
            pst.setInt(2, model.getIdsubcategorias());
            
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//******************************** End of Insert, Update, Delete, Disable ********************************

//********************************Begin of Display Methods********************************
    public DefaultTableModel showSubcategories(String search, String stateFilter) {
        DefaultTableModel model;
        String[] titles = {"Id","Codigo", "Subcategoría", "Descripción", "Idcategorias","Categoría", "Estado"};
        String[] records = new String[7];
        int totalRecords = 0;
        model = new DefaultTableModel(null, titles);

        String sSQL = "SELECT s.idsubcategorias, s.codigo, s.subcategorias, s.descripcion, c.idcategorias,c.categorias, e.estados "
                + "FROM subcategorias s "
                + "JOIN categorias c ON s.fk_categorias = c.idcategorias "
                + "JOIN estados e ON s.fk_estados = e.idestados "
                + "WHERE s.subcategorias LIKE ?";

        if (stateFilter.equals("activo")) {
            sSQL += " AND e.estados = 'activo'";
        } else if (stateFilter.equals("inactivo")) {
            sSQL += " AND e.estados = 'inactivo'";
        }

        sSQL += " ORDER BY s.codigo DESC";

        try (Connection cn = DataSource.getConnection(); PreparedStatement pst = cn.prepareStatement(sSQL)) {
            pst.setString(1, "%" + search + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                records[0] = rs.getString("idsubcategorias");
                records[1] = rs.getString("codigo");
                records[2] = rs.getString("subcategorias");
                records[3] = rs.getString("descripcion");
                records[4] = rs.getString("idcategorias");
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

    public HashMap<String, List<String>> fillCategoryCombos() {
        HashMap<String, List<String>> categoryMap = new HashMap<>();
        String sSQL = "SELECT idcategorias, codigo, categorias FROM categorias";

        try (Connection cn = DataSource.getConnection(); 
                PreparedStatement pst = cn.prepareStatement(sSQL); 
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("idcategorias");
                String code = rs.getString("codigo");
                String name = rs.getString("categorias");

                List<String> values = new ArrayList<>();
                values.add(code);
                values.add(id);

                categoryMap.put(name, values);
            }

        } catch (SQLException e) {
            // Mostrar mensaje de error para el usuario
            JOptionPane.showMessageDialog(null, "Error al obtener las categorías: " + e.getMessage());

            // Registrar el error para el análisis posterior (opcional)
            Logger.getLogger(Subcategory_Repository.class.getName()).log(Level.SEVERE, "Error en fillCategoryCombos", e);
        }

        return categoryMap;
    }

//********************************End of Display Methods********************************
}
