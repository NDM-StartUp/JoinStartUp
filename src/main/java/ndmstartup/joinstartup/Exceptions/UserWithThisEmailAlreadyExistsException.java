package ndmstartup.joinstartup.Exceptions;

public class UserWithThisEmailAlreadyExistsException extends RuntimeException {
    public UserWithThisEmailAlreadyExistsException(String message) {
        super(message);
    }
}
