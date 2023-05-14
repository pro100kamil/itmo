package common.network.requests;

import common.models.User;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
