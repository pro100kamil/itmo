package common.consoles;

/**
 * Интерфейс для "консолей".
 * У разных консолей разный ввод/вывод.
 */
public interface Console {
    /**
     * Проверяет, можно ли будет получить следующую строку
     *
     * @return boolean текст из файла
     */
    boolean hasNext();

    /**
     * Получает следующую строку
     *
     * @return String следующая строка
     */
    String getNextStr();

    /**
     * Делает запись текста
     *
     * @param text текст
     */
    void write(String text);
}