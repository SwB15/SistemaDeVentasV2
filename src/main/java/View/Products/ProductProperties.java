package View.Products;

import Config.Run;
import Controller.Product_Controller;
import Controller.State_Controller;
import Utils.ConvertDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author SwichBlade15
 */
public class ProductProperties extends javax.swing.JDialog {

    Product_Controller product_controller = new Product_Controller();
    ConvertDate convert_date = new ConvertDate();
    String code = "", initialState = "", finalState = "", stateFilter = "";
    int idestado, iva;

    public ProductProperties(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        fillComboboxes(cmbCategoryCode, cmbCategoryName, cmbSubCategoryCode, cmbSubCategoryName);
    }

    private final void fillComboboxes(JComboBox<String> categoryCode, JComboBox<String> categoryName, JComboBox<String> subcategoryCode, JComboBox<String> subcategoryName) {
        // Obtener el mapa de categorías desde el controlador
        HashMap<String, HashMap<String, List<String>>> categoryMap = product_controller.fillComboboxes();

        // Limpiar los JComboBoxes
        categoryCode.removeAllItems();
        categoryName.removeAllItems();
        subcategoryCode.removeAllItems();
        subcategoryName.removeAllItems();

        // Añadir opción por defecto
        categoryCode.addItem("--Seleccione--");
        categoryName.addItem("--Seleccione--");
        subcategoryCode.addItem("--Seleccione--");
        subcategoryName.addItem("--Seleccione--");

        // Llenar los JComboBoxes de categorías
        for (Map.Entry<String, HashMap<String, List<String>>> categoryEntry : categoryMap.entrySet()) {
            categoryName.addItem(categoryEntry.getKey());
            categoryCode.addItem(categoryEntry.getValue().get("categoryValues").get(0)); // Suponiendo que el código de la categoría está en "categoryValues"
        }

        // Acción al seleccionar un nombre de categoría
        categoryName.addActionListener(e -> {
            String selectedCategoryName = (String) categoryName.getSelectedItem();
            subcategoryCode.removeAllItems();
            subcategoryName.removeAllItems();

            if (selectedCategoryName != null && !selectedCategoryName.equals("--Seleccione--")) {
                HashMap<String, List<String>> subcategoryMap = categoryMap.get(selectedCategoryName);
                categoryCode.setSelectedItem(subcategoryMap.get("categoryValues").get(0));

                for (Map.Entry<String, List<String>> subcategoryEntry : subcategoryMap.entrySet()) {
                    if (!"categoryValues".equals(subcategoryEntry.getKey())) {
                        subcategoryName.addItem(subcategoryEntry.getKey());
                        subcategoryCode.addItem(subcategoryEntry.getValue().get(0));
                    }
                }
            }
        });

        // Acción al seleccionar un código de categoría
        categoryCode.addActionListener(e -> {
            String selectedCategoryCode = (String) categoryCode.getSelectedItem();
            if (selectedCategoryCode != null && !selectedCategoryCode.equals("--Seleccione--")) {
                for (Map.Entry<String, HashMap<String, List<String>>> categoryEntry : categoryMap.entrySet()) {
                    if (categoryEntry.getValue().get("categoryValues").get(0).equals(selectedCategoryCode)) {
                        categoryName.setSelectedItem(categoryEntry.getKey());
                        break;
                    }
                }
            }
        });

        // Acción al seleccionar un nombre de subcategoría
        subcategoryName.addActionListener(e -> {
            String selectedCategoryName = (String) categoryName.getSelectedItem();
            String selectedSubcategoryName = (String) subcategoryName.getSelectedItem();
            if (selectedCategoryName != null && selectedSubcategoryName != null) {
                HashMap<String, List<String>> subcategoryMap = categoryMap.get(selectedCategoryName);
                if (subcategoryMap != null) {
                    subcategoryCode.setSelectedItem(subcategoryMap.get(selectedSubcategoryName).get(0));
                }
            }
        });

        // Acción al seleccionar un código de subcategoría
        subcategoryCode.addActionListener(e -> {
            String selectedCategoryName = (String) categoryName.getSelectedItem();
            String selectedSubcategoryCode = (String) subcategoryCode.getSelectedItem();
            if (selectedCategoryName != null && selectedSubcategoryCode != null) {
                HashMap<String, List<String>> subcategoryMap = categoryMap.get(selectedCategoryName);
                if (subcategoryMap != null) {
                    for (Map.Entry<String, List<String>> subcategoryEntry : subcategoryMap.entrySet()) {
                        if (subcategoryEntry.getValue().get(0).equals(selectedSubcategoryCode)) {
                            subcategoryName.setSelectedItem(subcategoryEntry.getKey());
                            break;
                        }
                    }
                }
            }
        });
    }

