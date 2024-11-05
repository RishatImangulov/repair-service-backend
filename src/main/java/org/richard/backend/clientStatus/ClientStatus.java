package org.richard.backend.clientStatus;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ClientStatus {
    FIRST_CONTACT("first_contact"),
    REPEATED_CONTACT("repeated_contact"),
    REGULAR_CLIENT("regular_client"),
    VIP("vip"),
    BLACKLIST("blacklist");

    private final String key;

    ClientStatus(String key) {
        this.key = key;
    }

    public String getDisplayName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("ClientStatusMessages", locale);
        return bundle.getString(this.key);
    }

}