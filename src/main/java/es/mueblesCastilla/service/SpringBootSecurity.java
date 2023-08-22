package es.mueblesCastilla.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SpringBootSecurity {
	


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	UserDetailServiceImpl userDetailService() {
		return new UserDetailServiceImpl();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {


		 http
         .authorizeHttpRequests((authz) -> authz
             .requestMatchers("/administrador/**").hasRole("ADMIN")
             .requestMatchers("/productos/**").hasRole("USER")
             .anyRequest().permitAll()
         )
         .formLogin(formLogin -> formLogin
                 .loginPage("/usuario/login")
                 .permitAll());
     return http.build();
 }
	

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/vendor/**");

	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		
		UserDetailServiceImpl userDetailService= new UserDetailServiceImpl();

		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder).and().build();
	}

}
