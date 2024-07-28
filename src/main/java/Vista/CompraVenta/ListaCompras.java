package Vista.CompraVenta;

import Config.DataSource;
import Model.DatosCaja;
import Model.DatosCompras;
import Model.DatosDetalleCaja;
import Model.DatosDetalleCompra;
import Controller.FuncionesAuditoria;
import Controller.FuncionesCaja;
import Controller.FuncionesCompras;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import View.Principal3;
import static View.Principal3.jDesktopPane1;
import static View.Principal3.lblProceso;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class ListaCompras extends javax.swing.JInternalFrame {

    Compras form = new Compras();
//    FuncionesAuditoria audi = new FuncionesAuditoria();
    FuncionesCompras funcion = new FuncionesCompras();
    DatosCompras datos = new DatosCompras();
    DatosDetalleCompra datosdetalle = new DatosDetalleCompra();
    FuncionesCaja funcaja = new FuncionesCaja();
    DatosCaja datoscaja = new DatosCaja();
    DatosDetalleCaja detallecaja = new DatosDetalleCaja();
    public boolean tecla = false;
    DefaultTableModel model, modelodet, modeloprod;
    PreparedStatement ps;
    ResultSet rs;
    int seleccionar, fk_clientes, ingresodia, idd, cantidad, iddetallecaja, i, ingre;

    public ListaCompras() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);

        mostrar("");
        botonesTransparentes();
        inhabilitar();

        txtIdcompras.setVisible(false);
        txtBoleta.setVisible(false);
        txtFecha.setVisible(false);
        txtHora.setVisible(false);
        txtPrecioTotal.setVisible(false);
        txtIdproveedores.setVisible(false);
        jScrollPane2.setVisible(false);
        jScrollPane3.setVisible(false);
    }

    public void mostrar(String buscar) {
        try {
            //Llenar la tabla
            DefaultTableModel modelo;
            modelo = funcion.mostrar(buscar);
            tblListaCompras.setModel(modelo);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ocultar_columnas() {
        tblListaCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        tblListaCompras.getColumnModel().getColumn(0).setMinWidth(0);
        tblListaCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void inhabilitar() {
        btnEliminar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    public void limpiarTextFields() {
        txtIdcompras.setText("");
        txtBoleta.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtPrecioTotal.setText("");
        txtIdproveedores.setText("");
    }

    public void habilitar() {
        btnEliminar.setEnabled(true);
        btnNuevo.setEnabled(false);
    }

    public void botonesTransparentes() {
        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);

        btnNuevo.setOpaque(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setBorderPainted(false);
    }

    public int fk_Cliente(int buscar) {
        try (Connection cn = DataSource.getConnection()){
            ps = cn.prepareStatement("SELECT fk_clientes FROM ventas WHERE idventas = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();
            while (rs.next()) {
                fk_clientes = rs.getInt("fk_clientes");
            }
            return fk_clientes;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    //Ingresa los detalles de ventas en un JTable
    public void mostrarDetalles(int buscar) {
        try {
            //Llenar la tabla
            modelodet = funcion.mostrarDetalleCompras(buscar);
            tblDetalles.setModel(modelodet);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Ingresa los productos de DetalleVentas en un JTable
    public void mostrarProd(int buscar) {
        try {
            //Llenar la tabla
            modeloprod = funcion.seleccionarListaProductos(buscar);
            tblListaProductos.setModel(modeloprod);
            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private int ingreso(int buscar) {
        try (Connection cn = DataSource.getConnection()){
            ps = cn.prepareStatement("SELECT ingresodia FROM caja WHERE idcaja = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();
            while (rs.next()) {
                ingresodia = rs.getInt("ingresodia");
            }
            return ingresodia;
        } catch (SQLException ex) {
            Logger.getLogger(ListaCompras.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int detallecaja(int buscar) {
        try (Connection cn = DataSource.getConnection()){
            ps = cn.prepareStatement("SELECT iddetallecaja FROM detallecaja WHERE fk_compras = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();
            while (rs.next()) {
                iddetallecaja = rs.getInt("iddetallecaja");
            }
            return iddetallecaja;
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCerrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaCompras = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblFondoBuscar = new javax.swing.JLabel();
        txtIdcompras = new javax.swing.JTextField();
        txtBoleta = new javax.swing.JTextField();
        txtPrecioTotal = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtIdproveedores = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListaProductos = new javax.swing.JTable();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 14, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lista de Compras");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 280, -1));

        tblListaCompras = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblListaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblListaCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListaCompras);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 178, 1120, 430));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Compras...");
        txtBuscar.setBorder(null);
        txtBuscar.setDisabledTextColor(new java.awt.Color(255, 255, 255));
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
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(976, 73, 140, 18));

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 70, -1, -1));
        getContentPane().add(txtIdcompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, -1));
        getContentPane().add(txtBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 50, -1));
        getContentPane().add(txtPrecioTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 50, -1));
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 50, -1));
        getContentPane().add(txtIdproveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 50, -1));
        getContentPane().add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 50, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 120, 40, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar32.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 120, 40, -1));

        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblDetalles);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 320, 90));

        tblListaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblListaProductos);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 320, 90));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoListarProductos.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (tecla == false) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (tecla == false) {
            txtBuscar.setText("Busque algo...");
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
            advertencia();
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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.dispose();

        jDesktopPane1.add(form);
        lblProceso.setText("Proceso: ON");

        form.setClosable(true);
        form.setIconifiable(true);
        try {
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = form.getSize();
            form.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            form.show();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblListaComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaComprasMouseClicked
        seleccionar = tblListaCompras.getSelectedRow();
        txtIdcompras.setText(tblListaCompras.getValueAt(seleccionar, 0).toString());
        txtBoleta.setText(String.valueOf(tblListaCompras.getValueAt(seleccionar, 1)));
        txtFecha.setText(String.valueOf(tblListaCompras.getValueAt(seleccionar, 2)));
        txtHora.setText(String.valueOf(tblListaCompras.getValueAt(seleccionar, 3)));
        txtPrecioTotal.setText(String.valueOf(tblListaCompras.getValueAt(seleccionar, 4)));

        //Se buscan los datos del cliente
        fk_Cliente(Integer.parseInt(txtIdcompras.getText()));
        txtIdproveedores.setText(String.valueOf(fk_clientes));
        mostrarDetalles(Integer.parseInt(txtIdcompras.getText()));

        if (tblDetalles.getRowCount() != 0) {
            mostrarProd(Integer.parseInt(tblDetalles.getValueAt(0, 4).toString()));
        }

        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblListaComprasMouseClicked

    //Metodos para llamar a los JDialog de Advertencia, Fallo y Realizado
    Frame f = JOptionPane.getFrameForComponent(this);
    String encabezado;
    String mensaje;
    Icon icono;

    public void advertencia() {
        Advertencia dialog = new Advertencia(f, true);
        Realizado.lblEncabezado.setText(mensaje);
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
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondoBuscar;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTable tblListaCompras;
    private javax.swing.JTable tblListaProductos;
    public static javax.swing.JTextField txtBoleta;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtHora;
    public static javax.swing.JTextField txtIdcompras;
    public static javax.swing.JTextField txtIdproveedores;
    public static javax.swing.JTextField txtPrecioTotal;
    // End of variables declaration//GEN-END:variables

    public void eliminar() {
        if (txtIdcompras.getText().length() == 0) {
            mensaje = "Debes seleccionar primero una Compra a eliminar.";
            advertencia();
        } else {
            encabezado = "Eliminar permanentemente";
            mensaje = "Esta seguro de eliminar este registro?";
            aceptarCancelar();
            String reply = Principal3.txtAceptarCancelar.getText();

            //Si se selecciono aceptar, se procede a eliminar la compra
            if (reply.equals("1")) {

                //Se compara la fecha de la venta con la fecha actual (Solo se pueden eliminar compras del dia)
                if (txtFecha.getText().equals(Principal3.txtFecha.getText())) {

                    //Si hay detalles de ventas se ejecuta el if, si no, se ejecuta el else para eliminar la venta
                    if (modelodet.getRowCount() != -1) {
                        detallecaja(Integer.parseInt(txtIdcompras.getText()));
                        detallecaja.setIddetallecaja(iddetallecaja);
                        if (funcaja.eliminarDetalles(detallecaja)) {

                            //Actualiza la cantidad de productos
                            for (i = 0; i < tblListaProductos.getRowCount(); i++) {
                                System.out.println("Entro for");

                                //Obtiene los el id y cantidad para poder aumentar la cantidad al producto
                                idd = Integer.parseInt(tblListaProductos.getValueAt(0, 0).toString());
                                cantidad = Integer.parseInt(tblDetalles.getValueAt(0, 2).toString()) + Integer.parseInt(tblListaProductos.getValueAt(0, 4).toString());
                                System.out.println("Cantidad = " + cantidad);

                                //No values especified for parameter 1
                                try (Connection cn = DataSource.getConnection()){
                                    ps = cn.prepareStatement("UPDATE productos SET cantidad = ? WHERE idproductos = ?");
                                    ps.setInt(1, cantidad);
                                    ps.setInt(2, idd);
                                    ps.executeUpdate();
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                    System.out.println("Problema con el try");
                                }
                            }

                            //Actualiza el monto de los ingresos del dia
                            ingreso(Integer.parseInt(Principal3.txtIdcaja.getText()));
                            System.out.println("Ingreso dia = " + ingresodia);
                            ingre = ingresodia - Integer.parseInt(txtPrecioTotal.getText());
                            System.out.println("Ingre = " + ingre);
                            datoscaja.setIngresodia(ingre);
                            datoscaja.setIdcaja(Integer.parseInt(Principal3.txtIdcaja.getText()));
                            if (funcaja.editarIngresoDia(datoscaja)) {
                                //Obtiene el id de detalleservicios para eliminarlo
                                datosdetalle.setIddetallecompra(Integer.parseInt(tblDetalles.getValueAt(0, 0).toString()));
                                if (funcion.eliminarDetalles(datosdetalle)) {

                                    //Obtiene el id de fichaservicios para eliminarlo
                                    datos.setIdcompras(Integer.parseInt(txtIdcompras.getText()));
                                    if (funcion.eliminar(datos)) {
                                        mensaje = "Se elimino la Compra seleccionada";
                                        realizado();

                                        modelodet.removeRow(0);
                                        for (i = 0; i < modeloprod.getRowCount(); i++) {
                                            modeloprod.removeRow(i);
                                        }

                                        String usuario = Principal3.lblUsuario.getText();
                                        String objeto = "Registro N°: "+txtIdcompras.getText();
                                        String Accion = "ELIMINAR";
//                                        audi.audiservicios(usuario, objeto, Accion);

                                        mostrar("");
                                        inhabilitar();
                                        limpiarTextFields();
                                    } else {
                                        mensaje = "Fallo al eliminar";
                                        fallo();
                                    }
                                } else {
                                    mensaje = "Fallo al eliminar DetallServicios";
                                    fallo();
                                }
                            } else {
                                mensaje = "Error editar Ingreso Dia";
                                fallo();
                            }
                        } else {
                            mensaje = "Eliminar Detalles Caja";
                            fallo();
                        }
                    } else {
                        if (funcion.eliminar(datos)) {
                            mensaje = "Se elimino la Compra seleccionada";
                            realizado();

                            String usuario = Principal3.lblUsuario.getText();
                            String objeto = txtIdcompras.getText();
                            String Accion = "ELIMINAR";
//                            audi.audiservicios(usuario, objeto, Accion);

                            mostrar("");
                            inhabilitar();
                            limpiarTextFields();
                        }
                    }
                } else {
                    mensaje = "Solo se pueden eliminar Ventas del dia";
                    fallo();
                }
            }
        }
    }
}
