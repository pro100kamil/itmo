package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.managers.ValidateManager;
import common.models.UserRole;

import java.sql.SQLException;

/**
 * Команда change_user_role user_id role.
 * Изменяет роль пользователя.
 * Роли может менять только админ. Себе он не может изменить роль, других админов он назначить не может.
 */
public class ChangeUserRole extends ServerCommand {
    public ChangeUserRole() {
        super("change_role", "изменяет роль пользователя", false, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId {
        if (args.length != 2 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);

            int count = userDatabaseManager.changeUserRole(Integer.parseInt(args[0]), UserRole.valueOf(args[1]));
            if (count == 0) {
                console.write("Изменить роль не получилось");
            }
        } catch (WrongCommandArgsException | NonExistentId e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Изменить роль не получилось");
        }
    }
}
