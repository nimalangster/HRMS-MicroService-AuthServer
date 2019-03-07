package com.microservice.authserver.serviceimpl;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.authserver.entity.User;
import com.microservice.authserver.exceptionhandler.ResourceAlreadyExistsException;
import com.microservice.authserver.exceptionhandler.ResourceNotFoundException;
import com.microservice.authserver.repository.UserRepository;
import com.microservice.authserver.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
		
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User addUser(User newUser) throws URISyntaxException {
		
		Long id = newUser.getId();
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
				throw new ResourceAlreadyExistsException(id,User.class.getSimpleName());
		}
		User addedUser = userRepository.save(newUser);		
        
        return addedUser;		   
	}
	

	@Override
	public List<User> getAllUsers() {
		
        List<User> users = userRepository.findAll();                  
       
        return users;     
    }
	
	
	@Override
	public User getUserById(Long id) {
        
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,User.class.getSimpleName()));	
		
        return user;        
    }


	@Override
	public String deleteUser(Long id) {	
		
		userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id,User.class.getSimpleName()));
		userRepository.deleteById(id);	
		return "The record with id "+ id +" is successfully deleted!";		
	}		

	
	@Override
	public User editUser(User newUser) throws URISyntaxException {

		userRepository.findById(newUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException(newUser.getId(),User.class.getSimpleName()));		
		
		User userFromDB = userRepository.getOne(newUser.getId());
		newUser.setPassword(userFromDB.getPassword());
		
		User updatedUser = userRepository.save(newUser);
		return updatedUser;
		
	}


	@Override	
	public List<User> searchByName(String name, String nic){
		
		List<User> users = userRepository.findByUserNameOrNic(name,nic);
		
		return users;
	}

	

}
