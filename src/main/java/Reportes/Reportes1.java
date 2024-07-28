package Reportes;

import Config.DataSource;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import java.awt.Frame;
import java.sql.Connection;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author SwichBlade15
 */
public final class Reportes1 extends javax.swing.JInternalFrame {

    public Reportes1() {
        initComponents();
        initComponents();
//        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
//        this.setBackground(new Color(0, 0, 0, 0));
//        this.setIconifiable(false);
//        this.setBorder(null);

        botonesTransparentes();
        validarCheckBox();
    }

    private void validarCheckBox() {
        //CheckBox de Codigo  Categorias
        if (chbCodigoCategorias.isSelected()) {
            chbCategorias.setEnabled(false);
            chbNombreProductos.setEnabled(false);
            chbCodigoProductos.setEnabled(false);
            chbFechaCompra.setEnabled(false);
            chbFechaVencimiento.setEnabled(false);
            chbPrecioCompra.setEnabled(false);
            chbPrecioVenta.setEnabled(false);
            chbFactura.setEnabled(false);
            chbProveedor.setEnabled(false);

            txtDesdeCategorias.setText("");
            txtHastaCategorias.setText("");

            txtDesdeCategorias.setEditable(true);
            txtHastaCategorias.setEditable(true);
        } else if (!chbCodigoCategorias.isSelected()) {
            chbCategorias.setEnabled(true);
            chbNombreProductos.setEnabled(true);
            chbCodigoProductos.setEnabled(true);
            chbFechaCompra.setEnabled(true);
            chbFechaVencimiento.setEnabled(true);
            chbPrecioCompra.setEnabled(true);
            chbPrecioVenta.setEnabled(true);
            chbFactura.setEnabled(true);
            chbProveedor.setEnabled(true);

            txtHastaCategorias.setText("");
            txtDesdeCategorias.setText("");

            txtDesdeCategorias.setEditable(false);
            txtHastaCategorias.setEditable(false);
        }

        //CheckBox Categorias
        if (chbCategorias.isSelected()) {
            chbCodigoCategorias.setEnabled(false);
            chbNombreProductos.setEnabled(false);
            chbCodigoProductos.setEnabled(false);
            chbFechaCompra.setEnabled(false);
            chbFechaVencimiento.setEnabled(false);
            chbPrecioCompra.setEnabled(false);
            chbPrecioVenta.setEnabled(false);
            chbFactura.setEnabled(false);
            chbProveedor.setEnabled(false);

            txtCategorias.setText("");
            txtCategorias.setEditable(true);
        } else if (!chbCategorias.isSelected()) {
            chbCodigoCategorias.setEnabled(true);
            chbNombreProductos.setEnabled(true);
            chbCodigoProductos.setEnabled(true);
            chbFechaCompra.setEnabled(true);
            chbFechaVencimiento.setEnabled(true);
            chbPrecioCompra.setEnabled(true);
            chbPrecioVenta.setEnabled(true);
            chbFactura.setEnabled(true);
            chbProveedor.setEnabled(true);

            txtCategorias.setText("");
            txtCategorias.setEditable(false);
        }

        //CheckBox Productos
        if (chbNombreProductos.isSelected()) {
            chbCategorias.setEnabled(true);
            chbCodigoCategorias.setEnabled(true);
            chbCodigoProductos.setEnabled(true);

            chbFechaCompra.setEnabled(true);
            chbFechaVencimiento.setEnabled(true);
            chbPrecioCompra.setEnabled(true);
            chbPrecioVenta.setEnabled(true);
            chbFactura.setEnabled(true);
            chbProveedor.setEnabled(true);

            txtProductos.setText("");
            txtProductos.setEditable(true);
        } else if (!chbNombreProductos.isSelected()) {
            chbCategorias.setEnabled(true);
            chbCodigoCategorias.setEnabled(true);
            chbNombreProductos.setEnabled(true);
            chbFechaCompra.setEnabled(true);
            chbFechaVencimiento.setEnabled(true);
            chbPrecioCompra.setEnabled(true);
            chbPrecioVenta.setEnabled(true);
            chbFactura.setEnabled(true);
            chbProveedor.setEnabled(true);

            txtProductos.setText("");
            txtProductos.setEditable(false);
        }

        //CheckBox Codigo Productos
        if (chbCodigoProductos.isSelected()) {
            txtDesdeProductos.setText("");
            txtDesdeProductos.setEditable(true);
            txtHastaProductos.setText("");
            txtHastaProductos.setEditable(true);
        } else if (!chbCodigoProductos.isSelected()) {
            txtDesdeProductos.setText("");
            txtDesdeProductos.setEditable(false);
            txtHastaProductos.setText("");
            txtHastaProductos.setEditable(false);
        }

        //CheckBox Fecha Compra
        if (chbFechaCompra.isSelected()) {
            txtFechaCompra.setText("");
            txtFechaCompra.setEditable(true);
        } else if (!chbFechaCompra.isSelected()) {
            txtFechaCompra.setText("");
            txtFechaCompra.setEditable(false);
        }

        //CheckBox Fecha Vencimiento
        if (chbFechaVencimiento.isSelected()) {
            txtFechaVencimiento.setText("");
            txtFechaVencimiento.setEditable(true);
        } else if (!chbFechaVencimiento.isSelected()) {
            txtFechaVencimiento.setText("");
            txtFechaVencimiento.setEditable(false);
        }

        //CheckBox Precio Compra
        if (chbPrecioCompra.isSelected()) {
            txtPrecioCompra.setText("");
            txtPrecioCompra.setEditable(true);
        } else if (!chbPrecioCompra.isSelected()) {
            txtPrecioCompra.setText("");
            txtPrecioCompra.setEditable(false);
        }

        //CheckBox Precio Venta
        if (chbPrecioVenta.isSelected()) {
            txtPrecioVenta.setText("");
            txtPrecioVenta.setEditable(true);
        } else if (!chbPrecioVenta.isSelected()) {
            txtPrecioVenta.setText("");
            txtPrecioVenta.setEditable(false);
        }

        //CheckBox Factura
        if (chbFactura.isSelected()) {
            txtFactura.setText("");
            txtFactura.setEditable(true);
        } else if (!chbFactura.isSelected()) {
            txtFactura.setText("");
            txtFactura.setEditable(false);
        }

        //CheckBox Proveedor
        if (chbProveedor.isSelected()) {
            txtProveedor.setText("");
            txtProveedor.setEditable(true);
        } else if (!chbProveedor.isSelected()) {
            txtProveedor.setText("");
            txtProveedor.setEditable(false);
        }
    }

