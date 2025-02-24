package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {
}
