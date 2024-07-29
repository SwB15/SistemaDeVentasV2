package Services;

import Model.Category_Model;
import Repository.Category_Repository;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Category_Services {

    private final Category_Repository category_repository = new Category_Repository();

    public Category_Services() {
    }

    public boolean addCategory(Category_Model model, int foreignKey) {
        return category_repository.insert(model, foreignKey);
    }

    public boolean updateCategory(Category_Model model, int foreignKey) {
        return category_repository.update(model, foreignKey);
    }

    public boolean deleteCategory(Category_Model model) {
        return category_repository.delete(model);
    }
    
    public boolean disableCategory(Category_Model model, int foreignKey) {
        return category_repository.disable(model, foreignKey);
    }
    
    public DefaultTableModel showCategories(String search, String stateFilter){
        return category_repository.showCategories(search, stateFilter);
    }
}
