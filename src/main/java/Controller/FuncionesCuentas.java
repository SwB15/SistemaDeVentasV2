package Controller;

import Config.DataSource;
import Model.DatosCuentas;
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
public class FuncionesCuentas {

    private String sSQL = "";
    public Integer totalRegistros;

    Statement st;
    PreparedStatement pst;
    ResultSet rs;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Departamentos"};
        String[] registros = new String[2];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT * FROM cuentas WHERE cuentas LIKE '%" + buscar + "%' ORDER BY idcuentas DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idcuentas");
                registros[1] = rs.getString("cuentas");
                registros[1] = rs.getString("codigo");
                registros[1] = rs.getString("descripcion");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public boolean insertar(DatosCuentas datos) {
        sSQL = "INSERT INTO cuentas(cuentas, codigo, descripcion) VALUES (?,?,?)";

        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getCuentas());
            pst.setString(2, datos.getCodigo());
            pst.setString(3, datos.getDescripcion());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosCuentas datos) {
        sSQL = "UPDATE cuentas SET cuentas = ?, codigo = ?, descripcion = ? WHERE idcuentas = ?";

        try(Connection cn = DataSource.getConnection()) {
            pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getCuentas());
            pst.setString(2, datos.getCodigo());
            pst.setString(3, datos.getDescripcion());
            pst.setInt(4, datos.getIdcuentas());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosCuentas datos) {
        sSQL = "DELETE FROM cuentas WHERE idcuentas = ?";
        try (Connection cn = DataSource.getConnection()){
            pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getIdcuentas());
            int N = pst.executeUpdate();

            return N != 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
}
