package View.Products;

import Config.CustomTabbedPaneUI;
import Config.Run;
import Controller.Category_Controller;
import Model.Category_Model;
import Controller.State_Controller;
import Controller.Subcategory_Controller;
import Controller.Subsubcategory_Controller;
import Model.Subcategory_Model;
import Model.Subsubcategory_Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public final class Sorter extends javax.swing.JDialog {

    Category_Controller category_controller = new Category_Controller();
    Subcategory_Controller subcategory_controller = new Subcategory_Controller();
    Subsubcategory_Controller subsubcategory_controller = new Subsubcategory_Controller();
    Category_Model category_model = new Category_Model();
    Subcategory_Model subcategory_model = new Subcategory_Model();
    Subsubcategory_Model subsubcategory_model = new Subsubcategory_Model();

    private String id = "";
    private String initialState = "", finalState = "", stateFilter = "activo", tabSelected = "category";
    private int idestado, fk_category, fk_subcategory;
    private HashMap<String, List<String>> categoryMap, subcategoryMap;

    public Sorter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        jTabbedPane1.setUI(new CustomTabbedPaneUI());
        pmnuSorter.add(pnlPmnuSorter);
        showCategories("", stateFilter);
        rbtnCategoryActive.setSelected(true);
        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            onTabChange();
        });
        chbCategoryActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSelected = chbCategoryActive.isSelected();
                if (isSelected) {
//                    stateFilter = "activo";
//                    finalState = "activo";
                } else {
//                    stateFilter = "inactivo";
//                    finalState = "inactivo";
                }
            }
        });
        subCategoryCombobox();
        subsubCategoryCombobox();
    }

    private void showCategories(String search, String stateFilter) {
        try {
            DefaultTableModel model;
            model = category_controller.showCategories(search, stateFilter);
            tblCategory.setModel(model);
            ocultar_columnas(tblCategory);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showSubcategories(String search, String stateFilter) {
        try {
            DefaultTableModel model;
            model = subcategory_controller.showSubcategories(search, stateFilter);
            tblSubcategory.setModel(model);
            ocultar_columnas(tblSubcategory);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showSubsubcategories(String search, String stateFilter) {
        try {
            DefaultTableModel model;
            model = subsubcategory_controller.showSubsubcategories(search, stateFilter);
            tblSubsubcategory.setModel(model);
            ocultar_columnas(tblSubsubcategory);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void ocultar_columnas(JTable table) {
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void cleanTab(String excludeTab) {
        if (!excludeTab.equals("category")) {
            txtCategoryName.setText("");
            txtCategoryCode.setText("");
            txtCategoryDescription.setText("");
        }
        if (!excludeTab.equals("subcategory")) {
            cmbCategoryCode.setSelectedIndex(0);
            cmbCategoryName.setSelectedIndex(0);
            txtSubcategoryName.setText("");
            txtSubcategoryCode.setText("");
            txtSubcategoryDescription.setText("");
        }
        if (!excludeTab.equals("subsubcategory")) {
            cmbCategoryCode1.setSelectedIndex(0);
            cmbCategoryName1.setSelectedIndex(0);
            cmbSubCategoryCode.setSelectedIndex(0);
            cmbSubCategoryName.setSelectedIndex(0);
            txtSubsubcategoryName.setText("");
            txtSubsubcategoryCode.setText("");
            txtSubsubcategoryDescription.setText("");
        }
        id = "";
    }

    //Fill the comboboxes of categories in the subcategories tab
    private void subCategoryCombobox() {
        categoryMap = subcategory_controller.fillCategoryCombobox();
        cmbCategoryName.removeAllItems();
        cmbCategoryCode.removeAllItems();
        cmbCategoryName.addItem("--Seleccione--");
        cmbCategoryCode.addItem("--Seleccione--");

        for (String name : categoryMap.keySet()) {
            cmbCategoryName.addItem(name);
        }

        for (List<String> values : categoryMap.values()) {
            cmbCategoryCode.addItem(values.get(0));
        }

        cmbCategoryName.addActionListener((ActionEvent e) -> {
            String selectedName = (String) cmbCategoryName.getSelectedItem();
            if (selectedName != null) {
                List<String> correspondingValues = categoryMap.get(selectedName);
                if (correspondingValues != null) {
                    cmbCategoryCode.setSelectedItem(correspondingValues.get(0));
                }
            }
        });

        cmbCategoryCode.addActionListener((ActionEvent e) -> {
            String selectedCode = (String) cmbCategoryCode.getSelectedItem();
            if (selectedCode != null) {
                for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                    if (entry.getValue().get(0).equals(selectedCode)) {
                        cmbCategoryName.setSelectedItem(entry.getKey());
                        break;
                    }
                }
            }
        });
    }

    //Fill the comboboxes of categories in the subsubcategories tab
    private void subsubCategoryCombobox() {
        categoryMap = subcategory_controller.fillCategoryCombobox();
        cmbCategoryName1.removeAllItems();
        cmbCategoryCode1.removeAllItems();
        cmbCategoryName1.addItem("--Seleccione--");
        cmbCategoryCode1.addItem("--Seleccione--");

        for (String name : categoryMap.keySet()) {
            cmbCategoryName1.addItem(name);
        }

        for (List<String> values : categoryMap.values()) {
            cmbCategoryCode1.addItem(values.get(0));
        }

        cmbCategoryName1.addActionListener((ActionEvent e) -> {
            String selectedName = (String) cmbCategoryName1.getSelectedItem();
            if (selectedName != null) {
                List<String> correspondingValues = categoryMap.get(selectedName);
                if (correspondingValues != null) {
                    cmbCategoryCode1.setSelectedItem(correspondingValues.get(0));
                    updateSubcategoryComboboxes();
                }
            }
        });

        cmbCategoryCode1.addActionListener((ActionEvent e) -> {
            String selectedCode = (String) cmbCategoryCode1.getSelectedItem();
            if (selectedCode != null) {
                for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                    if (entry.getValue().get(0).equals(selectedCode)) {
                        cmbCategoryName1.setSelectedItem(entry.getKey());
                        updateSubcategoryComboboxes();
                        break;
                    }
                }
            }
        });
    }

    // Fill the comboboxes of subcategories in the subsubcategories tab based on the selected category
    private void updateSubcategoryComboboxes() {
        String selectedName = (String) cmbCategoryName1.getSelectedItem();
        String selectedCode = (String) cmbCategoryCode1.getSelectedItem();
        cmbSubCategoryName.removeAllItems();
        cmbSubCategoryCode.removeAllItems();
        cmbSubCategoryName.addItem("--Seleccione--");
        cmbSubCategoryCode.addItem("--Seleccione--");

        String categoryId = null;
        if (selectedName != null && !selectedName.equals("--Seleccione--")) {
            categoryId = categoryMap.get(selectedName).get(1);
        } else if (selectedCode != null && !selectedCode.equals("--Seleccione--")) {
            for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                if (entry.getValue().get(0).equals(selectedCode)) {
                    categoryId = entry.getValue().get(1);
                    break;
                }
            }
        }

        // Load subcategories for the selected category
        if (categoryId != null) {
            subcategoryMap = subsubcategory_controller.fillSubcategoryCombobox(Integer.parseInt(categoryId));
            cmbSubCategoryName.removeAllItems();
            cmbSubCategoryCode.removeAllItems();
            cmbSubCategoryName.addItem("--Seleccione--");
            cmbSubCategoryCode.addItem("--Seleccione--");

            for (String name : subcategoryMap.keySet()) {
                cmbSubCategoryName.addItem(name);
            }

            for (List<String> values : subcategoryMap.values()) {
                cmbSubCategoryCode.addItem(values.get(0));
            }

            cmbSubCategoryName.addActionListener((ActionEvent e) -> {
                String selectedSubCategoryName = (String) cmbSubCategoryName.getSelectedItem();
                if (selectedSubCategoryName != null) {
                    List<String> correspondingValues = subcategoryMap.get(selectedSubCategoryName);
                    if (correspondingValues != null) {
                        cmbSubCategoryCode.setSelectedItem(correspondingValues.get(0));
                        updateSubsubcategoryComboboxes();
                    }
                }
            });

            cmbSubCategoryCode.addActionListener((ActionEvent e) -> {
                String selectedSubCategoryCode = (String) cmbSubCategoryCode.getSelectedItem();
                if (selectedSubCategoryCode != null) {
                    for (Map.Entry<String, List<String>> entry : subcategoryMap.entrySet()) {
                        if (entry.getValue().get(0).equals(selectedSubCategoryCode)) {
                            cmbSubCategoryName.setSelectedItem(entry.getKey());
                            updateSubsubcategoryComboboxes();
                            break;
                        }
                    }
                }
            });
        }
    }

    //Update subcategories in subsubcategories tab
    private void updateSubsubcategoryComboboxes() {
        // Obtener el nombre o código de la subcategoría seleccionada
        String selectedName = (String) cmbSubCategoryName.getSelectedItem();
        String selectedCode = (String) cmbSubCategoryCode.getSelectedItem();

        // Obtener el ID de la subcategoría seleccionada
        String subcategoryId = null;
        if (selectedName != null && !selectedName.equals("--Seleccione--")) {
            subcategoryId = subcategoryMap.get(selectedName).get(1);
        } else if (selectedCode != null && !selectedCode.equals("--Seleccione--")) {
            for (Map.Entry<String, List<String>> entry : subcategoryMap.entrySet()) {
                if (entry.getValue().get(0).equals(selectedCode)) {
                    subcategoryId = entry.getValue().get(1);
                    break;
                }
            }
        }
    }

    private void onTabChange() {
        int selectedTabIndex = jTabbedPane1.getSelectedIndex();
        switch (selectedTabIndex) {
            case 0:
                // Categories tab selected
                rbtnCategoryActive.setSelected(true);
                stateFilter = "activo";
                tabSelected = "category";
                cleanTab("category");
                showCategories("", stateFilter);
                break;
            case 1:
                // Subcategories tab selected
                rbtnSubcategoryActive.setSelected(true);
                stateFilter = "activo";
                tabSelected = "subcategory";
                cleanTab("subcategory");
                showSubcategories("", stateFilter);
                break;
            case 2:
                // Sub-subcategories tab selected
                rbtnSubsubcategoryActive.setSelected(true);
                stateFilter = "activo";
                tabSelected = "subsubcategory";
                cleanTab("subsubcategory");
                showSubsubcategories("", stateFilter);
                break;
        }
    }

    //Save the new category, subcategory or subsubcategory
    private void save() {
        if (tabSelected.equals("category")) {
            if (validateFields()) {
                finalState = "activo";
                idestado = State_Controller.getEstadoId(finalState, Run.model);

                category_model.setCodigo(txtCategoryCode.getText());
                category_model.setCategorias(txtCategoryName.getText());
                category_model.setDescripcion(txtCategoryDescription.getText());
                category_controller.createCategory(category_model, idestado);

                showCategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Categoria guardada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la categoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (tabSelected.equals("subcategory")) {
            if (validateFields()) {
                finalState = "activo";
                idestado = State_Controller.getEstadoId(finalState, Run.model);

                String selectedName = (String) cmbCategoryName.getSelectedItem();
                String selectedCode = (String) cmbCategoryCode.getSelectedItem();
                int categoryId = -1; // Valor por defecto en caso de que no se encuentre el ID

                if (selectedName != null && categoryMap.containsKey(selectedName)) {
                    List<String> selectedCategoryValues = categoryMap.get(selectedName);
                    categoryId = selectedCategoryValues != null ? Integer.parseInt(selectedCategoryValues.get(1)) : -1;
                } else if (selectedCode != null) {
                    for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                        if (entry.getValue().get(0).equals(selectedCode)) {
                            categoryId = Integer.parseInt(entry.getValue().get(1));
                            break;
                        }
                    }
                }

                subcategory_model.setCodigo(txtSubcategoryCode.getText());
                subcategory_model.setSubcategorias(txtSubcategoryName.getText());
                subcategory_model.setDescripcion(txtSubcategoryDescription.getText());
                subcategory_controller.createSubcategory(subcategory_model, categoryId, idestado);

                showSubcategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Subcategoria guardada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la subcategoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (tabSelected.equals("subsubcategory")) {
            if (validateFields()) {
                finalState = "activo";
                idestado = State_Controller.getEstadoId(finalState, Run.model);

                String selectedName = (String) cmbSubCategoryName.getSelectedItem();
                String selectedCode = (String) cmbSubCategoryCode.getSelectedItem();
                int subcategoryId = -1; // Valor por defecto en caso de que no se encuentre el ID

                if (selectedName != null && subcategoryMap.containsKey(selectedName)) {
                    List<String> selectedCategoryValues = subcategoryMap.get(selectedName);
                    subcategoryId = selectedCategoryValues != null ? Integer.parseInt(selectedCategoryValues.get(1)) : -1;
                } else if (selectedCode != null) {
                    for (Map.Entry<String, List<String>> entry : subcategoryMap.entrySet()) {
                        if (entry.getValue().get(0).equals(selectedCode)) {
                            subcategoryId = Integer.parseInt(entry.getValue().get(1));
                            break;
                        }
                    }
                }

                subsubcategory_model.setCodigo(txtSubsubcategoryCode.getText());
                subsubcategory_model.setSubsubcategorias(txtSubsubcategoryName.getText());
                subsubcategory_model.setDescripcion(txtSubsubcategoryDescription.getText());
                subsubcategory_controller.createSubsubcategory(subsubcategory_model, subcategoryId, idestado);

                showSubsubcategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Sub-subcategoria guardada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar la sub-subcategoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Updaet the category, subcategory or subsubcategory
    private void update() {
        if (tabSelected.equals("category")) {
            if (validateFields()) {
                System.out.println("initial: " + initialState);
                System.out.println("final: " + finalState);
                if (initialState.equals("activo") && finalState.equals("inactivo")) {
                    if (txtCategoryCode.getText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Seleccione una categoria para anular.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int respuesta = JOptionPane.showConfirmDialog(this, "La categoria será desactivada", "Anular categoria?", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            idestado = State_Controller.getEstadoId(finalState, Run.model);

                            System.out.println("categoryId: " + idestado);

//                            category_model.setIdcategorias(Integer.parseInt(id));
//                            category_controller.disableCategory(category_model, idestado);
//                            JOptionPane.showMessageDialog(null, "Categoria anulada correctamente.", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else if (initialState.equals("inactivo") && finalState.equals("activo")) {
                    if (txtCategoryCode.getText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Seleccione una categoria para activar.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int respuesta = JOptionPane.showConfirmDialog(this, "La categoria será activada", "Activar categoria?", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            idestado = State_Controller.getEstadoId(finalState, Run.model);

                            System.out.println("categoryId: " + idestado);

//                            category_model.setIdcategorias(Integer.parseInt(id));
//                            category_controller.disableCategory(category_model, idestado);
//                            JOptionPane.showMessageDialog(null, "Categoria anulada correctamente.", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }else{
                    idestado = State_Controller.getEstadoId(initialState, Run.model);
                }

//                idestado = State_Controller.getEstadoId(finalState, Run.model);
                category_model.setIdcategorias(Integer.parseInt(id));
                category_model.setCodigo(txtCategoryCode.getText());
                category_model.setCategorias(txtCategoryName.getText());
                category_model.setDescripcion(txtCategoryDescription.getText());
                category_controller.updateCategory(category_model, idestado);

                showCategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Categoria editada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al editar la categoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (tabSelected.equals("subcategory")) {
            if (validateFields()) {
//                estado = "activo";
                idestado = State_Controller.getEstadoId(finalState, Run.model);

                subcategory_model.setIdsubcategorias(Integer.parseInt(id));
                subcategory_model.setCodigo(txtSubcategoryCode.getText());
                subcategory_model.setSubcategorias(txtSubcategoryName.getText());
                subcategory_model.setDescripcion(txtSubcategoryDescription.getText());
                subcategory_controller.updateSubcategory(subcategory_model, fk_category, idestado);

                showSubcategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Subcategoria editada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al editar la Subcategoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (tabSelected.equals("subsubcategory")) {
            if (validateFields()) {
//                estado = "activo";
                idestado = State_Controller.getEstadoId(finalState, Run.model);

                subsubcategory_model.setIdsubsubcategorias(Integer.parseInt(id));
                subsubcategory_model.setCodigo(txtSubsubcategoryCode.getText());
                subsubcategory_model.setSubsubcategorias(txtSubsubcategoryName.getText());
                subsubcategory_model.setDescripcion(txtSubsubcategoryDescription.getText());
                subsubcategory_controller.updateSubsubcategory(subsubcategory_model, fk_subcategory, idestado);

                showSubsubcategories("", stateFilter);
                JOptionPane.showMessageDialog(null, "Sub-subcategoria editada exitosamente!", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al editar la sub-subcategoria", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void disableCategory() {
        System.out.println("tab: " + tabSelected);
        if (tabSelected.equals("category")) {

        }
        if (tabSelected.equals("subcategory")) {
            if (txtSubcategoryCode.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione una subcategoria para anular.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            } else {
                int respuesta = JOptionPane.showConfirmDialog(this, "La subcategoria será desactivada", "Anular subcategoria?", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
//                    estado = "inactivo";
                    idestado = State_Controller.getEstadoId(finalState, Run.model);

                    subcategory_model.setIdsubcategorias(Integer.parseInt(id));
                    subcategory_controller.disableSubcategory(subcategory_model, idestado);
                    JOptionPane.showMessageDialog(null, "Subcategoria anulada correctamente.", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if (tabSelected.equals("subsubcategory")) {
            if (txtSubsubcategoryCode.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione una sub-subcategoria para anular.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            } else {
                int respuesta = JOptionPane.showConfirmDialog(this, "La sub-subcategoria será desactivada", "Anular sub-subcategoria?", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
//                    estado = "inactivo";
                    idestado = State_Controller.getEstadoId(finalState, Run.model);

                    subsubcategory_model.setIdsubsubcategorias(Integer.parseInt(id));
                    subsubcategory_controller.disableSubsubcategory(subsubcategory_model, idestado);
                    JOptionPane.showMessageDialog(null, "Sub-subcategoria anulada correctamente.", "Hecho!", JOptionPane.INFORMATION_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pmnuSorter = new javax.swing.JPopupMenu();
        pnlPmnuSorter = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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
        chbCategoryActive = new javax.swing.JCheckBox();
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
        chbSubcategoryActive = new javax.swing.JCheckBox();
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
        chbSubsubCategoryActive = new javax.swing.JCheckBox();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnAbort = new javax.swing.JButton();

        jLabel2.setText("Anular");
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlPmnuSorterLayout = new javax.swing.GroupLayout(pnlPmnuSorter);
        pnlPmnuSorter.setLayout(pnlPmnuSorterLayout);
        pnlPmnuSorterLayout.setHorizontalGroup(
            pnlPmnuSorterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlPmnuSorterLayout.setVerticalGroup(
            pnlPmnuSorterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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
        tblCategory.setComponentPopupMenu(pmnuSorter);
        tblCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoryMouseClicked(evt);
            }
        });
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

        chbCategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chbCategoryActive.setText("Activo");
        chbCategoryActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCategoryActiveActionPerformed(evt);
            }
        });

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
                .addGroup(pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCategoryLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbCategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCategoryLayout.createSequentialGroup()
                        .addComponent(chbCategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Categorias", pnlCategory);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Categoria:");

        txtSubcategoryName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubcategoryNameKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nombre:");

        cmbCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Descripcion:");

        txtSubcategoryDescription.setColumns(20);
        txtSubcategoryDescription.setRows(5);
        txtSubcategoryDescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubcategoryDescriptionKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtSubcategoryDescription);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Codigo:");

        txtSubcategoryCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubcategoryCodeKeyTyped(evt);
            }
        });

        cmbCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSubcategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSubcategory.setComponentPopupMenu(pmnuSorter);
        tblSubcategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSubcategoryMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSubcategory);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));

        buttonGroup1.add(rbtnSubcategoryActive);
        rbtnSubcategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryActive.setText("Solo activos");
        rbtnSubcategoryActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubcategoryActiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnSubcategoryInactive);
        rbtnSubcategoryInactive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryInactive.setText("Solo inactivos");
        rbtnSubcategoryInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubcategoryInactiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnSubcategoryAll);
        rbtnSubcategoryAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubcategoryAll.setText("Todos");
        rbtnSubcategoryAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubcategoryAllActionPerformed(evt);
            }
        });

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

        chbSubcategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chbSubcategoryActive.setText("Activo");

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
                .addGroup(pnlSubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSubcategoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubcategoryLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbSubcategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubcategoryLayout.createSequentialGroup()
                        .addComponent(chbSubcategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Subcategorias", pnlSubcategory);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Categoria:");

        txtSubsubcategoryName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubsubcategoryNameKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Nombre:");

        cmbCategoryCode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Descripcion:");

        txtSubsubcategoryDescription.setColumns(20);
        txtSubsubcategoryDescription.setRows(5);
        txtSubsubcategoryDescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubsubcategoryDescriptionKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtSubsubcategoryDescription);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Codigo:");

        txtSubsubcategoryCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubsubcategoryCodeKeyTyped(evt);
            }
        });

        cmbCategoryName1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSubsubcategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSubsubcategory.setComponentPopupMenu(pmnuSorter);
        tblSubsubcategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSubsubcategoryMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblSubsubcategory);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));

        buttonGroup1.add(rbtnSubsubcategoryActive);
        rbtnSubsubcategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryActive.setText("Solo activos");
        rbtnSubsubcategoryActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubsubcategoryActiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnSubsubcategoryInactive);
        rbtnSubsubcategoryInactive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryInactive.setText("Solo inactivos");
        rbtnSubsubcategoryInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubsubcategoryInactiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnSubsubcategoryAll);
        rbtnSubsubcategoryAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbtnSubsubcategoryAll.setText("Todos");
        rbtnSubsubcategoryAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSubsubcategoryAllActionPerformed(evt);
            }
        });

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

        cmbSubCategoryCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--" }));

        cmbSubCategoryName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--" }));
        cmbSubCategoryName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSubCategoryNameActionPerformed(evt);
            }
        });

        chbSubsubCategoryActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chbSubsubCategoryActive.setText("Activo");

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
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubsubcategoryLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbSubsubCategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubsubcategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubsubcategoryLayout.createSequentialGroup()
                        .addComponent(chbSubsubCategoryActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        if (id.equals("")) {
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

    private void txtSubcategoryCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubcategoryCodeKeyTyped
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
        if (txtSubcategoryCode.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 4 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubcategoryCodeKeyTyped

    private void txtSubcategoryNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubcategoryNameKeyTyped
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
        if (txtSubcategoryName.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 70 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubcategoryNameKeyTyped

    private void txtSubcategoryDescriptionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubcategoryDescriptionKeyTyped
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
        if (txtSubcategoryDescription.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 2.000 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubcategoryDescriptionKeyTyped

    private void txtSubsubcategoryDescriptionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubsubcategoryDescriptionKeyTyped
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
        if (txtSubsubcategoryDescription.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 2.000 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubsubcategoryDescriptionKeyTyped

    private void txtSubsubcategoryCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubsubcategoryCodeKeyTyped
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
        if (txtSubsubcategoryCode.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 4 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubsubcategoryCodeKeyTyped

    private void txtSubsubcategoryNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubsubcategoryNameKeyTyped
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
        if (txtSubsubcategoryName.getText().length() > numerocaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo 70 caracteres", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtSubsubcategoryNameKeyTyped

    private void cmbSubCategoryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSubCategoryNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSubCategoryNameActionPerformed

    private void tblCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoryMouseClicked
        int select = tblCategory.rowAtPoint(evt.getPoint());
        tblCategory.setRowSelectionInterval(select, select);

        id = tblCategory.getValueAt(select, 0).toString();
        txtCategoryCode.setText(String.valueOf(tblCategory.getValueAt(select, 1)));
        txtCategoryName.setText(String.valueOf(tblCategory.getValueAt(select, 2)));
        txtCategoryDescription.setText(String.valueOf(tblCategory.getValueAt(select, 3)));

        if (String.valueOf(tblCategory.getValueAt(select, 4)).equals("activo")) {
            chbCategoryActive.setSelected(true);
            initialState = tblCategory.getValueAt(select, 4).toString();
        } else {
            chbCategoryActive.setSelected(false);
            initialState = tblCategory.getValueAt(select, 4).toString();
        }

        showCategories("", stateFilter);
    }//GEN-LAST:event_tblCategoryMouseClicked

    private void tblSubcategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSubcategoryMouseClicked
        int select = tblSubcategory.rowAtPoint(evt.getPoint());
        tblSubcategory.setRowSelectionInterval(select, select);

        id = tblSubcategory.getValueAt(select, 0).toString();
        txtSubcategoryCode.setText(String.valueOf(tblSubcategory.getValueAt(select, 1)));
        txtSubcategoryName.setText(String.valueOf(tblSubcategory.getValueAt(select, 2)));
        txtSubcategoryDescription.setText(String.valueOf(tblSubcategory.getValueAt(select, 3)));
        fk_category = Integer.parseInt(tblSubcategory.getValueAt(select, 4).toString());
        cmbCategoryName.setSelectedItem(String.valueOf(tblSubcategory.getValueAt(select, 5)));

        if (String.valueOf(tblSubcategory.getValueAt(select, 6)).equals("activo")) {
            chbSubcategoryActive.setSelected(true);
            initialState = tblSubcategory.getValueAt(select, 6).toString();
        } else {
            chbSubcategoryActive.setSelected(false);
            initialState = tblSubcategory.getValueAt(select, 6).toString();
        }

        showSubcategories("", stateFilter);
    }//GEN-LAST:event_tblSubcategoryMouseClicked

    private void tblSubsubcategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSubsubcategoryMouseClicked
        int select = tblSubsubcategory.rowAtPoint(evt.getPoint());
        tblSubsubcategory.setRowSelectionInterval(select, select);

        id = tblSubsubcategory.getValueAt(select, 0).toString();
        txtSubsubcategoryCode.setText(String.valueOf(tblSubsubcategory.getValueAt(select, 1)));
        txtSubsubcategoryName.setText(String.valueOf(tblSubsubcategory.getValueAt(select, 2)));
        txtSubsubcategoryDescription.setText(String.valueOf(tblSubsubcategory.getValueAt(select, 3)));
        fk_subcategory = Integer.parseInt(tblSubsubcategory.getValueAt(select, 4).toString());
        cmbCategoryName1.setSelectedItem(String.valueOf(tblSubsubcategory.getValueAt(select, 6)));
        cmbSubCategoryName.setSelectedItem(String.valueOf(tblSubsubcategory.getValueAt(select, 5)));

        if (String.valueOf(tblSubsubcategory.getValueAt(select, 7)).equals("activo")) {
            chbSubsubCategoryActive.setSelected(true);
            initialState = tblSubsubcategory.getValueAt(select, 7).toString();
        } else {
            chbSubsubCategoryActive.setSelected(false);
            initialState = tblSubsubcategory.getValueAt(select, 7).toString();
        }

        showSubsubcategories("", stateFilter);
    }//GEN-LAST:event_tblSubsubcategoryMouseClicked

    private void rbtnSubcategoryActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubcategoryActiveActionPerformed
        stateFilter = "activo";
        showSubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubcategoryActiveActionPerformed

    private void rbtnSubcategoryInactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubcategoryInactiveActionPerformed
        stateFilter = "inactivo";
        showSubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubcategoryInactiveActionPerformed

    private void rbtnSubcategoryAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubcategoryAllActionPerformed
        stateFilter = "todos";
        showSubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubcategoryAllActionPerformed

    private void rbtnSubsubcategoryActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubsubcategoryActiveActionPerformed
        stateFilter = "activo";
        showSubsubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubsubcategoryActiveActionPerformed

    private void rbtnSubsubcategoryInactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubsubcategoryInactiveActionPerformed
        stateFilter = "inactivo";
        showSubsubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubsubcategoryInactiveActionPerformed

    private void rbtnSubsubcategoryAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSubsubcategoryAllActionPerformed
        stateFilter = "todos";
        showSubsubcategories("", stateFilter);
    }//GEN-LAST:event_rbtnSubsubcategoryAllActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        disableCategory();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void chbCategoryActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCategoryActiveActionPerformed
        if (chbCategoryActive.isSelected()) {
//            stateFilter = "activo";
            finalState = "activo";
        }
        if (!chbCategoryActive.isSelected()) {
//            stateFilter = "activo";
            finalState = "inactivo";
        }
    }//GEN-LAST:event_chbCategoryActiveActionPerformed

    private boolean validateFields() {
        if (tabSelected.equals("category")) {
            if (txtCategoryCode.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el código de la Categoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtCategoryName.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Categoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        if (tabSelected.equals("subcategory")) {
            if (cmbCategoryName.getSelectedItem() == "--Seleccione--" || cmbCategoryName.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoria del combobox", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtSubcategoryCode.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el código de la Subcategoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtSubcategoryName.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Subcategoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        if (tabSelected.equals("subsubcategory")) {
            if (cmbSubCategoryName.getSelectedItem() == "--Seleccione--" || cmbSubCategoryName.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una subcategoria del combobox", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtSubsubcategoryCode.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el código de la Sub-subcategoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtSubsubcategoryName.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Sub-subcategoria.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                return false;
            }
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
    private javax.swing.JCheckBox chbCategoryActive;
    private javax.swing.JCheckBox chbSubcategoryActive;
    private javax.swing.JCheckBox chbSubsubCategoryActive;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPopupMenu pmnuSorter;
    private javax.swing.JPanel pnlCategory;
    private javax.swing.JPanel pnlCategoryRadiobuttons;
    private javax.swing.JPanel pnlPmnuSorter;
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
