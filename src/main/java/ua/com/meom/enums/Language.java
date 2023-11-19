package ua.com.meom.enums;

import java.util.Locale;

/**
 * This enum contains all supported in the application languages
 */
public enum Language {

    UKRAINIAN("УКР", new Locale("uk", "UA")),
    ENGLISH("ENG", new Locale("en", "US"));

    private final String keyboardName;
    private final Locale locale;

    Language(String keyboardName, Locale locale) {
        this.keyboardName = keyboardName;
        this.locale = locale;
    }

    public String getKeyboardName() {
        return keyboardName;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + keyboardName + '\'' +
                ", locale=" + locale +
                '}';
    }
}
