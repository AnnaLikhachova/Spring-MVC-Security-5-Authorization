package guide.tour.security;

import java.util.ArrayList;
import java.util.List;

public class ActiveUser {

    public List<String> users;

    public ActiveUser() {
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}