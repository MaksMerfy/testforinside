package ru.maksmerfy.testforinside.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maksmerfy.testforinside.controller.MessageApi;
import ru.maksmerfy.testforinside.dto.MessageRequest;
import ru.maksmerfy.testforinside.dto.MessageResponse;
import ru.maksmerfy.testforinside.service.MessageService;

@RestController
public class MessageController implements MessageApi {
    @Autowired
    private MessageService messageService;

    @Override
    @PostMapping("/message")
    public MessageResponse addMessage(@RequestBody MessageRequest messageRequest){
        return messageService.readMessage(messageRequest.getName(), messageRequest.getMessage());
    }
}
