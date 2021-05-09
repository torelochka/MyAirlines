package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.MessageDto;
import ru.itis.zheleznov.api.forms.MessageForm;

import java.util.List;

public interface MessageService {
    MessageDto save(MessageForm messageForm);
    List<MessageDto> allMessages();
}
