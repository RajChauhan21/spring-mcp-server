package com.tutorail.ai.mcp_server;

import com.tutorail.ai.mcp_server.entity.User;
import com.tutorail.ai.mcp_server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class McpServerApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void SaveUserInDb() {
		User user = new User();
		user.setName("Raj");

		User user1 = new User();
		user1.setName("Ram");

		User user2 = new User();
		user2.setName("Raman");

		userRepository.saveAll(List.of(user,user2,user1));
	}

}
