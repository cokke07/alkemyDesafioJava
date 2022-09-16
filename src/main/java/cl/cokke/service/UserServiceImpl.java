package cl.cokke.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.cokke.exception.RestServiceException;
import cl.cokke.model.User;
import cl.cokke.repository.UserRepository;
import cl.cokke.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	//Inyeccion de nuevas utilidades de JWT para manejar autenticacion
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	public UserServiceImpl(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}

	@Override
	@Transactional
	public void update(User User) {
		// Idem anterior, pero en este caso actualiza con metodo interno de JPARepository
		userRepository.save(User);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		// Recibe lista de User
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		// Retorna un Optional<User> y luego se convierte en un User
		// Optional es útil para manejar opcionalidades, de esta forma, se evitan
		// valores nulos (NullPointerException desaparecen)
		return userRepository.findById(id).orElse(new User());
	}

	@Override
	@Transactional
	public void delete(User User) {
		// Se llama metodo delete y se entrega un User, primero transformandolo de
		// User a User con metodo toEtity
		userRepository.delete(User);
	}

	@Override
	@Transactional(readOnly = true)
	public String signIn(String username, String password) {
		try {
			//Validar datos de inicio de sesion
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println(username);
			System.out.println(password);
			//Retorna token si los datos son correctos
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		} catch (AuthenticationException e) {
			//Excepcion en caso de datos erroneos
			throw new RestServiceException("username o password invalido", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	@Transactional
	public String signUp(User User) {
		//Valida si el nombre de usuario no exista
		if (!userRepository.existsByUsername(User.getUsername())) {
			//Se encripta contraseña  
			User.setPassword(passwordEncoder.encode(User.getPassword()));
			//Se almacena el usuario
			userRepository.save(User);
			//Retrona token valido para este usuario
			return jwtTokenProvider.createToken(User.getUsername(), User.getRoles());
		} else {
			//En caso de que nombre de usuario exista se retonra excepcion
			throw new RestServiceException("Username ya esta en uso", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		//Se busca usuario por su username
		final User user = userRepository.findByUsername(username);
		//Se evalua si usuario existe
		if (user == null) {
			//Si no existe se retorna excepcion de "Usuario no encontrado"
			throw new UsernameNotFoundException("Usuario '" + username + "' no encontrado");
		}
		//Si existe, se retorna un objeto de tipo UserDetails, validando contraseña y su respectivo Rol.
		return org.springframework.security.core.userdetails.User
				.withUsername(username)
				.password(user.getPassword())
				.authorities(user.getRoles())
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}
}
