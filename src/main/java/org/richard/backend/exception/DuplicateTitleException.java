package org.richard.backend.exception;

public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String entityName, String message) {
        super("%s: %s".formatted(entityName, message));
    }
}