    private void save(HashMap<String, HashMap<String, List<String>>> categoryMap) {
        if (validateFields()) {
            finalState = "activo";
            idestado = State_Controller.getEstadoId(finalState, Run.model);

            String selectedCategoryName = (String) cmbCategoryName.getSelectedItem();
            String selectedSubcategoryName = (String) cmbSubCategoryName.getSelectedItem();
            int subcategoryId = -1; // Valor por defecto en caso de que no se encuentre el ID

            // Buscar el ID de la subcategoría seleccionada por nombre o código
            if (selectedCategoryName != null && !selectedCategoryName.equals("--Seleccione--")) {
                HashMap<String, List<String>> subcategoryMap = categoryMap.get(selectedCategoryName);
                if (subcategoryMap != null) {
                    if (selectedSubcategoryName != null && subcategoryMap.containsKey(selectedSubcategoryName)) {
                        List<String> selectedSubcategoryValues = subcategoryMap.get(selectedSubcategoryName);
                        subcategoryId = selectedSubcategoryValues != null ? Integer.parseInt(selectedSubcategoryValues.get(1)) : -1;
                    }
                }
            }

            String code2 = txtCode.getText();
            String internalcode = txtInternalCode.getText();
            String name = txtName.getText();
            int sellprice = Integer.parseInt(txtSellPrice.getText());
            int minimumstock = Integer.parseInt(txtMinimumStock.getText());
            String unit = cmbUnit.getSelectedItem().toString();
            String description = atxtDescription.getText();
            Date expiredDate = convert_date.utilToSql(dchExpiredDate.getDate());

            product_controller.createProduct(code2, internalcode, name, sellprice, minimumstock, unit, description, expiredDate, iva, subcategoryId, idestado);

            JOptionPane.showMessageDialog(null, "Producto guardado exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el producto", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update(HashMap<String, HashMap<String, List<String>>> categoryMap) {
        if (validateFields()) {
            if (initialState.equals("activo") && finalState.equals("inactivo")) {
                if (txtInternalCode.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto para anular.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                } else {
                    int respuesta = JOptionPane.showConfirmDialog(this, "El producto será desactivado", "Desactivar producto?", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        idestado = State_Controller.getEstadoId(finalState, Run.model);
                    }
                }
            } else if (initialState.equals("inactivo") && finalState.equals("activo")) {
                if (txtInternalCode.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto para activar.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                } else {
                    int respuesta = JOptionPane.showConfirmDialog(this, "El producto será activado", "Activar producto?", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        idestado = State_Controller.getEstadoId(finalState, Run.model);
                    }
                }
            } else {
                idestado = State_Controller.getEstadoId(initialState, Run.model);
            }

            String selectedCategoryName = (String) cmbCategoryName.getSelectedItem();
            String selectedSubcategoryName = (String) cmbSubCategoryName.getSelectedItem();
            int subcategoryId = -1; // Valor por defecto en caso de que no se encuentre el ID

            // Buscar el ID de la subcategoría seleccionada por nombre o código
            if (selectedCategoryName != null && !selectedCategoryName.equals("--Seleccione--")) {
                HashMap<String, List<String>> subcategoryMap = categoryMap.get(selectedCategoryName);
                if (subcategoryMap != null) {
                    if (selectedSubcategoryName != null && subcategoryMap.containsKey(selectedSubcategoryName)) {
                        List<String> selectedSubcategoryValues = subcategoryMap.get(selectedSubcategoryName);
                        subcategoryId = selectedSubcategoryValues != null ? Integer.parseInt(selectedSubcategoryValues.get(1)) : -1;
                    }
                }
            }

            int id = Integer.parseInt(code);
            String code2 = txtCode.getText();
            String internalcode = txtInternalCode.getText();
            String name = txtName.getText();
            int sellprice = Integer.parseInt(txtSellPrice.getText());
            int minimumstock = Integer.parseInt(txtMinimumStock.getText());
            String unit = cmbUnit.getSelectedItem().toString();
            String description = atxtDescription.getText();
            Date expiredDate = convert_date.utilToSql(dchExpiredDate.getDate());

            product_controller.updateProduct(id, code2, internalcode, name, sellprice, minimumstock, unit, description, expiredDate, iva, subcategoryId, idestado);
            
            JOptionPane.showMessageDialog(null, "Categoria editada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al editar la categoria", "Error!", JOptionPane.ERROR_MESSAGE);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbCategoryCode = new javax.swing.JComboBox<>();
        cmbCategoryName = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbSubCategoryCode = new javax.swing.JComboBox<>();
        cmbSubCategoryName = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbUnit = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtSellPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        atxtDescription = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        rbtn10 = new javax.swing.JRadioButton();
        rbtn5 = new javax.swing.JRadioButton();
        rbtn0 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMinimumStock = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        chbActive = new javax.swing.JCheckBox();
        txtInternalCode = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dchExpiredDate = new com.toedter.calendar.JDateChooser();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Nuevo Producto");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Categoria:");

        cmbCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Subcategoria:");

        cmbSubCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbSubCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Unidad:");

        cmbUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("P. de Venta:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Descripción:");

        atxtDescription.setColumns(20);
        atxtDescription.setRows(5);
        jScrollPane1.setViewportView(atxtDescription);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Codigo:");

        buttonGroup1.add(rbtn10);
        rbtn10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtn10.setText("10%");
        rbtn10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn10ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtn5);
        rbtn5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtn5.setText("5%");
        rbtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn5ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtn0);
        rbtn0.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtn0.setText("0%");
        rbtn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn0ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Iva:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Cant. Min.:");

        txtMinimumStock.setText("0");

        chbActive.setText("Activo");
        chbActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbActiveActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Cod. Interno:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("F. Venc.:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtName)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbtn0)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbtn5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbtn10))
                                    .addComponent(txtInternalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(dchExpiredDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMinimumStock, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCode)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chbActive)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtInternalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rbtn0)
                    .addComponent(rbtn5)
                    .addComponent(rbtn10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMinimumStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(dchExpiredDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbActive)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbSubCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSubCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSubCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSubCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap(14, Short.MAX_VALUE))
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

    private void chbActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbActiveActionPerformed
        if (chbActive.isSelected()) {
            finalState = "activo";
        }
        if (!chbActive.isSelected()) {
            finalState = "inactivo";
        }
    }//GEN-LAST:event_chbActiveActionPerformed

    private void rbtn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn0ActionPerformed
        iva = 0;
    }//GEN-LAST:event_rbtn0ActionPerformed

    private void rbtn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn5ActionPerformed
        iva = 5;
    }//GEN-LAST:event_rbtn5ActionPerformed

    private void rbtn10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn10ActionPerformed
        iva = 10;
    }//GEN-LAST:event_rbtn10ActionPerformed

    private boolean validateFields() {
        if (cmbSubCategoryName.getSelectedItem() == "--Seleccione--" || cmbSubCategoryName.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una subcategoria del combobox", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtCode.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el código del código.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtName.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del producto.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtSellPrice.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el precio del producto.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea atxtDescription;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chbActive;
    private javax.swing.JComboBox<String> cmbCategoryCode;
    private javax.swing.JComboBox<String> cmbCategoryName;
    private javax.swing.JComboBox<String> cmbSubCategoryCode;
    private javax.swing.JComboBox<String> cmbSubCategoryName;
    private javax.swing.JComboBox<String> cmbUnit;
    private com.toedter.calendar.JDateChooser dchExpiredDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton rbtn0;
    private javax.swing.JRadioButton rbtn10;
    private javax.swing.JRadioButton rbtn5;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtInternalCode;
    private javax.swing.JTextField txtMinimumStock;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSellPrice;
    // End of variables declaration//GEN-END:variables
}
