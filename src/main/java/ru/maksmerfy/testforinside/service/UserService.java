package ru.maksmerfy.testforinside.service;

import ru.maksmerfy.testforinside.models.User;

public interface UserService {
    User saveUser(User user);

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
