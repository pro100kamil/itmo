package localizations;

import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {

    public static final ResourceBundle ru = ResourceBundle.getBundle("resources.locales.gui", new Locale("ru", "RU"));

    public static final ResourceBundle en = ResourceBundle.getBundle("resources.locales.gui", Locale.ENGLISH);

    public static final ResourceBundle nl = ResourceBundle.getBundle("resources.locales.gui", new Locale("nl", "NL"));

    public static final ResourceBundle sv = ResourceBundle.getBundle("resources.locales.gui", new Locale("sv", "SE"));

}
