package common.loggers;

public interface Logger {
    /**
     * Выводит текст
     *
     * @param text текст
     */
    void write(String text);

    /**
     * Выводит ошибку
     *
     * @param text текст ошибки
     */
    void writeError(String text);
}
