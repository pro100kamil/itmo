package server.commands;

import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongCredentialsException;
import server.managers.AuthManager;

public class Register extends ServerCommand {
    private AuthManager authManager = new AuthManager();
    public Register() {
        super("register", "производит регистрацию пользователя", false, false);
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
        if (!authManager.checkUserName(args[0])) {//если такого логина не существует
            throw new WrongCredentialsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            boolean flag = authManager.register(args[0], args[1]);
            System.out.println(flag);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
