package com.meli.partidas_futebol.repository;
import com.meli.partidas_futebol.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstadioRepository extends JpaRepository<Estadio, Long>{

    Page<Estadio> findByNomeContaining(String nome, Pageable pageable);
    boolean existsByNome(String nome);

}
