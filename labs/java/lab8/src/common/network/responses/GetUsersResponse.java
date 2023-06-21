package common.network.responses;

import common.models.User;

import java.util.List;

/**
 * Ответ на запрос о получении всех пользователей
 */
public class GetUsersResponse extends Response {
    private final List<User> users;

    public GetUsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
