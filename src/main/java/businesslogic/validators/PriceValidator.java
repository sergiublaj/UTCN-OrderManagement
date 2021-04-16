package businesslogic.validators;

import model.Product;
import presentation.MainFrame;

public class PriceValidator implements Validator<Product> {
   private static final int MIN_PRICE = 1;
   private static final int MAX_PRICE = 9999;
   private static final String MESSAGE = "Product price is invalid!";

   public void validate(Product crtProduct) {
      if (crtProduct.getPrice() < MIN_PRICE || crtProduct.getPrice() > MAX_PRICE) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
