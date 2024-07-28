package View;

import Config.DataSource;
import Model.DatosUsuarios;
import Fuentes.Fuentes;
import Controller.FuncionesUsuarios;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Login extends javax.swing.JFrame {

    FuncionesUsuarios func = new FuncionesUsuarios();
    DatosUsuarios dts = new DatosUsuarios();
    private String sSQL = "";
    public Integer totalRegistros;
    Fuentes tipofuentes;
    public static String contrasena = null;
    int x, y;
    boolean tecla = false;
    boolean logueado = false;
    String password;
    String hashedpassword;
    String usuario;
    String depassword;
    int cont;
    boolean hash = false;
    DefaultTableModel modelo;
    Icon icono;

    public Login() {
        initComponents();
        tipofuentes = new Fuentes();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        btnIngresar.setBorder(null);
        btnIngresar.setBackground(java.awt.Color.white);
        lblPermisos.setVisible(false);
        jScrollPane1.setVisible(false);
        btnSalir.setBorder(null);
        btnSalir.setBackground(java.awt.Color.white);
        lblUsuario.setFont(tipofuentes.fuente(tipofuentes.decker, 0, 18));
        lblContrasena.setFont(tipofuentes.fuente(tipofuentes.decker, 0, 18));
        txtUsuario.setFont(tipofuentes.fuente(tipofuentes.decker, 0, 18));
        txtContrasena.setFont(tipofuentes.fuente(tipofuentes.decker, 0, 18));
        btnIngresar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                btnIngresarActionPerformed(null);
            }
        });
        mostrar("");
    }

    public void mostrar(String buscar) {
        try {
            FuncionesUsuarios funcion = new FuncionesUsuarios();
            modelo = funcion.mostrar(buscar);
            tablalistado.setModel(modelo);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ocultar_columnas() {
        tablalistado.getColumnModel().getColumn(0).setMaxWidth(0);
        tablalistado.getColumnModel().getColumn(0).setMinWidth(0);
        tablalistado.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void contrasena() {
        password = txtContrasena.getText();
//        hash = BCrypt.checkpw(password, hashedpassword);
    }

    private void gif() {
        icono = new ImageIcon(getClass().getResource("/Imagenes/Cargando.gif"));
        lblIcono.setIcon(icono);
    }

    private void icono() {
        icono = new ImageIcon(getClass().getResource("/Imagenes/Usuario100.png"));
        lblIcono.setIcon(icono);
    }

    public void inicioSesion() {
        gif();
        if (txtUsuario.getText().length() == 0) {
            icono();
            mensaje = "Ingrese un Usuario";
            advertencia();
            txtUsuario.requestFocus();
            return;
        }

        if (txtContrasena.getText().length() == 0) {
            icono();
            mensaje = "Ingrese una contraseña";
            advertencia();
            txtContrasena.requestFocus();
            return;
        }

        dts.setUsuarios(txtUsuario.getText());
        modelo = func.login(dts.getUsuarios());
        tablalistado.setModel(modelo);

        //Si la contraseña ingresada es igual a la contraseña desencriptada se procede a Iniciar Sesion
        try {
            if (hash == true && func.totalRegistros > 0) {
                lblUsuarioOculto.setText(null);
                lblContrasenaOculta.setText(null);
                lblPermisos.setText(tablalistado.getValueAt(0, 9).toString());
                switch (lblPermisos.getText()) { //Se extraen los Permisos del usuario para saber que Frame le será habilitado
                    case "Administrador": {
                        dispose();
                        Principal3 form = new Principal3();
                        form.toFront();
                        form.setVisible(true);
                        Principal3.lblCodigo.setText(tablalistado.getValueAt(0, 0).toString());
                        Principal3.lblUsuario.setText(tablalistado.getValueAt(0, 1).toString());
                        Principal3.lblPermisos.setText(tablalistado.getValueAt(0, 9).toString());
                        break;
                    }
                    case "Cajero": {
                        dispose();
                        Principal3 form = new Principal3();
                        form.toFront();

                        //Bloquear Productos, Proveedores, Usuarios, Herramientas
                        Principal3.mnuProductos.setEnabled(false);
                        Principal3.mnuProveedores.setEnabled(false);
                        Principal3.smnuUsuarios.setEnabled(false);
                        Principal3.mnuHerramientas.setEnabled(false);

                        //Hacer visible el JFrame e ingresar datos del Usuario
                        form.setVisible(true);
                        Principal3.lblCodigo.setText(tablalistado.getValueAt(0, 0).toString());
                        Principal3.lblUsuario.setText(tablalistado.getValueAt(0, 1).toString());
                        Principal3.lblPermisos.setText(tablalistado.getValueAt(0, 9).toString());
                        break;
                    }
                }
            } else {
                icono();
                lblUsuarioOculto.setText(null);
                lblContrasenaOculta.setText(null);
                mensaje = "Datos incorrectos";
                fallo();
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void datos() {
        dts.setUsuarios(txtUsuario.getText());
        modelo = func.login(dts.getUsuarios());
        tablalistado.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablalistado = new javax.swing.JTable();
        sepUsuario = new javax.swing.JSeparator();
        sepContrasena = new javax.swing.JSeparator();
        btnSalir = new javax.swing.JButton();
        lblContrasena = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        lblPermisos = new javax.swing.JLabel();
        lblUsuarioOculto = new javax.swing.JLabel();
        lblContrasenaOculta = new javax.swing.JLabel();
        lblIcono = new javax.swing.JLabel();
        lblIconoUsuario = new javax.swing.JLabel();
        lblIconoContrasena = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablalistado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablalistado);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 20, 20));

        sepUsuario.setForeground(new java.awt.Color(0, 102, 255));
        sepUsuario.setAlignmentX(2.0F);
        sepUsuario.setAlignmentY(2.0F);
        sepUsuario.setMinimumSize(new java.awt.Dimension(1, 1));
        sepUsuario.setPreferredSize(new java.awt.Dimension(8, 8));
        getContentPane().add(sepUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 230, 15));

        sepContrasena.setForeground(new java.awt.Color(0, 102, 255));
        sepContrasena.setAlignmentX(2.0F);
        sepContrasena.setAlignmentY(2.0F);
        sepContrasena.setMinimumSize(new java.awt.Dimension(1, 1));
        sepContrasena.setPreferredSize(new java.awt.Dimension(8, 8));
        getContentPane().add(sepContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 230, 15));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar30.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 30, 30));

        lblContrasena.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblContrasena.setForeground(new java.awt.Color(0, 102, 255));
        lblContrasena.setText("CONTRASEÑA:");
        getContentPane().add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 140, -1));

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(0, 102, 255));
        lblUsuario.setText("USUARIO:");
        getContentPane().add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 100, -1));

        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUsuario.setText("Ingrese Usuario");
        txtUsuario.setToolTipText("Usuario");
        txtUsuario.setBorder(null);
        txtUsuario.setCaretColor(new java.awt.Color(255, 255, 255));
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsuarioMouseClicked(evt);
            }
        });
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 200, 30));

        txtContrasena.setForeground(new java.awt.Color(204, 204, 204));
        txtContrasena.setText("Igrese Contraseña");
        txtContrasena.setBorder(null);
        txtContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContrasenaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContrasenaFocusLost(evt);
            }
        });
        txtContrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtContrasenaMouseClicked(evt);
            }
        });
        txtContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContrasenaActionPerformed(evt);
            }
        });
        txtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyTyped(evt);
            }
        });
        getContentPane().add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 200, 30));

        btnIngresar.setBackground(new java.awt.Color(0, 102, 255));
        btnIngresar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/INGRESAR.png"))); // NOI18N
        btnIngresar.setBorder(null);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 190, 50));
        getContentPane().add(lblPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 20, 20));
        getContentPane().add(lblUsuarioOculto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 20, 20));
        getContentPane().add(lblContrasenaOculta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 20, 20));

        lblIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario100.png"))); // NOI18N
        lblIcono.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblIconoMouseDragged(evt);
            }
        });
        lblIcono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblIconoMousePressed(evt);
            }
        });
        getContentPane().add(lblIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 350, -1));

        lblIconoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario20.png"))); // NOI18N
        getContentPane().add(lblIconoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, -1, -1));

        lblIconoContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/llave20.png"))); // NOI18N
        getContentPane().add(lblIconoContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        lblFondo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo Login.png"))); // NOI18N
        lblFondo.setToolTipText("Usuario");
        lblFondo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblFondoMouseDragged(evt);
            }
        });
        lblFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFondoMousePressed(evt);
            }
        });
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        txtUsuario.transferFocus();
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        txtUsuario.setText("");
        txtContrasena.setText("");
        modelo.setRowCount(0);
        System.gc();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusGained
        if (tecla == false) {
            txtUsuario.setText("");
        }
    }//GEN-LAST:event_txtUsuarioFocusGained

    private void txtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMouseClicked
        txtUsuario.setText("");
    }//GEN-LAST:event_txtUsuarioMouseClicked

    private void txtContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContrasenaActionPerformed
        txtContrasena.transferFocus();
    }//GEN-LAST:event_txtContrasenaActionPerformed

    private void txtContrasenaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContrasenaMouseClicked
        txtContrasena.setText("");
    }//GEN-LAST:event_txtContrasenaMouseClicked

    private void txtContrasenaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContrasenaFocusGained
        txtContrasena.setText("");
    }//GEN-LAST:event_txtContrasenaFocusGained

    private void lblFondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFondoMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_lblFondoMouseDragged

    private void lblFondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFondoMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_lblFondoMousePressed

    private void lblIconoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconoMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_lblIconoMousePressed

    private void lblIconoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconoMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_lblIconoMouseDragged

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        mostrar("");
        if (txtUsuario.getText().length() == 0) {
            txtUsuario.setText("Ingrese Usuario");
        } else {
            mostrar("");
            if (tablalistado.getRowCount() == 0) {
                mensaje = "Este Usuario no existe";
                fallo();
                txtUsuario.requestFocus();
            } else {
                mostrar("");
                datos();
                hashedpassword = tablalistado.getValueAt(0, 2).toString();
            }
        }
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void txtContrasenaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContrasenaFocusLost
        if (txtContrasena.getText().length() == 0) {
            txtContrasena.setText("Ingrese Contraseña");
        } else {
            contrasena();
            inicioSesion();
        }
    }//GEN-LAST:event_txtContrasenaFocusLost

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        tecla = true;
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        icono = new ImageIcon(getClass().getResource("/Imagenes/Cargando.gif"));
        lblIcono.setIcon(icono);
        inicioSesion();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        mostrar("");
        datos();
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtContrasenaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_txtContrasenaKeyTyped

    private void txtContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContrasenaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtContrasena.transferFocus();
            btnIngresar.requestFocus();
            contrasena();
        }
    }//GEN-LAST:event_txtContrasenaKeyPressed

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            }

            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblContrasenaOculta;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblIcono;
    private javax.swing.JLabel lblIconoContrasena;
    private javax.swing.JLabel lblIconoUsuario;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuarioOculto;
    private javax.swing.JSeparator sepContrasena;
    private javax.swing.JSeparator sepUsuario;
    private javax.swing.JTable tablalistado;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
