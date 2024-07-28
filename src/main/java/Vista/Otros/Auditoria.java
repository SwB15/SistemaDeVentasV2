
package Vista.Otros;

import Config.DataSource;
import View.Principal3;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 *
 * @author SwichBlade15
 */

public final class Auditoria extends javax.swing.JInternalFrame {
    private Connection con = DataSource.getConnection();
    Statement sentenciasql;
    DefaultComboBoxModel model2;
    DefaultTableModel model;
    Statement sent;
    PreparedStatement ps;
    ResultSet rsd;
    
    public Auditoria() throws SQLException {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setIconifiable(false);
        this.setBorder(null);
        
        con = DataSource.getConnection();
        ListarUsuario();
        LlenarTabla("");
    }
    
    void LlenarTabla(String valor) {
        try {
            
            String[] titulos = { "Fecha", "Hora", "Usuario", "Accion", "Formulario", "Descripcion", "PC", "IP"};
            String stsql = "SELECT * FROM auditoria WHERE accion IS NOT NULL";

            if (!"TODOS".equals(cmbAuditoriaUsuarios.getSelectedItem().toString())) {
                stsql = stsql + " AND usuario ='" + cmbAuditoriaUsuarios.getSelectedItem().toString() + "'";
                System.out.println(stsql);
            }
            
            if (!"TODOS".equals(cmbAuditoriaAccion.getSelectedItem().toString())) {
                stsql = stsql + " AND accion ='" + cmbAuditoriaAccion.getSelectedItem().toString() + "'";
                System.out.println(stsql);
            }
            
            model = new DefaultTableModel(null, titulos);
            sent = con.createStatement();
            //captura el resultado de la tabla
            ResultSet rs = sent.executeQuery(stsql);

            //arreglo fila, para almacenar registros
            String[] fila = new String[9];
            //while para insertar registros en la tabla
            while (rs.next()) {
                fila[0] = rs.getString("fecha");
                fila[1] = rs.getString("hora");
                fila[2] = rs.getString("usuario");
                fila[3] = rs.getString("accion");
                fila[4] = rs.getString("formulario");
                fila[5] = rs.getString("descripcion");
                fila[6] = rs.getString("pc");
                fila[7] = rs.getString("ip");

                model.addRow(fila);   
            }           
            tblAuditoria.setModel(model);
                 
            JTableHeader th; 
            th = tblAuditoria.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 14); 
            th.setFont(fuente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    void ListarUsuario() {
        try {
            String stsql = "SELECT * FROM usuarios";
            sentenciasql = con.createStatement();
            ResultSet rs = sentenciasql.executeQuery(stsql);
            model2 = new DefaultComboBoxModel();
            model2.addElement("TODOS");
            
            while (rs.next()) {
                model2.addElement(rs.getString("usuario"));
            }
            cmbAuditoriaUsuarios.setModel(model2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbAuditoriaUsuarios = new javax.swing.JComboBox<>();
        cmbAuditoriaAccion = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAuditoria = new javax.swing.JTable();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setFrameIcon(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cerrar32.png"))); // NOI18N
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 14, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro Auditoria");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 280, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 77, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Acción:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(562, 77, -1, -1));

        cmbAuditoriaUsuarios.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbAuditoriaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAuditoriaUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(cmbAuditoriaUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 78, 200, 23));

        cmbAuditoriaAccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbAuditoriaAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "GUARDAR", "EDITAR", "ELIMINAR", "APERTURA", "CIERRE" }));
        cmbAuditoriaAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAuditoriaAccionActionPerformed(evt);
            }
        });
        getContentPane().add(cmbAuditoriaAccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(637, 78, 200, 23));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("LISTA DE ACCIONES REALIZADAS");
        jLabel8.setOpaque(true);
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 119, 1019, 27));

        tblAuditoria = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblAuditoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAuditoria.setFocusable(false);
        tblAuditoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAuditoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAuditoria);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 1030, 480));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoAuditoria.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbAuditoriaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAuditoriaUsuariosActionPerformed
        LlenarTabla("");
    }//GEN-LAST:event_cmbAuditoriaUsuariosActionPerformed

    private void cmbAuditoriaAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAuditoriaAccionActionPerformed
        LlenarTabla("");
    }//GEN-LAST:event_cmbAuditoriaAccionActionPerformed

    private void tblAuditoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAuditoriaMouseClicked
        
    }//GEN-LAST:event_tblAuditoriaMouseClicked

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Principal3.lblProceso.setText("Proceso: OFF");
        this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbAuditoriaAccion;
    private javax.swing.JComboBox<String> cmbAuditoriaUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JTable tblAuditoria;
    // End of variables declaration//GEN-END:variables
}
