package br.com.fiap3es.springbootproject.config.security.filter;

import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import br.com.fiap3es.springbootproject.application.ports.out.UsuarioRepository;
import br.com.fiap3es.springbootproject.config.security.service.TokenBlackListService;
import br.com.fiap3es.springbootproject.config.security.service.TokenService;
import br.com.fiap3es.springbootproject.config.security.util.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final TokenBlackListService tokenBlackListService;

    public SecurityFilter(
            TokenService tokenService,
            UsuarioRepository usuarioRepository,
            TokenBlackListService tokenBlackListService) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.tokenBlackListService = tokenBlackListService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = TokenUtils.recuperarToken(request);
        if(tokenJWT != null) {
            if(tokenBlackListService.isBlackListed(tokenJWT)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String subject = tokenService.getSubject(tokenJWT);
            Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(subject);
            if(usuarioOptional.isPresent()) {
                UserDetails usuario = usuarioOptional.get();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );
                authentication.setDetails(tokenJWT);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}