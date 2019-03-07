package com.microservice.authserver.controller;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.microservice.authserver.entity.Privilege;
import com.microservice.authserver.entity.Role;
import com.microservice.authserver.service.RoleService;

@Controller
@RequestMapping(value = "roles/")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "homeRole",method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();
		
		List<Role> al = roleService.getAllRoles();
		mv.addObject("RoleList", al);	
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("roles/homeRole");	

		return mv;		
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@RequestMapping(value = "addRole",method = RequestMethod.GET)
		public ModelAndView add(@AuthenticationPrincipal final UserDetails userDetails) {		
			
			ModelAndView mv = new ModelAndView();				
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("roles/addRole");	

			return mv;		
		}
		
		
		//@PreAuthorize("hasAnyRole('ROLE_TestAdmin')")
		@RequestMapping(value = "addRole",method = RequestMethod.POST)
		public RedirectView add(Role a, @AuthenticationPrincipal final UserDetails userDetails) throws URISyntaxException {		
			
			ModelAndView mv = new ModelAndView();			
			
			Role ar = roleService.addRole(a);	
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("roles/homeRole");			

			return new RedirectView("roles/homeRole");		
		}
		
		
		@RequestMapping("viewRole")
		public ModelAndView view(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
			
			ModelAndView mv = new ModelAndView();		
			
			Role role = roleService.getRoleById(id);
			List<Privilege> privilegeList = role.getPrivileges();
			mv.addObject("PrivilegeList",privilegeList);
			mv.addObject("Role",role);
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("roles/viewRole");	

			return mv;
			
		}
		
		//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@RequestMapping(value = "editRole",method = RequestMethod.POST)
		public RedirectView edit(Role a, @AuthenticationPrincipal final UserDetails userDetails) throws URISyntaxException {	
			
			ModelAndView mv = new ModelAndView();
			
			Role ar = roleService.editRole(a);
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("roles/homeRole");
			
			return new RedirectView("roles/homeRole");
			
		}
		
		@RequestMapping("editRole")
		public ModelAndView edit(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
			
			ModelAndView mv = new ModelAndView();
			
			Role role = roleService.getRoleById(id);
			mv.addObject("Role",role);
			List<Privilege> privilegeList = role.getPrivileges();
			mv.addObject("PrivilegeList",privilegeList);
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("roles/editRole");	

			return mv;
			
		}
		
		
		@RequestMapping("deleteRole")
		public ModelAndView delete(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
			
			ModelAndView mv = new ModelAndView();
			
			Role role = roleService.getRoleById(id);
			mv.addObject("Role",role);
			List<Privilege> privilegeList = role.getPrivileges();
			mv.addObject("PrivilegeList",privilegeList);
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			mv.setViewName("roles/deleteRole");	

			return mv;		
		}
		
		
		//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@RequestMapping(value = "deleteRole",method = RequestMethod.POST)
		public RedirectView delete(Role a, @AuthenticationPrincipal final UserDetails userDetails) {		
			
			ModelAndView mv = new ModelAndView();
			
			Long id= a.getId();
			roleService.deleteRole(id);	
			
			String userName = userDetails.getUsername();
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();		
			mv.addObject("AuthList",authList);
			mv.addObject("Name", userName);		
			
			mv.setViewName("homeRole");	

			return new RedirectView("roles/homeRole");		
		}
}
