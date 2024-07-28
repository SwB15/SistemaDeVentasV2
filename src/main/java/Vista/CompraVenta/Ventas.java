package Vista.CompraVenta;

import Config.DataSource;
import Utils.ControlCantidad;
import Model.DatosCaja;
import Model.DatosDetalleCaja;
import Model.DatosDetalleVentas;
import Model.DatosVentas;
import Controller.FuncionesAuditoria;
import Controller.FuncionesCaja;
import Controller.FuncionesVentas;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Realizado;
import Vista.Personas.SeleccionarClientesVentas;
import View.Principal3;
import View.Products.SeleccionarProductosVentas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public final class Ventas extends javax.swing.JInternalFrame {

//    FuncionesAuditoria audi = new FuncionesAuditoria();
    FuncionesVentas funcion = new FuncionesVentas();
    DatosVentas datosventas = new DatosVentas();
    DatosDetalleVentas datosdetalle = new DatosDetalleVentas();
    FuncionesCaja funcaja = new FuncionesCaja();
    DatosCaja datoscaja = new DatosCaja();
    DatosDetalleCaja detallecaja = new DatosDetalleCaja();
    ControlCantidad control = new ControlCantidad();
    DefaultTableModel modelo, modeloticket;
    ResultSet rs;
    int codigo = 0, c = 0, n, totals = 0, cant, can, i, idd, cantidad, ingresodia, seleccionar;
    String boleta, sSQL;
    String[] ticket = new String[4];
    DateFormat hora, fecha;
    Date date = new Date();
    String fechaActual, horaActual, text;
    public String cli, pro;
    int indice = 0, columncount;
    String ruta;

    public Ventas() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);

        mostrar("");
        numeroBoleta();
        boleta();
        txtBoleta.setText(String.valueOf(boleta));
        fechaHora();
        vender();
        botonesTransparentes();

        txtIdproductos.setVisible(false);
        txtIdventas.setText(String.valueOf(codigo));
        btnEliminar.setEnabled(false);
        btnAgregarProducto.setEnabled(false);
        btnGuardarFacturas.setEnabled(false);
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

        btnSeleccionarCliente.setOpaque(false);
        btnSeleccionarCliente.setContentAreaFilled(false);
        btnSeleccionarCliente.setBorderPainted(false);

        btnCalcular.setOpaque(false);
        btnCalcular.setContentAreaFilled(false);
        btnCalcular.setBorderPainted(false);

        btnVender.setOpaque(false);
        btnVender.setContentAreaFilled(false);
        btnVender.setBorderPainted(false);

        btnEliminar.setOpaque(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);

        btnGuardarFacturas.setOpaque(false);
        btnGuardarFacturas.setContentAreaFilled(false);
        btnGuardarFacturas.setBorderPainted(false);
    }

    public void mostrar(String buscar) {
        modelo = new DefaultTableModel();
        // Agrega columnas
        modelo.addColumn("Numero");
        modelo.addColumn("Id");
        modelo.addColumn("Codigo");
        modelo.addColumn("Productos");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Importe");
        tblVentas.setModel(modelo);
    }

    public void modeloTicket() {
        modeloticket = new DefaultTableModel();
        // Agrega columnas
        modeloticket.addColumn("Productos");
        modeloticket.addColumn("Cantidad");
        modeloticket.addColumn("Precio");
        modeloticket.addColumn("Importe");
    }

    public void vender() {
        if (txtNombre.getText().length() != 0 && tblVentas.getRowCount() != 0) {
            btnVender.setEnabled(true);
        } else {
            btnVender.setEnabled(false);
        }
    }

    public void ocultar_columnas() {
        tblVentas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblVentas.getColumnModel().getColumn(0).setMinWidth(0);
        tblVentas.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public int numeroBoleta() {
        sSQL = "SELECT MAX(idventas)AS boleta FROM ventas";

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
        } else if (codigo > 9 && codigo < 100) {
            codigo++;
            boleta = "0000" + codigo;
        } else if (codigo >= 100 && codigo <= 999) {
            codigo++;
            boleta = "000" + codigo;
        } else if (codigo >= 1000 && codigo <= 9999) {
            codigo++;
            boleta = "00" + codigo;
        } else if (codigo >= 10000 && codigo <= 99999) {
            codigo++;
            boleta = "0" + codigo;
        }
    }

    private void llamarProductos() {
        SeleccionarProductosVentas dialog = new SeleccionarProductosVentas(f, true);
        dialog.setVisible(true);
    }

    private void llamarCliente() {
        SeleccionarClientesVentas dialog = new SeleccionarClientesVentas(f, true);
        dialog.setVisible(true);
    }

    public boolean recorrerTabla() {
        for (i = 0; i < tblVentas.getRowCount(); i++) {
            String codigoproducto = String.valueOf(tblVentas.getValueAt(i, 2));
            if (codigoproducto.equals(txtCodigoProductos.getText())) {
                return true;
            }
        }
        return false;
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
        if (tblVentas.getRowCount() > 0) {
            for (i = 0; i < tblVentas.getRowCount(); i++) {
                p = Integer.parseInt(tblVentas.getValueAt(i, 6).toString());
                t += p;
            }
            txtTotal.setText(String.valueOf(t));
        }
    }

    private int ingreso() {
        sSQL = "SELECT ingresodia FROM caja WHERE idcaja = '%" + Integer.parseInt(Principal3.txtIdcaja.getText()) + "%'";

        try (Connection cn = DataSource.getConnection()){
            Statement st = cn.createStatement();
            rs = st.executeQuery(sSQL);
            while (rs.next()) {
                ingresodia = rs.getInt("ingresodia");
            }
            return ingresodia;
        } catch (SQLException ex) {
            Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

//    public boolean generarFactura() {
//        // Crear el documento con un tamaño personalizado de rollos de hojas 75x65mm
//        // 1mm = 28.3f
//        Rectangle rectangle = new Rectangle(212.25f, 1000f);
//        Document document = new Document(rectangle);
//        document.setMargins(14.15f, 14.15f, 28.3f, 8f); //(izq, der, arriba, abajo)
//
//        try {
//            FileOutputStream ficheroPdf = new FileOutputStream(ruta + "Venta " + txtBoleta.getText() + ".pdf");
//            PdfWriter writer = PdfWriter.getInstance(document, ficheroPdf);
//            document.open();
//            Font fuente = new Font();
//            fuente.setSize(7f);
//
//            //Fuente para la tabla
//            Font fuente2 = new Font();
//            fuente2.setSize(6f);
//
//            //Crear la tabla para los productos
//            PdfPTable table = new PdfPTable(new float[]{63f, 20f, 27.3f, 31f});
//            table.setWidthPercentage(100);
//
//            //Llenar la tabla del ticket con los productos comprados
//            while (modeloticket.getRowCount() != 0) {
//                for (i = 0; i < modeloticket.getRowCount(); i++) {
//
//                    //Añadir columna productos
//                    PdfPCell productosCell = new PdfPCell(new Phrase(modeloticket.getValueAt(0, 0).toString(), fuente2));
//                    productosCell.setBorder(0);
//                    productosCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    table.addCell(productosCell);
//
//                    //Añadir columna cantidad
//                    PdfPCell cantidadCell = new PdfPCell(new Phrase(modeloticket.getValueAt(0, 1).toString(), fuente2));
//                    cantidadCell.setBorder(0);
//                    cantidadCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(cantidadCell);
//
//                    //Añadir columna precio unitario
//                    PdfPCell unitarioCell = new PdfPCell(new Phrase(modeloticket.getValueAt(0, 2).toString(), fuente2));
//                    unitarioCell.setBorder(0);
//                    unitarioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(unitarioCell);
//
//                    //Añadir columna importe
//                    PdfPCell importeCell = new PdfPCell(new Phrase(modeloticket.getValueAt(0, 3).toString(), fuente2));
//                    importeCell.setBorder(0);
//                    importeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(importeCell);
//
//                    //Elimina el ultimo registro ingresado para poder dar paso al ingreso del siguiente
//                    modeloticket.removeRow(0);
//                }
//            }
//
//            //Añadir los datos al documento
//            document.add(new Paragraph("SISTEMA DE VENTAS", fuente));
//            document.add(new Paragraph("RUC: 80002424-1  -  Tel.: 021485760", fuente));
//            document.add(new Paragraph("Direccion: Ever Dario Soto e/ Coronel Alfredo Ramos y Monseñor Rojas", fuente));
//            document.add(new Paragraph("Ticket N°: " + txtBoleta.getText(), fuente));
//            document.add(new Paragraph("Fecha: " + txtFecha.getText(), fuente));
//            document.add(new Paragraph("Cliente: " + txtNombre.getText() + " " + txtApellido.getText(), fuente));
//            document.add(new Paragraph("RUC: " + txtCedula.getText(), fuente));
//            document.add(new Paragraph("Atendio por : " + Principal.lblUsuario.getText(), fuente));
//            document.add(new Paragraph(" ", fuente));
//            document.add(new Paragraph("--------------------------------------------------------------------------------------------", fuente2));
//            document.add(new Paragraph("Productos                                 |   Cant.   |    Unitario    |     Importe     |", fuente2));
//            document.add(new Paragraph("--------------------------------------------------------------------------------------------", fuente2));
//            document.add(new Paragraph("", fuente));
//            // Agregar la tabla al documento
//            document.add(table);
//
//            //Agrega el total a pagar
//            document.add(new Paragraph("--------------------------------------------------------------------------------------------", fuente2));
//            document.add(new Paragraph("TOTAL A PAGAR                                                                 " + txtTotal.getText(), fuente2));
//            document.add(new Paragraph("--------------------------------------------------------------------------------------------", fuente2));
//            document.add(new Paragraph(" ", fuente2));
//            document.add(new Paragraph("Gracias por su preferencia", fuente2));
//            document.add(new Paragraph("Vuelva pronto", fuente2));
//
//            document.close();
//
//            return true;
//        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
//            e.printStackTrace();
//            mensaje = "Error al generar el PDF";
//            fallo();
//            return false;
//        }
//    }

    public void visualizar(String buscar) {
        try {
            File path = new File(ruta + "Venta " + buscar + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
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

    public void nuevaVenta() {
        txtTotal.setText("00.0");

        //Borrar los datos del cliente
        txtIdcliente.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");

        //Borrar los datos del producto
        txtCodigoProductos.setText("");
        txtIdproductos.setText("");
        txtProductos.setText("");
        txtNumero.setText("");
        txtCantidad.setText("");
        txtImporte.setText("");
        txtSuimporte.setText("");
        txtSuvuelto.setText("");

        btnSeleccionarCliente.setEnabled(true);
        btnSeleccionarProducto.setEnabled(true);
        btnAgregarProducto.setEnabled(false);
        btnVender.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardarFacturas.setEnabled(false);
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
        pnlClientes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIdcliente = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSeleccionarCliente = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBoleta = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIdventas = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        pnlProductos = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoProductos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
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
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnVender = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardarFacturas = new javax.swing.JToggleButton();
        jLabel19 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtIdproductos = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSuimporte = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSuvuelto = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setOpaque(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ventas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, -1));

        pnlClientes.setBackground(new java.awt.Color(255, 255, 255));
        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nombre:");
        pnlClientes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 132, -1));

        txtIdcliente.setEditable(false);
        txtIdcliente.setBackground(new java.awt.Color(255, 255, 255));
        txtIdcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtIdcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 96, -1));

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 132, -1));

        txtApellido.setEditable(false);
        txtApellido.setBackground(new java.awt.Color(255, 255, 255));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 201, -1));

        txtCedula.setEditable(false);
        txtCedula.setBackground(new java.awt.Color(255, 255, 255));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Cedula:");
        pnlClientes.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 110, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Apellido:");
        pnlClientes.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 201, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Id Cliente:");
        pnlClientes.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 93, -1));

        btnSeleccionarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSeleccionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirCliente.png"))); // NOI18N
        btnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarClienteActionPerformed(evt);
            }
        });
        pnlClientes.add(btnSeleccionarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 150, 50));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra4.png"))); // NOI18N
        pnlClientes.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 70));

        getContentPane().add(pnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 820, 70));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 14, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Boleta N°:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 12, -1, 20));

        txtBoleta.setEditable(false);
        txtBoleta.setBackground(new java.awt.Color(255, 255, 255));
        txtBoleta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel3.add(txtBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 12, 110, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Fecha:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 12, -1, 20));

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        jPanel3.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, 115, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Id Ventas:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(452, 12, -1, 20));

        txtIdventas.setEditable(false);
        txtIdventas.setBackground(new java.awt.Color(255, 255, 255));
        txtIdventas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdventasActionPerformed(evt);
            }
        });
        jPanel3.add(txtIdventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 12, 110, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra1.png"))); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 700, 50));

        pnlProductos.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Precio");
        pnlProductos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 12, 105, -1));

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
        pnlProductos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(529, 12, 47, -1));

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 33, 105, -1));

        spnCantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 0, 30, 1));
        spnCantidad.setToolTipText("");
        pnlProductos.add(spnCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(529, 33, 47, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Codigo:");
        pnlProductos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 12, 120, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Producto");
        pnlProductos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 12, 235, -1));

        txtProductos.setEditable(false);
        txtProductos.setBackground(new java.awt.Color(255, 255, 255));
        txtProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 33, 235, -1));

        btnSeleccionarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSeleccionarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirProducto.png"))); // NOI18N
        btnSeleccionarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnSeleccionarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 13, 160, 40));

        btnAgregarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonAgregarProducto.png"))); // NOI18N
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 160, 40));

        LabelCant.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LabelCant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCant.setText("Cantidad");
        pnlProductos.add(LabelCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 78, 138, -1));

        txtNumero.setEditable(false);
        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 99, 60, -1));

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 99, 138, -1));

        txtImporte.setEditable(false);
        txtImporte.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 99, 138, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("N°.");
        pnlProductos.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 78, 60, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Importe");
        pnlProductos.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 78, 138, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra2.png"))); // NOI18N
        pnlProductos.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 130));

        getContentPane().add(pnlProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 820, 130));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVender.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnVender.setForeground(new java.awt.Color(0, 51, 51));
        btnVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonVender.png"))); // NOI18N
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });
        jPanel2.add(btnVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, 150, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonEliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 57, 150, 31));

        btnGuardarFacturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonGuardarFacturas.png"))); // NOI18N
        btnGuardarFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarFacturasActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardarFacturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 92, 150, 29));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Total a Pagar $");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 130, 30));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 255, 204));
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 102, 0));
        txtTotal.setText("00.0");
        jPanel2.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 150, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoExtra6.png"))); // NOI18N
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 220));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 170, 220));
        getContentPane().add(txtIdproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(787, 28, 22, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Su importe");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 33, 148, -1));

        txtSuimporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSuimporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSuimporteActionPerformed(evt);
            }
        });
        jPanel1.add(txtSuimporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 61, 148, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Su vuelto");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 192, 148, -1));

        txtSuvuelto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(txtSuvuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 220, 148, -1));

        btnCalcular.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonCalcular.png"))); // NOI18N
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        jPanel1.add(btnCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 43));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoCalcular.png"))); // NOI18N
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 280));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 170, 280));

        tblVentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Id", "Codigo", "Productos", "Cantidad", "Precio", "Importe"
            }
        ));
        jScrollPane1.setViewportView(tblVentas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 820, 220));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoCompras.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarClienteActionPerformed
        llamarCliente();
        vender();
    }//GEN-LAST:event_btnSeleccionarClienteActionPerformed

    private void txtCodigoProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProductosActionPerformed

    private void txtIdventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdventasActionPerformed

    private void btnSeleccionarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarProductoActionPerformed
        llamarProductos();
    }//GEN-LAST:event_btnSeleccionarProductoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        if (tblVentas.getRowCount() != 0) {
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
        i = tblVentas.getSelectedRow();
        if (i == -1) {
            mensaje = "Seleccione una fila a Eliminar";
            advertencia();
        } else {
            String total = txtTotal.getText();
            int tot = Integer.parseInt(total);
            String nums = (String) tblVentas.getValueAt(i, 6);
            int entero = Integer.parseInt(nums);
            totals = tot - entero;
            txtTotal.setText(String.valueOf(totals));

            this.modelo.removeRow(i);
            n = n - 1;
            btnEliminar.setEnabled(false);
            int num = 1;
            for (int w = 0; w < n; w = w + 1) {
                tblVentas.setValueAt(num, w, 0);
                num = num + 1;
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        datosventas.setBoleta(txtBoleta.getText());
        datosventas.setFecha(txtFecha.getText());
        datosventas.setHora(horaActual);
        datosventas.setPreciototal(Integer.parseInt(txtTotal.getText()));

        //Genera un DefaultTableModel para cargar luego los productos al ticket
        modeloTicket();

        if (funcion.insertarVentas(datosventas, funcion.buscarClientes(String.valueOf(txtCedula.getText())))) {
            while (modelo.getRowCount() != 0) {
                for (i = 0; i < modelo.getRowCount(); i++) {
                    String[] registros = new String[2];
                    String sql = "SELECT idproductos, cantidad FROM productos WHERE productos.codigo = '" + tblVentas.getValueAt(i, 2).toString() + "'";

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
                    cantidad = cantidadd - Integer.parseInt(tblVentas.getValueAt(i, 4).toString());

                    datosdetalle.setPrecio(Integer.parseInt(tblVentas.getValueAt(i, 5).toString()));
                    datosdetalle.setCantidad(Integer.parseInt(tblVentas.getValueAt(i, 4).toString()));

                    if (funcion.insertarDetalleVentas(datosdetalle, funcion.buscarProductos(tblVentas.getValueAt(i, 3).toString()), funcion.buscarVentas(txtBoleta.getText()))) {
                        String ssql = "UPDATE productos SET cantidad = ? WHERE idproductos = ? ";
                        try (Connection cn = DataSource.getConnection()){
                            PreparedStatement pst2 = cn.prepareStatement(ssql);
                            pst2.setInt(1, cantidad);
                            pst2.setInt(2, idd);
                            pst2.executeUpdate();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        //Obtener el engreso acumulado y sumarle el total de la venta realizada
                        ingreso();
                        int ingre = ingresodia + Integer.parseInt(txtTotal.getText());
                        datoscaja.setIngresodia(ingre);
                        datoscaja.setIdcaja(Integer.parseInt(Principal3.txtIdcaja.getText()));

                        if (funcaja.insertarDetalleCajaVentas(funcaja.buscarVentas(Integer.parseInt(txtIdventas.getText())), funcaja.buscarCaja(Integer.parseInt(Principal3.txtIdcaja.getText())))) {
                            if (funcaja.editarIngresoDia(datoscaja)) {

                                //Agrega los datos necesarios para realizar el ticket luego
                                ticket[0] = String.valueOf(tblVentas.getValueAt(0, 3));
                                ticket[1] = String.valueOf(tblVentas.getValueAt(0, 4));
                                ticket[2] = String.valueOf(tblVentas.getValueAt(0, 5));
                                ticket[3] = String.valueOf(tblVentas.getValueAt(0, 6));
                                modeloticket.addRow(ticket);

                                //Eliminar la primera fila
                                modelo.removeRow(0);

                                if (modelo.getRowCount() == 0) {
                                    encabezado = "Venta realizada";
                                    mensaje = "Generar factura?";
                                    btnGuardarFacturas.setEnabled(true);

                                    aceptarCancelar();
                                    //Si se oprime aceptar, se genera la factura correspondiente
                                    if (Principal3.txtAceptarCancelar.getText().equals("1")) {
                                        //Funcion para generar una factura
                                        ruta = "C:\\Users\\User\\Documents\\NetBeansProjects\\ventainformatica\\src\\Temp\\";
//                                        generarFactura();
                                        visualizar(txtBoleta.getText());
                                    }

                                    numeroBoleta();
                                    boleta();
                                    txtBoleta.setText(boleta);
                                    txtIdventas.setText(String.valueOf(codigo));

                                    String usuario = Principal3.lblUsuario.getText();
                                    String objeto = txtBoleta.getText();
                                    String Accion = "VENDER";
//                                    audi.audiclientes(usuario, objeto, Accion);

                                    nuevaVenta();
                                }
                            } else {
                                mensaje = "Editar ingresodia";
                                fallo();
                            }
                        } else {
                            mensaje = "Edicion de detallecaja";
                            fallo();
                        }
                    } else {
                        mensaje = "Edicion de detalleventas";
                        fallo();
                    }
                }
            }
        }
    }//GEN-LAST:event_btnVenderActionPerformed

    private void btnGuardarFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarFacturasActionPerformed
        FileInputStream fis = null;
        ruta = "C:\\Users\\User\\Documents\\NetBeansProjects\\ventainformatica\\src\\Temp\\";
        codigo = codigo - 2;
        boleta();
        try {
            //Convertir el pdf creado recientemente a bytes
            File file = new File(ruta + "Venta " + boleta + ".pdf");
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                    System.out.println("read " + readNum + " bytes,");
                }
            } catch (IOException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] bytes = bos.toByteArray();

            //Convertir bytes a pdf de vuetla y guardar en otra carpeta
            ruta = "C:\\Users\\User\\Documents\\NetBeansProjects\\ventainformatica\\src\\Boletas Guardadas\\";
            File someFile = new File(ruta + "Venta " + boleta + ".pdf");
            try (FileOutputStream fos = new FileOutputStream(someFile)) {
                fos.write(bytes);
                fos.flush();
            }

            mensaje = "Venta " + boleta + ".pdf Generado exitosamente";
            realizado();
            codigo = codigo + 2;
            btnGuardarFacturas.setEnabled(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarFacturasActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        control.cont();
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        calcular();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void txtSuimporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuimporteActionPerformed
        calcular();
    }//GEN-LAST:event_txtSuimporteActionPerformed

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
    private javax.swing.JButton btnEliminar;
    private javax.swing.JToggleButton btnGuardarFacturas;
    private javax.swing.JButton btnSeleccionarCliente;
    private javax.swing.JButton btnSeleccionarProducto;
    private javax.swing.JButton btnVender;
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
    private javax.swing.JTable tblVentas;
    public static javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBoleta;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCedula;
    public static javax.swing.JTextField txtCodigoProductos;
    private javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtIdcliente;
    public static javax.swing.JTextField txtIdproductos;
    private javax.swing.JTextField txtIdventas;
    public static javax.swing.JTextField txtImporte;
    public static javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtNumero;
    public static javax.swing.JTextField txtPrecio;
    public static javax.swing.JTextField txtProductos;
    private javax.swing.JTextField txtSuimporte;
    private javax.swing.JTextField txtSuvuelto;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    public void nuevo() {
        n = n + 1;
        txtNumero.setText(String.valueOf(n));
        // leer la cantidad pedidas
        cant = (Integer) spnCantidad.getValue();
        //problema de obrtener valor de spinner toca dar vuelta
        txtCantidad.setText(String.valueOf(cant));

        int a = Integer.parseInt(txtPrecio.getText());
        int b = Integer.parseInt(txtCantidad.getText());
        // Calcular la cantidad por valor
        int importe = a * b;
        txtImporte.setText(String.valueOf(importe));

        if (txtIdventas.getText().length() == 0) {
            txtIdventas.setText("0");
        }

        can = Integer.parseInt(txtIdventas.getText());
        can = can + c;

        //Agregar datos a la tabla        
        String datos2[] = new String[7];
        datos2[0] = txtNumero.getText();
        datos2[1] = String.valueOf(can);
        datos2[2] = txtCodigoProductos.getText();
        datos2[3] = txtProductos.getText();
        datos2[4] = txtCantidad.getText();
        datos2[5] = txtPrecio.getText();
        datos2[6] = txtImporte.getText();
        modelo.addRow(datos2);
        tblVentas.setModel(modelo);

        totalizar();

        c++;
        vender();
    }

    public void actualizar() {
        txtPrecio.setText(String.valueOf(tblVentas.getValueAt(i, 5)));

        String cantidad = String.valueOf(tblVentas.getValueAt(i, 4));
        int cantidad2 = Integer.parseInt(cantidad);
        cant = (Integer) spnCantidad.getValue();
        cant = cant + cantidad2;
        txtCantidad.setText(String.valueOf(cant));

        int a = Integer.parseInt(txtPrecio.getText());
        int b = Integer.parseInt(txtCantidad.getText());
        // Calcular la cantidad por valor
        int importe = a * b;
        txtImporte.setText(String.valueOf(importe));

        if (txtIdventas.getText().length() == 0) {
            txtIdventas.setText("0");
        }

        can = Integer.parseInt(txtIdventas.getText());

        //Agregar datos a la tabla
        modelo.setValueAt(txtCantidad.getText(), i, 4);
        modelo.setValueAt(txtImporte.getText(), i, 6);

        totalizar();
        vender();
    }
}
