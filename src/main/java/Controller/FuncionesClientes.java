package Controller;

import Config.DataSource;
import Model.DatosCiudades;
import Model.DatosClientes;
import Model.DatosDepartamentos;
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
public class FuncionesClientes {

    private String sSQL = "";
    public Integer totalRegistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Nombre", "Apellido", "Cedula", "Direccion", "Telefono", "Email", "Ciudad"};
        String[] registros = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idclientes, nombre, apellido, cedula, direccion, telefono, email, (SELECT ciudades FROM ciudades WHERE idciudades = fk_ciudades) AS ciudades FROM clientes WHERE nombre LIKE '%" + buscar + "%' ORDER BY idclientes DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idclientes");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("apellido");
                registros[3] = rs.getString("cedula");
                registros[4] = rs.getString("direccion");
                registros[5] = rs.getString("telefono");
                registros[6] = rs.getString("email");
                registros[7] = rs.getString("ciudades");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public DefaultTableModel seleccionarClientesConDescuentos(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Cliente", "Cedula", "Descuento"};
        String[] registros = new String[4];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT clientes.idclientes, clientes.nombre, clientes.apellido, clientes.cedula, descuentos.descuento "
                + "FROM clientes LEFT OUTER JOIN detalledescuentos ON clientes.idclientes = detalledescuentos.fk_clientes "
                + "LEFT OUTER JOIN descuentos ON detalledescuentos.fk_descuentos = descuentos.iddescuentos "
                + "WHERE nombre LIKE '%" + buscar + "%' ORDER BY idclientes DESC";

        try(Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idclientes");
                registros[1] = rs.getString("nombre")+" "+rs.getString("apellido");
                registros[2] = rs.getString("cedula");
                registros[3] = rs.getString("descuento");

                if(registros[3] == null){ 
                    registros[3] = "0";
                }
                
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
//**********************************FIN MOSTRAR**********************************

    public boolean insertar(DatosClientes datos, int nombre) {
        sSQL = "INSERT INTO clientes(nombre, apellido, cedula, direccion, telefono, email, fk_ciudades) VALUES(?,?,?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getNombre());
            pst.setString(2, datos.getApellido());
            pst.setString(3, datos.getCedula());
            pst.setString(4, datos.getDireccion());
            pst.setString(5, datos.getTelefono());
            pst.setString(6, datos.getEmail());
            pst.setInt(7, nombre);
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean editar(DatosClientes datos, String nombre) {
        sSQL = "UPDATE clientes SET nombre = ?, apellido = ?, cedula = ?, direccion = ?,"
                + "telefono = ?, email = ?, fk_ciudades = (SELECT idciudades FROM ciudades WHERE ciudades LIKE '%" + nombre + "%' limit 1) WHERE idclientes = ? ";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setString(1, datos.getNombre());
            pst.setString(2, datos.getApellido());
            pst.setString(3, datos.getCedula());
            pst.setString(4, datos.getDireccion());
            pst.setString(5, datos.getTelefono());
            pst.setString(6, datos.getEmail());
            pst.setInt(7, datos.getIdclientes());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosClientes datos) {
        sSQL = "DELETE FROM clientes WHERE idclientes = ? ";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdclientes());

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

    public int PrimerCliente() {
        sSQL = "SELECT COUNT(idclientes) AS cantidad FROM clientes ";

        try (Connection cn = DataSource.getConnection()){
            int cantidad = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
            return cantidad;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    public boolean insertarPrimerCliente() {
        sSQL = "INSERT INTO clientes (nombre, apellido, cedula, direccion, telefono, email)"
                + "VALUES ('Cliente', 'General','0','0','0','0')";
        System.out.println("acceso");

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.executeUpdate();
        } catch (Exception e) {
        }
        return true;
    }

    //Agrega los departamentos al ComboBox Departamentos
    public void listar_departamentos(JComboBox box) {
        DefaultComboBoxModel value;
        DataSource conec = new DataSource();
        Statement st = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = conec.getConnection();
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
            } catch (Exception ex) {
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
            con = conec.getConnection();
            ps = con.prepareStatement("SELECT * FROM ciudades WHERE fk_departamentos = ?");
            ps.setInt(1, id);
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

    public int buscarCiudades(String ciudad) {
        sSQL = "SELECT idciudades FROM ciudades WHERE ciudades.ciudades = '" + ciudad + "'";
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
}
