package businesslogic.validators;

import model.Client;
import presentation.MainFrame;

public class AgeValidator implements Validator<Client> {
   private static final int MIN_AGE = 18;
   private static final int MAX_AGE = 70;
   private static final String MESSAGE = "Client age is invalid!";

   @Override
   public void validate(Client crtClient) {
      if (crtClient.getAge() < MIN_AGE || crtClient.getAge() > MAX_AGE) {
         MainFrame.showAlert(MESSAGE);
         throw new IllegalArgumentException(MESSAGE);
      }
   }
}
