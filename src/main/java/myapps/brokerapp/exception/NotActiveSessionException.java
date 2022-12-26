package myapps.brokerapp.exception;

public class NotActiveSessionException extends RuntimeException{
    public NotActiveSessionException(String message) {
        super(message);
    }
}
