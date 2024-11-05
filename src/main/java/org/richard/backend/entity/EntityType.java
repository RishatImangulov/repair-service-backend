package org.richard.backend.entity;

import java.util.Locale;

public enum EntityType {

    PERSON("Person", "Клиент"),
    ADVERTISEMENT("Advertisement", "Реклама"),
    OFFICE("Office", "Офис");

    private final String displayNameEn;
    private final String displayNameRu;

    EntityType(String displayNameEn, String displayNameRu) {
        this.displayNameEn = displayNameEn;
        this.displayNameRu = displayNameRu;
    }

    public String getDisplayName(Locale locale) {
        if (Locale.forLanguageTag("en").equals(locale)) {
            return displayNameEn;
        } else {
            return displayNameRu; // Default to Russian
        }
    }

}
