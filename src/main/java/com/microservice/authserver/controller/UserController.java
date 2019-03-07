package com.microservice.authserver.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.microservice.authserver.entity.Role;
import com.microservice.authserver.entity.User;
import com.microservice.authserver.repository.UserRepository;
import com.microservice.authserver.service.RoleService;
import com.microservice.authserver.service.UserService;

@Controller
@RequestMapping(value = "users/")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleService roleService;	

	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("/users/home");

		return mv;
	}

//	@RequestMapping(value = "logout", method = RequestMethod.GET)
//	public ModelAndView logout() {
//		System.out.println("in the logout");
//		ModelAndView mv = new ModelAndView();
////		mv.setViewName("users/logout-success");
//		return mv;
//	}

	@RequestMapping(value = "homeUser", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		List<User> al = userService.getAllUsers();
		mv.addObject("UserList", al);

		populateUserDetails(userDetails,mv);

		mv.setViewName("users/homeUser");

		return mv;
	}
	 @PreAuthorize("hasAuthority('CREATE_USER')")
	//@PreAuthorize("hasAnyRole('ROLE_VIEW_USER')")
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public ModelAndView add(@AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		List<Role> roles = roleService.getAllRoles();
		mv.addObject("RoleList", roles);

		populateUserDetails(userDetails,mv);

		mv.setViewName("users/addUser");

		return mv;
	}

	 
	 @PreAuthorize("hasAuthority('CREATE_USER')")
	//@PreAuthorize("hasAnyRole('ROLE_VIEW_USER')")
	@RequestMapping(value = "addUser",method = RequestMethod.POST)
	public ModelAndView add(User user, @AuthenticationPrincipal final UserDetails userDetails, @RequestParam("RoleId") Long roleId) throws URISyntaxException {		
		
		ModelAndView mv = new ModelAndView();			
		
		User userFromDB = null;;
		
		Long id = user.getId();
		if(id != null)
			userFromDB = userService.getUserById(id);
		
		if(userFromDB != null) {
			if(userRepository.findByUserRoleId(id,roleId) == null){
				System.out.println(userRepository.findByUserRoleId(id,roleId));
				user.setRoles(addNewRoleToRoles(userFromDB, roleId));
					
				}
			user.setRoles(addNewRoleToRoles(userFromDB, (long) 0));
			user.setEnabled(true);
			User updatedUser = userService.editUser(user);			
			
			populateForm(updatedUser,mv);						

			populateUserDetails(userDetails,mv);
			
			mv.setViewName("users/addUser");
			
			return mv;	
		}
				
		Role newRole = new Role();
		newRole.setId(roleId);
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(newRole);
		user.setRoles(roleList);
		user.setId((long) 0);
		user.setEnabled(true);
		User newUser = userService.addUser(user);
		
		populateForm(newUser,mv);		
		
		populateUserDetails(userDetails,mv);
		
		mv.setViewName("users/addUser");			

		return mv;		
	}
	

	private void populateForm(User updatedUser, ModelAndView mv) {
		
		List<Role> roles = roleService.getAllRoles();
		mv.addObject("RoleList",roles);
		mv.addObject("UserRoleList", updatedUser.getRoles());
		mv.addObject("User", updatedUser);
		
	}

	private List<Role> addNewRoleToRoles(User userFromDB, Long roleId) {
		
		List<Role> userRoles = userFromDB.getRoles();
		
		Role roleToAdd = new Role();
		roleToAdd.setId(roleId);
		if(roleId != (long) 0)
			userRoles.add(roleToAdd);
		return userRoles;
		
	}

	@RequestMapping(value = "RemoveRoleFromAddForm", method = RequestMethod.GET)
	public ModelAndView RemoveRoleFromAddForm(@RequestParam("roleId") Long roleId, @RequestParam("userId") Long userId,
			@AuthenticationPrincipal final UserDetails userDetails) throws URISyntaxException {

		ModelAndView mv = new ModelAndView();		
		
		User updatedUser = removeRoleFromUser(roleId, userId);
		
		populateForm(updatedUser,mv);
		
		populateUserDetails(userDetails,mv);

		mv.setViewName("users/addUser");
		return mv;
	}

	private User removeRoleFromUser(Long roleId, Long userId) throws URISyntaxException {
		
		Role role = roleService.getRoleById(roleId);
		User userFromDB = userService.getUserById(userId);

		List<Role> roles = userFromDB.getRoles();
		roles.remove(role);
		userFromDB.setRoles(roles);
		return userService.editUser(userFromDB);
	}

	@PreAuthorize("hasAuthority('VIEW_USER')")
	@RequestMapping("viewUser")
	public ModelAndView view(Long id, @AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		User user = userService.getUserById(id);
		List<Role> roleList = user.getRoles();
		mv.addObject("RoleList", roleList);
		mv.addObject("User", user);

		populateUserDetails(userDetails,mv);

		mv.setViewName("users/viewUser");

		return mv;
	}

	@PreAuthorize("hasAuthority('EDIT_USER')")
	@RequestMapping(value = "editUser", method = RequestMethod.POST)
	public RedirectView edit(User a, @AuthenticationPrincipal final UserDetails userDetails) throws URISyntaxException {

		ModelAndView mv = new ModelAndView();

		User ar = userService.editUser(a);
		populateUserDetails(userDetails,mv);

		mv.setViewName("users/homeUser");

		return new RedirectView("users/homeUser");
	}
	
	@PreAuthorize("hasAuthority('EDIT_USER')")
	@RequestMapping("editUser")
	public ModelAndView edit(Long id, @AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		User user = userService.getUserById(id);
		List<Role> userRoles = user.getRoles();
		List<Role> allRoles = roleService.getAllRoles();

		mv.addObject("User", user);
		mv.addObject("UserRoleList", userRoles);
		mv.addObject("RoleList", allRoles);

		populateUserDetails(userDetails,mv);

		mv.setViewName("users/addUser");

		return mv;

	}

	@PreAuthorize("hasAuthority('DELETE_USER')")
	@RequestMapping("deleteUser")
	public ModelAndView delete(Long id, @AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		if (id == null) {

			List<User> al = userService.getAllUsers();
			mv.addObject("UserList", al);
			mv.setViewName("users/homeUser");
			return mv;
		}
		User a = userService.getUserById(id);
		mv.addObject("User", a);

		populateUserDetails(userDetails,mv);

		mv.setViewName("users/deleteUser");

		return mv;
	}

	@PreAuthorize("hasAuthority('DELETE_USER')")
	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	public RedirectView delete(User user, @AuthenticationPrincipal final UserDetails userDetails) {

		ModelAndView mv = new ModelAndView();

		Long id = user.getId();
		userService.deleteUser(id);

		populateUserDetails(userDetails,mv);

		mv.setViewName("homeUser");

		return new RedirectView("homeUser");
	}
	
	
private void populateUserDetails(UserDetails userDetails, ModelAndView mv) {
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);
		
	}
}