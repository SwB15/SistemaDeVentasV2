package Controller;

import Config.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FuncionesAuditoria {

    Date date = new Date();
    private final DataSource mysql = new DataSource();
    private Connection con = DataSource.getConnection();
    private String sSQL;
    Statement sent;
    PreparedStatement ps;
    ResultSet rs;
    String usua, acc, des, form, obj1, obj2;
    String ip, no;
    DateFormat hora, fecha;
    String fechaActual, horaActual;

    public FuncionesAuditoria() throws SQLException {
        con = DataSource.getConnection();
    }
    
//****************************************INICIO***********************************************
    public String ipe() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ip = localHost.getHostAddress();
        } catch (UnknownHostException e) {

        }
        return ip;
    }

    public String pc() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            no = localHost.getHostName();
        } catch (UnknownHostException e) {

        }
        return no;
    }

    public void fechaHora() {
        fecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = fecha.format(date);
        System.out.println(fechaActual);
        
        hora = new SimpleDateFormat("HH:mm:ss");
        horaActual = hora.format(date);
    }
//****************************************FIN***********************************************

//**************************************LLENADO DE AUDITORIAS*********************************************
    
    
    //****************************************CLIENTES***********************************************
    public void audiclientes(String usu, String obj, String accion, String descripcion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = descripcion;
            System.out.println("audi "+des);
            form = "CLIENTES";
            sSQL = "CREATE TRIGGER `T_CLIENTES` AFTER INSERT ON `clientes` FOR EACH ROW INSERT INTO auditoria (fecha, hora, db_user, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,CURRENT_USER,?,?,?,?,?,?)";
            ps = con.prepareStatement(sSQL);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }

    //****************************************PRODUCTOS***********************************************
    public void audiproductos(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "PRODUCTOS";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }

    //****************************************USUARIOS***********************************************
    public void audiusuarios(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "USUARIOS";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }

    //****************************************VENTAS***********************************************
    public void audiventas(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "VENTAS";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }
    
    //****************************************COMPRA***********************************************
    public void audicompras(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "COMPRAS";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }
    
    //****************************************BACKUP***********************************************
    public void audibackup(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "BACKUP";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }

    //****************************************CAJA***********************************************
    public void audicaja(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "CAJA";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }
    
    public void audiservicios(String usu, String obj, String accion) {
        fechaHora();
        try {
            usua = usu;
            obj1 = obj;
            acc = accion;
            des = obj1;
            form = "SERVICIOS";
            String guardar = "INSERT INTO auditoria (fecha, hora, usuario, accion, formulario, descripcion, pc, ip) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(guardar);
            ps.setString(1, fechaActual);
            ps.setString(2, horaActual);
            ps.setString(3, usua);
            ps.setString(4, acc);
            ps.setString(5, form);
            ps.setString(6, des);
            ps.setString(7, pc());
            ps.setString(8, ipe());
            int nn = ps.executeUpdate();
            if (nn > 0) {
                ps.close();
            }
        } catch (SQLException e) {
        }
    }
}
