package ru.maksmerfy.testforinside.service;

import ru.maksmerfy.testforinside.dto.MessageResponse;

public interface MessageService {
    MessageResponse readMessage(String userName, String inputMessage);
}
