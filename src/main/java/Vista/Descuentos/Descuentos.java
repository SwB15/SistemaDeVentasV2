package Vista.Descuentos;

import Model.DatosDescuentos;
import Model.DatosDetalleDescuentos;
import Controller.FuncionesDescuentos;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import View.Principal3;
import java.awt.Color;
import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author SwichBlade15
 */
public final class Descuentos extends javax.swing.JInternalFrame {

    FuncionesDescuentos funcion = new FuncionesDescuentos();
    DatosDescuentos datos = new DatosDescuentos();
    DatosDetalleDescuentos datosdetalle = new DatosDetalleDescuentos();
    Date date = null, date2 = null;
    java.sql.Date sql;
    String fk_clientes, fk_categorias, fk_productos;
    DefaultTableModel modelo, modelo2;
    boolean tecla = false;

    public Descuentos() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);

        rbtnActivo.doClick();
        mostrar("");
        botonesTransparentes();
        inhabilitar();
        txtCantidad.setEditable(false);
        txtCantidad.setText("0");
        txtCantidad.setBackground(Color.white);
    }

    public void mostrar(String buscar) {
        try {
            modelo = funcion.mostrar(buscar);
            tblDescuentos.setModel(modelo);

            //Tamaño de las columnas de la tabla
            TableColumnModel columnModel = tblDescuentos.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(300);
            columnModel.getColumn(4).setPreferredWidth(30);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);
            columnModel.getColumn(7).setPreferredWidth(20);

            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Obtiene los datos de los detalles de la funcion MostrarDetalles
    public void mostrarDetalles(int buscar) {
        try {
            modelo2 = funcion.mostrarDetalle(buscar);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void botonesTransparentes() {
        btnGuardar.setOpaque(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setBorderPainted(false);

        btnSeleccionar.setOpaque(false);
        btnSeleccionar.setContentAreaFilled(false);
        btnSeleccionar.setBorderPainted(false);

        btnNuevo.setOpaque(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setBorderPainted(false);

        btnAnular.setOpaque(false);
        btnAnular.setContentAreaFilled(false);
        btnAnular.setBorderPainted(false);
    }

    private void revisarDetalles() {
        //Revisa la columna de productos, categorias y clientes para ver si alguno tiene datos dentro
        //Si alguno tiene datos, significa que los otros 2 no tienen ningun dato
        txtIddetalledescuentos.setText(String.valueOf(modelo2.getValueAt(0, 0)));

        if (!String.valueOf(modelo2.getValueAt(0, 1)).equals("null")) {  //Verifica si la columna productos es nula
            txtTipo.setText("Productos");
            txtAplicar.setText(String.valueOf(modelo2.getValueAt(0, 1)));
            txtCodigo.setText(String.valueOf(modelo2.getValueAt(0, 2)));
        } else if (!String.valueOf(modelo2.getValueAt(0, 4)).equals("null")) {  //Verifica si la columna categorias es nula
            txtTipo.setText("Categorias");
            txtAplicar.setText(String.valueOf(modelo2.getValueAt(0, 4)));
            txtCodigo.setText(String.valueOf(modelo2.getValueAt(0, 5)));
        } else if (!String.valueOf(modelo2.getValueAt(0, 6)).equals("null")) {  //Verifica si la columna clientes es nula
            txtTipo.setText("Clientes");
            txtAplicar.setText(String.valueOf(modelo2.getValueAt(0, 6)));
            txtCodigo.setText(String.valueOf(modelo2.getValueAt(0, 7)));
        }
    }

    public void ocultar_columnas() {
        tblDescuentos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblDescuentos.getColumnModel().getColumn(0).setMinWidth(0);
        tblDescuentos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void inhabilitar() {
        txtNombre.setEditable(false);
        txtDescuentos.setEditable(false);
        dchFechainicio.setEnabled(false);
        dchFechacierre.setEnabled(false);
        atxtDescripcion.setEditable(false);
        rbtnActivo.setEnabled(false);
        rbtnInactivo.setEnabled(false);
        txtCantidad.setEditable(false);
        
        btnSeleccionar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnAnular.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    private void limpiarTextFields() {
        txtCodigo.setText("");
        txtAplicar.setText("");
        txtTipo.setText("");
        txtNombre.setText("");
        txtDescuentos.setText("");
        dchFechainicio.setCalendar(null);
        dchFechacierre.setCalendar(null);
        atxtDescripcion.setText("");
        txtCantidad.setText("0");

    }

    private void habilitar() {
        txtNombre.setEditable(true);
        txtDescuentos.setEditable(true);
        dchFechainicio.setEnabled(true);
        dchFechacierre.setEnabled(true);
        atxtDescripcion.setEditable(true);
        rbtnActivo.setEnabled(true);
        rbtnInactivo.setEnabled(true);
        txtCantidad.setEditable(true);
        
        btnSeleccionar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnAnular.setEnabled(true);
        btnNuevo.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbtngDescuentos = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtDescuentos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        atxtDescripcion = new javax.swing.JTextArea();
        dchFechainicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dchFechacierre = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDescuentos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtAplicar = new javax.swing.JTextField();
        btnSeleccionar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        rbtnActivo = new javax.swing.JRadioButton();
        rbtnInactivo = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        txtIddescuentos = new javax.swing.JTextField();
        txtIddetalledescuentos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        lblFondoBuscar = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setOpaque(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Descuento:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 213, -1, -1));

        txtDescuentos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txtDescuentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 100, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Descripcion:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, -1, -1));

        atxtDescripcion.setColumns(20);
        atxtDescripcion.setRows(5);
        jScrollPane1.setViewportView(atxtDescripcion);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, -1, -1));

        dchFechainicio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(dchFechainicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 118, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("F. Inicio:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 243, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("F. Cierre:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 273, -1, -1));

        dchFechacierre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(dchFechacierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 118, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Aplicar a:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 123, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("%");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, -1, -1));

        tblDescuentos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblDescuentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDescuentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDescuentosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDescuentos);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 378, 930, 200));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Nombre:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 183, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 200, -1));

        lblCantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCantidad.setText("Cantidad:");
        getContentPane().add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, -1, -1));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 280, 145, -1));

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 100, -1));

        txtAplicar.setEditable(false);
        txtAplicar.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtAplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 300, -1));

        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonSeleccionar.png"))); // NOI18N
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnSeleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 115, 100, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Estado:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, -1, -1));

        rbtnActivo.setBackground(new java.awt.Color(255, 255, 255));
        rbtngDescuentos.add(rbtnActivo);
        rbtnActivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnActivo.setText("Activo");
        getContentPane().add(rbtnActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 250, -1, -1));

        rbtnInactivo.setBackground(new java.awt.Color(255, 255, 255));
        rbtngDescuentos.add(rbtnInactivo);
        rbtnInactivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnInactivo.setText("Inactivo");
        getContentPane().add(rbtnInactivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tipo:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 153, -1, -1));

        txtTipo.setEditable(false);
        txtTipo.setBackground(new java.awt.Color(255, 255, 255));
        txtTipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txtTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 100, -1));
        getContentPane().add(txtIddescuentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 41, -1));
        getContentPane().add(txtIddetalledescuentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 40, -1));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Descuentos");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 270, -1));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 14, 40, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar32.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 40, 40));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.setBorder(null);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 320, 40, 40));

        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Anular32.png"))); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 320, 40, -1));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Descuento...");
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
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(782, 77, 146, 17));

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, -1, 30));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoDescuentos.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        SeleccionarDescuentos dialog = new SeleccionarDescuentos(f, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (evt.getClickCount() == 2) {
            inhabilitar();
        }
    }//GEN-LAST:event_formMouseClicked

    private void tblDescuentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDescuentosMouseClicked
        int seleccionar = tblDescuentos.rowAtPoint(evt.getPoint());

        txtIddescuentos.setText(String.valueOf(tblDescuentos.getValueAt(seleccionar, 0)));
        txtNombre.setText(String.valueOf(tblDescuentos.getValueAt(seleccionar, 1)));
        txtDescuentos.setText(String.valueOf(tblDescuentos.getValueAt(seleccionar, 2)));
        atxtDescripcion.setText(String.valueOf(tblDescuentos.getValueAt(seleccionar, 3)));

