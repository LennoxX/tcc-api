package br.uema.locacao.api.service;

public interface LoginService {

	String login(String username, String password);

	boolean logout(String token);

	Boolean isValidToken(String token);

}
