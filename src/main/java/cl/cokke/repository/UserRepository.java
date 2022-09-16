package cl.cokke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.cokke.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
	
	//Metodos abstractos para busquedas personalizadas
	boolean existsByUsername(String username);
	User findByUsername(String username);
}
