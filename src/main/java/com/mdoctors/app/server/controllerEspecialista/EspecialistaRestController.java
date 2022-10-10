package com.mdoctors.app.server.controllerEspecialista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdoctors.app.server.entity.Especialistas;
import com.mdoctors.app.server.service.IEspecialistaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class EspecialistaRestController {
	@Autowired
	IEspecialistaService espService;
	
	@GetMapping("/especialista")
	public List<Especialistas> index(){
		return espService.findAll();
	}
	
	@GetMapping("/especialista/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Especialistas especialista = null;
		Map<String, Object> response = new HashMap<>();
		try {
			especialista = espService.findById(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (especialista == null) {
			response.put("mensaje", "El especialista ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Especialistas>(especialista, HttpStatus.OK);

	}
	
	
	@PostMapping("/especialista")
	public ResponseEntity<?> create( @RequestBody Especialistas especialista, BindingResult result) {
		
		Especialistas especialistaNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			especialistaNew = espService.save(especialista);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El especialista ha sido creado con éxito!");
		response.put("especialista", especialistaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/especialista/{id}")
	public ResponseEntity<?> update(@RequestBody Especialistas especialista, BindingResult result, @PathVariable Long id) {

		Especialistas especialistaActual = espService.findById(id);

		Especialistas especialistaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (especialistaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el especialista ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			especialistaActual.setEspecialidad(especialista.getEspecialidad());
			especialistaActual.setNombre(especialista.getNombre());
			especialistaActual.setCedula(especialista.getCedula());
			especialistaActual.setCelular(especialista.getCelular());
			especialistaActual.setEmail(especialista.getEmail());
			especialistaActual.setCiudad(especialista.getCiudad());
			especialistaActual.setConsultorio(especialista.getConsultorio());
			especialistaActual.setCreateAt(especialista.getCreateAt());
			

			especialistaUpdated = espService.save(especialistaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el especialista en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El especialista ha sido actualizado con éxito!");
		response.put("especialista", especialistaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/especialista/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Especialistas especialista = espService.findById(id);		
			espService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el especialista de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El especialista eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	

}
