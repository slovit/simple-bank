package model.dao;

import model.entity.User;
import model.entity.UserRole;

public interface UserDao {

    boolean addUser(User user);

    User getUserById(int id);

    User getUserByLoginPassword(String login, String password);

    UserRole getUserRoleByLoginPassword(String login, String password);

    void deleteUserById(int id);

    boolean userExists(String login, String password);

}
