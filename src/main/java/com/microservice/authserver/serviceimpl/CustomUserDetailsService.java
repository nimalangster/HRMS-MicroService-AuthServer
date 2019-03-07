package com.microservice.authserver.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.authserver.entity.Privilege;
import com.microservice.authserver.entity.Role;
import com.microservice.authserver.entity.User;
import com.microservice.authserver.repository.RoleRepository;
import com.microservice.authserver.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);
		
		if (user == null) {
			
			return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
					getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				user.getEnabled(), true, true, true, getAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));

	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (String privilege : privileges) {

			authorities.add(new SimpleGrantedAuthority(privilege));
		}

		return authorities;

	}

	private List<String> getPrivileges(List<Role> roles) {

		List<String> privilegeStringList = new ArrayList<>();

		List<Privilege> privilegeList = new ArrayList<>();

		for (Role role : roles) {
			privilegeList.addAll(role.getPrivileges());
		}

		for (Privilege privilege : privilegeList) {
			privilegeStringList.add(privilege.getName());
		}

		return privilegeStringList;

	}

}
