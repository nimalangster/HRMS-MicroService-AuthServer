package com.microservice.authserver.serviceimpl;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.authserver.entity.Role;
import com.microservice.authserver.exceptionhandler.ResourceAlreadyExistsException;
import com.microservice.authserver.exceptionhandler.ResourceNotFoundException;
import com.microservice.authserver.repository.RoleRepository;
import com.microservice.authserver.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role addRole(Role newRole) throws URISyntaxException {
		
		Long id = newRole.getId();
		
		Optional<Role> role = roleRepository.findById(id);
		if(role.isPresent()) {
				throw new ResourceAlreadyExistsException(id,Role.class.getSimpleName());
		}
		Role addedRole = roleRepository.save(newRole);		
        
        return addedRole;		   
	}
	

	@Override
	public List<Role> getAllRoles() {
		
        List<Role> roles = roleRepository.findAll();                  
       
        return roles;     
    }
	
	
	@Override
	public Role getRoleById(Long id) {
        
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,Role.class.getSimpleName()));

        return role;        
    }


	@Override
	public String deleteRole(Long id) {	
		
		roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,Role.class.getSimpleName()));
		roleRepository.deleteById(id);	
		return "The record with id "+ id +" is successfully deleted!";		
	}		

	
	@Override
	public Role editRole(Role newRole) throws URISyntaxException {

		roleRepository.findById(newRole.getId())
				.orElseThrow(() -> new ResourceNotFoundException(newRole.getId(),Role.class.getSimpleName()));		
		
		Role updatedRole = roleRepository.save(newRole);
		return updatedRole;
		
	}


	@Override	
	public Role searchByName(String name, String nic){
		
		Role roles = roleRepository.findByName(name);
		
		return roles;
	}

	
}
