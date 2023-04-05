package client.managers;

import client.Client;
import common.commands.Command;
import common.commands.ExecuteScript;
import common.commands.Exit;
import common.models.*;
import common.exceptions.*;
import common.consoles.Console;

import java.util.NoSuchElementException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Класс для работы с вводом
 */
public class InputManager {
    private final Console console;

    private static final String stopWorkerInput = "EXIT";  //слово, при котором обрывается ввод работника

    public InputManager(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return console;
    }

    /**
     * Получает целое число типа Integer (не null) из стандартного ввода
     *
     * @param text     текст, который подсказывает что и как вводить
     * @param positive true - число только положительное, false - любое
     * @return Integer введённое целое число
     */
    public Integer getInteger(String text, boolean positive) throws EndInputException, EndInputWorkerException {
        String tmp;  //временное хранение ввода
        int x;
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
            if (ValidateManager.isInteger(tmp)) {
                x = Integer.parseInt(tmp);
                if (x > 0 || (!positive)) {
                    return x;
                }
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
     * @return Float введённое целое число (может быть null)
     */
    public Float getFloat(String text, boolean notNull, boolean positive) throws EndInputException, EndInputWorkerException {
        String tmp; //временное хранение ввода
        float x;
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
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
    public Position getPosition() throws EndInputException, EndInputWorkerException {
        StringBuilder text = new StringBuilder("Введите позицию работника или пустую строку для null. Варианты: ");
        for (Position el : Position.values()) {
            text.append(el).append(" ");
        }

        String tmp; //временное хранение ввода
        console.write(text.toString());
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
            if (tmp.equals("")) {
                return null;
            }
            try {
                return Position.valueOf(tmp);
            } catch (IllegalArgumentException e) {
                console.write(text.toString());
            }
        }
        throw new EndInputException();
    }

    /**
     * Получает статус работника из стандартного ввода
     *
     * @return Status статус работника (может быть null)
     */
    public Status getStatus() throws EndInputException, EndInputWorkerException {
        StringBuilder text = new StringBuilder("Введите статус работника или пустую строку для null. Варианты: ");
        for (Status el : Status.values()) {
            text.append(el).append(" ");
        }

        String tmp; //временное хранение ввода
        console.write(text.toString());
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
            if (tmp.equals("")) {
                return null;
            }
            try {
                return Status.valueOf(tmp);
            } catch (IllegalArgumentException e) {
                console.write(text.toString());
            }
        }
        throw new EndInputException();
    }

    /**
     * Получает дату типа LocalDate (может быть null) из стандартного ввода
     *
     * @param text текст, который подсказывает что и как вводить
     * @return LocalDate введённая дата (может быть null)
     */
    public LocalDate getDate(String text) throws EndInputException, EndInputWorkerException {
        String tmp; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
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
     * Получает строку, состоящую не только из пробельных символов
     *
     * @param text      текст, который подсказывает что и как вводить
     * @param minLength минимальная допустимая длина для вводимой строки
     * @param notNull   true - число не может быть null, false - может
     * @return Float введённое целое число (может быть null)
     */
    public String getNotBlankString(String text, int minLength, boolean notNull) throws EndInputException, EndInputWorkerException {
        String tmp; //временное хранение ввода
        console.write(text);
        while (console.hasNext()) {
            tmp = console.getNextStr();
            if (tmp.equals(stopWorkerInput)) {
                throw new EndInputWorkerException();
            }
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
     * @return Worker - работник с введёнными полями
     */
    public Worker getWorker() throws EndInputException, EndInputWorkerException {
        String name = getNotBlankString("Введите имя работника:", 1, true);

        console.write("Введите координаты работника (X, Y).");

        Integer x = getInteger("Введите X (целое число):", false);
        Integer y = getInteger("Введите Y (целое число):", false);

        Coordinates coordinates = new Coordinates(x, y);

        Float salary = getFloat("Введите зарплату работника (вещественное число, целую и дробную часть разделяйте точкой) или пустую строку для null:", false, true);

        Position position = getPosition();
        Status status = getStatus();

        LocalDate birthday = getDate("Введите день рождения в формате 'yyyy-mm-dd' или пустую строку для null:");

        float height = getFloat("Введите рост работника (вещественное число, целую и дробную часть разделяйте точкой):", true, true);

        String passportID = getNotBlankString("Введите id паспорта (более 6 символов) или пустую строку для null:", 7, false);

        Person person = new Person(birthday, height, passportID);

        return new Worker(name, coordinates, salary, position, status, person);

    }

    /**
     * Запускает интерактивный режим
     */
    public void run() {
        CommandManager commandManager = new CommandManager(this);
        while (console.hasNext()) {
            String text = "Введите команду (help - чтобы узнать команды):";
            console.write(text);
            console.write("--------------------------");
            try {
                String strCommand = console.getNextStr();
                Command command = commandManager.getCommand(strCommand);
                if (command instanceof Exit) { //команда exit выполняется на стороне клиента
                    command.execute(command.getArgs());
                }
                else if (command instanceof ExecuteScript) {
                    command.setConsole(console);
                    command.execute(command.getArgs());
                }
                else {
                    new Client().run(command);
                }
            } catch (NoSuchCommandException | WrongCommandArgsException | NonExistentId e) {
                console.write(e.toString());
            } catch (NoSuchElementException | EndInputException | EndInputWorkerException e) {
                console.write("");
            }
            console.write("--------------------------");
        }
    }
}