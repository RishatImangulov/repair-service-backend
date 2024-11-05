package org.richard.backend.exception;

public class DuplicateFullNameException extends RuntimeException {
    public DuplicateFullNameException(String entityName, String message) {
        super("%s: %s".formatted(entityName, message));
    }
}
