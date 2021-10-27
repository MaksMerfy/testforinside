package ru.maksmerfy.testforinside.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksmerfy.testforinside.models.User;
import ru.maksmerfy.testforinside.repository.RoleRepositopry;
import ru.maksmerfy.testforinside.repository.UserRepository;
import ru.maksmerfy.testforinside.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepositopry roleRepositopry;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setRole(roleRepositopry.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user= findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) return user;
        return null;
    }
}
