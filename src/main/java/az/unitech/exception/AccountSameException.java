package az.unitech.exception;

public class AccountSameException extends RuntimeException{

    public AccountSameException(String message) {
        super(message);
    }
}
