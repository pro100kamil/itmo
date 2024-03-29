package common.network.responses;

import common.models.User;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private User user; //если user==null - пользователь не зарегистрирован

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
