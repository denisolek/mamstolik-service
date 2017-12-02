package pl.denisolek.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Profile("!test")
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable().and()
				.authorizeRequests()
				.antMatchers("/users").authenticated()
				.antMatchers("/api").permitAll()
				.antMatchers(HttpMethod.PUT, "/panel/users/password").hasAuthority("ROLE_OWNER")
				.antMatchers("/panel/restaurants").hasAuthority("ROLE_OWNER")
				.antMatchers("/panel/restaurants/**").permitAll()
				.antMatchers("/panel/employees").hasAuthority("ROLE_RESTAURANT")
				.antMatchers("/panel/users", "/panel/users/**").permitAll()
//                .antMatchers("/panel/*/employees").hasAnyAuthority("ROLE_OWNER", "ROLE_MANAGER")
				.antMatchers("/panel/*/schemas/**").hasAnyAuthority("ROLE_OWNER", "ROLE_MANAGER")
				.antMatchers("/panel/*/employees").hasAuthority("ROLE_OWNER")
				.antMatchers("/panel/**").hasAnyAuthority("ROLE_OWNER", "ROLE_MANAGER", "ROLE_EMPLOYEE")
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
	}
}
