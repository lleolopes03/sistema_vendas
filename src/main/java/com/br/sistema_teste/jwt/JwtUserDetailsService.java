package com.br.sistema_teste.jwt;

import com.br.sistema_teste.domain.Usuario;
import com.br.sistema_teste.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Autowired
    public JwtUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username) {
        // Recupera o ID e o role do usuário
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Usuario.Role role = usuario.getRole(); // Obtém a role diretamente do objeto usuário
        Long userId = usuario.getId(); // Obtém o ID do usuário

        // Gera o token com username, role e userId
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()), userId);
    }
}

