package businesslogic;

import businesslogic.validators.AgeValidator;
import businesslogic.validators.EmailValidator;
import businesslogic.validators.Validator;
import dataaccess.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientBLL extends AbstractBLL<Client> {
   private final ClientDAO clientDAO = new ClientDAO();
   private final List<Validator<Client>> validators = new ArrayList<>();

   public ClientBLL() {
      validators.add(new EmailValidator());
      validators.add(new AgeValidator());
   }

   public int createClient(Client newClient) {
      for (Validator<Client> crtValidator : validators) {
         crtValidator.validate(newClient);
      }
      return clientDAO.insert(newClient);
   }

   public boolean editClient(Client toUpdate) {
      return clientDAO.update(toUpdate);
   }

   public Client searchClient(int clientId) {
      return clientDAO.findById(clientId);
   }

   public boolean removeClient(int clientId) {
      return clientDAO.remove(clientId);
   }

   public ArrayList<Client> viewClients() {
      return clientDAO.findAll();
   }
}
