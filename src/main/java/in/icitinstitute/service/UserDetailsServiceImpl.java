package in.icitinstitute.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private Map<String, String> userStore;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (userStore.containsKey(username)) {
			return new User(username, userStore.get(username), getAuthorities());
		}else {
			throw new UsernameNotFoundException("User Not found");
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths=new ArrayList<>();
		auths.add(new SimpleGrantedAuthority("Admin"));
		auths.add(new SimpleGrantedAuthority("User"));
		return auths;
	}

}
