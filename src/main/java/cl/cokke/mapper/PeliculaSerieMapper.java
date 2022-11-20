package cl.cokke.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.cokke.dto.PeliculaSerieDTO;
import cl.cokke.model.PeliculaSerie;


@Component
public class PeliculaSerieMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public PeliculaSerieDTO convertirAPeliculaSerieDTO(PeliculaSerie peliculaSerie) {
		return modelMapper.map(peliculaSerie, PeliculaSerieDTO.class);
	}
	
	public List<PeliculaSerieDTO> convertirAListPersonajeDTO(List<PeliculaSerie> peliculasSeries){
		
		return peliculasSeries.stream().map(this::convertirAPeliculaSerieDTO).collect(Collectors.toList());
		
	}
}
