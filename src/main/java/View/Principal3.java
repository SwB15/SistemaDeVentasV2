package View;

import View.Caja.AperturaCaja;
import View.Caja.CierreCaja;
import Config.DataSource;
import Utils.ControlCantidad;
import Utils.VencimientosGastos;
import Controller.FuncionesCaja;
import Controller.FuncionesCategorias;
import Controller.FuncionesDescuentos;
import Controller.FuncionesNotificaciones;
import Reportes.Reportes;
import View.Caja.Cuentas;
import View.Caja.MovimientosCaja;
import Vista.CompraVenta.Compras;
import Vista.CompraVenta.ListaCompras;
import Vista.CompraVenta.ListaVentas;
import Vista.CompraVenta.Ventas1;
import Vista.Descuentos.Descuentos;
import Vista.Notificaciones.Aceptar_Cancelar;
import Vista.Notificaciones.Advertencia;
import Vista.Notificaciones.Fallo;
import Vista.Notificaciones.Notificaciones;
import Vista.Notificaciones.Realizado;
import Vista.Otros.AcercaDe;
import Vista.Otros.Auditoria;
import Vista.Otros.Ciudades;
import Vista.Otros.Departamentos;
import Vista.Personas.Clientes;
import Vista.Personas.Usuarios;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import Vista.Otros.Backup;
import Vista.Otros.CodeBar;
import Vista.Personas.Funcionarios;
import View.Products.Baja;
import View.Products.ListaProductos;
import View.Products.Productos;
import Vista.Proveedores.Proveedores;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Principal3 extends javax.swing.JFrame {

    FuncionesCaja funcion = new FuncionesCaja();
    ControlCantidad control = new ControlCantidad();
    FuncionesNotificaciones notificaciones = new FuncionesNotificaciones();
    FuncionesDescuentos funciones = new FuncionesDescuentos();
    VencimientosGastos vencimientogastos = new VencimientosGastos();
    FuncionesCategorias categorias = new FuncionesCategorias();
    public Integer totalRegistros;
    int idcaja, iddetallecaja;
    Date date = new Date();
    DateFormat hora, fecha;
    String fechaActual;
    static String[] titulos = {"Codigo", "Concepto", "Descripcion"};
    public static DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    public Principal3() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);

        if (null == funcion.estadoCaja()) {
            mnuVentas.setEnabled(false);
            mnuCompras.setEnabled(false);
            smnuAbrirCaja.setEnabled(true);
            smnuCerrarCaja.setEnabled(false);

            txtCaja.setText("Caja Cerrada");
            encabezado = "JSystems";
            mensaje = "Gracias por adquirir el sistema";
            realizado();
        } else {
            switch (funcion.estadoCaja()) {
                case "ABIERTO":
                    funcion.idCaja();
                    funcion.idDetalleCaja();
                    txtIdcaja.setText(String.valueOf(funcion.idCaja()));
                    txtIddetallecaja.setText(String.valueOf(funcion.idDetalleCaja()));
                    mnuVentas.setEnabled(true);
                    mnuCompras.setEnabled(true);
                    smnuAbrirCaja.setEnabled(false);
                    smnuCerrarCaja.setEnabled(true);
                    smnuMovimientos.setEnabled(true);
                    txtCaja.setText("Caja Abierta");
                    encabezado = "Estado de caja";
                    mensaje = "Se cerró el sistema sin cerrar Caja";
                    advertencia();
                    break;
                default:

                    mnuVentas.setEnabled(false);
                    mnuCompras.setEnabled(false);
                    smnuAbrirCaja.setEnabled(true);
                    smnuCerrarCaja.setEnabled(false);
                    smnuMovimientos.setEnabled(false);
                    txtCaja.setText("Caja Cerrada");
                    encabezado = "Estado de caja";
                    mensaje = "La caja se encuentra cerrada";
                    realizado();
                    break;
            }
        }

        botonesTransparentes();
        cerrar();
        fecha();
        eliminarArchivos();

        lblCodigo.setVisible(false);
        txtCaja.setVisible(false);
        txtAceptarCancelar.setVisible(false);
        txtIdcaja.setVisible(false);
        txtIddetallecaja.setVisible(false);
        txtFecha.setVisible(false);
        pnlNotificaciones.setBackground(new Color(0, 0, 0, 0));
        lblProceso.setVisible(false);
        lblContadorNotificaciones.setText("0");
        txtContadorNotificaciones.setText("0");

        categorias.mostrarDescuento("");
        control.mostrar("");
        funciones.mostrarFechas("");

        control.cantidadMinima();
        funciones.descuentosVencidos();
        notificaciones.cont();
        lblContadorNotificaciones.setVisible(true);
        lblNotificaciones.setVisible(true);
    }

    public void fecha() {
        fecha = new SimpleDateFormat("dd/MM/yyyy");
        txtFecha.setText(fecha.format(date));
    }

    public void ocultar_columnas() {
        tblCantidadMinima.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCantidadMinima.getColumnModel().getColumn(0).setMinWidth(0);
        tblCantidadMinima.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void botonesTransparentes() {
        btnSesion.setOpaque(false);
        btnSesion.setContentAreaFilled(false);
        btnSesion.setBorderPainted(false);
    }

    public void cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    encabezado = "Cerrar Sesión";
                    mensaje = "Está seguro que desea salir del Sistema?";
                    aceptarCancelar();
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
        }
    }

    public void confirmarSalida() {
        String valor = txtAceptarCancelar.getText();
        if (valor.equals("1")) {
            txtAceptarCancelar.setText("");
            System.exit(0);
        }
    }

    //Metodo que sirve para eliminar los archivos de la carpeta Boletas cuando pasen 24 horas de su creación
    public void eliminarArchivos() {
        String direccion = "C:\\Users\\User\\Documents\\NetBeansProjects\\ventainformatica\\src\\Temp";

        File directorio = new File(direccion);
        File file;
        if (directorio.isDirectory()) {
            String[] files = directorio.list();
            if (files.length > 0) {
                System.out.println(" Directorio vacio: " + direccion);
                for (String archivo : files) {
                    System.out.println(archivo);
                    file = new File(direccion + File.separator + archivo);

                    System.out.println("Ultima modificación: " + new Date(file.lastModified()));
                    long Time;
                    Time = (System.currentTimeMillis() - file.lastModified());
                    long cantidadDia = (Time / 86400000);
                    System.out.println("Age of the file is: " + cantidadDia + " days");
                    // Attempt to delete it
                    //86400000 ms is equivalent to one day
                    if (Time > (86400000 * 1) && archivo.contains(".pdf")) {
                        System.out.println("Borrado:" + archivo);
                        file.delete();
                        file.deleteOnExit();
                    }
                }
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

        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Principal.png"));
        Image image = icon.getImage();
        jDesktopPane1 = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
            }
        };
        lblCodigo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblPermisos = new javax.swing.JLabel();
        btnSesion = new javax.swing.JButton();
        lblProceso = new javax.swing.JLabel();
        pnlNotificaciones = new javax.swing.JPanel();
        lblContadorNotificaciones = new javax.swing.JLabel();
        lblNotificaciones = new javax.swing.JLabel();
        txtAceptarCancelar = new javax.swing.JTextField();
        txtCaja = new javax.swing.JTextField();
        txtIdcaja = new javax.swing.JTextField();
        txtIddetallecaja = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFechaDescuentos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNotificaciones = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCantidadMinima = new javax.swing.JTable();
        txtContadorNotificaciones = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategorias = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDescuentosActivos = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuProductos = new javax.swing.JMenu();
        smnuCategorias = new javax.swing.JMenuItem();
        smnuProductos = new javax.swing.JMenuItem();
        smnuListaProductos = new javax.swing.JMenuItem();
        smnuBaja = new javax.swing.JMenuItem();
        smnuDescuentos = new javax.swing.JMenuItem();
        mnuProveedores = new javax.swing.JMenu();
        mnuPersonas = new javax.swing.JMenu();
        smnuUsuarios = new javax.swing.JMenuItem();
        smnuClientes = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        mnuCompras = new javax.swing.JMenu();
        smnuCompras = new javax.swing.JMenuItem();
        smnuListaCompras = new javax.swing.JMenuItem();
        mnuCaja = new javax.swing.JMenu();
        smnuAbrirCaja = new javax.swing.JMenuItem();
        smnuCerrarCaja = new javax.swing.JMenuItem();
        smnuMovimientos = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        mnuVentas = new javax.swing.JMenu();
        smnuVentas = new javax.swing.JMenuItem();
        smnuListaVentas = new javax.swing.JMenuItem();
        mnuHerramientas = new javax.swing.JMenu();
        smnuOtros = new javax.swing.JMenu();
        smnuDepartamentos = new javax.swing.JMenuItem();
        smnuCiudades = new javax.swing.JMenuItem();
        smnuAcercaDe = new javax.swing.JMenuItem();
        smnuBackup = new javax.swing.JMenuItem();
        mnuAuditoria = new javax.swing.JMenuItem();
        smnuReportes = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(224, 224, 224));

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPermisos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logout30.png"))); // NOI18N
        btnSesion.setToolTipText("Cerrar Sesion");
        btnSesion.setBorder(null);
        btnSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSesionMouseClicked(evt);
            }
        });
        btnSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSesionActionPerformed(evt);
            }
        });
        btnSesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSesionKeyPressed(evt);
            }
        });

        pnlNotificaciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblContadorNotificaciones.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblContadorNotificaciones.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorNotificaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorNotificaciones.setText("+99");
        lblContadorNotificaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblContadorNotificacionesMouseClicked(evt);
            }
        });
        pnlNotificaciones.add(lblContadorNotificaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 4, 24, 24));

        lblNotificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pruebanotificaciones.png"))); // NOI18N
        lblNotificaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNotificacionesMouseClicked(evt);
            }
        });
        pnlNotificaciones.add(lblNotificaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tblFechaDescuentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblFechaDescuentos);

        tblNotificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblNotificaciones);

        tblCantidadMinima.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblCantidadMinima);

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
        jScrollPane1.setViewportView(tblCategorias);

        tblDescuentosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblDescuentosActivos);

        jDesktopPane1.setLayer(lblCodigo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lblUsuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lblPermisos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnSesion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lblProceso, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(pnlNotificaciones, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtAceptarCancelar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtCaja, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtIdcaja, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtIddetallecaja, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtFecha, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtContadorNotificaciones, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(424, 424, 424))
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addGap(456, 456, 456)
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(141, 141, 141)
                                        .addComponent(txtContadorNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(175, 175, 175)))
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlNotificaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtIdcaja)
                                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                            .addComponent(lblProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(22, 22, 22)
                                            .addComponent(txtAceptarCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtCaja)
                                        .addComponent(txtIddetallecaja, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtFecha))
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addComponent(btnSesion)
                                        .addGap(25, 25, 25)))
                                .addGap(20, 20, 20))))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContadorNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAceptarCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdcaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIddetallecaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(pnlNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(lblPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );

        mnuProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Productos32.png"))); // NOI18N
        mnuProductos.setText("Productos");
        mnuProductos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mnuProductos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mnuProductosMouseMoved(evt);
            }
        });

        smnuCategorias.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Categoria20.png"))); // NOI18N
        smnuCategorias.setText("Categorias");
        smnuCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuCategoriasActionPerformed(evt);
            }
        });
        mnuProductos.add(smnuCategorias);

        smnuProductos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Productos20.png"))); // NOI18N
        smnuProductos.setText("Productos");
        smnuProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuProductosActionPerformed(evt);
            }
        });
        mnuProductos.add(smnuProductos);

        smnuListaProductos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuListaProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Lista20.png"))); // NOI18N
        smnuListaProductos.setText("Lista Productos");
        smnuListaProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuListaProductosActionPerformed(evt);
            }
        });
        mnuProductos.add(smnuListaProductos);

        smnuBaja.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuBaja.setText("Baja de Productos");
        smnuBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuBajaActionPerformed(evt);
            }
        });
        mnuProductos.add(smnuBaja);

        smnuDescuentos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuDescuentos.setText("Descuentos");
        smnuDescuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuDescuentosActionPerformed(evt);
            }
        });
        mnuProductos.add(smnuDescuentos);

        jMenuBar1.add(mnuProductos);

        mnuProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Proveedores32.png"))); // NOI18N
        mnuProveedores.setText("Proveedores");
        mnuProveedores.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mnuProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuProveedoresMouseClicked(evt);
            }
        });
        mnuProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProveedoresActionPerformed(evt);
            }
        });
        jMenuBar1.add(mnuProveedores);

        mnuPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario32.png"))); // NOI18N
        mnuPersonas.setText("Personas");
        mnuPersonas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        smnuUsuarios.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario2-20.png"))); // NOI18N
        smnuUsuarios.setText("Usuarios");
        smnuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuUsuariosActionPerformed(evt);
            }
        });
        mnuPersonas.add(smnuUsuarios);

        smnuClientes.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cliente20.png"))); // NOI18N
        smnuClientes.setText("Clientes");
        smnuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuClientesActionPerformed(evt);
            }
        });
        mnuPersonas.add(smnuClientes);

        jMenuItem4.setText("Funcionarios");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mnuPersonas.add(jMenuItem4);

        jMenuBar1.add(mnuPersonas);

        mnuCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Compras32.png"))); // NOI18N
        mnuCompras.setText("Compras");
        mnuCompras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        smnuCompras.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/NuevaVenta20.png"))); // NOI18N
        smnuCompras.setText("Nueva Compra");
        smnuCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuComprasActionPerformed(evt);
            }
        });
        mnuCompras.add(smnuCompras);

        smnuListaCompras.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuListaCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Lista20.png"))); // NOI18N
        smnuListaCompras.setText("Lista Compras");
        smnuListaCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuListaComprasActionPerformed(evt);
            }
        });
        mnuCompras.add(smnuListaCompras);

        jMenuBar1.add(mnuCompras);

        mnuCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Caja Registradora2-32.png"))); // NOI18N
        mnuCaja.setText("Caja");
        mnuCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        smnuAbrirCaja.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuAbrirCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Caja Registradora2-20.png"))); // NOI18N
        smnuAbrirCaja.setText("Abrir Caja");
        smnuAbrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuAbrirCajaActionPerformed(evt);
            }
        });
        mnuCaja.add(smnuAbrirCaja);

        smnuCerrarCaja.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuCerrarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Caja Registradora2-20.png"))); // NOI18N
        smnuCerrarCaja.setText("Cerrar Caja");
        smnuCerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuCerrarCajaActionPerformed(evt);
            }
        });
        mnuCaja.add(smnuCerrarCaja);

        smnuMovimientos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuMovimientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Caja Registradora20.png"))); // NOI18N
        smnuMovimientos.setText("Movimientos");
        smnuMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuMovimientosActionPerformed(evt);
            }
        });
        mnuCaja.add(smnuMovimientos);

        jMenuItem2.setText("Cuentas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnuCaja.add(jMenuItem2);

        jMenuBar1.add(mnuCaja);

        mnuVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ventas32.png"))); // NOI18N
        mnuVentas.setText("Ventas");
        mnuVentas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        smnuVentas.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ventas2-20.png"))); // NOI18N
        smnuVentas.setText("Nueva Venta");
        smnuVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuVentasActionPerformed(evt);
            }
        });
        mnuVentas.add(smnuVentas);

        smnuListaVentas.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuListaVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Lista20.png"))); // NOI18N
        smnuListaVentas.setText("Lista Ventas");
        smnuListaVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuListaVentasActionPerformed(evt);
            }
        });
        mnuVentas.add(smnuListaVentas);

        jMenuBar1.add(mnuVentas);

        mnuHerramientas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Herramientas32.png"))); // NOI18N
        mnuHerramientas.setText("Herramientas");
        mnuHerramientas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        smnuOtros.setText("Otros");
        smnuOtros.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        smnuDepartamentos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuDepartamentos.setText("Departamentos");
        smnuDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuDepartamentosActionPerformed(evt);
            }
        });
        smnuOtros.add(smnuDepartamentos);

        smnuCiudades.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuCiudades.setText("Ciudades");
        smnuCiudades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuCiudadesActionPerformed(evt);
            }
        });
        smnuOtros.add(smnuCiudades);

        mnuHerramientas.add(smnuOtros);

        smnuAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        smnuAcercaDe.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuAcercaDe.setText("Acerca De");
        smnuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuAcercaDeActionPerformed(evt);
            }
        });
        mnuHerramientas.add(smnuAcercaDe);

        smnuBackup.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        smnuBackup.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuBackup.setText("Backup");
        smnuBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuBackupActionPerformed(evt);
            }
        });
        mnuHerramientas.add(smnuBackup);

        mnuAuditoria.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        mnuAuditoria.setText("Auditoria");
        mnuAuditoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAuditoriaActionPerformed(evt);
            }
        });
        mnuHerramientas.add(mnuAuditoria);

        smnuReportes.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        smnuReportes.setText("Reportes");
        smnuReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnuReportesActionPerformed(evt);
            }
        });
        mnuHerramientas.add(smnuReportes);

        jMenuItem1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jMenuItem1.setText("Generar CodeBar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnuHerramientas.add(jMenuItem1);

        jMenuBar1.add(mnuHerramientas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void smnuCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuCategoriasActionPerformed
        Categorias form = new Categorias();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuCategoriasActionPerformed

    private void smnuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuClientesActionPerformed
        Clientes form = new Clientes();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuClientesActionPerformed

    private void smnuProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuProductosActionPerformed
        Productos form = new Productos();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuProductosActionPerformed

    private void smnuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuUsuariosActionPerformed
        Usuarios form = new Usuarios();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuUsuariosActionPerformed

    private void smnuAbrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuAbrirCajaActionPerformed
        AperturaCaja form = new AperturaCaja();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuAbrirCajaActionPerformed

    private void smnuCerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuCerrarCajaActionPerformed
        CierreCaja form = new CierreCaja();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuCerrarCajaActionPerformed

    private void smnuVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuVentasActionPerformed
        Ventas1 form = new Ventas1();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuVentasActionPerformed

    private void smnuDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuDepartamentosActionPerformed
        Departamentos form = new Departamentos();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuDepartamentosActionPerformed

    private void smnuCiudadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuCiudadesActionPerformed
        Ciudades form = new Ciudades();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuCiudadesActionPerformed

    private void smnuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuAcercaDeActionPerformed
        AcercaDe form = new AcercaDe();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuAcercaDeActionPerformed

    private void smnuListaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuListaVentasActionPerformed
        ListaVentas form = new ListaVentas();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuListaVentasActionPerformed

    private void mnuAuditoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAuditoriaActionPerformed
//        Auditoria form = new Auditoria();
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
//        this.setLocationRelativeTo(null);
//        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_mnuAuditoriaActionPerformed

    private void smnuMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuMovimientosActionPerformed
        MovimientosCaja form = new MovimientosCaja();
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
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuMovimientosActionPerformed

    private void smnuBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuBackupActionPerformed
//        Backup form = new Backup();
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
//        this.setLocationRelativeTo(null);
//        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuBackupActionPerformed

    private void btnSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSesionActionPerformed

    }//GEN-LAST:event_btnSesionActionPerformed

    private void btnSesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSesionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSesionKeyPressed

    private void btnSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSesionMouseClicked
        encabezado = "Cerrar Sesión";
        mensaje = "Está seguro que desea cerrar sesión?";
        aceptarCancelar();

        if (txtAceptarCancelar.getText().equals("1")) {
            if (txtCaja.getText().equals("Caja Cerrada")) {
                this.dispose();
                Login form = new Login();
                form.toFront();
                form.setVisible(true);
            } else {
                mensaje = "Cierre la caja antes de Cerrar Sesión";
                advertencia();
            }
        }
    }//GEN-LAST:event_btnSesionMouseClicked

    private void smnuComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuComprasActionPerformed
        Compras form = new Compras();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuComprasActionPerformed

    private void smnuListaComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuListaComprasActionPerformed
        ListaCompras form = new ListaCompras();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuListaComprasActionPerformed

    private void mnuProductosMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuProductosMouseMoved

    }//GEN-LAST:event_mnuProductosMouseMoved

    private void smnuListaProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuListaProductosActionPerformed
        ListaProductos form = new ListaProductos();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuListaProductosActionPerformed

    private void lblNotificacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNotificacionesMouseClicked
        if (!lblProceso.getText().equals("Proceso: ON")) {
            Notificaciones form = new Notificaciones();
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

            form.toFront();
            form.setVisible(true);
            System.out.println("Antes de notificaciones.notificaciones");
            Notificaciones.btnNotificacionesOculto.doClick();
            this.setLocationRelativeTo(null);
            this.setExtendedState(MAXIMIZED_BOTH);
        } else {

        }
    }//GEN-LAST:event_lblNotificacionesMouseClicked

    private void smnuReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuReportesActionPerformed
        Reportes form = new Reportes();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuReportesActionPerformed

    private void smnuBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuBajaActionPerformed
        Baja form = new Baja();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuBajaActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Funcionarios form = new Funcionarios();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void mnuProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProveedoresActionPerformed

    }//GEN-LAST:event_mnuProveedoresActionPerformed

    private void mnuProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuProveedoresMouseClicked
        Proveedores form = new Proveedores();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_mnuProveedoresMouseClicked

    private void smnuDescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnuDescuentosActionPerformed
        Descuentos form = new Descuentos();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_smnuDescuentosActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        CodeBar form = new CodeBar();
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

        form.toFront();
        form.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void lblContadorNotificacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContadorNotificacionesMouseClicked
        if (!lblProceso.getText().equals("Proceso: ON")) {
            Notificaciones form = new Notificaciones();
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

            form.toFront();
            form.setVisible(true);
            System.out.println("Antes de notificaciones.notificaciones");
            Notificaciones.btnNotificacionesOculto.doClick();
            this.setLocationRelativeTo(null);
            this.setExtendedState(MAXIMIZED_BOTH);
        } else {

        }
    }//GEN-LAST:event_lblContadorNotificacionesMouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (!lblProceso.getText().equals("Proceso: ON")) {
            Cuentas form = new Cuentas();
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

            form.toFront();
            form.setVisible(true);
            System.out.println("Antes de notificaciones.notificaciones");
            Notificaciones.btnNotificacionesOculto.doClick();
            this.setLocationRelativeTo(null);
            this.setExtendedState(MAXIMIZED_BOTH);
        } else {

        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    //Metodos para llamar al JDialog Aceptar_Cancelar
    //Se le añade un Fondo diferente al de Eliminar (Se diferencian en el sombreado)
    Frame f = JOptionPane.getFrameForComponent(this);
    String encabezado;
    String mensaje;
    Icon icono;

    public void advertencia() {
        Advertencia dialog = new Advertencia(f, true);
        Advertencia.lblEncabezado.setText(mensaje);
        Advertencia.lblAdvertencia.setText(encabezado);
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
        Realizado.lblRealizado.setText(encabezado);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal3.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Principal3().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSesion;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public static javax.swing.JLabel lblCodigo;
    public static javax.swing.JLabel lblContadorNotificaciones;
    public static javax.swing.JLabel lblNotificaciones;
    public static javax.swing.JLabel lblPermisos;
    public static javax.swing.JLabel lblProceso;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem mnuAuditoria;
    public static javax.swing.JMenu mnuCaja;
    public static javax.swing.JMenu mnuCompras;
    public static javax.swing.JMenu mnuHerramientas;
    public static javax.swing.JMenu mnuPersonas;
    public static javax.swing.JMenu mnuProductos;
    public static javax.swing.JMenu mnuProveedores;
    public static javax.swing.JMenu mnuVentas;
    public static javax.swing.JPanel pnlNotificaciones;
    public static javax.swing.JMenuItem smnuAbrirCaja;
    private javax.swing.JMenuItem smnuAcercaDe;
    private javax.swing.JMenuItem smnuBackup;
    private javax.swing.JMenuItem smnuBaja;
    private javax.swing.JMenuItem smnuCategorias;
    public static javax.swing.JMenuItem smnuCerrarCaja;
    private javax.swing.JMenuItem smnuCiudades;
    private javax.swing.JMenuItem smnuClientes;
    private javax.swing.JMenuItem smnuCompras;
    private javax.swing.JMenuItem smnuDepartamentos;
    private javax.swing.JMenuItem smnuDescuentos;
    private javax.swing.JMenuItem smnuListaCompras;
    private javax.swing.JMenuItem smnuListaProductos;
    private javax.swing.JMenuItem smnuListaVentas;
    public static javax.swing.JMenuItem smnuMovimientos;
    private javax.swing.JMenu smnuOtros;
    private javax.swing.JMenuItem smnuProductos;
    private javax.swing.JMenuItem smnuReportes;
    public static javax.swing.JMenuItem smnuUsuarios;
    private javax.swing.JMenuItem smnuVentas;
    public static javax.swing.JTable tblCantidadMinima;
    public static javax.swing.JTable tblCategorias;
    public static javax.swing.JTable tblDescuentosActivos;
    public static javax.swing.JTable tblFechaDescuentos;
    public static javax.swing.JTable tblNotificaciones;
    public static javax.swing.JTextField txtAceptarCancelar;
    public static javax.swing.JTextField txtCaja;
    public static javax.swing.JTextField txtContadorNotificaciones;
    public static javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtIdcaja;
    public static javax.swing.JTextField txtIddetallecaja;
    // End of variables declaration//GEN-END:variables
}
