package Utils;

import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SwichBlade15
 */
public class Control extends javax.swing.JFrame {

    private File archivo = new File("archivo.tmp");
    private int segundos = 1;

    public Control() {
    }

    public boolean comprobar() {
        if (archivo.exists()) {
            long tiempo = leer();
            long res = restarTiempo(tiempo);
            if (res < segundos) {
                mensaje = "El programa ya está en ejecucion";
                fallo();
                return false;
            } else {
                programa_tarea();
                return true;
            }
        } else {
            crearTMP();
            programa_tarea();
            return true;
        }
    }

    private long leer() {
        String linea = "0";
        BufferedReader fr;

        try {
            fr = new BufferedReader(new FileReader(archivo));
            while (fr.ready()) {
                linea = fr.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Long.valueOf(linea);
    }

    private long restarTiempo(long tiempoActual) {
        Date date = new Date();
        long tiempoTMP = date.getTime();
        long tiempo = tiempoTMP - tiempoActual;
        tiempo = tiempo / 1000;
        return tiempo;
    }

    private void programa_tarea() {
        ScheduledExecutorService sc = Executors.newSingleThreadScheduledExecutor();
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                crearTMP();
            }
        }, 1000, segundos * 1000, TimeUnit.MILLISECONDS);
    }

    private void crearTMP() {
        Date fecha = new Date();

        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(archivo));
            wr.write(String.valueOf(fecha.getTime()));
            wr.close();
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarApp() {
        if (archivo.exists()) {
            archivo.delete();
        }
        System.exit(0);
    }

    //Metodos para llamar a los JDialog de Advertencia, Fallo y Realizado
    Frame f = JOptionPane.getFrameForComponent(this);
    String mensaje;

    public void advertencia() {
        Advertencia dialog = new Advertencia(f, true);
        Advertencia.lblEncabezado.setText(mensaje);
        dialog.setVisible(true);
    }

    public void fallo() {
        Fallo dialog = new Fallo(f, true);
        Fallo.lblEncabezado.setText(mensaje);
        dialog.setVisible(true);
    }

    public void realizado() {
        Realizado dialog = new Realizado(f, true);
        Realizado.lblEncabezado.setText(mensaje);
        dialog.setVisible(true);
    }
}
