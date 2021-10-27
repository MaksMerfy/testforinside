package ru.maksmerfy.testforinside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageResponse {
    private HttpStatus status;
    private List<String> data;
}
