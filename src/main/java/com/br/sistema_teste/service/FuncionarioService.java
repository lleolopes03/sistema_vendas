package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Funcionario;
import com.br.sistema_teste.dto.FuncionarioCreateDto;
import com.br.sistema_teste.dto.FuncionarioResponseDto;
import com.br.sistema_teste.dto.mapper.FuncionarioMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioResponseDto salvar(FuncionarioCreateDto createDto){
        Funcionario funcionario= FuncionarioMapper.toFuncionario(createDto);
        Funcionario funcionario1=funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(funcionario1);
    }
    public FuncionarioResponseDto buscarPorId(Long id){
        Funcionario funcionario=funcionarioRepository.findById(id).orElseThrow(
                ()->new BusinessException(String.format("Funcionario com id: %s não encontrado",id))
        );
                return FuncionarioMapper.toDto(funcionario);
    }
    public List<FuncionarioResponseDto>toListDto(){
        List<Funcionario>funcionarios=funcionarioRepository.findAll();
        return FuncionarioMapper.toListDto(funcionarios);
    }
    public void deletar(Long id){
        funcionarioRepository.deleteById(id);
    }
    public FuncionarioResponseDto editarFuncionario(Long id,FuncionarioCreateDto createDto){
        Optional<Funcionario>funcionarioOptional=funcionarioRepository.findById(id);
        if(funcionarioOptional.isPresent()){
            Funcionario funcionario=funcionarioOptional.get();
            Funcionario funcionarioAtualizado=FuncionarioMapper.toFuncionario(createDto);
            funcionarioAtualizado.setId(funcionario.getId());
            funcionario=funcionarioRepository.save(funcionarioAtualizado);
            return FuncionarioMapper.toDto(funcionario);
        }else {
            throw new BusinessException(String.format("Funcionario com id: %s não encontrado",id));
        }
    }
}
