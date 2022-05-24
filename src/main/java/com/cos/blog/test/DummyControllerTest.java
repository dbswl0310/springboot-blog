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
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		userRepository.deleteById(id);
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e){
			return "삭제에 실패하였습니다. 해당 아이디는 DB에 존재하지 않습니다.";
		}
		return "삭제되었습니다. id : " + id;
	}
	
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하여 해당 데이터가 존재하면 update를 해주고
	// save함수는 id를 전달하여 해당 데이터가 존재하지 않는 경우 insert를 한다. (없는 값은 null값으로 한다.)
	@Transactional // 함수 종료시에 자동 commit이 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.err.println("id : " + id);
		System.err.println("password : " + requestUser.getPassword());
		System.err.println("email : " + requestUser.getEmail());
		
		// id로 user를 찾는다.
		// id가 없으면 Exception을 표시한다.
		User user = userRepository.findById(id).orElseThrow(() ->{
			return new IllegalArgumentException("수정에 실패하였습니다. ");
		});
		
		// set메서드를 통해 값이 변경되면 @@Transactional이 더티체킹 하여
		// 트랜잭션이 종료될 때 변경을 감지한다. 
		// 그 다음 데이터베이스에 수정(update)를 날려준다.
		// 그래서 굳이 userRepository.save(user); 를 안해줘도 된다.
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		// 더티 체킹 -> @Transactional 어노테이션을 붙이면!
		
		return user;
		
	}
	
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