    public JasperDesign createDesign() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("sampleDynamicJasperDesign");
        jasperDesign.setPageWidth(666); // page width   Oficio
        jasperDesign.setPageHeight(978); // page height Oficio
        jasperDesign.setColumnWidth(515);   // column width of page
        jasperDesign.setColumnSpacing(0);
        jasperDesign.setLeftMargin(40);
        jasperDesign.setRightMargin(40);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        JRDesignExpression expression = new JRDesignExpression();

        //Set style of page.
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("DejaVu Sans");
//        normalStyle.setFontSize(12);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        normalStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(normalStyle);


        /*
        * Generate field dynamically
        * */
        
        JRDesignField field = new JRDesignField();
        field.setName("firstName");
        field.setValueClass(String.class);
        jasperDesign.addField(field);

        field = new JRDesignField();
        field.setName("lastName");  // set name for field.
        field.setValueClass(String.class);  // set class for field. Its always depends upon data type which we want to get in this field.
        jasperDesign.addField(field);   // Added field in design.

        field = new JRDesignField();
        field.setName("age");
        field.setValueClass(Integer.class);
        jasperDesign.addField(field);

        JRDesignBand band;

        //Title Band
        band = new JRDesignBand();
        band.setHeight(30);

        JRDesignStaticText staticText = new JRDesignStaticText();
        staticText.setText("Person's Specification");
        staticText.setX(0);
        staticText.setY(0);
        staticText.setHeight(20);
        staticText.setWidth(515);
//        staticText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
        band.addElement(staticText);
        jasperDesign.setTitle(band);

        band = new JRDesignBand(); // New band
        band.setHeight(20); // Set band height

        /*Create text field dynamically*/
        JRDesignTextField textField = new JRDesignTextField();
        textField.setX(0);  // x position of text field.
        textField.setY(0);  // y position of text field.
        textField.setWidth(160);    // set width of text field.
        textField.setHeight(20);    // set height of text field.
        JRDesignExpression jrExpression = new JRDesignExpression(); // new instanse of expression. We need create new instance always when need to set expression.
        jrExpression.setText("" + "First Name: " + "" + "+" + "$F{firstName}"); //  Added String before field in expression.
        textField.setExpression(jrExpression);  // set expression value in textfield.
        band.addElement(textField); // Added element in textfield.

