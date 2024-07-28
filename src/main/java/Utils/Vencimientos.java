package Utils;

import Config.DataSource;
import Vista.Notificaciones.Notificaciones;
import View.Principal3;
import static View.Principal3.lblContadorNotificaciones;
import static View.Principal3.lblNotificaciones;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Vencimientos {

    private String sSQL = "";
    int contador, i;
    public static long diasVencido;
    DateFormat fecha;
    static Date date, f1, f2;
    String fecha1, fechaVencimiento;
    public Integer totalRegistros;
    public static JTable tblVencimientos = new JTable();

    public void fecha() {
        fecha = new SimpleDateFormat("dd/MM/yyyy");
        fecha1 = fecha.format(date);
    }

    private DefaultTableModel mostrar() {
        DefaultTableModel modelo;
        String[] titulos = {"Codigo", "Productos", "Vencimiento"};
        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT idproductos, productos, fechavencimiento FROM productos WHERE cantidad > 0 ORDER BY idproductos DESC";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registros[0] = rs.getString("idproductos");
                registros[1] = rs.getString("productos");
                registros[2] = rs.getString("fechavencimiento");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }

    public void ocultar_columnas() {
        tblVencimientos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblVencimientos.getColumnModel().getColumn(0).setMinWidth(0);
        tblVencimientos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void vencimientos() {
        int cantidadNotificaciones = 0;
        mostrar("");
        contador = 0;
        if (tblVencimientos.getRowCount() > 0) {
            for (i = 0; i < tblVencimientos.getRowCount(); i++) {
                try {
                    //Convertir java.sql.date a java.util.date y mostrar en pantalla la fecha de Vencimiento
                    SimpleDateFormat vencimiento = new SimpleDateFormat("yyyy-MM-dd");
                    //Formato inicial. 
                    try {
                        fechaVencimiento = String.valueOf(tblVencimientos.getValueAt(0, 2));
                        date = vencimiento.parse(fechaVencimiento);
                    } catch (ParseException e) {

                    }

                    //Aplica formato requerido.
                    try {
                        vencimiento.applyPattern("dd/MM/yyyy");
                        String nuevoFormato = vencimiento.format(date);
                        f1 = vencimiento.parse(nuevoFormato);
                    } catch (ParseException ex) {

                    }
                    //Fin Vencimiento

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    f2 = formato.parse(fecha1);
                    if (f2.after(f1) || f2.equals(f1)) {
                        System.out.println("El producto ha vencido");
                        getDayCount();
//                        Notificaciones.btnVencimientosOculto.doClick();
                    }
                } catch (ParseException ex) {

                }
            }
        }

        if (cantidadNotificaciones < 100) {
            Principal3.lblContadorNotificaciones.setText(String.valueOf(contador));
        } else {
            Principal3.lblContadorNotificaciones.setText(String.valueOf("+99"));
        }
    }

    public static void getDayCount() {
        long diff = -1;
        try {
            //time is always 00:00:00, so rounding should help to ignore the missing hour when going from winter to summer time, as well as the extra hour in the other direction
            diff = Math.round((f1.getTime() - f2.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        diasVencido = diff;
    }

    public void mostrar(String buscar) {
        //Llenar la tabla Cantidades
        DefaultTableModel modelo2;
        modelo2 = mostrar();
        tblVencimientos.setModel(modelo2);
        ocultar_columnas();
    }

    public void cont() {
        if (lblContadorNotificaciones.getText().equals("0")) {
            lblNotificaciones.setVisible(false);
            lblContadorNotificaciones.setVisible(false);
        } else {
            lblNotificaciones.setVisible(true);
            lblContadorNotificaciones.setVisible(true);
        }
    }
}
