package cl.cokke.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import cl.cokke.model.User;

public interface UserService {
	
	List<User> findAll();
	String login(String username, String password);
	User crearUser(User User);
	UserDetails loadUserByUsername(String username);
}
