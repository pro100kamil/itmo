package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.UnavailableModelException;
import common.exceptions.WrongCommandArgsException;
import common.managers.ValidateManager;

/**
 * Команда change_user_role user_id role.
 * Изменяет роль пользователя.
 * Роли может менять только админ. Себе он не может изменить роль, других админов он назначить не может.
 */
public class ChangeUserRole extends ServerCommand {
    public ChangeUserRole() {
        super("change_user_role", "изменяет роль пользователя", false, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId, UnavailableModelException {
        if (args.length != 2 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {

    }
}
