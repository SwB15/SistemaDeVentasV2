package Controller;

import Config.DataSource;
import Model.DatosCaja;
import Model.DatosDetalleCaja;
import View.Caja.AperturaCaja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SwichBlade15
 */
public class FuncionesCaja {

    private String sSQL = "";
    String registros;
    int idcaja;
    int iddetallecaja;

//*****************************FUNCIONES REQUERIDAS AL INICIO DEL SISTEMA*****************************
    //Estas funciones son llamadas al iniciar sesion para validar el estado de la caja
    //Obtener datos de "caja" para evitar abrir una nueva caja al iniciar sesión si la anterior caja no ha sido cerrada
    //Si el sistema se cierra accidentalmente sin realizar un cierre de caja, el sistema se reinicia con la ultima caja sin cerrar
    public String estadoCaja() {
        try (Connection cn = DataSource.getConnection()){
            sSQL = "SELECT fechacierre FROM caja WHERE idcaja = (SELECT MAX(idcaja) FROM caja)";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                registros = rs.getString("fechacierre");
            }
            return registros;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Obtener datos de "caja" para conocer el ID de la caja abierta actualmente
    public int idCaja() {
        sSQL = "SELECT idcaja FROM caja WHERE idcaja = (SELECT MAX(idcaja) FROM caja)";
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                idcaja = rs.getInt("idcaja");
            }
            return idcaja;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    //Obtener datos de "detallecaja" para conocer el ID de los detalles de la caja abierta actualmente
    public int idDetalleCaja() {
        sSQL = "SELECT iddetallecaja FROM detallecaja WHERE iddetallecaja = (SELECT MAX(iddetallecaja) FROM detallecaja)";
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                iddetallecaja = rs.getInt("iddetallecaja");
            }
            return iddetallecaja;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

//*****************************INSERTAR*****************************
    //Funciones para insertar datos en la BD
    //Insertar datos en "caja" cuando se realiza una apertura de caja
    public boolean insertar(DatosCaja datos, int fk_usuario) {
        sSQL = "INSERT INTO caja (fechaapertura, horaapertura, saldoapertura, saldocierre, fechacierre, horacierre, fk_usuarios) VALUES (?,?,?,?,?,?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, datos.getFechapertura());
            pst.setString(2, datos.getHoraapertura());
            pst.setInt(3, datos.getSaldoapertura());
            pst.setInt(4, datos.getSaldocierre());
            pst.setString(5, datos.getFechacierre());
            pst.setString(6, datos.getHoracierre());
            pst.setInt(7, fk_usuario);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Insertar datos en "detallecaja" cuando se realiza una venta
    public boolean insertarDetalleCajaVentas(int fk_ventas, int fk_caja) {
        sSQL = "INSERT INTO detallecaja (fk_ventas, fk_caja) VALUES(?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_ventas);
            pst.setInt(2, fk_caja);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Insertar datos en "detallecaja" cuando se realiza un servicio
    public boolean insertarDetalleCajaServicios(int fk_servicios, int fk_caja) {
        sSQL = "INSERT INTO detallecaja (fk_servicios, fk_caja) VALUES(?,?)";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_servicios);
            pst.setInt(2, fk_caja);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Insertar datos en "detallecaja" cuando se realiza una compra
    public boolean insertarDetalleCajaCompras(int fk_compras, int fk_caja) {
        sSQL = "INSERT INTO detallecaja (fk_compras, fk_caja) VALUES(?,?)";
        try(Connection cn = DataSource.getConnection()) {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, fk_compras);
            pst.setInt(2, fk_caja);

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//*****************************FIN INSERTAR*****************************

//*****************************EDITAR*****************************
    //Editar los datos de "caja" cuando se realiza una operacion
    //Editar datos en la columna "ingresodia" cuando se realiza una venta o un servicio
    public boolean editarIngresoDia(DatosCaja datos) {
        sSQL = "UPDATE caja SET ingresodia = ? WHERE idcaja = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIngresodia());
            pst.setInt(2, datos.getIdcaja());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Editar datos en la columna "egresodia" cuando se realiza una compra
    public boolean editarEgresoDia(DatosCaja datos) {
        sSQL = "UPDATE caja SET egresodia = ? WHERE idcaja = ?";
        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getEgresodia());
            pst.setInt(2, datos.getIdcaja());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException ex) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//*****************************FIN EDITAR*****************************

    
//*****************************ELIMINAR*****************************
    public boolean eliminarDetalles(DatosDetalleCaja datos) {
        sSQL = "DELETE FROM detallecaja WHERE iddetallecaja = ? ";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getIddetallecaja());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
//*****************************FIN ELIMINAR*****************************    

    
//*****************************CERRAR CAJA*****************************
    //Actualizar datos de "caja" cuando se realiza un cierre de caja
    public boolean cerrarCaja(DatosCaja datos) {
        sSQL = "UPDATE caja SET saldocierre = ?, fechacierre = ?, horacierre = ? WHERE idcaja = ?";

        try (Connection cn = DataSource.getConnection()){
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, datos.getSaldocierre());
            pst.setString(2, datos.getFechacierre());
            pst.setString(3, datos.getHoracierre());
            pst.setInt(4, datos.getIdcaja());

            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            Logger.getLogger(AperturaCaja.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
//*****************************FIN CERRAR CAJA*****************************

//*****************************BUSCAR*****************************
    //Busca los id para asociarlos a las claves foraneas
    //Obtener ID de "usuarios"
    public int buscarUsuario(String usuario) {
        sSQL = "SELECT idusuarios FROM usuarios WHERE usuarios.usuario = '" + usuario + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idusuarios");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Obtener ID de "caja"
    public int buscarCaja(int caja) {
        sSQL = "SELECT idcaja FROM caja WHERE caja.idcaja = '" + caja + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idcaja");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Obtener ID de "detallecaja"
    public int buscarDetalleCaja(int detallecaja) {
        sSQL = "SELECT iddetallecaja FROM detallecaja WHERE detallecajacaja.iddetallecaja = '" + detallecaja + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("iddetallecaja");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Obtener ID de "compras"
    public int buscarCompras(int compras) {
        sSQL = "SELECT idcompras FROM compras WHERE compras.idcompras = '" + compras + "'";
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

    //Obtener ID de "ventas"
    public int buscarVentas(int ventas) {
        sSQL = "SELECT idventas FROM ventas WHERE ventas.idventas = '" + ventas + "'";
        int codigo = 0;
        try(Connection cn = DataSource.getConnection()) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idventas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }

    //Obtener ID de "servicios"
    public int buscarServicios(int servicios) {
        sSQL = "SELECT idservicios FROM fichaservicios WHERE fichaservicios.idservicios = '" + servicios + "'";
        int codigo = 0;
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                codigo = rs.getInt("idservicios");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return codigo;
    }
}
