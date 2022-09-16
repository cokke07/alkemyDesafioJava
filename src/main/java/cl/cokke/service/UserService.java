package cl.cokke.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import cl.cokke.model.User;

public interface UserService {
	
	void update(User user);
	List<User> findAll();
	User findById(Long id);
	void delete(User user);
	String signIn(String username, String password);
	String signUp(User User);
	UserDetails loadUserByUsername(String username);
}
