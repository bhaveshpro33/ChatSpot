package com.Backend.ChatSpot.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.ChatSpot.Model.Status;
import com.Backend.ChatSpot.Model.User;
import com.Backend.ChatSpot.Repository.UserRepository;



@Service

public class UserService {
	@Autowired
	private UserRepository repo;

	public User saveUser(User user) {
		user.setStatus(Status.ONLINE);
		return repo.save(user);
	}

	public User disconnectUser(User user) {
		var Storeduser = repo.findById(user.getNickname()).orElse(null);
		if (Storeduser != null) {
			Storeduser.setStatus(Status.OFFLINE);
			System.out.println("updated");
			return repo.save(Storeduser);
		}
		return null;
	}

	public List<User> findConnectedUser() {
		return repo.findByStatus(Status.ONLINE);
	}
}
