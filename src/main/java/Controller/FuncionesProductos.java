package Controller;

import Config.DataSource;
import Model.DatosProductos;
import java.sql.Connection;
import java.sql.Date;
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
public class FuncionesProductos {

    private String sSQL = "";
    public Integer totalRegistros;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Productos", "Tipo Codigo", "Codigo", "Cantidad", "Cantidad Minima", "Cant. x Mayor", "P. Minorista", "P. Mayorista", "Iva", "F. Venc.", "U. Medida", "Categorias", "Proveedores"};
        String[] registros = new String[14];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproductos, productos, tipocodigo, codigo, cantidad, cantidadminima, cantidadxmayor, preciominorista, preciomayorista, iva, fechavencimiento, unidadmedida, (SELECT categorias FROM categorias WHERE idcategorias = fk_categorias) AS categorias, (SELECT proveedores FROM proveedores WHERE idproveedores = fk_proveedores) AS proveedores FROM productos WHERE productos LIKE '%" + buscar + "%' ORDER BY idproductos DESC";

        try(Connection cn = DataSource.getConnection()) {
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("productos");
                registros[2] = rs.getString("tipocodigo");
                registros[3] = rs.getString("codigo");
                registros[4] = rs.getString("cantidad");
                registros[5] = rs.getString("cantidadminima");
                registros[6] = rs.getString("cantidadxmayor");
                registros[7] = rs.getString("preciominorista");
                registros[8] = rs.getString("preciomayorista");
                registros[9] = rs.getString("iva");
                registros[10] = rs.getString("fechavencimiento");
                registros[11] = rs.getString("unidadmedida");
                registros[12] = rs.getString("categorias");
                registros[13] = rs.getString("proveedores");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Tabla para la seleccion de productos en Venta con sus descuentos
    public DefaultTableModel seleccionarProductosVentas(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Codigo", "Productos", "P. Minor", "P. Mayor.", "Cantidad", "Cant. x Mayor", "Iva", "Descuento", "Categorias"};
        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT productos.idproductos, productos.codigo, productos.productos, productos.preciominorista, "
                + "productos.preciomayorista, productos.cantidad, productos.cantidadxmayor, iva, descuentos.descuento,"
                + "(SELECT categorias FROM categorias WHERE categorias.idcategorias = productos.fk_categorias) AS categorias FROM productos "
                + "LEFT OUTER JOIN detalledescuentos ON productos.idproductos = detalledescuentos.fk_productos "
                + "LEFT OUTER JOIN descuentos ON detalledescuentos.fk_descuentos = descuentos.iddescuentos "
                + "WHERE productos LIKE '%" + buscar + "%' ORDER BY idproductos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

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

    //Selecciona los productos mediante el codigo del producto
    public DefaultTableModel seleccionarProductosPorCodigo(String codigo) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Codigo", "Productos", "P. Minor", "P. Mayor.", "Cantidad", "Cant. x Mayor", "Iva", "Descuento", "Categorias"};
        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT productos.idproductos, productos.codigo, productos.productos, productos.preciominorista, "
                + "productos.preciomayorista, productos.cantidad, productos.cantidadxmayor, iva, descuentos.descuento,"
                + "(SELECT categorias FROM categorias WHERE categorias.idcategorias = productos.fk_categorias) AS categorias FROM productos "
                + "LEFT OUTER JOIN detalledescuentos ON productos.idproductos = detalledescuentos.fk_productos "
                + "LEFT OUTER JOIN descuentos ON detalledescuentos.fk_descuentos = descuentos.iddescuentos "
                + "WHERE codigo LIKE '%" + codigo + "%' ORDER BY idproductos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

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

    public DefaultTableModel seleccionarProductosCompra(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Codigo", "Productos", "Precio", "Cantidad", "Proveedor"};
        String[] registros = new String[6];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproductos, codigo, productos, precio, cantidad, (SELECT proveedores FROM proveedores WHERE idproveedores = fk_proveedores) AS proveedores FROM productos WHERE fk_proveedores LIKE '%" + buscar + "%' ORDER BY idproductos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("codigo");
                registros[2] = rs.getString("productos");
                registros[3] = rs.getString("precio");
                registros[4] = rs.getString("cantidad");
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

    public boolean insertar(DatosProductos datos, int cat, int prov) {
        sSQL = "INSERT INTO productos (productos, tipocodigo, codigo, cantidad, cantidadminima, cantidadxmayor, preciominorista, preciomayorista, iva, fechavencimiento, unidadmedida, fk_categorias, fk_proveedores) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getProductos());
            pst.setString(2, datos.getTipocodigo());
            pst.setString(3, datos.getCodigo());
            pst.setInt(4, datos.getCantidad());
            pst.setInt(5, datos.getCantidadminima());
            pst.setInt(6, datos.getCantidadxmayor());
            pst.setInt(7, datos.getPreciominorista());
            pst.setInt(8, datos.getPreciomayorista());
            pst.setInt(9, datos.getIva());
            pst.setDate(10, (Date) datos.getFechavencimiento());
            pst.setString(11, datos.getUnidadmedida());
            pst.setInt(12, cat);
            pst.setInt(13, prov);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean editar(DatosProductos datos, String cat, String prov) {
        sSQL = "UPDATE productos SET productos = ?, tipocodigo = ?, codigo = ?, cantidad = ?, cantidadminima = ?, cantidadxmayor = ?, preciominorista = ?, preciomayorista = ?, iva = ?, fechavencimiento = ?, unidadmedida = ?, fk_categorias = (SELECT idcategorias FROM categorias WHERE categorias LIKE '%" + cat + "%' limit 1), fk_proveedores = (SELECT idproveedores FROM proveedores WHERE proveedores LIKE '%" + prov + "%' limit 1) WHERE idproductos = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getProductos());
            pst.setString(2, datos.getTipocodigo());
            pst.setString(3, datos.getCodigo());
            pst.setInt(4, datos.getCantidad());
            pst.setInt(5, datos.getCantidadminima());
            pst.setInt(6, datos.getCantidadxmayor());
            pst.setInt(7, datos.getPreciominorista());
            pst.setInt(8, datos.getPreciomayorista());
            pst.setInt(9, datos.getIva());
            pst.setDate(10, (Date) datos.getFechavencimiento());
            pst.setString(11, datos.getUnidadmedida());
            pst.setInt(12, datos.getIdproductos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean eliminar(DatosProductos datos) {
        sSQL = "DELETE FROM productos WHERE idproductos = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIdproductos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public ArrayList<String> llenar_comboCat() {
        ArrayList<String> listaCat = new ArrayList<String>();
        sSQL = "SELECT categorias FROM categorias";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                listaCat.add(rs.getString("categorias"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listaCat;
    }

    public ArrayList<String> llenar_comboProv() {
        ArrayList<String> listaProv = new ArrayList<String>();
        sSQL = "SELECT proveedores FROM proveedores";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                listaProv.add(rs.getString("proveedores"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listaProv;
    }

    public int buscarCategoria(String categoria) {
        sSQL = "SELECT idcategorias FROM categorias WHERE categorias.categorias = '" + categoria + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idcategorias");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    public int buscarProveedor(String proveedor) {
        sSQL = "SELECT idproveedores FROM proveedores WHERE proveedores.proveedores = '" + proveedor + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idproveedores");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
}
