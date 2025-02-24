package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Produtos;
import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.dto.mapper.ProdutoMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;

import com.br.sistema_teste.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;


    public ProdutoResponseDto salvar(ProdutoCreateDto createDto){
        Produtos produtos= ProdutoMapper.toProduto(createDto);
        Produtos produtos1=produtoRepository.save(produtos);
        return ProdutoMapper.toDto(produtos1);
    }
    public ProdutoResponseDto buscarProId(Long id){
        Produtos produtos=produtoRepository.findById(id).orElseThrow(
                ()->new BusinessException(String.format("Produto com id: %s não encontrado",id))
        );
        return ProdutoMapper.toDto(produtos);
    }
    public List<ProdutoResponseDto>buscarTodos(){
        List<Produtos>produtos=produtoRepository.findAll();
        return ProdutoMapper.toListDto(produtos);
    }
    public void deletar(Long id){
        produtoRepository.deleteById(id);

    }
    public ProdutoResponseDto editarProduto(Long id,ProdutoCreateDto createDto){
        Optional<Produtos>produtosOptional=produtoRepository.findById(id);
        if (produtosOptional.isPresent()){
            Produtos produtos=produtosOptional.get();
            Produtos produtosAtualizado=ProdutoMapper.toProduto(createDto);
            produtosAtualizado.setId(produtos.getId());
            produtos=produtoRepository.save(produtosAtualizado);
            return ProdutoMapper.toDto(produtos);

        }else {
            throw new BusinessException(String.format("Produto com Id: %s não encontrado",id));
        }
    }



}
