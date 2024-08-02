package Repository;

import Config.DataSource;
import Model.Product_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Product_Repository {

    String sql = "";
    PreparedStatement pst = null;

//********************************Begin of Insert, Update, Delete, Disable********************************
    public boolean insert(Product_Model model, int foreignKey, int foreignKey2) {
        sql = "INSERT INTO productos(codigo, nombre, precio, presentacion, descripcion, fechavencimiento, iva, fk_subcategorias, fk_estados) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getNombre());
            pst.setInt(3, model.getPrecio());
            pst.setString(4, model.getPresentacion());
            pst.setString(5, model.getDescripcion());
            pst.setDate(6, model.getFechavencimiento());
            pst.setInt(7, model.getIva());
            pst.setInt(8, foreignKey);
            pst.setInt(9, foreignKey2);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Product_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Product_Model model, int foreignKey, int foreignKey2) {
        sql = "UPDATE productos SET codigo = ?, nombre = ?, precio = ?, presentacion = ?, descripcion = ?, fechavencimiento = ?, iva = ?, fk_subcategorias = ?, fk_estados = ? WHERE idproductos = ?";
        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setString(1, model.getCodigo());
            pst.setString(2, model.getNombre());
            pst.setInt(3, model.getPrecio());
            pst.setString(4, model.getPresentacion());
            pst.setString(5, model.getDescripcion());
            pst.setDate(6, model.getFechavencimiento());
            pst.setInt(7, model.getIva());
            pst.setInt(8, foreignKey);
            pst.setInt(9, foreignKey2);
            pst.setInt(10, model.getIdproductos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Product_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Product_Model model) {
        sql = "DELETE FROM productos WHERE idproductos = ?";

        try (Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sql);
            pst.setInt(1, model.getIdproductos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(Product_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//******************************** End of Insert, Update, Delete, Disable ********************************

//********************************Begin of Display Methods********************************
    public DefaultTableModel showProducts(String search, String stateFilter) {
        DefaultTableModel model;
        String[] titles = {"Id", "Codigo", "Producto", "Precio", "Presentación", "Descripción", "F. Venc.", "Iva", "Idsubcategorias", "Subcategoría", "Estado"};
        String[] records = new String[11];
        int totalRecords = 0;
        model = new DefaultTableModel(null, titles);

        String sSQL = "SELECT p.idproductos, p.codigo, p.nombre, p.precio, presentacion, p.descripcion, p.fechavencimiento, p.iva, s.idsubcategorias, s.subcategorias, e.estados "
                + "FROM productos p "
                + "JOIN subcategorias s ON p.fk_subcategorias = s.idsubcategorias "
                + "JOIN estados e ON s.fk_estados = e.idestados "
                + "WHERE p.nombre LIKE ?";

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
                records[0] = rs.getString("idproductos");
                records[1] = rs.getString("codigo");
                records[2] = rs.getString("nombre");
                records[3] = rs.getString("precio");
                records[4] = rs.getString("presentacion");
                records[5] = rs.getString("descripcion");
                records[6] = rs.getString("fechavencimiento");
                records[7] = rs.getString("iva");
                records[8] = rs.getString("idsubcategorias");
                records[9] = rs.getString("subcategorias");
                records[10] = rs.getString("estados");

                totalRecords++;
                model.addRow(records);
            }
            return model;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public HashMap<String, HashMap<String, List<String>>> fillCategoryAndSubcategoryComboboxes() {
        HashMap<String, HashMap<String, List<String>>> categoryMap = new HashMap<>();
        String sSQL = "SELECT c.idcategorias, c.codigo AS catCodigo, c.categorias, s.idsubcategorias, s.codigo AS subCodigo, s.subcategorias "
                + "FROM categorias c "
                + "LEFT JOIN subcategorias s ON c.idcategorias = s.fk_categorias";

        try (Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String categoryId = rs.getString("idcategorias");
                String categoryCode = rs.getString("catCodigo");
                String categoryName = rs.getString("categorias");

                String subcategoryId = rs.getString("idsubcategorias");
                String subcategoryCode = rs.getString("subCodigo");
                String subcategoryName = rs.getString("subcategorias");

                HashMap<String, List<String>> subcategoryMap;
                if (categoryMap.containsKey(categoryName)) {
                    subcategoryMap = categoryMap.get(categoryName);
                } else {
                    subcategoryMap = new HashMap<>();
                    List<String> categoryValues = new ArrayList<>();
                    categoryValues.add(categoryCode);
                    categoryValues.add(categoryId);
                    subcategoryMap.put("categoryValues", categoryValues);
                    categoryMap.put(categoryName, subcategoryMap);
                }

                if (subcategoryName != null) {
                    List<String> subcategoryValues = new ArrayList<>();
                    subcategoryValues.add(subcategoryCode);
                    subcategoryValues.add(subcategoryId);
                    subcategoryMap.put(subcategoryName, subcategoryValues);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return categoryMap;
    }
//********************************End of Display Methods********************************
}
