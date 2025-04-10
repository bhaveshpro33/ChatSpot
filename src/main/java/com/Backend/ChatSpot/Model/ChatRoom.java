package com.Backend.ChatSpot.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
@Document
public class ChatRoom {

	@Id
	private String id;
	private String chatId;
	private String senderId;
	private String receiverId;
}
