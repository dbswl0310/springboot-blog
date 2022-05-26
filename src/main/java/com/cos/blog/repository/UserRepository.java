package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// 스프링에 메모리에 띄워준다.
public interface UserRepository extends JpaRepository<User, Integer>{
	// JPA Naming 쿼리
	// SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?
	// findbyUsernameAndPassword의 이름을 가진 함수가 존재하지 않아도
	//  JPA가 자동으로 상단의 쿼리문을 실행시켜준다.
	User findByUsernameAndPassword(String username, String password);
	
	// 이 방법으로도 사용이 가능하다.
//	@Query(value = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?", nativeQuery = true)
//	User login(String username, String password);
}
