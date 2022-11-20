package cl.cokke.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.cokke.dto.PersonajeDTO;
import cl.cokke.model.Personaje;
import cl.cokke.service.GeneroService;
import cl.cokke.service.PeliculaSerieService;
import cl.cokke.service.PersonajeService;

@RestController
@RequestMapping("/api/v1/disney")
public class PersonajeController {

	@Autowired
	private PersonajeService personajeServicio;

	// Buscar todos los personajes y mostrarlos en la peticion
	@GetMapping("/characters")
	public ResponseEntity<List<PersonajeDTO>> listarTodos() {
		List<PersonajeDTO> personajes = new ArrayList<PersonajeDTO>();
		personajes = personajeServicio.buscarTodos();

		if (personajes.isEmpty()) {
			return new ResponseEntity<>(personajes, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(personajes, HttpStatus.OK);

	}

	// Crear personajes nuevos
	@PostMapping("/characters/create")
	public ResponseEntity<Personaje> insertarPersonaje(@RequestBody Personaje p) {
		Personaje nuevoPersonaje = (personajeServicio.guardarPersonaje(p));
		System.out.println(nuevoPersonaje.toString());
		return new ResponseEntity<>(nuevoPersonaje, HttpStatus.CREATED);
	}

	// Buscar personajes por ID
	@GetMapping("/characters/{id}")
	public ResponseEntity<Personaje> buscarPersonajePorId(@PathVariable("id") Long id) {

		try {
			Optional<Personaje> userEncontrado = personajeServicio.buscarPorId(id);

			if (userEncontrado.isPresent()) {
				return new ResponseEntity<>(userEncontrado.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Eliminar personajes pasando la ID
	@DeleteMapping("/characters/{id}")
	public ResponseEntity<HttpStatus> eliminarPersonaje(@PathVariable("id") Long id) {

		Optional<Personaje> personajeEncontrado = personajeServicio.buscarPorId(id);

		if (personajeEncontrado.isPresent()) {
			personajeServicio.eliminarPersonaje(id);
			System.out.println("Usuario eliminado");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// Modificar personaje pasando la ID
	@PutMapping("/characters/{id}")
	public ResponseEntity<Personaje> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Personaje p) {

		Optional<Personaje> personajeEncontrado = personajeServicio.buscarPorId(id);
		if (personajeEncontrado.isPresent()) {
			personajeEncontrado.get().setNombre(p.getNombre());
			personajeEncontrado.get().setEdad(p.getEdad());
			personajeEncontrado.get().setPeso(p.getPeso());
			personajeEncontrado.get().setHistoria(p.getHistoria());
			personajeEncontrado.get().setImagen(p.getImagen());

			return new ResponseEntity<>(personajeServicio.guardarPersonaje(personajeEncontrado.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// Buscar por Id de pelicula
	@GetMapping("/characters/movies")
	public ResponseEntity<List<PersonajeDTO>> buscarPersonajePorIdPeliculaSerie(@RequestParam Long idMovie) {

		List<PersonajeDTO> personajeBuscado = personajeServicio.findByIdPeliculaSerie(idMovie);

		return new ResponseEntity<>(personajeBuscado, HttpStatus.OK);
	}
		
	// Buscar por nombre
	@GetMapping("/characters/name")
	public ResponseEntity<List<PersonajeDTO>> buscarPersonajePorNombre(@RequestParam String nombre) {

		List<PersonajeDTO> personajeBuscado = personajeServicio.findByNombreContainingIgnoreCase(nombre);

		return new ResponseEntity<>(personajeBuscado, HttpStatus.OK);
	}

	// Buscar por edad
	@GetMapping("/characters/age")
	public ResponseEntity<List<PersonajeDTO>> buscarPersonajePorEdad(@RequestParam Integer edad) {

		List<PersonajeDTO> personajeBuscado = personajeServicio.findAllByEdad(edad);

		return new ResponseEntity<>(personajeBuscado, HttpStatus.OK);
	}
}
