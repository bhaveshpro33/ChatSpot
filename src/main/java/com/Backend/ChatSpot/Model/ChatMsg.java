package com.Backend.ChatSpot.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document
public class ChatMsg {

	@Id
	private String id;
	private String chatId;
	private String senderId;
	private String recieverId;
	private String content;
	private Date timestamp;
}
