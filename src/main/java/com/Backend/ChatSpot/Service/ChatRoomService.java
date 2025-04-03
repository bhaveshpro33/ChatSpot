package com.Backend.ChatSpot.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.ChatSpot.Model.ChatRoom;
import com.Backend.ChatSpot.Repository.ChatRoomRepository;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository repo;
	
	public Optional<String> getChatRoomId(
			String senderId,
			String receiverId,
			Boolean CreateNewRoomIfNorExists ){
		
		return repo.findBySenderIdAndReceiverId(senderId,receiverId)
				.map(ChatRoom::getId)
				.or(()->{
					if(CreateNewRoomIfNorExists) {
						var ChatId=CreateChatId(senderId,receiverId);
						Optional.of(ChatId);
					}
					return Optional.empty();
				});
	}

	private String CreateChatId(String senderId, String receiverId) {
		// TODO Auto-generated method stub
		var ChatId=String.format("%s_%s",senderId,receiverId);
		ChatRoom senderReciever=ChatRoom.builder()
				.senderId(senderId)
				.chatId(ChatId)
				.receiverId(receiverId)
				.build();
		ChatRoom RecieverSender=ChatRoom.builder()
				.senderId(receiverId)
				.chatId(ChatId)
				.receiverId(senderId)
				.build();
		repo.save(senderReciever);
		repo.save(RecieverSender);
		return ChatId;
	}
	
}
