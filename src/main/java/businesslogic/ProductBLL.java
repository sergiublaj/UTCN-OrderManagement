package businesslogic;

import businesslogic.validators.PriceValidator;
import businesslogic.validators.StockValidator;
import businesslogic.validators.Validator;
import dataaccess.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Various product business logic</p>
 */
public class ProductBLL extends AbstractBLL<Product> {
   private final ProductDAO productDAO = new ProductDAO();
   private final List<Validator<Product>> validators = new ArrayList<>();

   /**
    * <p>Default constructor adding the validators</p>
    */
   public ProductBLL() {
      validators.add(new PriceValidator());
      validators.add(new StockValidator());
   }

   /**
    * <p>Tries to validate a product and, if the product is valid, makes call to insert it into database</p>
    * @param newProduct product to be added to database
    * @return inserted product id
    */
   public int createProduct(Product newProduct) {
      for (Validator<Product> crtValidator : validators) {
         crtValidator.validate(newProduct);
      }
      return productDAO.insert(newProduct);
   }

   /**
    * <p>Tries to validate a product and, if the client is valid, makes call to update an existing product from database</p>
    * @param toUpdate product to be updated
    * @return boolean value representing success or fail
    */
   public boolean editProduct(Product toUpdate) {
      for (Validator<Product> crtValidator : validators) {
         crtValidator.validate(toUpdate);
      }
      return productDAO.update(toUpdate);
   }

   /**
    * <p>Makes call to search a product in database</p>
    * @param productId id of the product to be searched
    * @return the found product or null
    */
   public Product searchProduct(int productId) {
      return productDAO.findById(productId);
   }

   /**
    * <p>Makes call to search a product in database</p>
    * @param productName name of the product to be searched
    * @return the found product or null
    */
   public Product searchProduct(String productName) {
      return productDAO.findByName(productName);
   }

   /**
    * <p>Makes call to remove a product from database</p>
    * @param productId id of the product to be removed
    * @return boolean value representing success or fail
    */
   public boolean removeProduct(int productId) {
      return productDAO.remove(productId);
   }

   /**
    * <p>Makes call to select all the products from database</p>
    * @return a list of all the products existing in database
    */
   public ArrayList<Product> viewProducts() {
      return productDAO.findAll();
   }

   /**
    * <p>Returns the price of the desired product</p>
    * @param productId desired product
    * @return price product
    */
   public int getPrice(int productId) { return productDAO.getPrice(productId); }

   /**
    * <p>Decrements the stock of a product with an amount</p>
    * @param productId desired product
    * @param usedAmount amount the stock will be decremented with
    */
   public void decrementStock(int productId, int usedAmount) { productDAO.decrementStock(productId, usedAmount);}
}
