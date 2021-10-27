package ru.maksmerfy.testforinside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    private String name;
    private String message;

}
