package br.uema.locacao.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import br.uema.locacao.api.entity.Usuario;
import br.uema.locacao.api.enums.NivelEnum;
import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.service.LoginService;
import br.uema.locacao.api.service.UsuarioService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private JwtTokenServiceImpl jwtTokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public String login(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			Usuario usuario = usuarioService.findByUsername(username);
			
			List<NivelEnum> lista = new ArrayList<NivelEnum>();
			lista.add(usuario.getNivel());
			String token = jwtTokenService.createToken(username,
					lista.stream().map((NivelEnum nivel) -> "ROLE_" + nivel.name())
							.filter(Objects::nonNull).collect(Collectors.toList()));
			return token;
		
		} catch (AuthenticationException e) {
			throw new CustomException(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);

		}
	}

	@Override
	public boolean logout(String token) {
		jwtTokenService.delete(token);
		return true;
	}

	@Override
	public Boolean isValidToken(String token) {
		return jwtTokenService.validateToken(token);
	}

}
