package com.meli.partidas_futebol.repository;
import com.meli.partidas_futebol.model.Clube;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubeRepository extends JpaRepository<Clube, Long> {

    List<Clube> findByNomeContainingAndSiglaEstadoAndAtividade(String nome, String siglaEstado, Boolean atividade, Pageable paging);

    List<Clube> findByNomeContaining(String nome, Pageable paging);

    List<Clube> findBySiglaEstado(String siglaEstado, Pageable paging);

    List<Clube> findByAtividade(Boolean atividade, Pageable paging);

    boolean existsByNomeAndSiglaEstado(String nome, String siglaEstado);
}
