package businesslogic.validators;

import model.Product;
import presentation.MainFrame;

public class StockValidator implements Validator<Product> {
   private static final int MIN_STOCK = 1;
   private static final int MAX_STOCK = 9999;
   private static final String MESSAGE = "Product stock is invalid!";

   public void validate(Product crtProduct) {
      if (crtProduct.getStock() < MIN_STOCK || crtProduct.getStock() > MAX_STOCK) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
