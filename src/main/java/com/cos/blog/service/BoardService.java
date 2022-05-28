package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service	// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(IoC)
public class BoardService {
	
	@Autowired // DI
	private BoardRepository boardRepository;
	

	@Transactional
	public void 글쓰기(Board board, User user) {	// title, content
		board.setCount(0);	// 조회 수 넣어주기
		board.setUser(user);
		boardRepository.save(board);
	
	}
	
}
