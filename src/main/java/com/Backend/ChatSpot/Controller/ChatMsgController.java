package com.Backend.ChatSpot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Backend.ChatSpot.Model.ChatMsg;
import com.Backend.ChatSpot.Model.ChatNotification;
import com.Backend.ChatSpot.Service.ChatMsgService;

@Controller
public class ChatMsgController {

	@Autowired
	private ChatMsgService service;

	@Autowired
	private SimpMessagingTemplate msgtmp;

	@GetMapping("/messages/{SenderId}/{RecieverId}")
	public ResponseEntity<List<ChatMsg>> findChatMessages(@PathVariable("SenderId") String SenderId,
			@PathVariable("RecieverId") String RecieverId) {
		return ResponseEntity.ok(service.findChatMsg(SenderId, RecieverId));
	}

	 @MessageMapping("/chat")
	public void processMessage(@Payload ChatMsg chatmsg) {
		ChatMsg saveMsg = service.save(chatmsg);
		msgtmp.convertAndSendToUser(chatmsg.getRecieverId(), "/queue/messages",
				ChatNotification.builder()
						.SenderId(saveMsg.getSenderId())
						.RecieverId(saveMsg.getRecieverId())
						.Content(saveMsg.getContent())
						.build()
		);
	}
}
