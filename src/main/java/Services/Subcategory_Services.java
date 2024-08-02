package Services;

import Model.Subcategory_Model;
import Repository.Subcategory_Repository;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subcategory_Services {

    private final Subcategory_Repository subcategory_repository = new Subcategory_Repository();

    public Subcategory_Services() {
    }

    public boolean addSubcategory(Subcategory_Model model, int foreignKey, int foreignKey2) {
        return subcategory_repository.insert(model, foreignKey, foreignKey2);
    }

    public boolean updateSubcategory(Subcategory_Model model, int foreignKey, int foreignKey2) {
        return subcategory_repository.update(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubcategory(Subcategory_Model model) {
        return subcategory_repository.delete(model);
    }
    
    public DefaultTableModel showSubcategories(String search, String stateFilter){
        return subcategory_repository.showSubcategories(search, stateFilter);
    }
    
    public HashMap<String, List<String>> fillCategoryCombobox(){
        return subcategory_repository.fillCategoryCombos();
    }
}
