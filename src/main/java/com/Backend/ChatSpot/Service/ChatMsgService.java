package com.Backend.ChatSpot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.ChatSpot.Model.ChatMsg;
import com.Backend.ChatSpot.Repository.ChatMsgRepository;


@Service
public class ChatMsgService {

	@Autowired
	private ChatMsgRepository repo;
	@Autowired
	private ChatRoomService CRservice;

	public ChatMsg save(ChatMsg msg) {
		var chatId = CRservice.getChatRoomId(msg.getSenderId(), msg.getRecieverId(), true).orElseThrow();
		msg.setChatId(chatId);
		return repo.save(msg);
	}

	public List<ChatMsg> findChatMsg(String SenderId, String RecieverId) {
		var chatId = CRservice.getChatRoomId(SenderId, RecieverId, false);
		return chatId.map(repo::findByChatId).orElse(new ArrayList<>());
	}
}
