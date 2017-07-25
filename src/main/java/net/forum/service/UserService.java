package net.forum.service;

import net.forum.model.User;

import java.util.List;

/**
 * Service class for {@link User}
 */

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> getAllUsers();
}
