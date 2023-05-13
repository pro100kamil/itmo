package server.commands;

import common.exceptions.WrongCommandArgsException;
import server.managers.AuthManager;

public class Logout extends ServerCommand {
    private AuthManager authManager = new AuthManager();
    public Logout() {
        super("logout", "выходит из аккаунта",
                false, true);
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
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
            collectionManager.setUser(null);
            console.write("Вы вышли из аккаунта");
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

