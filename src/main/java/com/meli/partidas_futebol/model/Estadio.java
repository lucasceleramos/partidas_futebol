package com.meli.partidas_futebol.model;
import com.meli.partidas_futebol.dto.EstadioDto;
import jakarta.persistence.*;


@Entity
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nome;

    public Estadio(){}

    public Estadio(EstadioDto estadioDto) {
        this.nome = estadioDto.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}
