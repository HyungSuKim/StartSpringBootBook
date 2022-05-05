package org.zerock.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.persistence.MemberRepository;

import lombok.extern.java.Log;

@Service
@Log
public class ZerockUsersService implements UserDetailsService {
	
	@Autowired
	MemberRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*
		 * User sampleUser = new User(username, "{noop}1111", Arrays.asList(new
		 * SimpleGrantedAuthority("ROLE_Manager")));
		 */
		
		/* repo.findById(username).ifPresent(member -> log.info("" + member)); */
		
		return repo.findById(username)
				.filter(m -> m != null)
				.map(m -> new ZerockSecurityUser(m)).get();
	}
}
