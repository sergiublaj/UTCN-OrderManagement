package businesslogic;

import businesslogic.validators.PriceValidator;
import businesslogic.validators.StockValidator;
import businesslogic.validators.Validator;
import dataaccess.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBLL extends AbstractBLL<Product> {
   private final ProductDAO productDAO = new ProductDAO();
   private final List<Validator<Product>> validators = new ArrayList<>();

   public ProductBLL() {
      validators.add(new PriceValidator());
      validators.add(new StockValidator());
   }

   public int createProduct(Product newProduct) {
      for (Validator<Product> crtValidator : validators) {
         crtValidator.validate(newProduct);
      }
      return productDAO.insert(newProduct);
   }

   public boolean editProduct(Product toUpdate) {
      return productDAO.update(toUpdate);
   }

   public Product searchProduct(int productId) {
      return productDAO.findById(productId);
   }

   public boolean removeProduct(int productId) {
      return productDAO.remove(productId);
   }

   public ArrayList<Product> viewProducts() {
      return productDAO.findAll();
   }
}
