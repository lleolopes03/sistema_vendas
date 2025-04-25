package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Cliente;
import com.br.sistema_teste.dto.ClienteCreateDto;
import com.br.sistema_teste.dto.ClienteResponseDto;
import com.br.sistema_teste.dto.mapper.ClienteMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    public ClienteResponseDto salvar(ClienteCreateDto createDto){
        Cliente cliente= ClienteMapper.toCliente(createDto);
        if (createDto.getIdade()>17){
            Cliente cliente1=clienteRepository.save(cliente);
            return ClienteMapper.toDto(cliente1);
        }else {
            throw new BusinessException("Idade minima para cadastro 18 anos");
        }

    }
    public ClienteResponseDto buscarPorId(Long id){
        Cliente cliente=clienteRepository.findById(id).orElseThrow(
                ()->new BusinessException(String.format("Cliente com id: %s não encontrado",id))
        );
        return ClienteMapper.toDto(cliente);
    }
    public List<ClienteResponseDto>buscarTodos(){
        List<Cliente>clientes=clienteRepository.findAll();
        return ClienteMapper.toListDto(clientes);
    }
    public void deletar(Long id){
        clienteRepository.deleteById(id);
    }
    public ClienteResponseDto editarCliente(Long id ,ClienteCreateDto createDto){
        Optional<Cliente>clienteOptional=clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            Cliente cliente=clienteOptional.get();
            Cliente clienteAtualizado=ClienteMapper.toCliente(createDto);
            clienteAtualizado.setId(cliente.getId());
            clienteAtualizado.setCpf(cliente.getCpf());
            cliente=clienteRepository.save(clienteAtualizado);
            return ClienteMapper.toDto(cliente);
        }else {
            throw new BusinessException(String.format("Cliente não encontrado com o id: ",id));
        }
    }

}
