package cl.cokke.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.cokke.dto.PersonajeDTO;
import cl.cokke.mapper.PersonajeMapper;
import cl.cokke.model.Personaje;
import cl.cokke.repository.PersonajeRepository;

@Service
public class PersonajeServiceImp implements PersonajeService {

	@Autowired
	private PersonajeRepository personajeRepository;
	
	@Autowired
	private PersonajeMapper personajeMapper;
	
	@Override
	public List<PersonajeDTO> buscarTodos() {
		// TODO Auto-generated method stub
		List<Personaje> personajes = personajeRepository.findAll();
		return personajeMapper.convertirAListPersonajeDTO(personajes);
	}

	@Override
	public Optional<Personaje> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return personajeRepository.findById(id);
	}

	@Override
	public Personaje guardarPersonaje(Personaje p) {
		// TODO Auto-generated method stub
		return personajeRepository.save(p);
	}

	@Override
	public Personaje editarPersonaje(Personaje p) {
		// TODO Auto-generated method stub
		return personajeRepository.save(p);
	}

	@Override
	public void eliminarPersonaje(Long id) {
		personajeRepository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Personaje findByNombreContainingIgnoreCase(String nombre) {
		// TODO Auto-generated method stub
		//return personajeRepository.findByNombre(nombre);
		return  personajeRepository.findByNombreContainingIgnoreCase(nombre);	
	}

	@Override
	public List<Personaje> findAllByEdad(Integer edad) {
		// TODO Auto-generated method stub
		return personajeRepository.findAllByEdad(edad);
	}
	

	


	
}
