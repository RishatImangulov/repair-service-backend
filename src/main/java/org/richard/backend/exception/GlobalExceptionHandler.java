package org.richard.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions thrown when a method argument annotated with @Valid fails validation.
     * Extracts and formats the field-specific error messages to provide a user-friendly response.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity with a status of 400 BAD REQUEST and a detailed error message
     */
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

    /**
     * Handles exceptions where an entity is not found by its UUID.
     *
     * @param e the NotFoundEntityByUuid exception
     * @return ResponseEntity with a status of 404 NOT FOUND and the exception's message
     */
    @ExceptionHandler(NotFoundEntityByUuid.class)
    public ResponseEntity<String> handleNotFoundEntityByUuid(NotFoundEntityByUuid e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles exceptions thrown when the 'title' field is blank or invalid.
     *
     * @param e the TitleIsBlank exception
     * @return ResponseEntity with a status of 400 BAD REQUEST and the exception's message
     */
    @ExceptionHandler(TitleIsBlank.class)
    public ResponseEntity<String> handleTitleIsBlank(TitleIsBlank e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles exceptions thrown when there is an attempt to create or update an advertisement with a duplicate title.
     *
     * @param e the DuplicateTitleException exception
     * @return ResponseEntity with a status of 400 BAD REQUEST and the exception's message
     */
    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<String> handleDuplicateTitleException(DuplicateTitleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
