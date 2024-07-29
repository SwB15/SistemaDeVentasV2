
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

    public Subcategory_Controller() {
    }

    public boolean createSubcategory(Subcategory_Model model, int foreignKey, int foreignKey2) {
        return subcategory_services.addSubcategory(model, foreignKey, foreignKey2);
    }

    public boolean updateSubcategory(Subcategory_Model model, int foreignKey, int foreignKey2) {
        return subcategory_services.updateSubcategory(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubcategory(Subcategory_Model model) {
        return subcategory_services.deleteSubcategory(model);
    }

    public boolean disableSubcategory(Subcategory_Model model, int foreignKey) {
        return subcategory_services.disableSubcategory(model, foreignKey);
    }

    public DefaultTableModel showSubcategories(String search, String stateFilter) {
        return subcategory_services.showSubcategories(search, stateFilter);
    }
    
    public HashMap<String, List<String>> fillCategoryCombobox(){
        return subcategory_services.fillCategoryCombobox();
    }
}
