package cl.cokke.mapper;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.cokke.dto.PersonajeDTO;
import cl.cokke.model.Personaje;

@Component
public class PersonajeMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public PersonajeDTO convertirAPersonajeDTO(Personaje personaje) {
		return modelMapper.map(personaje, PersonajeDTO.class);
	}
	
	public List<PersonajeDTO> convertirAListPersonajeDTO(List<Personaje> personajes){
		
		return personajes.stream().map(this::convertirAPersonajeDTO).collect(Collectors.toList());
		
	}
	
	
	
	
	
}
