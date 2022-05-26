package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController	// 데이터만 리턴해준다.
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;  // session객체는 스프링컨테이너가 빈으로 등록을해서 갖고 있다.
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, email, password만 받는데?? -> 
		System.out.println("UserApplication :  save 호출됨");
		
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
		user.setRole(RoleType.USER);	// save함수에서 파라미터가 username, email, password만 받으니까 Role 파라미터를 set메서드로 넣어줌
		userService.회원가입(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user) {
		System.out.println("UserApplication :  login 호출됨");
		User principal = userService.로그인(user);	 	// principal(접근주체)
		
		if(principal != null) {
			session.setAttribute("principal", principal);  //principal를 key값으로 하고, 37줄을 담아버린다.
			System.out.println(session);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
}
