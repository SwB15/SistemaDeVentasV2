package Services;

import Model.Product_Model;
import Repository.Product_Repository;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Product_Services {

    private final Product_Repository product_repository = new Product_Repository();

    public Product_Services() {
    }

    public boolean addProduct(Product_Model model, int foreignKey, int foreignKey2) {
        return product_repository.insert(model, foreignKey, foreignKey2);
    }

    public boolean updateProduct(Product_Model model, int foreignKey, int foreignKey2) {
        return product_repository.update(model, foreignKey, foreignKey2);
    }

    public boolean deleteProduct(Product_Model model) {
        return product_repository.delete(model);
    }

    public DefaultTableModel showProduct(String search, String stateFilter) {
        return product_repository.showProducts(search, stateFilter);
    }

    public HashMap<String, HashMap<String, List<String>>> fillComboboxes() {
        return product_repository.fillCategoryAndSubcategoryComboboxes();
    }
}
