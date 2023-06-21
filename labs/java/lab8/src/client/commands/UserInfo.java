package client.commands;

import common.exceptions.WrongCommandArgsException;
import common.models.User;

/**
 * Команда user_info.
 * Выводит информацию о текущем пользователе.
 */
public class UserInfo extends ClientCommand {
    private User user;

    public UserInfo() {
        super("user_info", "выводит информацию о текущем пользователе",
                false, false);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            if (user == null) {
                console.write("Вы не вошли в аккаунт");
            }
            else {
                console.write(user.toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