        textField = new JRDesignTextField();
        textField.setX(160);
        textField.setY(0);
        textField.setWidth(160);
        textField.setHeight(20);
        jrExpression = new JRDesignExpression();
        jrExpression.setText("$F{lastName}" + "+" + "" + " :Last Name" + ""); // Added string after field value
        textField.setExpression(jrExpression);
        band.addElement(textField);

        textField = new JRDesignTextField();
        textField.setX(320);
        textField.setY(0);
        textField.setWidth(160);
        textField.setHeight(20);
        jrExpression = new JRDesignExpression();
        textField.setExpression(jrExpression);
        textField.setMarkup("html"); // By Default markup is none, We need to set it as html if we set expression as html.
        band.addElement(textField);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(band);

        return jasperDesign;
    }

    public void botonesTransparentes() {
//        btnProductos.setOpaque(false);
//        btnProductos.setContentAreaFilled(false);
//        btnProductos.setBorderPainted(false);
//
//        btnAuditoria.setOpaque(false);
//        btnAuditoria.setContentAreaFilled(false);
//        btnAuditoria.setBorderPainted(false);
//
//        btnServicios.setOpaque(false);
//        btnServicios.setContentAreaFilled(false);
//        btnServicios.setBorderPainted(false);
//
//        btnClientes.setOpaque(false);
//        btnClientes.setContentAreaFilled(false);
//        btnClientes.setBorderPainted(false);
//
//        btnVentas.setOpaque(false);
//        btnVentas.setContentAreaFilled(false);
//        btnVentas.setBorderPainted(false);
//
//        btnCompras.setOpaque(false);
//        btnCompras.setContentAreaFilled(false);
//        btnCompras.setBorderPainted(false);
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
        dchHasta = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        dchDesde = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtHastaCategorias = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtCategorias = new javax.swing.JTextField();
        txtDesdeCategorias = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        chbNombreProductos = new javax.swing.JCheckBox();
        chbCodigoCategorias = new javax.swing.JCheckBox();
        chbCodigoProductos = new javax.swing.JCheckBox();
        chbFechaCompra = new javax.swing.JCheckBox();
        chbPrecioCompra = new javax.swing.JCheckBox();
        chbCategorias = new javax.swing.JCheckBox();
        chbPrecioVenta = new javax.swing.JCheckBox();
        chbProveedor = new javax.swing.JCheckBox();
        chbFactura = new javax.swing.JCheckBox();
        chbFechaVencimiento = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        txtFactura = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtFechaCompra = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        txtDesdeProductos = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtProductos = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtFechaVencimiento = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtHastaProductos = new javax.swing.JTextField();
        btnProductos1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setOpaque(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEncabezado.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        lblEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        lblEncabezado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEncabezado.setText("Productos");
        getContentPane().add(lblEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 180, -1));
        getContentPane().add(dchHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 130, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Desde:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        getContentPane().add(dchDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 130, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Hasta:");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, -1, -1));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Hasta:");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));
        jPanel6.add(txtHastaCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 180, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Categoria:");
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        jPanel6.add(txtCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 180, -1));
        jPanel6.add(txtDesdeCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 180, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Desde:");
        jPanel6.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 580, 90));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Buscar por:");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        chbNombreProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbNombreProductos.setText("Nom. Prod.");
        chbNombreProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbNombreProductosActionPerformed(evt);
            }
        });
        getContentPane().add(chbNombreProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, -1));

        chbCodigoCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbCodigoCategorias.setText("Cod. Cat.");
        chbCodigoCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCodigoCategoriasActionPerformed(evt);
            }
        });
        getContentPane().add(chbCodigoCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        chbCodigoProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbCodigoProductos.setText("Cod. Prod.");
        chbCodigoProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCodigoProductosActionPerformed(evt);
            }
        });
        getContentPane().add(chbCodigoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));

        chbFechaCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbFechaCompra.setText("F. Compra");
        chbFechaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbFechaCompraActionPerformed(evt);
            }
        });
        getContentPane().add(chbFechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        chbPrecioCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbPrecioCompra.setText("P. Compra");
        chbPrecioCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPrecioCompraActionPerformed(evt);
            }
        });
        getContentPane().add(chbPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, -1, -1));

        chbCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbCategorias.setText("Categoria");
        chbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCategoriasActionPerformed(evt);
            }
        });
        getContentPane().add(chbCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        chbPrecioVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbPrecioVenta.setText("P. Venta");
        chbPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPrecioVentaActionPerformed(evt);
            }
        });
        getContentPane().add(chbPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, -1, -1));

        chbProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbProveedor.setText("Proveedor");
        chbProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbProveedorActionPerformed(evt);
            }
        });
        getContentPane().add(chbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, -1, -1));

        chbFactura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbFactura.setText("Factura N°");
        chbFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbFacturaActionPerformed(evt);
            }
        });
        getContentPane().add(chbFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, -1, -1));

        chbFechaVencimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chbFechaVencimiento.setText("F. Vencim.");
        chbFechaVencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbFechaVencimientoActionPerformed(evt);
            }
        });
        getContentPane().add(chbFechaVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, -1, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(txtFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 180, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("F. Compra:");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));
        jPanel9.add(txtFechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 180, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Factura N°:");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Proveedor:");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
        jPanel9.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 180, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("P. Compra");
        jPanel9.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));
        jPanel9.add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 180, -1));

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 580, 90));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(txtDesdeProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 180, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Producto:");
        jPanel8.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        jPanel8.add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 180, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Desde: ");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 50, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("P. Venta:");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
        jPanel8.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 180, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("F. Venc.:");
        jPanel8.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));
        jPanel8.add(txtFechaVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 180, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Hasta:");
        jPanel8.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 50, -1));
        jPanel8.add(txtHastaProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 180, -1));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 580, 120));

        btnProductos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonReportes.png"))); // NOI18N
        btnProductos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductos1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, 130, 60));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chbFechaVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbFechaVencimientoActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbFechaVencimientoActionPerformed

    private void chbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCategoriasActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbCategoriasActionPerformed

    private void chbFechaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbFechaCompraActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbFechaCompraActionPerformed

    private void chbCodigoCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCodigoCategoriasActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbCodigoCategoriasActionPerformed

    private void chbNombreProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbNombreProductosActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbNombreProductosActionPerformed

    private void chbCodigoProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCodigoProductosActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbCodigoProductosActionPerformed

    private void chbPrecioCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPrecioCompraActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbPrecioCompraActionPerformed

    private void chbPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPrecioVentaActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbPrecioVentaActionPerformed

    private void chbFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbFacturaActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbFacturaActionPerformed

    private void chbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbProveedorActionPerformed
        validarCheckBox();
    }//GEN-LAST:event_chbProveedorActionPerformed

    private void btnProductos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductos1ActionPerformed
