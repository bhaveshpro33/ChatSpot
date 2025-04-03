package com.Backend.ChatSpot.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User {
	
	@Id
	private String nickname;
	private String fullname;
	private Status status;
}
