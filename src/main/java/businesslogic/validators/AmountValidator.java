package businesslogic.validators;

import businesslogic.ProductBLL;
import model.Order;
import presentation.MainFrame;

/**
 * <p>Order amount validator</p>
 */
public class AmountValidator implements Validator<Order> {
   private static final int MIN_AMOUNT = 1;
   private static final int MAX_AMOUNT = 999;
   private static final String MESSAGE = "Not enough products in stock!";
   private final ProductBLL productBLL = new ProductBLL();

   /**
    * <p>Ensures that there are enough products in stock when placing an order</p>
    * @param crtOrder current order
    */
   @Override
   public void validate(Order crtOrder) {
      if (crtOrder.getAmount() < MIN_AMOUNT || crtOrder.getAmount() > MAX_AMOUNT || productBLL.searchProduct(crtOrder.getProduct()).getStock() < crtOrder.getAmount()) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
