package com.Backend.ChatSpot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.ChatSpot.Model.User;
import com.Backend.ChatSpot.Service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@MessageMapping("/user.addUser")
	@SendTo("/user/topic")
	public User addUser(@Payload User user) {
		return service.saveUser(user);

	}
	@MessageMapping("/user.disconnectUser")
	@SendTo("/user/topic")
	public User disconnect(@Payload User user) {
		System.out.println("reached");
		return service.disconnectUser(user);

	}
	
	@GetMapping("/users")
	public List<User> findConnectedUser(){
//		System.out.println("reached");
		return service.findConnectedUser();
	}
}
