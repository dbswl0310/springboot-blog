package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

//import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html파일이 아니라 data를 리턴해주는 controller이다. = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired	// 의존성 주입(DI) -> 메모리에 뜸!
	// @Autowired의 의미 -> DummyControllerTest가 메모리에 띄워질 때, UserRepository도 메모리에 띄워진다.
	private UserRepository userRepository; 
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
		@GetMapping("/dummy/user")
		public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
			Page<User> pagingUser = userRepository.findAll(pageable);

			List<User> users = pagingUser.getContent();
			return pagingUser;
		}
	
	// {id} 주소로 파라미터를 전달 받을 수 있다.
	// httpL
	@GetMapping("/dummy/user/{id}")	// -> http://localhost:8000/blog/dummy/user/3
	// 리턴값이 User객체를 return받을 것이다.
	public User detail(@PathVariable int id) {
		// findById(id)의 리턴값은 Optional 값이다.
		// user/4를 요청할 경우 서버가 DB에서 못찾아오게되면 user가 return이 된다.
		// 그럼 return이 null값이 된다. 그럼 프로그램에 문제가 생김
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해달라.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		// 요청 :  웹브라우저
		// user객체는 자바객체이다.
		// 웹 브라우저는 자바 객체를 이해하지 못한다.
		// 변환 : (웹브라우저가 이해할 수 있는 데이터) => JSON
		// 스프링부트는 MessageConverter라는 애가 응답시에 자동으로 작동한다.
		// 만약에 자바 오브젝트를 리턴을 하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 JSON으로 변환하여 브라우저에게 전달한다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join
	// http의 body에 username, password, email 데이터를 가지고 요청을 하게 되면
	// 세 값이 join함수의 파라미터에 쏙 들어간다.
	@PostMapping("/dummy/join")
	public String join(User user) {	// key = value, 약속된 규칙
		System.out.println("id : " + user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료";
	}

}
