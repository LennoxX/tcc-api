package br.uema.locacao.api.resource;

import java.util.Date;
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
import br.uema.locacao.api.entity.Locacao;
import br.uema.locacao.api.enums.EnumStatusDatashow;
import br.uema.locacao.api.enums.EnumStatusLocacao;
import br.uema.locacao.api.service.DatashowService;
import br.uema.locacao.api.service.LocacaoService;

@RestController
@RequestMapping("/api/locacao")
@CrossOrigin("*")
public class LocacaoResource {

	@Autowired
	private LocacaoService service;

	@Autowired
	private DatashowService datashowService;

	@PostMapping
	public ResponseEntity<Response<Locacao>> create(
			@RequestHeader(value = "Authorization", required = false) String authorization,
			@RequestBody Locacao locacao) {
		Response<Locacao> response = new Response<>();
		locacao.setStatus(EnumStatusLocacao.ANDAMENTO);
		locacao.getDatashow().setStatus(EnumStatusDatashow.EMPRESTADO);
		datashowService.update(locacao.getDatashow());
		locacao.setDataInicio(new Date());
		response.setData(service.create(locacao));
		return ResponseEntity.ok().body(response);

	}

	@PutMapping
	public ResponseEntity<Response<Locacao>> atualizar(
			@RequestHeader(value = "Authorization", required = false) String authorization,
			@RequestBody Locacao locacao) {
		Response<Locacao> response = new Response<>();
		Datashow temp = service.findById(locacao.getId()).getDatashow();
		temp.setStatus(EnumStatusDatashow.DISPONIVEL);
		datashowService.update(temp);
		
		locacao.setProfessor(service.findById(locacao.getId()).getProfessor());
		locacao.setDataInicio((service.findById(locacao.getId()).getDataInicio())); 
		locacao.setStatus(service.findById(locacao.getId()).getStatus());
		locacao.getDatashow().setStatus(EnumStatusDatashow.EMPRESTADO);
		datashowService.update(locacao.getDatashow());
		
		response.setData(service.update(locacao));
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("{id}/finalizar")
	public ResponseEntity<Response<Locacao>> finalizar(
			@RequestHeader(value = "Authorization", required = false) String authorization, @PathVariable Long id) {
		Locacao locacao = service.findById(id);
		locacao.setStatus(EnumStatusLocacao.CONCLUIDA);
		Response<Locacao> response = new Response<>();
		locacao.setDataFim(new Date());
		locacao.getDatashow().setStatus(EnumStatusDatashow.DISPONIVEL);
		datashowService.update(locacao.getDatashow());
		try {
			response.setData(service.update(locacao));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Locacao>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Locacao>> response = new Response<>();

		Pageable pageable = PageRequest.of(page, count);

		Page<Locacao> professores = service.findAll(pageable);
		response.setData(professores);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Locacao>> findById(@PathVariable Long id,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Locacao> response = new Response<>();

		response.setData(service.findById(id));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}/parameters")
	public ResponseEntity<Response<Page<Locacao>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestParam(name = "professor", required = false, defaultValue = "") String professor,
			@RequestParam(name = "datashow", required = false, defaultValue = "") String datashow,
			@RequestParam(name = "status", required = false, defaultValue = "") String status,
			@RequestParam(name = "sort", required = false, defaultValue = "id!asc") List<String> sort,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Locacao>> response = new Response<>();
		Pageable pageable = PageRequest.of(page, count);
		Page<Locacao> locacoes = service.findByParameters(page, count, professor, datashow, status, sort);
		response.setData(locacoes);
		return ResponseEntity.ok(response);
	}


}
