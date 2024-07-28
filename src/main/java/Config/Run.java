package Config;

import Controller.State_Controller;
import View.Principal;
import com.formdev.flatlaf.FlatLightLaf;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Run {

    public static DefaultTableModel model;

    public static void main(String[] args) {
        try {
            model = State_Controller.states();

            if (model != null) {
                System.out.println("Estados cargados:");
                for (int i = 0; i < model.getRowCount(); i++) {
                    System.out.println("ID: " + model.getValueAt(i, 0) + ", Estado: " + model.getValueAt(i, 1));
                }
            } else {
                System.out.println("Error al cargar los estados.");
            }

//        if (new Control().comprobar()) {
//            FuncionesUsuarios funcion = new FuncionesUsuarios();
//            int cantidadUsuarios = funcion.ContarUsuarios();
//
//            if (cantidadUsuarios == 0) {
//                Instalacion form = new Instalacion();
//                form.setResizable(false);
//                form.toFront();
//                form.setVisible(true);
//                form.setLocationRelativeTo(null);
//            } else {
            UIManager.setLookAndFeel(new FlatLightLaf());
            Principal form = new Principal();
            form.setVisible(true);
//            }
//        } else {
//            System.exit(0);
//        }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
