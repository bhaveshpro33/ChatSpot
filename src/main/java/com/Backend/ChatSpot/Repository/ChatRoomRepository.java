package com.Backend.ChatSpot.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.Backend.ChatSpot.Model.ChatRoom;

@Repository
@EnableMongoRepositories
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);

}
 