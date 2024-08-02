package Services;

import Model.Subsubcategory_Model;
import Repository.Subsubcategory_Repository;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Subsubcategory_Services {

    private final Subsubcategory_Repository subsubcategory_repository = new Subsubcategory_Repository();

    public Subsubcategory_Services() {
    }

    public boolean addSubsubcategory(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        return subsubcategory_repository.insert(model, foreignKey, foreignKey2);
    }

    public boolean updateSubsubcategory(Subsubcategory_Model model, int foreignKey, int foreignKey2) {
        return subsubcategory_repository.update(model, foreignKey, foreignKey2);
    }

    public boolean deleteSubsubcategory(Subsubcategory_Model model) {
        return subsubcategory_repository.delete(model);
    }

    public DefaultTableModel showSubsubcategories(String search, String stateFilter) {
        return subsubcategory_repository.showSubsubcategories(search, stateFilter);
    }
    
    public HashMap<String, List<String>> fillSubcategoryCombobox(int foreignKey){
        return subsubcategory_repository.fillSubcategoryCombobox(foreignKey);
    }
}
