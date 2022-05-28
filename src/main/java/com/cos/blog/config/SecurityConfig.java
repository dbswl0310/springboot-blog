package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것.(IoC관리)
@Configuration
@EnableWebSecurity // 시큐리티 필터 등록.
@EnableMethodSecurity(prePostEnabled = true)	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC가 됨.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인하여 password를 가로채기 하는데
	// 해당 password가 무엇으로 해쉬가 돼서 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()	// csrf 토큰 비활성화 (테스트시 걸어두는 것이 좋다.)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") 	// "/auth**"의 경로로 들어오는 건, auth경로가 아닌건 인증이 필요하다.
				.permitAll()								// 누구나 들어올 수 있다.
				.anyRequest()							// 외에 다른 요청은
				.authenticated()						// 인증을 필요로 한다.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")	// 인증이 필요한 요청은 해당 url로 온다.
				.loginProcessingUrl("/auth/loginProc")	// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다. ->  그래서 loginProc컨틀롤러 안만들어도됨
				.defaultSuccessUrl("/");		// 정상적으로 시큐리티가 로그인까지 가로챈 로그인이 실행되었을 때 실행된다.
	}
}
