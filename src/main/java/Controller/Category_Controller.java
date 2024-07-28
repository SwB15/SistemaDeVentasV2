package Controller;

import Model.Category_Model;
import Services.Category_Services;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Category_Controller {

    private final Category_Services category_Services = new Category_Services();

    public Category_Controller() {
    }

    public boolean createCategory(Category_Model model, int foreignKey) {
        return category_Services.addCategory(model, foreignKey);
    }

    public boolean updateCategory(Category_Model model, int foreignKey) {
        return category_Services.updateCategory(model, foreignKey);
    }

    public boolean deleteCategory(Category_Model model) {
        return category_Services.deleteCategory(model);
    }

    public boolean disableCategory(Category_Model model, int foreignKey) {
        return category_Services.disableCategory(model, foreignKey);
    }

    public DefaultTableModel showCategories(String search, String stateFilter) {
        return category_Services.showCategories(search, stateFilter);
    }
}
