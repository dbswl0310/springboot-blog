package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//@Controller가 붙은 파일을 리턴할 때 기본 경로 : src/main/resources/static <-이라고 하니까 못 찾음 +/home/html을 추가해줘야함.
		//리턴명: /home.html
		//풀경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.png";
	}
	
	
	@GetMapping("temp/jsp")
	public String tempJsp() {
		
		//prefix : /WEB-INF/views/
		//postfix : .jsp
		//풀경로 : /WEB-INF/views/test.jsp 
		
		return "test";
	}

}
