package com.br.sistema_teste.dto.mapper;

import com.br.sistema_teste.domain.Cliente;
import com.br.sistema_teste.dto.ClienteCreateDto;
import com.br.sistema_teste.dto.ClienteResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {
    public static Cliente toCliente(ClienteCreateDto createDto){
        return new ModelMapper().map(createDto, Cliente.class);
    }
    public static ClienteResponseDto toDto(Cliente cliente){
        PropertyMap<Cliente,ClienteResponseDto>props=new PropertyMap<Cliente, ClienteResponseDto>() {
            @Override
            protected void configure() {

            }
        };
        ModelMapper mapper=new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(cliente, ClienteResponseDto.class);
    }
    public static List<ClienteResponseDto>toListDto(List<Cliente>clientes){
        return clientes.stream().map(cli->toDto(cli)).collect(Collectors.toList());
    }
}
