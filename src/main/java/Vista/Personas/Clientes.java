package Vista.Personas;

import Model.DatosClientes;
import Model.DatosDepartamentos;
import Controller.FuncionesAuditoria;
import Controller.FuncionesClientes;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import View.Principal3;
import java.awt.Color;
import java.awt.Frame;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Clientes extends javax.swing.JInternalFrame {

    DatosClientes datos = new DatosClientes();
    FuncionesClientes funcion = new FuncionesClientes();
//    FuncionesAuditoria audi = new FuncionesAuditoria();
    boolean tecla = false;

    public Clientes() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);
        inhabilitar();
        mostrar("");
        funcion.listar_departamentos(cmbDepartamentos);
        cmbCiudades.setEnabled(false);
        cmbDepartamentos.setEnabled(false);
        txtIdclientes.setVisible(false);
        
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                btnGuardarActionPerformed(null);
            }
        });

        botonesTransparentes();
    }

    public void mostrar(String buscar) {
        try {
            DefaultTableModel modelo;
            modelo = funcion.mostrar(buscar);
            tblClientes.setModel(modelo);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void inhabilitar() {
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtCedula.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);

        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtBuscar.setText("");

        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    public void habilitar() {
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtCedula.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);

        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtBuscar.setText("");

        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnNuevo.setEnabled(false);
    }

    public void ocultar_columnas() {
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtIdclientes = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        cmbCiudades = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblFondoBuscar = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Clientes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Nombre:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 125, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Apellido:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 125, 170, -1));

        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 157, 170, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Cedula:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 192, -1, -1));

        txtTelefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 157, 100, -1));

        txtDireccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 125, 170, -1));

        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });
        getContentPane().add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 189, 70, -1));

        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmailMouseClicked(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 189, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Direccion:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 128, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Telefono:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Email:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(396, 192, -1, -1));

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 601, 160));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar32.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, 40, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar32.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 40, 40));
        getContentPane().add(txtIdclientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 60, 21, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 40, 40));

        cmbCiudades.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbCiudades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCiudades.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCiudadesItemStateChanged(evt);
            }
        });
        getContentPane().add(cmbCiudades, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 221, 170, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Ciudad:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 224, -1, -1));

        cmbDepartamentos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbDepartamentos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDepartamentos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDepartamentosItemStateChanged(evt);
            }
        });
        getContentPane().add(cmbDepartamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 221, 170, 24));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Departamento:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 226, -1, -1));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Clientes...");
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

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, -1, -1));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 14, -1, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoUsuarios.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        int seleccionar = tblClientes.rowAtPoint(evt.getPoint());
        txtIdclientes.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 0)));
        txtNombre.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 1)));
        txtApellido.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 2)));
        txtCedula.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 3)));
        txtDireccion.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 4)));
        txtTelefono.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 5)));
        txtEmail.setText(String.valueOf(tblClientes.getValueAt(seleccionar, 6)));

        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtCedula.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        cmbDepartamentos.setEnabled(true);
        cmbCiudades.setEnabled(true);

        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnNuevo.setEnabled(false);
        txtBuscar.setEditable(false);
    }//GEN-LAST:event_tblClientesMouseClicked

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        int numerocaracteres = 10;
        if (txtCedula.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Ingrese una cedula valida";
            advertencia();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        int numerocaracteres = 14;
        if (txtCedula.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 15 numeros";
            advertencia();
        }

        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo numeros";
            advertencia();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(txtEmail.getText());

        if (!mather.find() == true) {
            mensaje = "El email ingresado es invalido";
            advertencia();
        } else {
            txtEmail.transferFocus();
            btnGuardar.requestFocus();
        }
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
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
        if (txtNombre.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 20 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
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
        if (txtNombre.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 20 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        int numerocaracteres = 149;
        if (txtNombre.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 150 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtIdclientes.getText().length() == 0) {
            guardar();
        } else {
            editar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        habilitar();
        cmbCiudades.setEnabled(true);
        cmbDepartamentos.setEnabled(true);
        txtNombre.requestFocus();
        txtBuscar.setEditable(false);
        mostrar("");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        txtNombre.transferFocus();
        txtApellido.requestFocus();
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        txtApellido.transferFocus();
        txtCedula.requestFocus();
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        txtCedula.transferFocus();
        txtDireccion.requestFocus();
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        txtDireccion.transferFocus();
        txtTelefono.requestFocus();
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        txtTelefono.transferFocus();
        txtEmail.requestFocus();
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void cmbDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDepartamentosItemStateChanged
        DatosDepartamentos dep = (DatosDepartamentos) this.cmbDepartamentos.getSelectedItem();
        int iddep = dep.getIddepartamentos();
        String n = dep.getDepartamentos();

        funcion.listar_ciudades(cmbCiudades, iddep);
    }//GEN-LAST:event_cmbDepartamentosItemStateChanged

    private void cmbCiudadesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCiudadesItemStateChanged

    }//GEN-LAST:event_cmbCiudadesItemStateChanged

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

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        txtBuscar.transferFocus();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        tecla = true;
    }//GEN-LAST:event_txtBuscarKeyPressed

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

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailMouseClicked

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo numeros";
            advertencia();
        }

        int numerocaracteres = 100;
        if (txtEmail.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 100 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        mostrar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    //Metodos para llamar a los JDialog de Advertencia, Fallo y Realizado
    Frame f = JOptionPane.getFrameForComponent(this);
    String encabezado;
    String mensaje;
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
        icono = new ImageIcon(getClass().getResource("/Imagenes/FondoCerrarSesion.png"));
        Aceptar_Cancelar.lblFondo.setIcon(icono);
        Aceptar_Cancelar.lblEncabezado.setText(encabezado);
        Aceptar_Cancelar.lblMensaje.setText(mensaje);
        dialog.setVisible(true);
    }

    public void validarCampos() {
        if (txtNombre.getText().length() == 0) {
            mensaje = "Debes ingresar un Nombre.";
            advertencia();
            txtNombre.requestFocus();
            return;
        }

        if (txtApellido.getText().length() == 0) {
            mensaje = "Debes ingresar un Apellido.";
            advertencia();
            txtApellido.requestFocus();
            return;
        }

        if (txtCedula.getText().length() == 0) {
            mensaje = "Debes ingresar una Cedula.";
            advertencia();
            txtCedula.requestFocus();
            return;
        }

        if (txtDireccion.getText().length() == 0) {
            mensaje = "Debes ingresar una Direccion.";
            advertencia();
            txtDireccion.requestFocus();
            return;
        }

        if (txtTelefono.getText().length() == 0) {
            mensaje = "Debes ingresar un Telefono.";
            advertencia();
            txtTelefono.requestFocus();
            return;
        }

        if (txtEmail.getText().length() == 0) {
            mensaje = "Debes ingresar un Email.";
            advertencia();
            txtEmail.requestFocus();
            return;
        }

        if (cmbDepartamentos.getSelectedItem() == null) {
            mensaje = "No existen DEPARTAMENTOS en el sistema";
            advertencia();
            this.dispose();
        }

        if (cmbCiudades.getSelectedItem() == null) {
            mensaje = "No existen CIUDADES en el sistema";
            advertencia();
            this.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cmbCiudades;
    private javax.swing.JComboBox cmbDepartamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondoBuscar;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdclientes;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    public void guardar() {
        validarCampos();

        datos.setNombre(txtNombre.getText());
        datos.setApellido(txtApellido.getText());
        datos.setCedula(txtCedula.getText());
        datos.setDireccion(txtDireccion.getText());
        datos.setTelefono(txtTelefono.getText());
        datos.setEmail(txtEmail.getText());

        String ciudad2 = (cmbCiudades.getSelectedItem().toString());

        if (funcion.insertar(datos, funcion.buscarCiudades(ciudad2))) {
            mensaje = "Cliente ingresado correctamente.";
            realizado();
            mostrar("");

            String usuario = Principal3.lblUsuario.getText();
            String objeto = txtNombre.getText();
            String Accion = "GUARDAR";
            String Descripcion = txtNombre.getText() + " " + txtApellido.getText();
            System.out.println(Descripcion);
//            audi.audiclientes(usuario, objeto, Accion, Descripcion);
            inhabilitar();
        } else {
            mensaje = "El cliente no ha sido ingresado";
            fallo();
            mostrar("");
        }
    }

    public void editar() {
        validarCampos();

        datos.setIdclientes(Integer.parseInt(txtIdclientes.getText()));
        datos.setNombre(txtNombre.getText());
        datos.setApellido(txtApellido.getText());
        datos.setCedula(txtCedula.getText());
        datos.setDireccion(txtDireccion.getText());
        datos.setTelefono(txtTelefono.getText());
        datos.setEmail(txtEmail.getText());

        String row = (String) cmbCiudades.getSelectedItem().toString();

        if (funcion.editar(datos, row)) {
            mensaje = "Cliente editado correctamente.";
            realizado();
            mostrar("");
            inhabilitar();

            String usuario = Principal3.lblUsuario.getText();
            String objeto = txtNombre.getText();
            String Accion = "EDITAR";
//            audi.audiclientes(usuario, objeto, Accion);
        } else {
            mensaje = "El cliente no ha sido editado";
            fallo();
            mostrar("");
        }
    }

    public void eliminar() {
        if (txtNombre.getText().length() == 0) {
            mensaje = "Debes seleccionar primero un Cliente a eliminar.";
            advertencia();
            txtNombre.requestFocus();
        } else {
            encabezado = "Eliminar permanentemente";
            mensaje = "Esta seguro de eliminar este registro?";
            String reply = Principal3.txtAceptarCancelar.getText();
            if (reply.equals("1")) {

                datos.setIdclientes(Integer.parseInt(txtIdclientes.getText()));
                if (funcion.eliminar(datos)) {
                    mensaje = "Cliente eliminado correctamente.";
                    realizado();
                    mostrar("");
                    inhabilitar();

                    String usuario = Principal3.lblUsuario.getText();
                    String objeto = txtNombre.getText();
                    String Accion = "ELIMINAR";
////                    audi.audiclientes(usuario, objeto, Accion);
                } else {
                    mensaje = "El cliente no ha sido eliminado";
                    fallo();
                    mostrar("");
                }
            }
        }
    }
}