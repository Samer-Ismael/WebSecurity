package com.SpringSecurity.Samer;

import com.SpringSecurity.Samer.controller.UserController;
import com.SpringSecurity.Samer.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SamerApplicationTests {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void userControllerLoads() {
		assertNotNull(userController);
	}

	// Add more tests here...
}