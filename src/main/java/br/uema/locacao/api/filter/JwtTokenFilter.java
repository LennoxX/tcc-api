package br.uema.locacao.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.uema.locacao.api.exception.CustomException;
import br.uema.locacao.api.service.JwtTokenService;
import io.jsonwebtoken.JwtException;

public class JwtTokenFilter extends GenericFilterBean {
	
	private JwtTokenService jwtTokenProvider;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenProvider = jwtTokenService;
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null) {
            if (!jwtTokenProvider.isTokenPresentInDB(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
                throw new CustomException("Token JWT inválido.", HttpStatus.UNAUTHORIZED);
            }
            try {
                jwtTokenProvider.validateToken(token) ;
            } catch (JwtException | IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token JWT inválido.");
                throw new CustomException("Token JWT inválido.", HttpStatus.UNAUTHORIZED);
            }
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
	}

}
