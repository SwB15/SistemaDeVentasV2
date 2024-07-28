package Controller;

import Config.DataSource;
import Model.DatosDescuentos;
import Model.DatosDetalleDescuentos;
import View.Principal3;
import java.sql.Connection;
import java.sql.Date;
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
public class FuncionesDescuentos {

    private String sSQL = "";
    FuncionesNotificaciones notificaciones = new FuncionesNotificaciones();
    public Integer totalRegistros;
    int contador, i;
    DefaultTableModel modelo;
    String inicio, cierre, fecha1;
    ResultSet rs;
    PreparedStatement ps;
    Statement st;
    java.util.Date date, f1, f2, f3;
    Date fechainicio, fechacierre;

//**********************************MOSTRAR**********************************
    public DefaultTableModel mostrar(String buscar) {
        String[] titulos = {"Codigo", "Nombre", "Descuento", "Descripcion", "F. Inicio", "F. Cierre", "Cantidad", "Estado"};
        String[] registros = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT * FROM descuentos WHERE nombredescuentos LIKE '%" + buscar + "%' ORDER BY iddescuentos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("iddescuentos");
                registros[1] = rs.getString("nombredescuentos");
                registros[2] = rs.getString("descuento");
                registros[3] = rs.getString("descripcion");
                registros[4] = rs.getString("fechainicio");
                registros[5] = rs.getString("fechacierre");
                registros[6] = rs.getString("cantidad");
                registros[7] = rs.getString("estado");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar detalles de descuentos
    public DefaultTableModel mostrarDetalleDescuentos(String buscar) {
        String[] titulos = {"Codigo", "Fk_productos", "Fk_descuentos", "Fk_categorias", "Fk_clientes"};
        String[] registros = new String[5];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT iddetalledescuentos, (SELECT productos FROM productos WHERE idproductos = fk_productos) AS fk_productos, (SELECT nombredescuentos FROM descuentos WHERE iddescuentos = fk_descuentos) AS fk_descuentos, (SELECT categorias FROM categorias WHERE idcategorias = fk_categorias) AS fk_categorias, (SELECT nombre FROM clientes WHERE idclientes = fk_clientes) AS fk_clientes FROM detalledescuentos WHERE descuentos.nombredescuentos = '%" + buscar + "%' ORDER BY iddetalledescuentos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("iddetalledescuentos");
                registros[1] = rs.getString("fk_productos");
                registros[2] = rs.getString("fk_descuentos");
                registros[3] = rs.getString("fk_categorias");
                registros[4] = rs.getString("fk_clientes");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar detalles de descuentos
    public DefaultTableModel mostrarDetalle(int buscar) {
        String[] titulos = {"Codigo", "Productos", "Cod. Prod.", "Fk_descuentos", "Fk_categorias", "Cod. Cat.", "Fk_clientes", "Cedula"};
        String[] registros = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT iddetalledescuentos, (SELECT productos FROM productos WHERE idproductos = fk_productos) AS fk_productos, (SELECT codigo FROM productos WHERE idproductos = fk_productos) AS codigoproductos, (SELECT nombredescuentos FROM descuentos WHERE iddescuentos = fk_descuentos) AS fk_descuentos, (SELECT categorias FROM categorias WHERE idcategorias = fk_categorias) AS fk_categorias, (SELECT idcategorias FROM categorias WHERE idcategorias = fk_categorias) AS codigocategorias, (SELECT nombre FROM clientes WHERE idclientes = fk_clientes) AS fk_clientesnombre, (SELECT apellido FROM clientes WHERE idclientes = fk_clientes) AS fk_clientesapellido, (SELECT cedula FROM clientes WHERE idclientes = fk_clientes) AS cedulaclientes FROM detalledescuentos WHERE fk_descuentos LIKE '%" + buscar + "%' ORDER BY iddetalledescuentos DESC";
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("iddetalledescuentos");
                registros[1] = rs.getString("fk_productos");
                registros[2] = rs.getString("codigoproductos");
                registros[3] = rs.getString("fk_descuentos");
                registros[4] = rs.getString("fk_categorias");
                registros[5] = rs.getString("codigocategorias");
                registros[6] = rs.getString("fk_clientesnombre") + " " + rs.getString("fk_clientesapellido");
                registros[7] = rs.getString("cedulaclientes");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar Clientes para descuentos por cedula
    public DefaultTableModel mostrarClientesDescuentosCedula(String buscar) {
        String[] titulos = {"Codigo", "Cliente", "Cedula"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idclientes, nombre, apellido, cedula FROM clientes WHERE cedula LIKE '%" + buscar + "%' ORDER BY idclientes DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idclientes");
                registros[1] = rs.getString("nombre") + " " + rs.getString("apellido");
                registros[2] = rs.getString("cedula");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar Clientes para descuentos por Nombre
    public DefaultTableModel mostrarClientesDescuentosNombre(String buscar) {
        String[] titulos = {"Codigo", "Cliente", "Cedula"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idclientes, nombre, apellido, cedula FROM clientes WHERE nombre LIKE '%" + buscar + "%' ORDER BY idclientes DESC";

        try(Connection cn = DataSource.getConnection()) {
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idclientes");
                registros[1] = rs.getString("nombre") + " " + rs.getString("apellido");
                registros[2] = rs.getString("cedula");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar Categorias para descuentos por codigo
    public DefaultTableModel mostrarCategoriasDescuentosCodigo(String codigo) {
        String[] titulos = {"Codigo", "Categoria"};
        String[] registros = new String[2];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idcategorias, categorias FROM categorias WHERE idcategorias LIKE '%" + codigo + "%' ORDER BY idcategorias DESC";

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

    //Mostrar Categorias para descuentos por nombre
    public DefaultTableModel mostrarCategoriasDescuentosNombre(String nombre) {
        String[] titulos = {"Codigo", "Categoria"};
        String[] registros = new String[2];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idcategorias, categorias FROM categorias WHERE categorias LIKE '%" + nombre + "%' ORDER BY idcategorias DESC";

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

    //Mostrar Productos para descuentos por codigo
    public DefaultTableModel mostrarProductosDescuentosCodigo(String codigo) {
        String[] titulos = {"ID", "Codigo", "Producto"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproductos, productos, codigo FROM productos WHERE codigo LIKE '%" + codigo + "%' ORDER BY idproductos DESC";
        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("productos");
                registros[2] = rs.getString("codigo");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar Productos para descuentos por nombre
    public DefaultTableModel mostrarProductosDescuentosNombre(String nombre) {
        String[] titulos = {"ID", "Codigo", "Producto"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproductos, productos, codigo FROM productos WHERE productos LIKE '%" + nombre + "%' ORDER BY idproductos DESC";
        try(Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("productos");
                registros[2] = rs.getString("codigo");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Mostrar Descuentos activos
    public DefaultTableModel mostrarDescuentosVencidos() {
        String[] titulos = {"Codigo", "Nombre", "F. Inicio", "F. Cierre"};
        String[] registros = new String[4];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT iddescuentos, nombredescuentos, fechainicio, fechacierre FROM descuentos "
                + "WHERE estado = 'Activo' AND  fechacierre < CURDATE() ORDER BY iddescuentos DESC";

        try (Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("iddescuentos");
                registros[1] = rs.getString("nombredescuentos");
                registros[2] = rs.getString("fechainicio");
                registros[3] = rs.getString("fechacierre");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    //Envia los descuentos vencidos a una tabla en la pantalla principal y a la tabla de notificaciones
    public void mostrarFechas(String buscar) {
        //Llenar la tabla Cantidades
        DefaultTableModel modelo2;
        modelo2 = mostrarDescuentosVencidos();
        Principal3.tblFechaDescuentos.setModel(modelo2);
    }

    public void descuentosVencidos() {
        if (Principal3.tblFechaDescuentos.getRowCount() > 0) {
            for (i = 0; i < Principal3.tblFechaDescuentos.getRowCount(); i++) {
                notificaciones.agregar(String.valueOf(Principal3.tblFechaDescuentos.getValueAt(0, 1)), "Ha finalizado su descuento");
            }
            notificaciones.cont();
        }
    }
//**********************************FIN MOSTRAR**********************************

//**********************************INSERTAR**********************************
    //Insertar Descuentos
    public boolean insertar(DatosDescuentos datos) {
        sSQL = "INSERT INTO descuentos(nombredescuentos, descuento, descripcion, fechainicio, fechacierre, cantidad, estado) VALUES (?,?,?,?,?,?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getNombredescuentos());
            pst.setInt(2, datos.getDescuentos());
            pst.setString(3, datos.getDescripcion());
            pst.setDate(4, (Date) datos.getFechainicio());
            pst.setDate(5, (Date) datos.getFechacierre());
            pst.setInt(6, datos.getCantidad());
            pst.setString(7, datos.getEstado());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Insertar Detalles Descuentos con unicamente la clave foranea de productos
    public boolean insertarDetalleConProductos(int fk_productos, int fk_descuentos) {
        sSQL = "INSERT INTO detalledescuentos(fk_productos, fk_descuentos) VALUES (?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_productos);
            pst.setInt(2, fk_descuentos);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Insertar Detalles Descuentos con unicamente la clave foranea de categorias
    public boolean insertarDetalleConCategorias(int fk_descuentos, int fk_categorias) {
        sSQL = "INSERT INTO detalledescuentos(fk_descuentos, fk_categorias) VALUES (?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_descuentos);
            pst.setInt(2, fk_categorias);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Insertar Detalles Descuentos con unicamente la clave foranea de clientes
    public boolean insertarDetalleConClientes(int fk_descuentos, int fk_clientes) {
        sSQL = "INSERT INTO detalledescuentos(fk_descuentos, fk_clientes) VALUES (?,?)";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_descuentos);
            pst.setInt(2, fk_clientes);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
//**********************************FIN INSERTAR**********************************

//**********************************EDITAR**********************************
    //Editar Descuentos
    public boolean editar(DatosDescuentos datos) {
        sSQL = "UPDATE descuentos SET nombredescuentos = ?, descuento = ?, descripcion = ?, fechainicio = ?, fechacierre = ?, cantidad = ?, estado = ?WHERE iddescuentos = ?";

        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getNombredescuentos());
            pst.setInt(2, datos.getDescuentos());
            pst.setString(3, datos.getDescripcion());
            pst.setDate(4, (Date) datos.getFechainicio());
            pst.setDate(5, (Date) datos.getFechacierre());
            pst.setInt(6, datos.getCantidad());
            pst.setString(7, datos.getEstado());
            pst.setInt(8, datos.getIddescuentos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Editar Detalle Descuentos Con Productos
    public boolean editarDetalleConProductos(DatosDetalleDescuentos datosdetalle, String fk_descuentos, String fk_productos) {
        sSQL = "UPDATE detalledescuentos SET fk_descuentos = (SELECT iddescuentos FROM descuentos WHERE nombredescuentos LIKE '%" + fk_descuentos + "%' limit 1), fk_productos = (SELECT idproductos FROM productos WHERE productos LIKE '%" + fk_productos + "%' limit 1) WHERE iddetalledescuentos = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datosdetalle.getIddetalledescuentos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Editar Detalle Descuentos con Categorias
    public boolean editarDetalleConCategorias(DatosDetalleDescuentos datosdetalle, int fk_descuentos, int fk_categorias) {
        sSQL = "UPDATE detalledescuentos SET fk_descuentos = (SELECT iddescuentos FROM descuentos WHERE iddescuentos LIKE '%" + fk_descuentos + "%' limit 1), fk_categorias = (SELECT idcategorias FROM categorias WHERE idcategorias LIKE '%" + fk_categorias + "%' limit 1) WHERE iddetalledescuentos = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datosdetalle.getIddetalledescuentos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Editar Detalle Descuentos con Clientes
    public boolean editarDetalleConClientes(DatosDetalleDescuentos datosdetalle, String fk_descuentos, String fk_clientes) {
        sSQL = "UPDATE detalledescuentos SET fk_descuentos = (SELECT iddescuentos FROM descuentos WHERE nombredescuentos LIKE '%" + fk_descuentos + "%' limit 1), fk_clientes = (SELECT idclientes FROM clientes WHERE cedula LIKE '%" + fk_clientes + "%' limit 1) WHERE iddetalledescuentos = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datosdetalle.getIddetalledescuentos());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
//**********************************FIN EDITAR**********************************

//**********************************ELIMINAR**********************************
    //Eliminar Descuentos
    public boolean eliminar(DatosDescuentos datos) {
        sSQL = "DELETE FROM descuentos WHERE iddescuentos = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getIddescuentos());
            int N = pst.executeUpdate();

            return N != 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    //Eliminar Detalle Descuentos
    public boolean eliminarDetalle(DatosDetalleDescuentos datosdetalle) {
        sSQL = "DELETE FROM detalledescuentos WHERE iddetalledescuentos = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datosdetalle.getIddetalledescuentos());
            int N = pst.executeUpdate();

            return N != 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
//**********************************FIN ELIMINAR**********************************

//*******************BUSCAR ID DE TABLAS A TRAVES DE LAS CLAVES FORANEAS*******************
    //Buscar descuentos
    public int buscarDescuento(String descuentos) {
        sSQL = "SELECT iddescuentos FROM descuentos WHERE descuentos.nombredescuentos = '" + descuentos + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
            st = cn.createStatement();
            rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("iddescuentos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Buscar productos
    public int buscarProductos(String productos) {
        sSQL = "SELECT idproductos FROM productos WHERE productos.codigo = '" + productos + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
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

    //Buscar categorias
    public int buscarCategorias(String categorias) {
        sSQL = "SELECT idcategorias FROM categorias WHERE categorias.categorias = '" + categorias + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
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

    public int buscarClientes(String clientes) {
        sSQL = "SELECT idclientes FROM clientes WHERE clientes.cedula = '" + clientes + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
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

    //Obtiene el id maximo de la tabla descuentos
    public int conteo() {
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            ps = cn.prepareStatement("SELECT MAX(iddescuentos) AS iddescuentos FROM descuentos");
            rs = ps.executeQuery();

            while (rs.next()) {
                codigo = rs.getInt("iddescuentos");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return codigo + 1;
    }

    //Obtiene el id maximo de la tabla detalledescuentos
    public int conteoDetalles() {
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            ps = cn.prepareStatement("SELECT MAX(iddetalledescuentos) AS iddetalledescuentos FROM detalledescuentos");
            rs = ps.executeQuery();

            while (rs.next()) {
                codigo = rs.getInt("iddetalledescuentos");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return codigo + 1;
    }
}
