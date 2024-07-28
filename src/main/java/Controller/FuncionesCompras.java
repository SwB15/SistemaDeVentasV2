
package Controller;

import Config.DataSource;
import Model.DatosCompras;
import Model.DatosDetalleCompra;
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
public class FuncionesCompras {
    private String sSQL = "";
    public Integer totalRegistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Boleta", "Fecha", "Hora", "Precio Total", "Proveedores"};
        String[] registros = new String[6];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idcompras, boletanumero, fecha, hora, preciototal, (SELECT proveedores FROM proveedores WHERE idproveedores = fk_proveedores) AS proveedores FROM compras WHERE boletanumero LIKE '%" + buscar + "%' ORDER BY idcompras DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idcompras");
                registros[1] = rs.getString("boletanumero");
                registros[2] = rs.getString("fecha");
                registros[3] = rs.getString("hora");
                registros[4] = rs.getString("preciototal");
                registros[5] = rs.getString("proveedores");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public boolean insertar(DatosCompras datos, int fk_proveedores) {
        sSQL = "INSERT INTO compras (boletanumero, fecha, hora, preciototal, fk_proveedores) VALUES (?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getBoletanumero());
            pst.setString(2, datos.getFecha());
            pst.setString(3, datos.getHora());
            pst.setInt(4, datos.getPreciototal());
            pst.setInt(5, fk_proveedores);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public boolean insertarDetalleCompra(DatosDetalleCompra datos2, int fk_productos, int fk_compras) {
        sSQL = "INSERT INTO detallecompra (precio, cantidad, fk_productos, fk_compras) VALUES(?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst2 = cn.prepareStatement(sSQL);
            
            pst2.setInt(1, datos2.getPrecio());
            pst2.setInt(2, datos2.getCantidad());
            pst2.setInt(3, fk_productos);
            pst2.setInt(4, fk_compras);

            int N = pst2.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosCompras datos) {
        sSQL = "DELETE FROM compras WHERE idcompras = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdcompras());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public boolean eliminarDetalles(DatosDetalleCompra datos) {
        sSQL = "DELETE FROM detallecompra WHERE iddetallecompra = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIddetallecompra());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public int buscarProveedores(String proveedores){
        sSQL = "SELECT idproveedores FROM proveedores WHERE proveedores.proveedores = '"+proveedores+"'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idproveedores");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
    
    public int buscarProductos(String productos){
        sSQL = "SELECT idproductos FROM productos WHERE productos.productos = '"+productos+"'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idproductos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
    
    public int buscarCompras(String compras){
        sSQL = "SELECT idcompras FROM compras WHERE compras.boletanumero = '"+compras+"'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idcompras");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
    
    //Funciones para ListaServicios
    public DefaultTableModel mostrarDetalleCompras(int buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Precio", "Cantidad", "Fk_Compras", "Fk_productos"};
        String[] registros = new String[5];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement st = cn.prepareStatement("SELECT iddetallecompras, precio, cantidad, fk_fichaservicios, "
                    + "fk_productos FROM detallecompras WHERE fk_compras = ?");
            st.setInt(1, buscar);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("iddetallecompras");
                registros[1] = rs.getString("precio");
                registros[2] = rs.getString("cantidad");
                registros[3] = rs.getString("fk_compras");
                registros[4] = rs.getString("fk_productos");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public DefaultTableModel seleccionarListaProductos(int buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Codigo", "Productos", "Precio", "Cantidad"};
        String[] registros = new String[5];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement st = cn.prepareStatement("SELECT idproductos, codigo, productos, precio, cantidad FROM productos "
                    + "WHERE idproductos = ? ORDER BY idproductos DESC");
            st.setInt(1, buscar);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("codigo");
                registros[2] = rs.getString("productos");
                registros[3] = rs.getString("precio");
                registros[4] = rs.getString("cantidad");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
}
