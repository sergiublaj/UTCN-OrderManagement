package presentation.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Product;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;

   public EditObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      boolean returnedValue = false;
      T newObject = objectPanel.getFields();
      if(objectBLL.getClass() == ClientBLL.class) {
         returnedValue = ((ClientBLL) objectBLL).editClient((Client)newObject);
      } else if(objectBLL.getClass() == ProductBLL.class) {
         returnedValue = ((ProductBLL) objectBLL).editProduct((Product)newObject);
      }

      if(returnedValue) {
         MainFrame.showAlert("Successfully edited the " + newObject.getClass().getSimpleName() + "!");
      } else {
         MainFrame.showAlert("Failed to edit the " + newObject.getClass().getSimpleName() + "!");
      }
      objectPanel.removeAll();
      objectPanel.revalidate();
   }
}
