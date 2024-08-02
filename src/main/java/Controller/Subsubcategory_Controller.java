package Controller;

import Model.Subsubcategory_Model;
import Services.Subsubcategory_Services;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Controller {

    private final Subsubcategory_Services subsubcategory_services = new Subsubcategory_Services();
    private final Subsubcategory_Model model = new Subsubcategory_Model();

    public Subsubcategory_Controller() {
    }

    public boolean createSubsubcategory(String code, String subsubcategory, String description, int foreignKey, int foreignKey2) {
        model.setCodigo(code);
        model.setSubsubcategorias(subsubcategory);
        model.setDescripcion(description);
        return subsubcategory_services.addSubsubcategory(model, foreignKey, foreignKey2);
    }

    public boolean updateSubsubcategory(int id, String code, String subsubcategory, String description, int foreignKey, int foreignKey2) {
        model.setIdsubsubcategorias(id);
        model.setCodigo(code);
        model.setSubsubcategorias(subsubcategory);
        model.setDescripcion(description);
        return subsubcategory_services.updateSubsubcategory(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubsubcategory(int id) {
        model.setIdsubsubcategorias(id);
        return subsubcategory_services.deleteSubsubcategory(model);
    }

    public DefaultTableModel showSubsubcategories(String search, String stateFilter) {
        return subsubcategory_services.showSubsubcategories(search, stateFilter);
    }

    public HashMap<String, List<String>> fillSubcategoryCombobox(int foreignKey) {
        return subsubcategory_services.fillSubcategoryCombobox(foreignKey);
    }
}
