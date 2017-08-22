package pl.denisolek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
public class MamstolikApplication {

	@Autowired
	private UserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(MamstolikApplication.class, args);
	}

	// TODO: 28.06.2017 enable password hashing after register
	// http://www.devglan.com/spring-security/spring-boot-security-password-encoding-bcrypt-encoder

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService);
		//		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}
}
