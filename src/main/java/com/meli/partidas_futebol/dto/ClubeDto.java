package com.meli.partidas_futebol.dto;

import java.time.LocalDate;

public class ClubeDto {

    private Long id;
    private String nome;
    private String siglaEstado;
    private LocalDate fundacao;
    private boolean atividade;

    public ClubeDto(String nome, String siglaEstado, LocalDate fundacao, boolean atividade) {
        this.nome = nome;
        this.siglaEstado = siglaEstado;
        this.fundacao = fundacao;
        this.atividade = atividade;
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

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public boolean isAtividade() {
        return atividade;
    }

    public void setAtividade(boolean atividade) {
        this.atividade = atividade;
    }
}
