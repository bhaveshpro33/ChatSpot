package com.Backend.ChatSpot.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatNotification {

	private String Id;
	private String SenderId;
	private String RecieverId;
	private String Content;
}
