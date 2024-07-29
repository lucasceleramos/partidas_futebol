package com.meli.partidas_futebol.repository;
import com.meli.partidas_futebol.model.Clube;
import com.meli.partidas_futebol.model.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PartidaRepository extends JpaRepository<Partida, Long>{
    Page<Partida> findByClubeMandanteIdOrClubeVisitanteId(Long clubeMandanteId, Long clubeVisitanteId, Pageable pageable);
    Page<Partida> findByClubeMandanteIdOrClubeVisitanteIdAndEstadioId(Long clubeMandanteId, Long clubeVisitanteId, Long estadioId, Pageable pageable);
    Page<Partida> findByEstadioId(Long estadioId, Pageable pageable);

    boolean existsByClubeMandanteOrClubeVisitanteAndDataHoraAfter(Clube clubeMandante, Clube clubeVisitante, LocalDateTime dataHora);
}
