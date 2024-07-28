package Vista.CompraVenta;

import Config.DataSource;
import Utils.ControlCantidad;
import Model.DatosCaja;
import Model.DatosDetalleCaja;
import Model.DatosDetalleVentas;
import Model.DatosVentas;
import Controller.FuncionesAuditoria;
import Controller.FuncionesCaja;
import Controller.FuncionesProductos;
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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author SwichBlade15
 */
public final class Ventas1 extends javax.swing.JInternalFrame {

//    FuncionesAuditoria audi = new FuncionesAuditoria();
    FuncionesVentas funcion = new FuncionesVentas();
    FuncionesProductos funcionproductos = new FuncionesProductos();
    DatosVentas datosventas = new DatosVentas();
    DatosDetalleVentas datosdetalle = new DatosDetalleVentas();
    FuncionesCaja funcaja = new FuncionesCaja();
    DatosCaja datoscaja = new DatosCaja();
    DatosDetalleCaja detallecaja = new DatosDetalleCaja();
    ControlCantidad control = new ControlCantidad();
    DecimalFormat formateador14 = new DecimalFormat("###,###.###");
    DefaultTableModel modelo, modeloticket;
    ResultSet rs;
    int codigo = 0, c = 0, n, totals = 0, cant, can, i, idd, cantidad, ingresodia, seleccionar;
    int indice = 0, columncount, contador = 0, cant2;
    int total, descuento;
    float tamanoHoja;
    double iva;
    String boleta, sSQL;
    String fechaActual, horaActual, text;
    String ruta, datos4, datos5, datos6, total2;
    public String cli, pro;
    String[] ticket = new String[4];
    String[] datos = new String[13];
    DateFormat hora, fecha;
    Date date = new Date();

