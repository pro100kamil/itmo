package server;

/**
 * Класс конфигураций сервера (порт, хост,
 * название файла со стартовым состоянием коллекции, название файла с историей состояний)
 */
public class Configuration {
    private static String host;  // хост, на котором запускается сервер
    private static int port;  // порт, на котором запускается сервер
    private static String startFileName;  //файл, содержащий коллекцию
    private static String historyFileName;  // файл, содержащий состояния коллекции

    private static String dbUrl;   // url-адрес для подключения к бд
    private static String dbLogin; // логин для подключения к бд
    private static String dbPass;  //пароль для подключения

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Configuration.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Configuration.port = port;
    }

    public static String getStartFileName() {
        return startFileName;
    }

    public static void setStartFileName(String startFileName) {
        Configuration.startFileName = startFileName;
    }

    public static String getHistoryFileName() {
        return historyFileName;
    }

    public static void setHistoryFileName(String historyFileName) {
        Configuration.historyFileName = historyFileName;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String dbUrl) {
        Configuration.dbUrl = dbUrl;
    }

    public static String getDbLogin() {
        return dbLogin;
    }

    public static void setDbLogin(String dbLogin) {
        Configuration.dbLogin = dbLogin;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static void setDbPass(String dbPass) {
        Configuration.dbPass = dbPass;
    }
}
