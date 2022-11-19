package cl.cokke.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "generos")
public class Genero {

	@Id
	@Column(name="id_genero")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="image")
	private String image;
	//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	
	
	@OneToMany(mappedBy = "genero",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PeliculaSerie> peliculasYSeries;
}
