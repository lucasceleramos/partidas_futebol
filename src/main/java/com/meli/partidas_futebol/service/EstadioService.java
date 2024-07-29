package com.meli.partidas_futebol.service;
import com.meli.partidas_futebol.Exception.ValidationException;
import com.meli.partidas_futebol.dto.EstadioDto;
import com.meli.partidas_futebol.model.Estadio;
import com.meli.partidas_futebol.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public String cadastrarEstadio(EstadioDto estadioDto) {
        validarEstadio(estadioDto);
        if (estadioRepository.existsByNome(estadioDto.getNome())) {
            throw new ValidationException("Já existe um estádio com esse nome.");
        }
        Estadio estadio = new Estadio(estadioDto);
        estadioRepository.save(estadio);
        return "Estadio cadastrado com sucesso!";
        }

        private void validarEstadio(EstadioDto estadioDto) {
        if (estadioDto.getNome() == null || estadioDto.getNome().length() < 3) {
            throw new ValidationException("Nome do estádio é obrigatório e deve ter no mínimo 3 caracteres.");
        }}

    public EstadioDto buscarEstadio(Long id) {
        Optional<Estadio> estadio = estadioRepository.findById(id);
        if (estadio.isEmpty()){
            throw new ValidationException("Estadio não encontrado com ID: " + id);
        }
        return new EstadioDto(estadio.get().getNome());
    }

    public void editarEstadio(Long id, EstadioDto estadioDto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio não encontrado com ID: " + id));

        if (estadioDto.getNome().length() < 3) {
            throw new ValidationException("Nome do estádio é obrigatório e deve ter no mínimo 3 caracteres.");
        }

        if (estadioRepository.existsByNome(estadioDto.getNome())) {
            throw new ValidationException("Já existe um estádio com esse nome.");
        }

        estadio.setNome(estadioDto.getNome());
        estadioRepository.save(estadio);
    }

    public void excluirEstadio(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio não encontrado com ID: " + id));
        estadioRepository.delete(estadio);
    }

    public List<EstadioDto> listarEstadio(String nome, int page, int size, String sortBy, String order) {
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        List<Estadio> estadios = estadioRepository.findByNomeContaining(nome, paging).getContent();
        if (estadios.isEmpty()){
            return List.of();
        }
        return estadios.stream().map(estadio -> new EstadioDto(estadio.getNome())).collect(Collectors.toList());
    }
}
