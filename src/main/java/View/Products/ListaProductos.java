package View.Products;

import Config.DataSource;
import Model.DatosProductos;
import Controller.FuncionesAuditoria;
import Controller.FuncionesProductos;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class ListaProductos extends javax.swing.JInternalFrame {

    Productos form = new Productos();
    FuncionesProductos funcion = new FuncionesProductos();
    DatosProductos datos = new DatosProductos();
    public boolean tecla = false;
    DefaultTableModel model;
    Statement st;
    int fk_categorias;

    public ListaProductos() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);

        mostrar("");
        llenarTabla();
        botonesTransparentes();

        txtIdproductos.setVisible(false);
        txtProductos.setVisible(false);
        txtTipocodigo.setVisible(false);
        txtCodigo.setVisible(false);
        txtCantidad.setVisible(false);
        txtCantidadMinima.setVisible(false);
        txtPrecio.setVisible(false);
        txtIva.setVisible(false);
        txtDescuento.setVisible(false);
        txtCategorias.setVisible(false);
        txtProveedores.setVisible(false);
        
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    public void mostrar(String buscar) {
        try {
            cmbCategorias.removeAllItems();
            ArrayList<String> listaCat = new ArrayList<String>();
            listaCat = funcion.llenar_comboCat();
            
            cmbCategorias.addItem("TODOS");
            for (int i = 0; i < listaCat.size(); i++) {
                cmbCategorias.addItem(listaCat.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void llenarTabla() {   
        try (Connection cn = DataSource.getConnection()){
            String[] titulos = {"ID", "Productos", "Tipo Codigo", "Codigo", "Cantidad", "Cantidad Minima", "Precio", "Iva", "Descuento", "Categorias", "Proveedores"};
            String stsql = "SELECT idproductos, productos, tipocodigo, codigo, cantidad, cantidadminima, precio, iva, descuento, (SELECT categorias FROM categorias WHERE idcategorias = fk_categorias) AS categorias, (SELECT proveedores FROM proveedores WHERE idproveedores = fk_proveedores) AS proveedores FROM productos WHERE productos IS NOT NULL";

            if(!"TODOS".equals(cmbCategorias.getSelectedItem().toString())){
                stsql = stsql + "AND fk_categorias LIKE '" + funcion.buscarCategoria(cmbCategorias.getSelectedItem().toString()) + "'";
            }
            
            model = new DefaultTableModel(null, titulos);
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(stsql);
            String[] fila = new String[11];
            while (rs.next()) {
                fila[0] = rs.getString("idproductos");
                fila[1] = rs.getString("productos");
                fila[2] = rs.getString("tipocodigo");
                fila[3] = rs.getString("codigo");
                fila[4] = rs.getString("cantidad");
                fila[5] = rs.getString("cantidadminima");
                fila[6] = rs.getString("precio");
                fila[7] = rs.getString("iva");
                fila[8] = rs.getString("descuento");
                fila[9] = rs.getString("categorias");
                fila[10] = rs.getString("proveedores");
                model.addRow(fila);
            }
            tblListaProductos.setModel(model);
            ocultar_columnas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ocultar_columnas() {
        tblListaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblListaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tblListaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void botonesTransparentes() {
        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);

        btnEditar.setOpaque(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setBorderPainted(false);

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

        lblCerrar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        txtProductos = new javax.swing.JTextField();
        txtTipocodigo = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtCantidadMinima = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtCategorias = new javax.swing.JTextField();
        txtProveedores = new javax.swing.JTextField();
        txtIdproductos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaProductos = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblFondoBuscar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        lblFondo = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1105, 14, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lista de Productos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 280, -1));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar32.png"))); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 160, 40, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar32.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 160, 40, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 160, 40, -1));
        getContentPane().add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 70, -1));
        getContentPane().add(txtTipocodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 70, -1));
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 70, -1));
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 70, -1));
        getContentPane().add(txtCantidadMinima, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 70, -1));
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 70, -1));
        getContentPane().add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 70, -1));
        getContentPane().add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 70, -1));
        getContentPane().add(txtCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, 70, -1));
        getContentPane().add(txtProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 70, -1));
        getContentPane().add(txtIdproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 70, -1));

        tblListaProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblListaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblListaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListaProductos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 224, 1110, 380));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Producto...");
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
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(971, 73, 148, 18));

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 70, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Categorias:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 113, -1, -1));

        cmbCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategorias.setSelectedIndex(-1);
        cmbCategorias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriasItemStateChanged(evt);
            }
        });
        getContentPane().add(cmbCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 450, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoListarProductos.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        getContentPane().add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
            mensaje = "Ingrese solo el NOMBRE del Producto";
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

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        dispose();

        Productos.btnEditarOculto.doClick();
        jDesktopPane1.add(form);

        form.setClosable(true);
        form.setIconifiable(true);
        try {
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = form.getSize();
            form.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            form.show();
        } catch (Exception e) {
        }

        form.toFront();
        form.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.dispose();

        Productos form2 = new Productos();
        jDesktopPane1.add(form2);
        Productos.btnNuevo.doClick();
        lblProceso.setText("Proceso: ON");

        form2.setClosable(true);
        form2.setIconifiable(true);
        try {
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = form2.getSize();
            form2.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            form2.show();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tblListaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaProductosMouseClicked
        int seleccionar = tblListaProductos.rowAtPoint(evt.getPoint());
        txtIdproductos.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 0)));
        txtProductos.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 1)));
        txtTipocodigo.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 2)));
        txtCodigo.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 3)));
        txtCantidad.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 4)));
        txtCantidadMinima.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 5)));
        txtPrecio.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 6)));
        txtIva.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 7)));
        txtDescuento.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 8)));
        txtCategorias.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 9)));
        txtProveedores.setText(String.valueOf(tblListaProductos.getValueAt(seleccionar, 10)));

        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblListaProductosMouseClicked

    private void cmbCategoriasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriasItemStateChanged
        llenarTabla();
    }//GEN-LAST:event_cmbCategoriasItemStateChanged

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondoBuscar;
    public static javax.swing.JLabel lblImagen;
    private javax.swing.JTable tblListaProductos;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCantidadMinima;
    public static javax.swing.JTextField txtCategorias;
    public static javax.swing.JTextField txtCodigo;
    public static javax.swing.JTextField txtDescuento;
    public static javax.swing.JTextField txtIdproductos;
    public static javax.swing.JTextField txtIva;
    public static javax.swing.JTextField txtPrecio;
    public static javax.swing.JTextField txtProductos;
    public static javax.swing.JTextField txtProveedores;
    public static javax.swing.JTextField txtTipocodigo;
    // End of variables declaration//GEN-END:variables

    public void eliminar() {
        if (txtProductos.getText().length() == 0) {
            mensaje = "Debes seleccionar primero un Producto a eliminar.";
            advertencia();
        } else {
            encabezado = "Eliminar permanentemente";
            mensaje = "Esta seguro de eliminar este registro?";
            aceptarCancelar();
            String reply = Principal3.txtAceptarCancelar.getText();
            if (reply.equals("1")) {

                datos.setIdproductos(Integer.parseInt(txtIdproductos.getText()));

                if (funcion.eliminar(datos)) {
                    mensaje = "Se elimino el Producto seleccionado";
                    realizado();

                    String usuario = Principal3.lblUsuario.getText();
                    String objeto = txtProductos.getText();
                    String Accion = "ELIMINAR";
//                    audi.audiproductos(usuario, objeto, Accion);

                    llenarTabla();
                } else {
                    mensaje = "El Producto no ha sido borrado";
                    fallo();
                    llenarTabla();
                }
            }
        }
    }
}
