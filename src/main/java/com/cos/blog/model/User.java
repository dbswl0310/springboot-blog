package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity // User 클래스 MySQL에 테이블을 생성시켜주는 어노테이션
public class User {
	
	@Id //PK라는 걸 알려주기 위해서
	@GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링 전략 : 해당 프로젝트에 연결된 DB의 넘버링 전략을 따라감
	private int id; //long타입으로 해도 되지만, 사용자가 많지 않을거기 때문에 int
	
	@Column(nullable = false, length = 30)//Username이 널값이면 안된다는 어노테이션
	private String username; // <-id, /시퀀스,auto_increment
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'")
	private String role; //Enum을 쓰는 게 좋다 -> 도메인을 만들어줌.//admin, user, manager 셋 중 하나만 들어갈 수 있다(오타x)
	
	//회원정보 수정하는 updateDate도 필요하긴 한데 우선은 create만
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate; 
	
	

}
