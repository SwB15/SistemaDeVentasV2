package Controller;

import Config.DataSource;
import Model.DatosDepartamentos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */

public class FuncionesDepartamentos {
    private String sSQL = "";
    public Integer totalRegistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Departamentos"};
        String[] registros = new String[2];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT * FROM departamentos WHERE departamentos LIKE '%" + buscar + "%' ORDER BY iddepartamentos DESC";

        try(Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("iddepartamentos");
                registros[1] = rs.getString("departamentos");
                
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    
    
    public boolean insertar(DatosDepartamentos datos) {
        sSQL = "INSERT INTO departamentos(departamentos) VALUES (?)";
        
        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getDepartamentos());
            
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

    public boolean editar(DatosDepartamentos datos) {
        sSQL = "UPDATE departamentos SET departamentos = ? WHERE iddepartamentos = ?";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getDepartamentos());
            pst.setInt(2, datos.getIddepartamentos());

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

    public boolean eliminar(DatosDepartamentos datos) {
        sSQL = "DELETE FROM departamentos WHERE iddepartamentos = ?";
        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getIddepartamentos());
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
    
    
    //La primera vez que se inicia el Sistema, se ingresa un Usuario provisorio, pero este Usuario depende de Ciudades
    //Y Ciudades depende de Departamentos, por lo cual, debemos ingresar un primer departamento y una primera ciudad
    public boolean insertarPrimerDepartamento(DatosDepartamentos datos) {
        sSQL = "INSERT INTO departamentos (departamentos)"
                + "VALUES ('CONCEPCION')";
        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.executeUpdate();
        } catch (Exception e) {
        }
        return true;
    }
}
