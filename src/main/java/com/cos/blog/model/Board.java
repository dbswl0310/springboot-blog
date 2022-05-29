package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //빌더 패턴
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 취급할 때 사용한다..
	private String content;
	
	private int count;
	
	// 데이터베이스는 오브젝트를 저장할 수 없다. -> fk키 사용
	// 자바는 오브젝트를 저장할 수 있다. ->
	@ManyToOne(fetch = FetchType.EAGER) // board가 one, many가 user라는 의미(연관관계 설정)
	@JoinColumn(name="useId") //필드는 userId로 만들어진다.
	private User user;
	
	// 하나의 게시글은 여러개의 댓글을 가질 수 있다.
	@OneToMany (mappedBy = "board", fetch = FetchType.LAZY) // mappedBy, 연관관계의 주인이 아니다.(FK가 )
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;

}
