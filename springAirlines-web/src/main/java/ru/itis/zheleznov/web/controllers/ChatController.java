package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.zheleznov.api.dto.MessageDto;
import ru.itis.zheleznov.api.forms.MessageForm;
import ru.itis.zheleznov.api.services.MessageService;


@Controller
public class ChatController {

	private final MessageService messageService;

	@Autowired
	public ChatController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/chat")
	public String getChatPage(Model model) {
		model.addAttribute("messages", messageService.allMessages());
		return "chat";
	}

	@MessageMapping("/message")
	@SendTo("/chat/messages")
	public MessageDto getMessages(MessageForm message) {
		return messageService.save(message);
	}

}
