package businesslogic.validators;

import businesslogic.ClientBLL;
import model.Order;
import presentation.MainFrame;

/**
 * <p>Order client validator</p>
 */
public class ExistingClientValidator implements Validator<Order> {
   private static final String MESSAGE = "Client does not exist!";
   private final ClientBLL clientBLL = new ClientBLL();

   /**
    * <p>Ensures that an order's client exists in database</p>
    * @param crtOrder current order
    */
   @Override
   public void validate(Order crtOrder) {
      if (clientBLL.searchClient(crtOrder.getClient()) == null) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
