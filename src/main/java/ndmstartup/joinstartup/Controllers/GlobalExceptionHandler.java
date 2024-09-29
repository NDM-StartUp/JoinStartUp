package ndmstartup.joinstartup.Controllers;

import ndmstartup.joinstartup.Exceptions.StatusAlreadyExistsConflictException;
import ndmstartup.joinstartup.Exceptions.UserChangeTypeConflictException;
import ndmstartup.joinstartup.Exceptions.UserWithThisEmailAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserChangeTypeConflictException.class)
    public ResponseEntity<String> handleUserChangeTypeConflictException (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(StatusAlreadyExistsConflictException.class)
    public ResponseEntity<String> handleStatusAlreadyExistsConflictException (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UserWithThisEmailAlreadyExists.class)
    public ResponseEntity<String> handleUserWithThisEmailAlreadyExists (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
