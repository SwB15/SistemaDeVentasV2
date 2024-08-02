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
    private final Category_Model model = new Category_Model();

    public Category_Controller() {
    }

    public boolean createCategory(String code, String category, String description, int foreignKey) {
        model.setCodigo(code);
        model.setCategorias(category);
        model.setDescripcion(description);
        return category_Services.addCategory(model, foreignKey);
    }

    public boolean updateCategory(int id, String code, String category, String description, int foreignKey) {
        model.setIdcategorias(id);
        model.setCodigo(code);
        model.setCategorias(category);
        model.setDescripcion(description);
        return category_Services.updateCategory(model, foreignKey);
    }

    public boolean deleteCategory(int id) {
        model.setIdcategorias(id);
        return category_Services.deleteCategory(model);
    }

    public DefaultTableModel showCategories(String search, String stateFilter) {
        return category_Services.showCategories(search, stateFilter);
    }
}
