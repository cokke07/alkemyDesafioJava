package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.cokke.model.Genero;
import cl.cokke.repository.GeneroRepository;

@Service
public class GeneroServiceImp implements GeneroService {

	@Autowired
	private GeneroRepository generoRepository;
	
	@Override
	public List<Genero> buscarTodos() {
		// TODO Auto-generated method stub
		return generoRepository.findAll();
	}

	@Override
	public Optional<Genero> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return generoRepository.findById(id);
	}

	@Override
	public Genero guardarGenero(Genero g) {
		// TODO Auto-generated method stub
		return generoRepository.save(g);
	}

	@Override
	public Genero editarGenero(Genero g) {
		// TODO Auto-generated method stub
		return generoRepository.save(g);
	}

	@Override
	public void eliminarGenero(Long id) {
		generoRepository.deleteById(id);

	}

}
