package com.example.webstudy;

import com.example.webstudy.domain.user.Role;
import org.junit.jupiter.api.Test;
class WebStudyApplicationTests {

	@Test
	void contextLoads() {

		System.out.println(Role.GUEST.name());
	}

}
