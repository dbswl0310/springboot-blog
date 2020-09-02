package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

//자바에서 변수는 다 private로 만든다.
	//-> 변수에 직접적으로 접근하여 상태를 바꾸는 건 마법
	//private으로 변수를 만들고 함수를 publlic으로 만들어서
	//함수를 통해서 	상태값을 바꾸고( 직접적으로 변수로 상태변화가 아닌)
	//정리 : 변수는 private로 만들고 수정할 수 있게 메소드를 public으로 만든다.
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		
	}
}
	
	
	
	
	

