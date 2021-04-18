package presentation;

import presentation.panels.ClientPanel;
import presentation.panels.OrderPanel;
import presentation.panels.ProductPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <p>GUI panel for object</p>
 */
public class TabbedPanel extends JPanel {
   private final String objectName;

   private JPanel activityPanel;
   private JButton createObject;
   private JButton editObject;
   private JButton removeObject;
   private JButton searchObject;
   private JButton viewObjects;

   /**
    * <p>Default constructor</p>
    * @param objectName type of object
    */
   public TabbedPanel(String objectName) {
      this.objectName = objectName;
      this.setUpPanel();
   }

   /**
    * <p>Sets up the panel</p>
    */
   private void setUpPanel() {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.addButtons();
      this.addActivityPanel();
   }

   /**
    * <p>Adds the operation buttons</p>
    */
   private void addButtons() {
      JPanel buttonsPanel = new JPanel();
      buttonsPanel.setBackground(Color.BLUE);
      buttonsPanel.setLayout(new GridLayout(1, 5));
      buttonsPanel.setMaximumSize(new Dimension(1000, 40));
      this.add(buttonsPanel);

      createObject = new JButton("New " + this.objectName);
      createObject.setIcon(new ImageIcon("src/main/resources/images/create.png"));
      createObject.setCursor(new Cursor(Cursor.HAND_CURSOR));
      buttonsPanel.add(createObject);

      if (objectName.compareTo("order") != 0) {
         editObject = new JButton("Edit " + this.objectName);
         editObject.setIcon(new ImageIcon("src/main/resources/images/edit.png"));
         editObject.setCursor(new Cursor(Cursor.HAND_CURSOR));
         buttonsPanel.add(editObject);

         removeObject = new JButton("Remove " + this.objectName);
         removeObject.setIcon(new ImageIcon("src/main/resources/images/remove.png"));
         removeObject.setCursor(new Cursor(Cursor.HAND_CURSOR));
         buttonsPanel.add(removeObject);
      }

      searchObject = new JButton("Search " + this.objectName);
      searchObject.setIcon(new ImageIcon("src/main/resources/images/search.png"));
      searchObject.setCursor(new Cursor(Cursor.HAND_CURSOR));
      buttonsPanel.add(searchObject);

      viewObjects = new JButton("View all " + this.objectName + "s");
      viewObjects.setIcon(new ImageIcon("src/main/resources/images/view.png"));
      viewObjects.setCursor(new Cursor(Cursor.HAND_CURSOR));
      buttonsPanel.add(viewObjects);
   }

   /**
    * <p>Sets up the CRUD panels</p>
    */
   private void addActivityPanel() {
      if (objectName.compareTo("client") == 0) {
         activityPanel = new ClientPanel();
      } else if (objectName.compareTo("product") == 0) {
         activityPanel = new ProductPanel();
      } else {
         activityPanel = new OrderPanel();
      }
      activityPanel.setPreferredSize(new Dimension(-1, 410));
      this.add(activityPanel, BorderLayout.SOUTH);
   }

   /**
    * Adds createListener for createButton
    * @param btnListener createListener
    */
   public void addCreateButtonListener(ActionListener btnListener) {
      createObject.addActionListener(btnListener);
   }

   /**
    * Adds editListener for editButton
    * @param btnListener editListener
    */
   public void addEditButtonListener(ActionListener btnListener) {
      editObject.addActionListener(btnListener);
   }

   /**
    * Adds removeListener for removeButton
    * @param btnListener removeListener
    */
   public void addRemoveButtonListener(ActionListener btnListener) {
      removeObject.addActionListener(btnListener);
   }

   /**
    * Adds searchListener for searchButton
    * @param btnListener searchListener
    */
   public void addSearchButtonListener(ActionListener btnListener) {
      searchObject.addActionListener(btnListener);
   }

   /**
    * Adds viewListener for viewButton
    * @param btnListener viewListener
    */
   public void addViewButtonListener(ActionListener btnListener) {
      viewObjects.addActionListener(btnListener);
   }

   /**
    * <p>Returns the current GUI panel</p>
    * @return GUI panel
    */
   public JPanel getActivityPanel() {
      return activityPanel;
   }
}
