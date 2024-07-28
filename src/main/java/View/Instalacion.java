package View;

import Model.DatosCiudades;
import Model.DatosDepartamentos;
import Model.DatosUsuarios;
import Controller.FuncionesCiudades;
import Controller.FuncionesClientes;
import Controller.FuncionesDepartamentos;
import Controller.FuncionesUsuarios;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public final class Instalacion extends javax.swing.JFrame {

    DatosCiudades dciu = new DatosCiudades();
    DatosDepartamentos ddep = new DatosDepartamentos();
    FuncionesUsuarios funcion = new FuncionesUsuarios();
    FuncionesCiudades ciu = new FuncionesCiudades();
    FuncionesDepartamentos dep = new FuncionesDepartamentos();
    
    String password;
    String hashedpassword;

    public Instalacion() {
        initComponents();
        this.setBackground(new Color(0, 0, 0, 0));
        cmbDepartamentos.setVisible(false);
        cmbCiudades.setVisible(false);
        btnAceptar.setBorder(null);
        btnAceptar.setBackground(java.awt.Color.white);
        mostrar("");

        int cantidadDepartamentos = funcion.ContarDepartamentos();
        int cantidadCiudades = funcion.ContarCiudades();

        int categoria = cmbDepartamentos.getSelectedIndex();
        String categoria2 = ((String) cmbDepartamentos.getItemAt(categoria));

        if (cantidadDepartamentos == 0) {
            dep.insertarPrimerDepartamento(ddep);
            mostrar("");
            if (cantidadCiudades == 0) {
                mostrar("");
                ciu.insertarPrimeraCiudad(dciu, ciu.buscarDepartamento(categoria2));
            }
            mostrar("");
        } else {
            mostrar("");
            if (cantidadCiudades == 0) {
                mostrar("");
                ciu.insertarPrimeraCiudad(dciu, ciu.buscarDepartamento(categoria2));
            }
            mostrar("");
        }
        
        botonesTransparentes();
    }

    public void mostrar(String buscar) {
        try {
            cmbDepartamentos.removeAllItems();
            ArrayList<String> lista = new ArrayList<String>();
            lista = ciu.llenar_comboDep();

            for (int i = 0; i < lista.size(); i++) {
                cmbDepartamentos.addItem(lista.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void botonesTransparentes() {
        btnSalir.setOpaque(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        cmbCiudades = new javax.swing.JComboBox<>();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar30.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 30, 30));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonAceptar.png"))); // NOI18N
        btnAceptar.setToolTipText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 120, 32));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setText("Se ha asignado un Usuario provisorio para el primer Inicio de Sesión");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 574, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 255));
        jLabel3.setText("Es indispensable que cambie por un Usuario y Contraseña personalizados.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 574, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 255));
        jLabel4.setText("Usuario: admin");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 255));
        jLabel5.setText("Contraseña: admin");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        jLabel19.setBackground(new java.awt.Color(255, 87, 34));
        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("No se detecto ningún Usuario en el Sistema!!!");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 614, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setText("Tambien se creo el Cliente General de ventas");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 574, -1));

        cmbDepartamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentosActionPerformed(evt);
            }
        });
        getContentPane().add(cmbDepartamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        cmbCiudades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbCiudades, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoInstalacion.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        DatosUsuarios datos = new DatosUsuarios();
        FuncionesUsuarios funcion = new FuncionesUsuarios();
        FuncionesCiudades ciu = new FuncionesCiudades();
        FuncionesDepartamentos dep = new FuncionesDepartamentos();
        password = "ADMIN";
//        hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt(15));
        hashedpassword = "";
        
        datos.setNombre("Nombre");
        datos.setApellido("Apellido");
        datos.setCedula("0000000");
        datos.setDireccion("Direccion");
        datos.setTelefono("0000000000");
        datos.setEmail("example@mail.com");
        datos.setUsuarios("ADMIN");

        //Se ingresa la contraseña encriptada a la BD
        datos.setContrasena(hashedpassword);

        //Se seleccionan los permisos de Administrador
        datos.setPermisos("Administrador");

        int ciudad = cmbDepartamentos.getSelectedIndex();
        String ciudad2 = ((String) cmbDepartamentos.getItemAt(ciudad));

        if (funcion.insertar(datos, funcion.buscarCiudad(ciudad2))) {
            FuncionesClientes funcion2 = new FuncionesClientes();
            int CantidadClientes = funcion2.PrimerCliente();

            if (CantidadClientes == 0) {
                funcion2.insertarPrimerCliente();
                this.dispose();
            }
            this.dispose();
            Login form = new Login();
            form.setResizable(false);
            form.toFront();
            form.setVisible(true);
            form.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "NO se ingresador Datos");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cmbDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartamentosActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.gc();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Instalacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Instalacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Instalacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Instalacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Instalacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbCiudades;
    private javax.swing.JComboBox<String> cmbDepartamentos;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblFondo;
    // End of variables declaration//GEN-END:variables
}
