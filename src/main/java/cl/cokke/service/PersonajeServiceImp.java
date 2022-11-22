package cl.cokke.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.cokke.dto.PersonajeDTO;
import cl.cokke.exception.BadRecuestException;
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
		
		List<Personaje> personajes = personajeRepository.findAll();
		if(personajes.isEmpty()) {
			throw new BadRecuestException(" No se encontraron personajes");
		}else {
			return personajeMapper.convertirAListPersonajeDTO(personajes);
		}
		
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
	public List<PersonajeDTO> findAllByEdad(Integer edad) {
		List<Personaje> personajes = personajeRepository.findAllByEdad(edad);
				
		return personajeMapper.convertirAListPersonajeDTO(personajes);
	}

	@Override
	public List<PersonajeDTO> findByIdPeliculaSerie(Long id) {
		// TODO Auto-generated method stub
		List<Personaje> personajes =  personajeRepository.findByPeliculaSerieId(id);
		
		return personajeMapper.convertirAListPersonajeDTO(personajes);
	}

	@Override
	public List<PersonajeDTO> findByNombreContainingIgnoreCase(String nombre) {
		List<Personaje> personajes = personajeRepository.findByNombreContainingIgnoreCase(nombre);
		return personajeMapper.convertirAListPersonajeDTO(personajes);
	}
	

	


	
}
