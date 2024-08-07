package View.Caja;


import Config.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

 class DetallesMovimientoCaja extends javax.swing.JInternalFrame {

    TableRowSorter trs;
    JTable tabla;
    ResultSet rs;
    DefaultTableModel dfm = new DefaultTableModel();
    public static int id_caja_mov;
    public DetallesMovimientoCaja() {
        initComponents();
        cargarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrollPaneClientes = new javax.swing.JScrollPane();
        tbDetalleMovimientoCaja = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setIconifiable(true);
        setFrameIcon(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbDetalleMovimientoCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneClientes.setViewportView(tbDetalleMovimientoCaja);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salir.png"))); // NOI18N
        btnSalir.setToolTipText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(scrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        jLabel2.setText("Detalles de Movimientos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(jLabel2)
                .addContainerGap(285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        limpiarTabla();
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    //METODO PARA SELECCIONAR DATOS DE LA BD
    public  ResultSet SeleccionarDatos(){
        Statement st;
        ResultSet rse = null;
        try(Connection cn = DataSource.getConnection()){
            st = cn.createStatement();
            rse = st.executeQuery("SELECT * FROM detallecaja WHERE idcaja = '"+id_caja_mov+"'");
        } catch(SQLException ex){
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rse;
    }
    
    //metodo encargado de actualizar las tablas y voler a cargarlas
    public void cargarTabla(){
        tabla = this.tbDetalleMovimientoCaja;
        tabla.setModel(dfm);
        
        dfm.setColumnIdentifiers(new Object[]{"INGRESO", "EGRESO", "TIPO MOVIMIENTO"});
        
        rs = SeleccionarDatos();
        try{
            while(rs.next()){
                String monto = "";
                if(String.valueOf(rs.getInt("ingreso_detallecaja")).length()>3){
                    String numero = String.valueOf(rs.getInt("ingreso_detallecaja"));
                    DecimalFormat num = new DecimalFormat("###,###.##");
                    monto = num.format(Float.parseFloat(numero.replace(",", "")));
                }
                String monto2 = "";
                if(String.valueOf(rs.getInt("egreso_detallecaja")).length()>3){
                    String numero = String.valueOf(rs.getInt("egreso_detallecaja"));
                    DecimalFormat num = new DecimalFormat("###,###.##");
                    monto2 = num.format(Float.parseFloat(numero.replace(",", "")));
                }
                dfm.addRow(new Object[]{monto,monto2,rs.getString("tipo")});
            }
        }catch(Exception e){
           System.out.printf("Algo anda mal");
        }
    }
    //metodo encargado de limpiar tablas
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount()-1;
        
        for (int i = a; i >= 0; i--) {           
        tb.removeRow(tb.getRowCount()-1);
        } 
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrollPaneClientes;
    public static javax.swing.JTable tbDetalleMovimientoCaja;
    // End of variables declaration//GEN-END:variables
}
