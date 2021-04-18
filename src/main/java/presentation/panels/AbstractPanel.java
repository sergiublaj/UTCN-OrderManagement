package presentation.panels;

import presentation.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.util.ArrayList;

/**
 * <p>Abstract GUI panel for CRUD operations</p>
 * @param <T> type of the object
 */
public abstract class AbstractPanel<T> extends JPanel {
   private final Class<T> type;
   private final JLabel panelTitle = new JLabel();
   private final ArrayList<JLabel> fieldsLabel = new ArrayList<>();
   private final ArrayList<JTextField> fieldsInput = new ArrayList<>();
   private final JTable objectTable = new JTable();

   private DefaultTableModel tableEntries;
   private final JButton performOperationBtn = new JButton();
   private final JButton nextStepBtn = new JButton();

   /**
    * Default constructor
    */
   @SuppressWarnings("unchecked")
   public AbstractPanel() {
      this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.setUp();
      this.showCreateObjectPanel();
   }

   /**
    * <p>Set up the panel</p>
    */
   private void setUp() {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      this.initializeTitle();
      this.initializeButton(performOperationBtn);
      this.initializeButton(nextStepBtn);

      ArrayList<String> tableHeader = new ArrayList<>();
      for (Field field : type.getDeclaredFields()) {
         fieldsLabel.add(MainFrame.createLabel(field.getName().toUpperCase() + ": "));
         fieldsInput.add(MainFrame.createInput());
         tableHeader.add(field.getName().toUpperCase());
      }

      tableEntries = new DefaultTableModel(tableHeader.toArray(), 0);
   }

   /**
    * <p>Set up the panel title</p>
    */
   private void initializeTitle() {
      panelTitle.setAlignmentX(CENTER_ALIGNMENT);
      panelTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
      panelTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
   }

   /**
    * <p>Set up the buttons</p>
    * @param crtButton current button
    */
   private void initializeButton(JButton crtButton) {
      crtButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
      crtButton.setAlignmentX(CENTER_ALIGNMENT);
      crtButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
   }

