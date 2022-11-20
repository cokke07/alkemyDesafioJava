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
import cl.cokke.model.Genero;
import cl.cokke.model.PeliculaSerie;
import cl.cokke.model.Personaje;
import cl.cokke.service.GeneroService;
import cl.cokke.service.PeliculaSerieService;
import cl.cokke.service.PersonajeService;

@RestController
@RequestMapping("/api/v1/disney")
public class MundoDisneyController {

	@Autowired
	private PersonajeService personajeServicio;

	@Autowired
	private PeliculaSerieService peliculaSerieServicio;

	@Autowired
	private GeneroService generoServicio;

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
	@PostMapping("/characters")
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

	// Buscar por ID pelicula
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

	// Buscar todas las peliculas y series y mostrarlos en la peticion
	@GetMapping("/movies")
	public ResponseEntity<List<PeliculaSerie>> listarTodasPeliculaSeries() {
		List<PeliculaSerie> peliSeries = new ArrayList<PeliculaSerie>();
		peliSeries = peliculaSerieServicio.buscarTodos();

		if (peliSeries.isEmpty()) {
			return new ResponseEntity<>(peliSeries, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(peliSeries, HttpStatus.OK);

	}

	// Crear peliculas y series nuevas
	@PostMapping("/movies")
	public ResponseEntity<PeliculaSerie> insertarPeliculaSerie(@RequestBody PeliculaSerie p) {
		PeliculaSerie nuevaPeliSerie = (peliculaSerieServicio.guardarPeliculaSerie(p));
		System.out.println(nuevaPeliSerie.toString());
		return new ResponseEntity<>(nuevaPeliSerie, HttpStatus.CREATED);
	}

	// Buscar peli o series por ID
	@GetMapping("/movies/{id}")
	public ResponseEntity<PeliculaSerie> buscarPeliculaSeriePorId(@PathVariable("id") Long id) {

		try {
			Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);

			if (peliSerieEncontrada.isPresent()) {
				return new ResponseEntity<>(peliSerieEncontrada.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Eliminar peli o series pasando la ID
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<HttpStatus> eliminarPeluculaSerie(@PathVariable("id") Long id) {

		Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);

		if (peliSerieEncontrada.isPresent()) {
			peliculaSerieServicio.eliminarPeliculaSerie(id);
			System.out.println("Pelicula o serie eliminada");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// Modificar personaje pasando la ID
	@PutMapping("/movies/{id}")
	public ResponseEntity<PeliculaSerie> actualizarPeliculaSerie(@PathVariable("id") Long id,
			@RequestBody PeliculaSerie p) {

		Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);
		if (peliSerieEncontrada.isPresent()) {
			peliSerieEncontrada.get().setTitulo(p.getTitulo());
			peliSerieEncontrada.get().setFechaCreacion(p.getFechaCreacion());
			peliSerieEncontrada.get().setCalificacion(p.getCalificacion());
			peliSerieEncontrada.get().setPersonajes(p.getPersonajes());

			return new ResponseEntity<>(peliculaSerieServicio.guardarPeliculaSerie(peliSerieEncontrada.get()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// Buscar todos los generos y mostrarlos en la peticion
	@GetMapping("/gender")
	public ResponseEntity<List<Genero>> listarTodosGeneros() {
		List<Genero> generos = new ArrayList<Genero>();
		generos = generoServicio.buscarTodos();

		if (generos.isEmpty()) {
			return new ResponseEntity<>(generos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(generos, HttpStatus.OK);

	}

	// Crear genero nuevos
	@PostMapping("/gender")
	public ResponseEntity<Genero> insertarGenero(@RequestBody Genero g) {
		Genero nuevoGenero = (generoServicio.guardarGenero(g));
		System.out.println(nuevoGenero.toString());
		return new ResponseEntity<>(nuevoGenero, HttpStatus.CREATED);
	}

}
