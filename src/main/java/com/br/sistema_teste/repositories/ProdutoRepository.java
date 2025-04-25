package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produtos,Long> {
    Optional<Produtos> findByNome(String nome);
}
