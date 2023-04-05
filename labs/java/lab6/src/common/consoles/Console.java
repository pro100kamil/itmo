package common.consoles;

/**
 * Интерфейс для "консолей". Первая консоль - это стандартный ввод/вывод. Вторая консоль - это считывание из файл и стандартный вывод.
 */
public interface Console {
    /**
     * Проверяет, можно ли будет получить следующую строку
     *
     * @return boolean текст из файла
     */
    public boolean hasNext();

    /**
     * Получает следующую строку
     *
     * @return String следующая строка
     */
    public String getNextStr();

    /**
     * Делает запись текста
     *
     * @param text текст
     */
    public void write(String text);
}