package View.Products;

import Config.DataSource;
import Utils.ControlCantidad;
import Model.DatosProductos;
import Controller.FuncionesAuditoria;
import Controller.FuncionesNotificaciones;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
public final class Productos extends javax.swing.JInternalFrame {

    FuncionesProductos funcion = new FuncionesProductos();
    DatosProductos datos = new DatosProductos();
//    FuncionesAuditoria audi = new FuncionesAuditoria();
    ControlCantidad control = new ControlCantidad();
    FuncionesNotificaciones notificaciones = new FuncionesNotificaciones();
    Date date = null;
    boolean tecla = false;
    int codigo = 0;
    String tipocodigo, presentacion, codigointerno, preciocompra;
    String[] reg = null, reg2 = null;
    int iva = 10;
    byte[] imagen;
    java.sql.Date sql;

    public Productos() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);
        inhabilitar();
        mostrar("");
        botonesTransparentes();

        txtPrecioCompra.setEditable(false);
        txtIdproductos.setVisible(false);
        btnEditarOculto.setVisible(false);
        txtCodigo.setEditable(false);
        rbtn10.doClick();

    }

    public void mostrar(String buscar) {
        try {
            //Llenar la tabla
            DefaultTableModel modelo;
            modelo = funcion.mostrar(buscar);
            tblProductos.setModel(modelo);
            ocultar_columnas();

            //Cargar las categorias al ComboBox
            cmbCategorias.removeAllItems();
            ArrayList<String> listaCat;
            listaCat = funcion.llenar_comboCat();

            for (int i = 0; i < listaCat.size(); i++) {
                cmbCategorias.addItem(listaCat.get(i));
            }

            //Cargar los proveedores al ComboBox
            cmbProveedores.removeAllItems();
            ArrayList<String> listaProv;
            listaProv = funcion.llenar_comboProv();

            for (int i = 0; i < listaProv.size(); i++) {
                cmbProveedores.addItem(listaProv.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void inhabilitar() {
        //Inhabilitar los campos de texto, checkbox y radiobutton
        txtProductos.setEditable(false);
        txtCodigo.setEditable(false);
        rbtnCodigoBarras.setEnabled(false);
        rbtnCodigoInterno.setEnabled(false);
        rbtn0.setSelected(true);
        rbtn0.setEnabled(false);
        rbtn10.setEnabled(false);
        rbtn5.setEnabled(false);
        txtCantidad.setEditable(false);
        txtCantidadMinima.setEditable(false);
        txtPrecioMinorista.setEditable(false);
        txtPrecioMayorista.setEditable(false);
        txtPrecioMinoristaSinIva.setEditable(false);
        txtPrecioMayoristaSinIva.setEditable(false);
        txtIvaMinorista.setEditable(false);
        txtIvaMayorista.setEditable(false);
        txtCantidadXMayor.setEditable(false);
        cmbCategorias.setEnabled(false);
        cmbProveedores.setEnabled(false);
        cmbUnidadMedida.setEnabled(false);
        dchFechaVencimiento.setEnabled(false);

        //Inhabilitar los botones (Solo "btnNuevo" habilitado)
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    private void limpiarTextFields() {
        //Limpiar los campos de texto, checkbox y radiobutton
        txtProductos.setText("");
        txtPrecioCompra.setText("");
        txtCodigo.setText("");
        txtCantidadMinima.setText("");
        txtCantidad.setText("");
        txtPrecioMinorista.setText("");
        txtPrecioMayorista.setText("");
        txtIvaMinorista.setText("");
        txtIvaMayorista.setText("");
        txtPrecioMinoristaSinIva.setText("");
        txtPrecioMayoristaSinIva.setText("");
        dchFechaVencimiento.setCalendar(null);
        rbtnCodigoBarras.doClick();
    }

    public void habilitar() {
        //Habilitar los campos de texto, checkbox y radiobutton
        txtProductos.setEditable(true);
        txtCodigo.setEditable(true);
        rbtnCodigoBarras.setEnabled(true);
        rbtnCodigoInterno.setEnabled(true);
        rbtn0.setEnabled(true);
        rbtn10.setEnabled(true);
        rbtn5.setEnabled(true);
        txtCantidad.setEditable(true);
        txtCantidadMinima.setEditable(true);
        txtPrecioMinorista.setEditable(true);
        txtPrecioMayorista.setEditable(true);
        txtPrecioMinoristaSinIva.setEditable(true);
        txtPrecioMayoristaSinIva.setEditable(true);
        txtIvaMinorista.setEditable(true);
        txtIvaMayorista.setEditable(true);
        cmbCategorias.setEnabled(true);
        cmbProveedores.setEnabled(true);
        cmbUnidadMedida.setEnabled(true);
        dchFechaVencimiento.setEnabled(true);

        //Inhabilitar los botones (Solo "btnNuevo" habilitado)
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnNuevo.setEnabled(false);
    }

    public void ocultar_columnas() {
        tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
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

        btnListar.setOpaque(false);
        btnListar.setContentAreaFilled(false);
        btnListar.setBorderPainted(false);
    }

    public int codigoInterno() {
        String sSQL = "SELECT MAX(idproductos)AS productos FROM productos";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo = rs.getInt("productos");
            }
            return codigo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    private void precioCompra(String buscar) {
        String sql = "SELECT precio FROM detallecompra WHERE fk_productos LIKE '%" + buscar + "%'";
        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                preciocompra = rs.getString("precio");
            }
            txtPrecioCompra.setText(preciocompra);
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
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

        rbtngCodigo = new javax.swing.ButtonGroup();
        rbtngIva = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbProveedores = new javax.swing.JComboBox<>();
        cmbCategorias = new javax.swing.JComboBox<>();
        txtProductos = new javax.swing.JTextField();
        pnlTipoCodigo = new javax.swing.JPanel();
        rbtnCodigoBarras = new javax.swing.JRadioButton();
        rbtnCodigoInterno = new javax.swing.JRadioButton();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtCantidadMinima = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        lblFondoBuscar = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        txtIdproductos = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtPrecioMinoristaSinIva = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPrecioMinorista = new javax.swing.JTextField();
        rbtn10 = new javax.swing.JRadioButton();
        rbtn5 = new javax.swing.JRadioButton();
        rbtn0 = new javax.swing.JRadioButton();
        txtIvaMinorista = new javax.swing.JTextField();
        btnEditarOculto = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtPrecioMayorista = new javax.swing.JTextField();
        cmbUnidadMedida = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtCantidadXMayor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtIvaMayorista = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPrecioMayoristaSinIva = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        dchFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Productos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 280, -1));

        tblProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 1160, 210));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Producto:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 203, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tipo Codigo:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 160, 80, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Categoria:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 120, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Codigo:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 123, 50, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Cantidad Mínima:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, 110, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("P. Compra:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 270, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Proveedores:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, 80, -1));

        cmbProveedores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, 250, -1));

        cmbCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 120, 250, -1));

        txtProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductosActionPerformed(evt);
            }
        });
        txtProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductosKeyTyped(evt);
            }
        });
        getContentPane().add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 270, -1));

        pnlTipoCodigo.setBackground(new java.awt.Color(255, 255, 255));

        rbtnCodigoBarras.setBackground(new java.awt.Color(255, 255, 255));
        rbtngCodigo.add(rbtnCodigoBarras);
        rbtnCodigoBarras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnCodigoBarras.setText("Codigo de Barras");
        rbtnCodigoBarras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbtnCodigoBarrasFocusGained(evt);
            }
        });
        rbtnCodigoBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCodigoBarrasActionPerformed(evt);
            }
        });

        rbtnCodigoInterno.setBackground(new java.awt.Color(255, 255, 255));
        rbtngCodigo.add(rbtnCodigoInterno);
        rbtnCodigoInterno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnCodigoInterno.setText("Codigo Interno");
        rbtnCodigoInterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbtnCodigoInternoFocusGained(evt);
            }
        });
        rbtnCodigoInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCodigoInternoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTipoCodigoLayout = new javax.swing.GroupLayout(pnlTipoCodigo);
        pnlTipoCodigo.setLayout(pnlTipoCodigoLayout);
        pnlTipoCodigoLayout.setHorizontalGroup(
            pnlTipoCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rbtnCodigoBarras)
            .addComponent(rbtnCodigoInterno)
        );
        pnlTipoCodigoLayout.setVerticalGroup(
            pnlTipoCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipoCodigoLayout.createSequentialGroup()
                .addComponent(rbtnCodigoBarras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnCodigoInterno))
        );

        getContentPane().add(pnlTipoCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 130, -1));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 120, -1));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 180, 110, -1));

        txtCantidadMinima.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadMinima.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadMinimaFocusLost(evt);
            }
        });
        txtCantidadMinima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadMinimaActionPerformed(evt);
            }
        });
        txtCantidadMinima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadMinimaKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidadMinima, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 210, 110, -1));

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 102, 255));
        txtBuscar.setText("Buscar Producto...");
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
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1021, 75, 150, 17));

        lblFondoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoBuscador.png"))); // NOI18N
        getContentPane().add(lblFondoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 68, 170, 30));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 14, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 180, 60, -1));

        txtPrecioCompra.setEditable(false);
        txtPrecioCompra.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioCompraActionPerformed(evt);
            }
        });
        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 270, 110, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar32.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 330, 40, -1));
        getContentPane().add(txtIdproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 20, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nuevo32.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 330, 40, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar32.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 330, 40, -1));

        txtPrecioMinoristaSinIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioMinoristaSinIva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioMinoristaSinIvaFocusLost(evt);
            }
        });
        txtPrecioMinoristaSinIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioMinoristaSinIvaKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioMinoristaSinIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 110, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Iva:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 233, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("P. Minorista s/ Iva:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 120, -1));

        txtPrecioMinorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioMinorista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioMinoristaFocusLost(evt);
            }
        });
        txtPrecioMinorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioMinoristaActionPerformed(evt);
            }
        });
        txtPrecioMinorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioMinoristaKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioMinorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 110, -1));

        rbtn10.setBackground(new java.awt.Color(255, 255, 255));
        rbtngIva.add(rbtn10);
        rbtn10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtn10.setText("10%");
        rbtn10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn10ActionPerformed(evt);
            }
        });
        getContentPane().add(rbtn10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, -1));

        rbtn5.setBackground(new java.awt.Color(255, 255, 255));
        rbtngIva.add(rbtn5);
        rbtn5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtn5.setText("5%");
        rbtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn5ActionPerformed(evt);
            }
        });
        getContentPane().add(rbtn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, -1));

        rbtn0.setBackground(new java.awt.Color(255, 255, 255));
        rbtngIva.add(rbtn0);
        rbtn0.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtn0.setText("0%");
        rbtn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn0ActionPerformed(evt);
            }
        });
        getContentPane().add(rbtn0, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

        txtIvaMinorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIvaMinorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIvaMinoristaActionPerformed(evt);
            }
        });
        getContentPane().add(txtIvaMinorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 110, -1));

        btnEditarOculto.setText("EditarOculto");
        btnEditarOculto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarOcultoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditarOculto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Listar32.png"))); // NOI18N
        btnListar.setToolTipText("Listar Productos");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });
        getContentPane().add(btnListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 40, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Monto Iva:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 70, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("P. Minorista:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 80, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("F. Venc.:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("P. Mayorista:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 80, -1));

        txtPrecioMayorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioMayorista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioMayoristaFocusLost(evt);
            }
        });
        txtPrecioMayorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioMayoristaActionPerformed(evt);
            }
        });
        txtPrecioMayorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioMayoristaKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioMayorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 110, -1));

        cmbUnidadMedida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbUnidadMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidades", "Litros", "Paquetes", "Metros", "Kilos" }));
        getContentPane().add(cmbUnidadMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 150, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Cant. x Mayor:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 240, 90, -1));

        txtCantidadXMayor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadXMayor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadXMayorFocusLost(evt);
            }
        });
        txtCantidadXMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadXMayorActionPerformed(evt);
            }
        });
        txtCantidadXMayor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadXMayorKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidadXMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 240, 110, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Monto Iva:");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 70, -1));

        txtIvaMayorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txtIvaMayorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 110, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("P. Mayorista s/ Iva:");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 120, -1));

        txtPrecioMayoristaSinIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioMayoristaSinIva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioMayoristaSinIvaFocusLost(evt);
            }
        });
        txtPrecioMayoristaSinIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioMayoristaSinIvaKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioMayoristaSinIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, 110, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("U. Medida:");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));
        getContentPane().add(dchFechaVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 150, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoProductos2.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnCodigoBarrasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbtnCodigoBarrasFocusGained
        txtCodigo.setEditable(true);
        txtCodigo.setText("");
    }//GEN-LAST:event_rbtnCodigoBarrasFocusGained

    private void rbtnCodigoInternoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbtnCodigoInternoFocusGained
        txtCodigo.setEditable(false);

        codigoInterno();

        if (codigo >= 0 && codigo <= 9) {
            codigo++;
            codigointerno = "00000" + codigo;
            txtCodigo.setText(codigointerno);
        } else if (codigo >= 10 && codigo <= 99) {
            codigo++;
            codigointerno = "0000" + codigo;
            txtCodigo.setText(codigointerno);
        } else if (codigo >= 100 && codigo <= 999) {
            codigo++;
            codigointerno = "000" + codigo;
            txtCodigo.setText(codigointerno);
        } else if (codigo >= 1000 && codigo <= 9999) {
            codigo++;
            codigointerno = "00" + codigo;
            txtCodigo.setText(codigointerno);
        } else if (codigo >= 10000 && codigo <= 99999) {
            codigo++;
            codigointerno = "0" + codigo;
            txtCodigo.setText(codigointerno);
        }
    }//GEN-LAST:event_rbtnCodigoInternoFocusGained

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (tecla == false) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (tecla == false) {
            txtBuscar.setText("Busque un Producto...");
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
        notificaciones.agregar(txtCodigo.getText(), "");
        notificaciones.cont();
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        habilitar();
        rbtnCodigoBarras.setSelected(true);
        rbtn0.setSelected(true);
        txtCodigo.requestFocus();
        txtBuscar.setEditable(false);
        mostrar("");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtIdproductos.getText().length() == 0) {
            guardar();
        } else {
//            auditoria();
            editar();
        }
        control.cantidadMinima();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        this.dispose();

        ListaProductos form = new ListaProductos();
        jDesktopPane1.add(form);
        lblProceso.setText("Proceso: ON");

        try {
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension FrameSize = form.getSize();
            form.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            form.show();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        habilitar();

        int seleccionar = tblProductos.rowAtPoint(evt.getPoint());
        txtIdproductos.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 0)));
        txtProductos.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 1)));
        tipocodigo = String.valueOf(tblProductos.getValueAt(seleccionar, 2));
        if (tipocodigo.equals("Codigo de Barras")) {
            rbtnCodigoBarras.doClick();
        } else {
            rbtnCodigoInterno.doClick();
        }
        precioCompra(String.valueOf(tblProductos.getValueAt(seleccionar, 0)));
        txtCodigo.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 3)));
        txtCantidad.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 4)));
        txtCantidadMinima.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 5)));
        txtCantidadXMayor.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 6)));
        txtPrecioMinorista.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 7)));
        txtPrecioMayorista.setText(String.valueOf(tblProductos.getValueAt(seleccionar, 8)));
        iva = Integer.parseInt(String.valueOf(tblProductos.getValueAt(seleccionar, 9)));
        if (iva == 10) {
            rbtn10.doClick();
        } else {
            rbtn5.doClick();
        }

        //Fecha vencimiento. 
        String fechacierre = String.valueOf(tblProductos.getValueAt(seleccionar, 10));
        if (fechacierre.equals("null")) {
            dchFechaVencimiento.setCalendar(null);
        } else {
            //Convertir java.sql.date a java.util.date y mostrar en pantalla la fecha de inicio
            SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = formato2.parse(fechacierre);
            } catch (ParseException e) {

            }

            //Aplica formato requerido.
            try {
                formato2.applyPattern("dd/MM/yyyy");
                String nuevoFormato2 = formato2.format(date);
                Date fecha2 = formato2.parse(nuevoFormato2);
                dchFechaVencimiento.setDate(fecha2);
            } catch (ParseException ex) {
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Fin Fecha Cierre
        cmbUnidadMedida.setSelectedItem(String.valueOf(tblProductos.getValueAt(seleccionar, 11)));
        cmbCategorias.setSelectedItem(String.valueOf(tblProductos.getValueAt(seleccionar, 12)));
        cmbProveedores.setSelectedItem(String.valueOf(tblProductos.getValueAt(seleccionar, 13)));

        txtBuscar.setEnabled(false);
        txtProductos.requestFocus();
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnEditarOcultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarOcultoActionPerformed
        habilitar();
        txtIdproductos.setText(ListaProductos.txtIdproductos.getText());
        txtProductos.setText(ListaProductos.txtProductos.getText());
        tipocodigo = ListaProductos.txtTipocodigo.getText();
        if (tipocodigo.equals("Codigo Interno")) {
            rbtnCodigoInterno.doClick();
        } else {
            rbtnCodigoBarras.doClick();
        }
        txtCodigo.setText(ListaProductos.txtCodigo.getText());
        txtCantidad.setText(ListaProductos.txtCantidad.getText());
        txtCantidadMinima.setText(ListaProductos.txtCantidadMinima.getText());
        txtPrecioMinorista.setText(ListaProductos.txtPrecio.getText());
        iva = Integer.parseInt(ListaProductos.txtIva.getText());
        if (iva == 10) {
            rbtn10.doClick();
        } else {
            rbtn5.doClick();
        }
        cmbCategorias.setSelectedItem(String.valueOf(ListaProductos.txtCategorias.getText()));
        cmbProveedores.setSelectedItem(String.valueOf(ListaProductos.txtProveedores.getText()));
        precioCompra(String.valueOf(txtIdproductos.getText()));
    }//GEN-LAST:event_btnEditarOcultoActionPerformed

    private void rbtnCodigoBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCodigoBarrasActionPerformed
        tipocodigo = "Codigo de Barras";
        txtCodigo.requestFocus();
    }//GEN-LAST:event_rbtnCodigoBarrasActionPerformed

    private void rbtnCodigoInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCodigoInternoActionPerformed
        tipocodigo = "Codigo Interno";
        txtCodigo.setEditable(false);
        txtProductos.requestFocus();
    }//GEN-LAST:event_rbtnCodigoInternoActionPerformed

    private void rbtn10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn10ActionPerformed
        iva = 10;
        if (txtPrecioMinorista.getText().length() != 0) {
            int preciosiniva = Integer.parseInt(txtPrecioMinorista.getText()) / 11;
            txtIvaMinorista.setText(String.valueOf(preciosiniva));
            int calculoiva = Integer.parseInt(txtPrecioMinorista.getText()) - preciosiniva;
            txtPrecioMinoristaSinIva.setText(String.valueOf(calculoiva));
        }

        if (txtPrecioMayorista.getText().length() != 0) {
            int preciosiniva = Integer.parseInt(txtPrecioMayorista.getText()) / 11;
            txtIvaMayorista.setText(String.valueOf(preciosiniva));
            int calculoiva = Integer.parseInt(txtPrecioMayorista.getText()) - preciosiniva;
            txtPrecioMayoristaSinIva.setText(String.valueOf(calculoiva));
        }
    }//GEN-LAST:event_rbtn10ActionPerformed

    private void rbtn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn5ActionPerformed
        iva = 5;
        if (txtPrecioMinorista.getText().length() != 0) {
            int preciosiniva = Integer.parseInt(txtPrecioMinorista.getText()) / 21;
            txtIvaMinorista.setText(String.valueOf(preciosiniva));
            int calculoiva = Integer.parseInt(txtPrecioMinorista.getText()) - preciosiniva;
            txtPrecioMinoristaSinIva.setText(String.valueOf(calculoiva));
        }

        if (txtPrecioMayorista.getText().length() != 0) {
            int preciosiniva = Integer.parseInt(txtPrecioMayorista.getText()) / 21;
            txtIvaMayorista.setText(String.valueOf(preciosiniva));
            int calculoiva = Integer.parseInt(txtPrecioMayorista.getText()) - preciosiniva;
            txtPrecioMayoristaSinIva.setText(String.valueOf(calculoiva));
        }
    }//GEN-LAST:event_rbtn5ActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        int numerocaracteres = 30;
        if (txtCodigo.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 30 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductosKeyTyped
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

        int numerocaracteres = 70;
        if (txtProductos.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "Solo se permiten 70 caracteres";
            advertencia();
        }
    }//GEN-LAST:event_txtProductosKeyTyped

    private void txtPrecioCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            mensaje = "Ingrese solo numeros";
            advertencia();
            evt.consume();
        }

        int numerocaracteres = 10;
        if (txtProductos.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "El monto es demasiado elevado";
            advertencia();
        }
    }//GEN-LAST:event_txtPrecioCompraKeyTyped

    private void txtPrecioMinoristaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioMinoristaKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            mensaje = "Ingrese solo numeros";
            advertencia();
            evt.consume();
        }

        int numerocaracteres = 10;
        if (txtPrecioMinorista.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "El monto es demasiado elevado";
            advertencia();
        }
    }//GEN-LAST:event_txtPrecioMinoristaKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo numeros";
            advertencia();
        }

        int numerocaracteres = 9;
        if (txtCantidad.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "La cantidad es demasiado elevada";
            advertencia();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadMinimaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadMinimaKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            mensaje = "Ingrese solo numeros";
            advertencia();
        }

        int numerocaracteres = 5;
        if (txtCantidadMinima.getText().length() > numerocaracteres) {
            evt.consume();
            mensaje = "La cantidad minima es demasiado elevada";
            advertencia();
        }
    }//GEN-LAST:event_txtCantidadMinimaKeyTyped

    private void txtPrecioMinoristaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioMinoristaFocusLost
        if (txtPrecioMinorista.getText().length() != 0) {
            if (Integer.parseInt(txtPrecioMinorista.getText()) < 0) {
                mensaje = "No ingrese numeros negativos";
                advertencia();
                txtPrecioMinorista.setText("");
                txtPrecioMinorista.requestFocus();
            }

            if (rbtn10.isSelected()) {
                rbtn10.doClick();
            }

            if (rbtn5.isSelected()) {
                rbtn5.doClick();
            }
        }
    }//GEN-LAST:event_txtPrecioMinoristaFocusLost

    private void rbtn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn0ActionPerformed
        iva = 0;
        txtIvaMinorista.setText("0");
        txtIvaMayorista.setText("0");
    }//GEN-LAST:event_rbtn0ActionPerformed

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        if (txtCantidad.getText().length() != 0) {
            if (Integer.parseInt(txtCantidad.getText()) < 0) {
                mensaje = "No ingrese numeros negativos";
                advertencia();
                txtCantidad.setText("");
                txtCantidad.requestFocus();
            } else {
                txtCantidad.transferFocus();
            }
        }
    }//GEN-LAST:event_txtCantidadFocusLost

    private void txtCantidadMinimaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadMinimaFocusLost

    }//GEN-LAST:event_txtCantidadMinimaFocusLost

    private void txtPrecioCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioCompraActionPerformed

    private void txtProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductosActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPrecioMinoristaSinIvaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioMinoristaSinIvaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMinoristaSinIvaFocusLost

    private void txtPrecioMinoristaSinIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioMinoristaSinIvaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMinoristaSinIvaKeyTyped

    private void txtCantidadMinimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadMinimaActionPerformed
        if (txtCantidadMinima.getText().length() == 0) {
            mensaje = "Ingrese una Cantidad Minima";
            advertencia();
            txtCantidadMinima.requestFocus();
        }

        if (Integer.parseInt(txtCantidadMinima.getText()) < 0) {
            mensaje = "No ingrese numeros negativos";
            advertencia();
            txtCantidadMinima.setText("");
            txtCantidadMinima.requestFocus();
        }

        txtCantidadMinima.transferFocus();
    }//GEN-LAST:event_txtCantidadMinimaActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        mostrar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtPrecioMayoristaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaFocusLost
        if (txtPrecioMayorista.getText().length() != 0) {
            if (Integer.parseInt(txtPrecioMayorista.getText()) < 0) {
                mensaje = "No ingrese numeros negativos";
                advertencia();
                txtPrecioMayorista.setText("");
                txtPrecioMayorista.requestFocus();
            }

            if (rbtn10.isSelected()) {
                rbtn10.doClick();
            }

            if (rbtn5.isSelected()) {
                rbtn5.doClick();
            }
        }
    }//GEN-LAST:event_txtPrecioMayoristaFocusLost

    private void txtPrecioMayoristaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMayoristaKeyTyped

    private void txtCantidadXMayorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadXMayorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadXMayorFocusLost

    private void txtCantidadXMayorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadXMayorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadXMayorActionPerformed

    private void txtCantidadXMayorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadXMayorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadXMayorKeyTyped

    private void txtPrecioMayoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMayoristaActionPerformed

    private void txtPrecioMinoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioMinoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMinoristaActionPerformed

    private void txtIvaMinoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIvaMinoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIvaMinoristaActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        this.transferFocus();
        txtProductos.requestFocus();
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtPrecioMayoristaSinIvaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaSinIvaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMayoristaSinIvaFocusLost

    private void txtPrecioMayoristaSinIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaSinIvaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMayoristaSinIvaKeyTyped

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
        if (txtCodigo.getText().length() == 0) {
            mensaje = "Debes ingresar un Codigo de producto.";
            advertencia();
            txtCodigo.requestFocus();
            return;
        }

        if (txtProductos.getText().length() == 0) {
            mensaje = "Debes ingresar el nombre del producto.";
            advertencia();
            txtProductos.requestFocus();
            return;
        }

        if (txtCantidad.getText().length() == 0) {
            mensaje = "Ingresa una cantidad.";
            advertencia();
            txtCantidad.requestFocus();
            return;
        }

        if (txtCantidadMinima.getText().length() == 0) {
            mensaje = "Debes ingresar una Cantidad Mínima.";
            advertencia();
            txtCantidadMinima.requestFocus();
            return;
        }

        if (cmbCategorias.getSelectedItem() == null) {
            mensaje = "No existen CATEGORIAS en el sistema";
            advertencia();
            this.dispose();
        }

        if (cmbProveedores.getSelectedItem() == null) {
            mensaje = "No existen PROVEEDORES en el sistema";
            advertencia();
            this.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnEditarOculto;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnListar;
    public static javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JComboBox<String> cmbProveedores;
    private javax.swing.JComboBox<String> cmbUnidadMedida;
    private com.toedter.calendar.JDateChooser dchFechaVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondoBuscar;
    private javax.swing.JPanel pnlTipoCodigo;
    private javax.swing.JRadioButton rbtn0;
    private javax.swing.JRadioButton rbtn10;
    private javax.swing.JRadioButton rbtn5;
    private javax.swing.JRadioButton rbtnCodigoBarras;
    private javax.swing.JRadioButton rbtnCodigoInterno;
    private javax.swing.ButtonGroup rbtngCodigo;
    private javax.swing.ButtonGroup rbtngIva;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidadMinima;
    private javax.swing.JTextField txtCantidadXMayor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtIdproductos;
    private javax.swing.JTextField txtIvaMayorista;
    private javax.swing.JTextField txtIvaMinorista;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioMayorista;
    private javax.swing.JTextField txtPrecioMayoristaSinIva;
    private javax.swing.JTextField txtPrecioMinorista;
    private javax.swing.JTextField txtPrecioMinoristaSinIva;
    private javax.swing.JTextField txtProductos;
    // End of variables declaration//GEN-END:variables

    public void guardar() {
        validarCampos();

        datos.setProductos(txtProductos.getText());
        datos.setTipocodigo(tipocodigo);
        datos.setCodigo(txtCodigo.getText());
        datos.setPreciominorista(Integer.parseInt(txtPrecioMinorista.getText()));
        datos.setPreciomayorista(Integer.parseInt(txtPrecioMayorista.getText()));
        datos.setCantidad(Integer.parseInt(txtCantidad.getText()));
        datos.setCantidadminima(Integer.parseInt(txtCantidadMinima.getText()));
        datos.setCantidadxmayor(Integer.parseInt(txtCantidadXMayor.getText()));
        datos.setIva(iva);

        int categoria = cmbCategorias.getSelectedIndex();
        String categoria2 = ((String) cmbCategorias.getItemAt(categoria));

        int proveedores = cmbProveedores.getSelectedIndex();
        String proveedores2 = ((String) cmbProveedores.getItemAt(proveedores));

        int unidadmedida = cmbUnidadMedida.getSelectedIndex();
        String unidadmedida2 = ((String) cmbUnidadMedida.getItemAt(unidadmedida));

        datos.setUnidadmedida(unidadmedida2);

        //Fecha vencimiento. 
        //Convertir Fecha Inicio de java.util.date a java.sql.date
        try {
            date = dchFechaVencimiento.getDate();
            SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
            String tiempo = plantilla.format(date);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(tiempo);
            sql = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }

        datos.setFechavencimiento(sql);

        if (funcion.insertar(datos, funcion.buscarCategoria(categoria2), funcion.buscarProveedor(proveedores2))) {
            mensaje = "Producto ingresado correctamente.";
            realizado();

            String usuario = Principal3.lblUsuario.getText();
            String objeto = txtProductos.getText();
            String Accion = "GUARDAR";
//            audi.audiproductos(usuario, objeto, Accion);

            mostrar("");
            inhabilitar();
            limpiarTextFields();
        } else {
            mensaje = "Ha ocurrido un error al ingresar el Producto";
            fallo();
            mostrar("");
        }
    }

    public void editar() {
        validarCampos();

        datos.setIdproductos(Integer.parseInt(txtIdproductos.getText()));
        datos.setProductos(txtProductos.getText());
        datos.setTipocodigo(tipocodigo);
        datos.setCodigo(txtCodigo.getText());
        datos.setPreciominorista(Integer.parseInt(txtPrecioMinorista.getText()));
        datos.setPreciomayorista(Integer.parseInt(txtPrecioMayorista.getText()));
        datos.setCantidad(Integer.parseInt(txtCantidad.getText()));
        datos.setCantidadminima(Integer.parseInt(txtCantidadMinima.getText()));
        datos.setCantidadxmayor(Integer.parseInt(txtCantidadXMayor.getText()));
        datos.setIva(iva);

        int categoria = cmbCategorias.getSelectedIndex();
        String categoria2 = ((String) cmbCategorias.getItemAt(categoria));

        int proveedores = cmbProveedores.getSelectedIndex();
        String proveedores2 = ((String) cmbProveedores.getItemAt(proveedores));

        int unidadmedida = cmbUnidadMedida.getSelectedIndex();
        String unidadmedida2 = ((String) cmbUnidadMedida.getItemAt(unidadmedida));

        datos.setUnidadmedida(unidadmedida2);

        //Fecha vencimiento. 
        if (dchFechaVencimiento.getCalendar() != null) {
            //Convertir Fecha Inicio de java.util.date a java.sql.date
            try {
                date = dchFechaVencimiento.getDate();
                SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy");
                String tiempo = plantilla.format(date);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date parsed = format.parse(tiempo);
                sql = new java.sql.Date(parsed.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
            }

            datos.setFechavencimiento(sql);
        }

        if (funcion.editar(datos, categoria2, proveedores2)) {
            mensaje = "Producto editado correctamente.";
            realizado();

            String usuario = Principal3.lblUsuario.getText();
            String objeto = "Se modificó: " + Arrays.toString(reg2);
            String Accion = "EDITAR";
//            audi.audiproductos(usuario, objeto, Accion);
            mostrar("");
            inhabilitar();
            limpiarTextFields();
        } else {
            mensaje = "Ha ocurrido un error al editar el Producto.";
            fallo();
            mostrar("");
        }
    }

    public void eliminar() {
        if (txtProductos.getText().length() == 0) {
            mensaje = "Debes seleccionar primero un Producto a eliminar.";
            advertencia();
            txtProductos.requestFocus();
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

                    inhabilitar();
                    limpiarTextFields();
                    String usuario = Principal3.lblUsuario.getText();
                    String objeto = txtProductos.getText();
                    String Accion = "ELIMINAR";
//                    audi.audiproductos(usuario, objeto, Accion);

                    mostrar("");
                } else {
                    mensaje = "El Producto no ha sido borrado";
                    fallo();
                    mostrar("");
                }
            }
        }
    }
}
