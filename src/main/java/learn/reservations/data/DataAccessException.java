package learn.reservations.data;

public class DataAccessException extends Exception{

    public DataAccessException(String message) {super(message); }

    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }


}
