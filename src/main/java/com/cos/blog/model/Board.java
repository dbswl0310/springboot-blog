package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

public class Board {
	
	@Id//pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링 전략=auto-increment
	private int id;
	
	@Column(nullable = false , length = 100)
	private String title;
	
	@Lob //대용량 데이터일 때 사용
	private String content; //썸머노트 라이브러리 사용 -> 우리가 적는 글이 디자인이 섞여서 들어감
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne //Many=board, user=one -> 여러개의 게시글은 한명에 유저에 의해서 쓸 수 있다.
	@JoinColumn(name="userId")
													//요약 : 필드값은 userId러 만들어지고 연관관계는 ManyToOne이다.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK,자바는 오브젝트를 저장할 수 있다.
	
	
	@CreationTimestamp
	private Timestamp createDate;

}
