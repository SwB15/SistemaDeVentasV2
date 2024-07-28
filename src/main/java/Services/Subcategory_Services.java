package Services;

import Model.Subcategory_Model;
import Repository.Subcategory_Repository;

/**
 *
 * @author SwichBlade15
 */
public class Subcategory_Services {

    private final Subcategory_Repository subcategory_repository;

    public Subcategory_Services(Subcategory_Repository subcategory_repository) {
        this.subcategory_repository = subcategory_repository;
    }

    public boolean addSubcategory(Subcategory_Model model, int foreignKey) {
        return subcategory_repository.insert(model, foreignKey);
    }

    public boolean updateSubcategory(Subcategory_Model model, String foreignName) {
        return subcategory_repository.update(model, foreignName);
    }

    public boolean deleteSubcategory(Subcategory_Model model) {
        return subcategory_repository.delete(model);
    }
}
