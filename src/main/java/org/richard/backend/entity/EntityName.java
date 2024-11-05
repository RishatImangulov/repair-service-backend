package org.richard.backend.entity;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EntityName {

    PERSON,
    ADVERTISEMENT,
    OFFICE;

    public String getDisplayName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("EntityType", locale);
        return bundle.getString(this.name());
    }

}
