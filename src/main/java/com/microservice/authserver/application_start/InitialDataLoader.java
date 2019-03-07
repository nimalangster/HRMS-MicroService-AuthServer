package com.microservice.authserver.application_start;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.microservice.authserver.entity.Privilege;
import com.microservice.authserver.entity.Role;
import com.microservice.authserver.entity.User;
import com.microservice.authserver.repository.PrivilegeRepository;
import com.microservice.authserver.repository.RoleRepository;
import com.microservice.authserver.repository.UserRepository;

@Component
public class InitialDataLoader {

	boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@PostConstruct
	@Transactional
	public void loadData() {

		if (alreadySetup)
			return;
		
		Privilege p1 = createPrivilegeIfNotFound("CREATE_ROLE");
		Privilege p2 = createPrivilegeIfNotFound("EDIT_ROLE");
		Privilege p3 = createPrivilegeIfNotFound("VIEW_ROLE");		
		Privilege p4 = createPrivilegeIfNotFound("DELETE_ROLE");
		
		Privilege p5 = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
		Privilege p6 = createPrivilegeIfNotFound("EDIT_PRIVILEGE");
		Privilege p7 = createPrivilegeIfNotFound("VIEW_PRIVILEGE");
		Privilege p8 = createPrivilegeIfNotFound("DELETE_PRIVILEGE");		
		
		Privilege p9 = createPrivilegeIfNotFound("CREATE_USER");		
		Privilege p10 = createPrivilegeIfNotFound("EDIT_USER");
		Privilege p11 = createPrivilegeIfNotFound("VIEW_USER");
		Privilege p12 = createPrivilegeIfNotFound("DELETE_USER");
		
		Privilege p13 = createPrivilegeIfNotFound("ASSIGN_PRIVILEGE_TO_ROLE");
		Privilege p14 = createPrivilegeIfNotFound("VIEW_PRIVILEGE_BY_ROLE");
		Privilege p15 = createPrivilegeIfNotFound("ASSIGN_USER_TO_ROLE");
		Privilege p16 = createPrivilegeIfNotFound("VIEW_USER_BY_ROLE");	


		List<Privilege> adminPrivileges = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16);
		List<Privilege> userPrivileges = Arrays.asList(p3,p7,p11,p14,p16);

		createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_USER", userPrivileges);

		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");

		createTestUserIfNotFound(adminRole);
		createTestUserIfNotFound(userRole);

		alreadySetup = true;

	}

	private void createTestUserIfNotFound(Role role) {
	
		if ((userRepository.findByUserName("TestAdmin") == null)&(role.getName().equals("ROLE_ADMIN"))) {
			//System.out.println("creating admin user!");
			User user = new User();
			user.setUserName("TestAdmin");
			user.setFirstName("TestAdmin");
			user.setLastName("TestAdmin");
			user.setPassword(passwordEncoder.encode("a"));
			user.setEmail("testadmin@test.com");
			user.setRoles(Arrays.asList(role));
			user.setEnabled(true);
			userRepository.save(user);
		}
		
		if ((userRepository.findByUserName("TestUser") == null)&(role.getName().equals("ROLE_USER"))) {
			//System.out.println("creating test user!");
			User user = new User();
			user.setUserName("TestUser");
			user.setFirstName("TestUser");
			user.setLastName("TestUser");
			user.setPassword(passwordEncoder.encode("u"));
			user.setEmail("testuser@test.com");
			user.setRoles(Arrays.asList(role));
			user.setEnabled(true);
			userRepository.save(user);
		}

	}

	@Transactional
	private Role createRoleIfNotFound(String name, List<Privilege> privileges) {

		Role roleFromDB = roleRepository.findByName(name);
		if (roleFromDB == null) {
			//System.out.println("creating new role!");
			Role role = new Role();
			
			role.setName(name);
			role.setPrivileges(privileges);			
			roleRepository.save(role);
			return role;
		}
		return roleFromDB;
	}

	private Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilegeFromDB = privilegeRepository.findByName(name);

		if (privilegeFromDB == null) {		
			//System.out.println("creating newn privilege!");
			Privilege privilege = new Privilege();
			
			privilege.setName(name);
			privilegeRepository.save(privilege);
			return privilege;
		}
		return privilegeFromDB;
	}

}
