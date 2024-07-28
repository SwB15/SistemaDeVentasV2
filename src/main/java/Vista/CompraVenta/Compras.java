package Vista.CompraVenta;

import Config.DataSource;
import Utils.ControlCantidad;
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
import View.Products.SeleccionarProductosCompras;
import Vista.Proveedores.SeleccionarProveedoresCompras;
import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public final class Compras extends javax.swing.JInternalFrame {

//    FuncionesAuditoria audi = new FuncionesAuditoria();
    FuncionesCompras funcion = new FuncionesCompras();
    DatosCompras datoscompra = new DatosCompras();
    DatosDetalleCompra datosdetalle = new DatosDetalleCompra();
    FuncionesCaja funcaja = new FuncionesCaja();
    DatosCaja datoscaja = new DatosCaja();
    DatosDetalleCaja detallecaja = new DatosDetalleCaja();
    ControlCantidad control = new ControlCantidad();
    ResultSet rs;
    DefaultTableModel modelo;
    int codigo = 0, c = 0, n = 0, totals = 0, cant, can, i, id, cantidad, idd, egresodia;
    String boleta, mismoid, fechaActual, horaActual, sSQL;
    DateFormat hora, fecha;
    Date date = new Date();

    public Compras() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);

        mostrar("");
        numeroBoleta();
        boleta();
        fechaHora();
        comprar();
        botonesTransparentes();
        txtIdproductos.setVisible(false);
        txtIdcompras.setText(String.valueOf(codigo));
        btnEliminar.setEnabled(false);
        btnSeleccionarProducto.setEnabled(false);
        btnAgregarProducto.setEnabled(false);
    }
    
    public void botonesTransparentes() {
        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);

        btnSeleccionarProducto.setOpaque(false);
        btnSeleccionarProducto.setContentAreaFilled(false);
        btnSeleccionarProducto.setBorderPainted(false);

        btnAgregarProducto.setOpaque(false);
        btnAgregarProducto.setContentAreaFilled(false);
        btnAgregarProducto.setBorderPainted(false);

        btnSeleccionarProveedores.setOpaque(false);
        btnSeleccionarProveedores.setContentAreaFilled(false);
        btnSeleccionarProveedores.setBorderPainted(false);
        
        btnCalcular.setOpaque(false);
        btnCalcular.setContentAreaFilled(false);
        btnCalcular.setBorderPainted(false);
        
        btnComprar.setOpaque(false);
        btnComprar.setContentAreaFilled(false);
        btnComprar.setBorderPainted(false);
        
        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);
    }

    public void mostrar(String buscar) {
        modelo = new DefaultTableModel();
        // Agrega fila
        modelo.addColumn("Numero");
        modelo.addColumn("Id");
        modelo.addColumn("Codigo");
        modelo.addColumn("Productos");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Importe");
        tblCompras.setModel(modelo);
    }

    public void comprar() {
        if (txtProveedores.getText().length() != 0 && tblCompras.getRowCount() != 0) {
            btnComprar.setEnabled(true);
        } else {
            btnComprar.setEnabled(false);
        }
    }

    public void ocultar_columnas() {
        tblCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCompras.getColumnModel().getColumn(0).setMinWidth(0);
        tblCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public int numeroBoleta() {
        sSQL = "SELECT MAX(idcompras)AS boleta FROM compras";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo = rs.getInt("boleta");
            }
            return codigo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    public void boleta() {
        if (codigo >= 0 && codigo <= 9) {
            codigo++;
            boleta = "00000" + codigo;
            txtBoleta.setText(String.valueOf(boleta));
        } else if (codigo >= 10 && codigo <= 99) {
            codigo++;
            boleta = "0000" + codigo;
            txtBoleta.setText(String.valueOf(boleta));
        } else if (codigo >= 100 && codigo <= 999) {
            codigo++;
            boleta = "000" + codigo;
            txtBoleta.setText(String.valueOf(boleta));
        } else if (codigo >= 1000 && codigo <= 9999) {
            codigo++;
            boleta = "00" + codigo;
            txtBoleta.setText(String.valueOf(boleta));
        } else if (codigo >= 10000 && codigo <= 99999) {
            codigo++;
            boleta = "0" + codigo;
            txtBoleta.setText(String.valueOf(boleta));
        }
    }

    private void llamarProductos() {
        SeleccionarProductosCompras dialog = new SeleccionarProductosCompras(f, true);
        dialog.setVisible(true);
    }

    private void llamarProveedores() {
        SeleccionarProveedoresCompras dialog = new SeleccionarProveedoresCompras(f, true);
        dialog.setVisible(true);
    }

    public void fechaHora() {
        fecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = fecha.format(date);
        txtFecha.setText(fechaActual);

        hora = new SimpleDateFormat("HH:mm:ss");
        horaActual = hora.format(date);
    }

    private void totalizar() {
        int t = 0;
        int p = 0;
        if (tblCompras.getRowCount() > 0) {
            for (i = 0; i < tblCompras.getRowCount(); i++) {
                p = Integer.parseInt(tblCompras.getValueAt(i, 6).toString());
                t += p;
            }
            txtTotal.setText(String.valueOf(t));
        }
    }

    public boolean recorrerTabla() {
        for (i = 0; i < tblCompras.getRowCount(); i++) {
            String codigoproducto = String.valueOf(tblCompras.getValueAt(i, 2));
            if (codigoproducto.equals(txtCodigoProductos.getText())) {
                return true;
            }
        }
        return false;
    }

    private int egreso() {
        sSQL = "SELECT egresodia FROM caja WHERE idcaja = '%" + Integer.parseInt(Principal3.txtIdcaja.getText()) + "%'";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            rs = st.executeQuery(sSQL);
            while (rs.next()) {
                egresodia = rs.getInt("egresodia");
            }
            return egresodia;
        } catch (SQLException ex) {
            Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public void calcular() {
        int importe = Integer.parseInt(txtSuimporte.getText());
        int monto = Integer.parseInt(txtTotal.getText());
        int vuelto;
        
        if (txtTotal.getText().length() == 0) {
            mensaje = "Ingrese un monto total primero";
            advertencia();
            txtTotal.requestFocus();
        }
        
        if (importe < monto) {
            mensaje = "El monto es mayor al importe";
            advertencia();
            txtImporte.requestFocus();
        }
        
        if (importe <= 0) {
            mensaje = "El importe debe ser mayor a 0";
            advertencia();
            txtImporte.requestFocus();
        }
        
        vuelto = importe - monto;
        txtSuvuelto.setText(String.valueOf(vuelto));
    }
    
    public void nuevaCompra(){
        txtPrecioCompra.setEditable(false);
        txtTotal.setText("00.0");
        
        //Borrar los datos del proveedor
        txtIdproveedores.setText("");
        txtProveedores.setText("");
        txtRuc.setText("");
        txtTelefono.setText("");
        
        //Borrar los datos del producto
        txtIdproductos.setText("");
        txtCodigoProductos.setText("");
        txtProductos.setText("");
        txtPrecioVenta.setText("");
        txtNumero.setText("");
        txtCantidad.setText("");
        txtImporte.setText("");
        txtSuimporte.setText("");
        txtSuvuelto.setText("");
        
        btnSeleccionarProveedores.setEnabled(true);
        btnSeleccionarProducto.setEnabled(false);
        btnAgregarProducto.setEnabled(false);
        btnComprar.setEnabled(false);
        btnEliminar.setEnabled(false);
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
        pnlClientes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIdproveedores = new javax.swing.JTextField();
        txtProveedores = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSeleccionarProveedores = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        pnlProductos = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoProductos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        spnCantidad = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtProductos = new javax.swing.JTextField();
        btnSeleccionarProducto = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        LabelCant = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtFecha = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBoleta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIdcompras = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtIdproductos = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSuimporte = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSuvuelto = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnComprar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setOpaque(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 14, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Compras");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, -1));

        pnlClientes.setBackground(new java.awt.Color(255, 255, 255));
        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Proveedor:");
        pnlClientes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 220, -1));

        txtIdproveedores.setEditable(false);
        txtIdproveedores.setBackground(new java.awt.Color(255, 255, 255));
        txtIdproveedores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtIdproveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 96, -1));

        txtProveedores.setEditable(false);
        txtProveedores.setBackground(new java.awt.Color(255, 255, 255));
        txtProveedores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 220, -1));

        txtRuc.setEditable(false);
        txtRuc.setBackground(new java.awt.Color(255, 255, 255));
        txtRuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 99, -1));

        txtTelefono.setEditable(false);
        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Telefono:");
        pnlClientes.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 110, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("RUC:");
        pnlClientes.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 99, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Id Proveedor:");
        pnlClientes.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));

        btnSeleccionarProveedores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSeleccionarProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirProveedor.png"))); // NOI18N
        btnSeleccionarProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarProveedoresActionPerformed(evt);
            }
        });
        pnlClientes.add(btnSeleccionarProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 150, 50));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra4.png"))); // NOI18N
        pnlClientes.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 70));

        getContentPane().add(pnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 820, 70));

        pnlProductos.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Precio Compra");
        pnlProductos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 66, 105, -1));

        txtCodigoProductos.setEditable(false);
        txtCodigoProductos.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCodigoProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProductosActionPerformed(evt);
            }
        });
        pnlProductos.add(txtCodigoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 33, 120, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cant");
        pnlProductos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 12, 47, -1));

        txtPrecioCompra.setEditable(false);
        txtPrecioCompra.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 87, 105, -1));

        spnCantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 0, 30, 1));
        spnCantidad.setToolTipText("");
        pnlProductos.add(spnCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 33, 47, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Codigo:");
        pnlProductos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 12, 120, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Producto");
        pnlProductos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 12, 233, -1));

        txtProductos.setEditable(false);
        txtProductos.setBackground(new java.awt.Color(255, 255, 255));
        txtProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 33, 233, -1));

        btnSeleccionarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSeleccionarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirProducto.png"))); // NOI18N
        btnSeleccionarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnSeleccionarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 161, 43));

        btnAgregarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonAgregarProducto.png"))); // NOI18N
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 160, 40));

        LabelCant.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LabelCant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCant.setText("Cantidad");
        pnlProductos.add(LabelCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 66, 138, -1));

        txtNumero.setEditable(false);
        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 87, 60, -1));

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 87, 138, -1));

        txtImporte.setEditable(false);
        txtImporte.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 87, 138, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("N°.");
        pnlProductos.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 66, 60, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Importe");
        pnlProductos.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 66, 138, -1));

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 33, 105, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Precio Venta");
        pnlProductos.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 12, 105, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra3.png"))); // NOI18N
        pnlProductos.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 120));

        getContentPane().add(pnlProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 820, 120));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        jPanel2.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 37, 100, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fecha:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 9, 100, -1));

        txtBoleta.setEditable(false);
        txtBoleta.setBackground(new java.awt.Color(255, 255, 255));
        txtBoleta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(txtBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 37, 150, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Boleta N°:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 9, 150, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Id Compras:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 6, 140, 20));

        txtIdcompras.setEditable(false);
        txtIdcompras.setBackground(new java.awt.Color(255, 255, 255));
        txtIdcompras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdcompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdcomprasActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdcompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 37, 140, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra5.png"))); // NOI18N
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 70));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 480, 70));
        getContentPane().add(txtIdproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 22, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Su importe");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 33, 150, -1));

        txtSuimporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(txtSuimporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 61, 150, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Su vuelto");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 192, 150, -1));

        txtSuvuelto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(txtSuvuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 220, 150, -1));

        btnCalcular.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonCalcular.png"))); // NOI18N
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        jPanel1.add(btnCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 119, 140, 43));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoCalcular.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 280));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 170, 280));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnComprar.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnComprar.setForeground(new java.awt.Color(0, 51, 51));
        btnComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonComprar.png"))); // NOI18N
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });
        jPanel3.add(btnComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, 150, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonEliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 57, 150, 31));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Total a Pagar $");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 144, 148, 30));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 255, 204));
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 102, 0));
        txtTotal.setText("00.0");
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        jPanel3.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 180, 148, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra6.png"))); // NOI18N
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 220));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 170, 220));

        tblCompras = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Id", "Codigo", "Productos", "Cantidad", "Precio", "Importe"
            }
        ));
        tblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCompras);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 820, 220));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoCompras.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProductosActionPerformed

    private void txtIdcomprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdcomprasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdcomprasActionPerformed

    private void btnSeleccionarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarProductoActionPerformed
        txtCodigoProductos.setText("");
        txtProductos.setText("");
        txtPrecioCompra.setText("");
        txtNumero.setText("");
        txtCantidad.setText("");
        txtImporte.setText("");
        spnCantidad.setValue(1);
        llamarProductos();
    }//GEN-LAST:event_btnSeleccionarProductoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        if (tblCompras.getRowCount() != 0) {
            if (recorrerTabla() == true) {
                actualizar();
            } else {
                nuevo();
            }
        } else {
            nuevo();
        }
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        i = tblCompras.getSelectedRow();
        if (i == -1) {
            mensaje = "Seleccione una fila a Eliminar";
            advertencia();
        } else {
            String total = txtTotal.getText();
            int tot = Integer.parseInt(total);
            String nums = (String) tblCompras.getValueAt(i, 6);
            int entero = Integer.parseInt(nums);
            totals = tot - entero;
            txtTotal.setText(String.valueOf(totals));

            this.modelo.removeRow(i);
            n = n - 1;
            btnEliminar.setEnabled(false);
            int num = 1;
            for (int w = 0; w < n; w = w + 1) {
                tblCompras.setValueAt(num, w, 0);
                num = num + 1;
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        datoscompra.setBoletanumero(txtBoleta.getText());
        datoscompra.setFecha(txtFecha.getText());
        datoscompra.setHora(horaActual);
        datoscompra.setPreciototal(Integer.parseInt(txtTotal.getText()));

        if (funcion.insertar(datoscompra, funcion.buscarProveedores(String.valueOf(txtProveedores.getText())))) {
            for (i = 0; i < modelo.getRowCount(); i++) {
                String[] registros = new String[2];
                String sql = "SELECT idproductos, cantidad FROM productos WHERE productos.codigo = '" + tblCompras.getValueAt(i, 2).toString() + "'";

                try (Connection cn = DataSource.getConnection()){
                    Statement st = cn.createStatement();
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        registros[0] = rs.getString("idproductos");
                        registros[1] = rs.getString("cantidad");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
                }
                idd = Integer.parseInt(registros[0]);
                int cantidadd = Integer.parseInt(registros[1]);
                cantidad = cantidadd + Integer.parseInt(tblCompras.getValueAt(i, 4).toString());

                datosdetalle.setPrecio(Integer.parseInt(tblCompras.getValueAt(i, 5).toString()));
                datosdetalle.setCantidad(Integer.parseInt(tblCompras.getValueAt(i, 4).toString()));

                if (funcion.insertarDetalleCompra(datosdetalle, funcion.buscarProductos(tblCompras.getValueAt(i, 3).toString()), funcion.buscarCompras(txtBoleta.getText()))) {
                    String ssql = "UPDATE productos SET cantidad = ? WHERE idproductos = ? ";
                    try (Connection cn = DataSource.getConnection()){
                        PreparedStatement pst2 = cn.prepareStatement(ssql);
                        pst2.setInt(1, cantidad);
                        pst2.setInt(2, idd);
                        pst2.executeUpdate();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }

                    //Obtener el engreso acumulado y sumarle el total de la compra realizada
                    egreso();
                    int egre = egresodia + Integer.parseInt(txtTotal.getText());
                    datoscaja.setEgresodia(egre);
                    System.out.println("Egreso dia = "+egre);
                    datoscaja.setIdcaja(Integer.parseInt(Principal3.txtIdcaja.getText()));

                    if (funcaja.insertarDetalleCajaCompras(funcaja.buscarCompras(Integer.parseInt(txtIdcompras.getText())), funcaja.buscarCaja(Integer.parseInt(Principal3.txtIdcaja.getText())))) {
                        if (funcaja.editarEgresoDia(datoscaja)) {
                            mensaje = "Compra realizada";
                            realizado();
                            numeroBoleta();
                            boleta();
                            txtIdcompras.setText(String.valueOf(codigo));
                            
                            for (i = modelo.getRowCount() - 1; i >= 0; i--) {
                                modelo.removeRow(i);
                            }
                            
                            nuevaCompra();
                            
                            String usuario = Principal3.lblUsuario.getText();
                            String objeto = txtBoleta.getText();
                            String Accion = "COMPRAR";
//                            audi.audiclientes(usuario, objeto, Accion);
                        }
                    } else {
                        mensaje = "Edicion de detallecaja";
                        fallo();
                    }
                } else {
                    mensaje = "Edicion de detallecompras";
                    fallo();
                }
            }
        } else {
            mensaje = "Edicion de compras";
            fallo();
        }
    }//GEN-LAST:event_btnComprarActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void tblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComprasMouseClicked
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblComprasMouseClicked

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        control.cont();
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void btnSeleccionarProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarProveedoresActionPerformed
        txtIdproveedores.setText("");
        txtProveedores.setText("");
        txtRuc.setText("");
        txtTelefono.setText("");
        llamarProveedores();
    }//GEN-LAST:event_btnSeleccionarProveedoresActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        calcular();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

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
    private javax.swing.JLabel LabelCant;
    public static javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnComprar;
    private javax.swing.JButton btnEliminar;
    public static javax.swing.JButton btnSeleccionarProducto;
    public static javax.swing.JButton btnSeleccionarProveedores;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlProductos;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTextField txtBoleta;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCodigoProductos;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdcompras;
    public static javax.swing.JTextField txtIdproductos;
    public static javax.swing.JTextField txtIdproveedores;
    public static javax.swing.JTextField txtImporte;
    public static javax.swing.JTextField txtNumero;
    public static javax.swing.JTextField txtPrecioCompra;
    public static javax.swing.JTextField txtPrecioVenta;
    public static javax.swing.JTextField txtProductos;
    public static javax.swing.JTextField txtProveedores;
    public static javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtSuimporte;
    private javax.swing.JTextField txtSuvuelto;
    public static javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    public void nuevo() {
        if (txtPrecioCompra.getText().length() == 0) {
            mensaje = "Ingrese un precio de Compra";
            advertencia();
            txtPrecioCompra.requestFocus();
        } else {
            n = n + 1;
            txtNumero.setText(String.valueOf(n));
            // leer la cantidad pedidas
            cant = (Integer) spnCantidad.getValue();
            //problema de obrtener valor de spinner toca dar vuelta
            txtCantidad.setText(String.valueOf(cant));

            int a = Integer.parseInt(txtPrecioCompra.getText());
            int b = Integer.parseInt(txtCantidad.getText());
            // Calcular la cantidad por valor
            int importe = a * b;
            txtImporte.setText(String.valueOf(importe));

            if (txtIdcompras.getText().length() == 0) {
                txtIdcompras.setText("0");
            }

            can = Integer.parseInt(txtIdcompras.getText());
            can = can + c;

            //Agregar datos a la tabla        
            String datos2[] = new String[7];
            datos2[0] = txtNumero.getText();
            datos2[1] = String.valueOf(can);
            datos2[2] = txtCodigoProductos.getText();
            datos2[3] = txtProductos.getText();
            datos2[4] = txtCantidad.getText();
            datos2[5] = txtPrecioCompra.getText();
            datos2[6] = txtImporte.getText();
            modelo.addRow(datos2);
            tblCompras.setModel(modelo);

            totalizar();

            c++;
            comprar();
        }
    }

    public void actualizar() {
        txtPrecioCompra.setText(String.valueOf(tblCompras.getValueAt(i, 5)));

        String cantidad = String.valueOf(tblCompras.getValueAt(i, 4));
        int cantidad2 = Integer.parseInt(cantidad);
        cant = (Integer) spnCantidad.getValue();
        cant = cant + cantidad2;
        txtCantidad.setText(String.valueOf(cant));

        int a = Integer.parseInt(txtPrecioCompra.getText());
        int b = Integer.parseInt(txtCantidad.getText());
        // Calcular la cantidad por valor
        int importe = a * b;
        txtImporte.setText(String.valueOf(importe));

        if (txtIdcompras.getText().length() == 0) {
            txtIdcompras.setText("0");
        }

        can = Integer.parseInt(txtIdcompras.getText());

        //Agregar datos a la tabla
        modelo.setValueAt(txtCantidad.getText(), i, 4);
        modelo.setValueAt(txtImporte.getText(), i, 6);

        totalizar();
        comprar();
    }
}
