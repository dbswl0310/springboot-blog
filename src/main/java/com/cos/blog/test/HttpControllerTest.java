package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청을 하면 html파일로 응답을 해주는 역할
// @Controller

// 사용자가 요청을 하면 데이터 응답을 해주는 역할
@RestController
public class HttpControllerTest {
	
	// String -> 리턴타입이 String이라는 의미 -> 반환되어야 할 데이터가 문자열이라는 뜻이다.
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get요청: "  +id + " ," +username ;
	}
	
	@PostMapping("/http/post")
	public String postTest() {
		return "post요청";
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put요청";
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}

}
