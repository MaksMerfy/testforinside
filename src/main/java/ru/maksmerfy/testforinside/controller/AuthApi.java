package ru.maksmerfy.testforinside.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.maksmerfy.testforinside.dto.AuthRequest;
import ru.maksmerfy.testforinside.dto.AuthResponse;

public interface AuthApi {
    AuthResponse auth(@RequestBody AuthRequest authRequest);
}
