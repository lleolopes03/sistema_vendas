package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Usuario;
import com.br.sistema_teste.dto.UsuarioCreateDto;
import com.br.sistema_teste.dto.UsuarioResponseDto;
import com.br.sistema_teste.dto.UsuarioSenhaDto;
import com.br.sistema_teste.dto.mapper.UsuarioMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDto salvar(UsuarioCreateDto createDto){
        Usuario usuario=UsuarioMapper.toUsuario(createDto);
        usuario.setPassword(passwordEncoder.encode(createDto.getPassword()));
        if (createDto.getAge()>17) {
            Usuario usuarioSalvar = usuarioRepository.save(usuario);
            return UsuarioMapper.toDto(usuarioSalvar);
        }else {
            throw new BusinessException("Idade minima para cadastro 18 anos");
        }

    }
    public UsuarioResponseDto buscarPorId(Long id){
        Usuario usuario=usuarioRepository.findById(id).orElseThrow(
                ()->new BusinessException("Id:" +id+" não encontrado")
        );
        return UsuarioMapper.toDto(usuario);
    }
    public List<UsuarioResponseDto>buscarTodos(){
        List<Usuario>usuarios=usuarioRepository.findAll();
        return UsuarioMapper.toListDto(usuarios);
    }
    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }
    public void editarSenha(Long id, UsuarioSenhaDto senhaDto){
        Usuario usuario=usuarioRepository.findById(id).orElseThrow(()->new BusinessException("Id não encontrado"));
        if (!passwordEncoder.matches(senhaDto.getSenhaAtual(), usuario.getPassword())){
            throw new BusinessException("Sua senha não confere com senha atual");
        }
        if (!senhaDto.getNovaSenha().equals(senhaDto.getConfirmaSenha())){
            throw new BusinessException("Sua nova senha não confere com senha de confirmação");
        }
        
        if(senhaDto.getSenhaAtual().equals(senhaDto.getNovaSenha())){
            throw new BusinessException("Sua nova senha tem que ser diferente da senha antiga");
        }
        usuario.setPassword(passwordEncoder.encode(senhaDto.getNovaSenha()));
        usuarioRepository.save(usuario);


    }
    @Transactional
    public Usuario buscarPorUsername(String username){
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()->new EntityNotFoundException(String.format("Usuario com 'username' não encontrado",username))
        );
    }
    @Transactional
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }

}

