package ru.maksmerfy.testforinside.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.maksmerfy.testforinside.dto.MessageRequest;
import ru.maksmerfy.testforinside.dto.MessageResponse;

public interface MessageApi {
    MessageResponse addMessage(@RequestBody MessageRequest messageRequest);
}
