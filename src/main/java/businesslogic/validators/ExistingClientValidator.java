package businesslogic.validators;

import businesslogic.ClientBLL;
import model.Order;
import presentation.MainFrame;

public class ExistingClientValidator implements Validator<Order> {
   private static final String MESSAGE = "Client does not exist!";
   private final ClientBLL clientBLL = new ClientBLL();

   @Override
   public void validate(Order crtOrder) {
      if (clientBLL.searchClient(crtOrder.getClient()) == null) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
