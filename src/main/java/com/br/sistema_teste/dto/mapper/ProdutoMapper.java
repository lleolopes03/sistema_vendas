package com.br.sistema_teste.dto.mapper;

import com.br.sistema_teste.domain.Produtos;
import com.br.sistema_teste.dto.ProdutoCreateDto;
import com.br.sistema_teste.dto.ProdutoResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoMapper {
    public static Produtos toProduto(ProdutoCreateDto createDto) {
        ModelMapper mapper = new ModelMapper();
        Produtos produto = mapper.map(createDto, Produtos.class);

        // 🔥 Converte preco de String para BigDecimal, lidando com ',' e '.'
        if (createDto.getPreco() != null) { // ✅ Correto: Chamando o método da instância
            String precoString = createDto.getPreco().toString().replace(",", ".");
            produto.setPreco(new BigDecimal(precoString));
        }

        return produto;
    }

    public static ProdutoResponseDto toDto(Produtos produtos){
        PropertyMap<Produtos,ProdutoResponseDto>props=new PropertyMap<Produtos, ProdutoResponseDto>() {
            @Override
            protected void configure() {

            }
        };
        ModelMapper mapper=new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(produtos, ProdutoResponseDto.class);
    }
    public static List<ProdutoResponseDto>toListDto(List<Produtos>produtos){
        return produtos.stream().map(pro->toDto(pro)).collect(Collectors.toList());
    }
}
