package Controller;

import Config.DataSource;
import Model.DatosCategorias;
import View.Principal3;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */

public class FuncionesCategorias {

    private String sSQL = "";
    public Integer totalRegistros;
    Statement st;
    ResultSet rs;
    
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Categorias"};
        String[] registros = new String[2];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT * FROM categorias WHERE categorias LIKE '%" + buscar + "%' ORDER BY idcategorias DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idcategorias");
                registros[1] = rs.getString("categorias");
                
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    public DefaultTableModel descuentoCategorias(String buscar){
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Categorias", "Descuento"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "SELECT categorias.idcategorias, categorias.categorias, descuentos.descuento FROM categorias "
                + "LEFT OUTER JOIN detalledescuentos ON categorias.idcategorias = detalledescuentos.fk_categorias "
                + "LEFT OUTER JOIN descuentos ON detalledescuentos.fk_descuentos = descuentos.iddescuentos "
                + "WHERE categorias LIKE '%"+buscar+"%' ORDER BY idcategorias DESC";
        
        try(Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);
            
            while(rs.next()){
                registros[0] = rs.getString("idcategorias");
                registros[1] = rs.getString("categorias");
                registros[2] = rs.getString("descuento");
                
                if(registros[2] == null){ 
                    registros[2] = "0";
                }
                
                totalRegistros = totalRegistros+1;
                modelo.addRow(registros);
            }
            return modelo;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public void mostrarDescuento(String buscar) {
        try {
            DefaultTableModel modelo;
            modelo = descuentoCategorias(buscar);
            Principal3.tblCategorias.setModel(modelo);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ocultar_columnas() {
        Principal3.tblCategorias.getColumnModel().getColumn(0).setMaxWidth(0);
        Principal3.tblCategorias.getColumnModel().getColumn(0).setMinWidth(0);
        Principal3.tblCategorias.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public boolean insertar(DatosCategorias datos) {
        sSQL = "INSERT INTO categorias(categorias) VALUES (?)";
        
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getCategorias());
            
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosCategorias datos) {
        sSQL = "UPDATE categorias SET categorias = ? WHERE idcategorias = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getCategorias());
            pst.setInt(2, datos.getIdcategorias());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosCategorias datos) {
        sSQL = "DELETE FROM categorias WHERE idcategorias = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getIdcategorias());
            int N = pst.executeUpdate();

            return N != 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
}
