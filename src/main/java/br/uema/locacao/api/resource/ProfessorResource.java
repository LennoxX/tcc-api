package br.uema.locacao.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.uema.locacao.api.dto.Response;
import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.service.ProfessorService;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin("*")
public class ProfessorResource {

	@Autowired
	private ProfessorService service;

	@PostMapping
	public ResponseEntity<Response<Professor>> create(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody Professor professor) {
		Response<Professor> response = new Response<>();
		try {
			response.setData(service.create(professor));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping(value="{page}/{count}")
	public ResponseEntity<Response<Page<Professor>>> findAll(
			@PathVariable int page, 
			@PathVariable int count,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Professor>> response = new Response<>();
		
		Pageable pageable = PageRequest.of(page, count);
		
		Page<Professor> professores = service.findAll(pageable);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="{id}")
	public ResponseEntity<Response<Professor>> findById(
			@PathVariable Long id, 
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Professor> response = new Response<>();
				
		
		response.setData(service.findById(id));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="{page}/{count}/parameters")
	public ResponseEntity<Response<Page<Professor>>> findAll(
			@PathVariable int page, 
			@PathVariable int count,
			@RequestParam(name = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(name = "matricula", required = false, defaultValue = "") String matricula,
			@RequestParam(name = "curso", required = false, defaultValue = "") String curso,
			@RequestParam(name = "sort", required = false, defaultValue = "nome!asc") List<String> sort,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Professor>> response = new Response<>();
		
		Pageable pageable = PageRequest.of(page, count);
		
		Page<Professor> professores = service.findByParameters(page, count, matricula, nome, curso, sort, authorization);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}
}
