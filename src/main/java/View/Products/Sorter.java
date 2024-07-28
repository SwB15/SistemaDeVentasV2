package View.Products;

import Config.CustomTabbedPaneUI;
import Config.Run;
import Controller.Category_Controller;
import Model.Category_Model;
import Controller.State_Controller;
import Services.Category_Services;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Sorter extends javax.swing.JDialog {

    Category_Controller controller = new Category_Controller();
    Category_Model category_model = new Category_Model();
    private String code = "", estado = "", stateFilter = "activo";
    private int idestado;

    public Sorter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        jTabbedPane1.setUI(new CustomTabbedPaneUI());
        showCategories("", stateFilter);
        rbtnCategoryActive.setSelected(true);
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                onTabChange();
            }
        });
    }

    public void showCategories(String search, String stateFilter) {
        try {
            DefaultTableModel model;
            model = controller.showCategories(search, stateFilter);
            tblCategory.setModel(model);
//            ocultar_columnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ocultar_columnas() {
        tblCategory.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCategory.getColumnModel().getColumn(0).setMinWidth(0);
        tblCategory.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void onTabChange() {
        int selectedTabIndex = jTabbedPane1.getSelectedIndex();
        switch (selectedTabIndex) {
            case 0:
                // Categories tab selected
                rbtnCategoryActive.setSelected(true);
                stateFilter = "activo";
                showCategories("", stateFilter);
                break;
            case 1:
                // Subcategories tab selected
                rbtnSubcategoryActive.setSelected(true);
                stateFilter = "activo";
//                showSubcategories("", stateFilter);
                break;
            case 2:
                // Sub-subcategories tab selected
                rbtnSubsubcategoryActive.setSelected(true);
                stateFilter = "activo";
//                showSubsubcategories("", stateFilter);
                break;
        }
    }

    private void save() {
        if (validateFields()) {
            estado = "activo";
            idestado = State_Controller.getEstadoId(estado, Run.model);

            category_model.setCodigo(txtCategoryCode.getText());
            category_model.setCategorias(txtCategoryName.getText());
            category_model.setDescripcion(txtCategoryDescription.getText());
            controller.createCategory(category_model, idestado);

            showCategories("", stateFilter);
            JOptionPane.showMessageDialog(null, "Categoria guardada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar la categoria", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        if (validateFields()) {
            estado = "activo";
            idestado = State_Controller.getEstadoId(estado, Run.model);

            category_model.setIdcategorias(Integer.parseInt(code));
            category_model.setCodigo(txtCategoryCode.getText());
            category_model.setCategorias(txtCategoryName.getText());
            category_model.setDescripcion(txtCategoryDescription.getText());
            controller.updateCategory(category_model, idestado);

            showCategories("", stateFilter);
            JOptionPane.showMessageDialog(null, "Categoria editada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al editar la categoria", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void disableCategory() {
        if (txtCategoryCode.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria para anular.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        } else {
            int respuesta = JOptionPane.showConfirmDialog(this, "La categoria será desactivada", "Anular categoria?", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                estado = "inactivo";
                idestado = State_Controller.getEstadoId(estado, Run.model);

                category_model.setIdcategorias(Integer.parseInt(code));
                controller.disableCategory(category_model, idestado);
                JOptionPane.showMessageDialog(null, "Categoria anulada correctamente.", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlCategory = new javax.swing.JPanel();
        txtCategoryName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCategoryCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCategoryDescription = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        pnlCategoryRadiobuttons = new javax.swing.JPanel();
        rbtnCategoryActive = new javax.swing.JRadioButton();
        rbtnCategoryInactive = new javax.swing.JRadioButton();
        rbtnCategoryAll = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlSubcategory = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSubcategoryName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbCategoryCode = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSubcategoryDescription = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtSubcategoryCode = new javax.swing.JTextField();
        cmbCategoryName = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSubcategory = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        rbtnSubcategoryActive = new javax.swing.JRadioButton();
        rbtnSubcategoryInactive = new javax.swing.JRadioButton();
        rbtnSubcategoryAll = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        pnlSubsubcategory = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtSubsubcategoryName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbCategoryCode1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSubsubcategoryDescription = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtSubsubcategoryCode = new javax.swing.JTextField();
        cmbCategoryName1 = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSubsubcategory = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        rbtnSubsubcategoryActive = new javax.swing.JRadioButton();
        rbtnSubsubcategoryInactive = new javax.swing.JRadioButton();
        rbtnSubsubcategoryAll = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbSubCategoryCode = new javax.swing.JComboBox<>();
        cmbSubCategoryName = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnAbort = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clasificador");

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        pnlCategory.setPreferredSize(new java.awt.Dimension(411, 182));

        txtCategoryName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoryNameKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Codigo:");

        txtCategoryCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoryCodeKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Nombre:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Descripción:");

        txtCategoryDescription.setColumns(20);
        txtCategoryDescription.setRows(5);
        txtCategoryDescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoryDescriptionKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txtCategoryDescription);

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblCategory);

        pnlCategoryRadiobuttons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));

        buttonGroup1.add(rbtnCategoryActive);
        rbtnCategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnCategoryActive.setText("Solo activos");
        rbtnCategoryActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCategoryActiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnCategoryInactive);
        rbtnCategoryInactive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnCategoryInactive.setText("Solo inactivos");
        rbtnCategoryInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCategoryInactiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnCategoryAll);
        rbtnCategoryAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnCategoryAll.setText("Todos");
        rbtnCategoryAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCategoryAllActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mostrar en la tabla:");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout pnlCategoryRadiobuttonsLayout = new javax.swing.GroupLayout(pnlCategoryRadiobuttons);
        pnlCategoryRadiobuttons.setLayout(pnlCategoryRadiobuttonsLayout);
        pnlCategoryRadiobuttonsLayout.setHorizontalGroup(
            pnlCategoryRadiobuttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCategoryRadiobuttonsLayout.createSequentialGroup()
                .addComponent(rbtnCategoryActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnCategoryInactive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnCategoryAll))
            .addGroup(pnlCategoryRadiobuttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1))
        );
        pnlCategoryRadiobuttonsLayout.setVerticalGroup(
            pnlCategoryRadiobuttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCategoryRadiobuttonsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCategoryRadiobuttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnCategoryActive)
                    .addComponent(rbtnCategoryInactive)
                    .addComponent(rbtnCategoryAll)))
        );

        javax.swing.GroupLayout pnlCategoryLayout = new javax.swing.GroupLayout(pnlCategory);
        pnlCategory.setLayout(pnlCategoryLayout);
        pnlCategoryLayout.setHorizontalGroup(
            pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCategoryLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCategoryName)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
            .addGroup(pnlCategoryLayout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addGap(0, 0, 0)
                .addComponent(pnlCategoryRadiobuttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCategoryLayout.setVerticalGroup(
            pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCategoryLayout.createSequentialGroup()
                        .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCategoryLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCategoryRadiobuttons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Categorias", pnlCategory);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Categoria:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nombre:");

        cmbCategoryCode.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Descripcion:");

        txtSubcategoryDescription.setColumns(20);
        txtSubcategoryDescription.setRows(5);
        jScrollPane1.setViewportView(txtSubcategoryDescription);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Codigo:");

        cmbCategoryName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSubcategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblSubcategory);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));

        buttonGroup1.add(rbtnSubcategoryActive);
        rbtnSubcategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryActive.setText("Solo activos");

        buttonGroup1.add(rbtnSubcategoryInactive);
        rbtnSubcategoryInactive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryInactive.setText("Solo inactivos");

        buttonGroup1.add(rbtnSubcategoryAll);
        rbtnSubcategoryAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryAll.setText("Todos");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Mostrar en la tabla:");
        jLabel16.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(rbtnSubcategoryActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSubcategoryInactive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSubcategoryAll))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnSubcategoryActive)
                    .addComponent(rbtnSubcategoryInactive)
                    .addComponent(rbtnSubcategoryAll)))
        );

        javax.swing.GroupLayout pnlSubcategoryLayout = new javax.swing.GroupLayout(pnlSubcategory);
        pnlSubcategory.setLayout(pnlSubcategoryLayout);
        pnlSubcategoryLayout.setHorizontalGroup(
            pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                        .addComponent(cmbCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategoryName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(txtSubcategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSubcategoryName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
            .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator3)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlSubcategoryLayout.setVerticalGroup(
            pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                        .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubcategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubcategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 98, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Subcategorias", pnlSubcategory);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Categoria:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Nombre:");

        cmbCategoryCode1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbCategoryCode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Descripcion:");

        txtSubsubcategoryDescription.setColumns(20);
        txtSubsubcategoryDescription.setRows(5);
        jScrollPane3.setViewportView(txtSubsubcategoryDescription);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Codigo:");

        cmbCategoryName1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbCategoryName1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSubsubcategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblSubsubcategory);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));

        buttonGroup1.add(rbtnSubsubcategoryActive);
        rbtnSubsubcategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryActive.setText("Solo activos");

        buttonGroup1.add(rbtnSubsubcategoryInactive);
        rbtnSubsubcategoryInactive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryInactive.setText("Solo inactivos");

        buttonGroup1.add(rbtnSubsubcategoryAll);
        rbtnSubsubcategoryAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryAll.setText("Todos");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Mostrar en la tabla:");
        jLabel14.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(rbtnSubsubcategoryActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSubsubcategoryInactive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSubsubcategoryAll))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnSubsubcategoryActive)
                    .addComponent(rbtnSubsubcategoryInactive)
                    .addComponent(rbtnSubsubcategoryAll)))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Subcategoria:");

        cmbSubCategoryCode.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbSubCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbSubCategoryName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbSubCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlSubsubcategoryLayout = new javax.swing.GroupLayout(pnlSubsubcategory);
        pnlSubsubcategory.setLayout(pnlSubsubcategoryLayout);
        pnlSubsubcategoryLayout.setHorizontalGroup(
            pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubsubcategoryLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlSubsubcategoryLayout.createSequentialGroup()
                            .addComponent(cmbCategoryCode1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbCategoryName1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                        .addComponent(txtSubsubcategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSubsubcategoryName))
                    .addGroup(pnlSubsubcategoryLayout.createSequentialGroup()
                        .addComponent(cmbSubCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSubCategoryName, 0, 189, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubsubcategoryLayout.createSequentialGroup()
                .addComponent(jSeparator2)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlSubsubcategoryLayout.setVerticalGroup(
            pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubsubcategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlSubsubcategoryLayout.createSequentialGroup()
                        .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbCategoryCode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategoryName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cmbSubCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSubCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubsubcategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubsubcategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSubsubcategoryLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubsubcategoryLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sub-subcategorias", pnlSubsubcategory);

        btnCancel.setText("Cancelar");

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setText("Nuevo");

        btnAbort.setText("Anular");
        btnAbort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAbort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(jTabbedPane1))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave)
                    .addComponent(btnNew)
                    .addComponent(btnAbort))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (code.equals("")) {
            save();
        } else {
            update();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbortActionPerformed
        disableCategory();
    }//GEN-LAST:event_btnAbortActionPerformed

    private void rbtnCategoryActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCategoryActiveActionPerformed
        stateFilter = "activo";
        showCategories("", stateFilter);
    }//GEN-LAST:event_rbtnCategoryActiveActionPerformed

    private void rbtnCategoryInactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCategoryInactiveActionPerformed
        stateFilter = "inactivo";
        showCategories("", stateFilter);
    }//GEN-LAST:event_rbtnCategoryInactiveActionPerformed

    private void rbtnCategoryAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCategoryAllActionPerformed
        stateFilter = "todos";
        showCategories("", stateFilter);
    }//GEN-LAST:event_rbtnCategoryAllActionPerformed

    private void txtCategoryCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoryCodeKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

