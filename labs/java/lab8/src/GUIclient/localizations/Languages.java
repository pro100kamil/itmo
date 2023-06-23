package localizations;

import java.util.*;

public class Languages {

    private static final ResourceBundle ru = ResourceBundle.getBundle("resources.locales.gui", new Locale("ru", "RU"));

    private static final ResourceBundle en = ResourceBundle.getBundle("resources.locales.gui", Locale.ENGLISH);

    private static final ResourceBundle nl = ResourceBundle.getBundle("resources.locales.gui", new Locale("nl", "NL"));

    private static final ResourceBundle sv = ResourceBundle.getBundle("resources.locales.gui", new Locale("sv", "SE"));

    private static final List<String> stringLanguages = List.of(
            "ru",
            "en",
            "nl",
            "sv"
    );

    private static final Map<String, ResourceBundle> map = new HashMap<>();

    static {
        map.put("ru", ru);
        map.put("en", en);
        map.put("nl", nl);
        map.put("sv", sv);
    }

    public static ResourceBundle getRu() {
        return ru;
    }

    public static ResourceBundle getEn() {
        return en;
    }

    public static ResourceBundle getNl() {
        return nl;
    }

    public static ResourceBundle getSv() {
        return sv;
    }

    public static List<String> getStringLanguages() {
        return stringLanguages;
    }

    public static ResourceBundle getResourceBundle(String key) {
        return map.get(key);
    }
}
