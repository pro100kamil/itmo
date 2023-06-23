package localizations;

import java.util.ResourceBundle;

public class CurrentLanguage {
    private static String language = "en";
    private static ResourceBundle currentResourceBundle = Languages.getResourceBundle("en");

    public static ResourceBundle getCurrentResourceBundle() {
        return currentResourceBundle;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        CurrentLanguage.language = language;
        currentResourceBundle = Languages.getResourceBundle(language);
    }
}
