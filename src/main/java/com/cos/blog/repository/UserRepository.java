package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// 스프링에 메모리에 띄원준다.
public interface UserRepository extends JpaRepository<User, Integer>{

}
