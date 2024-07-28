package Controller;

import Config.DataSource;
import Model.DatosDetalleVentas;
import Model.DatosVentas;
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
public class FuncionesVentas {

    private String sSQL = "";
    public Integer totalRegistros;
    Statement st;
    ResultSet rs;

//**********************************MOSTRAR DATOS*********************************
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Boleta", "Fecha", "Hora", "Precio Total", "Clientes"};
        String[] registros = new String[6];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idventas, boleta, fecha, hora, preciototal, (SELECT nombre FROM clientes WHERE idclientes = fk_clientes)AS nombre, (SELECT apellido FROM clientes WHERE idclientes = fk_clientes)AS apellido FROM ventas WHERE boleta LIKE '%" + buscar + "%' ORDER BY idventas DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idventas");
                registros[1] = rs.getString("boleta");
                registros[2] = rs.getString("fecha");
                registros[3] = rs.getString("hora");
                registros[4] = rs.getString("preciototal");
                registros[5] = rs.getString("nombre") + " " + rs.getString("apellido");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

//**********************************INSERTAR*********************************
    //Insertar ventas
    public boolean insertarVentas(DatosVentas datos, int fk_clientes) {
        sSQL = "INSERT INTO ventas (boleta, fecha, hora, preciototal, fk_clientes) VALUES (?,?,?,?,?)";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getBoleta());
            pst.setString(2, datos.getFecha());
            pst.setString(3, datos.getHora());
            pst.setInt(4, datos.getPreciototal());
            pst.setInt(5, fk_clientes);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean insertarDetalleVentas(DatosDetalleVentas datos, int fk_productos, int fk_ventas) {
        sSQL = "INSERT INTO detalleventas (precio, cantidad, fk_productos, fk_ventas) VALUES(?,?,?,?)";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getPrecio());
            pst.setInt(2, datos.getCantidad());
            pst.setInt(3, fk_productos);
            pst.setInt(4, fk_ventas);

            int N = pst.executeUpdate();
            return N != 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosVentas datos) {
        sSQL = "DELETE FROM ventas WHERE idventas = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdventas());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminarDetalles(DatosDetalleVentas datos) {
        sSQL = "DELETE FROM detalleventas WHERE iddetalleventas = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIddetalleventas());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public int buscarClientes(String clientes) {
        sSQL = "SELECT idclientes FROM clientes WHERE clientes.cedula = '" + clientes + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idclientes");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    public int buscarProductos(String productos) {
        sSQL = "SELECT idproductos FROM productos WHERE productos.productos = '" + productos + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idproductos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    public int buscarVentas(String ventas) {
        sSQL = "SELECT idventas FROM ventas WHERE ventas.boleta = '" + ventas + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idventas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Funciones para ListaServicios
    public DefaultTableModel mostrarDetalleVentas(int buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Precio", "Cantidad", "Fk_ventas", "Fk_productos"};
        String[] registros = new String[5];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement ps = cn.prepareStatement("SELECT iddetalleventas, precio, cantidad, fk_ventas, "
                    + "fk_productos FROM detalleventas WHERE fk_ventas = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("iddetalleventas");
                registros[1] = rs.getString("precio");
                registros[2] = rs.getString("cantidad");
                registros[3] = rs.getString("fk_ventas");
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
        String[] titulos = {"Id", "Codigo", "Productos", "P. Minor", "P. Mayor.", "Cantidad", "Cant. x Mayor", "Iva", "Descuento", "Categorias"};
        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement ps = cn.prepareStatement("SELECT productos.idproductos, productos.codigo, productos.productos, productos.preciominorista, "
                    + "productos.preciomayorista, productos.cantidad, productos.cantidadxmayor, iva, descuentos.descuento,"
                    + "(SELECT categorias FROM categorias WHERE categorias.idcategorias = productos.fk_categorias) AS categorias FROM productos "
                    + "LEFT OUTER JOIN detalledescuentos ON productos.idproductos = detalledescuentos.fk_productos "
                    + "LEFT OUTER JOIN descuentos ON detalledescuentos.fk_descuentos = descuentos.iddescuentos "
                    + "WHERE idproductos = ? ORDER BY idproductos DESC");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("codigo");
                registros[2] = rs.getString("productos");
                registros[3] = rs.getString("preciominorista");
                registros[4] = rs.getString("preciomayorista");
                registros[5] = rs.getString("cantidad");
                registros[6] = rs.getString("cantidadxmayor");
                registros[7] = rs.getString("iva");
                registros[8] = rs.getString("descuento");
                registros[9] = rs.getString("categorias");

                if (registros[8] == null) {
                    registros[8] = "0";
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
}
