package org.richard.backend.exception;

public class NotFoundEntityByUuid extends RuntimeException {
    public NotFoundEntityByUuid(String entity, String uuid) {
        super("Cant find entity %s with UUID %s".formatted(entity, uuid));
    }
}