//********************************Fechas********************************
        //Fecha inicio. 
        //Convertir java.sql.date a java.util.date y mostrar en pantalla la fecha de inicio
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String fechaInicio = String.valueOf(tblDescuentos.getValueAt(seleccionar, 4));
            date = formato.parse(fechaInicio);
        } catch (ParseException e) {

        }

        //Aplica formato requerido.
        try {
            formato.applyPattern("dd/MM/yyyy");
            String nuevoFormato = formato.format(date);
            Date fecha = formato.parse(nuevoFormato);
            dchFechainicio.setDate(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Fin Fecha Inicio

        //Fecha cierre. 
        //Convertir java.sql.date a java.util.date y mostrar en pantalla la fecha de inicio
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String fechacierre = String.valueOf(tblDescuentos.getValueAt(seleccionar, 5));
            date2 = formato2.parse(fechacierre);
        } catch (ParseException e) {

        }

        //Aplica formato requerido.
        try {
            formato2.applyPattern("dd/MM/yyyy");
            String nuevoFormato2 = formato2.format(date2);
            Date fecha2 = formato2.parse(nuevoFormato2);
            dchFechacierre.setDate(fecha2);
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Fin Fecha Cierre
//********************************Fechas********************************

        txtCantidad.setText(String.valueOf(tblDescuentos.getValueAt(seleccionar, 6)));

        //Se hace click en el estado
        switch (String.valueOf(tblDescuentos.getValueAt(seleccionar, 7))) {
            case "Activo":
                rbtnActivo.doClick();
                habilitar();
                break;
            case "Inactivo":
                rbtnInactivo.doClick();
                habilitar();
                break;
            case "Anulado":
                rbtnInactivo.doClick();
                inhabilitar();
                break;
            default:
                break;
        }

        mostrarDetalles(Integer.parseInt(txtIddescuentos.getText()));
        revisarDetalles();
    }//GEN-LAST:event_tblDescuentosMouseClicked

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtIddescuentos.getText().length() == 0) {
            guardar();
        } else {
            editar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        habilitar();
        limpiarTextFields();
        txtNombre.requestFocus();
        txtBuscar.setEditable(false);
        mostrar("");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (tecla == false) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (tecla == false) {
            txtBuscar.setText("Buscar Descuento...");
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        txtBuscar.transferFocus();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        tecla = true;
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        mostrar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

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

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        anular();
    }//GEN-LAST:event_btnAnularActionPerformed

    public void validarCampos() {

    }

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
        icono = new ImageIcon(getClass().getResource("/Imagenes/FondoCerrarSesion.png"));
        Aceptar_Cancelar.lblFondo.setIcon(icono);
        Aceptar_Cancelar.lblEncabezado.setText(encabezado);
        Aceptar_Cancelar.lblMensaje.setText(mensaje);
        dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea atxtDescripcion;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSeleccionar;
    private com.toedter.calendar.JDateChooser dchFechacierre;
    private com.toedter.calendar.JDateChooser dchFechainicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondoBuscar;
    private javax.swing.JRadioButton rbtnActivo;
    private javax.swing.JRadioButton rbtnInactivo;
    private javax.swing.ButtonGroup rbtngDescuentos;
    public static javax.swing.JTable tblDescuentos;
    public static javax.swing.JTextField txtAplicar;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescuentos;
    private javax.swing.JTextField txtIddescuentos;
    public static javax.swing.JTextField txtIddetalledescuentos;
    private javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables

    public void guardar() {
        validarCampos();

        //Convertir Fecha Inicio de java.util.date a java.sql.date
        try {
            date = dchFechainicio.getDate();
            SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
            String tiempo = plantilla.format(date);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(tiempo);
            sql = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos.setFechainicio(sql);

        //Convertir Fecha Cierre de java.util.date a java.sql.date
        try {
            date = dchFechacierre.getDate();
            SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
            String tiempo = plantilla.format(date);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(tiempo);
            sql = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos.setNombredescuentos(txtNombre.getText());
        datos.setDescuentos(Integer.parseInt(txtDescuentos.getText()));
        datos.setDescripcion(atxtDescripcion.getText());
        datos.setFechacierre(sql);
        datos.setCantidad(Integer.parseInt(txtCantidad.getText()));

        //Ingreso del estado del descuento
        if (rbtnActivo.isSelected()) {
            datos.setEstado("Activo");
        } else if (rbtnInactivo.isSelected()) {
            datos.setEstado("Inactivo");
        }

        if (funcion.insertar(datos)) {
            switch (txtTipo.getText()) {
                case "Clientes":
                    fk_clientes = txtCodigo.getText();
                    if (funcion.insertarDetalleConClientes(funcion.buscarDescuento(txtNombre.getText()), funcion.buscarClientes(fk_clientes))) {
                        mensaje = "Descuento guardado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no guardado";
                        fallo();
                        mostrar("");
                    }
                    break;
                case "Categorias":
                    fk_categorias = txtCodigo.getText();
                    if (funcion.insertarDetalleConCategorias(funcion.buscarDescuento(txtNombre.getText()), funcion.buscarCategorias(txtAplicar.getText()))) {
                        mensaje = "Descuento guardado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no guardado";
                        fallo();
                        mostrar("");
                    }
                    break;
                case "Productos":
                    fk_productos = txtCodigo.getText();
                    if (funcion.insertarDetalleConProductos(funcion.buscarProductos(fk_productos), funcion.buscarDescuento(txtNombre.getText()))) {
                        mensaje = "Descuento guardado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no guardado";
                        fallo();
                        mostrar("");
                    }
                    break;
                default:
                    break;
            }
        } else {
            mensaje = "Descuento no guardado";
            fallo();
            mostrar("");
        }
    }

    public void editar() {
        validarCampos();

        datos.setIddescuentos(Integer.parseInt(txtIddescuentos.getText()));

        //Convertir Fecha Inicio de java.util.date a java.sql.date
        try {
            date = dchFechainicio.getDate();
            SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
            String tiempo = plantilla.format(date);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(tiempo);
            sql = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos.setFechainicio(sql);

        //Convertir Fecha Cierre de java.util.date a java.sql.date
        try {
            date = dchFechacierre.getDate();
            SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
            String tiempo = plantilla.format(date);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(tiempo);
            sql = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos.setNombredescuentos(txtNombre.getText());
        datos.setDescuentos(Integer.parseInt(txtDescuentos.getText()));
        datos.setFechacierre(sql);
        datos.setCantidad(Integer.parseInt(txtCantidad.getText()));
        datos.setDescripcion(atxtDescripcion.getText());

        //Ingreso del estado del descuento
        if (rbtnActivo.isSelected()) {
            datos.setEstado("Activo");
        } else if (rbtnInactivo.isSelected()) {
            datos.setEstado("Inactivo");
        }

        datosdetalle.setIddetalledescuentos(Integer.parseInt(txtIddetalledescuentos.getText()));

        if (funcion.editar(datos)) {
            switch (txtTipo.getText()) {
                case "Clientes":
                    fk_clientes = txtCodigo.getText();
                    if (funcion.editarDetalleConClientes(datosdetalle, txtNombre.getText(), fk_clientes)) {
                        mensaje = "Descuento editado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no editado";
                        fallo();
                        mostrar("");
                    }
                    break;
                case "Categorias":
                    fk_categorias = txtCodigo.getText();
                    if (funcion.editarDetalleConCategorias(datosdetalle, Integer.parseInt(txtIddescuentos.getText()), Integer.parseInt(txtCodigo.getText()))) {
                        mensaje = "Descuento editado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no editado";
                        fallo();
                        mostrar("");
                    }
                    break;
                case "Productos":
                    fk_productos = txtCodigo.getText();
                    if (funcion.editarDetalleConProductos(datosdetalle, txtNombre.getText(), fk_productos)) {
                        mensaje = "Descuento editado correctamente";
                        realizado();
                        mostrar("");
                        inhabilitar();
                        limpiarTextFields();
                    } else {
                        mensaje = "Descuento no editado";
                        fallo();
                        mostrar("");
                    }
                    break;
                default:
                    break;
            }
        } else {
            mensaje = "Descuento no editado";
            fallo();
            mostrar("");
        }
    }

    public void anular() {
        if (txtNombre.getText().length() == 0) {
            mensaje = "Debes seleccionar primero un Descuento a anular.";
            advertencia();
            txtNombre.requestFocus();
        } else {
            encabezado = "Anular Descuento";
            mensaje = "Ya no se podrá modificar una vez anulado";
            aceptarCancelar();
            String reply = Principal3.txtAceptarCancelar.getText();
            if (reply.equals("1")) {
                datos.setIddescuentos(Integer.parseInt(txtIddescuentos.getText()));
                datos.setEstado("Anulado");

                if (funcion.editar(datos)) {
                    mensaje = "Descuento anulado correctamente.";
                    realizado();

                    mostrar("");
                    inhabilitar();
                    limpiarTextFields();

//                    String usuario = Principal.lblUsuario.getText();
//                    String objeto = txtNombre.getText();
//                    String Accion = "Anular Descuento";
//                    audi.audiusuarios(usuario, objeto, Accion);
                } else {
                    mensaje = "Ha ocurrido un error al anular el Descuento.";
                    fallo();
                    mostrar("");
                }

                datos.setIddescuentos(Integer.parseInt(txtIddescuentos.getText()));

                //Convertir Fecha Inicio de java.util.date a java.sql.date
                try {
                    date = dchFechainicio.getDate();
                    SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
                    String tiempo = plantilla.format(date);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date parsed = format.parse(tiempo);
                    sql = new java.sql.Date(parsed.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
                }

                datos.setFechainicio(sql);

                //Convertir Fecha Cierre de java.util.date a java.sql.date
                try {
                    date = dchFechacierre.getDate();
                    SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
                    String tiempo = plantilla.format(date);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date parsed = format.parse(tiempo);
                    sql = new java.sql.Date(parsed.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(Descuentos.class.getName()).log(Level.SEVERE, null, ex);
                }

                datos.setNombredescuentos(txtNombre.getText());
                datos.setDescuentos(Integer.parseInt(txtDescuentos.getText()));
                datos.setFechacierre(sql);
                datos.setCantidad(Integer.parseInt(txtCantidad.getText()));
                datos.setDescripcion(atxtDescripcion.getText());

                datosdetalle.setIddetalledescuentos(Integer.parseInt(txtIddetalledescuentos.getText()));

                if (funcion.editar(datos)) {
                    switch (txtTipo.getText()) {
                        case "Clientes":
                            fk_clientes = txtCodigo.getText();
                            if (funcion.editarDetalleConClientes(datosdetalle, txtNombre.getText(), fk_clientes)) {
                                mensaje = "Descuento anulado correctamente";
                                realizado();
                                mostrar("");
                                inhabilitar();
                                limpiarTextFields();
                            } else {
                                mensaje = "Descuento no anulado";
                                fallo();
                                mostrar("");
                            }
                            break;
                        case "Categorias":
                            fk_categorias = txtCodigo.getText();
                            if (funcion.editarDetalleConCategorias(datosdetalle, Integer.parseInt(txtIddescuentos.getText()), Integer.parseInt(txtCodigo.getText()))) {
                                mensaje = "Descuento anulado correctamente";
                                realizado();
                                mostrar("");
                                inhabilitar();
                                limpiarTextFields();
                            } else {
                                mensaje = "Descuento no anulado";
                                fallo();
                                mostrar("");
                            }
                            break;
                        case "Productos":
                            fk_productos = txtCodigo.getText();
                            if (funcion.editarDetalleConProductos(datosdetalle, txtNombre.getText(), fk_productos)) {
                                mensaje = "Descuento anulado correctamente";
                                realizado();
                                mostrar("");
                                inhabilitar();
                                limpiarTextFields();
                            } else {
                                mensaje = "Descuento no anulado";
                                fallo();
                                mostrar("");
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    mensaje = "Descuento no anulado";
                    fallo();
                    mostrar("");
                }
            }
        }
    }
}
