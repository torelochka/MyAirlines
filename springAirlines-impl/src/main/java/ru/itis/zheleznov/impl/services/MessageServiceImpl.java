package ru.itis.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zheleznov.api.dto.MessageDto;
import ru.itis.zheleznov.api.forms.MessageForm;
import ru.itis.zheleznov.api.services.MessageService;
import ru.itis.zheleznov.impl.models.Message;
import ru.itis.zheleznov.impl.repositories.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageDto save(MessageForm messageForm) {
        Message message = Message.builder()
                .text(messageForm.getText())
                .username(messageForm.getFrom())
                .build();

        return modelMapper.map(messageRepository.save(message), MessageDto.class);
    }

    @Override
    public List<MessageDto> allMessages() {
        return messageRepository.findAll()
                .stream().map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }
}
