package ru.maksmerfy.testforinside.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maksmerfy.testforinside.config.jwt.JwtProvider;
import ru.maksmerfy.testforinside.dto.AuthRequest;
import ru.maksmerfy.testforinside.dto.AuthResponse;
import ru.maksmerfy.testforinside.models.User;
import ru.maksmerfy.testforinside.service.AuthService;
import ru.maksmerfy.testforinside.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    @Override
    public AuthResponse auth(AuthRequest authRequest){
        AuthResponse authResponse = new AuthResponse("");
        if (authRequest == null) return authResponse;
        User user = userService.findByUsername(authRequest.getName());
        if (user == null) user = registerUser(authRequest.getName(), authRequest.getPassword());
        if (user != null) {
            user = userService.findByUsernameAndPassword(authRequest.getName(), authRequest.getPassword());
            if (user != null) authResponse.setToken(jwtProvider.generateToken(user.getUsername()));
        }
        return authResponse;
    }

    private User registerUser(String userName, String password){
        User user = null;
        if (userName != null && password !=null && !userName.equals("") && !password.equals("")){
            user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            userService.saveUser(user);
        }
        return user;
    }
}
