package server.commands;

import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongCredentialsException;
import server.managers.AuthManager;

public class Auth extends ServerCommand {
    private AuthManager authManager;
    public Auth() {
        super("auth", "производит вход пользователя", false, false);
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 2) {
            throw new WrongCommandArgsException();
        }
        if (!authManager.checkUserPass(args[0], args[1])) {//если такого логина и пароля не существует
            throw new WrongCredentialsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            authManager.auth(args[0], args[1]);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
