package businesslogic.validators;

import businesslogic.ProductBLL;
import model.Order;
import presentation.MainFrame;

/**
 * <p>Order product validator</p>
 */
public class ExistingProductValidator implements Validator<Order> {
   private static final String MESSAGE = "Product does not exist!";
   private final ProductBLL productBLL = new ProductBLL();

   /**
    * <p>Ensures that an order's product exists in database</p>
    * @param crtOrder current order
    */
   @Override
   public void validate(Order crtOrder) {
      if (productBLL.searchProduct(crtOrder.getProduct()) == null) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
