package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produtos,Long> {
}
