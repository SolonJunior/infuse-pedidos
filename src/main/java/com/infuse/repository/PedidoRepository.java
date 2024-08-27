package com.infuse.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infuse.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByNumeroControle(String numeroControle);

    @Query("SELECT p FROM Pedido p WHERE (:numeroControle IS NULL OR p.numeroControle = :numeroControle) " +
           "AND (:dataCadastro IS NULL OR p.dataCadastro = :dataCadastro)")
    List<Pedido> findByFilters(@Param("numeroControle") String numeroControle, @Param("dataCadastro") LocalDate dataCadastro);
}
