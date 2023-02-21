package managers;

import models.*;
import commands.*;
import exceptions.*;

import java.util.TreeMap;
import java.util.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Класс для работы с вводом
 */
public class InputManager {
    private Console console;
    private CollectionManager collectionManager;
    private String dataFileName;  //файл, из которого мы берём коллекцию и в который сохраняем

    public InputManager(Console console, CollectionManager collectionManager, String dataFileName) {
        this.console = console;
        this.collectionManager = collectionManager;
        this.dataFileName = dataFileName;
    }

    /**
     * Получает целое число типа Integer (не null) из стандартного ввода
     *
     * @param text текст, который подсказывает что и как вводить
     * @return Integer ввёденное целое число
     */
    public Integer getInteger(String text) throws EndInputException {
        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (ValidateManager.isInteger(tmp)) {
                return Integer.parseInt(tmp);
            }
            console.write(text);
        }
        throw new EndInputException();
    }

    /**
     * Получает вещественное число типа Float из стандартного ввода
     *
     * @param text     текст, который подсказывает что и как вводить
     * @param notNull  true - число не может быть null, false - может
     * @param positive true - число только положительное, false - любое
     * @return Float ввёденное целое число (может быть null)
     */
    public Float getFloat(String text, boolean notNull, boolean positive) throws EndInputException {
        String tmp = ""; //временное хранение ввода
        Float x;
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals("") && !notNull) {
                return null;
            }
            if (ValidateManager.isFloat(tmp)) {
                x = Float.parseFloat(tmp);
                if (x > 0 || (x <= 0 && !positive)) {
                    return x;
                }
            }
            console.write(text);
        }
        throw new EndInputException();
    }

    /**
     * Получает позицию работника из стандартного ввода
     *
     * @return Position позиция работника (может быть null)
     */
    public Position getPosition() throws EndInputException {
        String text = "Введите позицию работника или пустую строку для null. Варианты: ";
        for (Position el : Position.values()) {
            text += el + " ";
        }

        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals("")) {
                return null;
            }
            try {
                return Position.valueOf(tmp);
            } catch (IllegalArgumentException e) {
                console.write(text);
            }
        }
        throw new EndInputException();
    }

    /**
     * Получает статус работника из стандартного ввода
     *
     * @return Status статус работника (может быть null)
     */
    public Status getStatus() throws EndInputException {
        String text = "Введите статус работника или пустую строку для null. Варианты: ";
        for (Status el : Status.values()) {
            text += el + " ";
        }

        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals("")) {
                return null;
            }
            try {
                return Status.valueOf(tmp);
            } catch (IllegalArgumentException e) {
                console.write(text);
            }
        }
        throw new EndInputException();
    }

    /**
     * Получает дату типа LocalDate (может быть null) из стандартного ввода
     *
     * @param text текст, который подсказывает что и как вводить
     * @return LocalDate ввёденная дата (может быть null)
     */
    public LocalDate getDate(String text) throws EndInputException {
        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals("")) {
                return null;
            }
            try {
                return LocalDate.parse(tmp);
            } catch (DateTimeParseException e) {
                console.write(text);
            }
        }
        throw new EndInputException();
    }

    /**
     * Получает строку состоящую не только из пробельных символов
     *
     * @param text      текст, который подсказывает что и как вводить
     * @param minLength минимальная допустимая длина для вводимой строки
     * @param notNull   true - число не может быть null, false - может
     * @return Float ввёденное целое число (может быть null)
     */
    public String getNotBlankString(String text, int minLength, boolean notNull) throws EndInputException {
        String tmp = ""; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (!notNull && tmp.equals("")) {
                return null;
            }
            if (!tmp.isBlank() && tmp.length() >= minLength) {
                return tmp;
            }
            console.write(text);
        }
        throw new EndInputException();
    }

    /**
     * Получает данные из стандартного ввода о работнике и возвращает работника
     *
     * @return Worker работник с введёнными полями
     */
    public Worker getWorker() {
        try {
            String tmp = ""; //временное хранение ввода

            String name = getNotBlankString("Введите имя работника:", 1, true);

            console.write("Введите координаты работника (X, Y).");

            Integer x = getInteger("Введите X (целое число):");
            Integer y = getInteger("Введите Y (целое число):");

            Coordinates coordinates = new Coordinates(x, y);

            Float salary = getFloat("Введите зарплату работника (вещественное число, целую и дробную часть разделяйте точкой) или пустую строку для null:", false, true);

            Position position = getPosition();
            Status status = getStatus();

            LocalDate birthday = getDate("Введите день рождения в формате 'yyyy-mm-dd' или пустую строку для null:");

            float height = getFloat("Введите рост работника (вещественнное число, целую и дробную часть разделяйте точкой):", true, true).floatValue();

            String passportID = getNotBlankString("Введите id пасспорта (более 6 символов) или пустую строку для null:", 7, false);

            Person person = new Person(birthday, height, passportID);

            return new Worker(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), salary, position, status, person);
        } catch (EndInputException e) {
            return null;
        }
    }

    /**
     * Запускает интерактивный режим
     */
    public void run() {
        Command command;
        CommandManager commandManager = new CommandManager(collectionManager, this, dataFileName);
        while (console.hasNext()) {
            String text = "Введите команду (help - чтобы узнать команды):";
            console.write(text);
            console.write("--------------------------");
            try {
                commandManager.executeCommand(console.getNextStr());
            } catch (NoSuchCommandException e) {
                console.write(e.toString());
            } catch (Exception e) {

            }
            console.write("--------------------------");
            console.write("");
        }
    }
}