package ru.maksmerfy.testforinside.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.maksmerfy.testforinside.dto.MessageResponse;
import ru.maksmerfy.testforinside.models.Message;
import ru.maksmerfy.testforinside.models.User;
import ru.maksmerfy.testforinside.repository.MessageRepository;
import ru.maksmerfy.testforinside.service.MessageService;
import ru.maksmerfy.testforinside.service.UserService;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    private User user;
    private static final HttpStatus BAD_STATUS = HttpStatus.BAD_REQUEST;

    private MessageResponse validateUserAndMessage(String userName, String inputMessage){
        MessageResponse messageResponse = new MessageResponse(HttpStatus.OK, null);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(userName)) messageResponse.setStatus(BAD_STATUS);
        if (userName == null || inputMessage == null || userName.equals("") || inputMessage.equals("")) messageResponse.setStatus(BAD_STATUS);
        user = userService.findByUsername(userName);
        if (user == null) messageResponse.setStatus(BAD_STATUS);
        return messageResponse;
    }

    private MessageResponse saveMessage(String userName, String inputMessage) {
        MessageResponse messageResponse = validateUserAndMessage(userName, inputMessage);
        if (messageResponse.getStatus() != BAD_STATUS) {
            try {
                Message message = new Message();
                message.setMessage(inputMessage);
                message.setUser(user);
                message.setDate(Calendar.getInstance().getTime());
                messageRepository.save(message);
            } catch (Exception e) {
                messageResponse.setStatus(BAD_STATUS);
            }
        }
        return messageResponse;
    }

    private List<String> findLastByUser(int limit) {
        List<String> messageList = null;
        if (user != null && limit != 0){
            messageList = messageRepository.findAllByUserOrderByDateDesc(user.getId(), limit);
        }
        return messageList;
    }

    private MessageResponse readHistory(String userName, int limit){
        MessageResponse messageResponse = validateUserAndMessage(userName, "Valid");
        if (messageResponse.getStatus() != BAD_STATUS && limit != 0) {
            messageResponse.setData(findLastByUser(limit));
        } else{
            messageResponse.setStatus(BAD_STATUS);
        }
        return messageResponse;
    }

    @Override
    public MessageResponse readMessage(String userName, String inputMessage) {
        String[] arrayListMessage = inputMessage.split(" ");
        MessageResponse messageResponse = null;
        if (arrayListMessage[0].equals("history")){
            int limit = 0;
            try {
                limit = Integer.parseInt(arrayListMessage[1]);
            } catch (NumberFormatException e){
                log.warn("Exception : {} in Input message : {}",e, inputMessage);
            }
            messageResponse = readHistory(userName, limit);
        } else {
            messageResponse = saveMessage(userName, inputMessage);
        }
        return messageResponse;
    }
}