    public Ventas1() {
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

        txtImporte.setVisible(false);
        txtNumero.setVisible(false);
        txtDescuento.setVisible(false);
        txtCantidad.setVisible(false);
        txtIdproductos.setVisible(false);
        txtIva.setVisible(false);
        txtCategorias.setVisible(false);
        txtCantidadxMayor.setVisible(false);
        txtDescuentoCategorias.setVisible(false);
        txtIdventas.setText(String.valueOf(codigo));
        btnEliminar.setEnabled(false);
        btnAgregarProducto.setEnabled(false);
        txtCodigoProductos.requestFocus();

        tblVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlPopupMenu.setVisible(true);

                if (SwingUtilities.isRightMouseButton(e)) {
                    Point p = e.getPoint();
                    int rowNumber = tblVentas.rowAtPoint(p);
                    tblVentas.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);

                    int seleccionar2 = rowNumber;

                    txtNumero.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 0)));
                    txtIdproductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 1)));
                    txtCodigoProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 2)));
                    txtProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 3)));
                    txtCantidad.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 4)));
                    txtPrecioMinorista.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 5)));
                    txtPrecioMayorista.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 6)));
                    txtDescuentoProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 7)));
                    txtImporte.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 8)));
                    txtCategorias.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 9)));
                    txtDescuentoCategorias.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 10)));
                    txtCantidadxMayor.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 11)));
                    txtIva.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 12)));

                    btnEliminar.setEnabled(true);

                    pnlPopupMenu.setBackground(new Color(0, 0, 0, 0));
                    pnlPopupMenu.setBorder(null);
                    pmnuVentas.setBorderPainted(false);
                    pmnuVentas.setBorder(null);
                    pmnuVentas.setBackground(new Color(0, 0, 0, 0));
                }
            }
        });

        pmnuVentas.add(pnlPopupMenu);
        pmnuVentas.setBorderPainted(false);
        pmnuVentas.setBorder(null);
        pmnuVentas.setBackground(new Color(0, 0, 0, 0));
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

        btnVender.setOpaque(false);
        btnVender.setContentAreaFilled(false);
        btnVender.setBorderPainted(false);

        pbtnEliminar.setOpaque(false);
        pbtnEliminar.setContentAreaFilled(false);
        pbtnEliminar.setBorderPainted(false);
    }

    public void mostrar(String buscar) {
        modelo = new DefaultTableModel();
        // Agrega columnas
        modelo.addColumn("N°");
        modelo.addColumn("Id");
        modelo.addColumn("Codigo");
        modelo.addColumn("Productos");
        modelo.addColumn("Cantidad");
        modelo.addColumn("P. Minorista");
        modelo.addColumn("P. Mayorista");
        modelo.addColumn("Descuento");
        modelo.addColumn("Importe");
        modelo.addColumn("Categoria");
        modelo.addColumn("Desc. Categoria");
        modelo.addColumn("Cant. x Mayor");
        modelo.addColumn("Iva");

        tblVentas.setModel(modelo);

        //Tamaño de las columnas de la tabla
        TableColumnModel columnModel = tblVentas.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(350);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(50);
        columnModel.getColumn(8).setPreferredWidth(100);
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);
        columnModel.getColumn(11).setPreferredWidth(50);
        columnModel.getColumn(12).setPreferredWidth(50);

        ocultar_columnas();
    }

    public void ocultar_columnas() {
        tblVentas.getColumnModel().getColumn(1).setMaxWidth(0);
        tblVentas.getColumnModel().getColumn(1).setMinWidth(0);
        tblVentas.getColumnModel().getColumn(1).setPreferredWidth(0);

        tblVentas.getColumnModel().getColumn(9).setMaxWidth(0);
        tblVentas.getColumnModel().getColumn(9).setMinWidth(0);
        tblVentas.getColumnModel().getColumn(9).setPreferredWidth(0);

        tblVentas.getColumnModel().getColumn(10).setMaxWidth(0);
        tblVentas.getColumnModel().getColumn(10).setMinWidth(0);
        tblVentas.getColumnModel().getColumn(10).setPreferredWidth(0);

        tblVentas.getColumnModel().getColumn(11).setMaxWidth(0);
        tblVentas.getColumnModel().getColumn(11).setMinWidth(0);
        tblVentas.getColumnModel().getColumn(11).setPreferredWidth(0);
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
        int p;
        if (tblVentas.getRowCount() > 0) {
            for (i = 0; i < tblVentas.getRowCount(); i++) {
                p = Integer.parseInt(tblVentas.getValueAt(i, 8).toString().replace(".", ""));
                t += p;
            }
            txtTotal.setText(String.valueOf(t));
            if (txtTotal.getText().length() > 3) {
                String cadena = txtTotal.getText().replace(".", "");
                txtTotal.setText(formateador14.format(Integer.parseInt(cadena)));
            }
        }
    }

    public void busquedaPorCodigo(String buscar) {
        try {
            DefaultTableModel modelo2;
            modelo2 = funcionproductos.seleccionarProductosPorCodigo(buscar);
            txtIdproductos.setText(String.valueOf(modelo2.getValueAt(seleccionar, 0)));
            txtCodigoProductos.setText(String.valueOf(modelo2.getValueAt(seleccionar, 1)));
            txtProductos.setText(String.valueOf(modelo2.getValueAt(seleccionar, 2)));
            txtPrecioMinorista.setText(String.valueOf(modelo2.getValueAt(seleccionar, 3)));
            txtPrecioMayorista.setText(String.valueOf(modelo2.getValueAt(seleccionar, 4)));
            txtCantidadxMayor.setText(String.valueOf(modelo2.getValueAt(seleccionar, 6)));
            txtIva.setText(String.valueOf(modelo2.getValueAt(seleccionar, 7)));
            txtDescuentoProductos.setText(String.valueOf(modelo2.getValueAt(seleccionar, 8)));
            txtCategorias.setText(String.valueOf(modelo2.getValueAt(seleccionar, 9)));

            btnAgregarProducto.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
//        //Primero se realiza un calculo del la cantidad de lineas necesarias para optimizar el tamaño del documento
//        
//        //9.5 cm desde arriba hasta tabla productos
//        
//        // Crear el documento con un tamaño personalizado de rollos de hojas 75x65mm
//        // 1cm = 28.3f
//        tamanoHoja = 1000;
//        Rectangle rectangle = new Rectangle(212.25f, 1000f);
//        System.out.println("Rectangulo ancho: "+rectangle.getWidth());
//        System.out.println("Rectangulo alto: "+rectangle.getHeight());
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
//            document.add(new Paragraph("Cliente: " + txtNombre.getText() + " " + txtDescuentoClientes.getText(), fuente));
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
            Logger.getLogger(Ventas1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaVenta() {
        txtTotal.setText("00.0");

        //Borrar los datos del cliente
        txtIdcliente.setText("");
        txtNombre.setText("");
        txtDescuentoClientes.setText("");
        txtCedula.setText("");

        //Borrar los datos del producto
        txtCodigoProductos.setText("");
        txtIdproductos.setText("");
        txtProductos.setText("");
        txtNumero.setText("");
        txtCantidad.setText("");
        txtImporte.setText("");

        btnSeleccionarCliente.setEnabled(true);
        btnSeleccionarProducto.setEnabled(true);
        btnAgregarProducto.setEnabled(false);
        btnVender.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    //Compara si la categoria del producto seleccionado ya tiene un descuento activo.
    //En caso de que si tenga, el descuento del producto se anula y se utiliza el descuento de la categoria.
    public void compararDescuentosProductos() {
        for (i = 0; i < Principal3.tblCategorias.getRowCount(); i++) {
            if (txtCategorias.getText().equals(String.valueOf(Principal3.tblCategorias.getValueAt(i, 1)))) {
                txtDescuentoCategorias.setText(String.valueOf(Principal3.tblCategorias.getValueAt(i, 2)));
            }
        }
    }

    public void ivaDescuentos() {
        int canti;
        int cantxmayor;
        txtTotalSinDescuento.setText("0");
        txtTotalDescuento.setText("0");

        for (i = 0; i < tblVentas.getRowCount(); i++) {
            canti = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)));
            cantxmayor = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 11)));

            if (canti >= cantxmayor) {
                total = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 6)).replace(".", ""));
            } else {
                total = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 5)).replace(".", ""));
            }

            txtTotalSinDescuento.setText(String.valueOf(Integer.parseInt(txtTotalSinDescuento.getText().replace(".", "")) + total));
            //Agrega el punto de miles al monto total sin descuento
            if (txtTotalSinDescuento.getText().length() > 3) {
                String cadena = txtTotalSinDescuento.getText().replace(".", "");
                txtTotalSinDescuento.setText(formateador14.format(Integer.parseInt(cadena)));
            }
        }

        //Calculo de Iva
        txtTotalSinDescuento.setText("0");
        txtTotalDescuento.setText("0");
        txtIva10.setText("0");
        txtIva5.setText("0");
        txtTotalIva.setText("0");

        for (i = 0; i < tblVentas.getRowCount(); i++) {
            canti = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)));
            cantxmayor = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 11)));

            if (canti >= cantxmayor) {
                total = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 6)).replace(".", ""));

                int cantidadd = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 6)).replace(".", ""));
                descuento = (int) (cantidadd * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 7)).replace(".", "")) / 100);
                int total3 = cantidadd - descuento;

                //Se realiza el calculo del iva
                switch (Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 12)))) {
                    case 10:
                        iva = total3 / 11;
                        iva = Integer.parseInt(txtIva10.getText().replace(".", "")) + iva;
                        txtIva10.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    case 5:
                        iva = total3 / 21;
                        iva = Integer.parseInt(txtIva5.getText().replace(".", "")) + iva;
                        txtIva5.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    case 0:
                        iva = Integer.parseInt(txtExcentas.getText().replace(".", "")) + iva;
                        txtExcentas.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    default:
                        break;
                }
            } else {
                total = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 5)).replace(".", ""));

                int cantidadd = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 4)).replace(".", "")) * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 5)).replace(".", ""));
                descuento = (int) (cantidadd * Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 7)).replace(".", "")) / 100);
                int total3 = cantidadd - descuento;

                //Se realiza el calculo del iva
                switch (Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 12)))) {
                    case 10:
                        iva = total3 / 11;
                        iva = Integer.parseInt(txtIva10.getText().replace(".", "")) + iva;
                        txtIva10.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    case 5:
                        iva = total3 / 21;
                        iva = Integer.parseInt(txtIva5.getText().replace(".", "")) + iva;
                        txtIva5.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    case 0:
                        iva = Integer.parseInt(txtExcentas.getText().replace(".", "")) + iva;
                        txtExcentas.setText(String.valueOf(formateador14.format(iva)));
                        break;
                    default:
                        break;
                }
            }

            txtTotalIva.setText(String.valueOf(Integer.parseInt(txtIva5.getText().replace(".", "")) + Integer.parseInt(txtIva10.getText().replace(".", "")) + Integer.parseInt(txtExcentas.getText().replace(".", ""))));
            //Agrega el punto de miles al monto total sin descuento
            if (txtTotalIva.getText().length() > 3) {
                String cadena = txtTotalIva.getText().replace(".", "");
                txtTotalIva.setText(formateador14.format(Integer.parseInt(cadena)));
            }

            txtTotalSinDescuento.setText(String.valueOf(Integer.parseInt(txtTotalSinDescuento.getText().replace(".", "")) + total));
            //Agrega el punto de miles al monto total sin descuento
            if (txtTotalSinDescuento.getText().length() > 3) {
                String cadena = txtTotalSinDescuento.getText().replace(".", "");
                txtTotalSinDescuento.setText(formateador14.format(Integer.parseInt(cadena)));
            }

            txtTotalDescuento.setText(String.valueOf(Integer.parseInt(txtTotalSinDescuento.getText().replace(".", "")) - Integer.parseInt(txtTotal.getText().replace(".", ""))));
            //Agrega el punto de miles al monto total sin descuento
            if (txtTotalDescuento.getText().length() > 3) {
                String cadena = txtTotalDescuento.getText().replace(".", "");
                txtTotalDescuento.setText(formateador14.format(Integer.parseInt(cadena)));
            }
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

        pmnuVentas = new javax.swing.JPopupMenu();
        pnlPopupMenu = new javax.swing.JPanel();
        pbtnEliminar = new javax.swing.JButton();
        lblFondoPopup = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlClientes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIdcliente = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescuentoClientes = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnSeleccionarCliente = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
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
        txtPrecioMinorista = new javax.swing.JTextField();
        spnCantidad = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        txtProductos = new javax.swing.JTextField();
        btnSeleccionarProducto = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtPrecioMayorista = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtDescuentoProductos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDescuentoCategorias = new javax.swing.JTextField();
        txtCategorias = new javax.swing.JTextField();
        txtCantidadxMayor = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnVender = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtExcentas = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTotalSinDescuento = new javax.swing.JTextField();
        txtIva5 = new javax.swing.JTextField();
        txtIva10 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTotalIva = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtTotalDescuento = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtIdproductos = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        pmnuVentas.setBorder(null);
        pmnuVentas.setBackground(new java.awt.Color(0,0,0,0));

        pnlPopupMenu.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.setBorder(null);
        pnlPopupMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pbtnEliminar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        pbtnEliminar.setForeground(new java.awt.Color(0, 102, 255));
        pbtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Eliminar15.png"))); // NOI18N
        pbtnEliminar.setText("Eliminar");
        pbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pbtnEliminarActionPerformed(evt);
            }
        });
        pnlPopupMenu.add(pbtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 32, 104, 25));

        lblFondoPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPopup.png"))); // NOI18N
        pnlPopupMenu.add(lblFondoPopup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 60));

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
        jLabel3.setText("Cliente:");
        pnlClientes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 260, -1));

        txtIdcliente.setEditable(false);
        txtIdcliente.setBackground(new java.awt.Color(255, 255, 255));
        txtIdcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtIdcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 96, -1));

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 260, -1));

        txtDescuentoClientes.setEditable(false);
        txtDescuentoClientes.setBackground(new java.awt.Color(255, 255, 255));
        txtDescuentoClientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtDescuentoClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 50, -1));

        txtCedula.setEditable(false);
        txtCedula.setBackground(new java.awt.Color(255, 255, 255));
        txtCedula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlClientes.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Cedula:");
        pnlClientes.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 110, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Descuento:");
        pnlClientes.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 70, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Id Cliente:");
        pnlClientes.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 93, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("%");
        pnlClientes.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 30, -1, 20));

        btnSeleccionarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSeleccionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirCliente.png"))); // NOI18N
        btnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarClienteActionPerformed(evt);
            }
        });
        pnlClientes.add(btnSeleccionarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 150, 50));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPanelVentas3.png"))); // NOI18N
        jLabel18.setText("jLabel18");
        pnlClientes.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 70));

        getContentPane().add(pnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 860, 70));

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 14, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Boleta N°:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 110, 20));

        txtBoleta.setEditable(false);
        txtBoleta.setBackground(new java.awt.Color(255, 255, 255));
        txtBoleta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel3.add(txtBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 110, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fecha:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 20));

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        jPanel3.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Id Ventas:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 110, 20));

        txtIdventas.setEditable(false);
        txtIdventas.setBackground(new java.awt.Color(255, 255, 255));
        txtIdventas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdventasActionPerformed(evt);
            }
        });
        jPanel3.add(txtIdventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 110, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPanelVentas2.png"))); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 70));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 410, 70));

        pnlProductos.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("P. Minor.");
        pnlProductos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 80, -1));

        txtCodigoProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCodigoProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProductosActionPerformed(evt);
            }
        });
        pnlProductos.add(txtCodigoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 120, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cant");
        pnlProductos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 60, -1));

        txtPrecioMinorista.setEditable(false);
        txtPrecioMinorista.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioMinorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtPrecioMinorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 80, -1));

        spnCantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 0, 999, 1));
        spnCantidad.setToolTipText("");
        pnlProductos.add(spnCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 60, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Codigo:");
        pnlProductos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, -1));

        txtProductos.setEditable(false);
        txtProductos.setBackground(new java.awt.Color(255, 255, 255));
        txtProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 230, -1));

        btnSeleccionarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSeleccionarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonElegirProducto.png"))); // NOI18N
        btnSeleccionarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnSeleccionarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 160, 40));

        btnAgregarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonAgregarProducto.png"))); // NOI18N
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        pnlProductos.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 160, 40));

        tblVentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Id", "Codigo", "Productos", "Cantidad", "Precio", "Descuento", "Importe"
            }
        ));
        tblVentas.setComponentPopupMenu(pmnuVentas);
        tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVentas);

        pnlProductos.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 1250, 210));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Producto:");
        pnlProductos.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 230, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("P. Mayor.");
        pnlProductos.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 80, -1));

        txtPrecioMayorista.setEditable(false);
        txtPrecioMayorista.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioMayorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtPrecioMayorista, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 80, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Descuento:");
        pnlProductos.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 80, -1));

        txtDescuentoProductos.setEditable(false);
        txtDescuentoProductos.setBackground(new java.awt.Color(255, 255, 255));
        txtDescuentoProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pnlProductos.add(txtDescuentoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("%");
        pnlProductos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 40, -1, 20));
        pnlProductos.add(txtDescuentoCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 40, 50, -1));
        pnlProductos.add(txtCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 50, -1));
        pnlProductos.add(txtCantidadxMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 50, -1));
        pnlProductos.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 50, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPanelVentas4.png"))); // NOI18N
        jLabel25.setText("jLabel25");
        pnlProductos.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 300));

        getContentPane().add(pnlProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1290, 300));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonEliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 31));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Total a Pagar $");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 130, 30));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 255, 204));
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 102, 0));
        txtTotal.setText("00.0");
        jPanel2.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 150, -1));

        btnVender.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnVender.setForeground(new java.awt.Color(0, 51, 51));
        btnVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BotonVender.png"))); // NOI18N
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });
        jPanel2.add(btnVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 150, 40));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPanelVentas5.png"))); // NOI18N
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 120));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 460, 340, 120));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Gravado 10%:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtExcentas.setEditable(false);
        txtExcentas.setBackground(new java.awt.Color(255, 255, 255));
        txtExcentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtExcentas.setText("0");
        jPanel1.add(txtExcentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 90, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Total s/ Desc.");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 90, -1));

        txtTotalSinDescuento.setEditable(false);
        txtTotalSinDescuento.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalSinDescuento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalSinDescuento.setText("0");
        jPanel1.add(txtTotalSinDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 90, -1));

        txtIva5.setEditable(false);
        txtIva5.setBackground(new java.awt.Color(255, 255, 255));
        txtIva5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIva5.setText("0");
        jPanel1.add(txtIva5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, -1));

        txtIva10.setEditable(false);
        txtIva10.setBackground(new java.awt.Color(255, 255, 255));
        txtIva10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIva10.setText("0");
        jPanel1.add(txtIva10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Gravado 5%:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Total IVA:");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 90, -1));

        txtTotalIva.setEditable(false);
        txtTotalIva.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalIva.setText("0");
        jPanel1.add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 90, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Excentas:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 90, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Descuento:");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 90, -1));

        txtTotalDescuento.setEditable(false);
        txtTotalDescuento.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalDescuento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalDescuento.setText("0");
        jPanel1.add(txtTotalDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 90, -1));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoPanelVentas6.png"))); // NOI18N
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 460, 350, 120));
        getContentPane().add(txtIdproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 50, -1));

        txtNumero.setEditable(false);
        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 50, -1));

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 50, -1));

        txtImporte.setEditable(false);
        txtImporte.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 50, -1));
        getContentPane().add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 50, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoVentas.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 600));
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarClienteActionPerformed
        llamarCliente();
        vender();
    }//GEN-LAST:event_btnSeleccionarClienteActionPerformed

    private void txtCodigoProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProductosActionPerformed
        busquedaPorCodigo(txtCodigoProductos.getText());

        compararDescuentosProductos();
        if (tblVentas.getRowCount() != 0) {
            if (recorrerTabla() == true) {
                actualizar();
            } else {
                nuevo();
            }
        } else {
            nuevo();
        }

        txtTotalIva.setText(String.valueOf(Integer.parseInt(txtIva5.getText().replace(".", "")) + Integer.parseInt(txtIva10.getText().replace(".", ""))));

        if (txtTotalIva.getText().length() > 3) {
            String cadena = txtTotalIva.getText().replace(".", "");
            txtTotalIva.setText(formateador14.format(Integer.parseInt(cadena)));
        }
    }//GEN-LAST:event_txtCodigoProductosActionPerformed

    private void txtIdventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdventasActionPerformed

    private void btnSeleccionarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarProductoActionPerformed
        llamarProductos();
    }//GEN-LAST:event_btnSeleccionarProductoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        compararDescuentosProductos();
        if (tblVentas.getRowCount() != 0) {
            if (recorrerTabla() == true) {
                actualizar();
            } else {
                nuevo();
            }
        } else {
            nuevo();
        }

        txtTotalIva.setText(String.valueOf(Integer.parseInt(txtIva5.getText().replace(".", "")) + Integer.parseInt(txtIva10.getText().replace(".", ""))));

        if (txtTotalIva.getText().length() > 3) {
            String cadena = txtTotalIva.getText().replace(".", "");
            txtTotalIva.setText(formateador14.format(Integer.parseInt(cadena)));
        }
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        datosventas.setBoleta(txtBoleta.getText());
        datosventas.setFecha(txtFecha.getText());
        datosventas.setHora(horaActual);
        datosventas.setPreciototal(Integer.parseInt(txtTotal.getText().replace(".", "")));

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

                    datosdetalle.setPrecio(Integer.parseInt(tblVentas.getValueAt(i, 5).toString().replace(".", "")));
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
                        int ingre = ingresodia + Integer.parseInt(txtTotal.getText().replace(".", ""));
                        datoscaja.setIngresodia(ingre);
                        datoscaja.setIdcaja(Integer.parseInt(Principal3.txtIdcaja.getText()));

                        if (funcaja.insertarDetalleCajaVentas(funcaja.buscarVentas(Integer.parseInt(txtIdventas.getText())), funcaja.buscarCaja(Integer.parseInt(Principal3.txtIdcaja.getText())))) {
                            if (funcaja.editarIngresoDia(datoscaja)) {
                                int canti = Integer.parseInt(String.valueOf(tblVentas.getValueAt(0, 4)));
                                int cantxmayor = Integer.parseInt(String.valueOf(tblVentas.getValueAt(0, 11)));

                                //Agrega los datos necesarios para realizar el ticket luego
                                ticket[0] = String.valueOf(tblVentas.getValueAt(0, 3));
                                ticket[1] = String.valueOf(tblVentas.getValueAt(0, 4));
                                if (canti >= cantxmayor) {
                                    ticket[2] = String.valueOf(tblVentas.getValueAt(0, 6));
                                } else {
                                    ticket[2] = String.valueOf(tblVentas.getValueAt(0, 5));
                                }

                                contador = contador + 1;
                                ticket[3] = String.valueOf(contador);
//                                ticket[3] = String.valueOf(tblVentas.getValueAt(0, 8));
                                modeloticket.addRow(ticket);

                                //Eliminar la primera fila
                                modelo.removeRow(0);

                                tamanoHoja = tamanoHoja+1;
                                System.out.println("tamaño hoja: "+tamanoHoja);
                                if (modelo.getRowCount() == 0) {
                                    encabezado = "Venta realizada";
                                    mensaje = "Generar factura?";

                                    aceptarCancelar();
                                    //Si se oprime aceptar, se genera la factura correspondiente
                                    if (Principal3.txtAceptarCancelar.getText().equals("1")) {
                                        //Funcion para generar una factura
                                        
                                        //Se genera una ruta absoluta a partir de una ruta relativa
                                        final String rutaImagen = "src/Temp/";
                                        Path rutarelativaImagen = Paths.get(rutaImagen);

                                        Path rutaabsolutaImagen = rutarelativaImagen.toAbsolutePath();
                                        String rutaImagen1 = rutaabsolutaImagen.toString();
                                        ruta = rutaImagen1;
                                        
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

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        control.cont();
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void tblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasMouseClicked
        int seleccionar2 = tblVentas.rowAtPoint(evt.getPoint());

        txtNumero.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 0)));
        txtIdproductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 1)));
        txtCodigoProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 2)));
        txtProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 3)));
        txtCantidad.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 4)));
        txtPrecioMinorista.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 5)));
        txtPrecioMayorista.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 6)));
        txtDescuentoProductos.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 7)));
        txtImporte.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 8)));
        txtCategorias.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 9)));
        txtDescuentoCategorias.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 10)));
        txtCantidadxMayor.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 11)));
        txtIva.setText(String.valueOf(tblVentas.getValueAt(seleccionar2, 12)));

        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblVentasMouseClicked

    private void pbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pbtnEliminarActionPerformed
        eliminar();
        pnlPopupMenu.setVisible(false);
    }//GEN-LAST:event_pbtnEliminarActionPerformed

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
    public static javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnEliminar;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
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
    private javax.swing.JLabel lblFondoPopup;
    private javax.swing.JButton pbtnEliminar;
    private javax.swing.JPopupMenu pmnuVentas;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlPopupMenu;
    private javax.swing.JPanel pnlProductos;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblVentas;
    private javax.swing.JTextField txtBoleta;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCantidadxMayor;
    public static javax.swing.JTextField txtCategorias;
    public static javax.swing.JTextField txtCedula;
    public static javax.swing.JTextField txtCodigoProductos;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDescuentoCategorias;
    public static javax.swing.JTextField txtDescuentoClientes;
    public static javax.swing.JTextField txtDescuentoProductos;
    private javax.swing.JTextField txtExcentas;
    private javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtIdcliente;
    public static javax.swing.JTextField txtIdproductos;
    private javax.swing.JTextField txtIdventas;
    public static javax.swing.JTextField txtImporte;
    public static javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    public static javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtNumero;
    public static javax.swing.JTextField txtPrecioMayorista;
    public static javax.swing.JTextField txtPrecioMinorista;
    public static javax.swing.JTextField txtProductos;
    public static javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalDescuento;
    private javax.swing.JTextField txtTotalIva;
    private javax.swing.JTextField txtTotalSinDescuento;
    // End of variables declaration//GEN-END:variables

    public void nuevo() {
        n = n + 1;
        txtNumero.setText(String.valueOf(n));
        // leer la cantidad pedidas
        cant = (Integer) spnCantidad.getValue();
        //problema de obrtener valor de spinner toca dar vuelta
        txtCantidad.setText(String.valueOf(cant));

        int a = Integer.parseInt(txtPrecioMinorista.getText().replace(".", ""));
        int b = Integer.parseInt(txtCantidad.getText());
        // Calcular la cantidad por valor
        int importe = a * b;
        txtImporte.setText(String.valueOf(importe));

        if (txtIdventas.getText().length() == 0) {
            txtIdventas.setText("0");
        }

        can = Integer.parseInt(txtIdventas.getText());
        can = can + c;

        if (txtCantidad.getText().length() > 3) {
            String cadena = txtCantidad.getText().replace(".", "");
            txtCantidad.setText(formateador14.format(Integer.parseInt(cadena)));
        }

        if (txtPrecioMinorista.getText().length() > 3) {
            String cadena = txtPrecioMinorista.getText().replace(".", "");
            txtPrecioMinorista.setText(formateador14.format(Integer.parseInt(cadena)));
        }

        if (txtPrecioMayorista.getText().length() > 3) {
            String cadena = txtPrecioMayorista.getText().replace(".", "");
            txtPrecioMayorista.setText(formateador14.format(Integer.parseInt(cadena)));
        }

        if (txtImporte.getText().length() > 3) {
            String cadena = txtImporte.getText().replace(".", "");
            txtImporte.setText(formateador14.format(Integer.parseInt(cadena)));
        }

        //Agregar datos a la tabla
        datos[0] = txtNumero.getText();
        datos[1] = String.valueOf(can);
        datos[2] = txtCodigoProductos.getText();
        datos[3] = txtProductos.getText();
        datos[4] = txtCantidad.getText();
        datos[5] = txtPrecioMinorista.getText();
        datos[6] = txtPrecioMayorista.getText();
        datos[7] = txtDescuentoProductos.getText();
        datos[8] = txtImporte.getText();
        datos[9] = txtCategorias.getText();
        datos[10] = txtDescuentoCategorias.getText();
        datos[11] = txtCantidadxMayor.getText();
        datos[12] = txtIva.getText();

        //Se comparan si hay descuentos activos en el cliente, categoria o productos y se crea un orden de prioridad
        if (txtDescuentoClientes.getText().length() > 0) {
            if (Integer.parseInt(txtDescuentoClientes.getText()) > 0) {
                txtDescuentoCategorias.setText("0");
                txtDescuentoProductos.setText("0");
                int canti = Integer.parseInt(datos[4]);
                int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());

                if (canti >= cantxmayor) {
                    total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[6].replace(".", ""));
                } else {
                    total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[5].replace(".", ""));
                }

                descuento = (int) (total * Integer.parseInt(txtDescuentoClientes.getText()) / 100);
                total = total - descuento;

                //Agrega el punto de miles al monto total
                if (String.valueOf(total).length() > 3) {
                    String cadena = String.valueOf(total).replace(".", "");
                    total2 = formateador14.format(Integer.parseInt(cadena));
                }
                datos[7] = txtDescuentoClientes.getText();
                datos[8] = total2;

            } else if (txtDescuentoCategorias.getText().length() > 0) {
                if (Integer.parseInt(txtDescuentoCategorias.getText()) > 0) {
                    int canti = Integer.parseInt(datos[4]);
                    int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());

                    txtDescuentoProductos.setText("0");
                    if (canti >= cantxmayor) {
                        total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[6].replace(".", ""));
                    } else {
                        total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[5].replace(".", ""));
                    }

                    descuento = (int) (total * Integer.parseInt(txtDescuentoCategorias.getText()) / 100);
                    total = total - descuento;

                    //Agrega el punto de miles al monto total
                    if (String.valueOf(total).length() > 3) {
                        String cadena = String.valueOf(total).replace(".", "");
                        total2 = formateador14.format(Integer.parseInt(cadena));
                    }
                    datos[8] = total2;
                    datos[7] = txtDescuentoCategorias.getText();

                } else if (txtDescuentoProductos.getText().length() > 0) {
                    if (Integer.parseInt(txtDescuentoProductos.getText()) > 0) {
                        int canti = Integer.parseInt(datos[4]);
                        int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());

                        if (canti >= cantxmayor) {
                            total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[6].replace(".", ""));
                        } else {
                            total = Integer.parseInt(datos[4].replace(".", "")) * Integer.parseInt(datos[5].replace(".", ""));
                        }

                        descuento = (int) (total * Integer.parseInt(txtDescuentoProductos.getText()) / 100);
                        total = total - descuento;

                        //Agrega el punto de miles al monto total
                        if (String.valueOf(total).length() > 3) {
                            String cadena = String.valueOf(total).replace(".", "");
                            total2 = formateador14.format(Integer.parseInt(cadena));
                        }
                        datos[8] = total2;
                        datos[7] = txtDescuentoProductos.getText();

                    }
                }
            }
        }

        modelo.addRow(datos);
        tblVentas.setModel(modelo);

        ivaDescuentos();
        totalizar();
        c++;
        vender();
    }

    public void actualizar() {
        txtPrecioMinorista.setText(String.valueOf(tblVentas.getValueAt(i, 5)));
        txtPrecioMayorista.setText(String.valueOf(tblVentas.getValueAt(i, 6)));

        String cantidad = String.valueOf(tblVentas.getValueAt(i, 4));
        int cantidad2 = Integer.parseInt(cantidad);
        cant = (Integer) spnCantidad.getValue();
        cant2 = (Integer) spnCantidad.getValue();
        cant = cant + cantidad2;
        txtCantidad.setText(String.valueOf(cant));

        int a = Integer.parseInt(txtPrecioMinorista.getText().replace(".", ""));
        int b = Integer.parseInt(txtCantidad.getText().replace(".", ""));
        // Calcular la cantidad por valor
        int importe = a * b;
        txtImporte.setText(String.valueOf(importe));

        if (txtIdventas.getText().length() == 0) {
            txtIdventas.setText("0");
        }

        can = Integer.parseInt(txtIdventas.getText());

        //Se comparan si hay descuentos activos en el cliente, categoria o productos y se crea un orden de prioridad
        if (txtDescuentoClientes.getText().length() > 0) {
            if (Integer.parseInt(txtDescuentoClientes.getText()) > 0) {
                txtDescuentoCategorias.setText("0");
                txtDescuentoProductos.setText("0");
                int canti = Integer.parseInt(txtCantidad.getText());
                int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());

                if (canti >= cantxmayor) {
                    total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMayorista.getText().replace(".", ""));
                } else {
                    total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMinorista.getText().replace(".", ""));
                }

                descuento = (int) (total * Integer.parseInt(txtDescuentoClientes.getText()) / 100);
                total = total - descuento;

                //Agrega el punto de miles al monto total
                if (String.valueOf(total).length() > 3) {
                    String cadena = String.valueOf(total).replace(".", "");
                    total2 = formateador14.format(Integer.parseInt(cadena));
                }

                txtImporte.setText(total2);
                txtDescuentoClientes.setText(String.valueOf(tblVentas.getValueAt(i, 7)));

            } else if (txtDescuentoCategorias.getText().length() > 0) {
                if (Integer.parseInt(txtDescuentoCategorias.getText()) > 0) {
                    int canti = Integer.parseInt(txtCantidad.getText());
                    int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());
                    txtDescuentoProductos.setText("0");

                    if (canti >= cantxmayor) {
                        total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMayorista.getText().replace(".", ""));
                    } else {
                        total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMinorista.getText().replace(".", ""));
                    }

                    descuento = (int) (total * Integer.parseInt(txtDescuentoCategorias.getText()) / 100);
                    total = total - descuento;

                    //Agrega el punto de miles al monto total
                    if (String.valueOf(total).length() > 3) {
                        String cadena = String.valueOf(total).replace(".", "");
                        total2 = formateador14.format(Integer.parseInt(cadena));
                    }

                    txtImporte.setText(total2);
                    txtDescuentoCategorias.setText(String.valueOf(tblVentas.getValueAt(i, 7)));
                } else if (txtProductos.getText().length() > 0) {
                    if (Integer.parseInt(txtProductos.getText()) > 0) {
                        int canti = Integer.parseInt(txtCantidad.getText());
                        int cantxmayor = Integer.parseInt(txtCantidadxMayor.getText());

                        if (canti >= cantxmayor) {
                            total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMayorista.getText().replace(".", ""));
                        } else {
                            total = Integer.parseInt(txtCantidad.getText()) * Integer.parseInt(txtPrecioMinorista.getText().replace(".", ""));
                        }

                        descuento = (int) (total * Integer.parseInt(txtDescuentoProductos.getText()) / 100);
                        total = total - descuento;

                        //Agrega el punto de miles al monto total
                        if (String.valueOf(total).length() > 3) {
                            String cadena = String.valueOf(total).replace(".", "");
                            total2 = formateador14.format(Integer.parseInt(cadena));
                        }
                        txtImporte.setText(total2);
                        txtDescuentoProductos.setText(String.valueOf(tblVentas.getValueAt(i, 7)));
                    }
                }
            }
        }

        //Agregar datos a la tabla
        modelo.setValueAt(txtCantidad.getText(), i, 4);
        modelo.setValueAt(txtImporte.getText(), i, 8);

        ivaDescuentos();
        totalizar();
        vender();
    }

    public void eliminar() {
        i = tblVentas.getSelectedRow();
        if (i == -1) {
            mensaje = "Seleccione una fila a Eliminar";
            advertencia();
        } else {
            int tot = Integer.parseInt(txtTotal.getText().replace(".", ""));
            int entero = Integer.parseInt(String.valueOf(tblVentas.getValueAt(i, 8)).replace(".", ""));
            totals = tot - entero;
            txtTotal.setText(String.valueOf(totals));

            //Agrega el punto de miles al monto total
            if (txtTotal.getText().length() > 3) {
                String cadena = txtTotal.getText().replace(".", "");
                txtTotal.setText(formateador14.format(Integer.parseInt(cadena)));
            }

            this.modelo.removeRow(i);
            n = n - 1;
            btnEliminar.setEnabled(false);
            int num = 1;
            for (int w = 0; w < n; w = w + 1) {
                tblVentas.setValueAt(num, w, 0);
                num = num + 1;
            }

            ivaDescuentos();
        }
    }
}
