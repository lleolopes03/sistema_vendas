package com.br.sistema_teste.dto.mapper;

import com.br.sistema_teste.domain.Usuario;
import com.br.sistema_teste.dto.UsuarioCreateDto;
import com.br.sistema_teste.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static Usuario toUsuario(UsuarioCreateDto createDto){
        if (createDto == null) {
            throw new IllegalArgumentException("CreateDto cannot be null");
        }
        System.out.println("Mapping UsuarioCreateDto: " + createDto);
        return new ModelMapper().map(createDto, Usuario.class);
    }
    public static UsuarioResponseDto toDto(Usuario usuario){
        PropertyMap<Usuario,UsuarioResponseDto>props=new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {

            }
        };
        ModelMapper mapper =new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }
    public static List<UsuarioResponseDto> toListDto(List<Usuario>usuarios){
        return usuarios.stream().map(user->toDto(user)).collect(Collectors.toList());
    }
}
