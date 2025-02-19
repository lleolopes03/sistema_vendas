package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
