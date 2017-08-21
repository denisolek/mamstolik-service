package pl.denisolek.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.User.User;
import pl.denisolek.User.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String lowercaseLogin = username.toLowerCase();

		User userFromDatabase;

		userFromDatabase = getUserRepository().findByEmail(lowercaseLogin);

		if (userFromDatabase == null) {
			throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		for (Authority authority : userFromDatabase.getAuthorities()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
			grantedAuthorities.add(grantedAuthority);
		}

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getEmail(), userFromDatabase.getPassword(), grantedAuthorities);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}
}
