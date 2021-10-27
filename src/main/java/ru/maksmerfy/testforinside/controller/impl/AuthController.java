package ru.maksmerfy.testforinside.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maksmerfy.testforinside.controller.AuthApi;
import ru.maksmerfy.testforinside.dto.AuthRequest;
import ru.maksmerfy.testforinside.dto.AuthResponse;
import ru.maksmerfy.testforinside.service.impl.AuthServiceImpl;

@RestController
public class AuthController implements AuthApi {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Override
    @PostMapping("/login")
    public AuthResponse auth(@RequestBody AuthRequest authRequest){
        return authServiceImpl.auth(authRequest);
    }
}
