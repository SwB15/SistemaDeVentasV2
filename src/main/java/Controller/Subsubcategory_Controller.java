package Controller;

import Model.Subsubcategory_Model;
import Services.Subsubcategory_Services;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Controller {

    private final Subsubcategory_Services subsubcategory_Services = new Subsubcategory_Services();

    public Subsubcategory_Controller() {
    }

    public boolean createSubsubcategory(Subsubcategory_Model model, int foreignKey) {
        return subsubcategory_Services.addSubsubcategory(model, foreignKey);
    }

    public boolean updateSubsubcategory(Subsubcategory_Model model, int foreignKey) {
        return subsubcategory_Services.updateSubsubcategory(model, foreignKey);
    }

    public boolean deleteSubsubcategory(Subsubcategory_Model model) {
        return subsubcategory_Services.deleteSubsubcategory(model);
    }

    public boolean disableSubsubcategory(Subsubcategory_Model model, int foreignKey) {
        return subsubcategory_Services.disableSubsubcategory(model, foreignKey);
    }

    public DefaultTableModel showSubsubcategories(String search, String stateFilter) {
        return subsubcategory_Services.showSubsubcategories(search, stateFilter);
    }
}
