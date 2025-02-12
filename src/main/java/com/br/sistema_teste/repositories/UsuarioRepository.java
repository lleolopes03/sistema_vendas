package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
