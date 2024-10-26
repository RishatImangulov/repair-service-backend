package org.richard.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation failed fo fields: ");

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(String.format("'%s': %s (rejected value: [%s]). ",
                    error.getField(),
                    error.getDefaultMessage(),
                    error.getRejectedValue()));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }

    @ExceptionHandler(NotFoundEntityByUuid.class)
    public ResponseEntity<String> handleNotFoundEntityByUuid(NotFoundEntityByUuid e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    @ExceptionHandler(TitleIsBlank.class)
    public ResponseEntity<String> handleTitleIsBlank(TitleIsBlank e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<String> handleDuplicateTitleException(DuplicateTitleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidUUIDFormat(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == UUID.class) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid UUID format for the requested ID.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

}
