package br.com.fiap3es.springbootproject.application.core.services;

import br.com.fiap3es.springbootproject.application.ports.out.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username)
                .filter(usuario -> usuario.isEnabled())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username + "!"));
    }
}