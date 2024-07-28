package Controller;

import Config.DataSource;
import Model.DatosCiudades;
import Model.DatosDepartamentos;
import Model.DatosUsuarios;
import Vista.Personas.Usuarios;
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
public class FuncionesUsuarios {

    private String sSQL = "";
    public Integer totalRegistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Usuario", "Nombre", "Apellido", "Cedula", "Direccion", "Telefono", "Email", "Permisos"};

        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idusuarios, usuario, nombre, apellido, cedula, direccion, telefono, email, permisos FROM usuarios WHERE usuario LIKE '%" + buscar + "%' ORDER BY idusuarios DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idusuarios");
                registros[1] = rs.getString("usuario");
                registros[2] = rs.getString("nombre");
                registros[3] = rs.getString("apellido");
                registros[4] = rs.getString("cedula");
                registros[5] = rs.getString("direccion");
                registros[6] = rs.getString("telefono");
                registros[7] = rs.getString("email");
                registros[8] = rs.getString("permisos");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public boolean insertar(DatosUsuarios datos, int nombre) {
        sSQL = "INSERT INTO usuarios (usuario, contrasena, nombre, apellido, cedula, direccion, telefono, email, permisos, fk_ciudades) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setString(1, datos.getUsuarios());
            pst.setString(2, datos.getContrasena());
            pst.setString(3, datos.getNombre());
            pst.setString(4, datos.getApellido());
            pst.setString(5, datos.getCedula());
            pst.setString(6, datos.getDireccion());
            pst.setString(7, datos.getTelefono());
            pst.setString(8, datos.getEmail());
            pst.setString(9, datos.getPermisos());
            pst.setInt(10, nombre);
            int N = pst.executeUpdate();

            if (N != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosUsuarios datos) {
        sSQL = "UPDATE usuarios SET usuario = ?, contrasena = ?, nombre = ?, apellido = ?, cedula = ?, direccion = ?, telefono = ?, email = ?, permisos = ? WHERE idusuarios = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getUsuarios());
            pst.setString(2, datos.getContrasena());
            pst.setString(3, datos.getNombre());
            pst.setString(4, datos.getApellido());
            pst.setString(5, datos.getCedula());
            pst.setString(6, datos.getDireccion());
            pst.setString(7, datos.getTelefono());
            pst.setString(8, datos.getEmail());
            pst.setString(9, datos.getPermisos());
            pst.setInt(10, datos.getIdusuario());

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

    public boolean eliminar(DatosUsuarios datos) {
        sSQL = "DELETE FROM usuarios WHERE idusuarios = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getIdusuario());
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

    public int ContarUsuarios() {
        sSQL = "SELECT COUNT(*)AS cantidadUsuarios FROM usuarios";

        try (Connection cn = DataSource.getConnection()){
            int codigo_venta = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo_venta = rs.getInt("cantidadUsuarios");
            }
            return codigo_venta;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    public int ContarDepartamentos() {
        sSQL = "SELECT COUNT(*)AS cantidadDepartamentos FROM departamentos";

        try (Connection cn = DataSource.getConnection()){
            int codigo_dep = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo_dep = rs.getInt("cantidadDepartamentos");
            }
            return codigo_dep;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    public int ContarCiudades() {
        sSQL = "SELECT COUNT(*)AS cantidadCiudades FROM ciudades";

        try (Connection cn = DataSource.getConnection()){
            int codigo_ciu = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo_ciu = rs.getInt("cantidadCiudades");
            }
            return codigo_ciu;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    //Carga una tabla en el Formulario Login, del cual se extrae para comparar el usuario y contraseña ingresados y verificarlo
    public DefaultTableModel login(String login) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Usuario", "Contraseña", "Nombre", "Apellido", "Cedula", "Direccion", "Telefono", "Email", "Permisos"};
        String[] registro = new String[10];

        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT idusuarios, usuario, contrasena, nombre, apellido, cedula, direccion, telefono, email, permisos FROM usuarios WHERE usuario = '" + login + "'";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registro[0] = rs.getString("idusuarios");
                registro[1] = rs.getString("usuario");
                registro[2] = rs.getString("contrasena");
                registro[3] = rs.getString("nombre");
                registro[4] = rs.getString("apellido");
                registro[5] = rs.getString("cedula");
                registro[6] = rs.getString("direccion");
                registro[7] = rs.getString("telefono");
                registro[8] = rs.getString("email");
                registro[9] = rs.getString("permisos");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public int verificarLogin() {
        String login = Usuarios.txtUsuarios.getText();
        sSQL = "SELECT COUNT(usuarios) AS usuarios FROM usuarios WHERE usuarios = " + login;

        try (Connection cn = DataSource.getConnection()){
            int loginResultante = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                loginResultante = rs.getInt("login");
            }
            return loginResultante;
        } catch (SQLException e) {
            return 0;
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    public int buscarCiudad(String usuario) {
        sSQL = "SELECT idciudades FROM ciudades WHERE ciudades.ciudades = '" + usuario + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idciudades");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
}
