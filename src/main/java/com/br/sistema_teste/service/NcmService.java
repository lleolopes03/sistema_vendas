package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Produtos;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import com.br.sistema_teste.dto.mapper.ProdutoMapper;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NcmService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponseDto atualizarNcm(Long produtoId, String descricaoProduto) {
        String apiUrl = "URL_DA_API_NCM" + descricaoProduto;
        String ncm = restTemplate.getForObject(apiUrl, String.class);

        Produtos produto = produtoRepository.findById(produtoId).orElseThrow(() -> new BusinessException(String.format("Produto com Id: %s não encontrado", produtoId)));
        produto.setNcm(ncm);

        produtoRepository.save(produto);
        return ProdutoMapper.toDto(produto);

    }
}