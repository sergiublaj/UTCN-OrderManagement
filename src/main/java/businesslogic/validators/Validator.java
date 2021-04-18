package businesslogic.validators;

/**
 * <p>Interface used to validate objects</p>
 * @param <T> type of object
 */
public interface Validator<T> {
   /**
    * <p>Method validates current object</p>
    * @param crtObject current object
    */
   void validate(T crtObject);
}
