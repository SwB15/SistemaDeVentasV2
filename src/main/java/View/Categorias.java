package View;

import Model.DatosCategorias;
import Fuentes.Fuentes;
import Controller.FuncionesCategorias;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Categorias extends javax.swing.JInternalFrame {

    DatosCategorias datos = new DatosCategorias();
    FuncionesCategorias funcion = new FuncionesCategorias();
    boolean tecla = false;
    Fuentes tipofuentes;

    public Categorias() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        inhabilitar();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);
        txtIdcategorias.setVisible(false);
        txtUsuario.setVisible(false);
        botonesTransparentes();
        mostrar("");
    }

    public void mostrar(String buscar) {
        try {
            DefaultTableModel modelo;
            modelo = funcion.mostrar(buscar);
            tblCategorias.setModel(modelo);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ocultar_columnas() {
        tblCategorias.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCategorias.getColumnModel().getColumn(0).setMinWidth(0);
        tblCategorias.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void habilitar() {
        txtBuscar.setText("Buscar Categoria...");
        txtCategorias.setText("");

        txtCategorias.setEditable(true);

        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    public void inhabilitar() {
        txtBuscar.requestFocus();

        txtCategorias.setText("");
        txtIdcategorias.setText("");

        txtCategorias.setEditable(false);

        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    public void botonesTransparentes() {
        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);

        btnGuardar.setOpaque(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setBorderPainted(false);

        btnNuevo.setOpaque(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setBorderPainted(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEncabezado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategorias = new javax.swing.JTable();
        txtCategorias = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtIdcategorias = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblCerrar = new javax.swing.JLabel();
        lblFondoBuscar = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblFondo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("Categorias");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Categoria20.png"))); // NOI18N
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(650, 443));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEncabezado.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        lblEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        lblEncabezado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEncabezado.setText("Categorias");
        getContentPane().add(lblEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, -1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblCategorias.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        tblCategorias = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCategorias);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 610, 210));

        txtCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCategorias.setBorder(null);
        txtCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriasActionPerformed(evt);
            }
        });
        txtCategorias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoriasKeyTyped(evt);
            }
        });
        getContentPane().add(txtCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 200, -1));

        lblMarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMarca.setText("Categoria:");
        getContentPane().add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 111, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 102, 255));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 126, 200, 10));
        getContentPane().add(txtIdcategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 20, -1));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Categoria...");
        txtBuscar.setBorder(null);
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 73, 148, 18));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar32.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 40, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.setToolTipText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 40, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar32.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 40, -1));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 14, -1, -1));

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, -1, -1));
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 21, -1));

        lblFondo.setBackground(new java.awt.Color(0, 51, 51));
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoCategorias.png"))); // NOI18N
        lblFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFondoMouseClicked(evt);
            }
        });
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriasMouseClicked
        int seleccionar = tblCategorias.rowAtPoint(evt.getPoint());
        txtIdcategorias.setText(String.valueOf(tblCategorias.getValueAt(seleccionar, 0)));
        txtCategorias.setText(String.valueOf(tblCategorias.getValueAt(seleccionar, 1)));
        txtCategorias.setEditable(true);
        txtCategorias.requestFocus();
        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnNuevo.setEnabled(false);
        txtBuscar.setEditable(false);
    }//GEN-LAST:event_tblCategoriasMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        txtBuscar.transferFocus();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo letras";
            advertencia();
        }

        int numerocaracteres = 19;
        if (txtBuscar.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 20 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtIdcategorias.getText().length() == 0) {
            guardar();
        } else {
            editar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        habilitar();
        btnEliminar.setEnabled(false);
        txtCategorias.requestFocus();
        mostrar("");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (tecla == false) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (tecla == false) {
            txtBuscar.setText("Buscar Categoria...");
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        tecla = true;
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void lblFondoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFondoMouseClicked
        //Si se hace clic 2 veces en cualquier zona blanca, el frame se resetea a sus valores iniciales
        if (evt.getClickCount() == 2) {
            inhabilitar();
        }
    }//GEN-LAST:event_lblFondoMouseClicked

    private void txtCategoriasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriasKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo letras";
            advertencia();
        }

        int numerocaracteres = 39;
        if (txtBuscar.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 40 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtCategoriasKeyTyped

    private void txtCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriasActionPerformed
        if (txtIdcategorias.getText().length() == 0) {
            guardar();
        } else {
            editar();
        }
    }//GEN-LAST:event_txtCategoriasActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        mostrar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    //Metodos para llamar a los JDialog de Advertencia, Fallo y Realizado
    Frame f = JOptionPane.getFrameForComponent(this);
    String mensaje;
    String encabezado;
    Icon icono;
    
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
    
    public void aceptarCancelar() {
        Aceptar_Cancelar dialog = new Aceptar_Cancelar(f, true);
        icono = new ImageIcon(getClass().getResource("/Imagenes/FondoNotificacionesAzul.png"));
        Aceptar_Cancelar.lblFondo.setIcon(icono);
        Aceptar_Cancelar.lblEncabezado.setText(encabezado);
        Aceptar_Cancelar.lblMensaje.setText(mensaje);
        dialog.setVisible(true);
    }
    
    private void validarCampos(){
        if (txtCategorias.getText().length() == 0) {
            mensaje = "Ingrese una Categoria";
            advertencia();

            txtCategorias.requestFocus();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblEncabezado;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondoBuscar;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JTable tblCategorias;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtCategorias;
    private javax.swing.JTextField txtIdcategorias;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    public void guardar() {
        validarCampos();

        datos.setCategorias(txtCategorias.getText());

        if (funcion.insertar(datos)) {
            mensaje = "Categoria guardada correctamente";
            txtBuscar.setEditable(true);
            txtBuscar.requestFocus();
            realizado();
            mostrar("");
            inhabilitar();
        } else {
            mensaje = "Categoria no guardada";
            fallo();
            mostrar("");
        }
    }
    
    public void editar() {
        validarCampos();

        datos.setIdcategorias(Integer.parseInt(txtIdcategorias.getText()));
        datos.setCategorias(txtCategorias.getText());

        if (funcion.editar(datos)) {
            mensaje = "Categoria editada correctamente";
            realizado();
            txtBuscar.setEditable(true);
            txtBuscar.requestFocus();
            mostrar("");
            inhabilitar();
        } else {
            mensaje = "Categoria no editada";
            fallo();
            mostrar("");
        }
    }

    public void eliminar() {
        if (txtCategorias.getText().length() == 0) {
            mensaje = "Seleccione primero una categoria";
            advertencia();
            txtCategorias.requestFocus();
        } else {
            encabezado = "Eliminar permanentemente";
            mensaje = "Esta seguro de eliminar este registro?";
            aceptarCancelar();
            String reply = Principal3.txtAceptarCancelar.getText();
            if (reply.equals("1")) {

                datos.setIdcategorias(Integer.parseInt(txtIdcategorias.getText()));

                if (funcion.eliminar(datos)) {
                    mensaje = "Categoria eliminada correctamente";
                    realizado();
                    txtBuscar.setEditable(true);
                    txtBuscar.requestFocus();
                    mostrar("");
                    inhabilitar();
                } else {
                    mensaje = "Categoria no eliminada";
                    fallo();
                    mostrar("");
                }
            }
        }
    }
}