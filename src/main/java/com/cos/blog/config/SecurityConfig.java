package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것.(IoC관리)
@Configuration
@EnableWebSecurity // 시큐리티 필터 등록.
@EnableMethodSecurity(prePostEnabled = true)	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean //IoC가 됨.
	public BCryptPasswordEncoder encodePWD() {
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()	// csrf 토큰 비활성화 (테스트시 걸어두는 것이 좋다.)
			.authorizeRequests()
				.antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**") 	// "/auth**"의 경로로 들어오는 건, auth경로가 아닌건 인증이 필요하다.
				.permitAll()								// 누구나 들어올 수 있다.
				.anyRequest()							// 외에 다른 요청은
				.authenticated()						// 인증을 필요로 한다.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}
}
