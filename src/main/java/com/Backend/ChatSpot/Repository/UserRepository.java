package com.Backend.ChatSpot.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.Backend.ChatSpot.Model.Status;
import com.Backend.ChatSpot.Model.User;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String>{

	

	List<User> findByStatus(Status status);




}
