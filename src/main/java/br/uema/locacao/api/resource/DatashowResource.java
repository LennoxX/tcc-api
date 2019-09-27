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
import br.uema.locacao.api.entity.Datashow;
import br.uema.locacao.api.entity.Professor;
import br.uema.locacao.api.enums.EnumStatusDatashow;
import br.uema.locacao.api.service.DatashowService;

@RestController
@RequestMapping("/api/datashow")
@CrossOrigin("*")
public class DatashowResource {

	@Autowired
	private DatashowService service;

	@PostMapping
	public ResponseEntity<Response<Datashow>> create(
			@RequestHeader(value = "Authorization", required = false) String authorization,
			@RequestBody Datashow datashow) {
		Response<Datashow> response = new Response<>();
		try {
			response.setData(service.create(datashow));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping
	public ResponseEntity<Response<Datashow>> update(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody Datashow datashow) {
		Response<Datashow> response = new Response<>();
		System.out.println(datashow.getStatus());
		try {
			response.setData(service.update(datashow));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Datashow>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Datashow>> response = new Response<>();

		Pageable pageable = PageRequest.of(page, count);

		Page<Datashow> professores = service.findAll(pageable);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Datashow>> findById(@PathVariable Long id,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Datashow> response = new Response<>();

		response.setData(service.findById(id));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}/parameters")
	public ResponseEntity<Response<Page<Datashow>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestParam(name = "identificacao", required = false, defaultValue = "") String identificacao,
			@RequestParam(name = "numTombamento", required = false, defaultValue = "") String numTombamento,			
			@RequestParam(name = "status", required = false, defaultValue = "") String status,
			@RequestParam(name = "sort", required = false, defaultValue = "identificacao!asc") List<String> sort,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Datashow>> response = new Response<>();

		Pageable pageable = PageRequest.of(page, count);

		Page<Datashow> professores = service.findByParameters(page, count, identificacao, numTombamento, status, sort);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<List<Datashow>>> getAll(
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<List<Datashow>> response = new Response<>();
		List<Datashow> datashows = service.findAll();
		response.setData(datashows);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "disponiveis")
	public ResponseEntity<Response<List<Datashow>>> getAllDisponiveis(
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<List<Datashow>> response = new Response<>();
		List<Datashow> datashows = service.findAllDisponiveis();
		response.setData(datashows);
		return ResponseEntity.ok(response);
	}

}
