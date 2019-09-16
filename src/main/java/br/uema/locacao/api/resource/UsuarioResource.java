package br.uema.locacao.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uema.locacao.api.dto.Response;
import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.service.JwtTokenService;
import br.uema.locacao.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtTokenService jwtTokenService;

	@PostMapping
	public ResponseEntity<Response<Usuario>> register(@RequestBody Usuario usuario) {
		Response<Usuario> response = new Response<>();
		try {
			response.setData(this.usuarioService.create(usuario));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}
	
	@PutMapping
	public ResponseEntity<Response<Usuario>> update(@RequestBody Usuario usuario) {
		Response<Usuario> response = new Response<>();
		try {
			response.setData(this.usuarioService.update(usuario));
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

	@GetMapping("/token")
	public ResponseEntity<Response<Usuario>> getUsuarioFromToken(@RequestHeader(name = "Authorization") String token) {

		Response<Usuario> response = new Response<>();
		Usuario usuario = usuarioService.findByUsername(jwtTokenService.getUsername(token));
		if (usuario != null) {
			usuario.setPassword("");
			response.setData(usuario);
			return ResponseEntity.ok().body(response);
		} else {
			response.getErrors().add("Usuario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping(value="{id}")
	public ResponseEntity<Response<Usuario>> findById(
			@PathVariable Long id, 
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Usuario> response = new Response<>();
				
		
		response.setData(usuarioService.findById(id));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value="{page}/{count}")
	public ResponseEntity<Response<Page<Usuario>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		Response<Page<Usuario>> response = new Response<>();
		Page<Usuario> usuarios = usuarioService.findAll(page, count);
		response.setData(usuarios);
		return ResponseEntity.ok(response);
	}

}
