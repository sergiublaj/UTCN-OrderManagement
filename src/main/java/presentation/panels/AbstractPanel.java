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

public abstract class AbstractPanel<T> extends JPanel {
   private final Class<T> type;
   private final JLabel panelTitle = new JLabel();
   private final ArrayList<JLabel> fieldsLabel = new ArrayList<>();
   private final ArrayList<JTextField> fieldsInput = new ArrayList<>();
   private final JTable objectTable = new JTable();

   private DefaultTableModel tableEntries;
   private final JButton performOperationBtn = new JButton();
   private final JButton nextStepBtn = new JButton();

   @SuppressWarnings("unchecked")
   public AbstractPanel() {
      this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.setUp();
      this.showCreateObjectPanel();
   }

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

   private void initializeTitle() {
      panelTitle.setAlignmentX(CENTER_ALIGNMENT);
      panelTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
      panelTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
   }

   private void initializeButton(JButton crtButton) {
      crtButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
      crtButton.setAlignmentX(CENTER_ALIGNMENT);
      crtButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
   }

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

   public void showEditObjectPanel(T object) {
      this.removeAll();

      panelTitle.setText("Edit " + type.getSimpleName().toLowerCase());
      this.add(panelTitle);

      this.showObjectInfo(true);
      this.setFields(object);
      this.enableFields(true);

      performOperationBtn.setText("Edit");
      this.add(performOperationBtn);

      this.repaint();
      this.revalidate();
   }

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

   private void initializeTable() {
      objectTable.setModel(tableEntries);
      objectTable.setRowHeight(25);
      objectTable.getTableHeader().setReorderingAllowed(false);
      objectTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 20));
      objectTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      objectTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
      objectTable.setEnabled(false);
   }

   public void showObjectsInTable(ArrayList<T> objectsList) {
      tableEntries.setRowCount(0);
      for (T crtObject : objectsList) {
         Object[] values = this.getValues(crtObject);
         tableEntries.addRow(values);
      }
   }

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

   public void enableFields(boolean isEnabled) {
      for (JTextField textField : fieldsInput) {
         textField.setEnabled(isEnabled);
      }
   }

   public int getIdField() {
      if (fieldsInput.get(0).getText().trim().isBlank()) {
         return -1;
      } else {
         return Integer.parseInt(fieldsInput.get(0).getText());
      }
   }

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

   public void setFields(T object) {
      Object[] values = this.getValues(object);
      for (int i = 0; i < fieldsLabel.size(); i++) {
         fieldsInput.get(i).setText(values[i] + "");
      }
   }

   public void clearFields() {
      for (int i = 0; i < fieldsLabel.size(); i++) {
         fieldsInput.get(i).setText("");
      }
   }

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

   public void addNextStepBtnListener(ActionListener btnListener) {
      nextStepBtn.addActionListener(btnListener);
   }

   public void addPerformOperationBtnListener(ActionListener btnListener) {
      performOperationBtn.addActionListener(btnListener);
   }

   public void removeAllEventListeners() {
      for (ActionListener crtListener : nextStepBtn.getActionListeners()) {
         nextStepBtn.removeActionListener(crtListener);
      }
      for (ActionListener crtListener : performOperationBtn.getActionListeners()) {
         performOperationBtn.removeActionListener(crtListener);
      }
   }
}
