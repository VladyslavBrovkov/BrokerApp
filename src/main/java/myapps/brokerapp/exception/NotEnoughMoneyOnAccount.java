package myapps.brokerapp.exception;

public class NotEnoughMoneyOnAccount extends RuntimeException{
    public NotEnoughMoneyOnAccount(String message) {
        super(message);
    }
}
