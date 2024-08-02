package Controller;

import Model.Subcategory_Model;
import Services.Subcategory_Services;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subcategory_Controller {

    private final Subcategory_Services subcategory_services = new Subcategory_Services();
    Subcategory_Model model = new Subcategory_Model();

    public Subcategory_Controller() {
    }

    public boolean createSubcategory(String code, String subcategory, String description, int foreignKey, int foreignKey2) {
        model.setCodigo(code);
        model.setSubcategorias(subcategory);
        model.setDescripcion(description);
        return subcategory_services.addSubcategory(model, foreignKey, foreignKey2);
    }

    public boolean updateSubcategory(int id, String code, String subcategory, String description, int foreignKey, int foreignKey2) {
        model.setIdsubcategorias(id);
        model.setCodigo(code);
        model.setSubcategorias(subcategory);
        model.setDescripcion(description);
        return subcategory_services.updateSubcategory(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubcategory(int id) {
        model.setIdsubcategorias(id);
        return subcategory_services.deleteSubcategory(model);
    }

    public DefaultTableModel showSubcategories(String search, String stateFilter) {
        return subcategory_services.showSubcategories(search, stateFilter);
    }

    public HashMap<String, List<String>> fillCategoryCombobox() {
        return subcategory_services.fillCategoryCombobox();
    }
}
