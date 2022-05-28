package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	// 컨트롤러에서 세션을 어떻게 찾는지?
	@Autowired
	private BoardService boardService;
	
	// 사용자가 "/"에 요청을하면
	// model에  boardService.글목록()들을 전부 다 들고간다.
	// 글 목록은 findAll이라고 하면 다 가져올 수 있다. -> boardRepository가 findAll이라는 함수를 들고 있기 때문에
	// 
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable)  {
		// "글목록" 을 boards에 담아서 model에 보내준다.
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index";	//WEB-INF/views/index.jsp
	}
	
	// USER권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "/board/saveForm";
	}
}
