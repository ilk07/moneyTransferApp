package cp.moneytransferapp.advice;

import cp.moneytransferapp.exception.Error;
import cp.moneytransferapp.exception.OutOfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice()
public class ExceptionHandlerAdvice {

    @ExceptionHandler(OutOfService.class)
    public ResponseEntity<Error> outOfServiceHandler(OutOfService e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        Error error = new Error(errors.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
