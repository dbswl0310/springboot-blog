package com.cos.blog.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	public void 해쉬_암호화() {
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		System.out.println("1234해쉬 : " + encPassword);
	}

}
