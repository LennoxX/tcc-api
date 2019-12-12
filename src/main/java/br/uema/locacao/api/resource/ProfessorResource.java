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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.uema.locacao.api.dto.Response;
import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.service.ProfessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin("*")
@Api(value = "Resource Professor")
public class ProfessorResource {

	@Autowired
	private ProfessorService service;

	@PostMapping
	@ApiOperation(value = "Create; Método que cadastra um professor no sistema")
	public ResponseEntity<Response<Professor>> create(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody Professor professor) {
		Response<Professor> response = new Response<>();
		response.setData(service.create(professor));
		return ResponseEntity.ok().body(response);

	}

	@PutMapping
	@ApiOperation(value = "Update; Método que atualiza um professor no sistema.")
	public ResponseEntity<Response<Professor>> update(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody Professor professor) {
		Response<Professor> response = new Response<>();
		response.setData(service.update(professor));
		return ResponseEntity.ok().body(response);

	}

	@GetMapping(value = "{page}/{count}")
	@ApiOperation(value = "Find All; Método que traz os professores cadastrados no sistema de forma paginada.")
	public ResponseEntity<Response<Page<Professor>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Page<Professor>> response = new Response<>();
		Pageable pageable = PageRequest.of(page, count);
		Page<Professor> professores = service.findAll(pageable);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	@ApiOperation(value = "Get All; Método que traz todos os professores cadastrados no sistema.")
	public ResponseEntity<Response<List<Professor>>> getAll(
			@RequestHeader(value = "Authorization") String authorization) {
		Response<List<Professor>> response = new Response<>();
		List<Professor> professores = service.findAll();
		response.setData(professores);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "elegiveis")
	@ApiOperation(value = "Find All; Método que traz os professores elegíveis para uma nova locação.")
	public ResponseEntity<Response<List<Professor>>> getAllElegiveis(
			@RequestHeader(value = "Authorization") String authorization) {
		Response<List<Professor>> response = new Response<>();
		List<Professor> professores = service.findAllElegiveis();
		response.setData(professores);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{id}")
	@ApiOperation(value = "Find All; Método que traz um professor pelo ID.")
	public ResponseEntity<Response<Professor>> findById(@PathVariable Long id,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Professor> response = new Response<>();
		response.setData(service.findById(id));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}/parameters")
	@ApiOperation(value = "Find All; Método que traz os professores cadastrados no sistema de forma paginada e com base nos filtros especificados.")
	public ResponseEntity<Response<Page<Professor>>> findAllByParameters(@PathVariable int page, @PathVariable int count,
			@RequestParam(name = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(name = "matricula", required = false, defaultValue = "") String matricula,
			@RequestParam(name = "curso", required = false, defaultValue = "") String curso,
			@RequestParam(name = "sort", required = false, defaultValue = "nome!asc") List<String> sort,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Page<Professor>> response = new Response<>();
		Page<Professor> professores = service.findByParameters(page, count, matricula, nome, curso, sort,
				authorization);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}
}
