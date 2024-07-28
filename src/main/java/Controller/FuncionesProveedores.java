package Controller;

import Config.DataSource;
import Model.DatosCiudades;
import Model.DatosDepartamentos;
import Model.DatosProveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class FuncionesProveedores {

    private String sSQL = ""; //Sentencia SQL
    private String sSQL2 = "";
    public Integer totalRegistros; // Obtener los registros

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Proveedor", "RUC", "Telefono", "Correo", "Direccion", "Ciudad"};
        String[] registros = new String[7];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproveedores, proveedores, ruc, telefono, email, direccion, (SELECT ciudades FROM ciudades WHERE idciudades = fk_ciudades) AS ciudades FROM proveedores WHERE proveedores LIKE '%" + buscar + "%' ORDER BY idproveedores DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproveedores");
                registros[1] = rs.getString("proveedores");
                registros[2] = rs.getString("ruc");
                registros[3] = rs.getString("telefono");
                registros[4] = rs.getString("email");
                registros[5] = rs.getString("direccion");
                registros[6] = rs.getString("ciudades");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    //Tabla para la seleccion de proveedores en Compra y Venta
    public DefaultTableModel seleccionarProveedores(String seleccionar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Proveedor", "RUC", "Telefono"};
        String[] registros = new String[4];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproveedores, proveedores, ruc, telefono FROM proveedores WHERE proveedores LIKE '%" + seleccionar + "%' ORDER BY idproveedores DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproveedores");
                registros[1] = rs.getString("proveedores");
                registros[2] = rs.getString("ruc");
                registros[3] = rs.getString("telefono");
                
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public boolean insertar(DatosProveedores datos, int nombre) {
        sSQL = "INSERT INTO proveedores (proveedores, ruc, direccion, telefono, email, fk_ciudades) VALUES (?,?,?,?,?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getProveedores());
            pst.setString(2, datos.getRuc());
            pst.setString(3, datos.getDireccion());
            pst.setString(4, datos.getTelefono());
            pst.setString(5, datos.getEmail());
            pst.setInt(6, nombre);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosProveedores datos, String nombre) {
        sSQL = "UPDATE proveedores SET proveedores = ?, ruc = ?, direccion = ?,"
                + "telefono = ?, email = ?, fk_ciudades = (SELECT idciudades FROM ciudades WHERE ciudades LIKE '%" + nombre + "%' limit 1) WHERE idproveedores = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setString(1, datos.getProveedores());
            pst.setString(2, datos.getRuc());
            pst.setString(3, datos.getDireccion());
            pst.setString(4, datos.getTelefono());
            pst.setString(5, datos.getEmail());
            pst.setInt(6, datos.getIdproveedores());

            int N = pst.executeUpdate();
            if (N != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosProveedores datos) {
        sSQL = "DELETE FROM proveedores WHERE idproveedores = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdproveedores());

            int N = pst.executeUpdate();
            if (N != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Agrega los departamentos al ComboBox Departamentos
    public void listar_departamentos(JComboBox box) {
        DefaultComboBoxModel value;
        DataSource conec = new DataSource();
        Statement st = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = DataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM departamentos");
            value = new DefaultComboBoxModel();
            box.setModel(value);
            while (rs.next()) {
                value.addElement(new DatosDepartamentos(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                st.close();
                rs.close();
//                conec.desconectar();
            } catch (SQLException ex) {
            }
        }
    }

    //Agrega las ciudades al ComboBox Ciudades
    public void listar_ciudades(JComboBox box, int id) {
        DefaultComboBoxModel value;
        DataSource conec = new DataSource();
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = DataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM ciudades WHERE fk_departamentos = ?");
            ps.setInt(1, id);
            System.out.println(id);
            rs = ps.executeQuery();
            value = new DefaultComboBoxModel();
            box.setModel(value);
            while (rs.next()) {
                value.addElement(new DatosCiudades(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public int buscarCiudades(String ciudad){
        sSQL = "SELECT idciudades FROM ciudades WHERE ciudades.ciudades = '"+ciudad+"'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idciudades");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
    
    public int buscarDepartamento(String ciudad){
        sSQL = "SELECT iddepartamentos FROM departamentos WHERE departamentos.departamentos = '"+ciudad+"'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("iddepartamentos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
}
