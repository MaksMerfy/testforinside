package ru.maksmerfy.testforinside.service;

import ru.maksmerfy.testforinside.dto.AuthRequest;
import ru.maksmerfy.testforinside.dto.AuthResponse;

public interface AuthService {
    AuthResponse auth(AuthRequest authRequest);
}
