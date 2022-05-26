package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service	// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(IoC)
public class UserService {
	
	@Autowired // DI
	private UserRepository userRepository;
	
	// 서비스 -> 여러 개의 트랜잭션을 하나로 묶어서 서비스화 시킬 수 있다.(여러개가 아니라도 서비스~!)
	// 여러개의 트랜잭션이 모두 성공해야 커밋이 되고,
	// 하나라도 실패하면 롤백이 된다.
//	@Transactional
//	public Integer 회원가입(User user) {
//		try {
//			userRepository.save(user);
//			return 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("UserService : 회원가입() : " + e.getMessage());
//		}
//		return -1;
//	}
	
	@Transactional
	public void 회원가입(User user) {
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true) //Select 할 때 트랜잭션 시장, 서비스 종료시에 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		// 여러번의 트랜잭션이 일어나도 데이터 정합성이 유지된다.
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
}
