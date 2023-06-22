package localizations;

import java.util.ResourceBundle;

public class CurrentLanguage {
    private static ResourceBundle currentResourceBundle = Languages.sv;

    public static ResourceBundle getCurrentResourceBundle() {
        return currentResourceBundle;
    }

    public static void setCurrentResourceBundle(ResourceBundle currentResourceBundle) {
        CurrentLanguage.currentResourceBundle = currentResourceBundle;
    }
}
