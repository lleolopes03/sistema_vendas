package com.br.sistema_teste.controller;

import com.br.sistema_teste.dto.UsuarioLoginDto;
import com.br.sistema_teste.jwt.JwtToken;
import com.br.sistema_teste.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j

@RestController
@RequestMapping("api/v1")
public class AutenticacaoController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/{auth}")
    public ResponseEntity<?>autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request){

        try{
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token=detailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException ex){

        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage( "Credenciais Inválidas"));

    }
}
