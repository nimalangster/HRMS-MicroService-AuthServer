package com.microservice.authserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservice.authserver.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	//@Query("SELECT user FROM User as user WHERE user.fullName=?1")
	public List<User> findByUserNameOrNic(String name, String nic);
	public User findByUserName(String name);
	
	@Query("SELECT user,role FROM User AS user JOIN user.roles AS role WHERE role.id =?2 AND user.id=?1")
	public User findByUserRoleId(Long userId, Long roleId);

	//SELECT a, b FROM Author a JOIN a.books b	

}
