package dataaccess;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
   private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
   private final Class<T> type;

   @SuppressWarnings("unchecked")
   public AbstractDAO() {
      this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }

   public int insert(T object) {
      Connection dbConnection = ConnectionFactory.getConnection();
      PreparedStatement insertStatement = null;
      ResultSet resultSet = null;
      int insertedId = -1;

      try {
         insertStatement = dbConnection.prepareStatement(createInsertQuery(), Statement.RETURN_GENERATED_KEYS);
         this.setValues(insertStatement, object, false);
         insertStatement.executeUpdate();
         resultSet = insertStatement.getGeneratedKeys();
         if (resultSet.next()) {
            insertedId = resultSet.getInt(1);
         }
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, type.getName() + "DAO: insert " + sqlEx.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, insertStatement, resultSet);
      }

      return insertedId;
   }

   private String createInsertQuery() {
      StringBuilder insertQuery = new StringBuilder("INSERT INTO `");
      insertQuery.append(type.getSimpleName());
      insertQuery.append("` (");
      Field[] fields = type.getDeclaredFields();
      for (int i = 1; i < fields.length; i++) {
         insertQuery.append(fields[i].getName());
         if (i == fields.length - 1) {
            insertQuery.append(") ");
         } else {
            insertQuery.append(", ");
         }
      }

      insertQuery.append("VALUES (");
      for (int i = 1; i < fields.length; i++) {
         insertQuery.append("?");
         if (i == fields.length - 1) {
            insertQuery.append(") ");
         } else {
            insertQuery.append(", ");
         }
      }

      return insertQuery.toString();
   }

   public T findById(int id) {
      Connection dbConnection = null;
      PreparedStatement findStatement = null;
      ResultSet resultSet = null;
      T toReturn = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         findStatement = dbConnection.prepareStatement(createFindQuery());
         findStatement.setInt(1, id);
         resultSet = findStatement.executeQuery();
         toReturn = createObjects(resultSet).get(0);
      } catch (SQLException | IndexOutOfBoundsException e) {
         LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + e.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, findStatement, resultSet);
      }
      return toReturn;
   }

   private String createFindQuery() {
      return "SELECT * FROM `" + type.getSimpleName() + "` WHERE ID = ?";
   }

   public boolean update(T object) {
      Connection dbConnection = null;
      PreparedStatement updateStatement = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         updateStatement = dbConnection.prepareStatement(createUpdateQuery());
         this.setValues(updateStatement, object, true);
         updateStatement.executeUpdate();
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, type.getName() + "DAO: update " + sqlEx.getMessage());
         return false;
      } finally {
         ConnectionFactory.closeAll(dbConnection, updateStatement, null);
      }
      return true;
   }

   private String createUpdateQuery() {
      StringBuilder updateQuery = new StringBuilder("UPDATE `");
      updateQuery.append(type.getSimpleName());
      updateQuery.append("` SET ");
      Field[] fields = type.getDeclaredFields();
      for (int i = 1; i < fields.length; i++) {
         updateQuery.append(fields[i].getName());
         updateQuery.append(" = ? ");
         if (i < fields.length - 1) {
            updateQuery.append(", ");
         } else {
            updateQuery.append(" ");
         }
      }
      updateQuery.append("WHERE ID = ?");
      return updateQuery.toString();
   }

   public boolean remove(int id) {
      Connection dbConnection = null;
      PreparedStatement preparedStatement = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         preparedStatement = dbConnection.prepareStatement(createRemoveQuery());
         preparedStatement.setInt(1, id);
         preparedStatement.executeUpdate();
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, type.getName() + "DAO: remove " + sqlEx.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, preparedStatement, null);
      }
      return true;
   }

   private String createRemoveQuery() {
      return "DELETE FROM `" + type.getSimpleName() + "` WHERE ID = ?";
   }

   public ArrayList<T> findAll() {
      ArrayList<T> entries = new ArrayList<>();
      Connection dbConnection = null;
      Statement statement = null;
      ResultSet resultSet = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         statement = dbConnection.createStatement();
         statement.execute(createFindAllQuery());
         resultSet = statement.getResultSet();
         entries = this.createObjects(resultSet);
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, type.getName() + "DAO: findAll " + sqlEx.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, statement, resultSet);
      }
      return entries;
   }

   private String createFindAllQuery() {
      return "SELECT * FROM `" + type.getSimpleName() + "`";
   }

   private void setValues(PreparedStatement preparedStatement, T object, boolean isUpdateQuery) {
      ArrayList<Object> values = this.getValues(object);
      try {
         for (int i = 1; i < values.size(); i++) {
            if (values.get(i).getClass() == String.class) {
               preparedStatement.setString(i, (String) values.get(i));
            } else if (values.get(i).getClass() == java.sql.Date.class) {
               preparedStatement.setDate(i, (Date) values.get(i));
            } else {
               preparedStatement.setInt(i, (int) values.get(i));
            }
         }
         if (isUpdateQuery) {
            preparedStatement.setInt(values.size(), (int) values.get(0));
         }
      } catch (SQLException throwable) {
         throwable.printStackTrace();
      }
   }

   public ArrayList<String> getFields(T object) {
      ArrayList<String> fields = new ArrayList<>();
      for (Field field : object.getClass().getDeclaredFields()) {
         field.setAccessible(true);
         try {
            fields.add(field.getName());
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
         }
      }
      return fields;
   }

   public ArrayList<Object> getValues(T object) {
      ArrayList<Object> values = new ArrayList<>();
      for (Field field : object.getClass().getDeclaredFields()) {
         field.setAccessible(true);
         try {
            values.add(field.get(object));
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
      }
      return values;
   }

   private ArrayList<T> createObjects(ResultSet resultSet) {
      ArrayList<T> list = new ArrayList<>();
      try {
         while (resultSet.next()) {
            T instance = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
               Object value = resultSet.getObject(field.getName());
               PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
               Method method = propertyDescriptor.getWriteMethod();
               method.invoke(instance, value);
            }
            list.add(instance);
         }
      } catch (SQLException | IllegalAccessException | InstantiationException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
         e.printStackTrace();
      }
      return list;
   }
}
