package br.uema.locacao.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/token")
	public ResponseEntity<Response<Usuario>> getUsuarioFromToken(@RequestHeader(name = "Authorization") String token) {
		
		Response<Usuario> response = new Response<>();
		Usuario usuario = usuarioService.findByUsername(jwtTokenService.getUsername(token));		
		if(usuario != null) {
			usuario.setPassword("");
			response.setData(usuario);
			return ResponseEntity.ok().body(response);
		}else {
			response.getErrors().add("Usuario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
	}

}
