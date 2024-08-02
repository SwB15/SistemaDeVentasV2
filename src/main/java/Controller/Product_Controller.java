package Controller;

import Model.Product_Model;
import Services.Product_Services;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SwichBlade15
 */
public class Product_Controller {

    private final Product_Services product_services = new Product_Services();
    private final Product_Model model = new Product_Model();

    public Product_Controller() {
    }

    public boolean createProduct(String code, String internalcode, String name, int price, int minimumstock, String unit, String description, Date expiredDate, int iva, int foreignKey, int foreignKey2) {
        model.setCodigo(code);
        model.setCodigointerno(internalcode);
        model.setNombre(name);
        model.setPrecio(price);
        model.setStockminimo(minimumstock);
        model.setPresentacion(unit);
        model.setDescripcion(description);
        model.setFechavencimiento(expiredDate);
        model.setIva(iva);
        return product_services.addProduct(model, foreignKey, foreignKey2);
    }

    public boolean updateProduct(int idproduct, String code, String internalcode, String name, int price, int minimumstock, String unit, String description, Date expiredDate, int iva, int foreignKey, int foreignKey2) {
        model.setIdproductos(idproduct);
        model.setCodigo(code);
        model.setCodigointerno(internalcode);
        model.setNombre(name);
        model.setPrecio(price);
        model.setStockminimo(minimumstock);
        model.setPresentacion(unit);
        model.setDescripcion(description);
        model.setFechavencimiento(expiredDate);
        model.setIva(iva);
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
