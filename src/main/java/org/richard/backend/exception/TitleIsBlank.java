package org.richard.backend.exception;

public class TitleIsBlank extends RuntimeException {
    public TitleIsBlank(String entity) {
        super("Title %s can't be blank".formatted(entity));
    }
}
