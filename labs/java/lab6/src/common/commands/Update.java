package common.commands;

import client.managers.ValidateManager;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;

/**
 * Команда update.
 * Обновляет работника по id на основе заданного работника.
 */
public class Update extends CommandWithWorker {
    public boolean ready;

    public Update() {
        super("update", "обновляет работника по id на основе заданного работника");
        ready = false;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    /**
     * Часть валидации, которая происходит на сервере.
     * Потому что на клиенте у нас нет информации по поводу id.
     */
    public void serverValidateArgs(String[] args) throws NonExistentId {
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            serverValidateArgs(args);
            collectionManager.update(Integer.parseInt(args[0]), worker);
        } catch (WrongCommandArgsException | NonExistentId e) {
            console.write(e.toString());
        }
    }

    /**
     * Возвращает готовность команды к заданию работника.
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Задаёт готовность команды к заданию работника.
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }
}