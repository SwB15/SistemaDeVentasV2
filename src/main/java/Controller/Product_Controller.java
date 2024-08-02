package Controller;

import Model.Product_Model;
import Services.Product_Services;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Product_Controller {

    private final Product_Services product_services = new Product_Services();

    public Product_Controller() {
    }

    public boolean createProduct(Product_Model model, int foreignKey, int foreignKey2) {
        return product_services.addProduct(model, foreignKey, foreignKey2);
    }

    public boolean updateProduct(Product_Model model, int foreignKey, int foreignKey2) {
        return product_services.updateProduct(model, foreignKey, foreignKey2);
    }

    public boolean deleteProduct(Product_Model model) {
        return product_services.deleteProduct(model);
    }

    public DefaultTableModel showProduct(String search, String stateFilter) {
        return product_services.showProduct(search, stateFilter);
    }

    public HashMap<String, HashMap<String, List<String>>> fillComboboxes() {
        return product_services.fillComboboxes();
    }
}
