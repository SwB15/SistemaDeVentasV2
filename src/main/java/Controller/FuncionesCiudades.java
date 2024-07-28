package Controller;

import Config.DataSource;
import Model.DatosCiudades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */

public class FuncionesCiudades {
    private String sSQL = "";
    public Integer totalRegistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Ciudad", "Departamento"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idciudades, ciudades, (SELECT departamentos FROM departamentos WHERE iddepartamentos = fk_departamentos) AS departamentos FROM ciudades WHERE ciudades LIKE '%" + buscar + "%' ORDER BY idciudades DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idciudades");
                registros[1] = rs.getString("ciudades");
                registros[2] = rs.getString("departamentos");
                
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public boolean insertar(DatosCiudades dciu, int nombre) {
        sSQL = "INSERT INTO ciudades(ciudades, fk_departamentos) VALUES (?,?)";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dciu.getCiudades());
            pst.setInt(2, nombre);
            
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosCiudades datos, String nombre) {
        sSQL = "UPDATE ciudades SET ciudades = ?, fk_departamentos = (SELECT iddepartamentos FROM departamentos WHERE departamentos LIKE '%" + nombre + "%' limit 1) WHERE idciudades = ? ";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getCiudades());
            pst.setInt(2, datos.getIdciudades());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosCiudades datos) {
        sSQL = "DELETE FROM ciudades WHERE idciudades = ?";
        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdciudades());
            
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public ArrayList<String> llenar_comboDep() {
        ArrayList<String> lista = new ArrayList<String>();
        sSQL = "SELECT departamentos FROM departamentos";
        
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                lista.add(rs.getString("departamentos"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
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
    
    //La primera vez que se inicia el Sistema, se ingresa un Usuario provisorio, pero este Usuario depende de Ciudades
    //Y Ciudades depende de Departamentos, por lo cual, debemos ingresar un primer departamento y una primera ciudad
    public boolean insertarPrimeraCiudad(DatosCiudades dciu, int nombre) {
        sSQL = "INSERT INTO ciudades (ciudades, fk_departamentos) VALUES ('CONCEPCION', ?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, nombre);
            pst.executeUpdate();
        } catch (SQLException e) {
        }
        return true;
    }
}