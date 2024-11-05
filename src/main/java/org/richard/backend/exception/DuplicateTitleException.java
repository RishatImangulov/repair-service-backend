package org.richard.backend.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String entityName) {
        throw new DuplicateTitleException(entityName, Locale.forLanguageTag("ru"));
    }

    public DuplicateTitleException(String entityName, Locale locale) {
        super("%s: %s".formatted(entityName, getLocalizedMessage(locale)));
    }

    private static String getLocalizedMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString("duplicate.title");
    }
}
