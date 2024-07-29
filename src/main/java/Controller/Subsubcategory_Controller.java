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

    public Subsubcategory_Controller() {
    }

    public boolean createSubsubcategory(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        return subsubcategory_services.addSubsubcategory(model, foreignKey, foreignKey2);
    }

    public boolean updateSubsubcategory(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        return subsubcategory_services.updateSubsubcategory(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubsubcategory(Subsubcategory_Model model) {
        return subsubcategory_services.deleteSubsubcategory(model);
    }

    public boolean disableSubsubcategory(Subsubcategory_Model model, int foreignKey) {
        return subsubcategory_services.disableSubsubcategory(model, foreignKey);
    }

    public DefaultTableModel showSubsubcategories(String search, String stateFilter) {
        return subsubcategory_services.showSubsubcategories(search, stateFilter);
    }
    
    public HashMap<String, List<String>> fillSubcategoryCombobox(int foreignKey){
        return subsubcategory_services.fillSubcategoryCombobox(foreignKey);
    }
}
