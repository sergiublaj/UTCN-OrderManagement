package businesslogic.validators;

import businesslogic.ProductBLL;
import model.Order;
import presentation.MainFrame;

public class ExistingProductValidator implements Validator<Order> {
   private static final String MESSAGE = "Product does not exist!";
   private final ProductBLL productBLL = new ProductBLL();

   @Override
   public void validate(Order crtOrder) {
      if (productBLL.searchProduct(crtOrder.getProduct()) == null) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
