package com.br.sistema_teste.repositories;

import com.br.sistema_teste.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda,Long> {
    @Query("SELECT v FROM Venda v WHERE v.funcionario.id = :funcionarioId AND v.data BETWEEN :startDate AND :endDate")
    List<Venda> findVendasByFuncionarioAndDateRange(@Param("funcionarioId") Long funcionarioId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId AND v.data BETWEEN :startDate AND :endDate")
    List<Venda> findVendasByClienteAndDateRange(@Param("clienteId") Long clienteId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