//        if (Character.isDigit(validar)) {
//            getToolkit().beep();
//            evt.consume();
//            mensaje = "Ingrese solo letras";
//            advertencia();
//        }
        int numerocaracteres = 3;
        if (txtCategoryCode.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 4 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCategoryCodeKeyTyped

    private void txtCategoryNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoryNameKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

//        if (Character.isDigit(validar)) {
//            getToolkit().beep();
//            evt.consume();
//            mensaje = "Ingrese solo letras";
//            advertencia();
//        }
        int numerocaracteres = 69;
        if (txtCategoryName.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 70 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCategoryNameKeyTyped

    private void txtCategoryDescriptionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoryDescriptionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

        char validar = evt.getKeyChar();

//        if (Character.isDigit(validar)) {
//            getToolkit().beep();
//            evt.consume();
//            mensaje = "Ingrese solo letras";
//            advertencia();
//        }
        int numerocaracteres = 1999;
        if (txtCategoryDescription.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 2.000 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCategoryDescriptionKeyTyped

    private boolean validateFields() {
        if (txtCategoryCode.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el código de la Categoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtCategoryName.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Categoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbort;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCategoryCode;
    private javax.swing.JComboBox<String> cmbCategoryCode1;
    private javax.swing.JComboBox<String> cmbCategoryName;
    private javax.swing.JComboBox<String> cmbCategoryName1;
    private javax.swing.JComboBox<String> cmbSubCategoryCode;
    private javax.swing.JComboBox<String> cmbSubCategoryName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlCategory;
    private javax.swing.JPanel pnlCategoryRadiobuttons;
    private javax.swing.JPanel pnlSubcategory;
    private javax.swing.JPanel pnlSubsubcategory;
    private javax.swing.JRadioButton rbtnCategoryActive;
    private javax.swing.JRadioButton rbtnCategoryAll;
    private javax.swing.JRadioButton rbtnCategoryInactive;
    private javax.swing.JRadioButton rbtnSubcategoryActive;
    private javax.swing.JRadioButton rbtnSubcategoryAll;
    private javax.swing.JRadioButton rbtnSubcategoryInactive;
    private javax.swing.JRadioButton rbtnSubsubcategoryActive;
    private javax.swing.JRadioButton rbtnSubsubcategoryAll;
    private javax.swing.JRadioButton rbtnSubsubcategoryInactive;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTable tblSubcategory;
    private javax.swing.JTable tblSubsubcategory;
    private javax.swing.JTextField txtCategoryCode;
    private javax.swing.JTextArea txtCategoryDescription;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtSubcategoryCode;
    private javax.swing.JTextArea txtSubcategoryDescription;
    private javax.swing.JTextField txtSubcategoryName;
    private javax.swing.JTextField txtSubsubcategoryCode;
    private javax.swing.JTextArea txtSubsubcategoryDescription;
    private javax.swing.JTextField txtSubsubcategoryName;
    // End of variables declaration//GEN-END:variables
}