package com.meli.partidas_futebol.dto;
import java.time.LocalDateTime;

public class PartidaDto {

    private Long id;
    private Long clubeMandanteId;
    private Long clubeVisitanteId;
    private String resultado;
    private LocalDateTime dataHora;
    private Long estadioId;
    private int golsMandante;
    private int golsVisitante;

    public PartidaDto(Long clubeMandanteId, Long clubeVisitanteId, String resultado, LocalDateTime dataHora, Long estadioId, int golsMandante, int golsVisitante) {
        this.clubeMandanteId = clubeMandanteId;
        this.clubeVisitanteId = clubeVisitanteId;
        this.resultado = resultado;
        this.dataHora = dataHora;
        this.estadioId = estadioId;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClubeMandanteId() {
        return clubeMandanteId;
    }

    public void setClubeMandanteId(Long clubeMandanteId) {
        this.clubeMandanteId = clubeMandanteId;
    }

    public Long getClubeVisitanteId() {
        return clubeVisitanteId;
    }

    public void setClubeVisitanteId(Long clubeVisitanteId) {
        this.clubeVisitanteId = clubeVisitanteId;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Long getEstadioId() {
        return estadioId;
    }

    public void setEstadioId(Long estadioId) {
        this.estadioId = estadioId;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public void setGolsMandante(int golsMandante) {
        this.golsMandante = golsMandante;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(int golsVisitante) {
        this.golsVisitante = golsVisitante;
    }
}




