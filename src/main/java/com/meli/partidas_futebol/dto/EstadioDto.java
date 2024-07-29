package com.meli.partidas_futebol.dto;

public class EstadioDto {

    private Long id;
    private String nome;

    public EstadioDto(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
