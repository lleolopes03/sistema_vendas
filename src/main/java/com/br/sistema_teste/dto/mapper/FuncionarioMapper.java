package com.br.sistema_teste.dto.mapper;

import com.br.sistema_teste.domain.Funcionario;
import com.br.sistema_teste.dto.FuncionarioCreateDto;
import com.br.sistema_teste.dto.FuncionarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioMapper {
    public static Funcionario toFuncionario(FuncionarioCreateDto createDto){
        return new ModelMapper().map(createDto, Funcionario.class);
    }
    public static FuncionarioResponseDto toDto(Funcionario funcionario){
        PropertyMap<Funcionario,FuncionarioResponseDto>props=new PropertyMap<Funcionario, FuncionarioResponseDto>() {
            @Override
            protected void configure() {

            }
        };
        ModelMapper mapper=new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(funcionario, FuncionarioResponseDto.class);
    }
    public static List<FuncionarioResponseDto>toListDto(List<Funcionario>funcionarios){
        return funcionarios.stream().map(funcionario -> toDto(funcionario)).collect(Collectors.toList());
    }
}