   /**
    * <p>Panel for creating an object</p>
    */
   public void showCreateObjectPanel() {
      this.removeAll();

      panelTitle.setText("Create new " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      this.showObjectInfo(false);
      this.enableFields(true);

      performOperationBtn.setText("Add");
      this.add(performOperationBtn);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Panel for inserting the id of an object</p>
    */
   public void showObjectByIdPanel() {
      this.removeAll();

      panelTitle.setText("Enter the id of the desired " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      JPanel midPanel = new JPanel(new GridLayout(1, 2, -150, 10));
      midPanel.setMaximumSize(new Dimension(650, 75));
      midPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
      this.add(midPanel);

      midPanel.add(fieldsLabel.get(0));
      midPanel.add(fieldsInput.get(0));

      nextStepBtn.setText("OK");
      this.add(nextStepBtn);
      this.enableFields(true);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Panel for editing an object</p>
    * @param object object to be edited
    */
   public void showEditObjectPanel(T object) {
      this.removeAll();

      panelTitle.setText("Edit " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      this.showObjectInfo(false);
      this.setFields(object);
      this.enableFields(true);

      performOperationBtn.setText("Edit");
      this.add(performOperationBtn);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Panel for removing an object</p>
    * @param object object to be removed
    */
   public void showRemoveObjectPanel(T object) {
      this.removeAll();

      panelTitle.setText("Remove " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      this.showObjectInfo(true);
      this.setFields(object);
      this.enableFields(false);

      performOperationBtn.setText("Remove");
      this.add(performOperationBtn);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Panel for searching an object</p>
    * @param object searched object
    */
   public void showSearchObjectPanel(T object) {
      this.removeAll();

      panelTitle.setText("Search " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      this.showObjectInfo(true);
      this.setFields(object);
      this.enableFields(false);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Panel for viewing all objects</p>
    */
   public void showViewObjectsPanel() {
      this.removeAll();

      panelTitle.setText("Viewing all " + type.getSimpleName().toLowerCase() + "s");
      this.add(panelTitle);

      this.initializeTable();
      JScrollPane tableScroll = new JScrollPane(objectTable);
      this.add(tableScroll);

      this.repaint();
      this.revalidate();
   }

   /**
    * <p>Set up the table</p>
    */
   private void initializeTable() {
      objectTable.setModel(tableEntries);
      objectTable.setRowHeight(25);
      objectTable.getTableHeader().setReorderingAllowed(false);
      objectTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 20));
      objectTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      objectTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
      objectTable.setEnabled(false);
   }

   /**
    * <p>Adds the objects in table</p>
    * @param objectsList list of objects
    */
   public void showObjectsInTable(ArrayList<T> objectsList) {
      tableEntries.setRowCount(0);
      for (T crtObject : objectsList) {
         Object[] values = this.getValues(crtObject);
         tableEntries.addRow(values);
      }
   }

   /**
    * <p>GUI panel for showing info about object</p>
    * @param hasId boolean value representing the needing of showing the id of object or not
    */
   public void showObjectInfo(boolean hasId) {
      int fieldsNb;
      if (type.getSimpleName().compareTo("Order") == 0) {
         fieldsNb = hasId ? fieldsInput.size() : fieldsInput.size() - 3;
      } else {
         fieldsNb = hasId ? fieldsInput.size() : fieldsInput.size() - 1;
      }
      JPanel midPanel = new JPanel(new GridLayout(fieldsNb, 2, -150, 10));
      midPanel.setMaximumSize(new Dimension(650, fieldsNb * 50));
      midPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
      this.add(midPanel);

      for (int i = 0; i < fieldsLabel.size(); i++) {
         if (hasId || i > 0 && (type.getSimpleName().compareTo("Order") != 0 || type.getSimpleName().compareTo("Order") == 0 && i < fieldsLabel.size() - 2)) {
            midPanel.add(fieldsLabel.get(i));
            midPanel.add(fieldsInput.get(i));
         }
      }
   }

   /**
    * <p>Enables or disables the input</p>
    * @param isEnabled input status
    */
   public void enableFields(boolean isEnabled) {
      for (JTextField textField : fieldsInput) {
         textField.setEnabled(isEnabled);
      }
   }

   /**
    * <p>Returns the id taken from input field</p>
    * @return id field
    */
   public int getIdField() {
      if (fieldsInput.get(0).getText().trim().isBlank()) {
         return -1;
      } else {
         return Integer.parseInt(fieldsInput.get(0).getText());
      }
   }

   /**
    * <p>Creates an object with the values taken from input fields</p>
    * @return new object
    */
   public T getFields() {
      T object = null;
      try {
         object = type.getDeclaredConstructor().newInstance();
         Field[] field = type.getDeclaredFields();
         for (int i = 0; i < field.length; i++) {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field[i].getName(), type);
            Method method = propertyDescriptor.getWriteMethod();
            if (method.toString().endsWith("(int)")) {
               if (fieldsInput.get(i).getText().trim().isBlank()) {
                  method.invoke(object, 0);
               } else {
                  method.invoke(object, Integer.parseInt(fieldsInput.get(i).getText()));
               }
            } else if (method.toString().endsWith("(java.sql.Date)")) {
               method.invoke(object, new Date(System.currentTimeMillis()));
            } else {
               method.invoke(object, fieldsInput.get(i).getText());
            }

         }
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | IntrospectionException e) {
         e.printStackTrace();
      }
      return object;
   }

   /**
    * <p>Puts in input fields object values</p>
    * @param object object where the values will be taken from
    */
   public void setFields(T object) {
      Object[] values = this.getValues(object);
      for (int i = 0; i < fieldsLabel.size(); i++) {
         fieldsInput.get(i).setText(values[i] + "");
      }
   }

   /**
    * <p>Clears the input fields</p>
    */
   public void clearFields() {
      for (int i = 0; i < fieldsLabel.size(); i++) {
         fieldsInput.get(i).setText("");
      }
   }

   /**
    * <p>Returns a list of an object's values</p>
    * @param object object where the values will be taken from
    * @return list of object's values
    */
   private Object[] getValues(T object) {
      Object[] values = new Object[type.getDeclaredFields().length];
      Field[] fields = object.getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
         fields[i].setAccessible(true);
         try {
            values[i] = fields[i].get(object);
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
      }
      return values;
   }

   /**
    * <p>Adds event listener for button</p>
    * @param btnListener event listener
    */
   public void addNextStepBtnListener(ActionListener btnListener) {
      nextStepBtn.addActionListener(btnListener);
   }

   /**
    * <p>Adds event listener for button</p>
    * @param btnListener event listener
    */
   public void addPerformOperationBtnListener(ActionListener btnListener) {
      performOperationBtn.addActionListener(btnListener);
   }

   /**
    * <p>Removes all buttons' event listeners</p>
    */
   public void removeAllEventListeners() {
      for (ActionListener crtListener : nextStepBtn.getActionListeners()) {
         nextStepBtn.removeActionListener(crtListener);
      }
      for (ActionListener crtListener : performOperationBtn.getActionListeners()) {
         performOperationBtn.removeActionListener(crtListener);
      }
   }
}
