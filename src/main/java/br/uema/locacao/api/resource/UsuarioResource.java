package br.uema.locacao.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.enums.NivelEnum;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.service.JwtTokenService;
import br.uema.locacao.api.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtTokenService jwtTokenService;

	@PostMapping
	public ResponseEntity<Response<Usuario>> register(@RequestHeader(name = "Authorization") String token, @RequestBody Usuario usuario) {
		if(usuarioService.findByUsername(jwtTokenService.getUsername(token)).getNivel() != NivelEnum.ADMIN)
			throw new CustomException("Acesso não autorizado", HttpStatus.UNAUTHORIZED);
		Response<Usuario> response = new Response<>();
		response.setData(this.usuarioService.create(usuario));
		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<Response<Usuario>> update(@RequestHeader(name = "Authorization") String token, @RequestBody Usuario usuario) {
		Response<Usuario> response = new Response<>();
		response.setData(this.usuarioService.update(usuario));
		return ResponseEntity.ok().body(response);

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

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Usuario>> findById(@PathVariable Long id,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Usuario> response = new Response<>();
		response.setData(usuarioService.findById(id));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Usuario>>> findAll(@PathVariable int page, @PathVariable int count,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Page<Usuario>> response = new Response<>();
		if(usuarioService.findByUsername(jwtTokenService.getUsername(authorization)).getNivel() != NivelEnum.ADMIN)
			throw new CustomException("Acesso não autorizado", HttpStatus.UNAUTHORIZED);
		Page<Usuario> usuarios = usuarioService.findAll(page, count);
		response.setData(usuarios);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/parameters")
	@ApiOperation(value = "Find All; Método que traz os usuários cadastrados no sistema de forma paginada e com base nos filtros especificados.")
	public ResponseEntity<Response<Page<Usuario>>> findAllByParameters(@PathVariable int page, @PathVariable int count,
			@RequestParam(name = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(name = "status", required = false, defaultValue = "") String status,
			@RequestParam(name = "nivel", required = false, defaultValue = "") String nivel,
			@RequestParam(name = "email", required = false, defaultValue = "") String email,
			@RequestParam(name = "sort", required = false, defaultValue = "nome!asc") List<String> sort,
			@RequestHeader(value = "Authorization") String authorization) {
		Response<Page<Usuario>> response = new Response<>();
		Page<Usuario> usuarios = usuarioService.findByParameters(page, count, status, nome, nivel, email, sort,
				authorization);
		response.setData(usuarios);
		return ResponseEntity.ok(response);
	}

	@PutMapping("password")
	public ResponseEntity<Response<Usuario>> updatePassword(@RequestBody Usuario usuario,
			@RequestParam String novaSenha,
			@RequestHeader(name = "Authorization") String authorization) {
		Response<Usuario> response = new Response<>();
		response.setData(usuarioService.updatePassword(usuario, novaSenha));
		return ResponseEntity.ok(response);
	}

}
