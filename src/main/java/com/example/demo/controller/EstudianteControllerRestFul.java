package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.IEstudianteService;
import com.example.demo.service.IMateriaService;
import com.example.demo.service.to.EstudianteTO;
import com.example.demo.service.to.MateriaTO;


@RestController
@RequestMapping("/estudiantes")
@CrossOrigin
public class EstudianteControllerRestFul {

	@Autowired
	private IEstudianteService estudianteService;
	
	@Autowired
	private IMateriaService materiaService;

	// GET
	@GetMapping(path = "/{cedula}", produces = MediaType.APPLICATION_JSON_VALUE)	
	@ResponseStatus(HttpStatus.OK)
	public Estudiante consultarPorCedula(@PathVariable String cedula) {
		
		return this.estudianteService.consultarPorCedula(cedula);
	}
	
	@GetMapping(path = "/status/{cedula}")	
	public ResponseEntity<Estudiante> consultarPorCedulaStatus(@PathVariable String cedula) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.estudianteService.consultarPorCedula(cedula));
	}
	
	@GetMapping
	public ResponseEntity<List<Estudiante>> consultarTodos(@RequestParam String provincia) {
		//buscarTodos?provincia=pichincha
		//List<Estudiante> lista = this.estudianteService.buscarTodos(provincia);
		//return this.estudianteService.buscarTodos(provincia);
		
		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.add("detalleMensaje", "Ciudadanos consultados exitosamente");
		cabeceras.add("valorAPI", "Incalculable");
		
		return new ResponseEntity<>(this.estudianteService.buscarTodos(provincia),cabeceras,228);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void guardar(@RequestBody Estudiante estudiante) {
		this.estudianteService.guardar(estudiante);
	}

	@PutMapping(path = "/{identificador}")
	public void actualizar(@RequestBody Estudiante estudiante, @PathVariable Integer identificador) {
		estudiante.setId(identificador);
		this.estudianteService.actualizar(estudiante);
	}

	@PatchMapping(path = "/{identificador}")
	public void actualizarParcial(@RequestBody Estudiante estudiante, @PathVariable Integer identificador) {
		estudiante.setId(identificador);
		String cedula = "789";
		Estudiante estu1 = this.estudianteService.consultarPorCedula(cedula);
		estu1.setCedula(estudiante.getCedula());

		this.estudianteService.actualizar(estu1);
	}

	@DeleteMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EstudianteTO> borrar(@PathVariable Integer id) {
		EstudianteTO estu =  this.estudianteService.consultarPorId(id);
		this.estudianteService.eliminar(id);
		return new ResponseEntity<>(estu,null,200);
	}
	
	@PostMapping(path = "/Respuesta",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Estudiante guardarRespuesta(@RequestBody Estudiante estudiante) {
		
		String cedula = estudiante.getCedula();
		
		this.estudianteService.guardar(estudiante);
		
		return this.estudianteService.consultarPorCedula(cedula);
		
	}
	
	@GetMapping(path = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EstudianteTO>> consultarTodosHATEOAS() {
		List<EstudianteTO> lista = this.estudianteService.buscarTodos();
		
		for(EstudianteTO e : lista) {
			Link myLink = linkTo(methodOn(EstudianteControllerRestFul.class).buscarPorEstudiante(e.getCedula())).withRel("materias");
			e.add(myLink);
		}
		
		return new ResponseEntity<>(lista,null,200);
	}
	

	@GetMapping(path = "/{cedula}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MateriaTO>> buscarPorEstudiante(@PathVariable String cedula){

        List<MateriaTO> lista = this.materiaService.buscarPorCedulaEstudiante(cedula);

        for(MateriaTO mat : lista) {
            Link myLink = linkTo(methodOn(MateriaControllerRestFul.class).buscarPorId(mat.getId())).withSelfRel();
            mat.add(myLink);
        }


        return new ResponseEntity<>(lista,null,200); 
    }
	

}
