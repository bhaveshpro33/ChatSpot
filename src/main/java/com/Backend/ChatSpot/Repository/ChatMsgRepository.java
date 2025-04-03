package com.Backend.ChatSpot.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.Backend.ChatSpot.Model.ChatMsg;

@Repository
@EnableMongoRepositories
public interface ChatMsgRepository extends MongoRepository<ChatMsg, String>{

	List<ChatMsg> findByChatId(String chatId);


}
