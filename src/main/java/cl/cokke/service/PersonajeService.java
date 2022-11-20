package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import cl.cokke.dto.PersonajeDTO;
import cl.cokke.model.Personaje;

public interface PersonajeService {

	//Crud
	public List<PersonajeDTO> buscarTodos();
	public Optional<Personaje> buscarPorId(Long id);
	public Personaje guardarPersonaje(Personaje p);
	public Personaje editarPersonaje(Personaje p);
	public void eliminarPersonaje(Long id);
	//Busquedas especiales
	public List<PersonajeDTO> findByNombreContainingIgnoreCase(String nombre);
	public List<PersonajeDTO> findByIdPeliculaSerie(Long id);
	public List<PersonajeDTO> findAllByEdad(Integer edad);
	
}
