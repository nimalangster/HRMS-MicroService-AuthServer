package com.microservice.authserver.serviceimpl;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.authserver.entity.Privilege;
import com.microservice.authserver.exceptionhandler.ResourceAlreadyExistsException;
import com.microservice.authserver.exceptionhandler.ResourceNotFoundException;
import com.microservice.authserver.repository.PrivilegeRepository;
import com.microservice.authserver.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	@Override
	public Privilege addPrivilege(Privilege newPrivilege) throws URISyntaxException {
		
		Long id = newPrivilege.getId();
		
		Optional<Privilege> privilege = privilegeRepository.findById(id);
		if(privilege.isPresent()) {
				throw new ResourceAlreadyExistsException(id,Privilege.class.getSimpleName());
		}
		Privilege addedPrivilege = privilegeRepository.save(newPrivilege);		
        
        return addedPrivilege;		   
	}
	

	@Override
	public List<Privilege> getAllPrivileges() {
		
        List<Privilege> privileges = privilegeRepository.findAll();                  
       
        return privileges;     
    }
	
	
	@Override
	public Privilege getPrivilegeById(Long id) {
        
		Privilege privilege = privilegeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,Privilege.class.getSimpleName()));

        return privilege;        
    }


	@Override
	public String deletePrivilege(Long id) {	
		
		privilegeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,Privilege.class.getSimpleName()));
		privilegeRepository.deleteById(id);	
		return "The record with id "+ id +" is successfully deleted!";		
	}		

	
	@Override
	public Privilege editPrivilege(Privilege newPrivilege) throws URISyntaxException {

		privilegeRepository.findById(newPrivilege.getId())
				.orElseThrow(() -> new ResourceNotFoundException(newPrivilege.getId(),Privilege.class.getSimpleName()));		
		
		Privilege updatedPrivilege = privilegeRepository.save(newPrivilege);
		return updatedPrivilege;
		
	}


	@Override	
	public Privilege searchByName(String name){
		
		Privilege privileges = privilegeRepository.findByName(name);
		
		return privileges;
	}

	

}
