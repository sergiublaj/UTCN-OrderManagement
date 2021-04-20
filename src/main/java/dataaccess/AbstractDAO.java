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

/**
 * CRUD queries
 * @param <T> type of object
 */
public abstract class AbstractDAO<T> {
   protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
   private final Class<T> type;

   /**
    * Default constructor
    */
   @SuppressWarnings("unchecked")
   public AbstractDAO() {
      this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }

   /**
    * <p>Inserts a given object into database</p>
    * @param object object to be inserted into database
    * @return id of inserted object in database
    */
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

   /**
    * <p>Builds the query for inserting an object</p>
    * @return insert query
    */
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

   /**
    * <p>Returns an object of a given id</p>
    * @param id id of the object to look for
    * @return object with the given id
    */
   public T findById(int id) {
      Connection dbConnection = null;
      PreparedStatement findStatement = null;
      ResultSet resultSet = null;
      T toReturn = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         findStatement = dbConnection.prepareStatement(createFindByIdQuery());
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

   /**
    * <p>Builds the query for finding an object with a given id</p>
    * @return find query
    */
   private String createFindByIdQuery() {
      return "SELECT * FROM `" + type.getSimpleName() + "` WHERE ID = ?";
   }

   /**
    * <p>Updates an object values in the database</p>
    * @param object object to be updated
    * @return boolean representing the status
    */
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

   /**
    * <p>Builds the query for updating an object from database</p>
    * @return update query
    */
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

   /**
    * <p>Removes an object with the given id from database</p>
    * @param id id of object to be removed
    * @return boolean representing the status
    */
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

   /**
    * <p>Builds the query for removing an object from database</p>
    * @return remove query
    */
   private String createRemoveQuery() {
      return "DELETE FROM `" + type.getSimpleName() + "` WHERE ID = ?";
   }

   /**
    * <p>Retrieves a list of objects from database</p>
    * @return an object list from database
    */
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

   /**
    * <p>Builds the query for selecting all objects type from database</p>
    * @return select all query
    */
   private String createFindAllQuery() {
      return "SELECT * FROM `" + type.getSimpleName() + "`";
   }

   /**
    * <p>Replaces '?' in statements with object's values</p>
    * @param preparedStatement statement where replace will happen
    * @param object object where the values will be taken
    * @param isUpdateQuery if it is a create query or an update one
    */
   private void setValues(PreparedStatement preparedStatement, T object, boolean isUpdateQuery) {
      ArrayList<Object> values = this.getValues(object);
      try {
         for (int i = 1; i < values.size(); i++) {
            preparedStatement.setObject(i, values.get(i));
         }
         if (isUpdateQuery) {
            preparedStatement.setInt(values.size(), (int) values.get(0));
         }
      } catch (SQLException throwable) {
         throwable.printStackTrace();
      }
   }

   /**
    * <p>Takes the name of fields from an object</p>
    * @param object object to take the fields name from
    * @return object fields names
    */
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

   /**
    * <p>Takes the values of fields from an object</p>
    * @param object object to take the values from
    * @return object fields values
    */
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

   /**
    * <p>Creates an object list from a result set</p>
    * @param resultSet result set where the object will be made from
    * @return list of created objects
    */
   protected ArrayList<T> createObjects(ResultSet resultSet) {
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
