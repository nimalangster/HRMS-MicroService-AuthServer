package com.microservice.authserver.controller;

import java.net.URISyntaxException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.microservice.authserver.entity.Privilege;
import com.microservice.authserver.entity.Role;
import com.microservice.authserver.service.PrivilegeService;


@Controller
@RequestMapping(value = "privileges/")
public class PrivilegeController {
	@Autowired
	PrivilegeService privilegeService;
	
	@RequestMapping(value = "homePrivilege",method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();
		
		List<Privilege> al = privilegeService.getAllPrivileges();
		mv.addObject("PrivilegeList",al);
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("privileges/homePrivilege");	

		return mv;		
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "addPrivilege",method = RequestMethod.GET)
	public ModelAndView add(@AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();	
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("privileges/addPrivilege");	

		return mv;
		
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "addPrivilege",method = RequestMethod.POST)
	public RedirectView add(Privilege a, @AuthenticationPrincipal final UserDetails userDetails) throws URISyntaxException {		
		
		ModelAndView mv = new ModelAndView();	
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		privilegeService.addPrivilege(a);		
		
		mv.setViewName("homePrivilege");			

		return new RedirectView("privileges/homePrivilege");
		
	}
	
	@RequestMapping("viewPrivilege")
	public ModelAndView view(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();		
		
		Privilege privilege = privilegeService.getPrivilegeById(id);
		mv.addObject("Privilege",privilege);
		
		List<Role> roles = privilege.getRoles();
		mv.addObject("RoleList",roles);
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("privileges/viewPrivilege");	

		return mv;
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "editPrivilege",method = RequestMethod.POST)
	public RedirectView edit(Privilege a) throws URISyntaxException {	
		
		ModelAndView mv = new ModelAndView();		
		
		privilegeService.editPrivilege(a);
		mv.setViewName("home");		
		return new RedirectView("privileges/homePrivilege");
		
	}
	
	@RequestMapping("editPrivilege")
	public ModelAndView edit(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();
		
		Privilege privilege = privilegeService.getPrivilegeById(id);
		mv.addObject("Privilege",privilege);
		List<Role> roles = privilege.getRoles();
		mv.addObject("RoleList",roles);
		mv.addObject("Privilege",privilege);
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("privileges/editPrivilege");	

		return mv;
		
	}
	
	@RequestMapping("deletePrivilege")
	public ModelAndView delete(Long id, @AuthenticationPrincipal final UserDetails userDetails) {		
		
		ModelAndView mv = new ModelAndView();
		
		Privilege privilege = privilegeService.getPrivilegeById(id);
		mv.addObject("Privilege",privilege);
		List<Role> roles = privilege.getRoles();
		mv.addObject("RoleList",roles);
		mv.addObject("Privilege",privilege);
		
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
		mv.addObject("AuthList",authList);
		mv.addObject("Name", userName);		
		
		mv.setViewName("privileges/deletePrivilege");	

		return mv;
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "deletePrivilege",method = RequestMethod.POST)
	public RedirectView delete(Privilege privilege) {		
		
		ModelAndView mv = new ModelAndView();
		
		Long id= privilege.getId();		
		privilegeService.deletePrivilege(id);			
		
		mv.setViewName("homePrivilege");	

		return new RedirectView("privileges/homePrivilege");
		
	}

}
