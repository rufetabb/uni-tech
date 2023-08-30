package az.unitech.exception;


public class AccountAlreadyExistsException extends RuntimeException {


    private static final Long serialVersionUID = 9L;

    public AccountAlreadyExistsException(String message) {
        super(message);
    }


}
