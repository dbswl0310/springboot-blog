package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired	// 의존성 주입(DI) -> 메모리에 뜸!
	// @Autowired의 의미 -> DummyControllerTest가 메모리에 띄워질 때, UserRepository도 메모리에 띄워진다.
	private UserRepository userRepository; 
	
	// http://localhost:8000/blog/dummy/join
	// http의 body에 username, password, email 데이터를 가지고 요청을 하게 되면
	// 세 값이 join함수의 파라미터에 쏙 들어간다.
	@PostMapping("/dummy/join")
	public String join(User user) {	// key = value, 약속된 규칙
		System.out.println("id : " + user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료";
	}

}
