package businesslogic;

import businesslogic.validators.AgeValidator;
import businesslogic.validators.EmailValidator;
import businesslogic.validators.Validator;
import dataaccess.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Various client business logic</p>
 */
public class ClientBLL extends AbstractBLL<Client> {
   private final ClientDAO clientDAO = new ClientDAO();
   private final List<Validator<Client>> validators = new ArrayList<>();

   /**
    * <p>Default constructor adding the validators</p>
    */
   public ClientBLL() {
      validators.add(new AgeValidator());
      validators.add(new EmailValidator());
   }

   /**
    * <p>Tries to validate a client and, if the client is valid, makes call to insert it into database</p>
    * @param newClient client to be added to database
    * @return inserted client id
    */
   public int createClient(Client newClient) {
      for (Validator<Client> crtValidator : validators) {
         crtValidator.validate(newClient);
      }
      return clientDAO.insert(newClient);
   }

   /**
    * <p>Tries to validate a client and, if the client is valid, makes call to update an existing client from database</p>
    * @param toUpdate client to be updated
    * @return boolean value representing success or fail
    */
   public boolean editClient(Client toUpdate) {
      for (Validator<Client> crtValidator : validators) {
         crtValidator.validate(toUpdate);
      }
      return clientDAO.update(toUpdate);
   }

   /**
    * <p>Makes call to search a client in database</p>
    * @param clientId id of the client to be searched
    * @return the found client or null
    */
   public Client searchClient(int clientId) {
      return clientDAO.findById(clientId);
   }

   /**
    * <p>Makes call to search a client in database</p>
    * @param clientName name of the client to be searched
    * @return the found client or null
    */
   public Client searchClient(String clientName) {
      return clientDAO.findByName(clientName);
   }

   /**
    * <p>Makes call to remove a client from database</p>
    * @param clientId id of the client to be removed
    * @return boolean value representing success or fail
    */
   public boolean removeClient(int clientId) {
      return clientDAO.remove(clientId);
   }

   /**
    * <p>Makes call to select all the clients from database</p>
    * @return a list of all the clients existing in database
    */
   public ArrayList<Client> viewClients() {
      return clientDAO.findAll();
   }
}
