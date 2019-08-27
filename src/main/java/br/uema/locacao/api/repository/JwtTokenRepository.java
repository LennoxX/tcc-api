package br.uema.locacao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.locacao.api.entity.JwtToken;

public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {

}