//        Reportes1 form = new Reportes1();
//        jDesktopPane1.add(form);
//        lblProceso.setText("Proceso: ON");
//
//        form.setClosable(true);
//        form.setIconifiable(true);
//        try {
//            Dimension desktopSize = jDesktopPane1.getSize();
//            Dimension FrameSize = form.getSize();
//            form.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
//            form.show();
//        } catch (Exception e) {
//        }
//
//        form.toFront();
//        form.setVisible(true);
    }//GEN-LAST:event_btnProductos1ActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProductos1;
    private javax.swing.JCheckBox chbCategorias;
    private javax.swing.JCheckBox chbCodigoCategorias;
    private javax.swing.JCheckBox chbCodigoProductos;
    private javax.swing.JCheckBox chbFactura;
    private javax.swing.JCheckBox chbFechaCompra;
    private javax.swing.JCheckBox chbFechaVencimiento;
    private javax.swing.JCheckBox chbNombreProductos;
    private javax.swing.JCheckBox chbPrecioCompra;
    private javax.swing.JCheckBox chbPrecioVenta;
    private javax.swing.JCheckBox chbProveedor;
    private com.toedter.calendar.JDateChooser dchDesde;
    private com.toedter.calendar.JDateChooser dchHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblEncabezado;
    private javax.swing.JTextField txtCategorias;
    private javax.swing.JTextField txtDesdeCategorias;
    private javax.swing.JTextField txtDesdeProductos;
    private javax.swing.JTextField txtFactura;
    private javax.swing.JTextField txtFechaCompra;
    private javax.swing.JTextField txtFechaVencimiento;
    private javax.swing.JTextField txtHastaCategorias;
    private javax.swing.JTextField txtHastaProductos;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtProductos;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables
}
